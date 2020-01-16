package com.github.nashorn.sandbox;

import com.github.nashorn.sandbox.defender.InterruptibleScriptDefender;
import com.github.nashorn.sandbox.defender.PreambleScriptDefender;
import com.github.nashorn.sandbox.defender.ScriptDefender;
import com.github.nashorn.sandbox.defender.ScriptInterruptor;
import com.github.nashorn.sandbox.exception.ScriptCPUAbuseException;
import com.github.nashorn.sandbox.exception.ScriptMemoryAbuseException;
import com.github.nashorn.sandbox.extinguisher.BindingsScriptExtinguisher;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * 沙箱具体实现
 *
 * @author baez
 * @date 2020-01-04 21:40
 */
public class StandardNashornSandbox implements NashornSandbox {

    private ScriptEngine scriptEngine;

    private SandboxClassFilter classFilter;

    private ExecutorService executorService;

    private long maxCPUTime;

    private long maxMemory;

    public StandardNashornSandbox() {
        this.classFilter = new SandboxClassFilter();
        this.scriptEngine = new NashornScriptEngineFactory().getScriptEngine(classFilter);
        this.allow(ScriptInterruptor.class);
    }

    @Override
    public void allow(Class<?> clazz) {
        classFilter.add(clazz);
    }

    @Override
    public void setMaxCPUTime(long milliseconds) {
        this.maxCPUTime = milliseconds;
    }

    @Override
    public void setMaxMemory(long bytes) {
        this.maxMemory = bytes;
    }

    @Override
    public void setExecutor(ExecutorService executor) {
        this.executorService = executor;
    }

    @Override
    public ScriptContext createScriptContext() {
        SimpleScriptContext simpleScriptContext = new SimpleScriptContext();
        Bindings bindings = scriptEngine.createBindings();
        simpleScriptContext.setBindings(bindings, ENGINE_SCOPE);
        return simpleScriptContext;
    }

    @Override
    public Object eval(String script) throws Exception {
        return eval(script, null);
    }

    @Override
    public Object eval(String script, ScriptContext scriptContext) throws Exception {
        Bindings bindings;
        if (Objects.isNull(scriptContext)) {
            scriptContext = scriptEngine.getContext();
            bindings = scriptEngine.getBindings(ENGINE_SCOPE);
        } else {
            bindings = scriptContext.getBindings(ENGINE_SCOPE);
        }
        applyScriptExtinguishers(bindings);
        String defendedScript = applyScriptDefenders(bindings, script);
        if (maxCPUTime == 0 && maxMemory == 0) {
            return scriptEngine.eval(defendedScript, scriptContext);
        }
        checkExecutorPresence();
        SandboxThread sandboxThread = new SandboxThread(scriptEngine, defendedScript, maxCPUTime, maxMemory);
        executorService.execute(sandboxThread);
        sandboxThread.monitoring();

        if (sandboxThread.isCpuTimeExceeded()) {
            throw new ScriptCPUAbuseException(String.format("Script runs for %sms, used more than %sms of CPU time.",
                    sandboxThread.getCpuRuntime(),
                    maxCPUTime));
        } else if (sandboxThread.isMemoryExceeded()) {
            throw new ScriptMemoryAbuseException(String.format("Script allocates %s bytes for memory, used more than %s bytes.",
                    sandboxThread.getAllocatedMemory(),
                    maxMemory));
        }
        if (Objects.nonNull(sandboxThread.getException())) {
            throw sandboxThread.getException();
        }
        return sandboxThread.getResult();
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        return ((Compilable) scriptEngine).compile(script);
    }

    private void checkExecutorPresence() {
        if (Objects.isNull(executorService)) {
            throw new IllegalStateException("When a max CPU time or max Memory limit is set, an executor needs to be provided by calling #setExecutor method");
        }
    }

    private String applyScriptDefenders(Bindings bindings, String script) throws ScriptException {
        List<ScriptDefender> scriptDefenders = new ArrayList<>(2);
        scriptDefenders.add(new InterruptibleScriptDefender());
        scriptDefenders.add(new PreambleScriptDefender());
        String defendedScript = script;
        for (ScriptDefender scriptDefender : scriptDefenders) {
             defendedScript = scriptDefender.defend(defendedScript);
             scriptDefender.defend(bindings);
        }
        return defendedScript;
    }

    private void applyScriptExtinguishers(Bindings bindings) {
        new BindingsScriptExtinguisher().apply(bindings);
    }
}

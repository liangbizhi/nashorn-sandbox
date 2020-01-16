package org.baez.nashorn.sandbox;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.baez.nashorn.sandbox.defender.InterruptibleScriptDefender;
import org.baez.nashorn.sandbox.defender.PreambleScriptDefender;
import org.baez.nashorn.sandbox.defender.ScriptDefender;
import org.baez.nashorn.sandbox.defender.ScriptInterruptor;
import org.baez.nashorn.sandbox.exception.ScriptCPUAbuseException;
import org.baez.nashorn.sandbox.exception.ScriptMemoryAbuseException;
import org.baez.nashorn.sandbox.extinguisher.EngineFunctionExtinguisher;
import org.baez.nashorn.sandbox.extinguisher.EngineObjectExtinguisher;
import org.baez.nashorn.sandbox.extinguisher.ScriptExtinguisher;

import javax.script.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

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

    private List<ScriptExtinguisher> scriptExtinguishers;

    private List<ScriptDefender> scriptDefenders;

    private long maxCPUTime;

    private long maxMemory;

    public StandardNashornSandbox() {
        this.classFilter = new SandboxClassFilter();
        this.scriptEngine = new NashornScriptEngineFactory().getScriptEngine(classFilter);
        registerScriptExtinguishers(this.scriptEngine);
        registerScriptDefenders();
        this.allow(ScriptInterruptor.class);
    }

    private void registerScriptDefenders() {
        scriptDefenders = new LinkedList<>();
        scriptDefenders.add(new InterruptibleScriptDefender());
        scriptDefenders.add(new PreambleScriptDefender());
    }

    private void registerScriptExtinguishers(ScriptEngine scriptEngine) {
        scriptExtinguishers = new LinkedList<>();
        scriptExtinguishers.add(new EngineObjectExtinguisher(scriptEngine));
        scriptExtinguishers.add(new EngineFunctionExtinguisher(scriptEngine));
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
    public Object eval(String script) throws Exception {
        applyScriptExtinguishers();
        String defendedScript = applyScriptDefenders(scriptEngine, script);
        if (maxCPUTime == 0 && maxMemory == 0) {
            return scriptEngine.eval(defendedScript);
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
    public Object eval(String script, ScriptContext scriptContext) throws Exception {
        return null;
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

    private String applyScriptDefenders(ScriptEngine scriptEngine, String script) throws ScriptException {
        String defendedScript = script;
        for (ScriptDefender scriptDefender : scriptDefenders) {
             defendedScript = scriptDefender.defend(defendedScript);
             scriptDefender.defend(scriptEngine);
        }
        return defendedScript;
    }

    private void applyScriptExtinguishers() {
        scriptExtinguishers.forEach(ScriptExtinguisher::extinguish);
    }
}

package org.baez.nashorn.sandbox;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.baez.nashorn.sandbox.defender.ScriptInterruptor;
import org.baez.nashorn.sandbox.extinguisher.EngineFunctionExtinguisher;
import org.baez.nashorn.sandbox.extinguisher.EngineObjectExtinguisher;
import org.baez.nashorn.sandbox.extinguisher.ScriptExtinguisher;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.LinkedList;
import java.util.List;
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

    private long maxCPUTime;

    private long maxMemory;

    public StandardNashornSandbox() {
        this.classFilter = new SandboxClassFilter();
        this.scriptEngine = new NashornScriptEngineFactory().getScriptEngine(classFilter);
        registerScriptExtinguishers(this.scriptEngine);
        this.allow(ScriptInterruptor.class);
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
    public void setMaxCPUTime(long limit) {
        this.maxCPUTime = limit;
    }

    @Override
    public void setMaxMemory(long limit) {
        this.maxMemory = limit;
    }

    @Override
    public void setExecutor(ExecutorService executor) {
        this.executorService = executor;
    }

    @Override
    public Object eval(String script, Bindings bindings) throws ScriptException {
        return scriptEngine.eval(script, bindings);
    }

    @Override
    public Object eval(String script) throws ScriptException {
        applyScriptExtinguishers();
        return scriptEngine.eval(script);
    }

    private void applyScriptExtinguishers() {
        scriptExtinguishers.forEach(ScriptExtinguisher::extinguish);
    }
}

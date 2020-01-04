package org.baez.nashorn.sandbox.extinguisher;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * 填写描述
 *
 * @author baez
 * @date 2020-01-04 23:47
 */
public class EngineObjectExtinguisher extends AbstractScriptExtinguisher {

    public EngineObjectExtinguisher(ScriptEngine scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public void extinguish() {
        Bindings bindings = scriptEngine.getBindings(ENGINE_SCOPE);
        bindings.remove("__noSuchProperty__");
        bindings.remove("engine");
        bindings.remove("context");
        scriptEngine.setBindings(bindings, ENGINE_SCOPE);
    }
}

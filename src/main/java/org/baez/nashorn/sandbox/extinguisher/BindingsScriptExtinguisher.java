package org.baez.nashorn.sandbox.extinguisher;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * 消除当前{@link Bindings}不安全或多余的功能
 *
 * @author baez
 * @date 2020-01-04 23:47
 */
public class BindingsScriptExtinguisher implements ScriptExtinguisher {

    @Override
    public void apply(Bindings bindings) {
        removeAttributes(bindings);
        removeFunctions(bindings);
    }

    private void removeFunctions(Bindings bindings) {
        bindings.remove("quit");
        bindings.remove("exit");
        bindings.remove("load");
        bindings.remove("print");
        bindings.remove("loadWithNewGlobal");
    }

    private void removeAttributes(Bindings bindings) {
        bindings.remove("__noSuchProperty__");
        bindings.remove("engine");
        bindings.remove("context");
    }
}

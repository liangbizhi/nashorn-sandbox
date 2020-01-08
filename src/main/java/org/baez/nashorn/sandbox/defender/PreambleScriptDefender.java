package org.baez.nashorn.sandbox.defender;

import org.baez.nashorn.sandbox.debug.StandardConsole;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import static javax.script.ScriptContext.ENGINE_SCOPE;

/**
 * 填写描述
 *
 * @author baez
 * @date 2020-01-08 19:31
 */
public class PreambleScriptDefender extends AbstractScriptDefender {

    @Override
    public void defend(ScriptEngine scriptEngine) {
        Bindings bindings = scriptEngine.getBindings(ENGINE_SCOPE);
        bindings.put("console", new StandardConsole());
        scriptEngine.setBindings(bindings, ENGINE_SCOPE);
    }

    @Override
    public String defend(String originalScript) throws ScriptException {
        String clazzName = ScriptInterruptor.class.getName();
        StringBuilder builder = new StringBuilder();
        builder.append("function ").append(INTERRUPTOR_FUNCTION).append("() {")
                .append("Java.type('").append(clazzName).append("').check();}\n")
                .append(originalScript);
        return builder.toString();
    }
}

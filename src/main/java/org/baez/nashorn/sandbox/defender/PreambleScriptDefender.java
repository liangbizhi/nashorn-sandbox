package org.baez.nashorn.sandbox.defender;

import org.baez.nashorn.sandbox.debug.StandardConsole;

import javax.script.Bindings;
import javax.script.ScriptException;

/**
 * 填写描述
 *
 * @author baez
 * @date 2020-01-08 19:31
 */
public class PreambleScriptDefender extends AbstractScriptDefender {

    @Override
    public void defend(Bindings bindings) {
        bindings.put("console", new StandardConsole());
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

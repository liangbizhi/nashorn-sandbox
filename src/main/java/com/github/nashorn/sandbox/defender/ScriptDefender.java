package com.github.nashorn.sandbox.defender;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * 脚本守护与防御。
 *
 * @author baez
 * @date 2020-01-04 21:12
 */
public interface ScriptDefender {
    /**
     * defend script
     * @param originalScript 原始脚本
     * @return much safer script
     * @throws ScriptException ScriptException
     */
    default String defend(String originalScript) throws ScriptException {
        return originalScript;
    }

    /**
     * defend {@link Bindings}
     * @param bindings Bindings
     */
    default void defend(Bindings bindings) {

    }
}

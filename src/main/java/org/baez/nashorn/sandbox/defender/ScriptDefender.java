package org.baez.nashorn.sandbox.defender;

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
    String defend(String originalScript) throws ScriptException;

    /**
     * defend {@link ScriptEngine}
     * @param scriptEngine ScriptEngine
     */
    void defend(ScriptEngine scriptEngine);
}

package org.baez.nashorn.sandbox.defender;

import javax.script.ScriptEngine;

/**
 * Abstract {@link ScriptDefender}
 *
 * @author baez
 * @date 2020-01-08 20:48
 */
public abstract class AbstractScriptDefender implements ScriptDefender {

    /**
     * 脚本检查线程是否中断的函数名称
     */
    public static final String INTERRUPTOR_FUNCTION = "__i031__";

    @Override
    public void defend(ScriptEngine scriptEngine) {
        // default implement: do nothing
    }
}

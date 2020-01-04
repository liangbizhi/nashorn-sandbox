package org.baez.nashorn.sandbox.extinguisher;

import javax.script.ScriptEngine;

/**
 * 填写描述
 *
 * @author baez
 * @date 2020-01-04 23:48
 */
public abstract class AbstractScriptExtinguisher implements ScriptExtinguisher {

    protected ScriptEngine scriptEngine;

    public AbstractScriptExtinguisher(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

}

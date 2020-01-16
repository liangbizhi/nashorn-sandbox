package org.baez.nashorn.sandbox.extinguisher;

import javax.script.Bindings;

/**
 * script灭火器……
 *
 * @author baez
 * @date 2020-01-04 23:29
 */
public interface ScriptExtinguisher {
    /**
     * 消除安全隐患
     * @param bindings
     */
    void apply(Bindings bindings);
}

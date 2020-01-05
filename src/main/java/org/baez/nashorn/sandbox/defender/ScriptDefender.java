package org.baez.nashorn.sandbox.defender;

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
     */
    String defend(String originalScript);
}

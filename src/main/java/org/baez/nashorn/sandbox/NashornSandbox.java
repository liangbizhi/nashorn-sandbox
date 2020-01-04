package org.baez.nashorn.sandbox;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.util.concurrent.ExecutorService;

/**
 * Sandbox of Nashorn
 *
 * @author baez
 * @date 2020-01-04 20:47
 */
public interface NashornSandbox {
    /**
     * 允许沙箱使用的Java类
     * @param clazz 类class
     */
    void allow(Class<?> clazz);

    /**
     * in milliseconds
     *
     * <p>当{@code maxCPUTime > 0}，需要设置{@link #setExecutor(ExecutorService)}
     * @param limit
     */
    void setMaxCPUTime(long limit);

    /**
     * in bytes
     * @param limit
     */
    void setMaxMemory(long limit);

    /**
     *
     * @param executor
     */
    void setExecutor(ExecutorService executor);

    /**
     *
     * @param script
     * @return
     * @throws ScriptException
     */
    Object eval(String script) throws ScriptException;

    /**
     *
     * @param script
     * @param bindings
     * @return
     * @throws ScriptException
     */
    Object eval(String script, Bindings bindings) throws ScriptException;
}

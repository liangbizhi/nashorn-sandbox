package org.baez.nashorn.sandbox;

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
     * @param milliseconds
     */
    void setMaxCPUTime(long milliseconds);

    /**
     * 单位：byte，如果使用binding，酌情设置大一点
     * @param bytes
     */
    void setMaxMemory(long bytes);

    /**
     *
     * @param executor
     */
    void setExecutor(ExecutorService executor);

    /**
     * 执行脚本
     * @param script 符合语法的script脚本
     * @return
     * @throws ScriptException 脚本异常
     * @throws InterruptedException 线程中断异常
     * @throws Exception 其他异常
     */
    Object eval(String script) throws Exception;
}

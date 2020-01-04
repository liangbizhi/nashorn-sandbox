package org.baez.nashorn.sandbox;

/**
 * JavaScript中可以使用的类，用来判断当前脚本线程是否被中断
 *
 * @author baez
 * @date 2020-01-04 21:05
 */
public class ScriptInterruptor {
    /**
     * 判断脚本线程是否被中断
     * @throws InterruptedException
     */
    public static void check() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}

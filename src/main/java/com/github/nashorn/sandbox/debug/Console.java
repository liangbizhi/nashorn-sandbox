package com.github.nashorn.sandbox.debug;

/**
 * 模拟console对象
 *
 * @author baez
 * @date 2020-01-05 23:01
 */
public interface Console {
    /**
     * debug级别日志
     * @param obj
     */
    void debug(Object...obj);
    void debug(String msg, Object...obj);

    /**
     * @see #info(Object...)
     * @param obj
     */
    void log(Object...obj);
    void log(String msg, Object...obj);

    /**
     * info级别日志
     * @param obj
     */
    void info(Object...obj);
    void info(String msg, Object...obj);

    /**
     * error级别日志
     * @param obj
     */
    void error(Object...obj);
    void error(String msg, Object...obj);
}

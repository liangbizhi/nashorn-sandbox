package com.github.nashorn.sandbox.exception;

/**
 * 脚本内存滥用异常
 *
 * @author baez
 * @date 2020-01-08 20:25
 */
public class ScriptMemoryAbuseException extends RuntimeException {
    public ScriptMemoryAbuseException(String message) {
        super(message);
    }
}

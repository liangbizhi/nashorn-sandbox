package com.github.nashorn.sandbox.exception;

/**
 * 脚本CPU滥用异常
 *
 * @author baez
 * @date 2020-01-08 20:24
 */
public class ScriptCPUAbuseException extends RuntimeException {
    public ScriptCPUAbuseException(String message) {
        super(message);
    }
}

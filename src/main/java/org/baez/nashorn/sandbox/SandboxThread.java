package org.baez.nashorn.sandbox;

import javax.script.ScriptEngine;

/**
 * 异步线程运行脚本
 *
 * @author baez
 * @date 2020-01-08 16:48
 */
public class SandboxThread implements Runnable {

    /**
     * 沙箱线程监控器
     */
    private final SandboxThreadMonitor sandboxThreadMonitor;

    private final ScriptEngine scriptEngine;

    private final String script;

    private Exception exception;

    private Object result;

    public SandboxThread(ScriptEngine scriptEngine, String script, final long maxCPUTime, final long maxMemory) {
        this.scriptEngine = scriptEngine;
        this.script = script;
        this.sandboxThreadMonitor = new SandboxThreadMonitor(maxCPUTime, maxMemory);
    }

    @Override
    public void run() {
        this.sandboxThreadMonitor.setThreadToMonitor(Thread.currentThread());
        try {
            result = scriptEngine.eval(script);
        } catch (Exception e) {
            // 中断异常不做处理
            if (!(e instanceof InterruptedException)) {
                exception = e;
            }
        } finally {
            this.sandboxThreadMonitor.stop();
        }
    }

    /**
     * 启动监控
     */
    public void monitoring() throws InterruptedException {
        this.sandboxThreadMonitor.run();
    }

    public boolean isCpuTimeExceeded() {
        return this.sandboxThreadMonitor.isCpuTimeExceeded();
    }

    public boolean isMemoryExceeded() {
        return this.sandboxThreadMonitor.isMemoryExceeded();
    }

    public final long getCpuRuntime() {
        return this.sandboxThreadMonitor.getCpuRuntime();
    }

    public final long getAllocatedMemory() {
        return this.sandboxThreadMonitor.getAllocatedMemory();
    }

    public Exception getException() {
        return exception;
    }

    public Object getResult() {
        return result;
    }
}

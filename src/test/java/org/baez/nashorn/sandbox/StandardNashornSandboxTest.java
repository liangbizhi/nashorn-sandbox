package org.baez.nashorn.sandbox;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 沙箱基本测试
 *
 * @author baez
 * @date 2020-01-04 22:33
 */
public class StandardNashornSandboxTest {

    private StandardNashornSandbox nashornSandbox;

    @Before
    public void setUp() throws Exception {
        nashornSandbox = new StandardNashornSandbox();
    }

    @Test
    public void testEval() throws Exception {
        String script = getScript();
        nashornSandbox.eval(script);
    }

    @Test
    public void testEvalWithLimit() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // s
        nashornSandbox.setMaxCPUTime(10 * 1000);
        // MB
        nashornSandbox.setMaxMemory(100 * 1024 * 1024);
        nashornSandbox.setExecutor(executorService);
        nashornSandbox.eval("while(true) {  }");
    }

    private String getScript() {
        StringBuilder builder = new StringBuilder();
//        builder
//                .append("print('__noSuchProperty__ object: ' + __noSuchProperty__);")
//                .append("print('engine object: ' + engine);")
//                .append("print('context object: ' + context);");
        // 全局变量，shell模式下存在
//        builder
//                .append("print('$ARG object: ' + $ARG);")
//                .append("print('$ENV object: ' + $ENV);")
//                .append("print('$EXEC method: ' + $EXEC);")
//                .append("print('$OPTIONS object: ' + $OPTIONS);")
//                .append("print('$OUT object: ' + $OUT);")
//                .append("print('$ERR object: ' + $ERR);")
//                .append("print('$EXIT object: ' + $EXIT);");
        // nashorn内置方法
        builder
//                .append("print('quit method: ' + quit);")
//                .append("print('exit method: ' + exit);")
                .append("print('print method: ' + print);")
                // 和stdin、stdout相关的函数
//                .append("print('echo method: ' + echo);")
//                .append("print('readLine method: ' + readLine);")
//                .append("print('readFully method: ' + readFully);")
//                .append("print('load method: ' + load);")
//                .append("print('loadWithNewGlobal method: ' + loadWithNewGlobal);")
                .append("print('Object.bindProperties method: ' + Object.bindProperties);");
        return builder.toString();
    }
}
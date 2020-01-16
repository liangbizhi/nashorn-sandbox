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
//                .append("console.log('__noSuchProperty__ object: ' + __noSuchProperty__);")
//                .append("console.log('engine object: ' + engine);")
//                .append("console.log('context object: ' + context);");
        // 全局变量，shell模式下存在
//        builder
//                .append("console.log('$ARG object: ' + $ARG);")
//                .append("console.log('$ENV object: ' + $ENV);")
//                .append("console.log('$EXEC method: ' + $EXEC);")
//                .append("console.log('$OPTIONS object: ' + $OPTIONS);")
//                .append("console.log('$OUT object: ' + $OUT);")
//                .append("console.log('$ERR object: ' + $ERR);")
//                .append("console.log('$EXIT object: ' + $EXIT);");
        // nashorn内置方法
        builder
//                .append("console.log('quit method: ' + quit);")
//                .append("console.log('exit method: ' + exit);")
                .append("console.debug('debug: ');")
                .append("console.log('log: ');")
                .append("console.info('info: ');")
                .append("console.error('error: ');")
//                .append("console.log('print method: ' + print);")
                // 和stdin、stdout相关的函数
//                .append("console.log('echo method: ' + echo);")
//                .append("console.log('readLine method: ' + readLine);")
//                .append("console.log('readFully method: ' + readFully);")
//                .append("console.log('load method: ' + load);")
//                .append("console.log('loadWithNewGlobal method: ' + loadWithNewGlobal);")
                .append("console.log('Object.bindProperties method: ' + Object.bindProperties);");
        return builder.toString();
    }
}
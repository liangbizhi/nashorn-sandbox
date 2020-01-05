package org.baez.nashorn.sandbox.defender;

import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

/**
 * 使得script运行时可中断
 *
 * @author baez
 * @date 2020-01-05 23:21
 */
public class InterruptibleScriptDefender implements ScriptDefender {

    @Override
    public String defend(String originalScript) {
        FunctionNode functionNode = parseScriptAST(originalScript);
        return null;
    }

    /**
     * 解析脚本抽象语法树
     * @see ScriptEnvironment
     * @param originalScript 原始脚本
     * @return FunctionNode
     */
    private FunctionNode parseScriptAST(String originalScript) {
        Options options = new Options("nashorn");
        options.set("anon.functions", true);
        options.set("parse.only", true);
        options.set("scripting", true);
        options.set("print.code", "true");

        ErrorManager errors = new ErrorManager();
        Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
        Source source   = Source.sourceFor("test", originalScript);
        Parser parser = new Parser(context.getEnv(), source, errors);
        return parser.parse();
    }
}

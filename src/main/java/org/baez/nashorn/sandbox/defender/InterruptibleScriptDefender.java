package org.baez.nashorn.sandbox.defender;

import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

import javax.script.ScriptException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 使得script运行时可中断
 *
 * @author baez
 * @date 2020-01-05 23:21
 */
public class InterruptibleScriptDefender extends AbstractScriptDefender {

    private static final String INTERRUPTOR_SCRIPT_SNIPPET = " " + INTERRUPTOR_FUNCTION + "();";

    private static final String OPEN_BRACE = "{";

    private static final String CLOSE_BRACE = "}";

    @Override
    public String defend(String originalScript) throws ScriptException {
        Objects.requireNonNull(originalScript, "script is required");
        FunctionNode functionNode = parseScriptAST(originalScript);
        StandardNodeVisitor standardNodeVisitor = new StandardNodeVisitor();
        functionNode.accept(standardNodeVisitor);
        List<InterruptorInsertPoint> interruptorInsertPoints = standardNodeVisitor.getInterruptorInsertPoints();
        scriptToSnippets(originalScript, interruptorInsertPoints);
        return joinInterruptor(originalScript, interruptorInsertPoints);
    }

    /**
     * 往脚本中添加中断检查方法
     * @param originalScript
     * @param interruptorInsertPoints
     * @return
     */
    private String joinInterruptor(String originalScript, List<InterruptorInsertPoint> interruptorInsertPoints) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean needCloseBrace = false;
        int currentPosition = 0;
        for (int i = 0; i < interruptorInsertPoints.size(); i++) {
            InterruptorInsertPoint interruptorInsertPoint = interruptorInsertPoints.get(i);
            if (needCloseBrace) {
                stringBuilder.append(CLOSE_BRACE);
            }
            String headCodeSnippet = originalScript.substring(currentPosition, interruptorInsertPoint.getHeadEnd());
            stringBuilder.append(headCodeSnippet);
            // 如果下一个字符不是 { 做大括号，并且只包含一个语句
            if (interruptorInsertPoint.getBodyStatementCount() <= 1 && !interruptorInsertPoint.getHeadWithOpenBrace()) {
                stringBuilder.append(OPEN_BRACE);
                needCloseBrace = true;
            } else {
                needCloseBrace = false;
            }
            stringBuilder.append(INTERRUPTOR_SCRIPT_SNIPPET);
            currentPosition = interruptorInsertPoint.getHeadEnd();
        }
        // 最后还有一部分脚本代码，如果有则需要拼接完整
        InterruptorInsertPoint lastInterruptorInsertPoint = interruptorInsertPoints.get(interruptorInsertPoints.size() - 1);
        int lastPosition = lastInterruptorInsertPoint.getHeadEnd();
        int length = originalScript.length();
        if (lastPosition < length) {
            String lastCodeSnippet = originalScript.substring(lastPosition, length);
            stringBuilder.append(lastCodeSnippet);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据抽象语法树对指定节点的解析，把脚本分成多个代码片段
     * @param originalScript
     * @param interruptorInsertPoints
     */
    private void scriptToSnippets(String originalScript, List<InterruptorInsertPoint> interruptorInsertPoints) throws ScriptException {
        // sort
        interruptorInsertPoints.sort(Comparator.comparingInt(InterruptorInsertPoint::getHeadStart));
        for (InterruptorInsertPoint interruptorInsertPoint : interruptorInsertPoints) {
            int headEnd = interruptorInsertPoint.getHeadEnd();
            // 如果下一个字符是 { ，那么也将其包含进来
            String nextSymbol = originalScript.substring(headEnd, headEnd + 1);
            if (OPEN_BRACE.equals(nextSymbol)) {
                // include next symbol
                interruptorInsertPoint.setHeadEnd(headEnd + 1);
                interruptorInsertPoint.setHeadWithOpenBrace(true);
                // body后移一个字符
                interruptorInsertPoint.setBodyStart(interruptorInsertPoint.getBodyStart() + 1);
            } else if (interruptorInsertPoint.getBodyStatementCount() == 1) {
                String headCodeSnippet = originalScript.substring(interruptorInsertPoint.getHeadStart(), interruptorInsertPoint.getHeadEnd());
                throw new ScriptException(String.format("line number: %s, code: %s Expected { but not found",
                        interruptorInsertPoint.getHeadLineNumber(), headCodeSnippet));
            }
        }
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

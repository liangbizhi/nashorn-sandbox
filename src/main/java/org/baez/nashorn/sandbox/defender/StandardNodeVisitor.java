package org.baez.nashorn.sandbox.defender;

import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

import java.util.LinkedList;
import java.util.List;

/**
 * node遍历访问器
 *
 * for、do/while、function、if/else、with、switch/case
 *
 * @author baez
 * @date 2020-01-06 10:49
 */
public class StandardNodeVisitor extends SimpleNodeVisitor {

    private List<InterruptorInsertPoint> interruptorInsertPoints;

    public StandardNodeVisitor() {
        this.interruptorInsertPoints = new LinkedList<>();
    }

    @Override
    public boolean enterWhileNode(WhileNode whileNode) {
        InterruptorInsertPoint interruptorInsertPoint = new InterruptorInsertPoint();
        int headStart = whileNode.position();
        int headLineNumber = whileNode.getLineNumber();
        Block body = whileNode.getBody();
        int headEnd = body.position();
        int bodyStart = headEnd;
        int bodyEnd = body.getFinish();
        int bodyStatementCount = body.getStatementCount();

        interruptorInsertPoint.setHeadStart(headStart);
        interruptorInsertPoint.setHeadEnd(headEnd);
        interruptorInsertPoint.setHeadLineNumber(headLineNumber);
        interruptorInsertPoint.setBodyStart(bodyStart);
        interruptorInsertPoint.setBodyEnd(bodyEnd);
        interruptorInsertPoint.setBodyStatementCount(bodyStatementCount);

        interruptorInsertPoints.add(interruptorInsertPoint);
        return super.enterWhileNode(whileNode);
    }

    @Override
    public boolean enterForNode(ForNode forNode) {
        InterruptorInsertPoint interruptorInsertPoint = new InterruptorInsertPoint();
        int headStart = forNode.position();
        int headLineNumber = forNode.getLineNumber();
        Block body = forNode.getBody();
        int headEnd = body.position();
        int bodyStart = headEnd;
        int bodyEnd = body.getFinish();
        int bodyStatementCount = body.getStatementCount();

        interruptorInsertPoint.setHeadStart(headStart);
        interruptorInsertPoint.setHeadEnd(headEnd);
        interruptorInsertPoint.setHeadLineNumber(headLineNumber);
        interruptorInsertPoint.setBodyStart(bodyStart);
        interruptorInsertPoint.setBodyEnd(bodyEnd);
        interruptorInsertPoint.setBodyStatementCount(bodyStatementCount);

        interruptorInsertPoints.add(interruptorInsertPoint);
        return super.enterForNode(forNode);
    }

    @Override
    public boolean enterFunctionNode(FunctionNode functionNode) {
        int headStart = functionNode.position();
        Block body = functionNode.getBody();
        int headLineNumber = functionNode.getLineNumber();
        int headEnd = body.position();
        if (isWrapperFunction(headStart, headEnd)) {
            return super.enterFunctionNode(functionNode);
        }
        InterruptorInsertPoint interruptorInsertPoint = new InterruptorInsertPoint();
        int bodyStart = headEnd;
        int bodyEnd = body.getFinish();
        int bodyStatementCount = body.getStatementCount();

        interruptorInsertPoint.setHeadStart(headStart);
        interruptorInsertPoint.setHeadEnd(headEnd);
        interruptorInsertPoint.setHeadLineNumber(headLineNumber);
        interruptorInsertPoint.setBodyStart(bodyStart);
        interruptorInsertPoint.setBodyEnd(bodyEnd);
        interruptorInsertPoint.setBodyStatementCount(bodyStatementCount);

        interruptorInsertPoints.add(interruptorInsertPoint);
        return super.enterFunctionNode(functionNode);
    }

    /**
     * 是否最外层包裹的FunctionNode
     * @param headStart
     * @param headEnd
     * @return
     */
    private boolean isWrapperFunction(int headStart, int headEnd) {
        return headStart == 0 && headStart == headEnd;
    }

    /**
     * 获取根据插入点分隔后的代码片段
     * @return
     */
    public List<InterruptorInsertPoint> getInterruptorInsertPoints() {
        return interruptorInsertPoints;
    }
}

package com.github.nashorn.sandbox.defender;

import java.util.Comparator;

/**
 * 根据中断代码需要插入的位置，对源代码进行切割分片。
 * <p>分片包括两部分：head和body</p>
 *
 * 例如，对于脚本片段：
 * <pre>
 *     while(a > 2 && b < 4) {
 *         c++
 *     }
 * </pre>
 * 分割后的head是：
 * <pre>
 *     while(a > 2 && b < 4)
 * </pre>
 * 分割后的body是：
 * <pre>
 *     {
 *         c++
 *     }
 * </pre>
 *
 * @author baez
 * @date 2020-01-07 15:06
 */
public class InterruptorInsertPoint implements Comparator<InterruptorInsertPoint> {

    /**
     * ast节点head开始下标
     */
    private int headStart;

    /**
     * ast节点head结束下标
     */
    private int headEnd;

    /**
     * head代码片段是否以 { 左大括号
     */
    private boolean headWithOpenBrace;

    /**
     * head代码片段
     */
    private String headCodeSnippet;

    /**
     * head部分所在脚本行数
     */
    private int headLineNumber;
    /**
     * body部分开始下标
     */
    private int bodyStart;

    /**
     * body部分结束下标
     */
    private int bodyEnd;

    /**
     * body部分代码片段
     */
    private String bodyCodeSnippet;

    /**
     * 该节点下的语句数
     */
    private int bodyStatementCount;

    @Override
    public int compare(InterruptorInsertPoint o1, InterruptorInsertPoint o2) {
        return o1.getHeadStart() - o2.getHeadStart();
    }

    public int getHeadStart() {
        return headStart;
    }

    public void setHeadStart(int headStart) {
        this.headStart = headStart;
    }

    public int getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(int headEnd) {
        this.headEnd = headEnd;
    }

    public boolean getHeadWithOpenBrace() {
        return headWithOpenBrace;
    }

    public void setHeadWithOpenBrace(boolean headWithOpenBrace) {
        this.headWithOpenBrace = headWithOpenBrace;
    }

    public String getHeadCodeSnippet() {
        return headCodeSnippet;
    }

    public void setHeadCodeSnippet(String headCodeSnippet) {
        this.headCodeSnippet = headCodeSnippet;
    }

    public int getBodyStart() {
        return bodyStart;
    }

    public void setBodyStart(int bodyStart) {
        this.bodyStart = bodyStart;
    }

    public int getBodyEnd() {
        return bodyEnd;
    }

    public void setBodyEnd(int bodyEnd) {
        this.bodyEnd = bodyEnd;
    }

    public String getBodyCodeSnippet() {
        return bodyCodeSnippet;
    }

    public void setBodyCodeSnippet(String bodyCodeSnippet) {
        this.bodyCodeSnippet = bodyCodeSnippet;
    }

    public int getBodyStatementCount() {
        return bodyStatementCount;
    }

    public void setBodyStatementCount(int bodyStatementCount) {
        this.bodyStatementCount = bodyStatementCount;
    }

    public void setHeadLineNumber(int headLineNumber) {
        this.headLineNumber = headLineNumber;
    }

    public int getHeadLineNumber() {
        return headLineNumber;
    }
}

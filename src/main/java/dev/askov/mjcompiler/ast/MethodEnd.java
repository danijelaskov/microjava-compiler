// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class MethodEnd implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String R1;

    public MethodEnd (String R1) {
        this.R1=R1;
    }

    public String getR1() {
        return R1;
    }

    public void setR1(String R1) {
        this.R1=R1;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodEnd(\n");

        buffer.append(" "+tab+R1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodEnd]");
        return buffer.toString();
    }
}

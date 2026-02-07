// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class DelimitedStatement extends Statement {

    private StatementList StatementList;
    private String R2;

    public DelimitedStatement (StatementList StatementList, String R2) {
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.R2=R2;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public String getR2() {
        return R2;
    }

    public void setR2(String R2) {
        this.R2=R2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DelimitedStatement(\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+R2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DelimitedStatement]");
        return buffer.toString();
    }
}

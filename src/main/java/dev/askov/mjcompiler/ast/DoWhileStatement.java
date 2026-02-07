// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class DoWhileStatement extends Statement {

    private DoWhileStatementStart DoWhileStatementStart;
    private Statement Statement;
    private ConditionStart ConditionStart;
    private Condition Condition;
    private String R5;
    private ConditionEnd ConditionEnd;

    public DoWhileStatement (DoWhileStatementStart DoWhileStatementStart, Statement Statement, ConditionStart ConditionStart, Condition Condition, String R5, ConditionEnd ConditionEnd) {
        this.DoWhileStatementStart=DoWhileStatementStart;
        if(DoWhileStatementStart!=null) DoWhileStatementStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ConditionStart=ConditionStart;
        if(ConditionStart!=null) ConditionStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.R5=R5;
        this.ConditionEnd=ConditionEnd;
        if(ConditionEnd!=null) ConditionEnd.setParent(this);
    }

    public DoWhileStatementStart getDoWhileStatementStart() {
        return DoWhileStatementStart;
    }

    public void setDoWhileStatementStart(DoWhileStatementStart DoWhileStatementStart) {
        this.DoWhileStatementStart=DoWhileStatementStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ConditionStart getConditionStart() {
        return ConditionStart;
    }

    public void setConditionStart(ConditionStart ConditionStart) {
        this.ConditionStart=ConditionStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public String getR5() {
        return R5;
    }

    public void setR5(String R5) {
        this.R5=R5;
    }

    public ConditionEnd getConditionEnd() {
        return ConditionEnd;
    }

    public void setConditionEnd(ConditionEnd ConditionEnd) {
        this.ConditionEnd=ConditionEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileStatementStart!=null) DoWhileStatementStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ConditionStart!=null) ConditionStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(ConditionEnd!=null) ConditionEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileStatementStart!=null) DoWhileStatementStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ConditionStart!=null) ConditionStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(ConditionEnd!=null) ConditionEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileStatementStart!=null) DoWhileStatementStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ConditionStart!=null) ConditionStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(ConditionEnd!=null) ConditionEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStatement(\n");

        if(DoWhileStatementStart!=null)
            buffer.append(DoWhileStatementStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionStart!=null)
            buffer.append(ConditionStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+R5);
        buffer.append("\n");

        if(ConditionEnd!=null)
            buffer.append(ConditionEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStatement]");
        return buffer.toString();
    }
}

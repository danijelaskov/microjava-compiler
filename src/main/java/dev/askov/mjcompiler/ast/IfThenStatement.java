// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class IfThenStatement extends Statement {

    private ConditionStart ConditionStart;
    private ErrorProneCondition ErrorProneCondition;
    private String R3;
    private ConditionEnd ConditionEnd;
    private Statement Statement;

    public IfThenStatement (ConditionStart ConditionStart, ErrorProneCondition ErrorProneCondition, String R3, ConditionEnd ConditionEnd, Statement Statement) {
        this.ConditionStart=ConditionStart;
        if(ConditionStart!=null) ConditionStart.setParent(this);
        this.ErrorProneCondition=ErrorProneCondition;
        if(ErrorProneCondition!=null) ErrorProneCondition.setParent(this);
        this.R3=R3;
        this.ConditionEnd=ConditionEnd;
        if(ConditionEnd!=null) ConditionEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ConditionStart getConditionStart() {
        return ConditionStart;
    }

    public void setConditionStart(ConditionStart ConditionStart) {
        this.ConditionStart=ConditionStart;
    }

    public ErrorProneCondition getErrorProneCondition() {
        return ErrorProneCondition;
    }

    public void setErrorProneCondition(ErrorProneCondition ErrorProneCondition) {
        this.ErrorProneCondition=ErrorProneCondition;
    }

    public String getR3() {
        return R3;
    }

    public void setR3(String R3) {
        this.R3=R3;
    }

    public ConditionEnd getConditionEnd() {
        return ConditionEnd;
    }

    public void setConditionEnd(ConditionEnd ConditionEnd) {
        this.ConditionEnd=ConditionEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionStart!=null) ConditionStart.accept(visitor);
        if(ErrorProneCondition!=null) ErrorProneCondition.accept(visitor);
        if(ConditionEnd!=null) ConditionEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionStart!=null) ConditionStart.traverseTopDown(visitor);
        if(ErrorProneCondition!=null) ErrorProneCondition.traverseTopDown(visitor);
        if(ConditionEnd!=null) ConditionEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionStart!=null) ConditionStart.traverseBottomUp(visitor);
        if(ErrorProneCondition!=null) ErrorProneCondition.traverseBottomUp(visitor);
        if(ConditionEnd!=null) ConditionEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfThenStatement(\n");

        if(ConditionStart!=null)
            buffer.append(ConditionStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ErrorProneCondition!=null)
            buffer.append(ErrorProneCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+R3);
        buffer.append("\n");

        if(ConditionEnd!=null)
            buffer.append(ConditionEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfThenStatement]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class AssignmentDesignatorStatement extends Statement {

    private Designator Designator;
    private ErrorProneExpr ErrorProneExpr;

    public AssignmentDesignatorStatement (Designator Designator, ErrorProneExpr ErrorProneExpr) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ErrorProneExpr=ErrorProneExpr;
        if(ErrorProneExpr!=null) ErrorProneExpr.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ErrorProneExpr getErrorProneExpr() {
        return ErrorProneExpr;
    }

    public void setErrorProneExpr(ErrorProneExpr ErrorProneExpr) {
        this.ErrorProneExpr=ErrorProneExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ErrorProneExpr!=null) ErrorProneExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ErrorProneExpr!=null) ErrorProneExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ErrorProneExpr!=null) ErrorProneExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignmentDesignatorStatement(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ErrorProneExpr!=null)
            buffer.append(ErrorProneExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignmentDesignatorStatement]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class PrintExprIntConstStatement extends Statement {

    private Expr Expr;
    private Integer intValue;
    private String R2;

    public PrintExprIntConstStatement (Expr Expr, Integer intValue, String R2) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.intValue=intValue;
        this.R2=R2;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue=intValue;
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
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintExprIntConstStatement(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+intValue);
        buffer.append("\n");

        buffer.append(" "+tab+R2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintExprIntConstStatement]");
        return buffer.toString();
    }
}

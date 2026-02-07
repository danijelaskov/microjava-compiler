// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class ErrorFieldDecl1 extends ErrorProneFieldDecl {

    private ErrorFieldDecl ErrorFieldDecl;

    public ErrorFieldDecl1 (ErrorFieldDecl ErrorFieldDecl) {
        this.ErrorFieldDecl=ErrorFieldDecl;
        if(ErrorFieldDecl!=null) ErrorFieldDecl.setParent(this);
    }

    public ErrorFieldDecl getErrorFieldDecl() {
        return ErrorFieldDecl;
    }

    public void setErrorFieldDecl(ErrorFieldDecl ErrorFieldDecl) {
        this.ErrorFieldDecl=ErrorFieldDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorFieldDecl!=null) ErrorFieldDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorFieldDecl!=null) ErrorFieldDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorFieldDecl!=null) ErrorFieldDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ErrorFieldDecl1(\n");

        if(ErrorFieldDecl!=null)
            buffer.append(ErrorFieldDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ErrorFieldDecl1]");
        return buffer.toString();
    }
}

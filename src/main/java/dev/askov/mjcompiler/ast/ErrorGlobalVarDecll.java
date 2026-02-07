// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class ErrorGlobalVarDecll extends ErrorProneGlobalVarDecl {

    private ErrorGlobalVarDecl ErrorGlobalVarDecl;

    public ErrorGlobalVarDecll (ErrorGlobalVarDecl ErrorGlobalVarDecl) {
        this.ErrorGlobalVarDecl=ErrorGlobalVarDecl;
        if(ErrorGlobalVarDecl!=null) ErrorGlobalVarDecl.setParent(this);
    }

    public ErrorGlobalVarDecl getErrorGlobalVarDecl() {
        return ErrorGlobalVarDecl;
    }

    public void setErrorGlobalVarDecl(ErrorGlobalVarDecl ErrorGlobalVarDecl) {
        this.ErrorGlobalVarDecl=ErrorGlobalVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorGlobalVarDecl!=null) ErrorGlobalVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorGlobalVarDecl!=null) ErrorGlobalVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorGlobalVarDecl!=null) ErrorGlobalVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ErrorGlobalVarDecll(\n");

        if(ErrorGlobalVarDecl!=null)
            buffer.append(ErrorGlobalVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ErrorGlobalVarDecll]");
        return buffer.toString();
    }
}

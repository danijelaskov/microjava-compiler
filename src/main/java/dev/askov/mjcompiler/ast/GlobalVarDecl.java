// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class GlobalVarDecl extends Decl {

    private ErrorProneGlobalVarDecl ErrorProneGlobalVarDecl;

    public GlobalVarDecl (ErrorProneGlobalVarDecl ErrorProneGlobalVarDecl) {
        this.ErrorProneGlobalVarDecl=ErrorProneGlobalVarDecl;
        if(ErrorProneGlobalVarDecl!=null) ErrorProneGlobalVarDecl.setParent(this);
    }

    public ErrorProneGlobalVarDecl getErrorProneGlobalVarDecl() {
        return ErrorProneGlobalVarDecl;
    }

    public void setErrorProneGlobalVarDecl(ErrorProneGlobalVarDecl ErrorProneGlobalVarDecl) {
        this.ErrorProneGlobalVarDecl=ErrorProneGlobalVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorProneGlobalVarDecl!=null) ErrorProneGlobalVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorProneGlobalVarDecl!=null) ErrorProneGlobalVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorProneGlobalVarDecl!=null) ErrorProneGlobalVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDecl(\n");

        if(ErrorProneGlobalVarDecl!=null)
            buffer.append(ErrorProneGlobalVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDecl]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class SingleVarGlobalVarList extends GlobalVarList {

    private ErrorProneGlobalVar ErrorProneGlobalVar;

    public SingleVarGlobalVarList (ErrorProneGlobalVar ErrorProneGlobalVar) {
        this.ErrorProneGlobalVar=ErrorProneGlobalVar;
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.setParent(this);
    }

    public ErrorProneGlobalVar getErrorProneGlobalVar() {
        return ErrorProneGlobalVar;
    }

    public void setErrorProneGlobalVar(ErrorProneGlobalVar ErrorProneGlobalVar) {
        this.ErrorProneGlobalVar=ErrorProneGlobalVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarGlobalVarList(\n");

        if(ErrorProneGlobalVar!=null)
            buffer.append(ErrorProneGlobalVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarGlobalVarList]");
        return buffer.toString();
    }
}

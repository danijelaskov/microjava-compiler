// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class MultipleVarGlobalVarList extends GlobalVarList {

    private ErrorProneGlobalVar ErrorProneGlobalVar;
    private GlobalVarList GlobalVarList;

    public MultipleVarGlobalVarList (ErrorProneGlobalVar ErrorProneGlobalVar, GlobalVarList GlobalVarList) {
        this.ErrorProneGlobalVar=ErrorProneGlobalVar;
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.setParent(this);
        this.GlobalVarList=GlobalVarList;
        if(GlobalVarList!=null) GlobalVarList.setParent(this);
    }

    public ErrorProneGlobalVar getErrorProneGlobalVar() {
        return ErrorProneGlobalVar;
    }

    public void setErrorProneGlobalVar(ErrorProneGlobalVar ErrorProneGlobalVar) {
        this.ErrorProneGlobalVar=ErrorProneGlobalVar;
    }

    public GlobalVarList getGlobalVarList() {
        return GlobalVarList;
    }

    public void setGlobalVarList(GlobalVarList GlobalVarList) {
        this.GlobalVarList=GlobalVarList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.accept(visitor);
        if(GlobalVarList!=null) GlobalVarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.traverseTopDown(visitor);
        if(GlobalVarList!=null) GlobalVarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorProneGlobalVar!=null) ErrorProneGlobalVar.traverseBottomUp(visitor);
        if(GlobalVarList!=null) GlobalVarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarGlobalVarList(\n");

        if(ErrorProneGlobalVar!=null)
            buffer.append(ErrorProneGlobalVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarList!=null)
            buffer.append(GlobalVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarGlobalVarList]");
        return buffer.toString();
    }
}

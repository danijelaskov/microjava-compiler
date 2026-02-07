// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class MultipleVarLocalVarList extends LocalVarList {

    private LocalVarList LocalVarList;
    private LocalVar LocalVar;

    public MultipleVarLocalVarList (LocalVarList LocalVarList, LocalVar LocalVar) {
        this.LocalVarList=LocalVarList;
        if(LocalVarList!=null) LocalVarList.setParent(this);
        this.LocalVar=LocalVar;
        if(LocalVar!=null) LocalVar.setParent(this);
    }

    public LocalVarList getLocalVarList() {
        return LocalVarList;
    }

    public void setLocalVarList(LocalVarList LocalVarList) {
        this.LocalVarList=LocalVarList;
    }

    public LocalVar getLocalVar() {
        return LocalVar;
    }

    public void setLocalVar(LocalVar LocalVar) {
        this.LocalVar=LocalVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarList!=null) LocalVarList.accept(visitor);
        if(LocalVar!=null) LocalVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarList!=null) LocalVarList.traverseTopDown(visitor);
        if(LocalVar!=null) LocalVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarList!=null) LocalVarList.traverseBottomUp(visitor);
        if(LocalVar!=null) LocalVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarLocalVarList(\n");

        if(LocalVarList!=null)
            buffer.append(LocalVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVar!=null)
            buffer.append(LocalVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarLocalVarList]");
        return buffer.toString();
    }
}

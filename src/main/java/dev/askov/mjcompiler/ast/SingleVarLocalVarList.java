// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class SingleVarLocalVarList extends LocalVarList {

    private LocalVar LocalVar;

    public SingleVarLocalVarList (LocalVar LocalVar) {
        this.LocalVar=LocalVar;
        if(LocalVar!=null) LocalVar.setParent(this);
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
        if(LocalVar!=null) LocalVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVar!=null) LocalVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVar!=null) LocalVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarLocalVarList(\n");

        if(LocalVar!=null)
            buffer.append(LocalVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarLocalVarList]");
        return buffer.toString();
    }
}

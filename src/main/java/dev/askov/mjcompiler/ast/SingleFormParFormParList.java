// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class SingleFormParFormParList extends FormParList {

    private ErrorProneFormPar ErrorProneFormPar;

    public SingleFormParFormParList (ErrorProneFormPar ErrorProneFormPar) {
        this.ErrorProneFormPar=ErrorProneFormPar;
        if(ErrorProneFormPar!=null) ErrorProneFormPar.setParent(this);
    }

    public ErrorProneFormPar getErrorProneFormPar() {
        return ErrorProneFormPar;
    }

    public void setErrorProneFormPar(ErrorProneFormPar ErrorProneFormPar) {
        this.ErrorProneFormPar=ErrorProneFormPar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorProneFormPar!=null) ErrorProneFormPar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorProneFormPar!=null) ErrorProneFormPar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorProneFormPar!=null) ErrorProneFormPar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormParFormParList(\n");

        if(ErrorProneFormPar!=null)
            buffer.append(ErrorProneFormPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormParFormParList]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:28


package dev.askov.mjcompiler.ast;

public class MultipleFormParFormParList extends FormParList {

    private ErrorProneFormPar ErrorProneFormPar;
    private FormParList FormParList;

    public MultipleFormParFormParList (ErrorProneFormPar ErrorProneFormPar, FormParList FormParList) {
        this.ErrorProneFormPar=ErrorProneFormPar;
        if(ErrorProneFormPar!=null) ErrorProneFormPar.setParent(this);
        this.FormParList=FormParList;
        if(FormParList!=null) FormParList.setParent(this);
    }

    public ErrorProneFormPar getErrorProneFormPar() {
        return ErrorProneFormPar;
    }

    public void setErrorProneFormPar(ErrorProneFormPar ErrorProneFormPar) {
        this.ErrorProneFormPar=ErrorProneFormPar;
    }

    public FormParList getFormParList() {
        return FormParList;
    }

    public void setFormParList(FormParList FormParList) {
        this.FormParList=FormParList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorProneFormPar!=null) ErrorProneFormPar.accept(visitor);
        if(FormParList!=null) FormParList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorProneFormPar!=null) ErrorProneFormPar.traverseTopDown(visitor);
        if(FormParList!=null) FormParList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorProneFormPar!=null) ErrorProneFormPar.traverseBottomUp(visitor);
        if(FormParList!=null) FormParList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleFormParFormParList(\n");

        if(ErrorProneFormPar!=null)
            buffer.append(ErrorProneFormPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParList!=null)
            buffer.append(FormParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleFormParFormParList]");
        return buffer.toString();
    }
}

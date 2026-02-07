// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class NonEmptyFieldDeclList extends FieldDeclList {

    private FieldDeclList FieldDeclList;
    private ErrorProneFieldDecl ErrorProneFieldDecl;

    public NonEmptyFieldDeclList (FieldDeclList FieldDeclList, ErrorProneFieldDecl ErrorProneFieldDecl) {
        this.FieldDeclList=FieldDeclList;
        if(FieldDeclList!=null) FieldDeclList.setParent(this);
        this.ErrorProneFieldDecl=ErrorProneFieldDecl;
        if(ErrorProneFieldDecl!=null) ErrorProneFieldDecl.setParent(this);
    }

    public FieldDeclList getFieldDeclList() {
        return FieldDeclList;
    }

    public void setFieldDeclList(FieldDeclList FieldDeclList) {
        this.FieldDeclList=FieldDeclList;
    }

    public ErrorProneFieldDecl getErrorProneFieldDecl() {
        return ErrorProneFieldDecl;
    }

    public void setErrorProneFieldDecl(ErrorProneFieldDecl ErrorProneFieldDecl) {
        this.ErrorProneFieldDecl=ErrorProneFieldDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldDeclList!=null) FieldDeclList.accept(visitor);
        if(ErrorProneFieldDecl!=null) ErrorProneFieldDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseTopDown(visitor);
        if(ErrorProneFieldDecl!=null) ErrorProneFieldDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldDeclList!=null) FieldDeclList.traverseBottomUp(visitor);
        if(ErrorProneFieldDecl!=null) ErrorProneFieldDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonEmptyFieldDeclList(\n");

        if(FieldDeclList!=null)
            buffer.append(FieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ErrorProneFieldDecl!=null)
            buffer.append(ErrorProneFieldDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyFieldDeclList]");
        return buffer.toString();
    }
}

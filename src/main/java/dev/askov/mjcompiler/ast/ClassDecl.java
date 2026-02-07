// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:27


package dev.askov.mjcompiler.ast;

public class ClassDecl extends Decl {

    private ClassName ClassName;
    private ErrorProneSuperclass ErrorProneSuperclass;
    private FieldDeclList FieldDeclList;
    private Methods Methods;
    private String R5;

    public ClassDecl (ClassName ClassName, ErrorProneSuperclass ErrorProneSuperclass, FieldDeclList FieldDeclList, Methods Methods, String R5) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ErrorProneSuperclass=ErrorProneSuperclass;
        if(ErrorProneSuperclass!=null) ErrorProneSuperclass.setParent(this);
        this.FieldDeclList=FieldDeclList;
        if(FieldDeclList!=null) FieldDeclList.setParent(this);
        this.Methods=Methods;
        if(Methods!=null) Methods.setParent(this);
        this.R5=R5;
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ErrorProneSuperclass getErrorProneSuperclass() {
        return ErrorProneSuperclass;
    }

    public void setErrorProneSuperclass(ErrorProneSuperclass ErrorProneSuperclass) {
        this.ErrorProneSuperclass=ErrorProneSuperclass;
    }

    public FieldDeclList getFieldDeclList() {
        return FieldDeclList;
    }

    public void setFieldDeclList(FieldDeclList FieldDeclList) {
        this.FieldDeclList=FieldDeclList;
    }

    public Methods getMethods() {
        return Methods;
    }

    public void setMethods(Methods Methods) {
        this.Methods=Methods;
    }

    public String getR5() {
        return R5;
    }

    public void setR5(String R5) {
        this.R5=R5;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(ErrorProneSuperclass!=null) ErrorProneSuperclass.accept(visitor);
        if(FieldDeclList!=null) FieldDeclList.accept(visitor);
        if(Methods!=null) Methods.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ErrorProneSuperclass!=null) ErrorProneSuperclass.traverseTopDown(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseTopDown(visitor);
        if(Methods!=null) Methods.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ErrorProneSuperclass!=null) ErrorProneSuperclass.traverseBottomUp(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseBottomUp(visitor);
        if(Methods!=null) Methods.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ErrorProneSuperclass!=null)
            buffer.append(ErrorProneSuperclass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclList!=null)
            buffer.append(FieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Methods!=null)
            buffer.append(Methods.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+R5);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}

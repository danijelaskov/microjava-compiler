// generated with ast extension for cup
// version 0.8
// 1/1/2023 19:22:26


package dev.askov.mjcompiler.ast;

public interface SyntaxNode { 

    void accept(Visitor visitor);

    void childrenAccept(Visitor visitor);
    void traverseBottomUp(Visitor visitor);
    void traverseTopDown(Visitor visitor);

    SyntaxNode getParent();
    void setParent(SyntaxNode parent);
    int getLine();
    void setLine(int line);
}

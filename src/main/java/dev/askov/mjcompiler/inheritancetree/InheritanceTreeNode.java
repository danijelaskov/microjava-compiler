/*
 * Copyright (C) 2018  Danijel Askov
 *
 * This file is part of MicroJava Compiler.
 *
 * MicroJava Compiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MicroJava Compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.askov.mjcompiler.inheritancetree;

import dev.askov.mjcompiler.exceptions.WrongObjKindException;
import dev.askov.mjcompiler.exceptions.WrongStructKindException;
import dev.askov.mjcompiler.inheritancetree.visitor.InheritanceTreeVisitor;
import dev.askov.mjcompiler.vmt.VMT;
import java.util.ArrayList;
import java.util.List;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public class InheritanceTreeNode {

  private final List<InheritanceTreeNode> children = new ArrayList<>();
  private final InheritanceTreeNode parent;

  private final Obj clss;
  private final VMT vmt = new VMT();

  public InheritanceTreeNode(Obj clss, InheritanceTreeNode parent)
      throws WrongObjKindException, WrongStructKindException {
    if (clss == null || clss.getType() == null) {
      throw new NullPointerException();
    }
    if (clss.getKind() != Obj.Type) {
      throw new WrongObjKindException();
    }
    if (clss.getType().getKind() != Struct.Class) {
      throw new WrongStructKindException();
    }
    this.parent = parent;
    if (this.parent != null) {
      this.parent.children.add(this);
    }
    this.clss = clss;
  }

  public InheritanceTreeNode(Obj clss) throws WrongObjKindException, WrongStructKindException {
    this(clss, InheritanceTree.ROOT_NODE);
  }

  public Obj getClss() {
    return clss;
  }

  public VMT getVMT() {
    return vmt;
  }

  public InheritanceTreeNode getParent() {
    return parent;
  }

  public List<InheritanceTreeNode> getChildren() {
    return children;
  }

  public boolean hasChildren() {
    return !children.isEmpty();
  }

  public void accept(InheritanceTreeVisitor inheritanceTreeNodeVisitor) {
    inheritanceTreeNodeVisitor.visit(this);
    for (var child : children) {
      child.accept(inheritanceTreeNodeVisitor);
    }
  }
}

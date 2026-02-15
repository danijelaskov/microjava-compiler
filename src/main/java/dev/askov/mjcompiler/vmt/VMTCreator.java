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

package dev.askov.mjcompiler.vmt;

import dev.askov.mjcompiler.inheritancetree.InheritanceTree;
import dev.askov.mjcompiler.inheritancetree.InheritanceTreeNode;
import dev.askov.mjcompiler.inheritancetree.visitor.InheritanceTreeVisitor;
import dev.askov.mjcompiler.util.MJUtils;
import rs.etf.pp1.symboltable.concepts.Obj;

/**
 * @author Danijel Askov
 */
public class VMTCreator implements InheritanceTreeVisitor {

  private void updateVMTs(InheritanceTreeNode node, Obj overriddenMethod) {
    node.getVMT().add(overriddenMethod);
    for (var child : node.getChildren()) {
      var childVisited = false;
      for (var member : child.getClss().getType().getMembers()) {
        if (member.getKind() == Obj.Meth) {
          if (MJUtils.haveSameSignatures(member, overriddenMethod)) {
            updateVMTs(child, member);
            childVisited = true;
            break;
          }
        }
      }
      if (!childVisited) {
        updateVMTs(child, overriddenMethod);
      }
    }
  }

  @Override
  public void visit(InheritanceTreeNode node) {
    if (!node.equals(InheritanceTree.ROOT_NODE)) {
      for (var member : node.getClss().getType().getMembers()) {
        if (member.getKind() == Obj.Meth) {
          var parent = node.getParent();
          while (!parent.equals(InheritanceTree.ROOT_NODE)) {
            var overriddenMethodFound = false;
            for (var parentMember : parent.getClss().getType().getMembers()) {
              if (parentMember.getKind() == Obj.Meth) {
                if (MJUtils.haveSameSignatures(member, parentMember)) {
                  if (MJUtils.returnTypesAssignmentCompatible(member, parentMember)) {
                    overriddenMethodFound = true;
                    updateVMTs(parent, parentMember);
                    break;
                  }
                }
              }
            }
            if (overriddenMethodFound) {
              break;
            }
            parent = parent.getParent();
          }
        }
      }
    }
  }
}

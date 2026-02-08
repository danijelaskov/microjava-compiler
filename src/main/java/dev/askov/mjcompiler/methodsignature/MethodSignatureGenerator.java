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

package dev.askov.mjcompiler.methodsignature;

import dev.askov.mjcompiler.ast.ActParsEnd;
import dev.askov.mjcompiler.ast.ActParsStart;
import dev.askov.mjcompiler.ast.IdentDesignator;
import dev.askov.mjcompiler.ast.MemberAccessDesignator;
import dev.askov.mjcompiler.ast.MultipleExprExprList;
import dev.askov.mjcompiler.ast.SingleExprExprList;
import dev.askov.mjcompiler.ast.VisitorAdaptor;
import dev.askov.mjcompiler.mjsymboltable.MJTab;
import rs.etf.pp1.symboltable.concepts.Obj;

/**
 * @author Danijel Askov
 */
public class MethodSignatureGenerator extends VisitorAdaptor {

  private MethodSignature methodSignature;
  private int level = -1;

  public MethodSignature getMethodSignature() {
    return methodSignature;
  }

  public void visit(IdentDesignator identDesignator) {
    if (methodSignature == null) {
      var identDesignatorObj = identDesignator.obj;
      methodSignature = new GlobalMethodSignature(identDesignatorObj.getName());
    }
  }

  public void visit(MemberAccessDesignator memberAccessDesignator) {
    if (methodSignature == null) {
      var identDesignatorObj = memberAccessDesignator.obj;
      methodSignature =
          new ClassMethodSignature(
              identDesignatorObj.getName(),
              memberAccessDesignator.getDesignatorStart().obj.getType());
    }
  }

  public void visit(ActParsStart actParsStart) {
    level++;
  }

  public void visit(ActParsEnd actParsEnd) {
    level--;
  }

  public void visit(MultipleExprExprList multipleExprExprList) {
    if (level == 0) {
      methodSignature.addParameter(multipleExprExprList.getExpr().obj);
      if (multipleExprExprList.getExpr().obj.getType() == MJTab.noType
          && multipleExprExprList.getExpr().obj.getKind() != Obj.Meth) {
        methodSignature.setContainsUndeclaredType();
      }
    }
  }

  public void visit(SingleExprExprList singleExprExprList) {
    if (level == 0) {
      methodSignature.addParameter(singleExprExprList.getExpr().obj);
      if (singleExprExprList.getExpr().obj.getType() == MJTab.noType
          && singleExprExprList.getExpr().obj.getKind() != Obj.Meth) {
        methodSignature.setContainsUndeclaredType();
      }
    }
  }
}

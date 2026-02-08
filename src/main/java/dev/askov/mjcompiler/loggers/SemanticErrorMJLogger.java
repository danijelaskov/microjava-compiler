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

package dev.askov.mjcompiler.loggers;

import dev.askov.mjcompiler.SemanticAnalyzer;
import dev.askov.mjcompiler.methodsignature.ClassMethodSignature;
import dev.askov.mjcompiler.methodsignature.MethodSignature;
import dev.askov.mjcompiler.util.MJUtils;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public class SemanticErrorMJLogger extends MJLogger<Obj> {

  public enum SemanticErrorKind {
    INAPPLICABLE_METHOD,
    INV_PROG_NAME,
    DUP_GLOBAL_DECL,
    DUP_PAR,
    DUP_MEMBER,
    DUP_LOCAL_VAR,
    MAIN_METHOD_DECL_NOT_FOUND,
    NON_PRIMITIVE_TYPE,
    UNRESOLVED_VARIABLE,
    UNRESOLVED_TYPE,
    INV_SUPERCLASS,
    NON_VOID_MAIN,
    MAIN_WITH_PARAMS,
    RET_VAL_FROM_VOID_METHOD,
    RETURN_NOT_FOUND,
    ASSIGINING_SYM_CONST,
    TYPE_MISMATCH,
    UNDEF_METHOD,
    MISPLACED_BREAK,
    MISPLACED_CONTINUE,
    UNDEF_OP,
    INDEXING_NON_ARRAY,
    ACCESSING_MEMBER_OF_NON_OBJECT,
    UNRESOLVED_MEMBER,
    INCOMPATIBLE_RET_TYPE,
    UNINVOKABLE_METHOD,
  }

  public SemanticErrorMJLogger() {
    super(Type.ERROR_LOGGER, "Semantic error");
  }

  @Override
  protected String messageBody(Obj obj, Object... context) {
    var semanticErrorKind = (SemanticErrorKind) context[0];
    String message = null;
    switch (semanticErrorKind) {
      case INAPPLICABLE_METHOD ->
          message =
              "The method \""
                  + ((Object[]) context[1])[0]
                  + "\" is not applicable for the arguments \""
                  + ((Object[]) context[1])[1]
                  + "\"";
      case INV_PROG_NAME -> message = "\"" + obj.getName() + "\" is not a valid program name";
      case DUP_GLOBAL_DECL -> message = "Duplicate global name \"" + obj.getName() + "\"";
      case DUP_PAR -> message = "Duplicate parameter \"" + obj.getName() + "\"";
      case DUP_MEMBER ->
          message =
              "Duplicate inner class member \""
                  + ((Obj) ((Object[]) context[1])[0]).getName()
                  + "."
                  + obj.getName()
                  + "\"";
      case DUP_LOCAL_VAR -> message = "Duplicate local variable \"" + obj.getName() + "\"";
      case MAIN_METHOD_DECL_NOT_FOUND ->
          message =
              "Declaration of global method \"void " + SemanticAnalyzer.MAIN + "()\" not found";
      case NON_PRIMITIVE_TYPE ->
          message =
              "Type \"" + MJUtils.typeToString(obj.getType()) + "\" is not a primitive data type";
      case UNRESOLVED_VARIABLE ->
          message = "\"" + obj.getName() + "\" cannot be resolved to a variable";
      case UNRESOLVED_TYPE -> message = "\"" + obj.getName() + "\" cannot be resolved to a type";
      case INV_SUPERCLASS -> message = "\"" + obj.getName() + "\" is not a valid superclass type";
      case NON_VOID_MAIN ->
          message = "Method \"" + SemanticAnalyzer.MAIN + "\" must be declared as \"void\"";
      case MAIN_WITH_PARAMS ->
          message = "Method \"" + SemanticAnalyzer.MAIN + "\" must not have any formal parameters";
      case RET_VAL_FROM_VOID_METHOD -> message = "Void methods cannot return a value";
      case RETURN_NOT_FOUND ->
          message =
              "Method \""
                  + obj.getName()
                  + "\" must return a result of type \""
                  + MJUtils.typeToString(obj.getType())
                  + "\"";
      case ASSIGINING_SYM_CONST ->
          message = "Symbolic constant \"" + obj.getName() + "\" cannot be assigned";
      case TYPE_MISMATCH ->
          message =
              "Type mismatch: cannot convert from \""
                  + MJUtils.typeToString((Struct) ((Object[]) (context[1]))[0])
                  + "\" to \""
                  + MJUtils.typeToString((Struct) ((Object[]) (context[1]))[1])
                  + "\"";
      case UNDEF_METHOD -> {
        var cntx1 = (Object[]) context[1];
        var overriddenMethodSignature1 = (MethodSignature) cntx1[0];
        message = "The method \"" + overriddenMethodSignature1.toString() + "\" is undefined";
      }
      case MISPLACED_BREAK -> message = "break cannot be used outside of a loop";
      case MISPLACED_CONTINUE -> message = "continue cannot be used outside of a loop";
      case UNDEF_OP -> {
        var cntx2 = (Object[]) context[1];
        var operator = (String) cntx2[cntx2.length - 1];
        var operandTypes = new StringBuilder();
        for (var i = 0; i < cntx2.length - 1; i++) {
          operandTypes.append("\"").append(MJUtils.typeToString((Struct) cntx2[i])).append("\"");
          if (i < cntx2.length - 2) {
            operandTypes.append(", ");
          }
        }
        message =
            "The operator \""
                + operator
                + "\" is undefined for the argument type(s) "
                + operandTypes;
      }
      case INDEXING_NON_ARRAY ->
          message =
              "The type of the designator must be an array type but it resolved to \""
                  + MJUtils.typeToString((Struct) ((Object[]) context[1])[0])
                  + "\"";
      case ACCESSING_MEMBER_OF_NON_OBJECT ->
          message =
              "The type of the designator must be a class type but it resolved to \""
                  + MJUtils.typeToString((Struct) ((Object[]) context[1])[0])
                  + "\"";
      case UNRESOLVED_MEMBER ->
          message = "\"" + obj.getName() + "\" cannot be resolved to a class member";
      case INCOMPATIBLE_RET_TYPE -> {
        var cntx3 = (Object[]) context[1];
        var overriddenMethodSignature2 = (ClassMethodSignature) cntx3[0];
        message =
            "The return type is incompatible with \""
                + overriddenMethodSignature2.toString()
                + "\"";
      }
      case UNINVOKABLE_METHOD -> {
        var cntx4 = (Object[]) context[1];
        var overriddenMethodSignature3 = (MethodSignature) cntx4[0];
        var type = (Struct) cntx4[1];
        message =
            "Cannot invoke \""
                + overriddenMethodSignature3.getMethodName()
                + " "
                + overriddenMethodSignature3.getParameterList()
                + "\" on the type \""
                + MJUtils.typeToString(type)
                + "\"";
      }
    }
    return message;
  }
}

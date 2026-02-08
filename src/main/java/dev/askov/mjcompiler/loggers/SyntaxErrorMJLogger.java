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

import java_cup.runtime.Symbol;

/**
 * @author Danijel Askov
 */
public class SyntaxErrorMJLogger extends MJLogger<Symbol> {

  public enum SyntaxErrorKind {
    INV_GLOBAL_VAR_DECL,
    INV_CLASS_INHERITANCE,
    INV_CLASS_FIELD_DECL,
    INV_FORM_PAR,
    INV_ASSIGNMENT,
    INV_IF_STMT_COND,
    FATAL_ERROR,
    INV_DECL,
  }

  public SyntaxErrorMJLogger() {
    super(Type.ERROR_LOGGER, "Syntax error");
  }

  @Override
  protected String messageBody(Symbol loggedObject, Object... context) {
    var syntaxErrorKind = (SyntaxErrorKind) context[0];
    return switch (syntaxErrorKind) {
      case INV_GLOBAL_VAR_DECL -> "Invalid global variable declaration. Parsing continued";
      case INV_CLASS_INHERITANCE -> "Invalid class inheritance declaration. Parsing continued";
      case INV_CLASS_FIELD_DECL -> "Invalid class field declaration. Parsing continued";
      case INV_FORM_PAR -> "Invalid formal parameter declaration. Parsing continued";
      case INV_ASSIGNMENT -> "Invalid assignment statement. Parsing continued";
      case INV_IF_STMT_COND -> "Invalid if-statement condition. Parsing continued";
      case FATAL_ERROR -> "Fatal syntax error. Parsing aborted";
      case INV_DECL -> "Invalid declaration. Parsing continued";
    };
  }
}

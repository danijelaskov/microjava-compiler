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

import dev.askov.mjcompiler.mjsymboltable.MJTab;
import dev.askov.mjcompiler.util.MJUtils;
import java.util.Optional;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public class ClassMethodSignature extends MethodSignature {

  private final Struct clss;

  public ClassMethodSignature(Obj method, Struct clss) {
    super(method, true);
    this.clss = clss;
  }

  public static Optional<ClassMethodSignature> from(Obj method, Struct clss) {
    if (method == null || method.getKind() != Obj.Meth) {
      return Optional.empty();
    }
    return Optional.of(new ClassMethodSignature(method, clss));
  }

  public ClassMethodSignature(String name, Struct clss) {
    super(name);
    this.clss = clss;
  }

  public Struct getThisParameterType() {
    return clss;
  }

  @Override
  public String toString() {
    return clss != MJTab.noType
        ? MJUtils.typeToString(clss) + "." + super.toString()
        : super.toString();
  }
}

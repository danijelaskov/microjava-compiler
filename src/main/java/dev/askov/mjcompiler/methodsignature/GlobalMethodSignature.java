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

import java.util.Optional;
import rs.etf.pp1.symboltable.concepts.Obj;

/**
 * @author Danijel Askov
 */
public class GlobalMethodSignature extends MethodSignature {

  public GlobalMethodSignature(Obj method) {
    super(method, false);
  }

  public static Optional<GlobalMethodSignature> from(Obj method) {
    if (method == null || method.getKind() != Obj.Meth) {
      return Optional.empty();
    }
    return Optional.of(new GlobalMethodSignature(method));
  }

  public GlobalMethodSignature(String name) {
    super(name);
  }
}

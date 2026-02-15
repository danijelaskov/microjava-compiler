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

package dev.askov.mjcompiler.util;

import dev.askov.mjcompiler.methodsignature.ClassMethodSignature;
import dev.askov.mjcompiler.mjsymboltable.MJTab;
import java.util.Optional;
import java.util.OptionalInt;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public final class MJUtils {

  private MJUtils() {}

  public static boolean haveSameSignatures(Obj method1, Obj method2) {
    if (method1 == null
        || method2 == null
        || method1.getKind() != Obj.Meth
        || method2.getKind() != Obj.Meth) {
      return false;
    }
    return new ClassMethodSignature(method1, MJTab.noType)
        .equals(new ClassMethodSignature(method2, MJTab.noType));
  }

  public static boolean returnTypesAssignmentCompatible(
      Obj overridingMethod, Obj overriddenMethod) {
    if (overridingMethod == null
        || overriddenMethod == null
        || overridingMethod.getKind() != Obj.Meth
        || overriddenMethod.getKind() != Obj.Meth) {
      return false;
    }
    return assignableTo(overridingMethod.getType(), overriddenMethod.getType());
  }

  public static String typeToString(Struct type) {
    switch (type.getKind()) {
      case Struct.Bool -> {
        return "bool";
      }
      case Struct.Int -> {
        return "int";
      }
      case Struct.Char -> {
        return "char";
      }
      case Struct.Array -> {
        return typeToString(type.getElemType()) + "[]";
      }
      case Struct.Class -> {
        if (type == MJTab.nullType) {
          return "null";
        } else {
          return MJTab.findObjForClass(type).getName();
        }
      }
      case Struct.None -> {
        return "void";
      }
      default -> {
        return null;
      }
    }
  }

  public static Optional<String> getCompactClassMethodSignature(Obj method) {
    if (method == null || method.getKind() != Obj.Meth) {
      return Optional.empty();
    }
    return Optional.of(new ClassMethodSignature(method, MJTab.noType).getCompactSignature());
  }

  public static boolean assignableTo(Struct source, Struct destination) {
    if (!canSubstitute(source, destination)) {
      return source.assignableTo(destination);
    }
    return true;
  }

  private static boolean canSubstitute(Struct subclass, Struct superclass) {
    if (subclass.getKind() == Struct.Class && superclass.getKind() == Struct.Class) {
      if (subclass == superclass) {
        return true;
      }
      var subclassElemType = subclass.getElemType();
      while (subclassElemType != null) {
        if (subclassElemType == superclass) {
          return true;
        }
        subclassElemType = subclassElemType.getElemType();
      }
    }
    if (subclass.getKind() == Struct.Array && superclass.getKind() == Struct.Array) {
      return canSubstitute(subclass.getElemType(), superclass.getElemType());
    }
    return false;
  }

  public static OptionalInt sizeOfClassInstance(Struct clss) {
    if (clss == null || clss.getKind() != Struct.Class) {
      return OptionalInt.empty();
    }
    var numberOfFields = 0;
    var superclass = clss;
    while (superclass != null) {
      numberOfFields += superclass.getNumberOfFields();
      superclass = superclass.getElemType();
    }
    return OptionalInt.of(numberOfFields * 4);
  }

  public static boolean isPrimitiveDataType(Struct type) {
    return type.equals(MJTab.intType)
        || type.equals(MJTab.charType)
        || type.equals(MJTab.BOOL_TYPE);
  }
}

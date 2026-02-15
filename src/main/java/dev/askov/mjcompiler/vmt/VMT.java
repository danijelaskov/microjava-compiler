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

import dev.askov.mjcompiler.mjsymboltable.MJTab;
import dev.askov.mjcompiler.util.MJUtils;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

/**
 * @author Danijel Askov
 */
public class VMT {

  private final Set<Obj> methods = new HashSet<>();
  private int size;

  public static final int NAME_ADDR_SEPARATOR = -1;
  public static final int TABLE_TERMINATOR = -2;

  public boolean add(Obj method) {
    if (method == null || method.getKind() != Obj.Meth) {
      return false;
    }
    if (methods.add(method)) {
      MJUtils.getCompactClassMethodSignature(method).ifPresent(sig -> size += sig.length() + 2);
      return true;
    }
    return false;
  }

  private final Obj sourceWord = new Obj(Obj.Con, "$currentChar", MJTab.charType);
  private final Obj destinationWord =
      new Obj(Obj.Var, "$currentWordInStaticMemoryZone", MJTab.intType, 0, 0);

  private void putInStaticMemoryZone(int word) {
    sourceWord.setAdr(word);
    Code.load(sourceWord);
    Code.store(destinationWord);
    destinationWord.setAdr(destinationWord.getAdr() + 1);
  }

  public void generateCreationCode() {
    if (!methods.isEmpty()) {
      for (var method : methods) {
        MJUtils.getCompactClassMethodSignature(method)
            .ifPresent(
                methodSignature -> {
                  var methodAddress = method.getAdr();
                  for (var i = 0; i < methodSignature.length(); i++) {
                    putInStaticMemoryZone(methodSignature.charAt(i));
                  }
                  putInStaticMemoryZone(NAME_ADDR_SEPARATOR);
                  putInStaticMemoryZone(methodAddress);
                });
      }
      putInStaticMemoryZone(TABLE_TERMINATOR);
    }
  }

  public int getSize() {
    return size != 0 ? size + 1 : 0;
  }

  public void setStartAddress(int startAddress) {
    destinationWord.setAdr(startAddress);
  }

  @Override
  public String toString() {
    var stringBuilder = new StringBuilder("VMT {");
    var i = 1;
    var iterator = methods.iterator();

    if (iterator.hasNext()) {
      stringBuilder.append("\n");
    }
    while (iterator.hasNext()) {
      var method = iterator.next();
      stringBuilder
          .append("(")
          .append(i++)
          .append(") ")
          .append(MJUtils.typeToString(method.getType()))
          .append(" ")
          .append(method.getName())
          .append("(");
      var formParsNumber = method.getLevel();
      var currentFormPar = 0;
      var pars = method.getLocalSymbols().iterator();
      while (pars.hasNext() && currentFormPar < formParsNumber) {
        stringBuilder.append(MJUtils.typeToString(pars.next().getType()));
        currentFormPar++;
        if (pars.hasNext() && currentFormPar < formParsNumber) {
          stringBuilder.append(",");
        }
      }
      stringBuilder.append(")");
      stringBuilder.append(" -> ").append(method.getAdr()).append("\n");
    }
    return stringBuilder + "}";
  }

  public boolean isEmpty() {
    return methods.isEmpty();
  }

  public boolean containsSameSignatureMethod(Obj overriddenMethod) {
    for (var method : methods) {
      if (MJUtils.haveSameSignatures(method, overriddenMethod)) {
        return true;
      }
    }
    return false;
  }

  public Optional<Obj> getSameSignatureMethod(Obj overriddenMethod) {
    for (var method : methods) {
      if (MJUtils.haveSameSignatures(method, overriddenMethod)) {
        return Optional.of(method);
      }
    }
    return Optional.empty();
  }
}

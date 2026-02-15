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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public class InheritanceTree {

  public static final InheritanceTreeNode ROOT_NODE;

  static {
    ROOT_NODE =
        new InheritanceTreeNode(
            new Obj(Obj.Type, "$RootClassNode", new Struct(Struct.Class)), null);
  }

  private static final Map<Obj, InheritanceTreeNode> MAP = new HashMap<>();

  public static void addNodeForClass(Obj clss) {
    if (clss == null || clss.getKind() != Obj.Type || clss.getType().getKind() != Struct.Class) {
      return;
    }
    if (MAP.containsKey(clss)) {
      return;
    }
    MAP.put(clss, new InheritanceTreeNode(clss));
  }

  public static void addNodeForClass(Obj subclass, Obj superclass) {
    if (subclass == null
        || superclass == null
        || subclass.getKind() != Obj.Type
        || superclass.getKind() != Obj.Type
        || subclass.getType().getKind() != Struct.Class
        || superclass.getType().getKind() != Struct.Class) {
      return;
    }
    if (MAP.containsKey(subclass) || !MAP.containsKey(superclass)) {
      return;
    }
    MAP.put(subclass, new InheritanceTreeNode(subclass, MAP.get(superclass)));
  }

  public static Optional<InheritanceTreeNode> getNode(Obj clss) {
    if (clss == null || clss.getKind() != Obj.Type || clss.getType().getKind() != Struct.Class) {
      return Optional.empty();
    }
    return Optional.ofNullable(MAP.get(clss));
  }
}

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

package dev.askov.mjcompiler.mjsymboltable;

import dev.askov.mjcompiler.inheritancetree.InheritanceTree;
import dev.askov.mjcompiler.inheritancetree.InheritanceTreeNode;
import dev.askov.mjcompiler.inheritancetree.visitor.LeafNodeVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 * @author Danijel Askov
 */
public class MJTab extends Tab {

  public static final Struct BOOL_TYPE = new Struct(Struct.Bool);
  public static final String MAIN = "main", TRUE = "true", FALSE = "false";
  public static Obj lenMethod;
  public static Obj ordMethod;
  public static Obj chrMethod;
  public static Obj printBoolMethod;
  public static Obj readBoolMethod;
  public static Obj vecTimesVecMethod;
  public static Obj vecTimesScalarMethod;
  public static Obj scalarTimesVecMethod;
  public static Obj vecPlusVecMethod;
  public static final Struct INT_ARRAY_TYPE = new Struct(Struct.Array, Tab.intType);

  private static final String PRINT_BOOL = "$printBool", READ_BOOL = "$readBool";
  private static final String VEC_TIMES_VEC = "$vecTimesVec",
      VEC_PLUS_VEC = "$vecPlusVec",
      VEC_TIMES_SCALAR = "$vecTimesScalar",
      SCALAR_TIMES_VEC = "$scalarTimesVecMethod";
  private static final String LEN = "len", ORD = "ord", CHR = "chr";
  private static final Map<Struct, Obj> CLASS_OBJS = new HashMap<>();
  private static int classId = 0;

  public static void init() {
    Tab.init();
    currentScope.addToLocals(new Obj(Obj.Type, "bool", BOOL_TYPE));
    currentScope.addToLocals(printBoolMethod = new Obj(Obj.Meth, PRINT_BOOL, noType, 0, 2));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "b", BOOL_TYPE, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "width1", intType, 1, 1));

      currentScope.addToLocals(new Obj(Obj.Var, "width2", intType, 2, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "blank", intType, 3, 1));
      printBoolMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
    currentScope.addToLocals(readBoolMethod = new Obj(Obj.Meth, READ_BOOL, BOOL_TYPE, 0, 0));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "skip", charType, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "result", BOOL_TYPE, 0, 1));
      readBoolMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
    lenMethod = currentScope.findSymbol(LEN);
    ordMethod = currentScope.findSymbol(ORD);
    chrMethod = currentScope.findSymbol(CHR);
    currentScope.addToLocals(vecTimesVecMethod = new Obj(Obj.Meth, VEC_TIMES_VEC, intType, 0, 2));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "a", INT_ARRAY_TYPE, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "b", INT_ARRAY_TYPE, 1, 1));

      currentScope.addToLocals(new Obj(Obj.Var, "la", intType, 2, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 3, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "result", intType, 4, 1));
      vecTimesVecMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
    currentScope.addToLocals(
        vecPlusVecMethod = new Obj(Obj.Meth, VEC_PLUS_VEC, INT_ARRAY_TYPE, 0, 2));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "a", INT_ARRAY_TYPE, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "b", INT_ARRAY_TYPE, 1, 1));

      currentScope.addToLocals(new Obj(Obj.Var, "la", intType, 2, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 3, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "result", INT_ARRAY_TYPE, 4, 1));
      vecPlusVecMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
    currentScope.addToLocals(
        vecTimesScalarMethod = new Obj(Obj.Meth, VEC_TIMES_SCALAR, INT_ARRAY_TYPE, 0, 2));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "a", INT_ARRAY_TYPE, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "s", intType, 1, 1));

      currentScope.addToLocals(new Obj(Obj.Var, "la", intType, 2, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 3, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "result", INT_ARRAY_TYPE, 4, 1));
      vecTimesScalarMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
    currentScope.addToLocals(
        scalarTimesVecMethod = new Obj(Obj.Meth, SCALAR_TIMES_VEC, INT_ARRAY_TYPE, 0, 2));
    {
      openScope();
      currentScope.addToLocals(new Obj(Obj.Var, "s", INT_ARRAY_TYPE, 1, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "a", intType, 0, 1));

      currentScope.addToLocals(new Obj(Obj.Var, "la", intType, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 0, 1));
      currentScope.addToLocals(new Obj(Obj.Var, "result", INT_ARRAY_TYPE, 0, 1));
      scalarTimesVecMethod.setLocals(currentScope.getLocals());
      closeScope();
    }
  }

  public static void dump(MJDumpSymbolTableVisitor mjSymbolTableVisitor, Logger logger) {
    if (mjSymbolTableVisitor == null) {
      mjSymbolTableVisitor = new MJDumpSymbolTableVisitor(true);
    }
    for (Scope s = currentScope; s != null; s = s.getOuter()) {
      s.accept(mjSymbolTableVisitor);
    }
    logger.info("Symbol table:\n" + mjSymbolTableVisitor.getOutput());
  }

  public static void dump(Logger logger) {
    dump(null, logger);
  }

  public static Obj insert(int kind, String name, Struct type) {
    Obj result = Tab.insert(kind, name, type);
    if (kind == Obj.Type && type.getKind() == Struct.Class) {
      CLASS_OBJS.put(type, result);
    }
    return result;
  }

  public static Obj findObjForClass(Struct classStruct) {
    return CLASS_OBJS.get(classStruct);
  }

  public static int nextClassId() {
    return classId++;
  }

  public static List<Obj> getLeafClasses() {
    LeafNodeVisitor leafNodeListCreator = new LeafNodeVisitor();
    InheritanceTree.ROOT_NODE.accept(leafNodeListCreator);
    List<Obj> leafClasses = new ArrayList<>();
    for (InheritanceTreeNode node : leafNodeListCreator.getLeafNodes()) {
      leafClasses.add(node.getClss());
    }
    return leafClasses;
  }
}

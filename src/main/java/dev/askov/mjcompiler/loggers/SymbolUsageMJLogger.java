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
import dev.askov.mjcompiler.mjsymboltable.MJDumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 *
 * @author Danijel Askov
 */
public final class SymbolUsageMJLogger extends MJLogger<Obj> {

    private final MJDumpSymbolTableVisitor symbolTableVisitor = new MJDumpSymbolTableVisitor(false);

    public SymbolUsageMJLogger() {
        super(MJLoggerKind.INFO_LOGGER, "Symbol usage");
    }

    @Override
    protected String messageBody(Obj obj, Object... context) {
        String message = null;

        symbolTableVisitor.clearOutput();
        obj.accept(symbolTableVisitor);
        switch (obj.getKind()) {
            case Obj.NO_VALUE -> message = "undeclared symbol \"" + obj.getName() + "\"";
            case Obj.Con -> message = "symbolic constant \"" + obj.getName() + "\"";
            case Obj.Var -> {
                String scalarOrVector = obj.getType().getKind() == Struct.Array ? "vector" : "scalar";
                switch (obj.getLevel()) {
                    case 0 -> message = "global " + scalarOrVector + " variable \"" + obj.getName() + "\"";
                    case 1 -> {
                        boolean thisParameter = obj.getName().equals(SemanticAnalyzer.THIS);
                        if (obj.getAdr() < ((Obj) context[0]).getLevel()) {
                            message = (thisParameter ? "implicit " : "") + scalarOrVector + " formal parameter \""
                                + obj.getName() + "\"";
                        } else {
                            message = "local " + scalarOrVector + " variable \"" + obj.getName() + "\"";
                        }
                    }
                }
            }
            case Obj.Meth -> {
                boolean globalMethod = true;
                for (Obj formalPar : obj.getLocalSymbols()) {
                    if (formalPar.getName().equals(SemanticAnalyzer.THIS)) {
                        globalMethod = false;
                        break;
                    }
                }
                if (globalMethod) {
                    message = "global method \"" + obj.getName() + "\" invocation";
                } else {
                    message = "inner class method \"" + obj.getName() + "\" invocation";
                }
            }
            case Obj.Fld -> {
                String scalarOrVector = obj.getType().getKind() == Struct.Array ? "vector" : "scalar";
                message = scalarOrVector + " inner class field \"" + obj.getName() + "\" access";
            }
            case Obj.Elem -> message = "vector \"" + ((Obj) context[0]).getName() + "\" element access";
            case Obj.Type -> message = "inner class \"" + obj.getName() + "\" instantiation";
        }

        return message + ", " + symbolTableVisitor.getOutput();
    }

}

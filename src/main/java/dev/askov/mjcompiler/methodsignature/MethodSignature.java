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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.askov.mjcompiler.exceptions.WrongObjKindException;
import dev.askov.mjcompiler.util.MJUtils;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 *
 * @author Danijel Askov
 */
public abstract class MethodSignature {

    private Obj method;

    private String methodName;
    private final List<Struct> parameters = new ArrayList<>();
    private String compactParameterList = "";
    private String parameterList = "";
    private boolean containsUndeclaredType = false;

    public MethodSignature(String name) {
        methodName = name;
    }

    public void setContainsUndeclaredType() {
        containsUndeclaredType = true;
    }

    public boolean containsUndeclaredType() {
        return containsUndeclaredType;
    }

    public void addParameter(Struct parameter) {
        parameters.add(parameter);
        compactParameterList += MJUtils.typeToString(parameter);
        parameterList += (parameterList.equals("") ? "" : ", ") + MJUtils.typeToString(parameter);
    }

    public void addParameter(Obj parameter) {
        addParameter(parameter.getType());
    }

    public MethodSignature(Obj method, boolean hasThisParameter) throws WrongObjKindException {
        if (method.getKind() != Obj.Meth) {
            throw new WrongObjKindException();
        }
        this.method = method;
        methodName = method.getName();
        Iterator<Obj> parametersIterator = method.getLocalSymbols().iterator();
        int parameterCount = method.getLevel();
        int i = hasThisParameter ? 1 : 0;
        if (hasThisParameter) {
            parametersIterator.next();
        }
        while (i < parameterCount) {
            Obj currentParameter = parametersIterator.next();
            this.parameters.add(currentParameter.getType());
            String parameterType = MJUtils.typeToString(currentParameter.getType());
            compactParameterList += parameterType;
            parameterList += parameterType;
            if (i < parameterCount - 1) {
                parameterList += ", ";
            }
            i++;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        } else {
            if (!(object instanceof MethodSignature)) {
                return false;
            } else {
                MethodSignature other = (MethodSignature) object;
                if (!methodName.equals(other.methodName)) {
                    return false;
                } else {
                    if (parameters.size() != other.parameters.size()) {
                        return false;
                    } else {
                        Iterator<Struct> thisParametersIterator = parameters.iterator();
                        Iterator<Struct> otherParametersIterator = other.parameters.iterator();
                        while (thisParametersIterator.hasNext()) {
                            if (!thisParametersIterator.next().equals(otherParametersIterator.next())) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
    }

    public boolean isInvokableBy(MethodSignature other) {
        if (!methodName.equals(other.methodName)) {
            return false;
        } else {
            if (parameters.size() != other.parameters.size()) {
                return false;
            } else {
                Iterator<Struct> thisParametersIterator = parameters.iterator();
                Iterator<Struct> otherParametersIterator = other.parameters.iterator();
                while (thisParametersIterator.hasNext()) {
                    if (!MJUtils.assignableTo(otherParametersIterator.next(), thisParametersIterator.next())) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    public Obj getMethod() {
        return method;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParameterList() {
        return "(" + (parameterList != null ? parameterList : "") + ")";
    }

    public String getSignature() {
        return methodName + " (" + (parameterList != null ? parameterList : "") + ")";
    }

    public String getCompactSignature() {
        return methodName + (compactParameterList != null ? compactParameterList : "");
    }

    @Override
    public String toString() {
        return getSignature();
    }

}

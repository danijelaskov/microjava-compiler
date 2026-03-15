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

package dev.askov.mjcompiler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Danijel Askov
 */
public class SemanticAnalyzerTest {

  private static void assertSemanticError(String source) throws Exception {
    var result = CompilationHelper.analyze(source);
    assertFalse("Expected no parse errors", result.parseResult.hasErrors());
    assertTrue("Expected semantic error", result.semanticError);
  }

  private static void assertNoSemanticError(String source) throws Exception {
    var result = CompilationHelper.analyze(source);
    assertFalse("Expected no parse errors", result.parseResult.hasErrors());
    assertFalse("Expected no semantic errors", result.semanticError);
  }

  @Test
  public void programNameClashesWithBuiltInType() throws Exception {
    assertSemanticError(
        """
        program int
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void programNameClashesWithBoolType() throws Exception {
    assertSemanticError(
        """
        program bool
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateGlobalVariables() throws Exception {
    assertSemanticError(
        """
        program Test
          int x;
          int x;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateGlobalConstantAndVariable() throws Exception {
    assertSemanticError(
        """
        program Test
          const int x = 5;
          int x;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateClassDeclaration() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
          class Foo {
            int y;
            {
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateGlobalMethods() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void foo()
          {
          }
          void foo()
          {
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateParameters() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void foo(int x, int x)
          {
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateClassFields() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            int x;
            {
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateClassMethods() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
              void bar()
              {
              }
              void bar()
              {
              }
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void duplicateLocalVariables() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          int x;
          int x;
          {
          }
        }
        """);
  }

  @Test
  public void missingMainMethod() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void foo()
          {
          }
        }
        """);
  }

  @Test
  public void programWithNoMethods() throws Exception {
    assertSemanticError(
        """
        program Test
        {
        }
        """);
  }

  @Test
  public void nonVoidMainMethod() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          int main()
          {
            return 0;
          }
        }
        """);
  }

  @Test
  public void mainWithParameters() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main(int x)
          {
          }
        }
        """);
  }

  @Test
  public void constantOfNonPrimitiveType() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
          const Foo f = 0;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void unresolvedType() throws Exception {
    assertSemanticError(
        """
        program Test
          UnknownType x;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void unresolvedVariable() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          {
            x = 5;
          }
        }
        """);
  }

  @Test
  public void unresolvedVariableInExpression() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          int y;
          {
            y = x;
          }
        }
        """);
  }

  @Test
  public void extendingNonClassType() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo extends int {
            {
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void returnValueFromVoidMethod() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void foo()
          {
            return 5;
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void missingReturnInNonVoidMethod() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          int foo()
          {
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void assigningToConstant() throws Exception {
    assertSemanticError(
        """
        program Test
          const int X = 5;
        {
          void main()
          {
            X = 10;
          }
        }
        """);
  }

  @Test
  public void typeMismatchInAssignment() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
        {
          void main()
          int x;
          Foo f;
          {
            x = f;
          }
        }
        """);
  }

  @Test
  public void typeMismatchInReturnStatement() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
        {
          int foo()
          Foo f;
          {
            return f;
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void typeMismatchInIncrement() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          char c;
          {
            c++;
          }
        }
        """);
  }

  @Test
  public void typeMismatchInDecrement() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          char c;
          {
            c--;
          }
        }
        """);
  }

  @Test
  public void breakOutsideLoop() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          {
            break;
          }
        }
        """);
  }

  @Test
  public void continueOutsideLoop() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          {
            continue;
          }
        }
        """);
  }

  @Test
  public void undefinedOperatorForClassTypes() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
        {
          void main()
          Foo a;
          Foo b;
          {
            a = new Foo;
            b = new Foo;
            if (a > b) {
            }
          }
        }
        """);
  }

  @Test
  public void indexingNonArray() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          int x;
          int y;
          {
            y = x[0];
          }
        }
        """);
  }

  @Test
  public void accessingMemberOfNonObject() throws Exception {
    assertSemanticError(
        """
        program Test
        {
          void main()
          int x;
          int y;
          {
            y = x.foo;
          }
        }
        """);
  }

  @Test
  public void unresolvedClassMember() throws Exception {
    assertSemanticError(
        """
        program Test
          class Foo {
            int x;
            {
            }
          }
        {
          void main()
          Foo f;
          int y;
          {
            f = new Foo;
            y = f.nonExistent;
          }
        }
        """);
  }

  @Test
  public void incompatibleReturnTypeInOverride() throws Exception {
    assertSemanticError(
        """
        program Test
          class Base {
            int x;
            {
              int foo()
              {
                return 0;
              }
            }
          }
          class Sub extends Base {
            {
              void foo()
              {
              }
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void minimalValidProgram() throws Exception {
    assertNoSemanticError(
        """
        program Minimal
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void breakInsideLoop() throws Exception {
    assertNoSemanticError(
        """
        program Test
        {
          void main()
          int i;
          {
            i = 0;
            do {
              i++;
              break;
            } while (i < 10);
          }
        }
        """);
  }

  @Test
  public void continueInsideLoop() throws Exception {
    assertNoSemanticError(
        """
        program Test
        {
          void main()
          int i;
          {
            i = 0;
            do {
              i++;
              continue;
            } while (i < 10);
          }
        }
        """);
  }

  @Test
  public void returnFromNonVoidMethod() throws Exception {
    assertNoSemanticError(
        """
        program Test
        {
          int add(int a, int b)
          {
            return a + b;
          }
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void validConstantDeclarations() throws Exception {
    assertNoSemanticError(
        """
        program Test
          const int MAX = 100;
          const char SPACE = ' ';
          const bool FLAG = true;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void validClassWithInheritance() throws Exception {
    assertNoSemanticError(
        """
        program Test
          class Base {
            int x;
            {
              int getX()
              {
                return this.x;
              }
            }
          }
          class Derived extends Base {
            {
              int getX()
              {
                return this.x + 1;
              }
            }
          }
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void validArrayUsage() throws Exception {
    assertNoSemanticError(
        """
        program Test
        {
          void main()
          int arr[];
          int i;
          {
            arr = new int[5];
            i = 0;
            do {
              arr[i] = i;
              i++;
            } while (i < 5);
          }
        }
        """);
  }
}

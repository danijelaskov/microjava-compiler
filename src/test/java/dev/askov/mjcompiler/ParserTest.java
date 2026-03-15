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
public class ParserTest {

  @Test
  public void validProgramParsesWithoutErrors() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
            {
              void main()
              {
              }
            }
            """);
    assertFalse("Expected no lexical errors", result.lexicalError);
    assertFalse("Expected no syntax errors", result.syntaxError);
    assertFalse("Expected no fatal syntax errors", result.fatalSyntaxError);
  }

  @Test
  public void lexicalErrorOnInvalidCharacter() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
              int x = #;
            {
              void main()
              {
              }
            }
            """);
    assertTrue("Expected lexical error", result.lexicalError);
  }

  @Test
  public void syntaxErrorOnMalformedProgram() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program
            {
              void main()
              {
              }
            }
            """);
    assertTrue("Expected syntax error", result.hasErrors());
  }

  @Test
  public void parsesClassDeclaration() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
              class Foo {
                int x;
                {
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
    assertFalse("Expected no parse errors", result.hasErrors());
  }

  @Test
  public void parsesInheritance() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
              class Base {
                int x;
                {
                }
              }
              class Sub extends Base {
                {
                }
              }
            {
              void main()
              {
              }
            }
            """);
    assertFalse("Expected no parse errors", result.hasErrors());
  }

  @Test
  public void parsesDoWhile() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
            {
              void main()
              int i;
              {
                i = 0;
                do {
                  i++;
                } while (i < 10);
              }
            }
            """);
    assertFalse("Expected no parse errors", result.hasErrors());
  }

  @Test
  public void parsesComplexExpressions() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
            {
              void main()
              int a, b, c;
              {
                a = 1;
                b = 2;
                c = (a + b) * (a - b) / 2;
              }
            }
            """);
    assertFalse("Expected no parse errors", result.hasErrors());
  }

  @Test
  public void parsesArrayDeclarationAndAccess() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
            {
              void main()
              int arr[];
              {
                arr = new int[10];
                arr[0] = 42;
              }
            }
            """);
    assertFalse("Expected no parse errors", result.hasErrors());
  }

  @Test
  public void parsesLogicalOperators() throws Exception {
    var result =
        CompilationHelper.parse(
            """
            program Test
            {
              void main()
              int x;
              {
                x = 5;
                if (x > 0 && x < 10 || x == 42) {
                  print(x);
                }
              }
            }
            """);
    assertFalse("Expected no parse errors", result.hasErrors());
  }
}

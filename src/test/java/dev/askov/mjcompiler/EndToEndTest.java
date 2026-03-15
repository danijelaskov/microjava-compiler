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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

/**
 * @author Danijel Askov
 */
public class EndToEndTest {

  private static final String RESOURCES_PREFIX =
      "src" + File.separator + "test" + File.separator + "resources" + File.separator;

  @Test
  public void simpleCalculatorCompilesSuccessfully() throws Exception {
    var source = Files.readString(Path.of(RESOURCES_PREFIX + "simple_calculator.mj"));
    var result = CompilationHelper.analyze(source);

    assertFalse("Expected no lexical errors", result.parseResult.lexicalError);
    assertFalse("Expected no syntax errors", result.parseResult.syntaxError);
    assertFalse("Expected no semantic errors", result.semanticError);
  }

  @Test
  public void simpleCalculatorProducesCorrectOutput() throws Exception {
    var source = Files.readString(Path.of(RESOURCES_PREFIX + "simple_calculator.mj"));
    var input = Files.readString(Path.of(RESOURCES_PREFIX + "input_stream.txt"));
    var output = CompilationHelper.compileAndRun(source, input);

    assertTrue("Expected output to contain '10 + 2 = 12'", output.contains("10 + 2 = 12"));
    assertTrue("Expected output to contain '10 - 2 = 8'", output.contains("10 - 2 = 8"));
    assertTrue("Expected output to contain '10 * 2 = 20'", output.contains("10 * 2 = 20"));
    assertTrue("Expected output to contain '10 / 2 = 5'", output.contains("10 / 2 = 5"));
  }

  @Test
  public void printIntLiteral() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program PrintInt
            {
              void main()
              {
                print(42);
                print(eol);
              }
            }
            """);
    assertEquals("42\n", output);
  }

  @Test
  public void printCharLiteral() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program PrintChar
            {
              void main()
              {
                print('A');
                print(eol);
              }
            }
            """);
    assertEquals("A\n", output);
  }

  @Test
  public void arithmeticOperations() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Arithmetic
            {
              void main()
              int a, b;
              {
                a = 10;
                b = 3;
                print(a + b);
                print(eol);
                print(a - b);
                print(eol);
                print(a * b);
                print(eol);
                print(a / b);
                print(eol);
                print(a % b);
                print(eol);
              }
            }
            """);
    assertEquals("13\n7\n30\n3\n1\n", output);
  }

  @Test
  public void incrementAndDecrement() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program IncrDecr
            {
              void main()
              int x;
              {
                x = 5;
                x++;
                print(x);
                print(eol);
                x--;
                x--;
                print(x);
                print(eol);
              }
            }
            """);
    assertEquals("6\n4\n", output);
  }

  @Test
  public void ifElseBranching() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program IfElse
            {
              void main()
              int x;
              {
                x = 5;
                if (x > 0) {
                  print(1);
                } else {
                  print(0);
                }
                print(eol);
                x = -1;
                if (x > 0) {
                  print(1);
                } else {
                  print(0);
                }
                print(eol);
              }
            }
            """);
    assertEquals("1\n0\n", output);
  }

  @Test
  public void doWhileCountsToFive() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Counting
            {
              void main()
              int i;
              {
                i = 1;
                do {
                  print(i);
                  print(' ');
                  i++;
                } while (i <= 5);
                print(eol);
              }
            }
            """);
    assertEquals("1 2 3 4 5 \n", output);
  }

  @Test
  public void doWhileWithBreak() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program BreakTest
            {
              void main()
              int i;
              {
                i = 1;
                do {
                  if (i == 4) {
                    break;
                  }
                  print(i);
                  print(' ');
                  i++;
                } while (i <= 10);
                print(eol);
              }
            }
            """);
    assertEquals("1 2 3 \n", output);
  }

  @Test
  public void methodCallAndReturn() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program MethodCall
            {
              int square(int x)
              {
                return x * x;
              }
              void main()
              {
                print(square(7));
                print(eol);
              }
            }
            """);
    assertEquals("49\n", output);
  }

  @Test
  public void recursiveFactorial() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Factorial
            {
              int factorial(int n)
              {
                if (n <= 1) {
                  return 1;
                }
                return n * factorial(n - 1);
              }
              void main()
              {
                print(factorial(1));
                print(eol);
                print(factorial(5));
                print(eol);
                print(factorial(10));
                print(eol);
              }
            }
            """);
    assertEquals("1\n120\n3628800\n", output);
  }

  @Test
  public void iterativeFibonacci() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Fibonacci
            {
              int fib(int n, int a, int b, int temp, int i)
              {
                if (n <= 0) {
                  return 0;
                }
                if (n == 1) {
                  return 1;
                }
                a = 0;
                b = 1;
                i = 2;
                do {
                  temp = b;
                  b = a + b;
                  a = temp;
                  i++;
                } while (i <= n);
                return b;
              }
              void main()
              {
                print(fib(0, 0, 0, 0, 0));
                print(' ');
                print(fib(1, 0, 0, 0, 0));
                print(' ');
                print(fib(2, 0, 0, 0, 0));
                print(' ');
                print(fib(5, 0, 0, 0, 0));
                print(' ');
                print(fib(10, 0, 0, 0, 0));
                print(eol);
              }
            }
            """);
    assertEquals("0 1 1 5 55\n", output);
  }

  @Test
  public void gcd() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program GCD
            {
              int gcd(int a, int b, int temp)
              {
                do {
                  temp = b;
                  b = a % b;
                  a = temp;
                } while (b != 0);
                return a;
              }
              void main()
              {
                print(gcd(48, 18, 0));
                print(eol);
                print(gcd(100, 75, 0));
                print(eol);
                print(gcd(7, 13, 0));
                print(eol);
              }
            }
            """);
    assertEquals("6\n25\n1\n", output);
  }

  @Test
  public void arrayOperations() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program ArrayOps
            {
              void main()
              int arr[];
              int i, sum;
              {
                arr = new int[5];
                i = 0;
                do {
                  arr[i] = (i + 1) * 10;
                  i++;
                } while (i < 5);
                sum = 0;
                i = 0;
                do {
                  sum = sum + arr[i];
                  i++;
                } while (i < 5);
                print(sum);
                print(eol);
              }
            }
            """);
    assertEquals("150\n", output);
  }

  @Test
  public void arrayLen() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program ArrayLen
            {
              void main()
              int arr[];
              {
                arr = new int[7];
                print(len(arr));
                print(eol);
              }
            }
            """);
    assertEquals("7\n", output);
  }

  @Test
  public void readAndPrint() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program ReadPrint
            {
              void main()
              int x;
              {
                read(x);
                print(x * 2);
                print(eol);
              }
            }
            """,
            "21\n");
    assertEquals("42\n", output);
  }

  @Test
  public void readMultipleValues() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program ReadMultiple
            {
              void main()
              int a, b;
              {
                read(a);
                read(b);
                print(a + b);
                print(eol);
              }
            }
            """,
            "15\n27\n");
    assertEquals("42\n", output);
  }

  @Test
  public void classMethodInvocation() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program ClassMethods
              class Counter {
                int count;
                {
                  void increment()
                  {
                    this.count = this.count + 1;
                  }
                  int getCount()
                  {
                    return this.count;
                  }
                }
              }
            {
              void main()
              Counter c;
              {
                c = new Counter;
                c.increment();
                c.increment();
                c.increment();
                print(c.getCount());
                print(eol);
              }
            }
            """);
    assertEquals("3\n", output);
  }

  @Test
  public void polymorphism() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Polymorphism
              class Shape {
                int id;
                {
                  int area()
                  {
                    return 0;
                  }
                }
              }
              class Square extends Shape {
                int side;
                {
                  int area()
                  {
                    return this.side * this.side;
                  }
                  void setSide(int s)
                  {
                    this.side = s;
                  }
                }
              }
              class Rectangle extends Shape {
                int width;
                int height;
                {
                  int area()
                  {
                    return this.width * this.height;
                  }
                  void setDimensions(int w, int h)
                  {
                    this.width = w;
                    this.height = h;
                  }
                }
              }
            {
              void main()
              Shape s;
              Square sq;
              Rectangle r;
              {
                sq = new Square;
                sq.setSide(5);
                s = sq;
                print(s.area());
                print(eol);
                r = new Rectangle;
                r.setDimensions(3, 7);
                s = r;
                print(s.area());
                print(eol);
              }
            }
            """);
    assertEquals("25\n21\n", output);
  }

  @Test
  public void printWithWidth() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program PrintWidth
            {
              void main()
              {
                print(42, 6);
                print(eol);
              }
            }
            """);
    assertEquals("    42\n", output);
  }

  @Test
  public void ordAndChr() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program OrdChr
            {
              void main()
              int code;
              char c;
              {
                c = 'A';
                code = ord(c);
                print(code);
                print(eol);
                c = chr(66);
                print(c);
                print(eol);
              }
            }
            """);
    assertEquals("65\nB\n", output);
  }

  @Test
  public void nestedMethodCalls() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program Nested
            {
              int add(int a, int b)
              {
                return a + b;
              }
              int mul(int a, int b)
              {
                return a * b;
              }
              void main()
              {
                print(add(mul(2, 3), mul(4, 5)));
                print(eol);
              }
            }
            """);
    assertEquals("26\n", output);
  }

  @Test
  public void multipleClassInstances() throws Exception {
    var output =
        CompilationHelper.compileAndRun(
            """
            program MultiInstance
              class Pair {
                int first;
                int second;
                {
                  void set(int a, int b)
                  {
                    this.first = a;
                    this.second = b;
                  }
                  int sum()
                  {
                    return this.first + this.second;
                  }
                }
              }
            {
              void main()
              Pair p1, p2;
              {
                p1 = new Pair;
                p2 = new Pair;
                p1.set(10, 20);
                p2.set(100, 200);
                print(p1.sum());
                print(' ');
                print(p2.sum());
                print(eol);
              }
            }
            """);
    assertEquals("30 300\n", output);
  }

  @Test
  public void compilerRejectsTooFewArguments() throws Exception {
    Compiler.main(new String[] {});
  }

  @Test
  public void compilerRejectsNonExistentSourceFile() throws Exception {
    Compiler.main(new String[] {"nonexistent.mj", "output.obj"});
  }

  @Test
  public void compilerOutputsBytecodeFile() throws Exception {
    var source = Files.readString(Path.of(RESOURCES_PREFIX + "simple_calculator.mj"));
    var objFile = CompilationHelper.compileToFile(source);

    assertTrue("Bytecode file should exist", objFile.exists());
    assertTrue("Bytecode file should not be empty", objFile.length() > 0);
  }

  @Test
  public void equivalentCompilationResults() throws Exception {
    var source =
        """
        program Test
        {
          void main()
          int x;
          {
            x = 42;
            print(x);
            print(eol);
          }
        }
        """;

    var result1 = CompilationHelper.analyze(source);
    var result2 = CompilationHelper.analyze(source);

    assertEquals(
        "Same source should produce same parse result",
        result1.parseResult.hasErrors(),
        result2.parseResult.hasErrors());
    assertEquals(
        "Same source should produce same semantic result",
        result1.semanticError,
        result2.semanticError);
  }
}

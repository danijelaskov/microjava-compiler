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

import org.junit.Test;

/**
 * @author Danijel Askov
 */
public class CompilerTest {

  private static void assertCompiles(String source) throws Exception {
    var result = CompilationHelper.analyze(source);
    assertFalse(
        "Expected no lexical errors, but lexical error was detected",
        result.parseResult.lexicalError);
    assertFalse(
        "Expected no syntax errors, but syntax error was detected", result.parseResult.syntaxError);
    assertFalse(
        "Expected no semantic errors, but semantic error was detected", result.semanticError);
  }

  @Test
  public void emptyMain() throws Exception {
    assertCompiles(
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
  public void intConstants() throws Exception {
    assertCompiles(
        """
        program Constants
          const int MIN = 0;
          const int MAX = 100;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void charConstants() throws Exception {
    assertCompiles(
        """
        program Constants
          const char NEWLINE = ' ';
          const char STAR = '*';
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void boolConstants() throws Exception {
    assertCompiles(
        """
        program Constants
          const bool YES = true;
          const bool NO = false;
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void globalVariables() throws Exception {
    assertCompiles(
        """
        program Globals
          int x;
          char c;
          bool flag;
          int arr[];
        {
          void main()
          {
          }
        }
        """);
  }

  @Test
  public void localVariablesAndArithmetic() throws Exception {
    assertCompiles(
        """
        program Arithmetic
        {
          void main()
          int a, b, c;
          {
            a = 10;
            b = 3;
            c = a + b;
            c = a - b;
            c = a * b;
            c = a / b;
            c = a % b;
          }
        }
        """);
  }

  @Test
  public void incrementAndDecrement() throws Exception {
    assertCompiles(
        """
        program IncrDecr
        {
          void main()
          int x;
          {
            x = 0;
            x++;
            x--;
          }
        }
        """);
  }

  @Test
  public void negation() throws Exception {
    assertCompiles(
        """
        program Negation
        {
          void main()
          int x, y;
          {
            x = 5;
            y = -x;
          }
        }
        """);
  }

  @Test
  public void ifElse() throws Exception {
    assertCompiles(
        """
        program IfElse
        {
          void main()
          int x, y;
          {
            x = 5;
            if (x > 0) {
              y = 1;
            } else {
              y = 0;
            }
          }
        }
        """);
  }

  @Test
  public void nestedIfElse() throws Exception {
    assertCompiles(
        """
        program NestedIf
        {
          void main()
          int x, y;
          {
            x = 5;
            if (x > 10) {
              y = 2;
            } else if (x > 0) {
              y = 1;
            } else {
              y = 0;
            }
          }
        }
        """);
  }

  @Test
  public void doWhileLoop() throws Exception {
    assertCompiles(
        """
        program DoWhile
        {
          void main()
          int i, sum;
          {
            i = 1;
            sum = 0;
            do {
              sum = sum + i;
              i++;
            } while (i <= 10);
          }
        }
        """);
  }

  @Test
  public void nestedDoWhileLoops() throws Exception {
    assertCompiles(
        """
        program NestedLoops
        {
          void main()
          int i, j, count;
          {
            count = 0;
            i = 0;
            do {
              j = 0;
              do {
                count++;
                j++;
              } while (j < 3);
              i++;
            } while (i < 3);
          }
        }
        """);
  }

  @Test
  public void loopWithBreakAndContinue() throws Exception {
    assertCompiles(
        """
        program LoopControl
        {
          void main()
          int i;
          {
            i = 0;
            do {
              i++;
              if (i == 3) {
                continue;
              }
              if (i == 7) {
                break;
              }
            } while (i < 10);
          }
        }
        """);
  }

  @Test
  public void comparisonOperators() throws Exception {
    assertCompiles(
        """
        program Comparisons
        {
          void main()
          int x, y, z;
          {
            x = 5;
            y = 10;
            if (x == y) { z = 1; }
            if (x != y) { z = 2; }
            if (x < y) { z = 3; }
            if (x <= y) { z = 4; }
            if (x > y) { z = 5; }
            if (x >= y) { z = 6; }
          }
        }
        """);
  }

  @Test
  public void logicalOperators() throws Exception {
    assertCompiles(
        """
        program Logic
        {
          void main()
          int x, y, z;
          {
            x = 5;
            y = 10;
            if (x > 0 && y > 0) { z = 1; }
            if (x > 100 || y > 0) { z = 2; }
          }
        }
        """);
  }

  @Test
  public void arrayCreationAndAccess() throws Exception {
    assertCompiles(
        """
        program Arrays
        {
          void main()
          int arr[];
          int sum, i;
          {
            arr = new int[5];
            i = 0;
            do {
              arr[i] = i * 2;
              i++;
            } while (i < 5);
            sum = arr[0] + arr[1] + arr[2];
          }
        }
        """);
  }

  @Test
  public void charArray() throws Exception {
    assertCompiles(
        """
        program CharArray
        {
          void main()
          char chars[];
          {
            chars = new char[3];
            chars[0] = 'a';
            chars[1] = 'b';
            chars[2] = 'c';
          }
        }
        """);
  }

  @Test
  public void arrayWithLen() throws Exception {
    assertCompiles(
        """
        program ArrayLen
        {
          void main()
          int arr[];
          int length;
          {
            arr = new int[10];
            length = len(arr);
          }
        }
        """);
  }

  @Test
  public void methodWithReturnValue() throws Exception {
    assertCompiles(
        """
        program Methods
        {
          int square(int x)
          {
            return x * x;
          }
          void main()
          int result;
          {
            result = square(5);
          }
        }
        """);
  }

  @Test
  public void methodWithMultipleParameters() throws Exception {
    assertCompiles(
        """
        program Methods
        {
          int add(int a, int b)
          {
            return a + b;
          }
          int multiply(int a, int b)
          {
            return a * b;
          }
          void main()
          int x;
          {
            x = add(3, multiply(2, 5));
          }
        }
        """);
  }

  @Test
  public void voidMethod() throws Exception {
    assertCompiles(
        """
        program Methods
        {
          void doNothing()
          {
          }
          void main()
          {
            doNothing();
          }
        }
        """);
  }

  @Test
  public void methodWithArrayParameter() throws Exception {
    assertCompiles(
        """
        program Methods
        {
          int sumArray(int arr[], int size, int i, int sum)
          {
            sum = 0;
            i = 0;
            do {
              sum = sum + arr[i];
              i++;
            } while (i < size);
            return sum;
          }
          void main()
          int data[];
          int total;
          {
            data = new int[3];
            data[0] = 1;
            data[1] = 2;
            data[2] = 3;
            total = sumArray(data, 3, 0, 0);
          }
        }
        """);
  }

  @Test
  public void simpleClass() throws Exception {
    assertCompiles(
        """
        program SimpleClass
          class Point {
            int x;
            int y;
            {
              void setX(int val)
              {
                this.x = val;
              }
              void setY(int val)
              {
                this.y = val;
              }
              int getX()
              {
                return this.x;
              }
              int getY()
              {
                return this.y;
              }
            }
          }
        {
          void main()
          Point p;
          int x;
          {
            p = new Point;
            p.setX(10);
            p.setY(20);
            x = p.getX();
          }
        }
        """);
  }

  @Test
  public void classInheritance() throws Exception {
    assertCompiles(
        """
        program Inheritance
          class Animal {
            int legs;
            {
              int getLegs()
              {
                return this.legs;
              }
              void setLegs(int n)
              {
                this.legs = n;
              }
            }
          }
          class Dog extends Animal {
            {
              void setLegs(int n)
              {
                this.legs = 4;
              }
            }
          }
        {
          void main()
          Dog d;
          Animal a;
          int legs;
          {
            d = new Dog;
            d.setLegs(0);
            a = d;
            legs = a.getLegs();
          }
        }
        """);
  }

  @Test
  public void multiLevelInheritance() throws Exception {
    assertCompiles(
        """
        program MultiLevel
          class A {
            int x;
            {
              int getX()
              {
                return this.x;
              }
            }
          }
          class B extends A {
            int y;
            {
              int getY()
              {
                return this.y;
              }
            }
          }
          class C extends B {
            int z;
            {
              int getZ()
              {
                return this.z;
              }
            }
          }
        {
          void main()
          C obj;
          {
            obj = new C;
          }
        }
        """);
  }

  @Test
  public void printStatements() throws Exception {
    assertCompiles(
        """
        program Printing
        {
          void main()
          int x;
          char c;
          {
            x = 42;
            c = '*';
            print(x);
            print(c);
            print(x, 5);
            print(eol);
          }
        }
        """);
  }

  @Test
  public void readStatements() throws Exception {
    assertCompiles(
        """
        program Reading
        {
          void main()
          int x;
          char c;
          {
            read(x);
            read(c);
          }
        }
        """);
  }

  @Test
  public void ordAndChr() throws Exception {
    assertCompiles(
        """
        program OrdChr
        {
          void main()
          char c;
          int i;
          {
            c = 'A';
            i = ord(c);
            c = chr(i);
          }
        }
        """);
  }

  @Test
  public void fibonacci() throws Exception {
    assertCompiles(
        """
        program Fibonacci
        {
          int fib(int n, int a, int b, int temp, int i)
          {
            if (n <= 1) {
              return n;
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
          int result;
          {
            result = fib(10, 0, 0, 0, 0);
            print(result);
            print(eol);
          }
        }
        """);
  }

  @Test
  public void gcd() throws Exception {
    assertCompiles(
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
          int result;
          {
            result = gcd(48, 18, 0);
            print(result);
            print(eol);
          }
        }
        """);
  }

  @Test
  public void bubbleSort() throws Exception {
    assertCompiles(
        """
        program BubbleSort
        {
          void sort(int arr[], int n, int i, int j, int temp, bool swapped)
          {
            i = 0;
            do {
              swapped = false;
              j = 0;
              do {
                if (arr[j] > arr[j + 1]) {
                  temp = arr[j];
                  arr[j] = arr[j + 1];
                  arr[j + 1] = temp;
                  swapped = true;
                }
                j++;
              } while (j < n - 1 - i);
              i++;
            } while (i < n - 1);
          }
          void main()
          int data[];
          int i;
          {
            data = new int[5];
            data[0] = 5;
            data[1] = 3;
            data[2] = 1;
            data[3] = 4;
            data[4] = 2;
            sort(data, 5, 0, 0, 0, false);
            i = 0;
            do {
              print(data[i]);
              print(' ');
              i++;
            } while (i < 5);
            print(eol);
          }
        }
        """);
  }
}

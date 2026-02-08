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

package dev.askov.compilerconstruction.mjcompiler;

import dev.askov.mjcompiler.MJCompiler;
import java.io.*;
import org.junit.Test;
import rs.etf.pp1.mj.runtime.Run;

/**
 * @author Danijel Askov
 */
public class MJCompilerTest {

  private static final String PATH_PREFIX =
      "src" + File.separator + "test" + File.separator + "resources" + File.separator;

  @Test
  public void simpleCalculatorTest() throws Exception {
    System.out.println(
        "\n\n1) Running MicroJava Compiler. Input file: \""
            + PATH_PREFIX
            + "simple_calculator.mj\"...\n\n");
    // Firstly, we run MicroJava Compiler
    MJCompiler.main(
        new String[] {PATH_PREFIX + "simple_calculator.mj", PATH_PREFIX + "simple_calculator.obj"});

    System.out.println(
        "\n\n2) Running MicroJava Virtual Machine. Input file: \""
            + PATH_PREFIX
            + "simple_calculator.obj\"...\n\n");
    final var originalInputStream = System.in;
    final var fileInputStream = new FileInputStream(PATH_PREFIX + "input_stream.txt");
    System.setIn(fileInputStream);
    // Secondly, we start MicroJava Virtual Machine
    Run.main(new String[] {PATH_PREFIX + "simple_calculator.obj"});
    System.setIn(originalInputStream);
  }
}

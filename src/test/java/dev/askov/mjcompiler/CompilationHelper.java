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

import dev.askov.mjcompiler.ast.Program;
import dev.askov.mjcompiler.inheritancetree.InheritanceTree;
import dev.askov.mjcompiler.inheritancetree.InheritanceTreeNode;
import dev.askov.mjcompiler.symboltable.MJTab;
import dev.askov.mjcompiler.vmt.VMTCodeGenerator;
import dev.askov.mjcompiler.vmt.VMTCreator;
import dev.askov.mjcompiler.vmt.VMTStartAddressGenerator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.mj.runtime.Run;

/**
 * @author Danijel Askov
 */
public class CompilationHelper {

  public static class ParseResult {
    public final boolean lexicalError;
    public final boolean syntaxError;
    public final boolean fatalSyntaxError;
    public final Program program;

    ParseResult(
        boolean lexicalError, boolean syntaxError, boolean fatalSyntaxError, Program program) {
      this.lexicalError = lexicalError;
      this.syntaxError = syntaxError;
      this.fatalSyntaxError = fatalSyntaxError;
      this.program = program;
    }

    public boolean hasErrors() {
      return lexicalError || syntaxError;
    }
  }

  public static class SemanticResult {
    public final ParseResult parseResult;
    public final boolean semanticError;

    SemanticResult(ParseResult parseResult, boolean semanticError) {
      this.parseResult = parseResult;
      this.semanticError = semanticError;
    }
  }

  @SuppressWarnings("unchecked")
  public static void resetCompilerState() {
    try {
      var mapField = InheritanceTree.class.getDeclaredField("MAP");
      mapField.setAccessible(true);
      ((Map<Object, Object>) mapField.get(null)).clear();

      var childrenField = InheritanceTreeNode.class.getDeclaredField("children");
      childrenField.setAccessible(true);
      ((List<Object>) childrenField.get(InheritanceTree.ROOT_NODE)).clear();

      var classObjsField = MJTab.class.getDeclaredField("CLASS_OBJS");
      classObjsField.setAccessible(true);
      ((Map<Object, Object>) classObjsField.get(null)).clear();

      var classIdField = MJTab.class.getDeclaredField("classId");
      classIdField.setAccessible(true);
      classIdField.setInt(null, 0);
    } catch (Exception e) {
      throw new RuntimeException("Failed to reset compiler state", e);
    }
  }

  public static ParseResult parse(String source) throws Exception {
    var lexer = new Lexer(new StringReader(source));
    var parser = new Parser(lexer);
    var symbol = parser.parse();

    Program program = null;
    if (!parser.lexicalErrorDetected() && !parser.syntaxErrorDetected()) {
      program = (Program) symbol.value;
    }

    return new ParseResult(
        parser.lexicalErrorDetected(),
        parser.syntaxErrorDetected(),
        parser.fatalSyntaxErrorDetected(),
        program);
  }

  public static SemanticResult analyze(String source) throws Exception {
    resetCompilerState();
    var parseResult = parse(source);

    if (parseResult.hasErrors()) {
      return new SemanticResult(parseResult, false);
    }

    MJTab.init();
    var semanticAnalyzer = new SemanticAnalyzer();
    parseResult.program.traverseBottomUp(semanticAnalyzer);

    return new SemanticResult(parseResult, semanticAnalyzer.semanticErrorDetected());
  }

  public static File compileToFile(String source) throws Exception {
    resetCompilerState();
    var parseResult = parse(source);

    if (parseResult.hasErrors()) {
      throw new RuntimeException("Source contains lexical/syntax errors");
    }

    MJTab.init();
    var semanticAnalyzer = new SemanticAnalyzer();
    parseResult.program.traverseBottomUp(semanticAnalyzer);

    if (semanticAnalyzer.semanticErrorDetected()) {
      throw new RuntimeException("Source contains semantic errors");
    }

    var vmtCreator = new VMTCreator();
    InheritanceTree.ROOT_NODE.accept(vmtCreator);

    var vmtStartAddressGenerator =
        new VMTStartAddressGenerator(semanticAnalyzer.getStaticVarsCount());
    InheritanceTree.ROOT_NODE.accept(vmtStartAddressGenerator);

    Code.dataSize =
        semanticAnalyzer.getStaticVarsCount() + vmtStartAddressGenerator.getTotalVMTSize();

    var codeGenerator = new CodeGenerator();

    if (semanticAnalyzer.printBoolMethodIsUsed()) CodeGenerator.generatePrintBoolMethod();
    if (semanticAnalyzer.readBoolMethodIsUsed()) CodeGenerator.generateReadBoolMethod();
    if (semanticAnalyzer.vecTimesVecMethodIsUsed()) CodeGenerator.generateVecTimesVecMethod();
    if (semanticAnalyzer.vecPlusVecMethodIsUsed()) CodeGenerator.generateVecPlusVecMethod();
    if (semanticAnalyzer.vecTimesScalarMethodIsUsed()) CodeGenerator.generateVecTimesScalarMethod();
    if (semanticAnalyzer.scalarTimesVectorMethodIsUsed())
      CodeGenerator.generateScalarTimesVectorMethod();

    parseResult.program.traverseBottomUp(codeGenerator);

    Code.mainPc = Code.pc;
    Code.put(Code.enter);
    Code.put(0);
    Code.put(0);

    var vmtCodeGenerator = new VMTCodeGenerator();
    InheritanceTree.ROOT_NODE.accept(vmtCodeGenerator);

    Code.put(Code.call);
    Code.put2(codeGenerator.getMainPc() - Code.pc + 1);
    Code.put(Code.exit);
    Code.put(Code.return_);

    var objFile = File.createTempFile("mjtest_", ".obj");
    objFile.deleteOnExit();
    Code.write(new FileOutputStream(objFile));

    return objFile;
  }

  public static String compileAndRun(String source, String input) throws Exception {
    var objFile = compileToFile(source);
    return runVM(objFile, input);
  }

  public static String compileAndRun(String source) throws Exception {
    return compileAndRun(source, "");
  }

  public static String runVM(File objFile, String input) {
    var originalIn = System.in;
    var originalOut = System.out;
    try {
      System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
      var outputStream = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

      Run.main(new String[] {objFile.getAbsolutePath()});

      var output = outputStream.toString(StandardCharsets.UTF_8);
      var completionIndex = output.indexOf("\nCompletion took");
      if (completionIndex >= 0) {
        output = output.substring(0, completionIndex);
      }
      return output;
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }
  }
}

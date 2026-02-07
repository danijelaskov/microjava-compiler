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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import dev.askov.mjcompiler.ast.Program;
import dev.askov.mjcompiler.inheritancetree.InheritanceTree;
import dev.askov.mjcompiler.inheritancetree.visitor.InheritanceTreePrinter;
import dev.askov.mjcompiler.mjsymboltable.MJTab;
import dev.askov.mjcompiler.util.Log4JUtils;
import dev.askov.mjcompiler.vmt.VMTCodeGenerator;
import dev.askov.mjcompiler.vmt.VMTCreator;
import dev.askov.mjcompiler.vmt.VMTStartAddressGenerator;
import rs.etf.pp1.mj.runtime.Code;

/**
 *
 * @author Danijel Askov
 */
public class MJCompiler {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    private static final Logger LOGGER = Logger.getLogger(MJCompiler.class);

    public static void tsdump() {
        MJTab.dump(LOGGER);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            LOGGER.error("Too few arguments. Usage: MJCompiler <source-file> <obj-file>");
            return;
        }
        File sourceFile = new File(args[0]);
        if (!sourceFile.exists()) {
            LOGGER.error("Source file \"" + sourceFile.getAbsolutePath() + "\" has not been found!");
            return;
        }
        LOGGER.info("Compiling source file \"" + sourceFile.getAbsolutePath() + "\"...");
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            MJLexer lexer = new MJLexer(br);
            MJParser parser = new MJParser(lexer);
            Symbol symbol = parser.parse();

            if (!parser.lexicalErrorDetected() && !parser.syntaxErrorDetected()) {
                LOGGER.info("No syntax errors have been detected in \"" + sourceFile.getAbsolutePath() + "\"");

                Program program = (Program) symbol.value;

                LOGGER.info("Abstract syntax tree:\n" + program.toString(""));

                MJTab.init();
                SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
                program.traverseBottomUp(semanticAnalyzer);

                tsdump();

                if (!semanticAnalyzer.semanticErrorDetected()) {

                    VMTCreator vmtCreator = new VMTCreator();
                    InheritanceTree.ROOT_NODE.accept(vmtCreator);

                    VMTStartAddressGenerator vmtStartAddressGenerator = new VMTStartAddressGenerator(
                            semanticAnalyzer.getStaticVarsCount());
                    InheritanceTree.ROOT_NODE.accept(vmtStartAddressGenerator);

                    Code.dataSize = semanticAnalyzer.getStaticVarsCount() + vmtStartAddressGenerator.getTotalVMTSize();

                    LOGGER.info("No semantic errors have been detected in \"" + sourceFile.getAbsolutePath() + "\"");

                    File objFile = new File(args[1]);
                    LOGGER.info("Generating bytecode file \"" + objFile.getAbsolutePath() + "\"...");
                    if (objFile.exists()) {
                        LOGGER.info("Deleting old bytecode file \"" + objFile.getAbsolutePath() + "\"...");
                        if (objFile.delete())
                            LOGGER.info("Old bytecode file \"" + objFile.getAbsolutePath() + "\" has been deleted.");
                        else
                            LOGGER.error("Old bytecode file \"" + objFile.getAbsolutePath() + "\" has not been deleted.");
                    }

                    CodeGenerator codeGenerator = new CodeGenerator();

                    if (semanticAnalyzer.printBoolMethodIsUsed()) CodeGenerator.generatePrintBoolMethod();
                    if (semanticAnalyzer.readBoolMethodIsUsed()) CodeGenerator.generateReadBoolMethod();
                    if (semanticAnalyzer.vecTimesVecMethodIsUsed()) CodeGenerator.generateVecTimesVecMethod();
                    if (semanticAnalyzer.vecPlusVecMethodIsUsed()) CodeGenerator.generateVecPlusVecMethod();
                    if (semanticAnalyzer.vecTimesScalarMethodIsUsed()) CodeGenerator.generateVecTimesScalarMethod();
                    if (semanticAnalyzer.scalarTimesVectorMethodIsUsed()) CodeGenerator.generateScalarTimesVectorMethod();

                    program.traverseBottomUp(codeGenerator);

                    InheritanceTreePrinter inheritanceTreeNodePrinter = new InheritanceTreePrinter();
                    InheritanceTree.ROOT_NODE.accept(inheritanceTreeNodePrinter);

                    Code.mainPc = Code.pc;
                    Code.put(Code.enter);
                    Code.put(0);
                    Code.put(0);

                    VMTCodeGenerator vmtCodeGenerator = new VMTCodeGenerator();
                    InheritanceTree.ROOT_NODE.accept(vmtCodeGenerator);

                    Code.put(Code.call);
                    Code.put2(codeGenerator.getMainPc() - Code.pc + 1);
                    Code.put(Code.exit);
                    Code.put(Code.return_);

                    Code.write(new FileOutputStream(objFile));
                    LOGGER.info("Bytecode file \"" + objFile.getAbsolutePath() + "\" has been generated.");
                    LOGGER.info("Compilation of source file \"" + sourceFile.getAbsolutePath() + "\" has finished successfully.");
                } else {
                    LOGGER.error("Source file \"" + sourceFile.getAbsolutePath() + "\" contains semantic error(s)!");
                    LOGGER.error("Compilation of source file \"" + sourceFile.getAbsolutePath() + "\" has finished unsuccessfully.");
                }

            } else {
                if (parser.lexicalErrorDetected()) {
                    LOGGER.error("Source file \"" + sourceFile.getAbsolutePath() + "\" contains lexical error(s)!");
                }
                if (parser.syntaxErrorDetected()) {
                    LOGGER.error("Source file \"" + sourceFile.getAbsolutePath() + "\" contains syntax error(s)!");
                }
                LOGGER.error("Compilation of source file \"" + sourceFile.getAbsolutePath() + "\" has finished unsuccessfully.");
            }

        }
    }

}

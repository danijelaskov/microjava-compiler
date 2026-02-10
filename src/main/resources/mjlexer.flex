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

import java_cup.runtime.Symbol;
import dev.askov.mjcompiler.loggers.LexicalErrorMJLogger;

%%

%class MJLexer
%cup
%line
%column

%{
	LexicalErrorMJLogger lexicalErrorMJLogger = new LexicalErrorMJLogger();

	private Symbol newSymbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol newSymbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}
%}

%eofval{
	return newSymbol(sym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Whitespace     = {LineTerminator}|[\t\f\b ]

SingleLineComment = "//"{InputCharacter}*{LineTerminator}?
Comment = {SingleLineComment}

DecIntegerLiteral = [0-9]+
PrintableCharLiteral = "'"[ -~]"'"
BooleanLiteral = "true"|"false"
Identifier = [a-zA-Z][a-zA-Z0-9_]*

%%

<YYINITIAL> {
	// Whitespace characters
	{Whitespace}           { /* ignore */ }
	
	// Keywords
	"program"              { return newSymbol(sym.PROGRAM); }
	"break"                { return newSymbol(sym.BREAK); }
	"class"                { return newSymbol(sym.CLASS); }
	"else"                 { return newSymbol(sym.ELSE); }
	"if"                   { return newSymbol(sym.IF); }
	"new"                  { return newSymbol(sym.NEW); }
	"print"                { return newSymbol(sym.PRINT); }
	"read"                 { return newSymbol(sym.READ); }
	"return"               { return newSymbol(sym.RETURN); }
	"void"                 { return newSymbol(sym.VOID); }
	"do"                   { return newSymbol(sym.DO); }
	"while"                { return newSymbol(sym.WHILE); }
	"extends"              { return newSymbol(sym.EXTENDS);}
	"continue"             { return newSymbol(sym.CONTINUE); }
	"const"                { return newSymbol(sym.CONST); }
	
	// Operators
	// Arithmetic operatrors
	"+"                    { return newSymbol(sym.PLUS); }
	"-"                    { return newSymbol(sym.MINUS); }
	"*"                    { return newSymbol(sym.TIMES); }
	"/"                    { return newSymbol(sym.DIV); }
	"%"                    { return newSymbol(sym.MOD); }
	"++"                   { return newSymbol(sym.INCR); }
	"--"                   { return newSymbol(sym.DECR); }
	// Relational operatrors
	"=="                   { return newSymbol(sym.EQ); }
	"!="                   { return newSymbol(sym.NEQ); }
	"<"                    { return newSymbol(sym.LT); }
	"<="                   { return newSymbol(sym.LEQ); }
	">"                    { return newSymbol(sym.GT); }
	">="                   { return newSymbol(sym.GEQ); }
	// Logical operatrors
	"&&"                   { return newSymbol(sym.AND); }
	"||"                   { return newSymbol(sym.OR); }

	// Assignment
	"="                    { return newSymbol(sym.ASSIGN); }
	
	// Separators
	";"                    { return newSymbol(sym.SEMI); }
	","                    { return newSymbol(sym.COMMA); }
	"."                    { return newSymbol(sym.DOT); }
	
	// Delimiters
	"("                    { return newSymbol(sym.LPAREN); } // Parentheses
	")"                    { return newSymbol(sym.RPAREN); }
	"["                    { return newSymbol(sym.LBRACKET); } // (Square) Brackets
	"]"                    { return newSymbol(sym.RBRACKET); }
	"{"                    { return newSymbol(sym.LBRACE); } // (Curly) Braces
	"}"                    { return newSymbol(sym.RBRACE); }
	
	// Comments
	{Comment}              { /* ignore */ }
	
	// Literals
	{DecIntegerLiteral}    { return newSymbol(sym.INT, new Integer(yytext())); }
	{BooleanLiteral}       { return newSymbol(sym.BOOL, Boolean.valueOf(yytext())); }  
	{PrintableCharLiteral} { return newSymbol(sym.CHAR, new Character(yytext().charAt(1))); }
	
	// Identifiers
	{Identifier}           { return newSymbol(sym.IDENT, yytext()); }
	
	// Lexical error (all text editors use 1-based indexing for lines, so yyline is incremented by 1)
	[^]                    { lexicalErrorMJLogger.log(yytext(), yyline + 1, yycolumn + 1); return newSymbol(sym.ERROR); }
}
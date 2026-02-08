plugins {
    java
    application
}

group = "dev.askov.mjcompiler"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass.set("dev.askov.mjcompiler.MJCompiler")
}

repositories {
    mavenCentral()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    testImplementation("junit:junit:4.13.1")

    // Using flatDir dependencies (lookup by name only)
    implementation(":JFlex")
    implementation(":cup_v10k")
    implementation(":log4j-1.2.17")
    implementation(":mj-runtime-1.1")
    implementation(":symboltable")
}

tasks.test {
    testLogging {
        outputs.upToDateWhen { false }
        showStandardStreams = true
    }
}

val jflexDir = layout.buildDirectory.dir("generated/sources/jflex/java/main").get()
val cupDir = layout.buildDirectory.dir("generated/sources/cup/java/main").get()

sourceSets.main {
    java.srcDirs(jflexDir, cupDir)
}

tasks.register<JavaExec>("lexer") {
    group = "generation"
    mainClass.set("JFlex.Main")
    classpath = sourceSets.main.get().compileClasspath

    val outputFile = jflexDir.file("dev/askov/mjcompiler/MJLexer.java")

    inputs.file("src/main/resources/mjlexer.flex")
    outputs.file(outputFile)

    args("-d", outputFile.asFile.parent, "src/main/resources/mjlexer.flex")
}

tasks.register<JavaExec>("parser") {
    group = "generation"
    dependsOn("lexer")
    mainClass.set("java_cup.Main")
    classpath = sourceSets.main.get().compileClasspath.minus(files("libs/JFlex.jar"))

    val genSourceRoot = cupDir.asFile

    workingDir = genSourceRoot

    inputs.file("src/main/resources/mjparser.cup")
    outputs.dir(genSourceRoot)

    doFirst {
        file("${cupDir.asFile.path}/dev/askov/mjcompiler").mkdirs()
    }

    args(
        "-destdir", "dev/askov/mjcompiler",
        "-parser", "MJParser",
        "-ast", "dev.askov.mjcompiler.ast",
        "-buildtree",
        file("src/main/resources/mjparser.cup").absolutePath
    )
}

tasks.compileJava {
    dependsOn("lexer", "parser")
}

tasks.clean {
    delete(
        "src/main/java/dev/askov/mjcompiler/MJLexer.java",
        "src/main/java/dev/askov/mjcompiler/MJParser.java",
        "src/main/java/dev/askov/mjcompiler/sym.java",
        "src/main/java/dev/askov/mjcompiler/ast"
    )
}

tasks.register<JavaExec>("disassemble") {
    group = "verification"

    mainClass.set("rs.etf.pp1.mj.runtime.disasm")
    classpath = sourceSets.main.get().runtimeClasspath
    args("src/test/resources/simple_calculator.obj")
}
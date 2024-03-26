package ru.bmstu.oberoncompiler.antlr4.service;

import lombok.extern.slf4j.Slf4j;
import oberon.antlr4.OberonLexer;
import oberon.antlr4.OberonParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.bytedeco.llvm.LLVM.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.oberoncompiler.antlr4.impl.OberonVisitor;
import ru.bmstu.oberoncompiler.antlr4.impl.OberonWalker;
import ru.bmstu.oberoncompiler.antlr4.config.AppParams;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.bytedeco.llvm.global.LLVM.*;
import static ru.bmstu.oberoncompiler.antlr4.service.ShutdownListener.runCommand;

@Slf4j
@Service
public class OberonCompilerService {
    private OberonLexer lexer;
    private LLVMModuleRef module;

    @Autowired
    private AppParams appParams;

    @Autowired
    private OberonVisitor visitor;

    public void process(String filename) throws IOException, InterruptedException {
        log.info("FILE: {}", filename);
        log.info("Запись модуля в LLVM Assembly файл: {}", appParams.buildDir + appParams.moduleName);

        lexer = doLexicalAnalysis(filename);
        log.info("lexer = doLexicalAnalysis(filename);");

        OberonParser parser = doSynAnalysis();
        Object context = parser.module();
        ParseTree tree = (ParseTree) context;

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new OberonWalker(parser), tree);

        module = (LLVMModuleRef) visitor.visitModule((OberonParser.ModuleContext) context);

        // Вывод LLVM-IR на консоль
        LLVMDumpModule(module);

        // Запись модуля в LLVM Assembly файл
        LLVMPrintModuleToFile(module, appParams.buildDir + appParams.moduleName + ".ll", (ByteBuffer) null);

        log.info("Запись модуля в LLVM Assembly файл: {}", appParams.buildDir + appParams.moduleName);

        runCommand(String.format("clang -c -o %s %s",
            appParams.buildDir.toString() + appParams.moduleName.toString() + ".obj",
            appParams.buildDir.toString() + appParams.moduleName.toString() + ".ll"));


        freeResources();
    }


    private OberonLexer doLexicalAnalysis(String filename) throws IOException {
        log.info("Выполняется лексический анализ файла: {}", filename);
        try {
            log.info("CharStreams.fromFileName(filename): {}", CharStreams.fromFileName(filename));
            OberonLexer lexer = new OberonLexer(CharStreams.fromFileName(filename));
            log.info("Lexer created successfully");
            return lexer;
        } catch (IOException e) {
            log.error("Ошибка при чтении файла: {}", e.getMessage());
            throw e; // Пробрасываем исключение дальше
        }
    }



    private OberonParser doSynAnalysis() {
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new OberonParser(tokens);
    }

    private void freeResources() {
        LLVMDisposeModule(module);
    }
}

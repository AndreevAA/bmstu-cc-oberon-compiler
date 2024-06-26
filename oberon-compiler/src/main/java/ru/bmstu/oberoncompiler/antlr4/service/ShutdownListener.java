package ru.bmstu.oberoncompiler.antlr4.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import ru.bmstu.oberoncompiler.antlr4.config.AppParams;

import java.io.IOException;


@Slf4j
@Component
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private AppParams appParams;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        createExeFromLL();
    }

    public static int runCommand(String cmd) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmd);
        return process.waitFor();
    }

    public void createExeFromLL() {
        int exitCode;
        String[] cmdArr = {
            String.format("clang -c -o %s %s",
                    appParams.buildDir + "mainCRTStartup.obj",
                    "/Users/andreevalexander/bmstu-2024/DesigningCompilersProject/oberon-compiler/src/main/java/ru/bmstu/oberoncompiler/antlr4/config/mainCRTStartup.c"),
            String.format("clang -c -o %s %s",
                    appParams.buildDir + appParams.moduleName + ".obj",
                    appParams.buildDir + appParams.moduleName + ".ll"),
            String.format("clang -o %s %s %s",
                    appParams.buildDir + appParams.moduleName + ".exe",
                    appParams.buildDir + appParams.moduleName + ".obj",
                    appParams.buildDir + "mainCRTStartup.obj")
        };

        for (String cmd : cmdArr) {
            try {
                exitCode = runCommand(cmd);

                if (exitCode == 0) {
                    log.info("{} was successfully completed.", cmd);
                } else {
                    log.warn("{} was failed.", cmd);
                }
            } catch (Exception e) {
                log.error("ERROR: {}", e.getMessage());
            }
        }
    }



}





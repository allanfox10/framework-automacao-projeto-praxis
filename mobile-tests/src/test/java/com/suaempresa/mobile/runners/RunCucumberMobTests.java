package com.suaempresa.mobile.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.suaempresa.mobile.steps", "com.suaempresa.mobile.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/mobile-report.html",
                "json:target/cucumber-reports/mobile-report.json"
        },
        monochrome = true
)
public class RunCucumberMobTests {

    @AfterClass
    public static void abrirRelatorio() {
        try {
            File reportFile = new File("target/cucumber-reports/mobile-report.html");
            if (reportFile.exists() && Desktop.isDesktopSupported()) {
                System.out.println("-------------------------------------------------------");
                System.out.println("Abrindo relatório de testes no navegador...");
                Desktop.getDesktop().browse(reportFile.toURI());
                System.out.println("-------------------------------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Não foi possível abrir o relatório automaticamente: " + e.getMessage());
        }
    }
}
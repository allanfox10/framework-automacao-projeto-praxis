package com.suaempresa.frontend.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "com.suaempresa.frontend.steps",

        // --- INÍCIO DA ATUALIZAÇÃO ---
        // Adicionado para espelhar a configuração do seu Runner de API
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui-report.html",
                "json:target/cucumber-reports/ui-report.json" // Essencial para o Cluecumber
        },
        monochrome = true
        // --- FIM DA ATUALIZAÇÃO ---
)
public class RunCucumberUiTests {
}
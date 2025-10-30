package com.suaempresa.backend.runners; // Certifique-se de que este é o pacote correto

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api", // 1. Caminho para suas features de API
        glue = "com.suaempresa.backend.steps",      // 2. Pacote dos seus Step Definitions de API
        tags = "@api and not @wip",                   // 3. (Opcional) Filtra quais cenários rodar
        plugin = {
                "pretty",                                 // 4. Formato do log no console
                "html:target/cucumber-reports/api-report.html", // 5. Relatório HTML
                "json:target/cucumber-reports/api-report.json"  // 6. Relatório JSON
        },
        monochrome = true // Melhora a legibilidade no console
)
public class RunCucumberApiTests {
    // A classe fica vazia.
    // As anotações acima fazem todo o trabalho.
}
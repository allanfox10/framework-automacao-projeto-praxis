package com.suaempresa.frontend.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "com.suaempresa.frontend.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui-report.html",
                "json:target/cucumber-reports/ui-report.json"
        },
        monochrome = true
)
public class RunCucumberUiTests {
}
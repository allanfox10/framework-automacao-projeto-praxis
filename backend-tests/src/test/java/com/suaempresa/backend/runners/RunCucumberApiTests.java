package com.suaempresa.backend.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = "com.suaempresa.backend.steps",
        tags = "@api and not @wip",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api-report.html",
                "json:target/cucumber-reports/api-report.json"
        },
        monochrome = true
)
public class RunCucumberApiTests {
}
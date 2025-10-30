package com.suaempresa.frontend.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = "com.suaempresa.frontend.steps"
        // A linha 'plugin' DEVE ser removida ou comentada
)
public class RunCucumberUiTests {
}

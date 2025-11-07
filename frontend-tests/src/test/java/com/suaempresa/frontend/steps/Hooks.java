package com.suaempresa.frontend.steps;

import com.suaempresa.core.web.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Permite que o Cucumber injete esta instância do WebDriver
     * nas classes de Steps (via Construtor).
     */
    public WebDriver getDriver() {
        return driver;
    }
}
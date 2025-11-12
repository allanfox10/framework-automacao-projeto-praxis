package com.suaempresa.mobile.pages;

import com.suaempresa.mobile.driver.MobileDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfiguracoesPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ConfiguracoesPage() {
        this.driver = MobileDriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String obterTituloDaTela(String titulo) {
        // Busca flexível: contém o texto informado
        By seletor = By.xpath("//*[contains(@text, '" + titulo + "')]");

        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(seletor));
        return elemento.getText();
    }
}
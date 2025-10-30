package com.suaempresa.frontend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Vvv CLASSE E CONSTRUTOR RENOMEADOS vvv
public class LoginAutPage {

    @FindBy(className = "title")
    private WebElement tituloPagina;

    public LoginAutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
// ^^^ CLASSE E CONSTRUTOR RENOMEADOS ^^^

    public String getTituloPagina() {
        return tituloPagina.getText();
    }
}
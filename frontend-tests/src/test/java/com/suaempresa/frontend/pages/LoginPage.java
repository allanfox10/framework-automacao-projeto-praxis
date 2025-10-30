package com.suaempresa.frontend.pages; // <-- MUDOU AQUI

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "user-name")
    private WebElement campoUsuario;

    @FindBy(id = "password")
    private WebElement campoSenha;

    @FindBy(id = "login-button")
    private WebElement botaoLogin;

    @FindBy(css = "[data-test='error']")
    private WebElement mensagemErro;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void preencherUsuario(String usuario) {
        campoUsuario.sendKeys(usuario);
    }

    public void preencherSenha(String senha) {
        campoSenha.sendKeys(senha);
    }

    public void clicarBotaoLogin() {
        botaoLogin.click();
    }

    public String getMensagemErro() {
        return mensagemErro.getText();
    }
}
package com.suaempresa.mobile.steps;

import com.suaempresa.mobile.driver.MobileDriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.pt.*;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginSteps {

    // --- ELEMENTOS ---
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement inputUsuario;

    @AndroidFindBy(accessibility = "test-Password")
    private WebElement inputSenha;

    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement btnLogin;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement tituloProdutos;

    // NOVO: Mapeamento da mensagem de erro (Procura o texto dentro do container de erro)
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView")
    private WebElement mensagemErro;

    public LoginSteps() {
        PageFactory.initElements(new AppiumFieldDecorator(MobileDriverManager.getDriver()), this);
    }

    // --- STEPS ---

    @Dado("que estou na tela de login do SwagLabs")
    public void estouNaTelaLogin() {
        // App já abre na tela de login
    }

    @Quando("realizo login com {string} e {string}")
    public void realizoLogin(String u, String s) {
        inputUsuario.clear(); // Boa prática: limpar antes de digitar
        inputUsuario.sendKeys(u);
        inputSenha.clear();
        inputSenha.sendKeys(s);
        btnLogin.click();
    }

    @Entao("devo visualizar a area de produtos")
    public void devoVisualizarProdutos() {
        Assert.assertTrue("Erro: A tela de produtos não apareceu!",
                tituloProdutos.isDisplayed());
    }

    // NOVO: Validação da mensagem de erro
    @Entao("devo ver a mensagem de erro {string}")
    public void devoVerMensagemErro(String msgEsperada) {
        try {
            Assert.assertTrue("O alerta de erro não apareceu!", mensagemErro.isDisplayed());
            Assert.assertEquals("O texto do erro está incorreto!", msgEsperada, mensagemErro.getText());
        } catch (Exception e) {
            Assert.fail("Falha ao validar mensagem de erro: " + e.getMessage());
        }
    }
}
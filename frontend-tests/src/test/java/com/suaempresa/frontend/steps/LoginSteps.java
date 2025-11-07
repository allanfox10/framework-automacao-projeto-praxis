package com.suaempresa.frontend.steps;

import com.suaempresa.core.utils.ConfigManager;
import com.suaempresa.frontend.pages.LoginAutPage;
import com.suaempresa.frontend.pages.LoginPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private LoginAutPage loginAutPage;
    private Wait<WebDriver> wait;

    public LoginSteps(Hooks hooks) {
        this.driver = hooks.getDriver();
        this.loginPage = new LoginPage(driver);
        this.loginAutPage = new LoginAutPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Dado("que eu acesse a página de login do SauceDemo")
    public void queEuAcesseAPaginaDeLoginDoSauceDemo() {
        String url = ConfigManager.getInstance().getBaseUrl();
        driver.get(url);
    }

    @Quando("eu preencher o campo de usuário com {string}")
    public void euPreencherOCampoDeUsuárioCom(String usuario) {
        loginPage.preencherUsuario(usuario);
    }

    @E("eu preencher o campo de senha com {string}")
    public void euPreencherOCampoDeSenhaCom(String senha) {
        loginPage.preencherSenha(senha);
    }

    @E("eu clicar no botão de login")
    public void euClicarNoBotãoDeLogin() {
        loginPage.clicarBotaoLogin();
    }

    @Então("eu devo ser redirecionado para a página de inventário")
    public void euDevoSerRedirecionadoParaAPáginaDeInventário() {
        String urlEsperada = "https://www.saucedemo.com/inventory.html";
        this.wait.until(ExpectedConditions.urlToBe(urlEsperada));

        String urlAtual = driver.getCurrentUrl();
        Assert.assertEquals("A URL de redirecionamento está incorreta.", urlEsperada, urlAtual);
    }

    @E("eu devo visualizar o título {string}")
    public void euDevoVisualizarOTítulo(String tituloEsperado) {
        String tituloAtual = loginAutPage.getTituloPagina();
        Assert.assertEquals("O título da página não é o esperado.", tituloEsperado, tituloAtual);
    }

    @Então("eu devo permanecer na página de login")
    public void euDevoPermanecerNaPáginaDeLogin() {
        String urlEsperada = "https://www.saucedemo.com/";
        String urlAtual = driver.getCurrentUrl();
        Assert.assertEquals("O usuário foi redirecionado incorretamente.", urlEsperada, urlAtual);
    }

    @E("eu devo visualizar a mensagem de erro {string}")
    public void euDevoVisualizarAMensagemDeErro(String mensagemEsperada) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String mensagemAtual = loginPage.getMensagemErro();
        Assert.assertEquals("A mensagem de erro não é a esperada.", mensagemEsperada, mensagemAtual);
    }
}
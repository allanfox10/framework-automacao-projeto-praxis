package com.suaempresa.frontend.steps;

// 1. Importamos o nosso DriverManager Singleton
import com.suaempresa.core.web.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver; // <-- Não é mais necessário
// import org.openqa.selenium.support.ui.WebDriverWait; // <-- Não é usado nesta classe

import java.time.Duration;

public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp() {
        // 2. Pedimos ao DriverManager para nos dar o driver
        // O seu DriverManager.java já está configurado para maximizar a janela
        driver = DriverManager.getDriver(); // <-- MUDANÇA PRINCIPAL

        // 3. Mantemos a configuração do 'implicit wait' aqui
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        // 4. Pedimos ao DriverManager para fechar o driver
        // Isso garante que a instância estática seja limpa (setada para null)
        DriverManager.quitDriver(); // <-- MUDANÇA PRINCIPAL
    }

    /**
     * Este método é usado pelo Cucumber (via Injeção de Dependência)
     * para passar o driver para as classes de Steps (como LoginSteps).
     * Agora ele está passando o driver correto (o Singleton).
     */
    public WebDriver getDriver() {
        return driver;
    }
}
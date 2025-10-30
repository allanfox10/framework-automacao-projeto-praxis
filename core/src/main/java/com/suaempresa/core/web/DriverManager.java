package com.suaempresa.core.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    // Nossa instância estática do WebDriver. Estática para ser a mesma em todo o projeto.
    private static WebDriver driver;

    /**
     * Construtor privado para evitar que alguém crie uma instância desta classe.
     * Ex: new DriverManager(); // Isso não será permitido.
     */
    private DriverManager() {
    }

    /**
     * Método principal que retorna a instância ÚNICA do WebDriver.
     * Se a instância não existir (for nula), ele a cria.
     * É o coração do padrão Singleton aplicado aqui.
     *
     * @return A instância do WebDriver.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            // No futuro, o tipo de browser pode vir de um arquivo de configuração (.properties)
            String browser = "chrome"; // "chrome" ou "firefox"

            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                // Case default sempre será o Chrome
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    // ChromeOptions são úteis para configurações avançadas (rodar em modo anônimo, headless, etc.)
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
                    driver = new ChromeDriver(options);
                    break;
            }
        }
        return driver;
    }

    /**
     * Método para "matar" a instância do driver.
     * Ele fecha TODAS as janelas do navegador e encerra o processo.
     * É crucial chamar este método no final dos testes para não deixar processos abertos.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // IMPORTANTE: Reseta a variável para que o próximo teste crie uma nova instância.
        }
    }
}
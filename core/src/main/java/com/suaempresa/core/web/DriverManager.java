package com.suaempresa.core.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static WebDriver driver;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private DriverManager() {
    }

    /**
     * Retorna a instância Singleton do WebDriver, criando-a se necessário.
     *
     * @return A instância do WebDriver.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            // TODO: Mover 'browser' para o ConfigManager
            String browser = "chrome";
            String executionMode = System.getProperty("EXECUTION_MODE", "local");

            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    if (executionMode.equals("headless")) {
                        System.out.println("Executando em modo HEADLESS");
                        options.addArguments("--headless=new");
                        options.addArguments("--no-sandbox"); // Necessário para rodar como root no Docker
                        options.addArguments("--disable-dev-shm-usage"); // Aumenta estabilidade no Docker
                        options.addArguments("--window-size=1920,1080");
                    } else {
                        System.out.println("Executando em modo LOCAL");
                        options.addArguments("--start-maximized");
                    }

                    driver = new ChromeDriver(options);
                    break;
            }
        }
        return driver;
    }

    /**
     * Encerra o processo do WebDriver e limpa a instância estática.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // IMPORTANTE: Reseta a variável para que o próximo teste crie uma nova instância.
        }
    }
}
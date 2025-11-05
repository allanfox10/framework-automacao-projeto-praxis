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

            // *** MUDANÇA PRINCIPAL ***
            // Lemos uma "Propriedade de Sistema" chamada EXECUTION_MODE.
            // Se ela não for definida, o padrão será "local" (rodar na sua máquina).
            // No Jenkins, vamos definir essa propriedade como "headless".
            String executionMode = System.getProperty("EXECUTION_MODE", "local");

            switch (browser) {
                case "firefox":
                    // TODO: Adicionar lógica headless para Firefox se necessário no futuro
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                // Case default sempre será o Chrome
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    // Verifica qual modo de execução usar
                    if (executionMode.equals("headless")) {
                        // --- MODO HEADLESS (Para rodar no Jenkins/Docker) ---
                        System.out.println("Executando em modo HEADLESS");
                        options.addArguments("--headless=new"); // O novo modo headless recomendado
                        options.addArguments("--no-sandbox"); // Necessário para rodar como root no Docker
                        options.addArguments("--disable-dev-shm-usage"); // Aumenta estabilidade no Docker
                        options.addArguments("--window-size=1920,1080"); // Define tamanho da "tela virtual"
                    } else {
                        // --- MODO LOCAL (Para rodar na sua máquina) ---
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
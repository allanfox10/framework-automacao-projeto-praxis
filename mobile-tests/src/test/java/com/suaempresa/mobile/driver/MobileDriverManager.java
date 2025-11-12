package com.suaempresa.mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Gerenciador de Driver LOCAL para o módulo 'mobile-tests'.
 * Este driver é 100% focado em Appium (Selenium 4) e não tem
 * dependência do módulo 'core' para evitar conflitos.
 */
public class MobileDriverManager {

    private static WebDriver driver;

    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private MobileDriverManager() {
    }

    /**
     * Retorna a instância Singleton do WebDriver, criando-a se necessário.
     *
     * @return A instância do AndroidDriver.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            System.out.println("Iniciando Driver MOBILE (Appium)... [Driver Local]");

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // Lê as capacidades das propriedades do sistema para flexibilidade
            capabilities.setCapability("platformName", System.getProperty("APPIUM_PLATFORM_NAME", "Android"));
            capabilities.setCapability("automationName", System.getProperty("APPIUM_AUTOMATION_NAME", "UiAutomator2"));
            capabilities.setCapability("deviceName", System.getProperty("APPIUM_DEVICE_NAME", "emulator-5554")); // Padrão

            // App a ser testado
            String appPath = System.getProperty("APPIUM_APP_PATH");
            if (appPath != null && !appPath.isEmpty()) {
                capabilities.setCapability("app", appPath);
            } else {
                // Se não passar um app, abre as Configurações como padrão
                System.out.println("Nenhum 'APPIUM_APP_PATH' fornecido, abrindo app por 'Package/Activity'...");
                capabilities.setCapability("appPackage", System.getProperty("APPIUM_APP_PACKAGE", "com.android.settings"));
                capabilities.setCapability("appActivity", System.getProperty("APPIUM_APP_ACTIVITY", ".Settings"));
            }

            try {
                // Conecta ao servidor Appium (deve estar rodando)
                URL appiumServerUrl = new URL(System.getProperty("APPIUM_SERVER_URL", "http://127.0.0.1:4723/"));

                driver = new AndroidDriver(appiumServerUrl, capabilities);

            } catch (MalformedURLException e) {
                throw new RuntimeException("Falha ao inicializar o driver: URL do Appium malformada", e);
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
            driver = null; // Reseta a variável
        }
    }
}
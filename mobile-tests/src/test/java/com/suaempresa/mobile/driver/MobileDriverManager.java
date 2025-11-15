package com.suaempresa.mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {

    private static AndroidDriver driver;
    // Agora usamos o Enum, não mais um boolean
    private static AppConfig appAtual = AppConfig.SWAG_LABS; // Padrão

    private MobileDriverManager() {}

    // O Hook vai chamar isso aqui
    public static void setAppConfig(AppConfig config) {
        appAtual = config;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            System.out.println("Iniciando Driver para: " + appAtual);

            try {
                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName("Android");
                options.setAutomationName("UiAutomator2");
                options.setDeviceName("Pixel 4 API 30");

                // LÓGICA CENTRALIZADA
                if (appAtual.isInstalaApk()) {
                    // --- Fluxo de Instalar APK ---
                    String userDir = System.getProperty("user.dir");
                    String relativePath = "/src/test/resources/apps/" + appAtual.getApkName();

                    if (!userDir.endsWith("mobile-tests")) {
                        relativePath = "/mobile-tests" + relativePath;
                    }

                    File app = new File(userDir + relativePath);
                    if (!app.exists()) {
                        throw new RuntimeException("APK não encontrado: " + app.getAbsolutePath());
                    }
                    options.setApp(app.getAbsolutePath());
                    options.setAppWaitActivity("*");

                } else {
                    // --- Fluxo de App Já Instalado / Nativo ---
                    options.setAppPackage(appAtual.getAppPackage());
                    options.setAppActivity(appAtual.getAppActivity());
                }

                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

            } catch (MalformedURLException e) {
                throw new RuntimeException("Erro na URL do Appium", e);
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
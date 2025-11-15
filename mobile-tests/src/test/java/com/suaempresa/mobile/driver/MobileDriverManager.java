package com.suaempresa.mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverManager {

    private static AndroidDriver driver;
    private static AppConfig appAtual = AppConfig.SWAG_LABS; // Padrão

    private MobileDriverManager() {}

    public static void setAppConfig(AppConfig config) {
        appAtual = config;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            System.out.println("🚀 Iniciando Driver para: " + appAtual);

            try {
                UiAutomator2Options options = new UiAutomator2Options();
                options.setPlatformName("Android");
                options.setAutomationName("UiAutomator2");
                options.setDeviceName("Pixel 4 API 30");

                // LÓGICA DE CAMINHO DO APK (Já estava boa, mantivemos a flexibilidade)
                if (appAtual.isInstalaApk()) {
                    String userDir = System.getProperty("user.dir");
                    // Usa File.separator para garantir compatibilidade Windows/Linux
                    String separator = File.separator;
                    String relativePath = separator + "src" + separator + "test" + separator + "resources" + separator + "apps" + separator + appAtual.getApkName();

                    // Se estiver rodando da raiz do projeto (Maven Parent), adiciona o nome do módulo
                    if (!userDir.endsWith("mobile-tests")) {
                        relativePath = separator + "mobile-tests" + relativePath;
                    }

                    File app = new File(userDir + relativePath);

                    // Log para ajudar no debug no Jenkins se der erro de caminho
                    System.out.println("📂 Procurando APK em: " + app.getAbsolutePath());

                    if (!app.exists()) {
                        throw new RuntimeException("❌ APK não encontrado: " + app.getAbsolutePath());
                    }
                    options.setApp(app.getAbsolutePath());
                    options.setAppWaitActivity("*");

                } else {
                    // Fluxo de App Já Instalado / Nativo
                    options.setAppPackage(appAtual.getAppPackage());
                    options.setAppActivity(appAtual.getAppActivity());
                }

                // --- AQUI ESTÁ A GRANDE MUDANÇA ---
                // 1. Tenta pegar a URL passada pelo Maven/Jenkins (-DAPPIUM_SERVER_URL=...)
                String appiumUrl = System.getProperty("APPIUM_SERVER_URL");

                // 2. Se não vier nada (rodando local na IDE), usa o padrão local
                if (appiumUrl == null || appiumUrl.isEmpty()) {
                    appiumUrl = "http://127.0.0.1:4723/";
                    System.out.println("⚠️ Variável APPIUM_SERVER_URL não definida. Usando padrão local: " + appiumUrl);
                } else {
                    System.out.println("🌐 Usando URL do Appium definida via System Property: " + appiumUrl);
                }

                driver = new AndroidDriver(new URL(appiumUrl), options);

            } catch (MalformedURLException e) {
                throw new RuntimeException("❌ Erro na URL do Appium", e);
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
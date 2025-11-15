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

                // --- AJUSTE NA LÓGICA DE CAMINHO DO APK ---
                if (appAtual.isInstalaApk()) {
                    String userDir = System.getProperty("user.dir");
                    // Caminho relativo padrão dentro do módulo
                    String localPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "apps" + File.separator + appAtual.getApkName();

                    // 1. Tenta montar o caminho direto (ideal para quando roda de dentro do módulo)
                    File app = new File(userDir, localPath);

                    // 2. Estratégia de Fallback (Contingência):
                    // Se o arquivo não existe ali, provavelmente estamos rodando da raiz do projeto (Jenkins/Maven Parent)
                    // Então tentamos adicionar o nome do módulo "mobile-tests" no caminho.
                    if (!app.exists()) {
                        app = new File(userDir + File.separator + "mobile-tests", localPath);
                    }

                    // Log para ajudar no debug no Jenkins
                    System.out.println("📂 Procurando APK em: " + app.getAbsolutePath());

                    // Validação Final
                    if (!app.exists()) {
                        throw new RuntimeException("❌ APK não encontrado no caminho: " + app.getAbsolutePath() +
                                "\n Verifique se o arquivo .apk foi commitado no Git e se o caminho está correto!");
                    }

                    options.setApp(app.getAbsolutePath());
                    options.setAppWaitActivity("*");

                } else {
                    // Fluxo de App Já Instalado / Nativo
                    options.setAppPackage(appAtual.getAppPackage());
                    options.setAppActivity(appAtual.getAppActivity());
                }

                // --- LÓGICA DA URL DO APPIUM ---
                String appiumUrl = System.getProperty("APPIUM_SERVER_URL");

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
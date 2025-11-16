package com.suaempresa.mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

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

                options.setNoReset(false);
                options.setNewCommandTimeout(Duration.ofSeconds(90));

                // 1. Defina AppPackage e AppActivity sempre
                options.setAppPackage(appAtual.getAppPackage());
                options.setAppActivity(appAtual.getAppActivity());

                if (appAtual.isInstalaApk()) {

                    String apkPath;
                    String ciApkPath = System.getProperty("CI_APK_PATH");

                    if (ciApkPath != null && !ciApkPath.isEmpty()) {
                        // --- CENÁRIO CRÍTICO DE CI (Jenkins Docker -> Appium Windows) ---
                        // Usa o caminho Windows fornecido como parâmetro Maven/Jenkins
                        apkPath = ciApkPath;
                        System.out.println("⭐ Modo CI Ativo. Usando caminho de APK fornecido: " + apkPath);
                    } else {
                        // --- CENÁRIO LOCAL (Windows/Linux Padrão) ---
                        String userDir = System.getProperty("user.dir");
                        String localPath = "mobile-tests" + File.separator +
                                "src" + File.separator +
                                "test" + File.separator +
                                "resources" + File.separator +
                                "apps" + File.separator +
                                appAtual.getApkName();

                        File appFile = new File(userDir, localPath);

                        // Lógica de fallback para quando o Maven é executado a partir do módulo
                        if (!appFile.exists() && new File(userDir).getName().equals("mobile-tests")) {
                            appFile = new File(userDir + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "apps" + File.separator + appAtual.getApkName());
                        }

                        if (!appFile.exists()) {
                            throw new RuntimeException("❌ APK não encontrado no caminho: " + appFile.getAbsolutePath());
                        }

                        apkPath = appFile.getAbsolutePath();
                        System.out.println("📂 Usando caminho ABSOLUTO local do APK: " + apkPath);
                    }

                    // Define o caminho para o Appium Server.
                    options.setApp(apkPath);

                    // Força o Appium a esperar pela Activity de login
                    options.setAppWaitActivity(appAtual.getAppActivity());
                }

                // --- LÓGICA DA URL DO APPIUM ---
                String appiumUrl = System.getProperty("APPIUM_SERVER_URL", "http://127.0.0.1:4723/");

                if (System.getProperty("APPIUM_SERVER_URL") == null || System.getProperty("APPIUM_SERVER_URL").isEmpty()) {
                    System.out.println("⚠️ Variável APPIUM_SERVER_URL não definida. Usando padrão local: " + appiumUrl);
                } else {
                    System.out.println("🌐 Usando URL do Appium definida via System Property: " + appiumUrl);
                }

                driver = new AndroidDriver(new URL(appiumUrl), options);

            } catch (MalformedURLException e) {
                throw new RuntimeException("❌ Erro na URL do Appium", e);
            } catch (Exception e) {
                // Captura o erro SessionNotCreatedException e relança com mensagem clara
                throw new RuntimeException("❓ Erro ao iniciar o driver Appium ou aplicar configurações: " + e.getMessage(), e);
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
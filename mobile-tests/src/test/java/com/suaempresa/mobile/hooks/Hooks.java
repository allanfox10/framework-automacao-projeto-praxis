package com.suaempresa.mobile.hooks;

import com.suaempresa.mobile.driver.AppConfig;
import com.suaempresa.mobile.driver.MobileDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before(value = "@mobile", order = 0)
    public void setupSwagLabs() {
        // Seleciona o perfil do SwagLabs
        MobileDriverManager.setAppConfig(AppConfig.SWAG_LABS);
    }

    @Before(value = "@AbrirAndroid", order = 0)
    public void setupAndroidSettings() {
        // Seleciona o perfil de Configurações
        MobileDriverManager.setAppConfig(AppConfig.ANDROID_SETTINGS);
    }

    // Futuro exemplo:
    // @Before("@FoodApp") -> MobileDriverManager.setAppConfig(AppConfig.IFOOD);

    @After
    public void finalizar() {
        MobileDriverManager.quitDriver();
    }
}
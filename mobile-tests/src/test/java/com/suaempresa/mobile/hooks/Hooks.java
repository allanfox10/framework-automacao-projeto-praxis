package com.suaempresa.mobile.hooks;

import com.suaempresa.mobile.driver.MobileDriverManager;
import io.cucumber.java.After;

public class Hooks {

    /**
     * Este Hook (@After) executa após cada cenário.
     * Ele chama o quitDriver() do nosso MobileDriverManager local.
     */
    @After
    public void tearDown() {
        MobileDriverManager.quitDriver();
    }
}
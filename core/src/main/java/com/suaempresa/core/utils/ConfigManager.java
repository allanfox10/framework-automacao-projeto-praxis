package com.suaempresa.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        properties = new Properties();
        String environment = System.getProperty("env");

        if (environment == null || environment.isEmpty()) {
            System.out.println("Variável 'env' não definida. Usando 'hml' (homologação) como padrão.");
            environment = "hml";
        }

        String configFilePath = "configs/" + environment + ".properties";
        System.out.println("Carregando configurações do ambiente: " + configFilePath);

        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFilePath)) {
            if (is == null) {
                throw new IOException("Arquivo de configuração não encontrado no classpath: " + configFilePath);
            }
            properties.load(is);
        } catch (IOException e) {
            System.err.println("ERRO: Não foi possível carregar o arquivo de configuração: " + configFilePath);
            e.printStackTrace();
            throw new RuntimeException("Não foi possível carregar o arquivo de configuração: " + configFilePath, e);
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Atenção: A propriedade '" + key + "' não foi encontrada no arquivo de configuração.");
        }
        return value;
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }
}
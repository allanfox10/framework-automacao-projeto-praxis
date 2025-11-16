package com.suaempresa.mobile.driver;

public enum AppConfig {

    // Adicionei os detalhes corretos para o APK de teste do Swag Labs
    SWAG_LABS("com.swaglabsmobileapp", "com.swaglabsmobileapp.MainActivity", "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk", true),

    // Configuração para abrir o App nativo de configurações (que estava funcionando)
    ANDROID_SETTINGS("com.android.settings", "com.android.settings.Settings", "", false);

    private final String appPackage;
    private final String appActivity;
    private final String apkName;
    private final boolean instalaApk;

    AppConfig(String appPackage, String appActivity, String apkName, boolean instalaApk) {
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        this.apkName = apkName;
        this.instalaApk = instalaApk;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public String getApkName() {
        return apkName;
    }

    public boolean isInstalaApk() {
        return instalaApk;
    }
}
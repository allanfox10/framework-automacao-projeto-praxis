package com.suaempresa.mobile.driver;

public enum AppConfig {

    // --- LISTA DE APPS DO PROJETO ---

    SWAG_LABS(true, "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk", null, null),

    ANDROID_SETTINGS(false, null, "com.android.settings", ".Settings"),
;
    // --- ESTRUTURA DO ENUM ---

    private final boolean instalaApk;
    private final String apkName;
    private final String appPackage;
    private final String appActivity;

    AppConfig(boolean instalaApk, String apkName, String appPackage, String appActivity) {
        this.instalaApk = instalaApk;
        this.apkName = apkName;
        this.appPackage = appPackage;
        this.appActivity = appActivity;
    }

    public boolean isInstalaApk() { return instalaApk; }
    public String getApkName() { return apkName; }
    public String getAppPackage() { return appPackage; }
    public String getAppActivity() { return appActivity; }
}
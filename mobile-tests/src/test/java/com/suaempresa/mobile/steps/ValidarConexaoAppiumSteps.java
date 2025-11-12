package com.suaempresa.mobile.steps;

import com.suaempresa.mobile.pages.ConfiguracoesPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

public class ValidarConexaoAppiumSteps {

    private ConfiguracoesPage configuracoesPage;

    // CORREÇÃO AQUI: Adicionamos \\ antes dos parenteses
    @Dado("que o driver mobile \\(local\\) foi iniciado")
    public void que_o_driver_mobile_local_foi_iniciado() {
        System.out.println("Setup inicial mobile...");
    }

    @Quando("eu estiver na tela principal de Configurações")
    public void eu_estiver_na_tela_principal_de_configuracoes() {
        this.configuracoesPage = new ConfiguracoesPage();
    }

    @Então("eu devo ver o título {string}")
    public void eu_devo_ver_o_título(String tituloEsperado) {
        String textoAtual = configuracoesPage.obterTituloDaTela(tituloEsperado);
        Assert.assertEquals("O título da tela não corresponde", tituloEsperado, textoAtual);
        System.out.println("Sucesso: Título '" + textoAtual + "' validado.");
    }
}
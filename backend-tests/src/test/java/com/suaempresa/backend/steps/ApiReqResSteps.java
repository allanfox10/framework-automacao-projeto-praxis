package com.suaempresa.backend.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List; // Removido se não for usar lista no GET
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiReqResSteps {

    private RequestSpecification request;
    private Response response;
    // Usaremos um Map<String, Object> para suportar Integer (userId)
    private Map<String, Object> requestData;

    @Before
    public void setup() {
        requestData = new HashMap<>();
    }

    @Dado("que a base URL da API é {string}")
    public void que_a_base_url_da_api_é(String baseUrl) {
        RestAssured.useRelaxedHTTPSValidation();
        request = given().baseUri(baseUrl);
    }

    // --- Passos do Cenário GET ---

    @Quando("eu fizer uma requisição GET para {string}")
    public void eu_fizer_uma_requisição_get_para(String endpoint) {
        request.log().all();
        response = request.get(endpoint);
        response.then().log().all();
    }

    @Entao("o status da resposta deve ser {int}")
    public void o_status_da_resposta_deve_ser(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    // Mantido para validar o ID do usuário no GET
    @Entao("o corpo da resposta deve conter a chave {string} com o valor {int}")
    public void o_corpo_da_resposta_deve_conter_a_chave_com_o_valor(String key, Integer value) {
        response.then().body(key, equalTo(value));
    }

    // Novo passo para validar chaves String (como "name" no GET)
    @Entao("o corpo da resposta deve conter a chave string {string}")
    public void o_corpo_da_resposta_deve_conter_a_chave_string(String key) {
        response.then().body(key, notNullValue());
        response.then().body(key, instanceOf(String.class));
    }

    // Removido ou comentado o passo antigo de lista, pois não usamos mais
    // @Entao("o corpo da resposta deve conter uma lista de usuários na chave {string}")
    // public void o_corpo_da_resposta_deve_conter_uma_lista_de_usuários_na_chave(String key) { ... }


    // --- Passos do Cenário POST ---

    // Alterado para refletir "post" e mapear novos campos
    @Dado("que eu tenho os dados de um novo post")
    public void que_eu_tenho_os_dados_de_um_novo_post(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        requestData.put("title", data.get("titulo"));
        requestData.put("body", data.get("corpo"));
        // Convertendo String para Integer
        requestData.put("userId", Integer.parseInt(data.get("usuarioId")));

        request.contentType(ContentType.JSON).body(requestData);
    }

    @Quando("eu fizer uma requisição POST para {string} com esses dados")
    public void eu_fizer_uma_requisição_post_para_com_esses_dados(String endpoint) {
        request.log().all();
        response = request.when().post(endpoint);
        response.then().log().all();
    }

    // Alterado para validar "titulo" e "corpo"
    @Entao("o corpo da resposta deve conter o {string} e {string} enviados")
    public void o_corpo_da_resposta_deve_conter_o_e_enviados(String gherkinKey1, String gherkinKey2) {
        // Mapeia Gherkin ("titulo") para API ("title")
        response.then().body("title", equalTo(requestData.get("title")));
        response.then().body("body", equalTo(requestData.get("body")));
    }

    // Mantido para validar o "id" gerado
    @Entao("o corpo da resposta deve conter uma chave {string} que nao seja nula")
    public void o_corpo_da_resposta_deve_conter_uma_chave_que_nao_seja_nula(String key) {
        response.then().body(key, notNullValue());
    }

    // --- Passos do Cenário PUT ---

    // Alterado para refletir "post" e mapear novos campos
    @Dado("que eu tenho os dados atualizados para o post de id {int}")
    public void que_eu_tenho_os_dados_atualizados_para_o_post_de_id(Integer id, DataTable dataTable) {
        // O ID não é usado aqui, mas sim no endpoint do 'Quando'
        Map<String, String> data = dataTable.asMaps().get(0);

        requestData.put("title", data.get("titulo"));
        requestData.put("body", data.get("corpo"));
        // userId pode ser omitido no PUT ou mantido, dependendo da API. Vamos omitir por simplicidade.

        request.contentType(ContentType.JSON).body(requestData);
    }

    @Quando("eu fizer uma requisição PUT para {string} com esses dados")
    public void eu_fizer_uma_requisição_put_para_com_esses_dados(String endpoint) {
        request.log().all();
        response = request.when().put(endpoint);
        response.then().log().all();
    }

    // Alterado para validar "titulo" e "corpo"
    @Entao("o corpo da resposta deve conter o {string} e {string} atualizados")
    public void o_corpo_da_resposta_deve_conter_o_e_atualizados(String gherkinKey1, String gherkinKey2) {
        response.then().body("title", equalTo(requestData.get("title")));
        response.then().body("body", equalTo(requestData.get("body")));
    }

    // --- Passos do Cenário DELETE ---

    @Quando("eu fizer uma requisição DELETE para {string}")
    public void eu_fizer_uma_requisição_delete_para(String endpoint) {
        request.log().all();
        response = request.when().delete(endpoint);
        response.then().log().all();
    }

    // Alterado para verificar um JSON vazio {}
    @Entao("o corpo da resposta deve ser um JSON vazio")
    public void o_corpo_da_resposta_deve_ser_um_json_vazio() {
        // JSONPlaceholder retorna {} no delete
        response.then().body(equalTo("{}"));
    }
}
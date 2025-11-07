package com.suaempresa.backend.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
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

    @Entao("o corpo da resposta deve conter a chave {string} com o valor {int}")
    public void o_corpo_da_resposta_deve_conter_a_chave_com_o_valor(String key, Integer value) {
        response.then().body(key, equalTo(value));
    }

    @Entao("o corpo da resposta deve conter a chave string {string}")
    public void o_corpo_da_resposta_deve_conter_a_chave_string(String key) {
        response.then().body(key, notNullValue());
        response.then().body(key, instanceOf(String.class));
    }

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

    @Entao("o corpo da resposta deve conter o {string} e {string} enviados")
    public void o_corpo_da_resposta_deve_conter_o_e_enviados(String gherkinKey1, String gherkinKey2) {
        // Mapeia Gherkin ("titulo") para API ("title")
        response.then().body("title", equalTo(requestData.get("title")));
        response.then().body("body", equalTo(requestData.get("body")));
    }

    @Entao("o corpo da resposta deve conter uma chave {string} que nao seja nula")
    public void o_corpo_da_resposta_deve_conter_uma_chave_que_nao_seja_nula(String key) {
        response.then().body(key, notNullValue());
    }

    @Dado("que eu tenho os dados atualizados para o post de id {int}")
    public void que_eu_tenho_os_dados_atualizados_para_o_post_de_id(Integer id, DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        requestData.put("title", data.get("titulo"));
        requestData.put("body", data.get("corpo"));

        request.contentType(ContentType.JSON).body(requestData);
    }

    @Quando("eu fizer uma requisição PUT para {string} com esses dados")
    public void eu_fizer_uma_requisição_put_para_com_esses_dados(String endpoint) {
        request.log().all();
        response = request.when().put(endpoint);
        response.then().log().all();
    }

    @Entao("o corpo da resposta deve conter o {string} e {string} atualizados")
    public void o_corpo_da_resposta_deve_conter_o_e_atualizados(String gherkinKey1, String gherkinKey2) {
        response.then().body("title", equalTo(requestData.get("title")));
        response.then().body("body", equalTo(requestData.get("body")));
    }

    @Quando("eu fizer uma requisição DELETE para {string}")
    public void eu_fizer_uma_requisição_delete_para(String endpoint) {
        request.log().all();
        response = request.when().delete(endpoint);
        response.then().log().all();
    }

    @Entao("o corpo da resposta deve ser um JSON vazio")
    public void o_corpo_da_resposta_deve_ser_um_json_vazio() {
        // JSONPlaceholder retorna {} no delete
        response.then().body(equalTo("{}"));
    }
}
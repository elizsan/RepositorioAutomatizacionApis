package com.nttdata.glue;

import com.nttdata.steps.OrderStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class OrderStepsDef {
    @Steps
    OrderStep order;

    @Given("la url {string}")
    public void laUrl(String url) {
        order.definirUrl(url);
    }

    @When("creo el order con id {string}, petId {string}, quantity {string}, shipDate {string}, status {string}, complete {string}")
    public void creoElOrderConIdPetIdQuantityShipDateStatusComplete(String id, String petId, String quantity, String shipDate, String status, String complete) {
    order.crearOrder(id,petId,quantity,shipDate,status,complete);
    }

    @Then("el código de respuesta es igual a {int}")
    public void elCódigoDeRespuestaEsIgualA(int statusCode) {
        order.validarCodigoRespuesta(statusCode);
    }

    @And("body tiene status {string}")
    public void bodyTieneStatus(String status) {
        order.validarBody(status);
    }

    @When("consulto el order con id {string}")
    public void consultoElOrderConId(String id) {
        order.consultarOrder(id);
    }

    @And("imprimir body")
    public void imprimirBody() {
        order.imprimirBody();
    }

    @And("body tiene status")
    public void bodyTieneStatus() {
        order.validarGetBody();
    }

    @Then("el código de respuesta es igual a {string}")
    public void elCódigoDeRespuestaEsIgualA(String statusCode) {
        int statusCode2 = Integer.parseInt(statusCode);
        order.validarCodigoRespuesta(statusCode2);
    }
}

package com.nttdata.steps;

import com.nttdata.model.Order;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.jruby.RubyProcess;

import com.google.gson.Gson;

import java.util.List;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderStep {
    private static String URL = null;
    Response response;
    int statusCodeGen = 200;

    public void definirUrl(String url) {
        URL = url;
    }

    public void crearOrder(String id, String petId, String quantity, String shipDate, String status, String complete) {
        int nuevoId = Integer.parseInt(id);
        int nuevoPetId = Integer.parseInt(petId);
        int nuevoQuantity = Integer.parseInt(quantity);

        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .body("{\n" +
                        "  \"id\": "+nuevoId+",\n" +
                        "  \"petId\": "+nuevoPetId+",\n" +
                        "  \"quantity\": "+nuevoQuantity+",\n" +
                        "  \"shipDate\": \""+shipDate+"\",\n" +
                        "  \"status\": \""+status+"\",\n" +
                        "  \"complete\": \""+complete+"\"\n" +
                        "}")
                .log().all()
                .post(URL+"v2/store/order")
                .then()
                .log().all()
        ;
    }

    public void validarCodigoRespuesta(int statusCode) {
        restAssuredThat(response -> response.statusCode(statusCode));
        System.out.println("Status code: "+SerenityRest.lastResponse().statusCode());
        statusCodeGen = SerenityRest.lastResponse().statusCode();
    }

    public void validarBody(String status) {
        restAssuredThat(response -> response.body("'status'", equalTo(status)));
        System.out.println("Status: " + SerenityRest.lastResponse().body().path("status").toString());
        System.out.println(SerenityRest.lastResponse().print());
    }

    public void consultarOrder(String id) {
        //int nuevoId = Integer.parseInt(id);
        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .log().all()
                .get(URL+"v2/store/order/"+id)
                .then()
                .log().all()
        ;
    }

    public void imprimirBody() {
        System.out.println(SerenityRest.lastResponse().print());

        if (statusCodeGen == 200){
            Gson gson = new Gson();
            Order order = gson.fromJson(SerenityRest.lastResponse().getBody().asString(), Order.class);
            System.out.println("id: " + order.getId() + " petId: " + order.getPetId() + " quantity: "+order.getQuantity() +" shipDate: " + order.getShipDate() +" status: " + order.getStatus() +" complete: " + order.getComplete());
        }else{
            System.out.println("Status no es 200, no hay objeto que imprimir");
        }

    }

    public void validarGetBody() {
        String getBody = SerenityRest.lastResponse().getBody().asString();
        if (getBody == null || getBody.trim().isEmpty()){
            System.out.println("Body esta vacio");
        }
        else{
            if(statusCodeGen == 400 || statusCodeGen == 404){
                System.out.println("Status is: "+statusCodeGen);
            }else{
                System.out.println("Status: " + SerenityRest.lastResponse().body().path("status").toString());
                System.out.println(SerenityRest.lastResponse().print());
            }

        }

    }
}

package org.exam;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class OrderSteps extends RestData{

    private final IngredientsSteps ingredientsSteps = new IngredientsSteps();
    private String firstIngredient;
    private String secondIngredient;

    @Step("Send POST request with authorization and body to create an order")
    public Response createOrder(String accessToken){
        Response response = ingredientsSteps.callTheListOfIngredients();
        firstIngredient = response
                .body()
                .path("data[0]._id");
        secondIngredient = response
                .body()
                .path("data[1]._id");
        String json = "{\"ingredients\": [\"" + firstIngredient + "\", \"" + secondIngredient + "\"]}";
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(json)
                .when()
                .post(POST_CREATE_ORDER_ENDPOINT);
    }

    @Step("Send POST request without authorization and but with body to create an order")
    public Response createOrderWithoutToken(){
        Response response = ingredientsSteps.callTheListOfIngredients();
        firstIngredient = response
                .body()
                .path("data[0]._id");
        secondIngredient = response
                .body()
                .path("data[1]._id");
        String json = "{\"ingredients\": [\"" + firstIngredient + "\", \"" + secondIngredient + "\"]}";
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(POST_CREATE_ORDER_ENDPOINT);
    }

    @Step("Send POST request with authorization and but without ingredients to create an order")
    public Response createOrderWithoutIngredients(String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .post(POST_CREATE_ORDER_ENDPOINT);
    }

    @Step("Send POST request with authorization but with invalid hash of ingredients in the body to create an order")
    public Response createOrderWithInvalidHash(String accessToken){
        String json = "{\"ingredients\": [\"" + "\", \"" + "\"]}";
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(json)
                .when()
                .post(POST_CREATE_ORDER_ENDPOINT);
    }

    @Step("Send GET request to retrieve the list of orders made by exact user")
    public Response retrieveUserOrder(String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get(GET_RETRIEVE_USER_ORDER_ENDPOINT);
    }

    @Step("Send GET request to retrieve the list of orders without sending authorization access token in header")
    public Response retrieveUserOrderWithoutAuthorization(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .get(GET_RETRIEVE_USER_ORDER_ENDPOINT);
    }
}
package org.exam;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class IngredientsSteps extends RestData{

    @Step("Send GET request to /api/ingredients to retrieve the list of all the ingredients")
    public Response callTheListOfIngredients(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .get(GET_INGREDIENTS_LIST_ENDPOINT);
    }
}
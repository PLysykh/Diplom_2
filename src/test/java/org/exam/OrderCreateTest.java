package org.exam;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static constants.Fields.*;
import static constants.Texts.*;
import static org.hamcrest.Matchers.*;

public class OrderCreateTest extends BaseTest{

    UserCreateSteps userCreateSteps = new UserCreateSteps();
    UserAuthLoginSteps userAuthLoginSteps = new UserAuthLoginSteps();
    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Create order with all parameters and token")
    @Description("Possibility of creation an order sending list of ingredients and token")
    public void createOrderWithAuthorizationAndIngredientsTest(){
        userCreateSteps.createUser();
        Response responseFromLoginUser = userAuthLoginSteps.loginUser();
        String accessToken = responseFromLoginUser
                .body()
                .path("accessToken")
                .toString();

        Response response = orderSteps.createOrder(accessToken);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(SUCCESS_FIELD, equalTo(TRUE_ANSWER))
                .body(ORDER_OBJECT, notNullValue())
                .body(STATUS_FIELD, equalTo("done"));
    }


    @Test
    @DisplayName("Create an order without authorization")
    @Description("Possibility of creation an order without authorization")
    public void createOrderWithOutAuthorizationTest(){
        Response response = orderSteps.createOrderWithoutToken();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(SUCCESS_FIELD, equalTo(TRUE_ANSWER))
                .body("order._id", nullValue())
                .body("order.owner", nullValue())
                .body(STATUS_FIELD, nullValue());
    }

    @Test
    @DisplayName("Create an order without ingredients list")
    @Description("Impossibility of creation an order without ingredients list")
    public void createOrderWithOutIngredientsTest(){
        userCreateSteps.createUser();
        Response responseFromLoginUser = userAuthLoginSteps.loginUser();
        String accessToken = responseFromLoginUser
                .body()
                .path("accessToken")
                .toString();

        Response response = orderSteps.createOrderWithoutIngredients(accessToken);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(SUCCESS_FIELD, equalTo(FALSE_ANSWER))
                .body(MESSAGE_FIELD, equalTo(INGREDIENTS_ABSENCE));
    }

    @Test
    @DisplayName("Create an order with invalid has of ingredients")
    @Description("Impossibility of creation an order with invalid hash of ingredients")
    public void createOrderWithInvalidHashTest(){
        userCreateSteps.createUser();
        Response responseFromLoginUser = userAuthLoginSteps.loginUser();
        String accessToken = responseFromLoginUser
                .body()
                .path("accessToken")
                .toString();

        Response response = orderSteps.createOrderWithInvalidHash(accessToken);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}
package org.exam;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static constants.Fields.*;
import static constants.Texts.*;

public class RetrieveUserOrdersTest extends BaseTest{
    UserCreateSteps userCreateSteps = new UserCreateSteps();
    UserAuthLoginSteps userAuthLoginSteps = new UserAuthLoginSteps();
    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Retrieving the list of orders")
    @Description("Possibility of retrieving the list of orders with authorization")
    public void retrieveUserOrdersWithAuthorizationTest(){
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

        String orderIdCreated = response
                .body()
                .path("order._id")
                .toString();

        Response responseFromExactUserOrder = orderSteps.retrieveUserOrder(accessToken);
        responseFromExactUserOrder
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(SUCCESS_FIELD, equalTo(TRUE_ANSWER))
                .body("orders", notNullValue());

        String orderIdReceived = responseFromExactUserOrder
                .body()
                .path("orders._id")
                .toString()
                .replaceAll("\\[|\\]", "");

        Assert.assertEquals(orderIdCreated, orderIdReceived);
    }

    @Test
    @DisplayName("Retrieving user's list of orders without authorization")
    @Description("Impossibility of retrieving the list of orders without authorization")
    public void retrieveUserOrdersWithoutAuthorizationTest(){
        userCreateSteps.createUser();

        Response responseFromExactUserOrder = orderSteps.retrieveUserOrderWithoutAuthorization();
        responseFromExactUserOrder
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(MESSAGE_FIELD, equalTo(NOT_AUTHORIZED));
    }
}
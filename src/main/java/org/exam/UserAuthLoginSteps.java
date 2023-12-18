package org.exam;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class UserAuthLoginSteps extends RestData{

    private final UserMethods userMethods = new UserMethods();

    @Step("Send POST request to /api/auth/login to login")
    public Response loginUser(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.loginUser())
                .when()
                .post(POST_LOGIN_USER_ENDPOINT);
    }

    @Step("Send POST request to /api/auth/login with wrong email")
    public Response loginWithWrongEmail(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.loginUserWithWrongEmail())
                .when()
                .post(POST_LOGIN_USER_ENDPOINT);
    }


    @Step("Send POST request to /api/auth/login with wrong password")
    public Response loginWithWrongPassword(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.loginUserWithWrongPassword())
                .when()
                .post(POST_LOGIN_USER_ENDPOINT);
    }

    @Step("Send POST request to /api/auth/logout to logout user")
    public Response logoutUser(String refreshToken){
        String json = "{\"token\": \"" + refreshToken + "\"}";
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(POST_LOGOUT_USER_ENDPOINT);
    }
}
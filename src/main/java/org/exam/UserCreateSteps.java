package org.exam;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class UserCreateSteps extends RestData{

    private final UserMethods userMethods = new UserMethods();

    @Step("Send POST request to /api/auth/register to register new user")
    public Response createUser(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.getUser())
                .when()
                .post(POST_CREATE_USER_ENDPOINT);
    }

    @Step("Send POST request to /api/auth/register to register new user without sending email")
    public Response sendPostWithoutEmail(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.getUserWithoutEmail())
                .when()
                .post(POST_CREATE_USER_ENDPOINT);
    }

    @Step("Send POST request to /api/auth/register to register new user without sending password")
    public Response sendPostWithoutPassword(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.getUserWithoutPassword())
                .when()
                .post(POST_CREATE_USER_ENDPOINT);
    }

    @Step("Send POST request to /api/auth/register to register new user without sending name")
    public Response sendPostWithoutName(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(userMethods.getUserWithoutName())
                .when()
                .post(POST_CREATE_USER_ENDPOINT);
    }
}
package org.exam;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URLs.*;
import static io.restassured.RestAssured.given;

public class UserUpdateDeleteSteps extends RestData {
    @Step("Send PATCH request to /api/auth/user for updating user's personal information")
    public Response updateUser(String email, String password, String name, String accessToken){
        String json = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\"}";
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(json)
                .when()
                .patch(PATCH_UPDATE_USER_ENDPOINT);
    }

    @Step("Send DELETE request to /api/auth/user to delete user")
    public Response deleteUser(String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER_ENDPOINT);
    }
}
package org.exam;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;
import static constants.Fields.*;
import static constants.Texts.*;

@RunWith(Parameterized.class)
public class UserUpdateTest{

    UserCreateSteps userCreateSteps = new UserCreateSteps();
    UserAuthLoginSteps userAuthLoginSteps = new UserAuthLoginSteps();
    UserUpdateDeleteSteps userUpdateDeleteSteps = new UserUpdateDeleteSteps();

    private final String email;
    private final String password;
    private final String name;

    public UserUpdateTest(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters()
    public static Object[][] getData(){
        return new Object[][]{
                {"er@yandex.ru", "QAtest123", "Robert"},
                {"oppenheimer@yandex.ru", "QAte", "Robert"},
                {"oppenheimer@yandex.ru", "QAtest123", "User"}
        };
    }

    @Test
    @DisplayName("Updating logged in user personal data")
    @Description("Possibility to update logged in user personal data")
    public void updateLoggedInUser(){

        userCreateSteps.createUser();
        Response responseFromLoginUser = userAuthLoginSteps.loginUser();

        String accessToken = responseFromLoginUser
                .body()
                .path("accessToken")
                .toString();

        Response response = userUpdateDeleteSteps.updateUser(email, password, name, accessToken);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(SUCCESS_FIELD, equalTo(true));

        Response responseFromDeletion = userUpdateDeleteSteps.deleteUser(accessToken);
        responseFromDeletion
                .then()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(USER_REMOVED));
    }

    @Test
    @DisplayName("Updating logged out user personal data")
    @Description("Possibility to update logged out user personal data")
    public void updateLoggedOutUser(){

        userCreateSteps.createUser();
        Response responseFromLoginUser = userAuthLoginSteps.loginUser();

        String accessToken = responseFromLoginUser
                .body()
                .path("accessToken")
                .toString();


        String refreshToken = responseFromLoginUser
                .body()
                .path("refreshToken")
                .toString();

        Response responseFromLoggoutUser = userAuthLoginSteps.logoutUser(refreshToken);

        responseFromLoggoutUser
                .then()
                .assertThat()

                .body(MESSAGE_FIELD, equalTo(LOGOUT));

        Response responseFromDeletion = userUpdateDeleteSteps.deleteUser(accessToken);
        responseFromDeletion
                .then()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(USER_REMOVED));

        Response response = userUpdateDeleteSteps.updateUser(email, password, name, accessToken);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(USER_NOT_FOUND));
    }
}
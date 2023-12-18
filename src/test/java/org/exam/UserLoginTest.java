package org.exam;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

import static constants.Fields.*;
import static constants.Texts.*;

public class UserLoginTest extends BaseTest{

    private final UserCreateSteps userCreateSteps = new UserCreateSteps();
    private final UserAuthLoginSteps userAuthLoginSteps = new UserAuthLoginSteps();

    @Test
    @DisplayName("User's login")
    @Description("Possibility of user's logging in")
    public void loginUserTest(){
        userCreateSteps.createUser();
        Response response = userAuthLoginSteps.loginUser();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(SUCCESS_FIELD, equalTo(TRUE_ANSWER));
    }

    @Test
    @DisplayName("User's login with wrong login") // login aka email
    @Description("Impossibility of user's logging in with wrong login")
    public void loginUserWithWrongLoginTest(){
        userCreateSteps.createUser();
        Response response = userAuthLoginSteps.loginWithWrongEmail();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(INCORRECT_DATA));
    }

    @Test
    @DisplayName("User's login with wrong password")
    @Description("Impossibility of user's logging in with wrong password")
    public void loginUserWithWrongPasswordTest(){
        userCreateSteps.createUser();
        Response response = userAuthLoginSteps.loginWithWrongPassword();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(INCORRECT_DATA));
    }
}
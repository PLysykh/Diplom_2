package org.exam;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

import static constants.Fields.*;
import static constants.Texts.*;

public class UserCreateTest extends BaseTest{
    private final UserCreateSteps userCreateSteps = new UserCreateSteps();

    @Test
    @DisplayName("Create a user")
    @Description("Possibility of user creation")
    public void createUserTest(){
        Response response = userCreateSteps.createUser();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(SUCCESS_FIELD, equalTo(TRUE_ANSWER));
    }

    @Test
    @DisplayName("Create a user using the same data")
    @Description("Impossibility of creating a user with the same data")
    public void createAlreadyExistingUserTest(){
        userCreateSteps.createUser();
        Response response = userCreateSteps.createUser();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(USER_EXISTS));
    }

    @Test
    @DisplayName("Create a user without sending email")
    @Description("Impossibility of user creation without email")
    public void createUserWithoutEmailTest(){
        Response response = userCreateSteps.sendPostWithoutEmail();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MISSING));
    }

    @Test
    @DisplayName("Create a user without sending password")
    @Description("Impossibility of user creation without password")
    public void createUserWithoutPasswordTest(){
        Response response = userCreateSteps.sendPostWithoutPassword();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MISSING));
    }

    @Test
    @DisplayName("Create a user without sending name")
    @Description("Impossibility of user creation without name")
    public void createUserWithoutNameTest(){
        Response response = userCreateSteps.sendPostWithoutName();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(REQUIRED_FIELD_MISSING));
    }
}
package org.exam;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;

import static constants.Fields.*;
import static constants.Texts.*;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    private final UserAuthLoginSteps userAuthLoginSteps = new UserAuthLoginSteps();
    private final UserUpdateDeleteSteps userUpdateDeleteSteps = new UserUpdateDeleteSteps();

    @After
    public void deleteUser() {
        if (userAuthLoginSteps.loginUser().body().path("success").equals(true)) {
            String accessToken = userAuthLoginSteps.loginUser().body().path("accessToken").toString();
            Response response = userUpdateDeleteSteps.deleteUser(accessToken);
            response
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_ACCEPTED)
                    .body(MESSAGE_FIELD, equalTo(USER_REMOVED));
        }
    }
}
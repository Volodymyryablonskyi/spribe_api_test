package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.StatusCode;
import org.testng.annotations.DataProvider;

import static com.spribe.yablonskyi.http.response.StatusCode.*;

public class AccessDataProvider {

    @DataProvider(name = "createAccess")
    public Object[][] createAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.SUPERVISOR, Role.ADMIN, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.SUPERVISOR, Role.SUPERVISOR, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.ADMIN, Role.USER, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.ADMIN, Role.ADMIN, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.ADMIN, Role.SUPERVISOR, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.USER, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.ADMIN, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.SUPERVISOR, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
        };
    }

    @DataProvider(name = "updateAccess")
    public Object[][] updateAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.SUPERVISOR, Role.ADMIN, false, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.SUPERVISOR, Role.SUPERVISOR, true, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.ADMIN, Role.USER, false, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.ADMIN, Role.ADMIN, true, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.ADMIN, Role.ADMIN, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.ADMIN, Role.SUPERVISOR, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.USER, true, new StatusCode[]{_200_OK, _204_NO_CONTENT}},
                {Role.USER, Role.USER, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.ADMIN, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.SUPERVISOR, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
        };
    }

    @DataProvider(name = "deleteAccess")
    public Object[][] deleteAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, new StatusCode[]{_204_NO_CONTENT, _200_OK}},
                {Role.SUPERVISOR, Role.ADMIN, false, new StatusCode[]{_204_NO_CONTENT, _200_OK}},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.SUPERVISOR, Role.SUPERVISOR, true, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.ADMIN, Role.USER, false, new StatusCode[]{_204_NO_CONTENT, _200_OK}},
                {Role.ADMIN, Role.ADMIN, true, new StatusCode[]{_204_NO_CONTENT, _200_OK}},
                {Role.ADMIN, Role.ADMIN, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.ADMIN, Role.SUPERVISOR, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.USER, true, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.USER, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.ADMIN, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
                {Role.USER, Role.SUPERVISOR, false, new StatusCode[]{_403_FORBIDDEN, _400_BAD_REQUEST}},
        };
    }

    @DataProvider(name = "createRoleValidation")
    public Object[][] createRoleValidation() {
        return new Object[][]{
                {Role.ADMIN, Role.SUPERVISOR, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.USER, Role.SUPERVISOR, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.SUPERVISOR, Role.SUPERVISOR, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}}
        };
    }

}

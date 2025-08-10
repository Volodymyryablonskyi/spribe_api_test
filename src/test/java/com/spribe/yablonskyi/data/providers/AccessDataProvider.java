package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.StatusCode;
import org.testng.annotations.DataProvider;

public class AccessDataProvider {

    @DataProvider(name = "createAccess", parallel = true)
    public Object[][] createAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.ADMIN, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.USER, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, StatusCode._200_OK},
                {Role.ADMIN, Role.USER, StatusCode._200_OK},
                {Role.USER, Role.ADMIN, StatusCode._403_FORBIDDEN},
                {Role.USER, Role.USER, StatusCode._403_FORBIDDEN}
        };
    }

    @DataProvider(name = "createRoleValidation", parallel = true)
    public Object[][] createRoleValidation() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.SUPERVISOR, StatusCode._400_BAD_REQUEST},
                {Role.ADMIN, Role.SUPERVISOR, StatusCode._400_BAD_REQUEST},
                {Role.USER, Role.SUPERVISOR, StatusCode._403_FORBIDDEN}
        };
    }

    @DataProvider(name = "updateAccess", parallel = true)
    public Object[][] updateAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.ADMIN, false, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, StatusCode._200_OK},
                {Role.ADMIN, Role.USER, false, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, true, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, false, StatusCode._403_FORBIDDEN},
                {Role.USER, Role.USER, true, StatusCode._200_OK},
                {Role.USER, Role.USER, false, StatusCode._403_FORBIDDEN},
        };
    }

    @DataProvider(name = "deleteAccess", parallel = true)
    public Object[][] deleteAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.ADMIN, false, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, StatusCode._403_FORBIDDEN},
                {Role.ADMIN, Role.USER, false, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, true, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, false, StatusCode._403_FORBIDDEN},
                {Role.USER, Role.USER, true, StatusCode._403_FORBIDDEN},
        };
    }

}
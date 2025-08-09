package com.spribe.yablonskyi.data;

import com.spribe.yablonskyi.http.response.StatusCode;
import org.testng.annotations.DataProvider;

public class AccessDataProvider {

    // ---------- CREATE ----------
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

    // ---------- UPDATE ----------
    @DataProvider(name = "updateAccess", parallel = true)
    public Object[][] updateAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.ADMIN, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.SUPERVISOR, StatusCode._200_OK},
                {Role.ADMIN, Role.USER, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, StatusCode._403_FORBIDDEN},
                {Role.USER, Role.USER, StatusCode._200_OK},
                {Role.USER, Role.USER, StatusCode._403_FORBIDDEN}
        };
    }

    // ---------- DELETE ----------
    @DataProvider(name = "deleteAccess", parallel = true)
    public Object[][] deleteAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.ADMIN, StatusCode._200_OK},
                {Role.SUPERVISOR, Role.SUPERVISOR, StatusCode._403_FORBIDDEN},
                {Role.ADMIN, Role.USER, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, StatusCode._200_OK},
                {Role.ADMIN, Role.ADMIN, StatusCode._403_FORBIDDEN},
                {Role.USER, Role.USER, StatusCode._403_FORBIDDEN}
        };
    }

    // ---------- GET ----------
    @DataProvider(name = "getAccess", parallel = true)
    public Object[][] getAccess() {
        return new Object[][]{
                {Role.USER, StatusCode._200_OK},
                {Role.ADMIN, StatusCode._200_OK},
                {Role.ADMIN, StatusCode._403_FORBIDDEN},
                {Role.SUPERVISOR, StatusCode._200_OK}
        };
    }

}
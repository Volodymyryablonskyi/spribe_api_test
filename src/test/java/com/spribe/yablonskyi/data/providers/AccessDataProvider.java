package com.spribe.yablonskyi.data.providers;

import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.StatusCode;
import org.testng.annotations.DataProvider;

public class AccessDataProvider {

    @DataProvider(name = "createAccess", parallel = true)
    public Object[][] createAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.ADMIN, new StatusCode[]{StatusCode._200_OK}},
                {Role.SUPERVISOR, Role.USER, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.ADMIN, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.USER, new StatusCode[]{StatusCode._200_OK}},
                {Role.USER, Role.ADMIN, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.USER, Role.USER, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}}
        };
    }

    @DataProvider(name = "createRoleValidation", parallel = true)
    public Object[][] createRoleValidation() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.SUPERVISOR, new StatusCode[]{StatusCode._400_BAD_REQUEST, StatusCode._403_FORBIDDEN}},
                {Role.ADMIN, Role.SUPERVISOR, new StatusCode[]{StatusCode._400_BAD_REQUEST, StatusCode._403_FORBIDDEN}},
                {Role.USER, Role.SUPERVISOR, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}}
        };
    }

    @DataProvider(name = "updateAccess", parallel = true)
    public Object[][] updateAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.SUPERVISOR, Role.ADMIN, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.USER, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.ADMIN, true, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.ADMIN, false, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.USER, Role.USER, true, new StatusCode[]{StatusCode._200_OK}},
                {Role.USER, Role.USER, false, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
        };
    }

    @DataProvider(name = "deleteAccess", parallel = true)
    public Object[][] deleteAccess() {
        return new Object[][]{
                {Role.SUPERVISOR, Role.USER, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.SUPERVISOR, Role.ADMIN, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.SUPERVISOR, Role.SUPERVISOR, false, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.ADMIN, Role.USER, false, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.ADMIN, true, new StatusCode[]{StatusCode._200_OK}},
                {Role.ADMIN, Role.ADMIN, false, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
                {Role.USER, Role.USER, true, new StatusCode[]{StatusCode._403_FORBIDDEN, StatusCode._400_BAD_REQUEST}},
        };
    }

}
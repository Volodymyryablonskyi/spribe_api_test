package com.spribe.yablonskyi.pojo;

import java.util.Objects;

public class CreatePlayerRequestPojo {

    private String age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    public String getAge() {
        return age;
    }

    public CreatePlayerRequestPojo setAge(String age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public CreatePlayerRequestPojo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public CreatePlayerRequestPojo setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreatePlayerRequestPojo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public CreatePlayerRequestPojo setRole(String role) {
        this.role = role;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public CreatePlayerRequestPojo setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    @Override
    public String toString() {
        return "Player {" +
                "age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePlayerRequestPojo that = (CreatePlayerRequestPojo) o;
        return Objects.equals(age, that.age) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role) &&
                Objects.equals(screenName, that.screenName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, gender, login, password, role, screenName);
    }

}
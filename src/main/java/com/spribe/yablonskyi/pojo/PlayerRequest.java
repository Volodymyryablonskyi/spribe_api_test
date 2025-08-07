package com.spribe.yablonskyi.pojo;

public class PlayerRequest {

    private String age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

    public String getAge() {
        return age;
    }

    public PlayerRequest setAge(String age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PlayerRequest setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public PlayerRequest setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PlayerRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public PlayerRequest setRole(String role) {
        this.role = role;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public PlayerRequest setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

}
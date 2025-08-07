package com.spribe.yablonskyi.pojo;

public class PlayerResponsePojo {

    private int age;
    private String gender;
    private long id;
    private String login;
    private String password;
    private String role;
    private String screenName;

    public int getAge() {
        return age;
    }

    public PlayerResponsePojo setAge(int age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PlayerResponsePojo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public long getId() {
        return id;
    }

    public PlayerResponsePojo setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public PlayerResponsePojo setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PlayerResponsePojo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public PlayerResponsePojo setRole(String role) {
        this.role = role;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public PlayerResponsePojo setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }
}

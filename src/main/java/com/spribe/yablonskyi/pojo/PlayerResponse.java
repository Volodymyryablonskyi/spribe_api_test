package com.spribe.yablonskyi.pojo;

public class PlayerResponse {

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

    public PlayerResponse setAge(int age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PlayerResponse setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public long getId() {
        return id;
    }

    public PlayerResponse setId(long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public PlayerResponse setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PlayerResponse setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public PlayerResponse setRole(String role) {
        this.role = role;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public PlayerResponse setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }
}

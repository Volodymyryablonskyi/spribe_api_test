package com.spribe.yablonskyi.pojo;

public class SimplePlayer {

    private int age;
    private String gender;
    private long id;
    private String role;
    private String screenName;

    public int getAge() {
        return age;
    }

    public SimplePlayer setAge(int age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public SimplePlayer setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public long getId() {
        return id;
    }

    public SimplePlayer setId(long id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public SimplePlayer setRole(String role) {
        this.role = role;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public SimplePlayer setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

}
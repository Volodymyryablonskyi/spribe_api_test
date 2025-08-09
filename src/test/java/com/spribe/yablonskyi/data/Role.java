package com.spribe.yablonskyi.data;

public enum Role {

    SUPERVISOR("supervisor"),
    ADMIN("admin"),
    USER("user");

    private final String login;

    Role(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public static Role fromLogin(String login) {
        for (Role role : Role.values()) {
            if (role.getLogin().equalsIgnoreCase(login)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown login/role: " + login);
    }


}
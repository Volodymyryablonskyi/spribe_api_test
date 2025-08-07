package com.spribe.yablonskyi.testdata;

import com.spribe.yablonskyi.pojo.PlayerRequest;

public class CreatePlayerTestData extends BasePlayerTestData {

    public PlayerRequest generateValidPlayer() {
        return new PlayerRequest()
                .setAge(getRandomAge())
                .setGender(getValidGender())
                .setLogin(getValidLogin())
                .setPassword(getValidPassword())
                .setRole(getUserRole())
                .setScreenName(getValidScreenName());
    }

}

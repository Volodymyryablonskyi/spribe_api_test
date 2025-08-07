package com.spribe.yablonskyi.endpoints;

public class PlayerEndpoints implements BaseEndpoints {

    private static final String BASE_PATH = "/player";
    private static final String CREATE_PLAYER = BASE_PATH + "/create/%s";
    private static final String DELETE_PLAYER = BASE_PATH + "/delete/%s";
    private static final String GET_PLAYER_BY_ID = BASE_PATH + "/get";
    private static final String GET_ALL_PLAYERS = BASE_PATH + "/get/all";
    private static final String UPDATE_PLAYER = BASE_PATH + "/update/%s/%s";

    @Override
    public String getCreateUri(String editor) {
        return String.format(CREATE_PLAYER, editor);
    }

    @Override
    public String getDeleteUri(String editor) {
        return String.format(DELETE_PLAYER, editor);
    }

    @Override
    public String getGetByIdUri() {
        return GET_PLAYER_BY_ID;
    }

    @Override
    public String getGetAllUri() {
        return GET_ALL_PLAYERS;
    }

    @Override
    public String getUpdateUri(String editor, long id) {
        return String.format(UPDATE_PLAYER, editor, id);
    }

}
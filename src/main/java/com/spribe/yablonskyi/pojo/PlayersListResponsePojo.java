package com.spribe.yablonskyi.pojo;

import java.util.List;

public class PlayersListResponsePojo {
    private List<PlayerResponsePojo> players;

    public List<PlayerResponsePojo> getPlayers() {
        return players;
    }

    public PlayersListResponsePojo setPlayers(List<PlayerResponsePojo> players) {
        this.players = players;
        return this;
    }
}

package com.spribe.yablonskyi.pojo;

import java.util.List;
import java.util.Objects;

public class PlayersListResponsePojo {
    private List<PlayerResponsePojo> players;

    public List<PlayerResponsePojo> getPlayers() {
        return players;
    }

    public PlayersListResponsePojo setPlayers(List<PlayerResponsePojo> players) {
        this.players = players;
        return this;
    }

    @Override
    public String toString() {
        return "PlayersListResponsePojo{" +
                "players=" + players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayersListResponsePojo)) return false;
        PlayersListResponsePojo that = (PlayersListResponsePojo) o;
        return Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }


}

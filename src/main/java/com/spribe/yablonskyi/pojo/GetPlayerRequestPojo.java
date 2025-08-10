package com.spribe.yablonskyi.pojo;

import java.util.Objects;

public class GetPlayerRequestPojo {

    private Long playerId;


    public Long getPlayerId() {
        return playerId;
    }

    public GetPlayerRequestPojo setPlayerId(Long playerId) {
        this.playerId = playerId;
        return this;
    }

    @Override
    public String toString() {
        return "GetPlayerRequestPojo{" +
                "playerId=" + playerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetPlayerRequestPojo)) return false;
        GetPlayerRequestPojo that = (GetPlayerRequestPojo) o;
        return Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }

}

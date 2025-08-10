package com.spribe.yablonskyi.pojo;

import java.util.Objects;

public class DeletePlayerRequestPojo {

    private long playerId;

    public long getPlayerId() {
        return playerId;
    }

    public DeletePlayerRequestPojo setPlayerId(long playerId) {
        this.playerId = playerId;
        return this;
    }

    @Override
    public String toString() {
        return "DeletePlayerRequestPojo{" +
                "playerId=" + playerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeletePlayerRequestPojo)) return false;
        DeletePlayerRequestPojo that = (DeletePlayerRequestPojo) o;
        return playerId == that.playerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }


}

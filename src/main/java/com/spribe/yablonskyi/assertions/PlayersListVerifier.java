package com.spribe.yablonskyi.assertions;

import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import com.spribe.yablonskyi.pojo.PlayersListResponsePojo;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.function.Predicate;

public class PlayersListVerifier {

    private final List<PlayerResponsePojo> players;
    private final SoftAssert soft;

    private PlayersListVerifier(List<PlayerResponsePojo> players) {
        this.players = players;
        this.soft = new SoftAssert();
    }

    public static PlayersListVerifier verifyThat(PlayersListResponsePojo list) {
        return new PlayersListVerifier(list.getPlayers());
    }

    public PlayersListVerifier containsId(long id) {
        soft.assertTrue(players.stream().anyMatch(p -> p.getId() == id),
                "Expected list to contain player with id=" + id);
        return this;
    }

    public PlayersListVerifier containsScreenName(String screenName) {
        soft.assertTrue(players.stream().anyMatch(p -> screenName.equals(p.getScreenName())),
                "Expected list to contain player with screenName=" + screenName);
        return this;
    }

    public PlayersListVerifier containsLogin(String login) {
        soft.assertTrue(players.stream().anyMatch(p -> login.equals(p.getLogin())),
                "Expected list to contain player with login=" + login);
        return this;
    }

    public PlayersListVerifier contains(Predicate<PlayerResponsePojo> predicate, String messageIfMissing) {
        soft.assertTrue(players.stream().anyMatch(predicate), messageIfMissing);
        return this;
    }

    public void assertAll() {
        soft.assertAll();
    }

}
package com.spribe.yablonskyi.base;

import com.spribe.yablonskyi.clients.PlayersApiClient;
import com.spribe.yablonskyi.constants.Constants;
import com.spribe.yablonskyi.data.PlayerDataGenerator;
import com.spribe.yablonskyi.data.Role;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.http.response.StatusCode;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerResponsePojo;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.ArrayDeque;
import java.util.Deque;

public class BasePlayerTest extends BaseTest {

    protected static final String BASE_URL = Constants.BASE_URI;
    protected final static String ADMIN = Role.ADMIN.getLogin();
    protected final static String SUPERVISOR = Role.SUPERVISOR.getLogin();
    protected final ThreadLocal<Deque<Long>> createdIds = ThreadLocal.withInitial(ArrayDeque::new);
    protected final ThreadLocal<PlayerDataGenerator> playersDataGenerator = ThreadLocal.withInitial(PlayerDataGenerator::new);
    protected PlayersApiClient playersApiClient;

    @BeforeClass(alwaysRun = true)
    public void initContext() {
        this.playersApiClient = new PlayersApiClient(spec);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        Deque<Long> stack = createdIds.get();
        while (!stack.isEmpty()) {
            long id = stack.pollFirst();
            try {
                playersApiClient.deletePlayer(ADMIN, new DeletePlayerRequestPojo().setPlayerId(id));
            } catch (Throwable ignored) {  }
        }
    }

    protected ResponseWrapper callCreate(Role targetRole, String editorLogin) {
        PlayerRequestPojo req = playersDataGenerator.get().generateValidPlayer(targetRole.getLogin());
        ResponseWrapper resp =  playersApiClient.createPlayer(editorLogin, req);
        registerIfCreated(resp);
        return resp;
    }

    protected ResponseWrapper callUpdate(long id, String editorLogin, PlayerRequestPojo partial) {
        return playersApiClient.updatePlayer(editorLogin, id, partial);
    }

    protected ResponseWrapper callDelete(long id, String editorLogin) {
        DeletePlayerRequestPojo del = new DeletePlayerRequestPojo().setPlayerId(id);
        return playersApiClient.deletePlayer(editorLogin, del);
    }

    protected void registerForCleanup(long id) { createdIds.get().addFirst(id); }

    protected void registerIfCreated(ResponseWrapper resp) {
        if (resp.statusCode() == StatusCode._200_OK || resp.statusCode() == StatusCode._201_CREATED) {
            Long id = resp.getId();
            if (id != null && id > 0) {
                registerForCleanup(id);
            }
        }
    }

    protected PlayerResponsePojo createUser(Role targetRole, String editorLogin) {
        PlayerRequestPojo request = playersDataGenerator.get().generateValidPlayer(targetRole.getLogin());
        ResponseWrapper resp = playersApiClient.createPlayer(editorLogin, request);
        if (!resp.statusCode().equals(StatusCode._200_OK) && !resp.statusCode().equals(StatusCode._201_CREATED)) {
            throw new AssertionError("Failed to create user. code=" + resp.statusCode() + " body=" + resp.asString());
        }
        PlayerResponsePojo created = resp.asPojo(PlayerResponsePojo.class);
        registerForCleanup(created.getId());
        return created;
    }

    protected PlayerResponsePojo createUser(Role targetRole) {
        return createUser(targetRole, targetRole.equals(Role.SUPERVISOR) ? SUPERVISOR : ADMIN);
    }

    protected ResponseWrapper createUser(String editorLogin, PlayerRequestPojo request) {
        ResponseWrapper resp = playersApiClient.createPlayer(editorLogin, request);
        registerForCleanup(resp.getId());
        return resp;
    }

    protected ResponseWrapper createAsAdmin(PlayerRequestPojo req) {
        ResponseWrapper resp = playersApiClient.createPlayer(ADMIN, req);
        registerIfCreated(resp);
        return resp;
    }

    protected PlayerResponsePojo fetchPlayer(long id) {
        var resp = playersApiClient.getPlayerById(id);
        if (!resp.statusCode().equals(StatusCode._200_OK)) {
            throw new AssertionError("GET by id failed. code=" + resp.statusCode() + " body=" + resp.asString());
        }
        return resp.asPojo(PlayerResponsePojo.class);
    }

}
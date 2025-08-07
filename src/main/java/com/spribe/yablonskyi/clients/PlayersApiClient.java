package com.spribe.yablonskyi.clients;

import com.spribe.yablonskyi.constants.Constants;
import com.spribe.yablonskyi.endpoints.PlayerEndpoints;
import com.spribe.yablonskyi.http.request.HttpMethods;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.GetPlayerRequestPojo;
import com.spribe.yablonskyi.pojo.DeletePlayerRequestPojo;
import com.spribe.yablonskyi.pojo.PlayerRequestPojo;
import com.spribe.yablonskyi.util.CustomLogger;
import com.spribe.yablonskyi.util.PojoConverter;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PlayersApiClient extends BaseApiClient<PlayerEndpoints> {

    private static final CustomLogger log = CustomLogger.getLogger(PlayersApiClient.class);

    public PlayersApiClient(RequestSpecification spec) {
        super(spec, Constants.BASE_URI, new PlayerEndpoints());
    }

    public ResponseWrapper createPlayer(String editor, PlayerRequestPojo request) {
        log.info("Create new player: {}", request.toString());
        Map<String, String> queryParams = PojoConverter.toQueryParams(request);
        return request(
                HttpMethods.GET,
                endpoints.getCreateUri(editor),
                null,
                queryParams,
                null
        );
    }

    public ResponseWrapper getPlayerById(GetPlayerRequestPojo request) {
        return request(
                HttpMethods.POST,
                endpoints.getGetByIdUri(),
                request
        );
    }

    public ResponseWrapper deletePlayer(String editor, DeletePlayerRequestPojo request) {
        return request(
                HttpMethods.DELETE,
                endpoints.getDeleteUri(editor),
                request
        );
    }

    public ResponseWrapper updatePlayer(String editor, long id, PlayerRequestPojo request) {
        return request(
                HttpMethods.PATCH,
                endpoints.getUpdateUri(editor, id),
                request
        );
    }

    public ResponseWrapper getAllPlayers() {
        return request(
                HttpMethods.GET,
                endpoints.getGetAllUri()
        );
    }


}

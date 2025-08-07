package com.spribe.yablonskyi.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spribe.yablonskyi.endpoints.PlayerEndpoints;
import com.spribe.yablonskyi.http.request.HttpMethods;
import com.spribe.yablonskyi.http.response.ResponseWrapper;
import com.spribe.yablonskyi.pojo.PlayerIdRequest;
import com.spribe.yablonskyi.pojo.PlayerRequest;
import com.spribe.yablonskyi.util.PojoConverter;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PlayersApiClient extends BaseApiClient<PlayerEndpoints> {

    public PlayersApiClient(RequestSpecification spec) {
        super(spec, new PlayerEndpoints());
    }

    public ResponseWrapper createPlayer(String editor, PlayerRequest request) {
        Map<String, String> queryParams = PojoConverter.toQueryParams(request);
        return request(
                HttpMethods.GET,
                endpoints.getCreateUri(editor),
                null,
                queryParams,
                null
        );
    }

    public ResponseWrapper getPlayerById(PlayerIdRequest request) {
        return request(
                HttpMethods.POST,
                endpoints.getGetByIdUri(),
                request
        );
    }

    public ResponseWrapper deletePlayer(String editor, PlayerIdRequest request) {
        return request(
                HttpMethods.DELETE,
                endpoints.getDeleteUri(editor),
                request
        );
    }

    public ResponseWrapper updatePlayer(String editor, long id, PlayerRequest request) {
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

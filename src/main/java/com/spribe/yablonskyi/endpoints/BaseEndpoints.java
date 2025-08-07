package com.spribe.yablonskyi.endpoints;

public interface BaseEndpoints {

    String getCreateUri(String editor);
    String getDeleteUri(String editor);
    String getGetByIdUri();
    String getGetAllUri();
    String getUpdateUri(String editor, long id);

}
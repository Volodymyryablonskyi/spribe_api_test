package com.spribe.yablonskyi.http.response;

public enum StatusCode {

    _200_OK(200),
    _201_CREATED(201),
    _204_NO_CONTENT(204),
    _400_BAD_REQUEST(400),
    _401_UNAUTHORIZED(401),
    _403_FORBIDDEN(403),
    _404_NOT_FOUND(404),
    _409_CONFLICT(409);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusCode parseToCode(int code) {
        for (StatusCode status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown HTTP status code: " + code);
    }

    @Override
    public String toString() {
        return String.valueOf(getCode());
    }

}

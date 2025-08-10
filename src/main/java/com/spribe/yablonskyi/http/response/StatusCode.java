package com.spribe.yablonskyi.http.response;

public enum StatusCode {

    STATUS_200_OK(200),
    STATUS_201_CREATED(201),
    STATUS_204_NO_CONTENT(204),
    STATUS_400_BAD_REQUEST(400),
    STATUS_401_UNAUTHORIZED(401),
    STATUS_403_FORBIDDEN(403),
    STATUS_404_NOT_FOUND(404),
    STATUS_409_CONFLICT(409);

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

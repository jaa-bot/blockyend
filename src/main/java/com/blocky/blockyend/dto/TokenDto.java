package com.blocky.blockyend.dto;

public class TokenDto {
    private String endpoint;
    private String auth;
    private String p256dh;
    private int userId;

    public TokenDto(String endpoint, String auth, String p256dh, int userId) {
        this.endpoint = endpoint;
        this.auth = auth;
        this.p256dh = p256dh;
        this.userId = userId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

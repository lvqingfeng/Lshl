package com.lshl.bean;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AuthenticationBean {
    private String token;
    private String idcard;

    public String getToken() {
        return token;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}

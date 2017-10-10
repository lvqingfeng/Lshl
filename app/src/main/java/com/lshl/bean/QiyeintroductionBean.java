package com.lshl.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/30.
 */

public class QiyeintroductionBean implements Serializable{
    private String token;
    private String name;
    private String info;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

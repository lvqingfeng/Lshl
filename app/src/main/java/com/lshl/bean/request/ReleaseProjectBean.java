package com.lshl.bean.request;

/**
 * Created by Administrator on 2016/11/21.
 */

public class ReleaseProjectBean {
    private String token;
    private String title;
    private String address;
    private String info;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getToken() {
        return token;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getInfo() {
        return info;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

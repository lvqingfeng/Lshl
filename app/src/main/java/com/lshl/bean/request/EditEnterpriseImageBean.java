package com.lshl.bean.request;

/**
 * Created by Administrator on 2016/12/28.
 */

public class EditEnterpriseImageBean {
    private String token;
    private String enid;
    private String info;
    private String old_img;
    private String del_img;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnid() {
        return enid;
    }

    public void setEnid(String enid) {
        this.enid = enid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOld_img() {
        return old_img;
    }

    public void setOld_img(String old_img) {
        this.old_img = old_img;
    }

    public String getDel_img() {
        return del_img;
    }

    public void setDel_img(String del_img) {
        this.del_img = del_img;
    }
}

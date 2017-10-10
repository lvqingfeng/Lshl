package com.lshl.bean.request;

/***
 * Created by Administrator on 2017/1/18.
 */

public class ImageWallBean {
    private String token;
    private String  old_img;
    private String del_img;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

package com.lshl.bean.request;

import java.io.Serializable;

/***
 * Created by Administrator on 2017/1/13.
 */

public class UpdatePeojectBean implements Serializable {
    private String token;
    private String pid;
    private String title;
    private String info;
    private String address;
    private String imgfile;
    private String old_img;
    private String del_img;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgfile() {
        return imgfile;
    }

    public void setImgfile(String imgfile) {
        this.imgfile = imgfile;
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

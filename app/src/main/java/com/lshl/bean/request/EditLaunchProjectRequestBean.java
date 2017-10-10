package com.lshl.bean.request;

/**
 * 作者：吕振鹏
 * 创建时间：12月27日
 * 时间：21:56
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class EditLaunchProjectRequestBean {

    /*token	string	Y
pid	int	Y		项目id
title	string	Y		标题
info	string	Y		说明
address	string	Y		地址
imgfile	file	Y		图片
old_img	string	Y		剩余图片
del_img	string	Y		删除的图片*/


    private String token;

    private String pid;
    private String title;
    private String info;
    private String address;
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

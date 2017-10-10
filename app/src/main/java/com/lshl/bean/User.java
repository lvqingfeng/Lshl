package com.lshl.bean;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：10月21日
 * 时间：15:19
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class User implements Serializable {


    /**
     * hxid : 4e76288696b0714ad30697b2c007e796
     * token : e31508d8bc6609c8dd8375c88a05cb21
     * uid : 41
     */

    private String hxid;
    private String token;
    private String uid;
    private String realName;
    private String phone;
    private String avatar;
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

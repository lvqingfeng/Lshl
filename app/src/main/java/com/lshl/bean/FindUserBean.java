package com.lshl.bean;

import com.lshl.utils.DateUtils;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：10月15日
 * 时间：22:41
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class FindUserBean implements Serializable {


    /**
     * uid : 1
     * realname :   张三
     * phone : 18366189938
     * age : 29
     * sex : 1
     * hx_id : null
     * avatar :
     */

    private String uid;
    private String realname;
    private String phone;
    private String age;
    private int sex;
    private String hx_id;
    private String avatar;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAgeBirthDate() {
        return DateUtils.getAgeBirthDate(age);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHx_id() {
        return hx_id;
    }

    public void setHx_id(String hx_id) {
        this.hx_id = hx_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

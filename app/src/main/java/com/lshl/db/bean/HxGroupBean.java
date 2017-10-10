package com.lshl.db.bean;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：10月17日
 * 时间：22:01
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxGroupBean implements Serializable {

    private int id;
    private String add_time;
    private String gid;
    private String group_id;
    private String group_img;
    private String group_info;
    private String group_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getGroup_info() {
        return group_info;
    }

    public void setGroup_info(String group_info) {
        this.group_info = group_info;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

}

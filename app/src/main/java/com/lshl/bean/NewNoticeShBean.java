package com.lshl.bean;

/**
 * Created by Administrator on 2017/3/5.
 */

public class NewNoticeShBean {

    /**
     * status : 1
     * info : 1
     * business_name : 测试商会
     */

    private int status;
    private int info;
    private String business_name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }
}

package com.lshl.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ReturnResultBean {

    /**
     * status : 3
     * message : 系统异常(token)，请稍后再试
     */

    private int status;
    private String info;

    public static ReturnResultBean objectFromData(String str) {

        return new Gson().fromJson(str, ReturnResultBean.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return info;
    }

    public void setMessage(String message) {
        this.info = message;
    }
}

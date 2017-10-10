package com.lshl.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/25.
 */

public class ResultInfoBean implements Serializable{

    /**
     * status : 1
     * info : 1
     */

    private int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

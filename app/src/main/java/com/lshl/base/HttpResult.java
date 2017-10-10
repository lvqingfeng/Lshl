package com.lshl.base;

/**
 * 作者：吕振鹏
 * 创建时间：11月07日
 * 时间：16:07
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HttpResult<T> {

    protected String status;
    protected T info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}

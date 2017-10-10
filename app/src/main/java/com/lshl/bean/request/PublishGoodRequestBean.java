package com.lshl.bean.request;

/**
 * 作者：吕振鹏
 * 创建时间：11月23日
 * 时间：20:31
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PublishGoodRequestBean {

    /**
     * token	string	Y
     * file	file	Y		项目图片
     * name	string	Y		项目名称
     * money	string	Y		项目金额
     * info	string	Y		项目信息
     * address	string	Y		项目地址
     */

    private String token;
    private String name;
    private String money;
    private String info;
    private String address;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
}

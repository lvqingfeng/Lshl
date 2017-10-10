package com.lshl.bean.request;

/**
 * 作者：吕振鹏
 * 创建时间：11月14日
 * 时间：23:10
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ReleaseReputationRequestBean {
    /*title	string	Y		标题
info	string	Y		内容
type	int	Y		类型(1红榜 2黑榜)
file	file	N		图片
city	string	Y		发布地区*/
    private String token;//
    private String title;//标题
    private String info;//内容
    private String type;//类型
    private String city;//城市名称

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

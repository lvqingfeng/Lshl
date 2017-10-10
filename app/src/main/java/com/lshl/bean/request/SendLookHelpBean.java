package com.lshl.bean.request;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SendLookHelpBean {
    private String token;//
    private String title;//标题
    private String info;//内容
    private String cityname;//城市名称
    private String cityno;//城市编号

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

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityno() {
        return cityno;
    }

    public void setCityno(String cityno) {
        this.cityno = cityno;
    }
}

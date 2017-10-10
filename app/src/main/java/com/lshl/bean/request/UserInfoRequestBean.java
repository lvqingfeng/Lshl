package com.lshl.bean.request;

import android.text.TextUtils;

/**
 * 作者：吕振鹏
 * 创建时间：12月26日
 * 时间：16:03
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class UserInfoRequestBean {

    private String token;
    private String realname;//       真实姓名
    private String rd;//          性别
    private String age;//             出生年月日
    private String origin_cityname;//   老家城市
    private String origin_county;//     老家县区
    private String origin_address;//   老家详情地址
    private String live_cityname;//   现居城市
    private String live_county;//     现居县区（街道）
    private String live_address;//    现居详细地址
    private String live_cityno;//    城市编号

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOrigin_cityname() {
        return origin_cityname;
    }

    public void setOrigin_cityname(String origin_cityname) {
        this.origin_cityname = origin_cityname;
    }

    public String getOrigin_county() {
        return origin_county;
    }

    public void setOrigin_county(String origin_county) {
        this.origin_county = origin_county;
    }

    public String getOrigin_address() {
        return origin_address;
    }

    public void setOrigin_address(String origin_address) {
        this.origin_address = origin_address;
    }

    public String getLive_cityname() {
        return live_cityname;
    }

    public void setLive_cityname(String live_cityname) {
        this.live_cityname = live_cityname;
    }

    public String getLive_county() {
        return live_county;
    }

    public void setLive_county(String live_county) {
        this.live_county = live_county;
    }

    public String getLive_address() {
        return live_address;
    }

    public void setLive_address(String live_address) {
        this.live_address = live_address;
    }

    public String getLive_cityno() {
        return live_cityno;
    }

    public void setLive_cityno(String live_cityno) {
        this.live_cityno = live_cityno;
    }

    public boolean hasEmpty() {
        return TextUtils.isEmpty(token) || TextUtils.isEmpty(realname) || TextUtils
                .isEmpty(live_cityname) ||
                TextUtils.isEmpty(live_cityno) || TextUtils.isEmpty(rd) || TextUtils
                .isEmpty(origin_cityname) || TextUtils.isEmpty(age);
    }

}

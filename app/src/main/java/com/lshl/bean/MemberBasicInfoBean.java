package com.lshl.bean;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：11月03日
 * 时间：9:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class MemberBasicInfoBean implements Serializable {

    /**
     * status : 1
     * info : {"uid":"3","phone":"18366189918","realname":"魁大圣","idcard":"620104198705131776","sex":"1","age":"29","home_address":"兰州市西固区","current_address":"兰州市七里河区土门墩","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","avatar":"58083664e2360.png","cityname":"兰州市"}
     */

    private int status;

    /**
     * age : 1988年7月12日
     * all_live : 甘肃省兰州市安宁区
     * all_origin : 济宁市梁山宋江故居
     * authenticate : 1
     * avatar : 585496f302a8c.jpg
     * enterprise : 1
     * grade : 3
     * hx_id : 04b82c554a89931a699f48dc496d2305
     * idcard : 620104198807121885
     * live_address : 金牛街
     * live_cityname : 甘肃省兰州市
     * live_county : 安宁区
     * lj_county : 济宁市梁山
     * origin_address : 宋江故居
     * origin_cityname : 济宁市
     * origin_county : 梁山
     * phone : 18919912285
     * president : 0
     * profile : 我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿你我的呢饿
     * realname : 李志刚
     * sex : 1
     * uid : 15
     * xj_county : 甘肃省兰州市安宁区
     */


    private InfoBean info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable {


        private String age;
        private String all_live;
        private String all_origin;
        private String authenticate;
        private String avatar;
        private String enterprise;
        private int grade;
        private String hx_id;
        private String idcard;
        private String live_address;
        private String live_cityname;
        private String live_county;
        private String lj_county;
        private String origin_address;
        private String origin_cityname;
        private String origin_county;
        private String phone;
        private String president;
        private String profile;
        private String realname;
        private String sex;
        private String uid;
        private String xj_county;

        public static String getSex(String sexCode) {
            if (sexCode.equals("1")) {
                return "男";
            } else {
                return "女";
            }
        }
        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAll_live() {
            return all_live;
        }

        public void setAll_live(String all_live) {
            this.all_live = all_live;
        }

        public String getAll_origin() {
            return all_origin;
        }

        public void setAll_origin(String all_origin) {
            this.all_origin = all_origin;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(String enterprise) {
            this.enterprise = enterprise;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getLive_address() {
            return live_address;
        }

        public void setLive_address(String live_address) {
            this.live_address = live_address;
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

        public String getLj_county() {
            return lj_county;
        }

        public void setLj_county(String lj_county) {
            this.lj_county = lj_county;
        }

        public String getOrigin_address() {
            return origin_address;
        }

        public void setOrigin_address(String origin_address) {
            this.origin_address = origin_address;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPresident() {
            return president;
        }

        public void setPresident(String president) {
            this.president = president;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getXj_county() {
            return xj_county;
        }

        public void setXj_county(String xj_county) {
            this.xj_county = xj_county;
        }
    }
}

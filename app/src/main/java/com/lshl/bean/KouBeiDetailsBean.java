package com.lshl.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/4.
 */

public class KouBeiDetailsBean implements Serializable {

    /**
     * status : 1
     * info : {"realname":"李志刚","phone":"13162839599","hx_id":"04b82c554a89931a699f48dc496d2305","avatar":"585496f302a8c.jpg","uid":"15","pay_starttime":null,"pay_endtime":null,"authenticate":"1","origin_cityname":"济宁市","origin_county":"梁山","s_id":"4","s_uid":"15","s_title":"我要发红榜","s_info":"我要发红榜","s_img":"5854a44645592.png,5854a446464d5.png,5854a44648afb.png","s_city":"内蒙古呼和浩特市","s_type":"1","s_status":"1","s_addtime":"1481942086","s_editinfo":"ok","s_edittime":"1481942113","grade":3}
     */

    private int status;
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

    public static class InfoBean {
        /**
         * realname : 李志刚
         * phone : 13162839599
         * hx_id : 04b82c554a89931a699f48dc496d2305
         * avatar : 585496f302a8c.jpg
         * uid : 15
         * pay_starttime : null
         * pay_endtime : null
         * authenticate : 1
         * origin_cityname : 济宁市
         * origin_county : 梁山
         * s_id : 4
         * s_uid : 15
         * s_title : 我要发红榜
         * s_info : 我要发红榜
         * s_img : 5854a44645592.png,5854a446464d5.png,5854a44648afb.png
         * s_city : 内蒙古呼和浩特市
         * s_type : 1
         * s_status : 1
         * s_addtime : 1481942086
         * s_editinfo : ok
         * s_edittime : 1481942113
         * grade : 3
         */

        private String realname;
        private String phone;
        private String hx_id;
        private String avatar;
        private String uid;
        private Object pay_starttime;
        private Object pay_endtime;
        private String authenticate;
        private String origin_cityname;
        private String origin_county;
        private String s_id;
        private String s_uid;
        private String s_title;
        private String s_info;
        private String s_img;
        private String s_city;
        private String s_type;
        private String s_status;
        private String s_addtime;
        private String s_editinfo;
        private String s_edittime;
        private int grade;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Object getPay_starttime() {
            return pay_starttime;
        }

        public void setPay_starttime(Object pay_starttime) {
            this.pay_starttime = pay_starttime;
        }

        public Object getPay_endtime() {
            return pay_endtime;
        }

        public void setPay_endtime(Object pay_endtime) {
            this.pay_endtime = pay_endtime;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
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

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getS_uid() {
            return s_uid;
        }

        public void setS_uid(String s_uid) {
            this.s_uid = s_uid;
        }

        public String getS_title() {
            return s_title;
        }

        public void setS_title(String s_title) {
            this.s_title = s_title;
        }

        public String getS_info() {
            return s_info;
        }

        public void setS_info(String s_info) {
            this.s_info = s_info;
        }

        public String getS_img() {
            return s_img;
        }

        public void setS_img(String s_img) {
            this.s_img = s_img;
        }

        public String getS_city() {
            return s_city;
        }

        public void setS_city(String s_city) {
            this.s_city = s_city;
        }

        public String getS_type() {
            return s_type;
        }

        public void setS_type(String s_type) {
            this.s_type = s_type;
        }

        public String getS_status() {
            return s_status;
        }

        public void setS_status(String s_status) {
            this.s_status = s_status;
        }

        public String getS_addtime() {
            return s_addtime;
        }

        public void setS_addtime(String s_addtime) {
            this.s_addtime = s_addtime;
        }

        public String getS_editinfo() {
            return s_editinfo;
        }

        public void setS_editinfo(String s_editinfo) {
            this.s_editinfo = s_editinfo;
        }

        public String getS_edittime() {
            return s_edittime;
        }

        public void setS_edittime(String s_edittime) {
            this.s_edittime = s_edittime;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }
}

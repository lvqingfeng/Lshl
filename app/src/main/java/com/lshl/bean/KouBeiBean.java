package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class KouBeiBean implements Serializable{

    /**
     * status : 1
     * info : [{"realname":"局长","phone":"18306431704","hx_id":"647731a4d5736ce5e00f67133d665a61","avatar":"5840f4f4194b5.jpg","uid":"11","pay_starttime":null,"pay_endtime":null,"authenticate":"1","s_id":"3","s_uid":"11","s_title":"发个黑帮","s_info":"分享到新闻了记住，我是口碑的数据(⊙o⊙)哦","s_img":"5840f61eb8f93.png,5840f61ebcc9c.png,5840f61ec0d76.png,5840f61ec46af.png","s_city":"甘肃省兰州市","s_type":"2","s_status":"1","s_addtime":"1480652318","s_editinfo":"ok","s_edittime":"1481017600","grade":3}]
     */

    private int status;
    /**
     * realname : 局长
     * phone : 18306431704
     * hx_id : 647731a4d5736ce5e00f67133d665a61
     * avatar : 5840f4f4194b5.jpg
     * uid : 11
     * pay_starttime : null
     * pay_endtime : null
     * authenticate : 1
     * s_id : 3
     * s_uid : 11
     * s_title : 发个黑帮
     * s_info : 分享到新闻了记住，我是口碑的数据(⊙o⊙)哦
     * s_img : 5840f61eb8f93.png,5840f61ebcc9c.png,5840f61ec0d76.png,5840f61ec46af.png
     * s_city : 甘肃省兰州市
     * s_type : 2
     * s_status : 1
     * s_addtime : 1480652318
     * s_editinfo : ok
     * s_edittime : 1481017600
     * grade : 3
     */

    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        private String realname;
        private String phone;
        private String hx_id;
        private String avatar;
        private String uid;
        private String pay_starttime;
        private String pay_endtime;
        private String authenticate;
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

        public String getPay_starttime() {
            return pay_starttime;
        }

        public void setPay_starttime(String pay_starttime) {
            this.pay_starttime = pay_starttime;
        }

        public String getPay_endtime() {
            return pay_endtime;
        }

        public void setPay_endtime(String pay_endtime) {
            this.pay_endtime = pay_endtime;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
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

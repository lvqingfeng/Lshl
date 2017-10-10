package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class LookHelpDetailsBean {

    /**
     * status : 1
     * info : {"fh_id":"15","fh_uid":"4","fh_title":"生物所以上网友","fh_info":"摩托车跑车票帝帮你买票房间","fh_img":"587586f94576060601.png,587586f94576086399.png,587586f94576087658.png,587586f94576058104.png,587586f94946970888.png,587586f94946994352.png","fh_cityname":"甘肃省兰州市","fh_cityno":"0","fh_status":"1","fh_addtime":"1484097273","fh_editinfo":null,"fh_edittime":null,"uid":"4","realname":"吕清锋","phone":"17010207171","avatar":"586710d342d2d60135.jpg","hx_id":"145e03ecd389cec5c4f28ffcc99f3994","age":"1991年10月16日 ","sex":"1","live_cityname":"甘肃省兰州市","origin_cityname":"东营市","origin_county":"嘉祥","pay_starttime":null,"pay_endtime":null,"authenticate":"2","image":["587586f94576060601.png","587586f94576086399.png","587586f94576087658.png","587586f94576058104.png","587586f94946970888.png","587586f94946994352.png"],"grade":1}
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
         * fh_id : 15
         * fh_uid : 4
         * fh_title : 生物所以上网友
         * fh_info : 摩托车跑车票帝帮你买票房间
         * fh_img : 587586f94576060601.png,587586f94576086399.png,587586f94576087658.png,587586f94576058104.png,587586f94946970888.png,587586f94946994352.png
         * fh_cityname : 甘肃省兰州市
         * fh_cityno : 0
         * fh_status : 1
         * fh_addtime : 1484097273
         * fh_editinfo : null
         * fh_edittime : null
         * uid : 4
         * realname : 吕清锋
         * phone : 17010207171
         * avatar : 586710d342d2d60135.jpg
         * hx_id : 145e03ecd389cec5c4f28ffcc99f3994
         * age : 1991年10月16日
         * sex : 1
         * live_cityname : 甘肃省兰州市
         * origin_cityname : 东营市
         * origin_county : 嘉祥
         * pay_starttime : null
         * pay_endtime : null
         * authenticate : 2
         * image : ["587586f94576060601.png","587586f94576086399.png","587586f94576087658.png","587586f94576058104.png","587586f94946970888.png","587586f94946994352.png"]
         * grade : 1
         */

        private String fh_id;
        private String fh_uid;
        private String fh_title;
        private String fh_info;
        private String fh_img;
        private String fh_cityname;
        private String fh_cityno;
        private String fh_status;
        private String fh_addtime;
        private Object fh_editinfo;
        private Object fh_edittime;
        private String uid;
        private String realname;
        private String phone;
        private String avatar;
        private String hx_id;
        private String age;
        private String sex;
        private String live_cityname;
        private String origin_cityname;
        private String origin_county;
        private Object pay_starttime;
        private Object pay_endtime;
        private String authenticate;
        private int grade;
        private List<String> image;

        public String getFh_id() {
            return fh_id;
        }

        public void setFh_id(String fh_id) {
            this.fh_id = fh_id;
        }

        public String getFh_uid() {
            return fh_uid;
        }

        public void setFh_uid(String fh_uid) {
            this.fh_uid = fh_uid;
        }

        public String getFh_title() {
            return fh_title;
        }

        public void setFh_title(String fh_title) {
            this.fh_title = fh_title;
        }

        public String getFh_info() {
            return fh_info;
        }

        public void setFh_info(String fh_info) {
            this.fh_info = fh_info;
        }

        public String getFh_img() {
            return fh_img;
        }

        public void setFh_img(String fh_img) {
            this.fh_img = fh_img;
        }

        public String getFh_cityname() {
            return fh_cityname;
        }

        public void setFh_cityname(String fh_cityname) {
            this.fh_cityname = fh_cityname;
        }

        public String getFh_cityno() {
            return fh_cityno;
        }

        public void setFh_cityno(String fh_cityno) {
            this.fh_cityno = fh_cityno;
        }

        public String getFh_status() {
            return fh_status;
        }

        public void setFh_status(String fh_status) {
            this.fh_status = fh_status;
        }

        public String getFh_addtime() {
            return fh_addtime;
        }

        public void setFh_addtime(String fh_addtime) {
            this.fh_addtime = fh_addtime;
        }

        public Object getFh_editinfo() {
            return fh_editinfo;
        }

        public void setFh_editinfo(Object fh_editinfo) {
            this.fh_editinfo = fh_editinfo;
        }

        public Object getFh_edittime() {
            return fh_edittime;
        }

        public void setFh_edittime(Object fh_edittime) {
            this.fh_edittime = fh_edittime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLive_cityname() {
            return live_cityname;
        }

        public void setLive_cityname(String live_cityname) {
            this.live_cityname = live_cityname;
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

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }
    }
}

package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ResumeDetailsBean {

    /**
     * status : 1
     * info : [{"r_id":"1","r_uid":"9","r_erid":"1","r_status":"1","r_addtime":"1482415736","cv_id":"4","cv_uid":"9","cv_school":"","cv_education":"其他","cv_major":"","cv_addtime":"1482402773","realname":"李菲菲","avatar":"58524c6a1408e.jpg","phone":"18393811681","hx_id":"3fde1275d0082eae973b2b2feebd05e6","live_cityname":"甘肃省兰州市","origin_cityname":"聊城市","origin_county":"莘县","sex":"2","age":"1991年11月13日 ","profile":"瞬间感觉心情美美哒！","live_county":"安宁区"}]
     */

    private int status;
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
        /**
         * r_id : 1
         * r_uid : 9
         * r_erid : 1
         * r_status : 1
         * r_addtime : 1482415736
         * cv_id : 4
         * cv_uid : 9
         * cv_school :
         * cv_education : 其他
         * cv_major :
         * cv_addtime : 1482402773
         * realname : 李菲菲
         * avatar : 58524c6a1408e.jpg
         * phone : 18393811681
         * hx_id : 3fde1275d0082eae973b2b2feebd05e6
         * live_cityname : 甘肃省兰州市
         * origin_cityname : 聊城市
         * origin_county : 莘县
         * sex : 2
         * age : 1991年11月13日
         * profile : 瞬间感觉心情美美哒！
         * live_county : 安宁区
         */

        private String r_id;
        private String r_uid;
        private String r_erid;
        private String r_status;
        private String r_addtime;
        private String cv_id;
        private String cv_uid;
        private String cv_school;
        private String cv_education;
        private String cv_major;
        private String cv_addtime;
        private String realname;
        private String avatar;
        private String phone;
        private String hx_id;
        private String live_cityname;
        private String origin_cityname;
        private String origin_county;
        private String sex;
        private String age;
        private String profile;
        private String live_county;

        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
            this.r_id = r_id;
        }

        public String getR_uid() {
            return r_uid;
        }

        public void setR_uid(String r_uid) {
            this.r_uid = r_uid;
        }

        public String getR_erid() {
            return r_erid;
        }

        public void setR_erid(String r_erid) {
            this.r_erid = r_erid;
        }

        public String getR_status() {
            return r_status;
        }

        public void setR_status(String r_status) {
            this.r_status = r_status;
        }

        public String getR_addtime() {
            return r_addtime;
        }

        public void setR_addtime(String r_addtime) {
            this.r_addtime = r_addtime;
        }

        public String getCv_id() {
            return cv_id;
        }

        public void setCv_id(String cv_id) {
            this.cv_id = cv_id;
        }

        public String getCv_uid() {
            return cv_uid;
        }

        public void setCv_uid(String cv_uid) {
            this.cv_uid = cv_uid;
        }

        public String getCv_school() {
            return cv_school;
        }

        public void setCv_school(String cv_school) {
            this.cv_school = cv_school;
        }

        public String getCv_education() {
            return cv_education;
        }

        public void setCv_education(String cv_education) {
            this.cv_education = cv_education;
        }

        public String getCv_major() {
            return cv_major;
        }

        public void setCv_major(String cv_major) {
            this.cv_major = cv_major;
        }

        public String getCv_addtime() {
            return cv_addtime;
        }

        public void setCv_addtime(String cv_addtime) {
            this.cv_addtime = cv_addtime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getLive_county() {
            return live_county;
        }

        public void setLive_county(String live_county) {
            this.live_county = live_county;
        }
    }
}

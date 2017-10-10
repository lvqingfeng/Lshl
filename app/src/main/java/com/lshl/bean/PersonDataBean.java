package com.lshl.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/12.
 */

public class PersonDataBean implements Serializable {

    /**
     * status : 1
     * info : {"uid":"6","phone":"18306431704","realname":"吕传会","idcard":"370829199408204211","sex":"1","age":"1994年8月13日 ","enterprise":"1","hx_id":"3d644f1c0064bece3b0f873846171bf7","avatar":"58526899511b4.jpg","authenticate":"1","profile":"漂流瓶提绿水江漂流刘乱世佳人剧情介绍马上就睡了这破手机有哦婆婆咯哦婆婆咯","origin_cityname":"济宁市","president":"0","origin_county":"嘉祥县","origin_address":"马村镇","live_cityname":"山东省济南市","live_county":"高新区","live_address":"经十路","idcard_img_z":"58526878a6de9.png","idcard_img_f":"58526878a71ba.png","live_cityno":"1135","enterprise_list":{"en_id":"51","en_name":"企业信息","en_img":"58612e9f61510.png","en_addtime":"1482763994","en_uid":"6"},"lj_county":"济宁市嘉祥县","xj_county":"山东省济南市高新区","all_origin":"济宁市嘉祥县马村镇","all_live":"山东省济南市高新区","grade":1}
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

    public static class InfoBean implements Serializable{
        /**
         * uid : 6
         * phone : 18306431704
         * realname : 吕传会
         * idcard : 370829199408204211
         * sex : 1
         * age : 1994年8月13日
         * enterprise : 1
         * hx_id : 3d644f1c0064bece3b0f873846171bf7
         * avatar : 58526899511b4.jpg
         * authenticate : 1
         * profile : 漂流瓶提绿水江漂流刘乱世佳人剧情介绍马上就睡了这破手机有哦婆婆咯哦婆婆咯
         * origin_cityname : 济宁市
         * president : 0
         * origin_county : 嘉祥县
         * origin_address : 马村镇
         * live_cityname : 山东省济南市
         * live_county : 高新区
         * live_address : 经十路
         * idcard_img_z : 58526878a6de9.png
         * idcard_img_f : 58526878a71ba.png
         * live_cityno : 1135
         * enterprise_list : {"en_id":"51","en_name":"企业信息","en_img":"58612e9f61510.png","en_addtime":"1482763994","en_uid":"6"}
         * lj_county : 济宁市嘉祥县
         * xj_county : 山东省济南市高新区
         * all_origin : 济宁市嘉祥县马村镇
         * all_live : 山东省济南市高新区
         * grade : 1
         */

        private String uid;
        private String phone;
        private String realname;
        private String idcard;
        private String sex;
        private String age;
        private String enterprise;
        private String hx_id;
        private String avatar;
        private String authenticate;
        private String profile;
        private String origin_cityname;
        private String president;
        private String origin_county;
        private String origin_address;
        private String live_cityname;
        private String live_county;
        private String live_address;
        private String idcard_img_z;
        private String idcard_img_f;
        private String live_cityno;
        private String lj_county;
        private String xj_county;
        private String all_origin;
        private String all_live;
        private int grade;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
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

        public String getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(String enterprise) {
            this.enterprise = enterprise;
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

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getOrigin_cityname() {
            return origin_cityname;
        }

        public void setOrigin_cityname(String origin_cityname) {
            this.origin_cityname = origin_cityname;
        }

        public String getPresident() {
            return president;
        }

        public void setPresident(String president) {
            this.president = president;
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

        public String getIdcard_img_z() {
            return idcard_img_z;
        }

        public void setIdcard_img_z(String idcard_img_z) {
            this.idcard_img_z = idcard_img_z;
        }

        public String getIdcard_img_f() {
            return idcard_img_f;
        }

        public void setIdcard_img_f(String idcard_img_f) {
            this.idcard_img_f = idcard_img_f;
        }

        public String getLive_cityno() {
            return live_cityno;
        }

        public void setLive_cityno(String live_cityno) {
            this.live_cityno = live_cityno;
        }

        public String getLj_county() {
            return lj_county;
        }

        public void setLj_county(String lj_county) {
            this.lj_county = lj_county;
        }

        public String getXj_county() {
            return xj_county;
        }

        public void setXj_county(String xj_county) {
            this.xj_county = xj_county;
        }

        public String getAll_origin() {
            return all_origin;
        }

        public void setAll_origin(String all_origin) {
            this.all_origin = all_origin;
        }

        public String getAll_live() {
            return all_live;
        }

        public void setAll_live(String all_live) {
            this.all_live = all_live;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

    }
}

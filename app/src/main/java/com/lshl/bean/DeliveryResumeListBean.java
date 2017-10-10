package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月23日
 * 时间：11:37
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class DeliveryResumeListBean {


    /**
     * end : 1
     * list : [{"avatar":"5857502a29126.jpg","cityname":"济宁市 嘉祥","education":"请选择您的学历","hx_id":"9a6bd7628d5704849bde783493b132ca","live_cityname":"甘肃省酒泉市","major":"","origin_cityname":"济宁市","origin_county":"嘉祥","phone":"13526485613","r_addtime":"1482463292","r_erid":"5","r_id":"36","r_status":"0","r_uid":"19","realname":"陆小凤"}]
     * pages : 1
     */

    private int end;
    private int pages;
    private List<ListBean> list;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        /**
         * avatar : 5857502a29126.jpg
         * cityname : 济宁市 嘉祥
         * education : 11
         * enterprise : 甘肃鲁商电子商务有限公司
         * hx_id : 9a6bd7628d5704849bde783493b132ca
         * live_cityname : 甘肃省酒泉市
         * major :
         * origin_cityname : 济宁市
         * origin_county : 嘉祥
         * phone : 13526485613
         * r_addtime : 1482480091
         * r_erid : 5
         * r_id : 52
         * r_status : 0
         * r_uid : 19
         * realname : 陆小凤
         * title : ios
         */

        private String avatar;
        private String cityname;
        private String education;
        private String enterprise;
        private String hx_id;
        private String live_cityname;
        private String major;
        private String origin_cityname;
        private String origin_county;
        private String phone;
        private String r_addtime;
        private String r_erid;
        private String r_id;
        private String r_status;
        private String r_uid;
        private String realname;
        private String title;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
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

        public String getLive_cityname() {
            return live_cityname;
        }

        public void setLive_cityname(String live_cityname) {
            this.live_cityname = live_cityname;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
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

        public String getR_addtime() {
            return r_addtime;
        }

        public void setR_addtime(String r_addtime) {
            this.r_addtime = r_addtime;
        }

        public String getR_erid() {
            return r_erid;
        }

        public void setR_erid(String r_erid) {
            this.r_erid = r_erid;
        }

        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
            this.r_id = r_id;
        }

        public String getR_status() {
            return r_status;
        }

        public void setR_status(String r_status) {
            this.r_status = r_status;
        }

        public String getR_uid() {
            return r_uid;
        }

        public void setR_uid(String r_uid) {
            this.r_uid = r_uid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

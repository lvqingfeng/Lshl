package com.lshl.bean;

import java.util.List;

/***
 * 二版的招聘详情实体类
 * Created by Administrator on 2017/3/17.
 */

public class RecruitDetailBean {

    /**
     * status : 1
     * info : [{"en_name":"测试","er_id":"1","er_enid":"79","er_uid":"2","er_title":"Android","er_max_money":"5","er_min_money":"3","er_education":"博士","er_demand":"三年经验","er_info":"安卓开发","er_cityname":"甘肃省兰州市西固区","er_phone":"18366189918","er_nums":"14","er_cityno":"8401","er_addtime":"1489542916"}]
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
         * en_name : 测试
         * er_id : 1
         * er_enid : 79
         * er_uid : 2
         * er_title : Android
         * er_max_money : 5
         * er_min_money : 3
         * er_education : 博士
         * er_demand : 三年经验
         * er_info : 安卓开发
         * er_cityname : 甘肃省兰州市西固区
         * er_phone : 18366189918
         * er_nums : 14
         * er_cityno : 8401
         * er_addtime : 1489542916
         */

        private String en_name;
        private String er_id;
        private String er_enid;
        private String er_uid;
        private String er_title;
        private String er_max_money;
        private String er_min_money;
        private String er_education;
        private String er_demand;
        private String er_info;
        private String er_cityname;
        private String er_phone;
        private String er_nums;
        private String er_cityno;
        private String er_addtime;
        private String hx_id;

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getEr_id() {
            return er_id;
        }

        public void setEr_id(String er_id) {
            this.er_id = er_id;
        }

        public String getEr_enid() {
            return er_enid;
        }

        public void setEr_enid(String er_enid) {
            this.er_enid = er_enid;
        }

        public String getEr_uid() {
            return er_uid;
        }

        public void setEr_uid(String er_uid) {
            this.er_uid = er_uid;
        }

        public String getEr_title() {
            return er_title;
        }

        public void setEr_title(String er_title) {
            this.er_title = er_title;
        }

        public String getEr_max_money() {
            return er_max_money;
        }

        public void setEr_max_money(String er_max_money) {
            this.er_max_money = er_max_money;
        }

        public String getEr_min_money() {
            return er_min_money;
        }

        public void setEr_min_money(String er_min_money) {
            this.er_min_money = er_min_money;
        }

        public String getEr_education() {
            return er_education;
        }

        public void setEr_education(String er_education) {
            this.er_education = er_education;
        }

        public String getEr_demand() {
            return er_demand;
        }

        public void setEr_demand(String er_demand) {
            this.er_demand = er_demand;
        }

        public String getEr_info() {
            return er_info;
        }

        public void setEr_info(String er_info) {
            this.er_info = er_info;
        }

        public String getEr_cityname() {
            return er_cityname;
        }

        public void setEr_cityname(String er_cityname) {
            this.er_cityname = er_cityname;
        }

        public String getEr_phone() {
            return er_phone;
        }

        public void setEr_phone(String er_phone) {
            this.er_phone = er_phone;
        }

        public String getEr_nums() {
            return er_nums;
        }

        public void setEr_nums(String er_nums) {
            this.er_nums = er_nums;
        }

        public String getEr_cityno() {
            return er_cityno;
        }

        public void setEr_cityno(String er_cityno) {
            this.er_cityno = er_cityno;
        }

        public String getEr_addtime() {
            return er_addtime;
        }

        public void setEr_addtime(String er_addtime) {
            this.er_addtime = er_addtime;
        }
    }
}

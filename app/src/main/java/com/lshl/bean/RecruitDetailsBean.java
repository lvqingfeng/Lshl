package com.lshl.bean;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RecruitDetailsBean {

    /**
     * status : 1
     * info : {"en_name":"马来西亚第二季","en_address":"上海市上海市","en_img":"585a57c56b258.png,585a57c56b9fa.png,585a57c56c19b.png","er_id":"2","er_enid":"39","er_uid":"19","er_title":"苹果开发","er_money":"八块","er_education":"博士","er_info":"啥都能干最好","er_addtime":"1482403174","img":"585a57c56b258.png","collection":2,"delivery":1}
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
         * en_name : 马来西亚第二季
         * en_address : 上海市上海市
         * en_img : 585a57c56b258.png,585a57c56b9fa.png,585a57c56c19b.png
         * er_id : 2
         * er_enid : 39
         * er_uid : 19
         * er_title : 苹果开发
         * er_money : 八块
         * er_education : 博士
         * er_info : 啥都能干最好
         * er_addtime : 1482403174
         * img : ["585a57c56b258.png","585a57c56b9fa.png","585a57c56c19b.png"]
         * collection : 2
         * delivery : 1
         */

        private String en_name;
        private String en_address;
        private String en_img;
        private String er_id;
        private String er_enid;
        private String er_uid;
        private String er_title;
        private String er_money;
        private String er_education;
        private String er_info;
        private String er_addtime;
        private String img;
        private int collection;
        private int delivery;

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getEn_address() {
            return en_address;
        }

        public void setEn_address(String en_address) {
            this.en_address = en_address;
        }

        public String getEn_img() {
            return en_img;
        }

        public void setEn_img(String en_img) {
            this.en_img = en_img;
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

        public String getEr_money() {
            return er_money;
        }

        public void setEr_money(String er_money) {
            this.er_money = er_money;
        }

        public String getEr_education() {
            return er_education;
        }

        public void setEr_education(String er_education) {
            this.er_education = er_education;
        }

        public String getEr_info() {
            return er_info;
        }

        public void setEr_info(String er_info) {
            this.er_info = er_info;
        }

        public String getEr_addtime() {
            return er_addtime;
        }

        public void setEr_addtime(String er_addtime) {
            this.er_addtime = er_addtime;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

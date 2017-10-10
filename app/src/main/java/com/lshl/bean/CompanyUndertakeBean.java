package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class CompanyUndertakeBean {

    /**
     * status : 1
     * info : {"en":{"en_id":"79","en_img":"58cb35b118d8160874.png","en_name":"测试","en_info":"测试"},"good":[{"gd_id":"257","gd_goodname":"测试","gd_original_price":"147.00","gd_special_offer":"23.00","gd_img":"58cb59e7469ed76410.png","gd_uid":"16","gd_cityname":"内蒙古呼和浩特市"}],"er":[{"en_name":"测试","en_img":"58cb35b118d8160874.png","er_id":"1","er_title":"Android","er_max_money":"5","er_min_money":"3","er_addtime":"1489542916","er_cityname":"甘肃省兰州市西固区"}]}
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
         * en : {"en_id":"79","en_img":"58cb35b118d8160874.png","en_name":"测试","en_info":"测试"}
         * good : [{"gd_id":"257","gd_goodname":"测试","gd_original_price":"147.00","gd_special_offer":"23.00","gd_img":"58cb59e7469ed76410.png","gd_uid":"16","gd_cityname":"内蒙古呼和浩特市"}]
         * er : [{"en_name":"测试","en_img":"58cb35b118d8160874.png","er_id":"1","er_title":"Android","er_max_money":"5","er_min_money":"3","er_addtime":"1489542916","er_cityname":"甘肃省兰州市西固区"}]
         */

        private EnBean en;
        private List<GoodBean> good;
        private List<ErBean> er;

        public EnBean getEn() {
            return en;
        }

        public void setEn(EnBean en) {
            this.en = en;
        }

        public List<GoodBean> getGood() {
            return good;
        }

        public void setGood(List<GoodBean> good) {
            this.good = good;
        }

        public List<ErBean> getEr() {
            return er;
        }

        public void setEr(List<ErBean> er) {
            this.er = er;
        }

        public static class EnBean {
            /**
             * en_id : 79
             * en_img : 58cb35b118d8160874.png
             * en_name : 测试
             * en_info : 测试
             */

            private String en_id;
            private String en_img;
            private String en_name;
            private String en_info;

            public String getEn_id() {
                return en_id;
            }

            public void setEn_id(String en_id) {
                this.en_id = en_id;
            }

            public String getEn_img() {
                return en_img;
            }

            public void setEn_img(String en_img) {
                this.en_img = en_img;
            }

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public String getEn_info() {
                return en_info;
            }

            public void setEn_info(String en_info) {
                this.en_info = en_info;
            }
        }

        public static class GoodBean {
            /**
             * gd_id : 257
             * gd_goodname : 测试
             * gd_original_price : 147.00
             * gd_special_offer : 23.00
             * gd_img : 58cb59e7469ed76410.png
             * gd_uid : 16
             * gd_cityname : 内蒙古呼和浩特市
             */

            private String gd_id;
            private String gd_goodname;
            private String gd_original_price;
            private String gd_special_offer;
            private String gd_img;
            private String gd_uid;
            private String gd_cityname;

            public String getGd_id() {
                return gd_id;
            }

            public void setGd_id(String gd_id) {
                this.gd_id = gd_id;
            }

            public String getGd_goodname() {
                return gd_goodname;
            }

            public void setGd_goodname(String gd_goodname) {
                this.gd_goodname = gd_goodname;
            }

            public String getGd_original_price() {
                return gd_original_price;
            }

            public void setGd_original_price(String gd_original_price) {
                this.gd_original_price = gd_original_price;
            }

            public String getGd_special_offer() {
                return gd_special_offer;
            }

            public void setGd_special_offer(String gd_special_offer) {
                this.gd_special_offer = gd_special_offer;
            }

            public String getGd_img() {
                return gd_img;
            }

            public void setGd_img(String gd_img) {
                this.gd_img = gd_img;
            }

            public String getGd_uid() {
                return gd_uid;
            }

            public void setGd_uid(String gd_uid) {
                this.gd_uid = gd_uid;
            }

            public String getGd_cityname() {
                return gd_cityname;
            }

            public void setGd_cityname(String gd_cityname) {
                this.gd_cityname = gd_cityname;
            }
        }

        public static class ErBean {
            /**
             * en_name : 测试
             * en_img : 58cb35b118d8160874.png
             * er_id : 1
             * er_title : Android
             * er_max_money : 5
             * er_min_money : 3
             * er_addtime : 1489542916
             * er_cityname : 甘肃省兰州市西固区
             */

            private String en_name;
            private String en_img;
            private String er_id;
            private String er_title;
            private String er_max_money;
            private String er_min_money;
            private String er_addtime;
            private String er_cityname;

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
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

            public String getEr_addtime() {
                return er_addtime;
            }

            public void setEr_addtime(String er_addtime) {
                this.er_addtime = er_addtime;
            }

            public String getEr_cityname() {
                return er_cityname;
            }

            public void setEr_cityname(String er_cityname) {
                this.er_cityname = er_cityname;
            }
        }
    }
}

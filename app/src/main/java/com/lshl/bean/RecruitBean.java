package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RecruitBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"4","er_enid":"79","er_title":"测试安卓","er_max_money":"20","er_min_money":"10","er_education":"博士","er_addtime":"1489737339","er_uid":"16","er_cityname":"朔州市"},{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"3","er_enid":"79","er_title":"测试测试","er_max_money":"10","er_min_money":"5","er_education":"硕士","er_addtime":"1489736574","er_uid":"16","er_cityname":"呼和浩特市"},{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"1","er_enid":"79","er_title":"Android","er_max_money":"5","er_min_money":"3","er_education":"博士","er_addtime":"1489542916","er_uid":"2","er_cityname":"甘肃省兰州市西固区"}]}
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
         * pages : 1
         * end : 1
         * list : [{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"4","er_enid":"79","er_title":"测试安卓","er_max_money":"20","er_min_money":"10","er_education":"博士","er_addtime":"1489737339","er_uid":"16","er_cityname":"朔州市"},{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"3","er_enid":"79","er_title":"测试测试","er_max_money":"10","er_min_money":"5","er_education":"硕士","er_addtime":"1489736574","er_uid":"16","er_cityname":"呼和浩特市"},{"en_name":"测试","en_img":"/Data/Uploads/enterprises/58cb35b118d8160874.png","er_id":"1","er_enid":"79","er_title":"Android","er_max_money":"5","er_min_money":"3","er_education":"博士","er_addtime":"1489542916","er_uid":"2","er_cityname":"甘肃省兰州市西固区"}]
         */

        private int pages;
        private int end;
        private List<ListBean> list;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * en_name : 测试
             * en_img : /Data/Uploads/enterprises/58cb35b118d8160874.png
             * er_id : 4
             * er_enid : 79
             * er_title : 测试安卓
             * er_max_money : 20
             * er_min_money : 10
             * er_education : 博士
             * er_addtime : 1489737339
             * er_uid : 16
             * er_cityname : 朔州市
             */

            private String en_name;
            private String en_img;
            private String er_id;
            private String er_enid;
            private String er_title;
            private String er_max_money;
            private String er_min_money;
            private String er_education;
            private String er_addtime;
            private String er_uid;
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

            public String getEr_enid() {
                return er_enid;
            }

            public void setEr_enid(String er_enid) {
                this.er_enid = er_enid;
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

            public String getEr_addtime() {
                return er_addtime;
            }

            public void setEr_addtime(String er_addtime) {
                this.er_addtime = er_addtime;
            }

            public String getEr_uid() {
                return er_uid;
            }

            public void setEr_uid(String er_uid) {
                this.er_uid = er_uid;
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

package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月17日
 * 时间：22:40
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SHTalkAboutListBean {

    /**
     * end : 0
     * key : 0
     * list : [{"avatar":"3.jpg","bct_addtime":"1474455521","bct_bid":"1","bct_id":"1","bct_info":"提问","bct_status":"1","bct_uid":"3","cityname":"甘肃兰州","hx_id":"2fcb13c32827c61a5d040c82e03eab28","live_cityname":"甘肃兰州","realname":"魁大圣","uid":"3"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479288005","bct_bid":"1","bct_contact":"问题已解决","bct_id":"12","bct_info":"跳跳糖吃","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479288037","bct_bid":"1","bct_contact":"你的一件很宝贵","bct_id":"13","bct_info":"我们","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479288370","bct_bid":"1","bct_contact":"我们会处理","bct_id":"14","bct_info":"我们","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479307489","bct_bid":"1","bct_id":"20","bct_info":"","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479343881","bct_bid":"1","bct_id":"21","bct_info":"Sssddddddddd","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"bct_addtime":"1479391081","bct_bid":"1","bct_id":"22","bct_info":"将继续","bct_status":"1","bct_uid":"55","cityname":"甘肃兰州","hx_id":"ea9fd357fb239679204e83630ad19acf","live_cityname":"甘肃兰州","realname":"sdfgh","uid":"55"},{"bct_addtime":"1479391099","bct_bid":"1","bct_id":"23","bct_info":"糊涂 ","bct_status":"1","bct_uid":"55","cityname":"甘肃兰州","hx_id":"ea9fd357fb239679204e83630ad19acf","live_cityname":"甘肃兰州","realname":"sdfgh","uid":"55"},{"avatar":"57fa21d102ce3.png","bct_addtime":"1479391175","bct_bid":"1","bct_id":"24","bct_info":"是我们自己也许的愿是一","bct_status":"1","bct_uid":"7","cityname":"甘肃兰州","hx_id":"0808a5ad58d717da49953d243a022575","live_cityname":"甘肃兰州","realname":"王大人","uid":"7"},{"bct_addtime":"1479391413","bct_bid":"1","bct_id":"25","bct_info":"你大爷","bct_status":"1","bct_uid":"55","cityname":"甘肃兰州","hx_id":"ea9fd357fb239679204e83630ad19acf","live_cityname":"甘肃兰州","realname":"sdfgh","uid":"55"}]
     * pages : 2
     */

    private int end;
    private int key;
    private int pages;
    private List<ListBean> list;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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
         * avatar : 3.jpg
         * bct_addtime : 1474455521
         * bct_bid : 1
         * bct_id : 1
         * bct_info : 提问
         * bct_status : 1
         * bct_uid : 3
         * cityname : 甘肃兰州
         * hx_id : 2fcb13c32827c61a5d040c82e03eab28
         * live_cityname : 甘肃兰州
         * realname : 魁大圣
         * uid : 3
         * bct_contact : 问题已解决
         */

        private String avatar;
        private String bct_addtime;
        private String bct_bid;
        private String bct_id;
        private String bct_info;
        private String bct_status;
        private String bct_uid;
        private String cityname;
        private String hx_id;
        private String live_cityname;
        private String realname;
        private String uid;
        private String bct_contact;
        private int grade;

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBct_addtime() {
            return bct_addtime;
        }

        public void setBct_addtime(String bct_addtime) {
            this.bct_addtime = bct_addtime;
        }

        public String getBct_bid() {
            return bct_bid;
        }

        public void setBct_bid(String bct_bid) {
            this.bct_bid = bct_bid;
        }

        public String getBct_id() {
            return bct_id;
        }

        public void setBct_id(String bct_id) {
            this.bct_id = bct_id;
        }

        public String getBct_info() {
            return bct_info;
        }

        public void setBct_info(String bct_info) {
            this.bct_info = bct_info;
        }

        public String getBct_status() {
            return bct_status;
        }

        public void setBct_status(String bct_status) {
            this.bct_status = bct_status;
        }

        public String getBct_uid() {
            return bct_uid;
        }

        public void setBct_uid(String bct_uid) {
            this.bct_uid = bct_uid;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBct_contact() {
            return bct_contact;
        }

        public void setBct_contact(String bct_contact) {
            this.bct_contact = bct_contact;
        }
    }
}

package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class CommerceMemberBean implements Serializable {

    /**
     * status : 1
     * info : {"h":[{"realname":"魁大圣","avatar":"582d510ed6317.jpg","uid":"3","live_cityname":"甘肃兰州","bm_id":"8","bm_bid":"1","bm_position":"99","jobs":"会长","cityname":"甘肃兰州"}],"p":[{"realname":"王大人","avatar":"57fa21d102ce3.png","uid":"7","live_cityname":"甘肃兰州","bm_id":"20","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"王麻子","avatar":"8.jpg","uid":"10","live_cityname":"甘肃兰州","bm_id":"26","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"赵倩","avatar":"pay_suc.jpg","uid":"12","live_cityname":"甘肃兰州","bm_id":"27","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"孙俪","avatar":"8.jpg","uid":"13","live_cityname":"甘肃兰州","bm_id":"28","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"郑旺","avatar":"8.jpg","uid":"18","live_cityname":"甘肃兰州","bm_id":"29","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"杨少侠","avatar":null,"uid":"23","live_cityname":"甘肃兰州","bm_id":"30","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"局长","avatar":"5804a00a6cea0.png","uid":"41","live_cityname":"甘肃兰州","bm_id":"31","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"},{"realname":"王小川","avatar":"582470d3beaf3.png","uid":"43","live_cityname":"甘肃兰州","bm_id":"32","bm_bid":"1","bm_position":"1","jobs":"会员","cityname":"甘肃兰州"}]}
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
         * realname : 魁大圣
         * avatar : 582d510ed6317.jpg
         * uid : 3
         * live_cityname : 甘肃兰州
         * bm_id : 8
         * bm_bid : 1
         * bm_position : 99
         * jobs : 会长
         * cityname : 甘肃兰州
         */

        private List<HBean> h;
        /**
         * realname : 王大人
         * avatar : 57fa21d102ce3.png
         * uid : 7
         * live_cityname : 甘肃兰州
         * bm_id : 20
         * bm_bid : 1
         * bm_position : 1
         * jobs : 会员
         * cityname : 甘肃兰州
         */

        private List<PBean> p;

        public List<HBean> getH() {
            return h;
        }

        public void setH(List<HBean> h) {
            this.h = h;
        }

        public List<PBean> getP() {
            return p;
        }

        public void setP(List<PBean> p) {
            this.p = p;
        }

        public static class HBean {
            private String realname;
            private String avatar;
            private String uid;
            private String live_cityname;
            private String bm_id;
            private String bm_bid;
            private String bm_position;
            private String jobs;
            private String cityname;

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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getLive_cityname() {
                return live_cityname;
            }

            public void setLive_cityname(String live_cityname) {
                this.live_cityname = live_cityname;
            }

            public String getBm_id() {
                return bm_id;
            }

            public void setBm_id(String bm_id) {
                this.bm_id = bm_id;
            }

            public String getBm_bid() {
                return bm_bid;
            }

            public void setBm_bid(String bm_bid) {
                this.bm_bid = bm_bid;
            }

            public String getBm_position() {
                return bm_position;
            }

            public void setBm_position(String bm_position) {
                this.bm_position = bm_position;
            }

            public String getJobs() {
                return jobs;
            }

            public void setJobs(String jobs) {
                this.jobs = jobs;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }
        }

        public static class PBean {
            private String realname;
            private String avatar;
            private String uid;
            private String live_cityname;
            private String bm_id;
            private String bm_bid;
            private String bm_position;
            private String jobs;
            private String cityname;

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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getLive_cityname() {
                return live_cityname;
            }

            public void setLive_cityname(String live_cityname) {
                this.live_cityname = live_cityname;
            }

            public String getBm_id() {
                return bm_id;
            }

            public void setBm_id(String bm_id) {
                this.bm_id = bm_id;
            }

            public String getBm_bid() {
                return bm_bid;
            }

            public void setBm_bid(String bm_bid) {
                this.bm_bid = bm_bid;
            }

            public String getBm_position() {
                return bm_position;
            }

            public void setBm_position(String bm_position) {
                this.bm_position = bm_position;
            }

            public String getJobs() {
                return jobs;
            }

            public void setJobs(String jobs) {
                this.jobs = jobs;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }
        }
    }
}

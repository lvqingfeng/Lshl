package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class BannerBean {

    /**
     * status : 1
     * info : [{"bn_id":"19","bn_mid":"17","bn_img":"57425880ca31c7292.jpeg","bn_url":"koubei","bn_urlid":"8","bn_sort":"3"},{"bn_id":"12","bn_mid":"17","bn_img":"198075865d8cb12e43.jpeg","bn_url":"0","bn_urlid":null,"bn_sort":"0"},{"bn_id":"18","bn_mid":"17","bn_img":"291385880c5c9e9278.jpeg","bn_url":"zimaoqu","bn_urlid":"8","bn_sort":"0"}]
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
         * bn_id : 19
         * bn_mid : 17
         * bn_img : 57425880ca31c7292.jpeg
         * bn_url : koubei
         * bn_urlid : 8
         * bn_sort : 3
         */

        private String bn_id;
        private String bn_mid;
        private String bn_img;
        private String bn_url;
        private String bn_urlid;
        private String bn_sort;

        public String getBn_id() {
            return bn_id;
        }

        public void setBn_id(String bn_id) {
            this.bn_id = bn_id;
        }

        public String getBn_mid() {
            return bn_mid;
        }

        public void setBn_mid(String bn_mid) {
            this.bn_mid = bn_mid;
        }

        public String getBn_img() {
            return bn_img;
        }

        public void setBn_img(String bn_img) {
            this.bn_img = bn_img;
        }

        public String getBn_url() {
            return bn_url;
        }

        public void setBn_url(String bn_url) {
            this.bn_url = bn_url;
        }

        public String getBn_urlid() {
            return bn_urlid;
        }

        public void setBn_urlid(String bn_urlid) {
            this.bn_urlid = bn_urlid;
        }

        public String getBn_sort() {
            return bn_sort;
        }

        public void setBn_sort(String bn_sort) {
            this.bn_sort = bn_sort;
        }
    }
}

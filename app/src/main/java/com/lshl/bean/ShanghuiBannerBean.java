package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class ShanghuiBannerBean {

    /**
     * status : 1
     * info : [{"bub_id":"2","bub_uid":"1","bub_img":"301335880ba0c6f6c3.jpeg","bub_url":null,"bub_sort":"4","bud_addtime":"1484831244"},{"bub_id":"3","bub_uid":"1","bub_img":"250985880bccf41a57.jpeg","bub_url":"2","bub_sort":"2","bud_addtime":"1484831951"},{"bub_id":"1","bub_uid":"1","bub_img":"162965865d1eff3f56.jpeg","bub_url":null,"bub_sort":"0","bud_addtime":"1483067887"}]
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
         * bub_id : 2
         * bub_uid : 1
         * bub_img : 301335880ba0c6f6c3.jpeg
         * bub_url : null
         * bub_sort : 4
         * bud_addtime : 1484831244
         */

        private String bub_id;
        private String bub_uid;
        private String bub_img;
        private String bub_url;
        private String bub_sort;
        private String bud_addtime;

        public String getBub_id() {
            return bub_id;
        }

        public void setBub_id(String bub_id) {
            this.bub_id = bub_id;
        }

        public String getBub_uid() {
            return bub_uid;
        }

        public void setBub_uid(String bub_uid) {
            this.bub_uid = bub_uid;
        }

        public String getBub_img() {
            return bub_img;
        }

        public void setBub_img(String bub_img) {
            this.bub_img = bub_img;
        }

        public String getBub_url() {
            return bub_url;
        }

        public void setBub_url(String bub_url) {
            this.bub_url = bub_url;
        }

        public String getBub_sort() {
            return bub_sort;
        }

        public void setBub_sort(String bub_sort) {
            this.bub_sort = bub_sort;
        }

        public String getBud_addtime() {
            return bud_addtime;
        }

        public void setBud_addtime(String bud_addtime) {
            this.bud_addtime = bud_addtime;
        }
    }
}

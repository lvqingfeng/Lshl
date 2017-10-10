package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */

public class EnterpeiseBean {

    /**
     * status : 1
     * info : [{"en_id":"23","en_name":"鲁豫园区","en_img":"58578283aed93.png,58578283af164.png,58578283af535.png","en_addtime":"1482130089","en_status":"2","img":"58578283aed93.png"},{"en_id":"20","en_name":"就美女小游戏","en_img":"585638e3b46aa.png,585638e3b4a7a.png,585638e3b4e4b.png","en_addtime":"1482047293","en_status":"2","img":"585638e3b46aa.png"},{"en_id":"19","en_name":"魔高一丈篇","en_img":"5856369f652cf.png,5856369f656a0.png,5856369f65a70.png","en_addtime":"1482045174","en_status":"2","img":"5856369f652cf.png"},{"en_id":"18","en_name":"罗马尼亚","en_img":"5854bf6c37984.png","en_addtime":"1481949064","en_status":"2","img":"5854bf6c37984.png"},{"en_id":"17","en_name":"后来慢慢在","en_img":"5854bdfeb0c5a.png,5854bdfeb17cc.png,5854bdfeb1b9c.png","en_addtime":"1481948707","en_status":"2","img":"5854bdfeb0c5a.png"},{"en_id":"16","en_name":"哈哈哈哈哈","en_img":"5854bbe846a16.png,5854bbe846de7.png,5854bbe8471b7.png","en_addtime":null,"en_status":"3","img":"5854bbe846a16.png"},{"en_id":"15","en_name":"我们俩国家民族","en_img":"58549aae1dea4.png,58549aae1e275.png,58549aae1e645.png,58549aae1ea16.png,58549aae1ede6.png,58549aae1f1b7.png","en_addtime":null,"en_status":"3","img":"58549aae1dea4.png"},{"en_id":"13","en_name":"克拉玛依","en_img":"58549a5347638.png,58549a5347a09.png,58549a5347dd9.png","en_addtime":"1481939581","en_status":"1","img":"58549a5347638.png"}]
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
         * en_id : 23
         * en_name : 鲁豫园区
         * en_img : 58578283aed93.png,58578283af164.png,58578283af535.png
         * en_addtime : 1482130089
         * en_status : 2
         * img : 58578283aed93.png
         */

        private String en_id;
        private String en_name;
        private String en_img;
        private String en_addtime;
        private String en_status;
        private String img;

        public String getEn_id() {
            return en_id;
        }

        public void setEn_id(String en_id) {
            this.en_id = en_id;
        }

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

        public String getEn_addtime() {
            return en_addtime;
        }

        public void setEn_addtime(String en_addtime) {
            this.en_addtime = en_addtime;
        }

        public String getEn_status() {
            return en_status;
        }

        public void setEn_status(String en_status) {
            this.en_status = en_status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

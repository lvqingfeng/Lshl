package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CityQiyeBean {


    /**
     * end : 1
     * list : [{"en_address":"甘肃省兰州市","en_id":"1","en_img":"3.jpg","en_name":"兰州市企业","en_status":"1","en_uid":"3","img":"3.jpg"}]
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
         * en_address : 甘肃省兰州市
         * en_id : 1
         * en_img : 3.jpg
         * en_name : 兰州市企业
         * en_status : 1
         * en_uid : 3
         * img : 3.jpg
         */

        private String en_address;
        private String en_id;
        private String en_img;
        private String en_name;
        private String en_status;
        private String en_uid;
        private String img;
        private String realname;
        private String en_addtime;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getEn_addtime() {
            return en_addtime;
        }

        public void setEn_addtime(String en_addtime) {
            this.en_addtime = en_addtime;
        }

        public String getEn_address() {
            return en_address;
        }

        public void setEn_address(String en_address) {
            this.en_address = en_address;
        }

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

        public String getEn_status() {
            return en_status;
        }

        public void setEn_status(String en_status) {
            this.en_status = en_status;
        }

        public String getEn_uid() {
            return en_uid;
        }

        public void setEn_uid(String en_uid) {
            this.en_uid = en_uid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

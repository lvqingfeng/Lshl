package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */

public class LookHelpListBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"fh_id":"2","fh_title":"我要盖高楼大厦","fh_status":"1","fh_addtime":"1483954110","fh_img":null,"fh_cityname":"北京","realname":"孔夫子","image":"img"},{"fh_id":"1","fh_title":"测试找帮手","fh_status":"1","fh_addtime":"1483954049","fh_img":null,"fh_cityname":"甘肃兰州","realname":"孔夫子","image":"img"}]}
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
         * list : [{"fh_id":"2","fh_title":"我要盖高楼大厦","fh_status":"1","fh_addtime":"1483954110","fh_img":null,"fh_cityname":"北京","realname":"孔夫子","image":"img"},{"fh_id":"1","fh_title":"测试找帮手","fh_status":"1","fh_addtime":"1483954049","fh_img":null,"fh_cityname":"甘肃兰州","realname":"孔夫子","image":"img"}]
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
             * fh_id : 2
             * fh_title : 我要盖高楼大厦
             * fh_status : 1
             * fh_addtime : 1483954110
             * fh_img : null
             * fh_cityname : 北京
             * realname : 孔夫子
             * image : img
             */

            private String fh_id;
            private String fh_title;
            private String fh_status;
            private String fh_addtime;
            private Object fh_img;
            private String fh_cityname;
            private String realname;
            private String image;

            public String getFh_id() {
                return fh_id;
            }

            public void setFh_id(String fh_id) {
                this.fh_id = fh_id;
            }

            public String getFh_title() {
                return fh_title;
            }

            public void setFh_title(String fh_title) {
                this.fh_title = fh_title;
            }

            public String getFh_status() {
                return fh_status;
            }

            public void setFh_status(String fh_status) {
                this.fh_status = fh_status;
            }

            public String getFh_addtime() {
                return fh_addtime;
            }

            public void setFh_addtime(String fh_addtime) {
                this.fh_addtime = fh_addtime;
            }

            public Object getFh_img() {
                return fh_img;
            }

            public void setFh_img(Object fh_img) {
                this.fh_img = fh_img;
            }

            public String getFh_cityname() {
                return fh_cityname;
            }

            public void setFh_cityname(String fh_cityname) {
                this.fh_cityname = fh_cityname;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}

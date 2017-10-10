package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */

public class ServiceStationBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"ss_id":"3","ss_name":"济南服务站","ss_cityname":"山东济南","ss_img":"3.jpg","ss_addtime":"1469581377"},{"ss_id":"1","ss_name":"兰州服务站","ss_cityname":"甘肃兰州","ss_img":"3.jpg","ss_addtime":"1469581377"},{"ss_id":"2","ss_name":"西安服务站","ss_cityname":"陕西西安","ss_img":"3.jpg","ss_addtime":"1469581377"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"ss_id":"3","ss_name":"济南服务站","ss_cityname":"山东济南","ss_img":"3.jpg","ss_addtime":"1469581377"},{"ss_id":"1","ss_name":"兰州服务站","ss_cityname":"甘肃兰州","ss_img":"3.jpg","ss_addtime":"1469581377"},{"ss_id":"2","ss_name":"西安服务站","ss_cityname":"陕西西安","ss_img":"3.jpg","ss_addtime":"1469581377"}]
     */

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
        private int pages;
        private int end;
        /**
         * ss_id : 3
         * ss_name : 济南服务站
         * ss_cityname : 山东济南
         * ss_img : 3.jpg
         * ss_addtime : 1469581377
         */

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
            private String ss_id;
            private String ss_name;
            private String ss_cityname;
            private String ss_img;
            private String ss_addtime;

            public String getSs_id() {
                return ss_id;
            }

            public void setSs_id(String ss_id) {
                this.ss_id = ss_id;
            }

            public String getSs_name() {
                return ss_name;
            }

            public void setSs_name(String ss_name) {
                this.ss_name = ss_name;
            }

            public String getSs_cityname() {
                return ss_cityname;
            }

            public void setSs_cityname(String ss_cityname) {
                this.ss_cityname = ss_cityname;
            }

            public String getSs_img() {
                return ss_img;
            }

            public void setSs_img(String ss_img) {
                this.ss_img = ss_img;
            }

            public String getSs_addtime() {
                return ss_addtime;
            }

            public void setSs_addtime(String ss_addtime) {
                this.ss_addtime = ss_addtime;
            }
        }
    }
}

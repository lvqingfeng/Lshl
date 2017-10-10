package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MyCollectionBean  {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"cg_id":"9","cg_goodsid":"27","cg_uid":"3","cg_addtime":"1477908646","gd_type":"12","gd_goodname":"哈哈哈","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","gd_img1":"12102580200ab42b33.jpeg","gd_uid":"7","gd_orders":"34"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"cg_id":"9","cg_goodsid":"27","cg_uid":"3","cg_addtime":"1477908646","gd_type":"12","gd_goodname":"哈哈哈","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","gd_img1":"12102580200ab42b33.jpeg","gd_uid":"7","gd_orders":"34"}]
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
         * cg_id : 9
         * cg_goodsid : 27
         * cg_uid : 3
         * cg_addtime : 1477908646
         * gd_type : 12
         * gd_goodname : 哈哈哈
         * gd_selling : 家具啊
         * gd_original_price : 100.00
         * gd_special_offer : 100.00
         * gd_img1 : 12102580200ab42b33.jpeg
         * gd_uid : 7
         * gd_orders : 34
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

            private String cg_id;
            private String cg_goodsid;
            private String cg_uid;
            private String cg_addtime;
            private String gd_type;
            private String gd_goodname;
            private String gd_selling;
            private String gd_original_price;
            private String gd_special_offer;
            private String gd_uid;
            private String gd_orders;
            /**
             * gd_addtime : 1478865198
             * gd_cityname : 江西省 南昌市
             * gd_id : 43
             * gd_img : 5825b12e7919a.png,5825b12e7956b.png,5825b12e7993c.png,5825b12e7a0dd.png,5825b12e7a4ad.png
             * gd_sort : 0
             * gd_sort_time : 0
             * img : 5825b12e7919a.png
             * top : 0
             */

            private String gd_addtime;
            private String gd_cityname;
            private String gd_id;
            private String gd_img;
            private String gd_sort;
            private String gd_sort_time;
            private String img;
            private int top;

            public String getCg_id() {
                return cg_id;
            }

            public void setCg_id(String cg_id) {
                this.cg_id = cg_id;
            }

            public String getCg_goodsid() {
                return cg_goodsid;
            }

            public void setCg_goodsid(String cg_goodsid) {
                this.cg_goodsid = cg_goodsid;
            }

            public String getCg_uid() {
                return cg_uid;
            }

            public void setCg_uid(String cg_uid) {
                this.cg_uid = cg_uid;
            }

            public String getCg_addtime() {
                return cg_addtime;
            }

            public void setCg_addtime(String cg_addtime) {
                this.cg_addtime = cg_addtime;
            }

            public String getGd_type() {
                return gd_type;
            }

            public void setGd_type(String gd_type) {
                this.gd_type = gd_type;
            }

            public String getGd_goodname() {
                return gd_goodname;
            }

            public void setGd_goodname(String gd_goodname) {
                this.gd_goodname = gd_goodname;
            }

            public String getGd_selling() {
                return gd_selling;
            }

            public void setGd_selling(String gd_selling) {
                this.gd_selling = gd_selling;
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

            public String getGd_uid() {
                return gd_uid;
            }

            public void setGd_uid(String gd_uid) {
                this.gd_uid = gd_uid;
            }

            public String getGd_orders() {
                return gd_orders;
            }

            public void setGd_orders(String gd_orders) {
                this.gd_orders = gd_orders;
            }

            public String getGd_addtime() {
                return gd_addtime;
            }

            public void setGd_addtime(String gd_addtime) {
                this.gd_addtime = gd_addtime;
            }

            public String getGd_cityname() {
                return gd_cityname;
            }

            public void setGd_cityname(String gd_cityname) {
                this.gd_cityname = gd_cityname;
            }

            public String getGd_id() {
                return gd_id;
            }

            public void setGd_id(String gd_id) {
                this.gd_id = gd_id;
            }

            public String getGd_img() {
                return gd_img;
            }

            public void setGd_img(String gd_img) {
                this.gd_img = gd_img;
            }

            public String getGd_sort() {
                return gd_sort;
            }

            public void setGd_sort(String gd_sort) {
                this.gd_sort = gd_sort;
            }

            public String getGd_sort_time() {
                return gd_sort_time;
            }

            public void setGd_sort_time(String gd_sort_time) {
                this.gd_sort_time = gd_sort_time;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }
        }
    }
}

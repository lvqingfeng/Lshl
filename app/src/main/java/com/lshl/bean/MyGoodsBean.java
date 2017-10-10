package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class MyGoodsBean implements Serializable {


    /**
     * end : 1
     * list : [{"gd_addtime":"1480739045","gd_cityname":"山西省太原市","gd_click":"1","gd_goodname":"大美女，对老乡的福利，大家的福利，快来抢购吧","gd_hot":0,"gd_id":"2","gd_img":"584248e535a7c.png,584248e53621d.png,584248e5369bf.png,584248e537530.png","gd_original_price":"999999.99","gd_selling":"","gd_sort":"0","gd_sort_time":"0","gd_special_offer":"1.00","gd_status":"1","gd_type":"4","gd_uid":"11","img":"584248e535a7c.png","top":0}]
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
         * gd_addtime : 1480739045
         * gd_cityname : 山西省太原市
         * gd_click : 1
         * gd_goodname : 大美女，对老乡的福利，大家的福利，快来抢购吧
         * gd_hot : 0
         * gd_id : 2
         * gd_img : 584248e535a7c.png,584248e53621d.png,584248e5369bf.png,584248e537530.png
         * gd_original_price : 999999.99
         * gd_selling :
         * gd_sort : 0
         * gd_sort_time : 0
         * gd_special_offer : 1.00
         * gd_status : 1
         * gd_type : 4
         * gd_uid : 11
         * img : 584248e535a7c.png
         * top : 0
         */

        private String gd_addtime;
        private String gd_cityname;
        private String gd_click;
        private String gd_goodname;
        private int gd_hot;
        private String gd_id;
        private String gd_img;
        private String gd_original_price;
        private String gd_selling;
        private String gd_sort;
        private String gd_sort_time;
        private String gd_special_offer;
        private String gd_status;
        private String gd_type;
        private String gd_uid;
        private String img;
        private String gd_nums;
        private int top;

        public String getGd_nums() {
            return gd_nums;
        }

        public void setGd_nums(String gd_nums) {
            this.gd_nums = gd_nums;
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

        public String getGd_click() {
            return gd_click;
        }

        public void setGd_click(String gd_click) {
            this.gd_click = gd_click;
        }

        public String getGd_goodname() {
            return gd_goodname;
        }

        public void setGd_goodname(String gd_goodname) {
            this.gd_goodname = gd_goodname;
        }

        public int getGd_hot() {
            return gd_hot;
        }

        public void setGd_hot(int gd_hot) {
            this.gd_hot = gd_hot;
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

        public String getGd_original_price() {
            return gd_original_price;
        }

        public void setGd_original_price(String gd_original_price) {
            this.gd_original_price = gd_original_price;
        }

        public String getGd_selling() {
            return gd_selling;
        }

        public void setGd_selling(String gd_selling) {
            this.gd_selling = gd_selling;
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

        public String getGd_special_offer() {
            return gd_special_offer;
        }

        public void setGd_special_offer(String gd_special_offer) {
            this.gd_special_offer = gd_special_offer;
        }

        public String getGd_status() {
            return gd_status;
        }

        public void setGd_status(String gd_status) {
            this.gd_status = gd_status;
        }

        public String getGd_type() {
            return gd_type;
        }

        public void setGd_type(String gd_type) {
            this.gd_type = gd_type;
        }

        public String getGd_uid() {
            return gd_uid;
        }

        public void setGd_uid(String gd_uid) {
            this.gd_uid = gd_uid;
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

package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */
public class ShangHuiBean {


    /**
     * end : 1
     * list : [{"add":1,"bus_business_addtime":"1469581377","bus_business_cityname":"兰州市","bus_business_img":"234657faff5db7980.jpeg","bus_business_logo":"3.jpg","bus_business_name":"甘肃省山东商会","bus_business_number":"199","bus_id":"1","img":"234657faff5db7980.jpeg"},{"add":1,"bus_business_addtime":"1470403484","bus_business_cityname":"兰州市","bus_business_img":"243657c686a05c238.jpeg","bus_business_logo":"4.jpg","bus_business_name":"兰州市山东商会","bus_business_number":"30","bus_id":"3","img":"243657c686a05c238.jpeg"},{"add":1,"bus_business_addtime":"1479226500","bus_business_cityname":"兰州市","bus_business_name":"测试测试","bus_business_number":"0","bus_id":"16","img":""}]
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
         * add : 1
         * bus_business_addtime : 1469581377
         * bus_business_cityname : 兰州市
         * bus_business_img : 234657faff5db7980.jpeg
         * bus_business_logo : 3.jpg
         * bus_business_name : 甘肃省山东商会
         * bus_business_number : 199
         * bus_id : 1
         * img : 234657faff5db7980.jpeg
         */

        private int add;
        private String bus_business_addtime;
        private String bus_business_cityname;
        private String bus_business_img;
        private String bus_business_logo;
        private String bus_business_name;
        private String bus_business_number;
        private String bus_id;

        public int getAdd() {
            return add;
        }

        public void setAdd(int add) {
            this.add = add;
        }

        public String getBus_business_addtime() {
            return bus_business_addtime;
        }

        public void setBus_business_addtime(String bus_business_addtime) {
            this.bus_business_addtime = bus_business_addtime;
        }

        public String getBus_business_cityname() {
            return bus_business_cityname;
        }

        public void setBus_business_cityname(String bus_business_cityname) {
            this.bus_business_cityname = bus_business_cityname;
        }

        public String getBus_business_img() {
            return bus_business_img;
        }

        public void setBus_business_img(String bus_business_img) {
            this.bus_business_img = bus_business_img;
        }

        public String getBus_business_logo() {
            return bus_business_logo;
        }

        public void setBus_business_logo(String bus_business_logo) {
            this.bus_business_logo = bus_business_logo;
        }

        public String getBus_business_name() {
            return bus_business_name;
        }

        public void setBus_business_name(String bus_business_name) {
            this.bus_business_name = bus_business_name;
        }

        public String getBus_business_number() {
            return bus_business_number;
        }

        public void setBus_business_number(String bus_business_number) {
            this.bus_business_number = bus_business_number;
        }

        public String getBus_id() {
            return bus_id;
        }

        public void setBus_id(String bus_id) {
            this.bus_id = bus_id;
        }

    }
}

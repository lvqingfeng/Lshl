package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class AleradyCommerceBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"bus_id":"1","bus_business_name":"甘肃省山东商会","bus_business_logo":"3.jpg","bus_business_cityname":"兰州市","bus_business_addtime":"1469581377","bus_business_number":"199"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"bus_id":"1","bus_business_name":"甘肃省山东商会","bus_business_logo":"3.jpg","bus_business_cityname":"兰州市","bus_business_addtime":"1469581377","bus_business_number":"199"}]
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
         * bus_id : 1
         * bus_business_name : 甘肃省山东商会
         * bus_business_logo : 3.jpg
         * bus_business_cityname : 兰州市
         * bus_business_addtime : 1469581377
         * bus_business_number : 199
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
            private String bus_id;
            private String bus_business_name;
            private String bus_business_logo;
            private String bus_business_cityname;
            private String bus_business_addtime;
            private String bus_business_number;

            public String getBus_id() {
                return bus_id;
            }

            public void setBus_id(String bus_id) {
                this.bus_id = bus_id;
            }

            public String getBus_business_name() {
                return bus_business_name;
            }

            public void setBus_business_name(String bus_business_name) {
                this.bus_business_name = bus_business_name;
            }

            public String getBus_business_logo() {
                return bus_business_logo;
            }

            public void setBus_business_logo(String bus_business_logo) {
                this.bus_business_logo = bus_business_logo;
            }

            public String getBus_business_cityname() {
                return bus_business_cityname;
            }

            public void setBus_business_cityname(String bus_business_cityname) {
                this.bus_business_cityname = bus_business_cityname;
            }

            public String getBus_business_addtime() {
                return bus_business_addtime;
            }

            public void setBus_business_addtime(String bus_business_addtime) {
                this.bus_business_addtime = bus_business_addtime;
            }

            public String getBus_business_number() {
                return bus_business_number;
            }

            public void setBus_business_number(String bus_business_number) {
                this.bus_business_number = bus_business_number;
            }
        }
    }
}

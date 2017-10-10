package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */

public class StationDetailsBean {

    /**
     * status : 0
     * info : {"realname":"魁魁魁11","avatar":"3.jpg","ss_id":"1","ss_name":"兰州服务站","ss_img":"3.jpg","ss_address":"兰州市安宁区宁苑小区","ss_phone":"18366189918","ss_cityname":"甘肃兰州","ss_uid":"3","service":[{"si_id":"1","si_sid":"1","si_title":"周边游","si_img":"tourism.png"},{"si_id":"3","si_sid":"1","si_title":"酒店住宿","si_img":"hotel.png"},{"si_id":"4","si_sid":"1","si_title":"餐饮娱乐","si_img":"food.png"},{"si_id":"2","si_sid":"1","si_title":"汽车租赁","si_img":"car.png"},{"si_id":"5","si_sid":"1","si_title":"订票服务","si_img":"reserve.png"}]}
     */

    private int status;
    /**
     * realname : 魁魁魁11
     * avatar : 3.jpg
     * ss_id : 1
     * ss_name : 兰州服务站
     * ss_img : 3.jpg
     * ss_address : 兰州市安宁区宁苑小区
     * ss_phone : 18366189918
     * ss_cityname : 甘肃兰州
     * ss_uid : 3
     * service : [{"si_id":"1","si_sid":"1","si_title":"周边游","si_img":"tourism.png"},{"si_id":"3","si_sid":"1","si_title":"酒店住宿","si_img":"hotel.png"},{"si_id":"4","si_sid":"1","si_title":"餐饮娱乐","si_img":"food.png"},{"si_id":"2","si_sid":"1","si_title":"汽车租赁","si_img":"car.png"},{"si_id":"5","si_sid":"1","si_title":"订票服务","si_img":"reserve.png"}]
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
        private String realname;
        private String avatar;
        private String ss_id;
        private String ss_name;
        private String ss_img;
        private String ss_address;
        private String ss_phone;
        private String ss_cityname;
        private String ss_uid;
        /**
         * si_id : 1
         * si_sid : 1
         * si_title : 周边游
         * si_img : tourism.png
         */

        private List<ServiceBean> service;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

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

        public String getSs_img() {
            return ss_img;
        }

        public void setSs_img(String ss_img) {
            this.ss_img = ss_img;
        }

        public String getSs_address() {
            return ss_address;
        }

        public void setSs_address(String ss_address) {
            this.ss_address = ss_address;
        }

        public String getSs_phone() {
            return ss_phone;
        }

        public void setSs_phone(String ss_phone) {
            this.ss_phone = ss_phone;
        }

        public String getSs_cityname() {
            return ss_cityname;
        }

        public void setSs_cityname(String ss_cityname) {
            this.ss_cityname = ss_cityname;
        }

        public String getSs_uid() {
            return ss_uid;
        }

        public void setSs_uid(String ss_uid) {
            this.ss_uid = ss_uid;
        }

        public List<ServiceBean> getService() {
            return service;
        }

        public void setService(List<ServiceBean> service) {
            this.service = service;
        }

        public static class ServiceBean {
            private String si_id;
            private String si_sid;
            private String si_title;
            private String si_img;

            public String getSi_id() {
                return si_id;
            }

            public void setSi_id(String si_id) {
                this.si_id = si_id;
            }

            public String getSi_sid() {
                return si_sid;
            }

            public void setSi_sid(String si_sid) {
                this.si_sid = si_sid;
            }

            public String getSi_title() {
                return si_title;
            }

            public void setSi_title(String si_title) {
                this.si_title = si_title;
            }

            public String getSi_img() {
                return si_img;
            }

            public void setSi_img(String si_img) {
                this.si_img = si_img;
            }
        }
    }
}

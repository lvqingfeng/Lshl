package com.lshl.bean;

/**
 * Created by Administrator on 2017/1/18.
 */

public class PayZfbBean {

    /**
     * status : 1
     * info : {"private_key":"mdskb04o673bjtsxgjg66ehgrft5phj5","notify_url":"http://lushanghulian.com/Api/AliApp/notify_url","orderno":"2017011809422319798"}
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
         * private_key : mdskb04o673bjtsxgjg66ehgrft5phj5
         * notify_url : http://lushanghulian.com/Api/AliApp/notify_url
         * orderno : 2017011809422319798
         */

        private String private_key;
        private String notify_url;
        private String orderno;


        public String getPrivate_key() {
            return private_key;
        }

        public void setPrivate_key(String private_key) {
            this.private_key = private_key;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }
    }
}

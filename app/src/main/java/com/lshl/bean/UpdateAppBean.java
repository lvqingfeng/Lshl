package com.lshl.bean;

/**
 * Created by Administrator on 2017/3/4.
 */

public class UpdateAppBean {

    /**
     * status : 1
     * info : {"min_code":"2.1","new_code":"2.1.1","url":"http://lushanghulian.com/app/lshl.apk"}
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
         * min_code : 2.1
         * new_code : 2.1.1
         * url : http://lushanghulian.com/app/lshl.apk
         */

        private String min_code;
        private String new_code;
        private String url;
        private String message;

        public String getMin_code() {
            return min_code;
        }

        public void setMin_code(String min_code) {
            this.min_code = min_code;
        }

        public String getNew_code() {
            return new_code;
        }

        public void setNew_code(String new_code) {
            this.new_code = new_code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

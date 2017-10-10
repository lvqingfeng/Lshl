package com.lshl.bean;

/**
 * Created by Administrator on 2016/11/23.
 */

public class Capitalbean {

    /**
     * status : 1
     * info : {"all":"2406397.98","gongyi":0,"huzhu":0,"surplus":2406397.98}
     */

    private int status;
    /**
     * all : 2406397.98
     * gongyi : 0
     * huzhu : 0
     * surplus : 2406397.98
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
        private String all;
        private String gongyi;
        private String huzhu;
        private String surplus;

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }

        public String getGongyi() {
            return gongyi;
        }

        public void setGongyi(String gongyi) {
            this.gongyi = gongyi;
        }

        public String getHuzhu() {
            return huzhu;
        }

        public void setHuzhu(String huzhu) {
            this.huzhu = huzhu;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }
    }
}

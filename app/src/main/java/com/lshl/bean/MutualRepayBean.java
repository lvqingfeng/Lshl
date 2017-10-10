package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class MutualRepayBean {

    /**
     * status : 1
     * info : [{"ma_repayment":"2017年12月30日 ","ma_repayment_time":"1514563200","ma_id":"2","ma_project_name":"我有项目，但是缺钱啦","ma_project_money":"800000.00"}]
     */

    private int status;
    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * ma_repayment : 2017年12月30日
         * ma_repayment_time : 1514563200
         * ma_id : 2
         * ma_project_name : 我有项目，但是缺钱啦
         * ma_project_money : 800000.00
         */

        private String ma_repayment;
        private String ma_repayment_time;
        private String ma_id;
        private String ma_project_name;
        private String ma_project_money;

        public String getMa_repayment() {
            return ma_repayment;
        }

        public void setMa_repayment(String ma_repayment) {
            this.ma_repayment = ma_repayment;
        }

        public String getMa_repayment_time() {
            return ma_repayment_time;
        }

        public void setMa_repayment_time(String ma_repayment_time) {
            this.ma_repayment_time = ma_repayment_time;
        }

        public String getMa_id() {
            return ma_id;
        }

        public void setMa_id(String ma_id) {
            this.ma_id = ma_id;
        }

        public String getMa_project_name() {
            return ma_project_name;
        }

        public void setMa_project_name(String ma_project_name) {
            this.ma_project_name = ma_project_name;
        }

        public String getMa_project_money() {
            return ma_project_money;
        }

        public void setMa_project_money(String ma_project_money) {
            this.ma_project_money = ma_project_money;
        }
    }
}

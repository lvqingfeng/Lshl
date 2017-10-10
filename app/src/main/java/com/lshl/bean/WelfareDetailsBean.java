package com.lshl.bean;

import java.util.List;

/***
 * Created by Administrator on 2017/1/9.
 */

public class WelfareDetailsBean {

    /**
     * status : 1
     * info : {"pw_id":"1","pw_type":"1","pw_uid":"17","pw_project_name":"宝贝很好\n","pw_project_money":"555.00","pw_project_info":"呵呵呵","pw_project_img":"58675284c026693944.png,58675284c026651108.png","pw_addtime":"1483166340","pw_address":"北京市北京市","pw_status":"2","pw_audit_info":null,"pw_audit_time":null,"f_vote_total_number":"0","f_vote_support":"0","f_vote_opposition":"0","f_vote_wairer":"0","f_vote_endtime":null,"s_vote_total_number":"0","s_vote_support":"0","s_vote_opposition":"0","s_vote_wairer":"0","s_vote_enttime":null,"t_vote_total_number":"0","t_vote_support":"0","t_vote_opposition":"0","t_vote_wairer":"0","t_vote_enttime":null,"pw_payment":null,"pw_payment_time":null,"pw_payment_info":null,"pw_payment_img":null,"pw_receipt_info":null,"pw_receipt_img":null,"name":"v高呵呵好吧","allmember":"","imgs":["58675284c026693944.png","58675284c026651108.png"],"feedback_img":[""]}
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
         * pw_id : 1
         * pw_type : 1
         * pw_uid : 17
         * pw_project_name : 宝贝很好

         * pw_project_money : 555.00
         * pw_project_info : 呵呵呵
         * pw_project_img : 58675284c026693944.png,58675284c026651108.png
         * pw_addtime : 1483166340
         * pw_address : 北京市北京市
         * pw_status : 2
         * pw_audit_info : null
         * pw_audit_time : null
         * f_vote_total_number : 0
         * f_vote_support : 0
         * f_vote_opposition : 0
         * f_vote_wairer : 0
         * f_vote_endtime : null
         * s_vote_total_number : 0
         * s_vote_support : 0
         * s_vote_opposition : 0
         * s_vote_wairer : 0
         * s_vote_enttime : null
         * t_vote_total_number : 0
         * t_vote_support : 0
         * t_vote_opposition : 0
         * t_vote_wairer : 0
         * t_vote_enttime : null
         * pw_payment : null
         * pw_payment_time : null
         * pw_payment_info : null
         * pw_payment_img : null
         * pw_receipt_info : null
         * pw_receipt_img : null
         * name : v高呵呵好吧
         * allmember :
         * imgs : ["58675284c026693944.png","58675284c026651108.png"]
         * feedback_img : [""]
         */

        private String pw_id;
        private String pw_type;
        private String pw_uid;
        private String pw_project_name;
        private String pw_project_money;
        private String pw_project_info;
        private String pw_project_img;
        private String pw_addtime;
        private String pw_address;
        private String pw_status;
        private Object pw_audit_info;
        private Object pw_audit_time;
        private String f_vote_total_number;
        private String f_vote_support;
        private String f_vote_opposition;
        private String f_vote_wairer;
        private Object f_vote_endtime;
        private String s_vote_total_number;
        private String s_vote_support;
        private String s_vote_opposition;
        private String s_vote_wairer;
        private Object s_vote_enttime;
        private String t_vote_total_number;
        private String t_vote_support;
        private String t_vote_opposition;
        private String t_vote_wairer;
        private Object t_vote_enttime;
        private Object pw_payment;
        private Object pw_payment_time;
        private Object pw_payment_info;
        private String pw_payment_img;
        private Object pw_receipt_info;
        private Object pw_receipt_img;
        private String name;
        private String allmember;
        private List<String> imgs;
        private List<String> feedback_img;

        public String getPw_id() {
            return pw_id;
        }

        public void setPw_id(String pw_id) {
            this.pw_id = pw_id;
        }

        public String getPw_type() {
            return pw_type;
        }

        public void setPw_type(String pw_type) {
            this.pw_type = pw_type;
        }

        public String getPw_uid() {
            return pw_uid;
        }

        public void setPw_uid(String pw_uid) {
            this.pw_uid = pw_uid;
        }

        public String getPw_project_name() {
            return pw_project_name;
        }

        public void setPw_project_name(String pw_project_name) {
            this.pw_project_name = pw_project_name;
        }

        public String getPw_project_money() {
            return pw_project_money;
        }

        public void setPw_project_money(String pw_project_money) {
            this.pw_project_money = pw_project_money;
        }

        public String getPw_project_info() {
            return pw_project_info;
        }

        public void setPw_project_info(String pw_project_info) {
            this.pw_project_info = pw_project_info;
        }

        public String getPw_project_img() {
            return pw_project_img;
        }

        public void setPw_project_img(String pw_project_img) {
            this.pw_project_img = pw_project_img;
        }

        public String getPw_addtime() {
            return pw_addtime;
        }

        public void setPw_addtime(String pw_addtime) {
            this.pw_addtime = pw_addtime;
        }

        public String getPw_address() {
            return pw_address;
        }

        public void setPw_address(String pw_address) {
            this.pw_address = pw_address;
        }

        public String getPw_status() {
            return pw_status;
        }

        public void setPw_status(String pw_status) {
            this.pw_status = pw_status;
        }

        public Object getPw_audit_info() {
            return pw_audit_info;
        }

        public void setPw_audit_info(Object pw_audit_info) {
            this.pw_audit_info = pw_audit_info;
        }

        public Object getPw_audit_time() {
            return pw_audit_time;
        }

        public void setPw_audit_time(Object pw_audit_time) {
            this.pw_audit_time = pw_audit_time;
        }

        public String getF_vote_total_number() {
            return f_vote_total_number;
        }

        public void setF_vote_total_number(String f_vote_total_number) {
            this.f_vote_total_number = f_vote_total_number;
        }

        public String getF_vote_support() {
            return f_vote_support;
        }

        public void setF_vote_support(String f_vote_support) {
            this.f_vote_support = f_vote_support;
        }

        public String getF_vote_opposition() {
            return f_vote_opposition;
        }

        public void setF_vote_opposition(String f_vote_opposition) {
            this.f_vote_opposition = f_vote_opposition;
        }

        public String getF_vote_wairer() {
            return f_vote_wairer;
        }

        public void setF_vote_wairer(String f_vote_wairer) {
            this.f_vote_wairer = f_vote_wairer;
        }

        public Object getF_vote_endtime() {
            return f_vote_endtime;
        }

        public void setF_vote_endtime(Object f_vote_endtime) {
            this.f_vote_endtime = f_vote_endtime;
        }

        public String getS_vote_total_number() {
            return s_vote_total_number;
        }

        public void setS_vote_total_number(String s_vote_total_number) {
            this.s_vote_total_number = s_vote_total_number;
        }

        public String getS_vote_support() {
            return s_vote_support;
        }

        public void setS_vote_support(String s_vote_support) {
            this.s_vote_support = s_vote_support;
        }

        public String getS_vote_opposition() {
            return s_vote_opposition;
        }

        public void setS_vote_opposition(String s_vote_opposition) {
            this.s_vote_opposition = s_vote_opposition;
        }

        public String getS_vote_wairer() {
            return s_vote_wairer;
        }

        public void setS_vote_wairer(String s_vote_wairer) {
            this.s_vote_wairer = s_vote_wairer;
        }

        public Object getS_vote_enttime() {
            return s_vote_enttime;
        }

        public void setS_vote_enttime(Object s_vote_enttime) {
            this.s_vote_enttime = s_vote_enttime;
        }

        public String getT_vote_total_number() {
            return t_vote_total_number;
        }

        public void setT_vote_total_number(String t_vote_total_number) {
            this.t_vote_total_number = t_vote_total_number;
        }

        public String getT_vote_support() {
            return t_vote_support;
        }

        public void setT_vote_support(String t_vote_support) {
            this.t_vote_support = t_vote_support;
        }

        public String getT_vote_opposition() {
            return t_vote_opposition;
        }

        public void setT_vote_opposition(String t_vote_opposition) {
            this.t_vote_opposition = t_vote_opposition;
        }

        public String getT_vote_wairer() {
            return t_vote_wairer;
        }

        public void setT_vote_wairer(String t_vote_wairer) {
            this.t_vote_wairer = t_vote_wairer;
        }

        public Object getT_vote_enttime() {
            return t_vote_enttime;
        }

        public void setT_vote_enttime(Object t_vote_enttime) {
            this.t_vote_enttime = t_vote_enttime;
        }

        public Object getPw_payment() {
            return pw_payment;
        }

        public void setPw_payment(Object pw_payment) {
            this.pw_payment = pw_payment;
        }

        public Object getPw_payment_time() {
            return pw_payment_time;
        }

        public void setPw_payment_time(Object pw_payment_time) {
            this.pw_payment_time = pw_payment_time;
        }

        public Object getPw_payment_info() {
            return pw_payment_info;
        }

        public void setPw_payment_info(Object pw_payment_info) {
            this.pw_payment_info = pw_payment_info;
        }

        public String getPw_payment_img() {
            return pw_payment_img;
        }

        public void setPw_payment_img(String pw_payment_img) {
            this.pw_payment_img = pw_payment_img;
        }

        public Object getPw_receipt_info() {
            return pw_receipt_info;
        }

        public void setPw_receipt_info(Object pw_receipt_info) {
            this.pw_receipt_info = pw_receipt_info;
        }

        public Object getPw_receipt_img() {
            return pw_receipt_img;
        }

        public void setPw_receipt_img(Object pw_receipt_img) {
            this.pw_receipt_img = pw_receipt_img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAllmember() {
            return allmember;
        }

        public void setAllmember(String allmember) {
            this.allmember = allmember;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getFeedback_img() {
            return feedback_img;
        }

        public void setFeedback_img(List<String> feedback_img) {
            this.feedback_img = feedback_img;
        }
    }
}

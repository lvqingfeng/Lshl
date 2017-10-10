package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/***
 * Created by Administrator on 2016/11/15.
 */

public class BusinessDetailsBean implements Serializable{

    /**
     * status : 1
     * info : {"realname":"吕清锋","uid":"4","en_id":"19","en_name":"哇噢横眉怒目","en_uid":"4","en_img":"587351637eae789782.png,587351637eae79902.png","en_info":"数据模型 浪漫主义","en_code":"258039","en_address":"台湾","en_type":"有限公司(法人独资)内资","en_capital":"5800000","en_establish":"2015年1月9日 ","en_deadline":"2019年1月9日 ","en_branched":"是浪漫主义","en_license":null,"en_cityno":"1345","en_status":"1","en_addtime":"1483952483","en_edittime":"1483952520","en_editinfo":"3560--8743","img":["587351637eae789782.png","587351637eae79902.png"]}
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

    public static class InfoBean implements Serializable{
        /**
         * realname : 吕清锋
         * uid : 4
         * en_id : 19
         * en_name : 哇噢横眉怒目
         * en_uid : 4
         * en_img : 587351637eae789782.png,587351637eae79902.png
         * en_info : 数据模型 浪漫主义
         * en_code : 258039
         * en_address : 台湾
         * en_type : 有限公司(法人独资)内资
         * en_capital : 5800000
         * en_establish : 2015年1月9日
         * en_deadline : 2019年1月9日
         * en_branched : 是浪漫主义
         * en_license : null
         * en_cityno : 1345
         * en_status : 1
         * en_addtime : 1483952483
         * en_edittime : 1483952520
         * en_editinfo : 3560--8743
         * img : ["587351637eae789782.png","587351637eae79902.png"]
         */

        private String realname;
        private String uid;
        private String en_id;
        private String en_name;
        private String en_uid;
        private String en_img;
        private String en_info;
        private String en_code;
        private String en_address;
        private String en_type;
        private String en_capital;
        private String en_establish;
        private String en_deadline;
        private String en_branched;
        private String en_license;
        private String en_cityno;
        private String en_status;
        private String en_addtime;
        private String en_edittime;
        private String en_editinfo;
        private List<String> img;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEn_id() {
            return en_id;
        }

        public void setEn_id(String en_id) {
            this.en_id = en_id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getEn_uid() {
            return en_uid;
        }

        public void setEn_uid(String en_uid) {
            this.en_uid = en_uid;
        }

        public String getEn_img() {
            return en_img;
        }

        public void setEn_img(String en_img) {
            this.en_img = en_img;
        }

        public String getEn_info() {
            return en_info;
        }

        public void setEn_info(String en_info) {
            this.en_info = en_info;
        }

        public String getEn_code() {
            return en_code;
        }

        public void setEn_code(String en_code) {
            this.en_code = en_code;
        }

        public String getEn_address() {
            return en_address;
        }

        public void setEn_address(String en_address) {
            this.en_address = en_address;
        }

        public String getEn_type() {
            return en_type;
        }

        public void setEn_type(String en_type) {
            this.en_type = en_type;
        }

        public String getEn_capital() {
            return en_capital;
        }

        public void setEn_capital(String en_capital) {
            this.en_capital = en_capital;
        }

        public String getEn_establish() {
            return en_establish;
        }

        public void setEn_establish(String en_establish) {
            this.en_establish = en_establish;
        }

        public String getEn_deadline() {
            return en_deadline;
        }

        public void setEn_deadline(String en_deadline) {
            this.en_deadline = en_deadline;
        }

        public String getEn_branched() {
            return en_branched;
        }

        public void setEn_branched(String en_branched) {
            this.en_branched = en_branched;
        }

        public String getEn_license() {
            return en_license;
        }

        public void setEn_license(String en_license) {
            this.en_license = en_license;
        }

        public String getEn_cityno() {
            return en_cityno;
        }

        public void setEn_cityno(String en_cityno) {
            this.en_cityno = en_cityno;
        }

        public String getEn_status() {
            return en_status;
        }

        public void setEn_status(String en_status) {
            this.en_status = en_status;
        }

        public String getEn_addtime() {
            return en_addtime;
        }

        public void setEn_addtime(String en_addtime) {
            this.en_addtime = en_addtime;
        }

        public String getEn_edittime() {
            return en_edittime;
        }

        public void setEn_edittime(String en_edittime) {
            this.en_edittime = en_edittime;
        }

        public String getEn_editinfo() {
            return en_editinfo;
        }

        public void setEn_editinfo(String en_editinfo) {
            this.en_editinfo = en_editinfo;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}

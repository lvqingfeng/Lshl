package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class ShiJiuDetailsBean implements Serializable {
    /**
     * status : 1
     * info : [{"realname":null,"avatar":null,"phone":"18892084259","pre_id":"164","pre_uid":"43","pre_info":"得目录下了好大","pre_cityname":"甘肃省兰州市安宁区北滨河西路","pre_jingwei":"36.096709,103.743336","pre_addtime":"1477635583","pre_status":"1","pre_report_status":"0","pre_endtime":"1477635587"}]
     */

    private int status;
    /**
     * realname : null
     * avatar : null
     * phone : 18892084259
     * pre_id : 164
     * pre_uid : 43
     * pre_info : 得目录下了好大
     * pre_cityname : 甘肃省兰州市安宁区北滨河西路
     * pre_jingwei : 36.096709,103.743336
     * pre_addtime : 1477635583
     * pre_status : 1
     * pre_report_status : 0
     * pre_endtime : 1477635587
     */

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
        private String realname;
        private String avatar;
        private String phone;
        private String pre_id;
        private String pre_uid;
        private String pre_info;
        private String pre_cityname;
        private String pre_jingwei;
        private String pre_addtime;
        private String pre_status;
        private String pre_report_status;
        private String pre_endtime;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPre_id() {
            return pre_id;
        }

        public void setPre_id(String pre_id) {
            this.pre_id = pre_id;
        }

        public String getPre_uid() {
            return pre_uid;
        }

        public void setPre_uid(String pre_uid) {
            this.pre_uid = pre_uid;
        }

        public String getPre_info() {
            return pre_info;
        }

        public void setPre_info(String pre_info) {
            this.pre_info = pre_info;
        }

        public String getPre_cityname() {
            return pre_cityname;
        }

        public void setPre_cityname(String pre_cityname) {
            this.pre_cityname = pre_cityname;
        }

        public String getPre_jingwei() {
            return pre_jingwei;
        }

        public void setPre_jingwei(String pre_jingwei) {
            this.pre_jingwei = pre_jingwei;
        }

        public String getPre_addtime() {
            return pre_addtime;
        }

        public void setPre_addtime(String pre_addtime) {
            this.pre_addtime = pre_addtime;
        }

        public String getPre_status() {
            return pre_status;
        }

        public void setPre_status(String pre_status) {
            this.pre_status = pre_status;
        }

        public String getPre_report_status() {
            return pre_report_status;
        }

        public void setPre_report_status(String pre_report_status) {
            this.pre_report_status = pre_report_status;
        }

        public String getPre_endtime() {
            return pre_endtime;
        }

        public void setPre_endtime(String pre_endtime) {
            this.pre_endtime = pre_endtime;
        }
    }

}

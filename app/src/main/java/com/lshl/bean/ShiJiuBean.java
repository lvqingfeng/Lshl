package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class ShiJiuBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"gre_id":"69","gre_pid":"94","gre_uid":"12","gre_jingwei":"36.096663,103.743511","gre_distance":"55","gre_addtime":"1474336907","gre_status":"0","gre_arrivetime":"0","gre_complaints":"1","gre_complaints_info":"乱按","pre_uid":"3","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_info":"啊礼物","pre_status":"1","realname":"魁大圣"},{"gre_id":"72","gre_pid":"95","gre_uid":"12","gre_jingwei":"36.096663,103.743511","gre_distance":"472","gre_addtime":"1474337472","gre_status":"1","gre_arrivetime":"1474875667","gre_complaints":"0","gre_complaints_info":null,"pre_uid":"3","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_info":"兰州","pre_status":"1","realname":"魁大圣"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"gre_id":"69","gre_pid":"94","gre_uid":"12","gre_jingwei":"36.096663,103.743511","gre_distance":"55","gre_addtime":"1474336907","gre_status":"0","gre_arrivetime":"0","gre_complaints":"1","gre_complaints_info":"乱按","pre_uid":"3","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_info":"啊礼物","pre_status":"1","realname":"魁大圣"},{"gre_id":"72","gre_pid":"95","gre_uid":"12","gre_jingwei":"36.096663,103.743511","gre_distance":"472","gre_addtime":"1474337472","gre_status":"1","gre_arrivetime":"1474875667","gre_complaints":"0","gre_complaints_info":null,"pre_uid":"3","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_info":"兰州","pre_status":"1","realname":"魁大圣"}]
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
         * gre_id : 69
         * gre_pid : 94
         * gre_uid : 12
         * gre_jingwei : 36.096663,103.743511
         * gre_distance : 55
         * gre_addtime : 1474336907
         * gre_status : 0
         * gre_arrivetime : 0
         * gre_complaints : 1
         * gre_complaints_info : 乱按
         * pre_uid : 3
         * pre_cityname : 甘肃省兰州市安宁区建宁东路
         * pre_info : 啊礼物
         * pre_status : 1
         * realname : 魁大圣
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
            private String gre_id;
            private String gre_pid;
            private String gre_uid;
            private String gre_jingwei;
            private String gre_distance;
            private String gre_addtime;
            private String gre_status;
            private String gre_arrivetime;
            private String gre_complaints;
            private String gre_complaints_info;
            private String pre_uid;
            private String pre_cityname;
            private String pre_info;
            private String pre_status;
            private String realname;

            public String getGre_id() {
                return gre_id;
            }

            public void setGre_id(String gre_id) {
                this.gre_id = gre_id;
            }

            public String getGre_pid() {
                return gre_pid;
            }

            public void setGre_pid(String gre_pid) {
                this.gre_pid = gre_pid;
            }

            public String getGre_uid() {
                return gre_uid;
            }

            public void setGre_uid(String gre_uid) {
                this.gre_uid = gre_uid;
            }

            public String getGre_jingwei() {
                return gre_jingwei;
            }

            public void setGre_jingwei(String gre_jingwei) {
                this.gre_jingwei = gre_jingwei;
            }

            public String getGre_distance() {
                return gre_distance;
            }

            public void setGre_distance(String gre_distance) {
                this.gre_distance = gre_distance;
            }

            public String getGre_addtime() {
                return gre_addtime;
            }

            public void setGre_addtime(String gre_addtime) {
                this.gre_addtime = gre_addtime;
            }

            public String getGre_status() {
                return gre_status;
            }

            public void setGre_status(String gre_status) {
                this.gre_status = gre_status;
            }

            public String getGre_arrivetime() {
                return gre_arrivetime;
            }

            public void setGre_arrivetime(String gre_arrivetime) {
                this.gre_arrivetime = gre_arrivetime;
            }

            public String getGre_complaints() {
                return gre_complaints;
            }

            public void setGre_complaints(String gre_complaints) {
                this.gre_complaints = gre_complaints;
            }

            public String getGre_complaints_info() {
                return gre_complaints_info;
            }

            public void setGre_complaints_info(String gre_complaints_info) {
                this.gre_complaints_info = gre_complaints_info;
            }

            public String getPre_uid() {
                return pre_uid;
            }

            public void setPre_uid(String pre_uid) {
                this.pre_uid = pre_uid;
            }

            public String getPre_cityname() {
                return pre_cityname;
            }

            public void setPre_cityname(String pre_cityname) {
                this.pre_cityname = pre_cityname;
            }

            public String getPre_info() {
                return pre_info;
            }

            public void setPre_info(String pre_info) {
                this.pre_info = pre_info;
            }

            public String getPre_status() {
                return pre_status;
            }

            public void setPre_status(String pre_status) {
                this.pre_status = pre_status;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
        }
    }
}
package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月12日
 * 时间：12:53
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewNoticeBean {


    /**
     * end : 0
     * list : [{"pi_addtime":"1481510310","pi_catid":"huzhu","pi_id":"41","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481466111","pi_catid":"huzhu","pi_id":"40","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481465943","pi_catid":"huzhu","pi_id":"39","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481465308","pi_catid":"huzhu","pi_id":"38","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481464915","pi_catid":"huzhu","pi_id":"37","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481463957","pi_catid":"huzhu","pi_id":"35","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481463949","pi_catid":"huzhu","pi_id":"34","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481463932","pi_catid":"huzhu","pi_id":"33","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481463831","pi_catid":"huzhu","pi_id":"32","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"},{"pi_addtime":"1481352199","pi_catid":"huzhu","pi_id":"18","pi_info":"内容","pi_project":"1","pi_status":"1","pi_title":"测试测试","pi_uid":"5"}]
     * pages : 4
     */

    private int end;
    private int pages;
    private List<ListBean> list;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * pi_addtime : 1481510310
         * pi_catid : huzhu
         * pi_id : 41
         * pi_info : 内容
         * pi_project : 1
         * pi_status : 1
         * pi_title : 测试测试
         * pi_uid : 5
         */

        private String pi_addtime;
        private String pi_catid;
        private String pi_id;
        private String pi_info;
        private String pi_project;
        private int pi_status;
        private String pi_title;
        private String pi_uid;

        public String getPi_addtime() {
            return pi_addtime;
        }

        public void setPi_addtime(String pi_addtime) {
            this.pi_addtime = pi_addtime;
        }

        public String getPi_catid() {
            return pi_catid;
        }

        public void setPi_catid(String pi_catid) {
            this.pi_catid = pi_catid;
        }

        public String getPi_id() {
            return pi_id;
        }

        public void setPi_id(String pi_id) {
            this.pi_id = pi_id;
        }

        public String getPi_info() {
            return pi_info;
        }

        public void setPi_info(String pi_info) {
            this.pi_info = pi_info;
        }

        public String getPi_project() {
            return pi_project;
        }

        public void setPi_project(String pi_project) {
            this.pi_project = pi_project;
        }

        public int getPi_status() {
            return pi_status;
        }

        public void setPi_status(int pi_status) {
            this.pi_status = pi_status;
        }

        public String getPi_title() {
            return pi_title;
        }

        public void setPi_title(String pi_title) {
            this.pi_title = pi_title;
        }

        public String getPi_uid() {
            return pi_uid;
        }

        public void setPi_uid(String pi_uid) {
            this.pi_uid = pi_uid;
        }
    }
}

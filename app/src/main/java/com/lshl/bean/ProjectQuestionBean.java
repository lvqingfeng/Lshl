package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ProjectQuestionBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"realname":"王大人","avatar":"57fa21d102ce3.png","pc_id":"1","pc_pid":"1","pc_question_uid":"7","pc_question_info":"提问","pc_question_time":"1477291110","pc_answer_uid":"3","pc_answer_info":"回复","pc_answer_time":"1477381372"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"realname":"王大人","avatar":"57fa21d102ce3.png","pc_id":"1","pc_pid":"1","pc_question_uid":"7","pc_question_info":"提问","pc_question_time":"1477291110","pc_answer_uid":"3","pc_answer_info":"回复","pc_answer_time":"1477381372"}]
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
         * realname : 王大人
         * avatar : 57fa21d102ce3.png
         * pc_id : 1
         * pc_pid : 1
         * pc_question_uid : 7
         * pc_question_info : 提问
         * pc_question_time : 1477291110
         * pc_answer_uid : 3
         * pc_answer_info : 回复
         * pc_answer_time : 1477381372
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
            private String realname;
            private String avatar;
            private String pc_id;
            private String pc_pid;
            private String pc_question_uid;
            private String pc_question_info;
            private String pc_question_time;
            private String pc_answer_uid;
            private String pc_answer_info;
            private String pc_answer_time;

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

            public String getPc_id() {
                return pc_id;
            }

            public void setPc_id(String pc_id) {
                this.pc_id = pc_id;
            }

            public String getPc_pid() {
                return pc_pid;
            }

            public void setPc_pid(String pc_pid) {
                this.pc_pid = pc_pid;
            }

            public String getPc_question_uid() {
                return pc_question_uid;
            }

            public void setPc_question_uid(String pc_question_uid) {
                this.pc_question_uid = pc_question_uid;
            }

            public String getPc_question_info() {
                return pc_question_info;
            }

            public void setPc_question_info(String pc_question_info) {
                this.pc_question_info = pc_question_info;
            }

            public String getPc_question_time() {
                return pc_question_time;
            }

            public void setPc_question_time(String pc_question_time) {
                this.pc_question_time = pc_question_time;
            }

            public String getPc_answer_uid() {
                return pc_answer_uid;
            }

            public void setPc_answer_uid(String pc_answer_uid) {
                this.pc_answer_uid = pc_answer_uid;
            }

            public String getPc_answer_info() {
                return pc_answer_info;
            }

            public void setPc_answer_info(String pc_answer_info) {
                this.pc_answer_info = pc_answer_info;
            }

            public String getPc_answer_time() {
                return pc_answer_time;
            }

            public void setPc_answer_time(String pc_answer_time) {
                this.pc_answer_time = pc_answer_time;
            }
        }
    }
}

package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class LookHelpCommentBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"realname":"孔夫子","avatar":"586eee79b7e6e73650.jpg","fhc_id":"1","fhc_fid":"1","fhc_question_uid":"30","fhc_question_info":"评论评论评论评论评论评论评论评论评论评论评论评论","fhc_question_time":"1483156857","fhc_answer_uid":"5","fhc_answer_info":"回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复","fhc_answer_time":"1483156885"}]}
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
         * pages : 1
         * end : 1
         * list : [{"realname":"孔夫子","avatar":"586eee79b7e6e73650.jpg","fhc_id":"1","fhc_fid":"1","fhc_question_uid":"30","fhc_question_info":"评论评论评论评论评论评论评论评论评论评论评论评论","fhc_question_time":"1483156857","fhc_answer_uid":"5","fhc_answer_info":"回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复","fhc_answer_time":"1483156885"}]
         */

        private int pages;
        private int end;
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
            /**
             * realname : 孔夫子
             * avatar : 586eee79b7e6e73650.jpg
             * fhc_id : 1
             * fhc_fid : 1
             * fhc_question_uid : 30
             * fhc_question_info : 评论评论评论评论评论评论评论评论评论评论评论评论
             * fhc_question_time : 1483156857
             * fhc_answer_uid : 5
             * fhc_answer_info : 回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复回复
             * fhc_answer_time : 1483156885
             */

            private String realname;
            private String avatar;
            private String fhc_id;
            private String fhc_fid;
            private String fhc_question_uid;
            private String fhc_question_info;
            private String fhc_question_time;
            private String fhc_answer_uid;
            private String fhc_answer_info;
            private String fhc_answer_time;

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

            public String getFhc_id() {
                return fhc_id;
            }

            public void setFhc_id(String fhc_id) {
                this.fhc_id = fhc_id;
            }

            public String getFhc_fid() {
                return fhc_fid;
            }

            public void setFhc_fid(String fhc_fid) {
                this.fhc_fid = fhc_fid;
            }

            public String getFhc_question_uid() {
                return fhc_question_uid;
            }

            public void setFhc_question_uid(String fhc_question_uid) {
                this.fhc_question_uid = fhc_question_uid;
            }

            public String getFhc_question_info() {
                return fhc_question_info;
            }

            public void setFhc_question_info(String fhc_question_info) {
                this.fhc_question_info = fhc_question_info;
            }

            public String getFhc_question_time() {
                return fhc_question_time;
            }

            public void setFhc_question_time(String fhc_question_time) {
                this.fhc_question_time = fhc_question_time;
            }

            public String getFhc_answer_uid() {
                return fhc_answer_uid;
            }

            public void setFhc_answer_uid(String fhc_answer_uid) {
                this.fhc_answer_uid = fhc_answer_uid;
            }

            public String getFhc_answer_info() {
                return fhc_answer_info;
            }

            public void setFhc_answer_info(String fhc_answer_info) {
                this.fhc_answer_info = fhc_answer_info;
            }

            public String getFhc_answer_time() {
                return fhc_answer_time;
            }

            public void setFhc_answer_time(String fhc_answer_time) {
                this.fhc_answer_time = fhc_answer_time;
            }
        }
    }
}

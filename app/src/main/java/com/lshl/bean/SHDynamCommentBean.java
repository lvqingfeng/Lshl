package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月17日
 * 时间：12:41
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SHDynamCommentBean {


    /**
     * end : 1
     * list : [{"avatar":"582c769c77a06.jpg","bc_addtime":"1467859547","bc_bdid":"1","bc_id":"1","bc_info":"评论信息","bc_uid":"3","realname":"魁大圣","uid":"3"}]
     * pages : 1
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

    public static class ListBean {
        /**
         * avatar : 582c769c77a06.jpg
         * bc_addtime : 1467859547
         * bc_bdid : 1
         * bc_id : 1
         * bc_info : 评论信息
         * bc_uid : 3
         * realname : 魁大圣
         * uid : 3
         */
        private String url;
        private String avatar;
        private String bc_addtime;
        private String bc_bdid;
        private String bc_id;
        private String bc_info;
        private String bc_uid;
        private String realname;
        private String uid;
        private int grade;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBc_addtime() {
            return bc_addtime;
        }

        public void setBc_addtime(String bc_addtime) {
            this.bc_addtime = bc_addtime;
        }

        public String getBc_bdid() {
            return bc_bdid;
        }

        public void setBc_bdid(String bc_bdid) {
            this.bc_bdid = bc_bdid;
        }

        public String getBc_id() {
            return bc_id;
        }

        public void setBc_id(String bc_id) {
            this.bc_id = bc_id;
        }

        public String getBc_info() {
            return bc_info;
        }

        public void setBc_info(String bc_info) {
            this.bc_info = bc_info;
        }

        public String getBc_uid() {
            return bc_uid;
        }

        public void setBc_uid(String bc_uid) {
            this.bc_uid = bc_uid;
        }

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
    }
}

package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：18:54
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsCommentBean {


    /**
     * end : 0
     * list : [{"am_anonymous":"0","am_article_id":"3","am_city":"","am_comment_info":"在我身边我会告诉","am_comment_time":"1478161216","am_id":"3","am_uid":"9","avatar":"582c41004f745.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","phone":"13162839599","realname":"I'm "},{"am_anonymous":"1","am_anonymous_name":"1103lsRjlUHTLermc","am_article_id":"3","am_city":"","am_comment_info":"甘肃的一切我都是一次的士上","am_comment_time":"1478161443","am_id":"4","am_uid":"9","avatar":"582c41004f745.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","phone":"13162839599","realname":"I'm "},{"am_anonymous":"0","am_article_id":"3","am_city":"","am_comment_info":"你就不能自拔？我要","am_comment_time":"1478162906","am_id":"5","am_uid":"9","avatar":"582c41004f745.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","phone":"13162839599","realname":"I'm "},{"am_anonymous":"0","am_article_id":"3","am_city":"","am_comment_info":":joy::joy::joy::joy::joy::joy::joy::joy:","am_comment_time":"1478163461","am_id":"6","am_uid":"9","avatar":"582c41004f745.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","phone":"13162839599","realname":"I'm "},{"am_anonymous":"1","am_anonymous_name":"1117ls2r7bOeukh1Q","am_article_id":"3","am_city":"兰州","am_comment_info":"56655","am_comment_time":"1479378393","am_id":"27","am_uid":"3","avatar":"3.jpg","hx_id":"2fcb13c32827c61a5d040c82e03eab28","phone":"18366189988","realname":"魁魁魁11"},{"am_anonymous":"1","am_anonymous_name":"1117lsD1s6sAHeTP2","am_article_id":"3","am_city":"","am_comment_info":"I'm not getting my :grin::grin::grin::grin:","am_comment_time":"1479378477","am_id":"28","am_uid":"9","avatar":"582c41004f745.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","phone":"13162839599","realname":"I'm "},{"am_anonymous":"1","am_anonymous_name":"1117lsxUfs1WqC6pE","am_article_id":"3","am_city":"兰州","am_comment_info":"56655","am_comment_time":"1479378519","am_id":"29","am_uid":"3","avatar":"3.jpg","hx_id":"2fcb13c32827c61a5d040c82e03eab28","phone":"18366189988","realname":"魁魁魁11"},{"am_anonymous":"1","am_anonymous_name":"1117lsxUfs1WqC6pE","am_article_id":"3","am_city":"兰州","am_comment_info":"56655","am_comment_time":"1479378526","am_id":"30","am_uid":"3","avatar":"3.jpg","hx_id":"2fcb13c32827c61a5d040c82e03eab28","phone":"18366189988","realname":"魁魁魁11"},{"am_anonymous":"1","am_anonymous_name":"111714UrQJ4DuZZe6","am_article_id":"3","am_city":"兰州","am_comment_info":"56655","am_comment_time":"1479378547","am_id":"31","am_uid":"3","avatar":"3.jpg","hx_id":"2fcb13c32827c61a5d040c82e03eab28","phone":"18366189988","realname":"魁魁魁11"},{"am_anonymous":"1","am_anonymous_name":"111714UrQJ4DuZZe6","am_article_id":"3","am_city":"兰州","am_comment_info":"56655","am_comment_time":"1479378551","am_id":"32","am_uid":"3","avatar":"3.jpg","hx_id":"2fcb13c32827c61a5d040c82e03eab28","phone":"18366189988","realname":"魁魁魁11"}]
     * pages : 2
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
         * am_anonymous : 0
         * am_article_id : 3
         * am_city :
         * am_comment_info : 在我身边我会告诉
         * am_comment_time : 1478161216
         * am_id : 3
         * am_uid : 9
         * avatar : 582c41004f745.jpg
         * hx_id : e191c1cab6772c1aa4c6887082a4d8d5
         * phone : 13162839599
         * realname : I'm
         * am_anonymous_name : 1103lsRjlUHTLermc
         */

        private String am_anonymous;
        private String am_article_id;
        private String am_city;
        private String am_comment_info;
        private String am_comment_time;
        private String am_id;
        private String am_uid;
        private String avatar;
        private String hx_id;
        private String phone;
        private String realname;
        private String am_anonymous_name;

        public String getAm_anonymous() {
            return am_anonymous;
        }

        public void setAm_anonymous(String am_anonymous) {
            this.am_anonymous = am_anonymous;
        }

        public String getAm_article_id() {
            return am_article_id;
        }

        public void setAm_article_id(String am_article_id) {
            this.am_article_id = am_article_id;
        }

        public String getAm_city() {
            return am_city;
        }

        public void setAm_city(String am_city) {
            this.am_city = am_city;
        }

        public String getAm_comment_info() {
            return am_comment_info;
        }

        public void setAm_comment_info(String am_comment_info) {
            this.am_comment_info = am_comment_info;
        }

        public String getAm_comment_time() {
            return am_comment_time;
        }

        public void setAm_comment_time(String am_comment_time) {
            this.am_comment_time = am_comment_time;
        }

        public String getAm_id() {
            return am_id;
        }

        public void setAm_id(String am_id) {
            this.am_id = am_id;
        }

        public String getAm_uid() {
            return am_uid;
        }

        public void setAm_uid(String am_uid) {
            this.am_uid = am_uid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAm_anonymous_name() {
            return am_anonymous_name;
        }

        public void setAm_anonymous_name(String am_anonymous_name) {
            this.am_anonymous_name = am_anonymous_name;
        }
    }
}

package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class DynamicNewsInfo {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"60","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"11","dn_type":"2","dn_addtime":"1488009476"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"58","dn_uid":"40","dn_do_uid":"13","dn_mdid":"64","dn_info":"","dn_comment_info":"哈哈哈","dn_type":"2","dn_addtime":"1488007059"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"57","dn_uid":"40","dn_do_uid":"13","dn_mdid":"64","dn_info":"","dn_comment_info":"","dn_type":"1","dn_addtime":"1488007053"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"56","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"进来了","dn_type":"2","dn_addtime":"1488006925"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"55","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"","dn_type":"1","dn_addtime":"1488006922"}]}
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
         * list : [{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"60","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"11","dn_type":"2","dn_addtime":"1488009476"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"58","dn_uid":"40","dn_do_uid":"13","dn_mdid":"64","dn_info":"","dn_comment_info":"哈哈哈","dn_type":"2","dn_addtime":"1488007059"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"57","dn_uid":"40","dn_do_uid":"13","dn_mdid":"64","dn_info":"","dn_comment_info":"","dn_type":"1","dn_addtime":"1488007053"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"56","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"进来了","dn_type":"2","dn_addtime":"1488006925"},{"realname":"若曦","uid":"40","avatar":"58a66a15afb4491840.jpg","dn_id":"55","dn_uid":"40","dn_do_uid":"54","dn_mdid":"64","dn_info":"","dn_comment_info":"","dn_type":"1","dn_addtime":"1488006922"}]
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
             * realname : 若曦
             * uid : 40
             * avatar : 58a66a15afb4491840.jpg
             * dn_id : 60
             * dn_uid : 40
             * dn_do_uid : 54
             * dn_mdid : 64
             * dn_info :
             * dn_comment_info : 11
             * dn_type : 2
             * dn_addtime : 1488009476
             */

            private String realname;
            private String uid;
            private String avatar;
            private String dn_id;
            private String dn_uid;
            private String dn_do_uid;
            private String dn_mdid;
            private String dn_info;
            private String dn_comment_info;
            private String dn_type;
            private String dn_addtime;

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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDn_id() {
                return dn_id;
            }

            public void setDn_id(String dn_id) {
                this.dn_id = dn_id;
            }

            public String getDn_uid() {
                return dn_uid;
            }

            public void setDn_uid(String dn_uid) {
                this.dn_uid = dn_uid;
            }

            public String getDn_do_uid() {
                return dn_do_uid;
            }

            public void setDn_do_uid(String dn_do_uid) {
                this.dn_do_uid = dn_do_uid;
            }

            public String getDn_mdid() {
                return dn_mdid;
            }

            public void setDn_mdid(String dn_mdid) {
                this.dn_mdid = dn_mdid;
            }

            public String getDn_info() {
                return dn_info;
            }

            public void setDn_info(String dn_info) {
                this.dn_info = dn_info;
            }

            public String getDn_comment_info() {
                return dn_comment_info;
            }

            public void setDn_comment_info(String dn_comment_info) {
                this.dn_comment_info = dn_comment_info;
            }

            public String getDn_type() {
                return dn_type;
            }

            public void setDn_type(String dn_type) {
                this.dn_type = dn_type;
            }

            public String getDn_addtime() {
                return dn_addtime;
            }

            public void setDn_addtime(String dn_addtime) {
                this.dn_addtime = dn_addtime;
            }
        }
    }
}

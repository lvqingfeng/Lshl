package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class GoodsCommentBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"gc_id":"6","gc_gdid":"10","gc_goid":"16","gc_sell_uid":"3","gc_buy_uid":"9","gc_buy_realname":"张大人","gc_buy_avatar":"pay_suc.jpg","gc_comment_info":"在一起就业服务态度","gc_addtime":"1476863349","gc_reply_info":null,"gc_replytime":null}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"gc_id":"6","gc_gdid":"10","gc_goid":"16","gc_sell_uid":"3","gc_buy_uid":"9","gc_buy_realname":"张大人","gc_buy_avatar":"pay_suc.jpg","gc_comment_info":"在一起就业服务态度","gc_addtime":"1476863349","gc_reply_info":null,"gc_replytime":null}]
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
         * gc_id : 6
         * gc_gdid : 10
         * gc_goid : 16
         * gc_sell_uid : 3
         * gc_buy_uid : 9
         * gc_buy_realname : 张大人
         * gc_buy_avatar : pay_suc.jpg
         * gc_comment_info : 在一起就业服务态度
         * gc_addtime : 1476863349
         * gc_reply_info : null
         * gc_replytime : null
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
            private String gc_id;
            private String gc_gdid;
            private String gc_goid;
            private String gc_sell_uid;
            private String gc_buy_uid;
            private String gc_buy_realname;
            private String gc_buy_avatar;
            private String gc_comment_info;
            private String gc_addtime;
            private Object gc_reply_info;
            private Object gc_replytime;

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_gdid() {
                return gc_gdid;
            }

            public void setGc_gdid(String gc_gdid) {
                this.gc_gdid = gc_gdid;
            }

            public String getGc_goid() {
                return gc_goid;
            }

            public void setGc_goid(String gc_goid) {
                this.gc_goid = gc_goid;
            }

            public String getGc_sell_uid() {
                return gc_sell_uid;
            }

            public void setGc_sell_uid(String gc_sell_uid) {
                this.gc_sell_uid = gc_sell_uid;
            }

            public String getGc_buy_uid() {
                return gc_buy_uid;
            }

            public void setGc_buy_uid(String gc_buy_uid) {
                this.gc_buy_uid = gc_buy_uid;
            }

            public String getGc_buy_realname() {
                return gc_buy_realname;
            }

            public void setGc_buy_realname(String gc_buy_realname) {
                this.gc_buy_realname = gc_buy_realname;
            }

            public String getGc_buy_avatar() {
                return gc_buy_avatar;
            }

            public void setGc_buy_avatar(String gc_buy_avatar) {
                this.gc_buy_avatar = gc_buy_avatar;
            }

            public String getGc_comment_info() {
                return gc_comment_info;
            }

            public void setGc_comment_info(String gc_comment_info) {
                this.gc_comment_info = gc_comment_info;
            }

            public String getGc_addtime() {
                return gc_addtime;
            }

            public void setGc_addtime(String gc_addtime) {
                this.gc_addtime = gc_addtime;
            }

            public Object getGc_reply_info() {
                return gc_reply_info;
            }

            public void setGc_reply_info(Object gc_reply_info) {
                this.gc_reply_info = gc_reply_info;
            }

            public Object getGc_replytime() {
                return gc_replytime;
            }

            public void setGc_replytime(Object gc_replytime) {
                this.gc_replytime = gc_replytime;
            }
        }
    }
}

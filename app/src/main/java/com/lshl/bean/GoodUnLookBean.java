package com.lshl.bean;

import java.util.List;

/***
 * Created by Administrator on 2017/3/17.
 */

public class GoodUnLookBean {

    /**
     * status : 1
     * info : {"pages":1,"end":0,"list":[{"realname":"安若曦","uid":"1","avatar":"58b6376269ed26604.jpg","origin_cityname":"潍坊安丘","cg_addtime":"1488357155"},{"realname":"杨立升","uid":"6","avatar":"58b819e2b278496266.jpg","origin_cityname":"济宁梁山","cg_addtime":"1488355433"}]}
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
         * end : 0
         * list : [{"realname":"安若曦","uid":"1","avatar":"58b6376269ed26604.jpg","origin_cityname":"潍坊安丘","cg_addtime":"1488357155"},{"realname":"杨立升","uid":"6","avatar":"58b819e2b278496266.jpg","origin_cityname":"济宁梁山","cg_addtime":"1488355433"}]
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
             * realname : 安若曦
             * uid : 1
             * avatar : 58b6376269ed26604.jpg
             * origin_cityname : 潍坊安丘
             * cg_addtime : 1488357155
             */

            private String realname;
            private String uid;
            private String avatar;
            private String origin_cityname;
            private String cg_addtime;

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

            public String getOrigin_cityname() {
                return origin_cityname;
            }

            public void setOrigin_cityname(String origin_cityname) {
                this.origin_cityname = origin_cityname;
            }

            public String getCg_addtime() {
                return cg_addtime;
            }

            public void setCg_addtime(String cg_addtime) {
                this.cg_addtime = cg_addtime;
            }
        }
    }
}

package com.lshl.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RankBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"dm_uid":"9","allmoney":"3000.00","realname":"张大人","phone":"13162839599","avatar":"pay_suc.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5"},{"dm_uid":"43","allmoney":"2000.00","realname":null,"phone":"18892084259","avatar":null,"hx_id":"21a1a09dfd364628bea7d68d61641cb3"},{"dm_uid":"7","allmoney":"1300.00","realname":"王大人","phone":"18509488472","avatar":"57fa21d102ce3.png","hx_id":"0808a5ad58d717da49953d243a022575"},{"dm_uid":"3","allmoney":"900.00","realname":"魁大圣","phone":"18366189918","avatar":"58083664e2360.png","hx_id":"2fcb13c32827c61a5d040c82e03eab28"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"dm_uid":"9","allmoney":"3000.00","realname":"张大人","phone":"13162839599","avatar":"pay_suc.jpg","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5"},{"dm_uid":"43","allmoney":"2000.00","realname":null,"phone":"18892084259","avatar":null,"hx_id":"21a1a09dfd364628bea7d68d61641cb3"},{"dm_uid":"7","allmoney":"1300.00","realname":"王大人","phone":"18509488472","avatar":"57fa21d102ce3.png","hx_id":"0808a5ad58d717da49953d243a022575"},{"dm_uid":"3","allmoney":"900.00","realname":"魁大圣","phone":"18366189918","avatar":"58083664e2360.png","hx_id":"2fcb13c32827c61a5d040c82e03eab28"}]
     */

    private InfoEntity info;

    public static RankBean objectFromData(String str) {

        return new Gson().fromJson(str, RankBean.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public static class InfoEntity {
        private int pages;
        private int end;
        /**
         * dm_uid : 9
         * allmoney : 3000.00
         * realname : 张大人
         * phone : 13162839599
         * avatar : pay_suc.jpg
         * hx_id : e191c1cab6772c1aa4c6887082a4d8d5
         */

        private List<ListEntity> list;

        public static InfoEntity objectFromData(String str) {

            return new Gson().fromJson(str, InfoEntity.class);
        }

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

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            private String dm_uid;
            private String allmoney;
            private String realname;
            private String phone;
            private String avatar;
            private String hx_id;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public String getDm_uid() {
                return dm_uid;
            }

            public void setDm_uid(String dm_uid) {
                this.dm_uid = dm_uid;
            }

            public String getAllmoney() {
                return allmoney;
            }

            public void setAllmoney(String allmoney) {
                this.allmoney = allmoney;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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
        }
    }
}

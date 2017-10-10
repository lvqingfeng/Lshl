package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class KouBeiCommmentBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"sc_id":"105","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"9","sc_active_realname":"nwRllrQxx4","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":grin::grin::grin::grin::persevere:","sc_addtime":"1477128982"},{"sc_id":"106","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"9","sc_active_realname":"jFoEQ33yYE","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":":kissing_smiling_eyes::pensive::flushed::kissing_closed_eyes::blush::kissing_closed_eyes::stuck_out_tongue_closed_eyes::unamused:","sc_addtime":"1477128990"},{"sc_id":"108","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"3","sc_active_realname":"XYpeIPqSdy","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":monkey_face:","sc_addtime":"1477472382"},{"sc_id":"109","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"43","sc_active_realname":"ndIdXR4I66","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":"啊都是","sc_addtime":"1477476172"},{"sc_id":"111","sc_sid":"82","sc_anonymous":"2","sc_active_uid":"7","sc_active_realname":"王大人","sc_active_avatar":"57fa21d102ce3.png","sc_active_cityname":"位置地点","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":smile::smile::smile:","sc_addtime":"1477964853"},{"sc_id":"116","sc_sid":"82","sc_anonymous":"2","sc_active_uid":"7","sc_active_realname":"王大人","sc_active_avatar":"57fa21d102ce3.png","sc_active_cityname":"位置地点","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":"group_header@2x","sc_addtime":"1478144194"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"sc_id":"105","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"9","sc_active_realname":"nwRllrQxx4","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":grin::grin::grin::grin::persevere:","sc_addtime":"1477128982"},{"sc_id":"106","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"9","sc_active_realname":"jFoEQ33yYE","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":":kissing_smiling_eyes::pensive::flushed::kissing_closed_eyes::blush::kissing_closed_eyes::stuck_out_tongue_closed_eyes::unamused:","sc_addtime":"1477128990"},{"sc_id":"108","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"3","sc_active_realname":"XYpeIPqSdy","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":monkey_face:","sc_addtime":"1477472382"},{"sc_id":"109","sc_sid":"82","sc_anonymous":"1","sc_active_uid":"43","sc_active_realname":"ndIdXR4I66","sc_active_avatar":"","sc_active_cityname":"兰州市","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":"啊都是","sc_addtime":"1477476172"},{"sc_id":"111","sc_sid":"82","sc_anonymous":"2","sc_active_uid":"7","sc_active_realname":"王大人","sc_active_avatar":"57fa21d102ce3.png","sc_active_cityname":"位置地点","sc_passive_uid":"0","sc_passive_realname":"","sc_info":":smile::smile::smile:","sc_addtime":"1477964853"},{"sc_id":"116","sc_sid":"82","sc_anonymous":"2","sc_active_uid":"7","sc_active_realname":"王大人","sc_active_avatar":"57fa21d102ce3.png","sc_active_cityname":"位置地点","sc_passive_uid":"9","sc_passive_realname":"nwRllrQxx4","sc_info":"group_header@2x","sc_addtime":"1478144194"}]
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
         * sc_id : 105
         * sc_sid : 82
         * sc_anonymous : 1
         * sc_active_uid : 9
         * sc_active_realname : nwRllrQxx4
         * sc_active_avatar :
         * sc_active_cityname : 兰州市
         * sc_passive_uid : 0
         * sc_passive_realname :
         * sc_info : :grin::grin::grin::grin::persevere:
         * sc_addtime : 1477128982
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
            private String sc_id;
            private String sc_sid;
            private String sc_anonymous;
            private String sc_active_uid;
            private String sc_active_realname;
            private String sc_active_avatar;
            private String sc_active_cityname;
            private String sc_passive_uid;
            private String sc_passive_realname;
            private String sc_info;
            private String sc_addtime;

            public String getSc_id() {
                return sc_id;
            }

            public void setSc_id(String sc_id) {
                this.sc_id = sc_id;
            }

            public String getSc_sid() {
                return sc_sid;
            }

            public void setSc_sid(String sc_sid) {
                this.sc_sid = sc_sid;
            }

            public String getSc_anonymous() {
                return sc_anonymous;
            }

            public void setSc_anonymous(String sc_anonymous) {
                this.sc_anonymous = sc_anonymous;
            }

            public String getSc_active_uid() {
                return sc_active_uid;
            }

            public void setSc_active_uid(String sc_active_uid) {
                this.sc_active_uid = sc_active_uid;
            }

            public String getSc_active_realname() {
                return sc_active_realname;
            }

            public void setSc_active_realname(String sc_active_realname) {
                this.sc_active_realname = sc_active_realname;
            }

            public String getSc_active_avatar() {
                return sc_active_avatar;
            }

            public void setSc_active_avatar(String sc_active_avatar) {
                this.sc_active_avatar = sc_active_avatar;
            }

            public String getSc_active_cityname() {
                return sc_active_cityname;
            }

            public void setSc_active_cityname(String sc_active_cityname) {
                this.sc_active_cityname = sc_active_cityname;
            }

            public String getSc_passive_uid() {
                return sc_passive_uid;
            }

            public void setSc_passive_uid(String sc_passive_uid) {
                this.sc_passive_uid = sc_passive_uid;
            }

            public String getSc_passive_realname() {
                return sc_passive_realname;
            }

            public void setSc_passive_realname(String sc_passive_realname) {
                this.sc_passive_realname = sc_passive_realname;
            }

            public String getSc_info() {
                return sc_info;
            }

            public void setSc_info(String sc_info) {
                this.sc_info = sc_info;
            }

            public String getSc_addtime() {
                return sc_addtime;
            }

            public void setSc_addtime(String sc_addtime) {
                this.sc_addtime = sc_addtime;
            }
        }
    }
}

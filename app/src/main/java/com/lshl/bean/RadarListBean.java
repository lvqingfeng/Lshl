package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RadarListBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"pi_id":"92","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3},{"pi_id":"91","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3},{"pi_id":"90","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3}]}
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
         * list : [{"pi_id":"92","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3},{"pi_id":"91","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3},{"pi_id":"90","pi_title":"【高云飞】从北京市北京市来到了兰州市,您可以联系他哦","pi_uid":"19","pi_catid":"radar","pi_project":"10","pi_addtime":"1482313078","realname":"高云飞","hx_id":"3a26a5ef93215aa8efc2b8c0166cdccd","avatar":"58526eeada29b.jpg","authenticate":"1","president":"0","pay_starttime":null,"pay_endtime":null,"grade":3}]
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
             * pi_id : 92
             * pi_title : 【高云飞】从北京市北京市来到了兰州市,您可以联系他哦
             * pi_uid : 19
             * pi_catid : radar
             * pi_project : 10
             * pi_addtime : 1482313078
             * realname : 高云飞
             * hx_id : 3a26a5ef93215aa8efc2b8c0166cdccd
             * avatar : 58526eeada29b.jpg
             * authenticate : 1
             * president : 0
             * pay_starttime : null
             * pay_endtime : null
             * grade : 3
             */

            private String pi_id;
            private String pi_title;
            private String pi_uid;
            private String pi_catid;
            private String pi_project;
            private String pi_addtime;
            private String realname;
            private String hx_id;
            private String avatar;
            private String authenticate;
            private String president;
            private Object pay_starttime;
            private Object pay_endtime;
            private int grade;

            public String getPi_id() {
                return pi_id;
            }

            public void setPi_id(String pi_id) {
                this.pi_id = pi_id;
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

            public String getPi_catid() {
                return pi_catid;
            }

            public void setPi_catid(String pi_catid) {
                this.pi_catid = pi_catid;
            }

            public String getPi_project() {
                return pi_project;
            }

            public void setPi_project(String pi_project) {
                this.pi_project = pi_project;
            }

            public String getPi_addtime() {
                return pi_addtime;
            }

            public void setPi_addtime(String pi_addtime) {
                this.pi_addtime = pi_addtime;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getHx_id() {
                return hx_id;
            }

            public void setHx_id(String hx_id) {
                this.hx_id = hx_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAuthenticate() {
                return authenticate;
            }

            public void setAuthenticate(String authenticate) {
                this.authenticate = authenticate;
            }

            public String getPresident() {
                return president;
            }

            public void setPresident(String president) {
                this.president = president;
            }

            public Object getPay_starttime() {
                return pay_starttime;
            }

            public void setPay_starttime(Object pay_starttime) {
                this.pay_starttime = pay_starttime;
            }

            public Object getPay_endtime() {
                return pay_endtime;
            }

            public void setPay_endtime(Object pay_endtime) {
                this.pay_endtime = pay_endtime;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }
        }
    }
}

package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class LookMeBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"realname":"若兮","avatar":"588381bc864f958992.jpg","origin_cityname":"山东省 济南市不轻啊","re_id":"64","re_active_look":"11","re_passive_look":"3","re_addtime":"1486977591"},{"realname":"红红火火","avatar":"589a853ea054963463.png","origin_cityname":"山东省","re_id":"61","re_active_look":"29","re_passive_look":"3","re_addtime":"1486972204"},{"realname":"哈哈","avatar":"58821e8ebbb7773410.png","origin_cityname":"泰安市 ","re_id":"44","re_active_look":"13","re_passive_look":"3","re_addtime":"1486806821"}]}
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
         * list : [{"realname":"若兮","avatar":"588381bc864f958992.jpg","origin_cityname":"山东省 济南市不轻啊","re_id":"64","re_active_look":"11","re_passive_look":"3","re_addtime":"1486977591"},{"realname":"红红火火","avatar":"589a853ea054963463.png","origin_cityname":"山东省","re_id":"61","re_active_look":"29","re_passive_look":"3","re_addtime":"1486972204"},{"realname":"哈哈","avatar":"58821e8ebbb7773410.png","origin_cityname":"泰安市 ","re_id":"44","re_active_look":"13","re_passive_look":"3","re_addtime":"1486806821"}]
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
             * realname : 若兮
             * avatar : 588381bc864f958992.jpg
             * origin_cityname : 山东省 济南市不轻啊
             * re_id : 64
             * re_active_look : 11
             * re_passive_look : 3
             * re_addtime : 1486977591
             */

            private String realname;
            private String avatar;
            private String origin_cityname;
            private String re_id;
            private String re_active_look;
            private String re_passive_look;
            private String re_addtime;

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

            public String getOrigin_cityname() {
                return origin_cityname;
            }

            public void setOrigin_cityname(String origin_cityname) {
                this.origin_cityname = origin_cityname;
            }

            public String getRe_id() {
                return re_id;
            }

            public void setRe_id(String re_id) {
                this.re_id = re_id;
            }

            public String getRe_active_look() {
                return re_active_look;
            }

            public void setRe_active_look(String re_active_look) {
                this.re_active_look = re_active_look;
            }

            public String getRe_passive_look() {
                return re_passive_look;
            }

            public void setRe_passive_look(String re_passive_look) {
                this.re_passive_look = re_passive_look;
            }

            public String getRe_addtime() {
                return re_addtime;
            }

            public void setRe_addtime(String re_addtime) {
                this.re_addtime = re_addtime;
            }
        }
    }
}

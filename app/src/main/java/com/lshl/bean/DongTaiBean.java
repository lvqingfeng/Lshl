package com.lshl.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class DongTaiBean {

    /**
     * status : 1
     * info : {"pages":5,"end":0,"list":[{"did":"4","title":"测试动态1","imgs":"2651357c68f3a60c70.jpeg","add_time":"1472630586"},{"did":"5","title":"动态2","imgs":"1213857c68f6286218.jpeg","add_time":"1472630626"},{"did":"6","title":"动态3","imgs":"187457c68fc366648.jpeg","add_time":"1472630723"}]}
     */

    private int status;
    private InfoEntity info;

    public static DongTaiBean objectFromData(String str) {

        return new Gson().fromJson(str, DongTaiBean.class);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public static class InfoEntity {
        /**
         * pages : 5
         * end : 0
         * list : [{"did":"4","title":"测试动态1","imgs":"2651357c68f3a60c70.jpeg","add_time":"1472630586"},{"did":"5","title":"动态2","imgs":"1213857c68f6286218.jpeg","add_time":"1472630626"},{"did":"6","title":"动态3","imgs":"187457c68fc366648.jpeg","add_time":"1472630723"}]
         */

        private int pages;
        private int end;
        private List<ListEntity> list;

        public static InfoEntity objectFromData(String str) {

            return new Gson().fromJson(str, InfoEntity.class);
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getPages() {
            return pages;
        }

        public int getEnd() {
            return end;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * did : 4
             * title : 测试动态1
             * imgs : 2651357c68f3a60c70.jpeg
             * add_time : 1472630586
             */

            private String did;
            private String title;
            private String imgs;
            private String add_time;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public void setDid(String did) {
                this.did = did;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getDid() {
                return did;
            }

            public String getTitle() {
                return title;
            }

            public String getImgs() {
                return imgs;
            }

            public String getAdd_time() {
                return add_time;
            }
        }
    }
}

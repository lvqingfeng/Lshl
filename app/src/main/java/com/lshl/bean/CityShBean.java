package com.lshl.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CityShBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"did":"1","title":"测试标题","add_time":"1471254133","imgs":"1979957b19154711cb.jpeg"},{"did":"19","title":"鲁商互联举行换届仪式","add_time":"1474249377","imgs":"1004157df42a18ffce.jpeg"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"did":"1","title":"测试标题","add_time":"1471254133","imgs":"1979957b19154711cb.jpeg"},{"did":"19","title":"鲁商互联举行换届仪式","add_time":"1474249377","imgs":"1004157df42a18ffce.jpeg"}]
     */

    private InfoEntity info;

    public static CityShBean objectFromData(String str) {

        return new Gson().fromJson(str, CityShBean.class);
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
         * did : 1
         * title : 测试标题
         * add_time : 1471254133
         * imgs : 1979957b19154711cb.jpeg
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
            private String did;
            private String title;
            private String add_time;
            private String imgs;

            public static ListEntity objectFromData(String str) {

                return new Gson().fromJson(str, ListEntity.class);
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }
        }
    }
}

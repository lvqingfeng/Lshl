package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 互助
 * Created by Administrator on 2016/11/7.
 */

public class MyMutualBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"ma_id":"26","ma_project_name":"急救","ma_project_money":"58484.00","ma_project_img":"5835a019cbb05.png","ma_status":"2","img":"/Data/Uploads/MutualAid/5835a019cbb05.png"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"ma_id":"26","ma_project_name":"急救","ma_project_money":"58484.00","ma_project_img":"5835a019cbb05.png","ma_status":"2","img":"/Data/Uploads/MutualAid/5835a019cbb05.png"}]
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
         * ma_id : 26
         * ma_project_name : 急救
         * ma_project_money : 58484.00
         * ma_project_img : 5835a019cbb05.png
         * ma_status : 2
         * img : /Data/Uploads/MutualAid/5835a019cbb05.png
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
            private String ma_id;
            private String ma_project_name;
            private String ma_project_money;
            private String ma_project_img;
            private String ma_status;
            private String img;

            public String getMa_id() {
                return ma_id;
            }

            public void setMa_id(String ma_id) {
                this.ma_id = ma_id;
            }

            public String getMa_project_name() {
                return ma_project_name;
            }

            public void setMa_project_name(String ma_project_name) {
                this.ma_project_name = ma_project_name;
            }

            public String getMa_project_money() {
                return ma_project_money;
            }

            public void setMa_project_money(String ma_project_money) {
                this.ma_project_money = ma_project_money;
            }

            public String getMa_project_img() {
                return ma_project_img;
            }

            public void setMa_project_img(String ma_project_img) {
                this.ma_project_img = ma_project_img;
            }

            public String getMa_status() {
                return ma_status;
            }

            public void setMa_status(String ma_status) {
                this.ma_status = ma_status;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

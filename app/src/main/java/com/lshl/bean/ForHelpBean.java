package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class ForHelpBean {


    /**
     * pages : 2
     * end : 0
     * list : [{"ma_id":"21","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442f78bbed.png","ma_status":"12","img":"/Data/Uploads/MutualAid/583442f78bbed.png"},{"ma_id":"14","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442e5010ce.png","ma_status":"12","img":"/Data/Uploads/MutualAid/583442e5010ce.png"},{"ma_id":"15","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442e770e44.png","ma_status":"22","img":"/Data/Uploads/MutualAid/583442e770e44.png"},{"ma_id":"23","ma_project_name":"awe","ma_project_money":"1234.00","ma_project_img":"5834e51513b5e.png","ma_status":"32","img":"/Data/Uploads/MutualAid/5834e51513b5e.png"},{"ma_id":"16","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442e9d455b.png","ma_status":"32","img":"/Data/Uploads/MutualAid/583442e9d455b.png"},{"ma_id":"17","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442ec3f1b6.png","ma_status":"42","img":"/Data/Uploads/MutualAid/583442ec3f1b6.png"},{"ma_id":"18","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442ef9d507.png","ma_status":"52","img":"/Data/Uploads/MutualAid/583442ef9d507.png"},{"ma_id":"11","ma_project_name":"213213","ma_project_money":"321312.00","ma_project_img":"58344219e7d7e.png","ma_status":"52","img":"/Data/Uploads/MutualAid/58344219e7d7e.png"},{"ma_id":"19","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442f26a8ea.png","ma_status":"62","img":"/Data/Uploads/MutualAid/583442f26a8ea.png"},{"ma_id":"12","ma_project_name":"1","ma_project_money":"2324.00","ma_project_img":"583442ded9b72.png","ma_status":"62","img":"/Data/Uploads/MutualAid/583442ded9b72.png"}]
     */

    private int pages;
    private int end;
    /**
     * ma_id : 21
     * ma_project_name : 1
     * ma_project_money : 2324.00
     * ma_project_img : 583442f78bbed.png
     * ma_status : 12
     * img : /Data/Uploads/MutualAid/583442f78bbed.png
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

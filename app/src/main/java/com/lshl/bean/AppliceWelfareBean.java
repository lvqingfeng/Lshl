package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class AppliceWelfareBean implements Serializable {

    /**
     * pages : 2
     * end : 0
     * list : [{"pw_id":"3","pw_project_img":"58343a77c6276.png","pw_project_name":"the problem was ","pw_project_money":"20.00","pw_status":"2","img":"/Data/Uploads/PublicWelfare/58343a77c6276.png"},{"pw_id":"4","pw_project_img":"5834427c65a2d.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"12","img":"/Data/Uploads/PublicWelfare/5834427c65a2d.png"},{"pw_id":"5","pw_project_img":"5834429169a5d.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"22","img":"/Data/Uploads/PublicWelfare/5834429169a5d.png"},{"pw_id":"11","pw_project_img":"583442a6d6ce6.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"22","img":"/Data/Uploads/PublicWelfare/583442a6d6ce6.png"},{"pw_id":"6","pw_project_img":"5834429815c97.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"32","img":"/Data/Uploads/PublicWelfare/5834429815c97.png"},{"pw_id":"12","pw_project_img":"583442a981b73.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"32","img":"/Data/Uploads/PublicWelfare/583442a981b73.png"},{"pw_id":"7","pw_project_img":"5834429a98b3d.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"42","img":"/Data/Uploads/PublicWelfare/5834429a98b3d.png"},{"pw_id":"13","pw_project_img":"583442acdef81.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"42","img":"/Data/Uploads/PublicWelfare/583442acdef81.png"},{"pw_id":"15","pw_project_img":"5834e4d978957.png","pw_project_name":"wewqe","pw_project_money":"1111.00","pw_status":"52","img":"/Data/Uploads/PublicWelfare/5834e4d978957.png"},{"pw_id":"8","pw_project_img":"5834429e6e4cc.png","pw_project_name":"3213123123","pw_project_money":"999999.99","pw_status":"52","img":"/Data/Uploads/PublicWelfare/5834429e6e4cc.png"}]
     */

    private int pages;
    private int end;
    /**
     * pw_id : 3
     * pw_project_img : 58343a77c6276.png
     * pw_project_name : the problem was
     * pw_project_money : 20.00
     * pw_status : 2
     * img : /Data/Uploads/PublicWelfare/58343a77c6276.png
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
        private String pw_id;
        private String pw_project_img;
        private String pw_project_name;
        private String pw_project_money;
        private String pw_status;
        private String img;

        public String getPw_id() {
            return pw_id;
        }

        public void setPw_id(String pw_id) {
            this.pw_id = pw_id;
        }

        public String getPw_project_img() {
            return pw_project_img;
        }

        public void setPw_project_img(String pw_project_img) {
            this.pw_project_img = pw_project_img;
        }

        public String getPw_project_name() {
            return pw_project_name;
        }

        public void setPw_project_name(String pw_project_name) {
            this.pw_project_name = pw_project_name;
        }

        public String getPw_project_money() {
            return pw_project_money;
        }

        public void setPw_project_money(String pw_project_money) {
            this.pw_project_money = pw_project_money;
        }

        public String getPw_status() {
            return pw_status;
        }

        public void setPw_status(String pw_status) {
            this.pw_status = pw_status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

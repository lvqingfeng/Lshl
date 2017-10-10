package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月14日
 * 时间：11:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ReputationBean {

    /**
     * pages : 1
     * end : 1
     * list : [{"s_id":"9","s_uid":"4","s_title":"猴年马月什么用某美女小游戏格罗姆之血在国美女小游戏是浪漫主义","s_info":"还咯呕吐有我也数据模型在浪漫主义是就默默格罗姆之血在哈佛前几年Sony wrong国美女小游戏泡沫后来美女小游戏在他们的局域网管理发信息技术人员名单公布的时候就","s_img":"5870ace2733cc64554.png,5870ace2733cc938.png,5870ace2733cc94794.png,5870ace2733cc82658.png,5870ace2733cc79888.png","s_city":"甘肃省兰州市","s_type":"2","s_status":"1","s_addtime":"1483779298","s_editinfo":null,"s_edittime":null,"realname":"吕清锋","img":"5870ace2733cc64554.png"},{"s_id":"8","s_uid":"4","s_title":"据寂寞","s_info":"塔里木","s_img":"5870aa165c59613659.png,5870aa165c59665739.png","s_city":"甘肃省兰州市","s_type":"1","s_status":"1","s_addtime":"1483778582","s_editinfo":null,"s_edittime":null,"realname":"吕清锋","img":"5870aa165c59613659.png"}]
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
         * s_id : 9
         * s_uid : 4
         * s_title : 猴年马月什么用某美女小游戏格罗姆之血在国美女小游戏是浪漫主义
         * s_info : 还咯呕吐有我也数据模型在浪漫主义是就默默格罗姆之血在哈佛前几年Sony wrong国美女小游戏泡沫后来美女小游戏在他们的局域网管理发信息技术人员名单公布的时候就
         * s_img : 5870ace2733cc64554.png,5870ace2733cc938.png,5870ace2733cc94794.png,5870ace2733cc82658.png,5870ace2733cc79888.png
         * s_city : 甘肃省兰州市
         * s_type : 2
         * s_status : 1
         * s_addtime : 1483779298
         * s_editinfo : null
         * s_edittime : null
         * realname : 吕清锋
         * img : 5870ace2733cc64554.png
         */

        private String s_id;
        private String s_uid;
        private String s_title;
        private String s_info;
        private String s_img;
        private String s_city;
        private String s_type;
        private String s_status;
        private String s_addtime;
        private Object s_editinfo;
        private Object s_edittime;
        private String realname;
        private String img;

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getS_uid() {
            return s_uid;
        }

        public void setS_uid(String s_uid) {
            this.s_uid = s_uid;
        }

        public String getS_title() {
            return s_title;
        }

        public void setS_title(String s_title) {
            this.s_title = s_title;
        }

        public String getS_info() {
            return s_info;
        }

        public void setS_info(String s_info) {
            this.s_info = s_info;
        }

        public String getS_img() {
            return s_img;
        }

        public void setS_img(String s_img) {
            this.s_img = s_img;
        }

        public String getS_city() {
            return s_city;
        }

        public void setS_city(String s_city) {
            this.s_city = s_city;
        }

        public String getS_type() {
            return s_type;
        }

        public void setS_type(String s_type) {
            this.s_type = s_type;
        }

        public String getS_status() {
            return s_status;
        }

        public void setS_status(String s_status) {
            this.s_status = s_status;
        }

        public String getS_addtime() {
            return s_addtime;
        }

        public void setS_addtime(String s_addtime) {
            this.s_addtime = s_addtime;
        }

        public Object getS_editinfo() {
            return s_editinfo;
        }

        public void setS_editinfo(Object s_editinfo) {
            this.s_editinfo = s_editinfo;
        }

        public Object getS_edittime() {
            return s_edittime;
        }

        public void setS_edittime(Object s_edittime) {
            this.s_edittime = s_edittime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

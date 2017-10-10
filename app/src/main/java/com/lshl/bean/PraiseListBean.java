package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月13日
 * 时间：16:45
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PraiseListBean {


    /**
     * end : 1
     * list : [{"authenticate":"1","avatar":"584b714a76e90.png","grade":3,"origin_cityname":"山东省 济宁市","origin_county":"嘉祥县","p_addtime":"1481618084","p_cuid":"5","p_id":"5","p_puid":"4","realname":"局长"}]
     * pages : 1
     */

    private int end;
    private int pages;
    private List<ListBean> list;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * authenticate : 1
         * avatar : 584b714a76e90.png
         * grade : 3
         * origin_cityname : 山东省 济宁市
         * origin_county : 嘉祥县
         * p_addtime : 1481618084
         * p_cuid : 5
         * p_id : 5
         * p_puid : 4
         * realname : 局长
         */

        private String authenticate;
        private String avatar;
        private int grade;
        private String origin_cityname;
        private String origin_county;
        private String p_addtime;
        private String p_cuid;
        private String p_id;
        private String p_puid;
        private String realname;

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getOrigin_cityname() {
            return origin_cityname;
        }

        public void setOrigin_cityname(String origin_cityname) {
            this.origin_cityname = origin_cityname;
        }

        public String getOrigin_county() {
            return origin_county;
        }

        public void setOrigin_county(String origin_county) {
            this.origin_county = origin_county;
        }

        public String getP_addtime() {
            return p_addtime;
        }

        public void setP_addtime(String p_addtime) {
            this.p_addtime = p_addtime;
        }

        public String getP_cuid() {
            return p_cuid;
        }

        public void setP_cuid(String p_cuid) {
            this.p_cuid = p_cuid;
        }

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_puid() {
            return p_puid;
        }

        public void setP_puid(String p_puid) {
            this.p_puid = p_puid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}

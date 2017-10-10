package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class RankingBean  {

    /**
     * status : 1
     * info : {"Totalcontribution":"96700.00","pages":1,"end":0,"list":[{"all_money":"70500.00","dm_customer_uid":"3","dm_customer_type":"1","realname":"魁大圣","avatar":"583419eec86fc.jpg","member_grade":null,"live_cityname":"nullnull","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","gread":null},{"all_money":"10000.00","dm_customer_uid":"10","dm_customer_type":"1","realname":"王麻子","avatar":"8.jpg","member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/8.jpg","name":"王麻子","cityname":"","gread":null},{"all_money":"6200.00","dm_customer_uid":"9","dm_customer_type":"1","realname":"I'm ","avatar":"583394a95408c.png","member_grade":null,"live_cityname":"北京市 北京","img":"/Data/Uploads/avatar/583394a95408c.png","name":"I'm ","cityname":"","gread":null},{"all_money":"5000.00","dm_customer_uid":"7","dm_customer_type":"1","realname":"王大人","avatar":"57fa21d102ce3.png","member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/57fa21d102ce3.png","name":"王大人","cityname":"","gread":null},{"all_money":"5000.00","dm_customer_uid":"23","dm_customer_type":"1","realname":"杨少侠","avatar":null,"member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/","name":"杨少侠","cityname":"","gread":null}]}
     */

    private int status;
    /**
     * Totalcontribution : 96700.00
     * pages : 1
     * end : 0
     * list : [{"all_money":"70500.00","dm_customer_uid":"3","dm_customer_type":"1","realname":"魁大圣","avatar":"583419eec86fc.jpg","member_grade":null,"live_cityname":"nullnull","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","gread":null},{"all_money":"10000.00","dm_customer_uid":"10","dm_customer_type":"1","realname":"王麻子","avatar":"8.jpg","member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/8.jpg","name":"王麻子","cityname":"","gread":null},{"all_money":"6200.00","dm_customer_uid":"9","dm_customer_type":"1","realname":"I'm ","avatar":"583394a95408c.png","member_grade":null,"live_cityname":"北京市 北京","img":"/Data/Uploads/avatar/583394a95408c.png","name":"I'm ","cityname":"","gread":null},{"all_money":"5000.00","dm_customer_uid":"7","dm_customer_type":"1","realname":"王大人","avatar":"57fa21d102ce3.png","member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/57fa21d102ce3.png","name":"王大人","cityname":"","gread":null},{"all_money":"5000.00","dm_customer_uid":"23","dm_customer_type":"1","realname":"杨少侠","avatar":null,"member_grade":null,"live_cityname":"甘肃兰州","img":"/Data/Uploads/avatar/","name":"杨少侠","cityname":"","gread":null}]
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
        private String Totalcontribution;
        private int pages;
        private int end;
        /**
         * all_money : 70500.00
         * dm_customer_uid : 3
         * dm_customer_type : 1
         * realname : 魁大圣
         * avatar : 583419eec86fc.jpg
         * member_grade : null
         * live_cityname : nullnull
         * img : /Data/Uploads/avatar/583419eec86fc.jpg
         * name : 魁大圣
         * cityname :
         * gread : null
         */

        private List<ListBean> list;

        public String getTotalcontribution() {
            return Totalcontribution;
        }

        public void setTotalcontribution(String Totalcontribution) {
            this.Totalcontribution = Totalcontribution;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String all_money;
            private String dm_customer_uid;
            private String dm_customer_type;
            private String realname;
            private String avatar;
            private Object member_grade;
            private String live_cityname;
            private String img;
            private String name;
            private String cityname;
            private Object gread;

            public String getAll_money() {
                return all_money;
            }

            public void setAll_money(String all_money) {
                this.all_money = all_money;
            }

            public String getDm_customer_uid() {
                return dm_customer_uid;
            }

            public void setDm_customer_uid(String dm_customer_uid) {
                this.dm_customer_uid = dm_customer_uid;
            }

            public String getDm_customer_type() {
                return dm_customer_type;
            }

            public void setDm_customer_type(String dm_customer_type) {
                this.dm_customer_type = dm_customer_type;
            }

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

            public Object getMember_grade() {
                return member_grade;
            }

            public void setMember_grade(Object member_grade) {
                this.member_grade = member_grade;
            }

            public String getLive_cityname() {
                return live_cityname;
            }

            public void setLive_cityname(String live_cityname) {
                this.live_cityname = live_cityname;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public Object getGread() {
                return gread;
            }

            public void setGread(Object gread) {
                this.gread = gread;
            }
        }
    }
}

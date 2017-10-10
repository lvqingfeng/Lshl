package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class SearchResultBean {

    /**
     * status : 1
     * info : {"img":"/Data/Uploads/avatar/583394a95408c.png","name":"I'm ","cityname":"北京市 北京","gread":null,"all_money":"6200.00","all_num":"2","list":[{"dm_money":"200.00","dm_addtime":"1479203243"},{"dm_money":"6000.00","dm_addtime":"1479203909"}]}
     */

    private int status;
    /**
     * img : /Data/Uploads/avatar/583394a95408c.png
     * name : I'm
     * cityname : 北京市 北京
     * gread : null
     * all_money : 6200.00
     * all_num : 2
     * list : [{"dm_money":"200.00","dm_addtime":"1479203243"},{"dm_money":"6000.00","dm_addtime":"1479203909"}]
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
        private String img;
        private String name;
        private String cityname;
        private Object gread;
        private String all_money;
        private String all_num;
        /**
         * dm_money : 200.00
         * dm_addtime : 1479203243
         */

        private List<ListBean> list;

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

        public String getAll_money() {
            return all_money;
        }

        public void setAll_money(String all_money) {
            this.all_money = all_money;
        }

        public String getAll_num() {
            return all_num;
        }

        public void setAll_num(String all_num) {
            this.all_num = all_num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String dm_money;
            private String dm_addtime;

            public String getDm_money() {
                return dm_money;
            }

            public void setDm_money(String dm_money) {
                this.dm_money = dm_money;
            }

            public String getDm_addtime() {
                return dm_addtime;
            }

            public void setDm_addtime(String dm_addtime) {
                this.dm_addtime = dm_addtime;
            }
        }
    }
}

package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class YingyongJuanKuanBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":2,"end":0,"list":[{"dm_id":"13","dm_fund_type":"2","dm_customer_type":"3","dm_customer_uid":"2","dm_money":"3000.00","dm_status":"1","dm_addtime":"1479202191","img":"/Data/Uploads/enterprise/4.jpg","name":"天水市企业","cityname":"甘肃","grade":""},{"dm_id":"12","dm_fund_type":"2","dm_customer_type":"3","dm_customer_uid":"1","dm_money":"20000.00","dm_status":"1","dm_addtime":"1467859547","img":"/Data/Uploads/enterprise/3.jpg","name":"兰州市企业","cityname":"甘肃省兰州市","grade":""},{"dm_id":"11","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"2","dm_money":"50000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/business/3.jpg","name":"天水市山东商会","cityname":"天水市","grade":""},{"dm_id":"10","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"1","dm_money":"20000.00","dm_status":"1","dm_addtime":"1479203909","img":"/Data/Uploads/business/2056582ed1d27b4a8.jpeg","name":"甘肃省山东商会","cityname":"兰州市","grade":""},{"dm_id":"9","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"1","dm_money":"100000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/business/2056582ed1d27b4a8.jpeg","name":"甘肃省山东商会","cityname":"兰州市","grade":""},{"dm_id":"8","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"9","dm_money":"6000.00","dm_status":"1","dm_addtime":"1479203909","img":"/Data/Uploads/avatar/583394a95408c.png","name":"I'm ","cityname":"","grade":null},{"dm_id":"7","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"23","dm_money":"5000.00","dm_status":"1","dm_addtime":"1479203819","img":"/Data/Uploads/avatar/","name":"杨少侠","cityname":"","grade":null},{"dm_id":"6","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"10","dm_money":"10000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/avatar/8.jpg","name":"王麻子","cityname":"","grade":null},{"dm_id":"5","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"3","dm_money":"60000.00","dm_status":"1","dm_addtime":"1479203548","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","grade":null},{"dm_id":"4","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"3","dm_money":"500.00","dm_status":"1","dm_addtime":"1479203394","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","grade":null}]}
     */

    private int status;
    /**
     * pages : 2
     * end : 0
     * list : [{"dm_id":"13","dm_fund_type":"2","dm_customer_type":"3","dm_customer_uid":"2","dm_money":"3000.00","dm_status":"1","dm_addtime":"1479202191","img":"/Data/Uploads/enterprise/4.jpg","name":"天水市企业","cityname":"甘肃","grade":""},{"dm_id":"12","dm_fund_type":"2","dm_customer_type":"3","dm_customer_uid":"1","dm_money":"20000.00","dm_status":"1","dm_addtime":"1467859547","img":"/Data/Uploads/enterprise/3.jpg","name":"兰州市企业","cityname":"甘肃省兰州市","grade":""},{"dm_id":"11","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"2","dm_money":"50000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/business/3.jpg","name":"天水市山东商会","cityname":"天水市","grade":""},{"dm_id":"10","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"1","dm_money":"20000.00","dm_status":"1","dm_addtime":"1479203909","img":"/Data/Uploads/business/2056582ed1d27b4a8.jpeg","name":"甘肃省山东商会","cityname":"兰州市","grade":""},{"dm_id":"9","dm_fund_type":"2","dm_customer_type":"2","dm_customer_uid":"1","dm_money":"100000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/business/2056582ed1d27b4a8.jpeg","name":"甘肃省山东商会","cityname":"兰州市","grade":""},{"dm_id":"8","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"9","dm_money":"6000.00","dm_status":"1","dm_addtime":"1479203909","img":"/Data/Uploads/avatar/583394a95408c.png","name":"I'm ","cityname":"","grade":null},{"dm_id":"7","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"23","dm_money":"5000.00","dm_status":"1","dm_addtime":"1479203819","img":"/Data/Uploads/avatar/","name":"杨少侠","cityname":"","grade":null},{"dm_id":"6","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"10","dm_money":"10000.00","dm_status":"1","dm_addtime":"1479203808","img":"/Data/Uploads/avatar/8.jpg","name":"王麻子","cityname":"","grade":null},{"dm_id":"5","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"3","dm_money":"60000.00","dm_status":"1","dm_addtime":"1479203548","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","grade":null},{"dm_id":"4","dm_fund_type":"2","dm_customer_type":"1","dm_customer_uid":"3","dm_money":"500.00","dm_status":"1","dm_addtime":"1479203394","img":"/Data/Uploads/avatar/583419eec86fc.jpg","name":"魁大圣","cityname":"","grade":null}]
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
         * dm_id : 13
         * dm_fund_type : 2
         * dm_customer_type : 3
         * dm_customer_uid : 2
         * dm_money : 3000.00
         * dm_status : 1
         * dm_addtime : 1479202191
         * img : /Data/Uploads/enterprise/4.jpg
         * name : 天水市企业
         * cityname : 甘肃
         * grade :
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
            private String dm_id;
            private String dm_fund_type;
            private String dm_customer_type;
            private String dm_customer_uid;
            private String dm_money;
            private String dm_status;
            private String dm_addtime;
            private String img;
            private String name;
            private String cityname;
            private String grade;

            public String getDm_id() {
                return dm_id;
            }

            public void setDm_id(String dm_id) {
                this.dm_id = dm_id;
            }

            public String getDm_fund_type() {
                return dm_fund_type;
            }

            public void setDm_fund_type(String dm_fund_type) {
                this.dm_fund_type = dm_fund_type;
            }

            public String getDm_customer_type() {
                return dm_customer_type;
            }

            public void setDm_customer_type(String dm_customer_type) {
                this.dm_customer_type = dm_customer_type;
            }

            public String getDm_customer_uid() {
                return dm_customer_uid;
            }

            public void setDm_customer_uid(String dm_customer_uid) {
                this.dm_customer_uid = dm_customer_uid;
            }

            public String getDm_money() {
                return dm_money;
            }

            public void setDm_money(String dm_money) {
                this.dm_money = dm_money;
            }

            public String getDm_status() {
                return dm_status;
            }

            public void setDm_status(String dm_status) {
                this.dm_status = dm_status;
            }

            public String getDm_addtime() {
                return dm_addtime;
            }

            public void setDm_addtime(String dm_addtime) {
                this.dm_addtime = dm_addtime;
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

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }
        }
    }
}

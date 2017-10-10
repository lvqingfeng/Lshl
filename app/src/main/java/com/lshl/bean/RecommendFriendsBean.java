package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public class RecommendFriendsBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":2,"end":0,"list":[{"realname":"鲁商互联","sex":"1","avatar":"58b77062eba0466266.jpg","age":"1990年3月2日 ","origin_cityname":"济南","origin_county":"市中","uid":"16","jingdu":"103.743562","weidu":"36.096561","l_distance":0,"distance":0},{"realname":"鲁商互联","sex":"1","avatar":"58b768336dbdb97358.jpg","age":"1991年10月16日 ","origin_cityname":"济宁梁山","origin_county":" ","uid":"3","jingdu":"103.74356","weidu":"36.096577","l_distance":2,"distance":2},{"realname":"安若曦","sex":"2","avatar":"58b6376269ed26604.jpg","age":"2000年1月1日 ","origin_cityname":"潍坊","origin_county":"安丘","uid":"1","jingdu":"103.743566","weidu":"36.096593","l_distance":4,"distance":4},{"realname":"鲁商互联","sex":"1","avatar":"58b7f0472553791566.jpg","age":"1993年12月25日","origin_cityname":"烟台","origin_county":"芝罘","uid":"2","jingdu":"103.743566","weidu":"36.096596","l_distance":4,"distance":4},{"realname":"刘","sex":"1","avatar":"58b6394202edf66004.jpg","age":"1982年12月3日 ","origin_cityname":"济南","origin_county":"市中","uid":"8","jingdu":"103.743583","weidu":"36.096615","l_distance":6,"distance":6},{"realname":"王龙","sex":"1","avatar":"58b63a6ccd1bc79775.jpg","age":"1994年9月27日 ","origin_cityname":"济南","origin_county":"市中","uid":"7","jingdu":"103.74358","weidu":"36.096617","l_distance":6,"distance":6},{"realname":"夏太热","sex":"2","avatar":"58b6416d624c061172.png","age":"2017年03月01日","origin_cityname":"莱芜 钢城","origin_county":"","uid":"10","jingdu":"103.743633","weidu":"36.096613","l_distance":9,"distance":9},{"realname":"杜甫","sex":"1","avatar":"58b826379f65756302.jpg","age":"1992年11月10日 ","origin_cityname":"济南市","origin_county":"槐荫区","uid":"43","jingdu":"103.743695","weidu":"36.096506","l_distance":13,"distance":13},{"realname":"王立莹","sex":"2","avatar":"58b7ebebd89de35521.jpg","age":"1989年10月15日 ","origin_cityname":"济宁汶上","origin_county":" ","uid":"19","jingdu":"103.743549","weidu":"36.096827","l_distance":30,"distance":30},{"realname":"马小马","sex":"1","avatar":"58b63f4e1230339414.png","age":"1992年12月14日 ","origin_cityname":"济宁","origin_county":"","uid":"9","jingdu":"103.743488","weidu":"36.096896","l_distance":38,"distance":38},{"realname":"魁大圣","sex":"1","avatar":"58b670e456da595554.png","age":"1987年03月01日","origin_cityname":"济南天桥","origin_county":"","uid":"12","jingdu":"103.743572","weidu":"36.096941","l_distance":42,"distance":42},{"realname":"魁先生","sex":"1","avatar":"58b7787ccd1bc3876.png","age":"1987年03月01日","origin_cityname":"济南 历下","origin_county":"","uid":"4","jingdu":"103.744554","weidu":"36.094023","l_distance":296,"distance":296},{"realname":"王会","sex":"1","avatar":"58b82ec19023379327.png","age":"1984年03月24日","origin_cityname":"聊城 东昌府","origin_county":"","uid":"45","jingdu":"103.720341","weidu":"36.103851","l_distance":2237,"distance":"2.2 千"},{"realname":"侯典申","sex":"1","avatar":"58b822c8e7e027806.png","age":"1981年07月29日","origin_cityname":"济宁 梁山","origin_county":"","uid":"38","jingdu":"103.781957","weidu":"36.076363","l_distance":4114,"distance":"4.1 千"},{"realname":"王广禄","sex":"1","avatar":"58b8162d0e70161624.jpg","age":"1973年10月16日 ","origin_cityname":"济南天桥","origin_county":" ","uid":"22","jingdu":"103.824839","weidu":"36.06166","l_distance":8266,"distance":"8.3 千"},{"realname":"徐斌","sex":"1","avatar":"58b8207d9f65753350.png","age":"1979年10月10日","origin_cityname":"菏泽 郓城","origin_county":"","uid":"32","jingdu":"103.856112","weidu":"36.044635","l_distance":11640,"distance":"11.6 千"},{"realname":"刘海军","sex":"1","avatar":"58b81f5cbde9f15096.png","age":"1981年06月26日","origin_cityname":"聊城 东阿","origin_county":"","uid":"30","jingdu":"101.769058","weidu":"36.664212","l_distance":187572,"distance":"187.6 千"},{"realname":"葛玲玲","sex":"2","avatar":"58b826b8b278419096.png","age":"1969年04月21日","origin_cityname":"威海 文登","origin_county":"","uid":"41","jingdu":"106.684734","weidu":"35.552971","l_distance":271789,"distance":"271.8 千"},{"realname":"朱彦国","sex":"1","avatar":"58b6381a43c786013.jpg","age":"1987年3月1日 ","origin_cityname":"济南","origin_county":"市中","uid":"5","jingdu":"100.495485","weidu":"38.93052","l_distance":425549,"distance":"425.5 千"},{"realname":"杨立升","sex":"1","avatar":"58b819e2b278496266.jpg","age":"1972年1月11日 ","origin_cityname":"济宁","origin_county":"梁山","uid":"6","jingdu":"100.495504","weidu":"38.930527","l_distance":425549,"distance":"425.5 千"}]}
     */

    private int status;
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
        /**
         * pages : 2
         * end : 0
         * list : [{"realname":"鲁商互联","sex":"1","avatar":"58b77062eba0466266.jpg","age":"1990年3月2日 ","origin_cityname":"济南","origin_county":"市中","uid":"16","jingdu":"103.743562","weidu":"36.096561","l_distance":0,"distance":0},{"realname":"鲁商互联","sex":"1","avatar":"58b768336dbdb97358.jpg","age":"1991年10月16日 ","origin_cityname":"济宁梁山","origin_county":" ","uid":"3","jingdu":"103.74356","weidu":"36.096577","l_distance":2,"distance":2},{"realname":"安若曦","sex":"2","avatar":"58b6376269ed26604.jpg","age":"2000年1月1日 ","origin_cityname":"潍坊","origin_county":"安丘","uid":"1","jingdu":"103.743566","weidu":"36.096593","l_distance":4,"distance":4},{"realname":"鲁商互联","sex":"1","avatar":"58b7f0472553791566.jpg","age":"1993年12月25日","origin_cityname":"烟台","origin_county":"芝罘","uid":"2","jingdu":"103.743566","weidu":"36.096596","l_distance":4,"distance":4},{"realname":"刘","sex":"1","avatar":"58b6394202edf66004.jpg","age":"1982年12月3日 ","origin_cityname":"济南","origin_county":"市中","uid":"8","jingdu":"103.743583","weidu":"36.096615","l_distance":6,"distance":6},{"realname":"王龙","sex":"1","avatar":"58b63a6ccd1bc79775.jpg","age":"1994年9月27日 ","origin_cityname":"济南","origin_county":"市中","uid":"7","jingdu":"103.74358","weidu":"36.096617","l_distance":6,"distance":6},{"realname":"夏太热","sex":"2","avatar":"58b6416d624c061172.png","age":"2017年03月01日","origin_cityname":"莱芜 钢城","origin_county":"","uid":"10","jingdu":"103.743633","weidu":"36.096613","l_distance":9,"distance":9},{"realname":"杜甫","sex":"1","avatar":"58b826379f65756302.jpg","age":"1992年11月10日 ","origin_cityname":"济南市","origin_county":"槐荫区","uid":"43","jingdu":"103.743695","weidu":"36.096506","l_distance":13,"distance":13},{"realname":"王立莹","sex":"2","avatar":"58b7ebebd89de35521.jpg","age":"1989年10月15日 ","origin_cityname":"济宁汶上","origin_county":" ","uid":"19","jingdu":"103.743549","weidu":"36.096827","l_distance":30,"distance":30},{"realname":"马小马","sex":"1","avatar":"58b63f4e1230339414.png","age":"1992年12月14日 ","origin_cityname":"济宁","origin_county":"","uid":"9","jingdu":"103.743488","weidu":"36.096896","l_distance":38,"distance":38},{"realname":"魁大圣","sex":"1","avatar":"58b670e456da595554.png","age":"1987年03月01日","origin_cityname":"济南天桥","origin_county":"","uid":"12","jingdu":"103.743572","weidu":"36.096941","l_distance":42,"distance":42},{"realname":"魁先生","sex":"1","avatar":"58b7787ccd1bc3876.png","age":"1987年03月01日","origin_cityname":"济南 历下","origin_county":"","uid":"4","jingdu":"103.744554","weidu":"36.094023","l_distance":296,"distance":296},{"realname":"王会","sex":"1","avatar":"58b82ec19023379327.png","age":"1984年03月24日","origin_cityname":"聊城 东昌府","origin_county":"","uid":"45","jingdu":"103.720341","weidu":"36.103851","l_distance":2237,"distance":"2.2 千"},{"realname":"侯典申","sex":"1","avatar":"58b822c8e7e027806.png","age":"1981年07月29日","origin_cityname":"济宁 梁山","origin_county":"","uid":"38","jingdu":"103.781957","weidu":"36.076363","l_distance":4114,"distance":"4.1 千"},{"realname":"王广禄","sex":"1","avatar":"58b8162d0e70161624.jpg","age":"1973年10月16日 ","origin_cityname":"济南天桥","origin_county":" ","uid":"22","jingdu":"103.824839","weidu":"36.06166","l_distance":8266,"distance":"8.3 千"},{"realname":"徐斌","sex":"1","avatar":"58b8207d9f65753350.png","age":"1979年10月10日","origin_cityname":"菏泽 郓城","origin_county":"","uid":"32","jingdu":"103.856112","weidu":"36.044635","l_distance":11640,"distance":"11.6 千"},{"realname":"刘海军","sex":"1","avatar":"58b81f5cbde9f15096.png","age":"1981年06月26日","origin_cityname":"聊城 东阿","origin_county":"","uid":"30","jingdu":"101.769058","weidu":"36.664212","l_distance":187572,"distance":"187.6 千"},{"realname":"葛玲玲","sex":"2","avatar":"58b826b8b278419096.png","age":"1969年04月21日","origin_cityname":"威海 文登","origin_county":"","uid":"41","jingdu":"106.684734","weidu":"35.552971","l_distance":271789,"distance":"271.8 千"},{"realname":"朱彦国","sex":"1","avatar":"58b6381a43c786013.jpg","age":"1987年3月1日 ","origin_cityname":"济南","origin_county":"市中","uid":"5","jingdu":"100.495485","weidu":"38.93052","l_distance":425549,"distance":"425.5 千"},{"realname":"杨立升","sex":"1","avatar":"58b819e2b278496266.jpg","age":"1972年1月11日 ","origin_cityname":"济宁","origin_county":"梁山","uid":"6","jingdu":"100.495504","weidu":"38.930527","l_distance":425549,"distance":"425.5 千"}]
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
             * realname : 鲁商互联
             * sex : 1
             * avatar : 58b77062eba0466266.jpg
             * age : 1990年3月2日
             * origin_cityname : 济南
             * origin_county : 市中
             * uid : 16
             * jingdu : 103.743562
             * weidu : 36.096561
             * l_distance : 0
             * distance : 0
             */

            private String realname;
            private String sex;
            private String avatar;
            private String age;
            private String origin_cityname;
            private String origin_county;
            private String uid;
            private String jingdu;
            private String weidu;
            private int l_distance;
            private String distance;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getJingdu() {
                return jingdu;
            }

            public void setJingdu(String jingdu) {
                this.jingdu = jingdu;
            }

            public String getWeidu() {
                return weidu;
            }

            public void setWeidu(String weidu) {
                this.weidu = weidu;
            }

            public int getL_distance() {
                return l_distance;
            }

            public void setL_distance(int l_distance) {
                this.l_distance = l_distance;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}

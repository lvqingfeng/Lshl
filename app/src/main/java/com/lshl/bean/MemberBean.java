package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class MemberBean implements Serializable{

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"uid":"7","phone":"18509488472","realname":"王大人","age":"99","sex":"1","cityname":"兰州市","enterprise":"2","hx_id":"0808a5ad58d717da49953d243a022575","rid":"2","add_time":"1471570317","status":"1","eid":null,"enterprise_name":null,"workunit":"甘肅眾商網絡科技有限公司","avatar":"/Data/Uploads/avatar/7_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"5","enterprise_name":"甘肃好公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"17","enterprise_name":"中创","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"19","enterprise_name":"电子商务有限公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"20","enterprise_name":"11","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"21","enterprise_name":"甘肃众商互联网络科技有限公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"22","enterprise_name":"22","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"9","phone":"13162839599","realname":"张大人","age":"24","sex":"1","cityname":"兰州市","enterprise":"2","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","rid":"8","add_time":"1475116614","status":"1","eid":"12","enterprise_name":"中创","workunit":null,"avatar":"/Data/Uploads/avatar/9_200.jpg"},{"uid":"43","phone":"18892084259","realname":null,"age":null,"sex":null,"cityname":null,"enterprise":null,"hx_id":"21a1a09dfd364628bea7d68d61641cb3","rid":"9","add_time":"1476842470","status":"1","eid":null,"enterprise_name":null,"workunit":null,"avatar":""},{"uid":"46","phone":"17185380832","realname":"小鹏鹏","age":"22","sex":"1","cityname":"济宁市","enterprise":"1","hx_id":"7cc505a479cbe0166daac04b03a44ab4","rid":"10","add_time":"1477055089","status":"1","eid":null,"enterprise_name":null,"workunit":null,"avatar":""}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"uid":"7","phone":"18509488472","realname":"王大人","age":"99","sex":"1","cityname":"兰州市","enterprise":"2","hx_id":"0808a5ad58d717da49953d243a022575","rid":"2","add_time":"1471570317","status":"1","eid":null,"enterprise_name":null,"workunit":"甘肅眾商網絡科技有限公司","avatar":"/Data/Uploads/avatar/7_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"5","enterprise_name":"甘肃好公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"17","enterprise_name":"中创","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"19","enterprise_name":"电子商务有限公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"20","enterprise_name":"11","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"21","enterprise_name":"甘肃众商互联网络科技有限公司","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"3","phone":"18366189918","realname":"魁大圣","age":"29","sex":"1","cityname":"兰州市","enterprise":"1","hx_id":"2fcb13c32827c61a5d040c82e03eab28","rid":"4","add_time":"1471570317","status":"1","eid":"22","enterprise_name":"22","workunit":"测试工作单位","avatar":"/Data/Uploads/avatar/3_200.jpg"},{"uid":"9","phone":"13162839599","realname":"张大人","age":"24","sex":"1","cityname":"兰州市","enterprise":"2","hx_id":"e191c1cab6772c1aa4c6887082a4d8d5","rid":"8","add_time":"1475116614","status":"1","eid":"12","enterprise_name":"中创","workunit":null,"avatar":"/Data/Uploads/avatar/9_200.jpg"},{"uid":"43","phone":"18892084259","realname":null,"age":null,"sex":null,"cityname":null,"enterprise":null,"hx_id":"21a1a09dfd364628bea7d68d61641cb3","rid":"9","add_time":"1476842470","status":"1","eid":null,"enterprise_name":null,"workunit":null,"avatar":""},{"uid":"46","phone":"17185380832","realname":"小鹏鹏","age":"22","sex":"1","cityname":"济宁市","enterprise":"1","hx_id":"7cc505a479cbe0166daac04b03a44ab4","rid":"10","add_time":"1477055089","status":"1","eid":null,"enterprise_name":null,"workunit":null,"avatar":""}]
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
         * uid : 7
         * phone : 18509488472
         * realname : 王大人
         * age : 99
         * sex : 1
         * cityname : 兰州市
         * enterprise : 2
         * hx_id : 0808a5ad58d717da49953d243a022575
         * rid : 2
         * add_time : 1471570317
         * status : 1
         * eid : null
         * enterprise_name : null
         * workunit : 甘肅眾商網絡科技有限公司
         * avatar : /Data/Uploads/avatar/7_200.jpg
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
            private String uid;
            private String phone;
            private String realname;
            private String age;
            private String sex;
            private String cityname;
            private String enterprise;
            private String hx_id;
            private String rid;
            private String add_time;
            private String status;
            private Object eid;
            private Object enterprise_name;
            private String workunit;
            private String avatar;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getEnterprise() {
                return enterprise;
            }

            public void setEnterprise(String enterprise) {
                this.enterprise = enterprise;
            }

            public String getHx_id() {
                return hx_id;
            }

            public void setHx_id(String hx_id) {
                this.hx_id = hx_id;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getEid() {
                return eid;
            }

            public void setEid(Object eid) {
                this.eid = eid;
            }

            public Object getEnterprise_name() {
                return enterprise_name;
            }

            public void setEnterprise_name(Object enterprise_name) {
                this.enterprise_name = enterprise_name;
            }

            public String getWorkunit() {
                return workunit;
            }

            public void setWorkunit(String workunit) {
                this.workunit = workunit;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}

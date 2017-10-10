package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月02日
 * 时间：9:31
 * 版本：v1.0.0
 * 类描述：用户信息
 * 修改时间：
 */

public class UserInfoBean {


    /**
     * age : 99
     * current_address : 甘肃兰州
     * enterprise : 2
     * home_address : 陕西西安
     * hx_id : 0808a5ad58d717da49953d243a022575
     * idcard : 610527199409273613
     * phone : 18509488472
     * realname : 王大人
     * uid : 7
     */

    private InfoBean info;
    /**
     * info : {"age":"99","current_address":"甘肃兰州","enterprise":"2","home_address":"陕西西安","hx_id":"0808a5ad58d717da49953d243a022575","idcard":"610527199409273613","phone":"18509488472","realname":"王大人","uid":"7"}
     * status : 1
     */

    private String status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class InfoBean {
        private String age;
        private String current_address;
        private String enterprise;
        private String home_address;
        private String hx_id;
        private String idcard;
        private String phone;
        private String realname;
        private String uid;

        private List<EnterpriseBean> enList;
        private List<UserBean> infoList;

        public List<UserBean> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<UserBean> infoList) {
            this.infoList = infoList;
        }

        public List<EnterpriseBean> getEnList() {
            return enList;
        }

        public void setEnList(List<EnterpriseBean> enList) {
            this.enList = enList;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCurrent_address() {
            return current_address;
        }

        public void setCurrent_address(String current_address) {
            this.current_address = current_address;
        }

        public String getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(String enterprise) {
            this.enterprise = enterprise;
        }

        public String getHome_address() {
            return home_address;
        }

        public void setHome_address(String home_address) {
            this.home_address = home_address;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public static class UserBean{

            /**
             * add_time : 1476103732
             * id : 4
             * is_gudong : 2
             * juid : 7
             * workaddress : 兰州市安宁区北滨河西路
             * worknature : 软件开发
             * workpost : 软件工程师
             * workscope : 移动互联网
             * workunit : 甘肅眾商網絡科技有限公司
             */

            private String add_time;
            private String id;
            private String is_gudong;
            private String juid;
            private String workaddress;
            private String worknature;
            private String workpost;
            private String workscope;
            private String workunit;

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIs_gudong() {
                return is_gudong;
            }

            public void setIs_gudong(String is_gudong) {
                this.is_gudong = is_gudong;
            }

            public String getJuid() {
                return juid;
            }

            public void setJuid(String juid) {
                this.juid = juid;
            }

            public String getWorkaddress() {
                return workaddress;
            }

            public void setWorkaddress(String workaddress) {
                this.workaddress = workaddress;
            }

            public String getWorknature() {
                return worknature;
            }

            public void setWorknature(String worknature) {
                this.worknature = worknature;
            }

            public String getWorkpost() {
                return workpost;
            }

            public void setWorkpost(String workpost) {
                this.workpost = workpost;
            }

            public String getWorkscope() {
                return workscope;
            }

            public void setWorkscope(String workscope) {
                this.workscope = workscope;
            }

            public String getWorkunit() {
                return workunit;
            }

            public void setWorkunit(String workunit) {
                this.workunit = workunit;
            }
        }

        public static class EnterpriseBean{//法人信息

            /**
             * businesshours : 2015年11月8号 至 2035年11月8号
             * businessrange : 经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围经营范围
             * credit_code : 123654789987
             * enterprise_address : 甘肃省兰州市七里河区
             * enterprise_name : 甘肃好公司
             * enterprise_type : 有限责任公司
             * establish_time : 2015年11月8号
             * legalperson : 奎大爷大砍海神
             * registered_capital : 500万
             * sfz_fm : 2259157c502d8ccaee.jpeg
             * sfz_sc : 2996457c502d8cda8e.jpeg
             * sfz_zm : ls57d65d8b618f2.jpg
             * yyzz : 1186157c502d8ce646.jpeg
             */

            private String businesshours;
            private String businessrange;
            private String credit_code;
            private String enterprise_address;
            private String enterprise_name;
            private String enterprise_type;
            private String establish_time;
            private String legalperson;
            private String registered_capital;
            private String sfz_fm;
            private String sfz_sc;
            private String sfz_zm;
            private String yyzz;

            public String getBusinesshours() {
                return businesshours;
            }

            public void setBusinesshours(String businesshours) {
                this.businesshours = businesshours;
            }

            public String getBusinessrange() {
                return businessrange;
            }

            public void setBusinessrange(String businessrange) {
                this.businessrange = businessrange;
            }

            public String getCredit_code() {
                return credit_code;
            }

            public void setCredit_code(String credit_code) {
                this.credit_code = credit_code;
            }

            public String getEnterprise_address() {
                return enterprise_address;
            }

            public void setEnterprise_address(String enterprise_address) {
                this.enterprise_address = enterprise_address;
            }

            public String getEnterprise_name() {
                return enterprise_name;
            }

            public void setEnterprise_name(String enterprise_name) {
                this.enterprise_name = enterprise_name;
            }

            public String getEnterprise_type() {
                return enterprise_type;
            }

            public void setEnterprise_type(String enterprise_type) {
                this.enterprise_type = enterprise_type;
            }

            public String getEstablish_time() {
                return establish_time;
            }

            public void setEstablish_time(String establish_time) {
                this.establish_time = establish_time;
            }

            public String getLegalperson() {
                return legalperson;
            }

            public void setLegalperson(String legalperson) {
                this.legalperson = legalperson;
            }

            public String getRegistered_capital() {
                return registered_capital;
            }

            public void setRegistered_capital(String registered_capital) {
                this.registered_capital = registered_capital;
            }

            public String getSfz_fm() {
                return sfz_fm;
            }

            public void setSfz_fm(String sfz_fm) {
                this.sfz_fm = sfz_fm;
            }

            public String getSfz_sc() {
                return sfz_sc;
            }

            public void setSfz_sc(String sfz_sc) {
                this.sfz_sc = sfz_sc;
            }

            public String getSfz_zm() {
                return sfz_zm;
            }

            public void setSfz_zm(String sfz_zm) {
                this.sfz_zm = sfz_zm;
            }

            public String getYyzz() {
                return yyzz;
            }

            public void setYyzz(String yyzz) {
                this.yyzz = yyzz;
            }
        }


    }
}

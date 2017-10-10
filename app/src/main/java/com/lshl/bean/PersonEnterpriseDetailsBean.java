package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */

public class PersonEnterpriseDetailsBean implements Serializable{

    /**
     * status : 1
     * info : [{"credit_code":"6988484894481","enterprise_name":"电子商务有限公司","legalperson":"海月","enterprise_type":"电子商务","enterprise_address":"46","registered_capital":"1548","establish_time":"6546","businesshours":"546","businessrange":"56546","img_id":"12","uid":"3","eid":"19","yyzz":"450557f8a19228926.jpeg","qyxx":"1955057f8a1922b037.jpeg"}]
     */

    private int status;
    /**
     * credit_code : 6988484894481
     * enterprise_name : 电子商务有限公司
     * legalperson : 海月
     * enterprise_type : 电子商务
     * enterprise_address : 46
     * registered_capital : 1548
     * establish_time : 6546
     * businesshours : 546
     * businessrange : 56546
     * img_id : 12
     * uid : 3
     * eid : 19
     * yyzz : 450557f8a19228926.jpeg
     * qyxx : 1955057f8a1922b037.jpeg
     */

    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        private String credit_code;
        private String enterprise_name;
        private String legalperson;
        private String enterprise_type;
        private String enterprise_address;
        private String registered_capital;
        private String establish_time;
        private String businesshours;
        private String businessrange;
        private String img_id;
        private String uid;
        private String eid;
        private String yyzz;
        private String qyxx;

        public String getCredit_code() {
            return credit_code;
        }

        public void setCredit_code(String credit_code) {
            this.credit_code = credit_code;
        }

        public String getEnterprise_name() {
            return enterprise_name;
        }

        public void setEnterprise_name(String enterprise_name) {
            this.enterprise_name = enterprise_name;
        }

        public String getLegalperson() {
            return legalperson;
        }

        public void setLegalperson(String legalperson) {
            this.legalperson = legalperson;
        }

        public String getEnterprise_type() {
            return enterprise_type;
        }

        public void setEnterprise_type(String enterprise_type) {
            this.enterprise_type = enterprise_type;
        }

        public String getEnterprise_address() {
            return enterprise_address;
        }

        public void setEnterprise_address(String enterprise_address) {
            this.enterprise_address = enterprise_address;
        }

        public String getRegistered_capital() {
            return registered_capital;
        }

        public void setRegistered_capital(String registered_capital) {
            this.registered_capital = registered_capital;
        }

        public String getEstablish_time() {
            return establish_time;
        }

        public void setEstablish_time(String establish_time) {
            this.establish_time = establish_time;
        }

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

        public String getImg_id() {
            return img_id;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getYyzz() {
            return yyzz;
        }

        public void setYyzz(String yyzz) {
            this.yyzz = yyzz;
        }

        public String getQyxx() {
            return qyxx;
        }

        public void setQyxx(String qyxx) {
            this.qyxx = qyxx;
        }
    }
}

package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月25日
 * 时间：16:59
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HelpMemberListBean {


    /**
     * info : [{"age":"99","avatar":"57fa21d102ce3.png","cityname":"兰州市","enterprise":"2","hx_id":"0808a5ad58d717da49953d243a022575","phone":"18509488472","realname":"王大人","rid":"2","sex":"1","uid":"7"},{"age":"22","avatar":"5804a00a6cea0.png","cityname":"济南市","enterprise":"1","hx_id":"4e76288696b0714ad30697b2c007e796","phone":"18306431704","realname":"局长","rid":"11","sex":"1","uid":"41"}]
     * status : 1
     */

    private String status;
    /**
     * age : 99
     * avatar : 57fa21d102ce3.png
     * cityname : 兰州市
     * enterprise : 2
     * hx_id : 0808a5ad58d717da49953d243a022575
     * phone : 18509488472
     * realname : 王大人
     * rid : 2
     * sex : 1
     * uid : 7
     */

    private List<InfoBean> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable {
        private String age;
        private String avatar;
        private String cityname;
        private String enterprise;
        private String hx_id;
        private String phone;
        private String realname;
        private String rid;
        private String sex;
        private String uid;
        private int distance;
        private double latitude;
        private double longitude;
        private boolean isSelected;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}

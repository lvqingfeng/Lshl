package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/***
 * Created by Administrator on 2016/11/1.
 */

public class GoodsDetailsBean implements Serializable {

    /**
     * status : 1
     * info : [{"realname":"吕清锋","avatar":"58b77062eba0466266.jpg","phone":"17010207172","uid":"16","hx_id":"31cb44006b8505076372bcd6377104b2","gd_id":"245","gd_one_class":"美妆/清洁","gd_two_class":"面部护理","gd_type":"1035","gd_number":"LSHL201703161040029876581","gd_goodname":"测试","gd_selling":"","gd_original_price":"147.00","gd_special_offer":"36.00","gd_phone":"17010207172","gd_img":"58c9fb0259b1a28539.png,58c9fb0259b1a67248.png","gd_info":"好像","gd_uid":"16","gd_sort":"0","gd_sort_time":"0","gd_status":"1","gd_cityname":"福建省福州市","gd_cityno":"3401","gd_click":"0","gd_addtime":"1489632002","gd_editinfo":null,"gd_edittime":null,"img":["58c9fb0259b1a28539.png","58c9fb0259b1a67248.png"],"top":0,"isCollection":0,"gd_hot":0}]
     */

    private String status;
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

    public static class InfoBean implements Serializable{
        /**
         * realname : 吕清锋
         * avatar : 58b77062eba0466266.jpg
         * phone : 17010207172
         * uid : 16
         * hx_id : 31cb44006b8505076372bcd6377104b2
         * gd_id : 245
         * gd_one_class : 美妆/清洁
         * gd_two_class : 面部护理
         * gd_type : 1035
         * gd_number : LSHL201703161040029876581
         * gd_goodname : 测试
         * gd_selling :
         * gd_original_price : 147.00
         * gd_special_offer : 36.00
         * gd_phone : 17010207172
         * gd_img : 58c9fb0259b1a28539.png,58c9fb0259b1a67248.png
         * gd_info : 好像
         * gd_uid : 16
         * gd_sort : 0
         * gd_sort_time : 0
         * gd_status : 1
         * gd_cityname : 福建省福州市
         * gd_cityno : 3401
         * gd_click : 0
         * gd_addtime : 1489632002
         * gd_editinfo : null
         * gd_edittime : null
         * img : ["58c9fb0259b1a28539.png","58c9fb0259b1a67248.png"]
         * top : 0
         * isCollection : 0
         * gd_hot : 0
         */

        private String realname;
        private String avatar;
        private String phone;
        private String uid;
        private String hx_id;
        private String gd_id;
        private String gd_one_class;
        private String gd_two_class;
        private String gd_type;
        private String gd_number;
        private String gd_goodname;
        private String gd_selling;
        private String gd_original_price;
        private String gd_special_offer;
        private String gd_phone;
        private String gd_img;
        private String gd_info;
        private String gd_uid;
        private String gd_sort;
        private String gd_sort_time;
        private String gd_status;
        private String gd_cityname;
        private String gd_cityno;
        private String gd_click;
        private String gd_addtime;
        private String gd_editinfo;
        private String gd_edittime;
        private String top;
        private String isCollection;
        private int gd_hot;
        private List<String> img;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getGd_id() {
            return gd_id;
        }

        public void setGd_id(String gd_id) {
            this.gd_id = gd_id;
        }

        public String getGd_one_class() {
            return gd_one_class;
        }

        public void setGd_one_class(String gd_one_class) {
            this.gd_one_class = gd_one_class;
        }

        public String getGd_two_class() {
            return gd_two_class;
        }

        public void setGd_two_class(String gd_two_class) {
            this.gd_two_class = gd_two_class;
        }

        public String getGd_type() {
            return gd_type;
        }

        public void setGd_type(String gd_type) {
            this.gd_type = gd_type;
        }

        public String getGd_number() {
            return gd_number;
        }

        public void setGd_number(String gd_number) {
            this.gd_number = gd_number;
        }

        public String getGd_goodname() {
            return gd_goodname;
        }

        public void setGd_goodname(String gd_goodname) {
            this.gd_goodname = gd_goodname;
        }

        public String getGd_selling() {
            return gd_selling;
        }

        public void setGd_selling(String gd_selling) {
            this.gd_selling = gd_selling;
        }

        public String getGd_original_price() {
            return gd_original_price;
        }

        public void setGd_original_price(String gd_original_price) {
            this.gd_original_price = gd_original_price;
        }

        public String getGd_special_offer() {
            return gd_special_offer;
        }

        public void setGd_special_offer(String gd_special_offer) {
            this.gd_special_offer = gd_special_offer;
        }

        public String getGd_phone() {
            return gd_phone;
        }

        public void setGd_phone(String gd_phone) {
            this.gd_phone = gd_phone;
        }

        public String getGd_img() {
            return gd_img;
        }

        public void setGd_img(String gd_img) {
            this.gd_img = gd_img;
        }

        public String getGd_info() {
            return gd_info;
        }

        public void setGd_info(String gd_info) {
            this.gd_info = gd_info;
        }

        public String getGd_uid() {
            return gd_uid;
        }

        public void setGd_uid(String gd_uid) {
            this.gd_uid = gd_uid;
        }

        public String getGd_sort() {
            return gd_sort;
        }

        public void setGd_sort(String gd_sort) {
            this.gd_sort = gd_sort;
        }

        public String getGd_sort_time() {
            return gd_sort_time;
        }

        public void setGd_sort_time(String gd_sort_time) {
            this.gd_sort_time = gd_sort_time;
        }

        public String getGd_status() {
            return gd_status;
        }

        public void setGd_status(String gd_status) {
            this.gd_status = gd_status;
        }

        public String getGd_cityname() {
            return gd_cityname;
        }

        public void setGd_cityname(String gd_cityname) {
            this.gd_cityname = gd_cityname;
        }

        public String getGd_cityno() {
            return gd_cityno;
        }

        public void setGd_cityno(String gd_cityno) {
            this.gd_cityno = gd_cityno;
        }

        public String getGd_click() {
            return gd_click;
        }

        public void setGd_click(String gd_click) {
            this.gd_click = gd_click;
        }

        public String getGd_addtime() {
            return gd_addtime;
        }

        public void setGd_addtime(String gd_addtime) {
            this.gd_addtime = gd_addtime;
        }

        public String getGd_editinfo() {
            return gd_editinfo;
        }

        public void setGd_editinfo(String gd_editinfo) {
            this.gd_editinfo = gd_editinfo;
        }

        public String getGd_edittime() {
            return gd_edittime;
        }

        public void setGd_edittime(String gd_edittime) {
            this.gd_edittime = gd_edittime;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(String isCollection) {
            this.isCollection = isCollection;
        }

        public int getGd_hot() {
            return gd_hot;
        }

        public void setGd_hot(int gd_hot) {
            this.gd_hot = gd_hot;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}

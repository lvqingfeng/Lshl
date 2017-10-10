package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ProjectDetailsBean {

    /**
     * status : 1
     * info : [{"realname":"丁亚庆","avatar":"584b796854439.jpg","phone":"15117123226","uid":"11","hx_id":"df9dba6a2752985190378773b18995b5","pp_id":"1","pp_uid":"11","pp_title":"刚回家不过","pp_imgs":"584b846a6268c.png","pp_info":"有空v饭局发酒疯就不让句停机图呲牙忽然我饿u额我呀申诉额缺乏估计","pp_address":"干活呢不干净","pp_status":"1","pp_addtime":"1481344106","pp_editinfo":"OK","pp_edittime":"1481359550","imgs":["584b846a6268c.png"]}]
     */

    private int status;
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

    public static class InfoBean implements Serializable{
        /**
         * realname : 丁亚庆
         * avatar : 584b796854439.jpg
         * phone : 15117123226
         * uid : 11
         * hx_id : df9dba6a2752985190378773b18995b5
         * pp_id : 1
         * pp_uid : 11
         * pp_title : 刚回家不过
         * pp_imgs : 584b846a6268c.png
         * pp_info : 有空v饭局发酒疯就不让句停机图呲牙忽然我饿u额我呀申诉额缺乏估计
         * pp_address : 干活呢不干净
         * pp_status : 1
         * pp_addtime : 1481344106
         * pp_editinfo : OK
         * pp_edittime : 1481359550
         * imgs : ["584b846a6268c.png"]
         */

        private String realname;
        private String avatar;
        private String phone;
        private String uid;
        private String hx_id;
        private String pp_id;
        private String pp_uid;
        private String pp_title;
        private String pp_imgs;
        private String pp_info;
        private String pp_address;
        private String pp_status;
        private String pp_addtime;
        private String pp_editinfo;
        private String pp_edittime;
        private List<String> imgs;

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

        public String getPp_id() {
            return pp_id;
        }

        public void setPp_id(String pp_id) {
            this.pp_id = pp_id;
        }

        public String getPp_uid() {
            return pp_uid;
        }

        public void setPp_uid(String pp_uid) {
            this.pp_uid = pp_uid;
        }

        public String getPp_title() {
            return pp_title;
        }

        public void setPp_title(String pp_title) {
            this.pp_title = pp_title;
        }

        public String getPp_imgs() {
            return pp_imgs;
        }

        public void setPp_imgs(String pp_imgs) {
            this.pp_imgs = pp_imgs;
        }

        public String getPp_info() {
            return pp_info;
        }

        public void setPp_info(String pp_info) {
            this.pp_info = pp_info;
        }

        public String getPp_address() {
            return pp_address;
        }

        public void setPp_address(String pp_address) {
            this.pp_address = pp_address;
        }

        public String getPp_status() {
            return pp_status;
        }

        public void setPp_status(String pp_status) {
            this.pp_status = pp_status;
        }

        public String getPp_addtime() {
            return pp_addtime;
        }

        public void setPp_addtime(String pp_addtime) {
            this.pp_addtime = pp_addtime;
        }

        public String getPp_editinfo() {
            return pp_editinfo;
        }

        public void setPp_editinfo(String pp_editinfo) {
            this.pp_editinfo = pp_editinfo;
        }

        public String getPp_edittime() {
            return pp_edittime;
        }

        public void setPp_edittime(String pp_edittime) {
            this.pp_edittime = pp_edittime;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}

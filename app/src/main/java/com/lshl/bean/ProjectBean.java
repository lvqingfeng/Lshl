package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ProjectBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"realname":"魁大圣","avatar":"58083664e2360.png","phone":"18366189918","uid":"3","hx_id":"2fcb13c32827c61a5d040c82e03eab28","pp_id":"1","pp_uid":"3","pp_title":"hello world","pp_imgs":"5821940442aa9.png,582194044361a.png,582194044418c.png,58219404450ce.png,5821940445c3f.png","pp_info":"Hello my name is Chen bian bian.  I am a yan yuan\n","pp_address":"Bei jing ","pp_status":"1","pp_addtime":"1478596968","pp_editinfo":"合格","pp_edittime":"1478600062","img":"5821940442aa9.png"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"realname":"魁大圣","avatar":"58083664e2360.png","phone":"18366189918","uid":"3","hx_id":"2fcb13c32827c61a5d040c82e03eab28","pp_id":"1","pp_uid":"3","pp_title":"hello world","pp_imgs":"5821940442aa9.png,582194044361a.png,582194044418c.png,58219404450ce.png,5821940445c3f.png","pp_info":"Hello my name is Chen bian bian.  I am a yan yuan\n","pp_address":"Bei jing ","pp_status":"1","pp_addtime":"1478596968","pp_editinfo":"合格","pp_edittime":"1478600062","img":"5821940442aa9.png"}]
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
         * realname : 魁大圣
         * avatar : 58083664e2360.png
         * phone : 18366189918
         * uid : 3
         * hx_id : 2fcb13c32827c61a5d040c82e03eab28
         * pp_id : 1
         * pp_uid : 3
         * pp_title : hello world
         * pp_imgs : 5821940442aa9.png,582194044361a.png,582194044418c.png,58219404450ce.png,5821940445c3f.png
         * pp_info : Hello my name is Chen bian bian.  I am a yan yuan

         * pp_address : Bei jing
         * pp_status : 1
         * pp_addtime : 1478596968
         * pp_editinfo : 合格
         * pp_edittime : 1478600062
         * img : 5821940442aa9.png
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
            private String img;

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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

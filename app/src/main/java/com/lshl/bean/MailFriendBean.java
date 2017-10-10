package com.lshl.bean;

import java.util.List;

/**
 * 项目名称：鲁商互联
 * 类描述：
 * 创建人：吕清锋
 * 创建时间：2017/3/21 19:07
 * 修改备注：
 */
public class MailFriendBean {

    /**
     * info : [{"avatar":"58b7787ccd1bc3876.png","hx_id":"1f06d9bb88e9bdfd04dfc8aaba863d65","ismember":1,"phone":"18366189918","realname":"张三","uid":"4"},{"avatar":"58b670e456da595554.png","hx_id":"1c05da73d7d52910086ff1216021bead","ismember":1,"phone":"18919912285","realname":"李四","uid":"12"},{"avatar":0,"hx_id":"","ismember":0,"phone":"123456789","realname":"王五","uid":""},{"avatar":0,"hx_id":"","ismember":0,"phone":"98764321","realname":"马六","uid":""}]
     * status : 1
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

    public static class InfoBean {
        /**
         * avatar : 58b7787ccd1bc3876.png
         * hx_id : 1f06d9bb88e9bdfd04dfc8aaba863d65
         * ismember : 1
         * phone : 18366189918
         * realname : 张三
         * uid : 4
         */

        private String avatar;
        private String hx_id;
        private int ismember;
        private String phone;
        private String realname;
        private String uid;
        private String invitation;

        public String getInvitation() {
            return invitation;
        }

        public void setInvitation(String invitation) {
            this.invitation = invitation;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public int getIsmember() {
            return ismember;
        }

        public void setIsmember(int ismember) {
            this.ismember = ismember;
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
    }
}

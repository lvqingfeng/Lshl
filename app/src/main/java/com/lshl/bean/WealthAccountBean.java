package com.lshl.bean;

/**
 * Created by Administrator on 2016/11/22.
 */

public class WealthAccountBean {

    /**
     * status : 1
     * info : {"wa_id":"1","wa_uid":"9","wa_wei":"wwdfghjjtrer","wa_zhi":"wwdfghjjtrerwwxx","wa_bankname":"安宁区支行","wa_banknum":"664447788443484","wa_bankaddree":"安宁区支行"}
     */

    private int status;
    /**
     * wa_id : 1
     * wa_uid : 9
     * wa_wei : wwdfghjjtrer
     * wa_zhi : wwdfghjjtrerwwxx
     * wa_bankname : 安宁区支行
     * wa_banknum : 664447788443484
     * wa_bankaddree : 安宁区支行
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
        private String wa_id;
        private String wa_uid;
        private String wa_wei;
        private String wa_zhi;
        private String wa_bankname;
        private String wa_banknum;
        private String wa_bankaddree;

        public String getWa_id() {
            return wa_id;
        }

        public void setWa_id(String wa_id) {
            this.wa_id = wa_id;
        }

        public String getWa_uid() {
            return wa_uid;
        }

        public void setWa_uid(String wa_uid) {
            this.wa_uid = wa_uid;
        }

        public String getWa_wei() {
            return wa_wei;
        }

        public void setWa_wei(String wa_wei) {
            this.wa_wei = wa_wei;
        }

        public String getWa_zhi() {
            return wa_zhi;
        }

        public void setWa_zhi(String wa_zhi) {
            this.wa_zhi = wa_zhi;
        }

        public String getWa_bankname() {
            return wa_bankname;
        }

        public void setWa_bankname(String wa_bankname) {
            this.wa_bankname = wa_bankname;
        }

        public String getWa_banknum() {
            return wa_banknum;
        }

        public void setWa_banknum(String wa_banknum) {
            this.wa_banknum = wa_banknum;
        }

        public String getWa_bankaddree() {
            return wa_bankaddree;
        }

        public void setWa_bankaddree(String wa_bankaddree) {
            this.wa_bankaddree = wa_bankaddree;
        }
    }
}

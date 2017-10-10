package com.lshl.bean;

/**
 * Created by Administrator on 2017/1/19.
 */

public class IconBean {

    /**
     * status : 1
     * info : {"authenticate":0,"president":0,"charityfund":0,"maxCharityfund":0,"dues":0}
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
         * authenticate : 0
         * president : 0
         * charityfund : 0
         * maxCharityfund : 0
         * dues : 0
         */

        private int authenticate;
        private int president;
        private int charityfund;
        private int maxCharityfund;
        private int dues;

        public int getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(int authenticate) {
            this.authenticate = authenticate;
        }

        public int getPresident() {
            return president;
        }

        public void setPresident(int president) {
            this.president = president;
        }

        public int getCharityfund() {
            return charityfund;
        }

        public void setCharityfund(int charityfund) {
            this.charityfund = charityfund;
        }

        public int getMaxCharityfund() {
            return maxCharityfund;
        }

        public void setMaxCharityfund(int maxCharityfund) {
            this.maxCharityfund = maxCharityfund;
        }

        public int getDues() {
            return dues;
        }

        public void setDues(int dues) {
            this.dues = dues;
        }
    }
}

package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class FundrecordBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"dm_id":"39","dm_fund_type":"3","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481856390","dm_order_sn":"2016121610463045804","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"公益支出"},{"dm_id":"6","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.02","dm_status":"1","dm_addtime":"1481807349","dm_order_sn":"2016121521090991484","dm_tradeid":"2016121521001004170207648562","dm_paytime":"1481807358","dm_paystyle":"alipay","dm_payed":"1","dm_nums":"0","type":"交会费"},{"dm_id":"5","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.02","dm_status":"0","dm_addtime":"1481807260","dm_order_sn":"2016121521074048739","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"4","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481807202","dm_order_sn":"2016121521064273886","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"3","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806897","dm_order_sn":"2016121521013725993","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"2","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806467","dm_order_sn":"2016121520542746857","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"1","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806285","dm_order_sn":"2016121520512590670","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"}]}
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
         * pages : 1
         * end : 1
         * list : [{"dm_id":"39","dm_fund_type":"3","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481856390","dm_order_sn":"2016121610463045804","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"公益支出"},{"dm_id":"6","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.02","dm_status":"1","dm_addtime":"1481807349","dm_order_sn":"2016121521090991484","dm_tradeid":"2016121521001004170207648562","dm_paytime":"1481807358","dm_paystyle":"alipay","dm_payed":"1","dm_nums":"0","type":"交会费"},{"dm_id":"5","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.02","dm_status":"0","dm_addtime":"1481807260","dm_order_sn":"2016121521074048739","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"4","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481807202","dm_order_sn":"2016121521064273886","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"3","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806897","dm_order_sn":"2016121521013725993","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"2","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806467","dm_order_sn":"2016121520542746857","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"},{"dm_id":"1","dm_fund_type":"1","dm_customer_type":"1","dm_customer_uid":"11","dm_money":"0.01","dm_status":"0","dm_addtime":"1481806285","dm_order_sn":"2016121520512590670","dm_tradeid":null,"dm_paytime":null,"dm_paystyle":"alipay","dm_payed":"0","dm_nums":"0","type":"交会费"}]
         */

        private int pages;
        private int end;
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
            /**
             * dm_id : 39
             * dm_fund_type : 3
             * dm_customer_type : 1
             * dm_customer_uid : 11
             * dm_money : 0.01
             * dm_status : 0
             * dm_addtime : 1481856390
             * dm_order_sn : 2016121610463045804
             * dm_tradeid : null
             * dm_paytime : null
             * dm_paystyle : alipay
             * dm_payed : 0
             * dm_nums : 0
             * type : 公益支出
             */

            private String dm_id;
            private String dm_fund_type;
            private String dm_customer_type;
            private String dm_customer_uid;
            private String dm_money;
            private String dm_status;
            private String dm_addtime;
            private String dm_order_sn;
            private Object dm_tradeid;
            private Object dm_paytime;
            private String dm_paystyle;
            private String dm_payed;
            private String dm_nums;
            private String type;

            public String getDm_id() {
                return dm_id;
            }

            public void setDm_id(String dm_id) {
                this.dm_id = dm_id;
            }

            public String getDm_fund_type() {
                return dm_fund_type;
            }

            public void setDm_fund_type(String dm_fund_type) {
                this.dm_fund_type = dm_fund_type;
            }

            public String getDm_customer_type() {
                return dm_customer_type;
            }

            public void setDm_customer_type(String dm_customer_type) {
                this.dm_customer_type = dm_customer_type;
            }

            public String getDm_customer_uid() {
                return dm_customer_uid;
            }

            public void setDm_customer_uid(String dm_customer_uid) {
                this.dm_customer_uid = dm_customer_uid;
            }

            public String getDm_money() {
                return dm_money;
            }

            public void setDm_money(String dm_money) {
                this.dm_money = dm_money;
            }

            public String getDm_status() {
                return dm_status;
            }

            public void setDm_status(String dm_status) {
                this.dm_status = dm_status;
            }

            public String getDm_addtime() {
                return dm_addtime;
            }

            public void setDm_addtime(String dm_addtime) {
                this.dm_addtime = dm_addtime;
            }

            public String getDm_order_sn() {
                return dm_order_sn;
            }

            public void setDm_order_sn(String dm_order_sn) {
                this.dm_order_sn = dm_order_sn;
            }

            public Object getDm_tradeid() {
                return dm_tradeid;
            }

            public void setDm_tradeid(Object dm_tradeid) {
                this.dm_tradeid = dm_tradeid;
            }

            public Object getDm_paytime() {
                return dm_paytime;
            }

            public void setDm_paytime(Object dm_paytime) {
                this.dm_paytime = dm_paytime;
            }

            public String getDm_paystyle() {
                return dm_paystyle;
            }

            public void setDm_paystyle(String dm_paystyle) {
                this.dm_paystyle = dm_paystyle;
            }

            public String getDm_payed() {
                return dm_payed;
            }

            public void setDm_payed(String dm_payed) {
                this.dm_payed = dm_payed;
            }

            public String getDm_nums() {
                return dm_nums;
            }

            public void setDm_nums(String dm_nums) {
                this.dm_nums = dm_nums;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}

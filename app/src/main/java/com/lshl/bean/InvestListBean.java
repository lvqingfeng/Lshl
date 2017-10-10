package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月10日
 * 时间：20:25
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class InvestListBean {


    /**
     * allmoney : 1300.00
     * end : 1
     * list : [{"dm_addtime":"1476050329","dm_id":"2","dm_money":"500.00","dm_status":"1","dm_style":"1","dm_type":"1","dm_uid":"7"},{"dm_addtime":"1476700401","dm_id":"7","dm_money":"200.00","dm_status":"1","dm_style":"1","dm_type":"2","dm_uid":"7"},{"dm_addtime":"1476454116","dm_id":"10","dm_money":"600.00","dm_status":"1","dm_style":"1","dm_type":"2","dm_uid":"7"}]
     * pages : 1
     */

    private String allmoney;
    private int end;
    private int pages;
    private List<ListBean> list;

    public String getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(String allmoney) {
        this.allmoney = allmoney;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * dm_addtime : 1476050329
         * dm_id : 2
         * dm_money : 500.00
         * dm_status : 1
         * dm_style : 1
         * dm_type : 1
         * dm_uid : 7
         */

        private String dm_addtime;
        private String dm_id;
        private String dm_money;
        private String dm_status;
        private String dm_style;
        private String dm_type;
        private String dm_uid;

        public String getDm_addtime() {
            return dm_addtime;
        }

        public void setDm_addtime(String dm_addtime) {
            this.dm_addtime = dm_addtime;
        }

        public String getDm_id() {
            return dm_id;
        }

        public void setDm_id(String dm_id) {
            this.dm_id = dm_id;
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

        public String getDm_style() {
            return dm_style;
        }

        public void setDm_style(String dm_style) {
            this.dm_style = dm_style;
        }

        public String getDm_type() {
            return dm_type;
        }

        public void setDm_type(String dm_type) {
            this.dm_type = dm_type;
        }

        public String getDm_uid() {
            return dm_uid;
        }

        public void setDm_uid(String dm_uid) {
            this.dm_uid = dm_uid;
        }
    }
}

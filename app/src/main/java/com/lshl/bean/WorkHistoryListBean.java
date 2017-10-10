package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月22日
 * 时间：18:22
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class WorkHistoryListBean {


    /**
     * end : 1
     * list : [{"we_addtime":"1482401970","we_company_endtime":"至今","we_company_name":"甘肃鲁商互联有限公司","we_company_starttime":"2016年10月","we_id":"4","we_jobs":""}]
     * pages : 1
     */

    private int end;
    private int pages;
    private List<ListBean> list;

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

    public static class ListBean implements Serializable {
        /**
         * we_addtime : 1482401970
         * we_company_endtime : 至今
         * we_company_name : 甘肃鲁商互联有限公司
         * we_company_starttime : 2016年10月
         * we_id : 4
         * we_jobs :
         */

        private String we_addtime;
        private String we_company_endtime;
        private String we_company_name;
        private String we_company_starttime;
        private String we_id;
        private String we_jobs;

        public String getWe_addtime() {
            return we_addtime;
        }

        public void setWe_addtime(String we_addtime) {
            this.we_addtime = we_addtime;
        }

        public String getWe_company_endtime() {
            return we_company_endtime;
        }

        public void setWe_company_endtime(String we_company_endtime) {
            this.we_company_endtime = we_company_endtime;
        }

        public String getWe_company_name() {
            return we_company_name;
        }

        public void setWe_company_name(String we_company_name) {
            this.we_company_name = we_company_name;
        }

        public String getWe_company_starttime() {
            return we_company_starttime;
        }

        public void setWe_company_starttime(String we_company_starttime) {
            this.we_company_starttime = we_company_starttime;
        }

        public String getWe_id() {
            return we_id;
        }

        public void setWe_id(String we_id) {
            this.we_id = we_id;
        }

        public String getWe_jobs() {
            return we_jobs;
        }

        public void setWe_jobs(String we_jobs) {
            this.we_jobs = we_jobs;
        }
    }
}

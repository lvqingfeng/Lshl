package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class QiuJiuBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":4,"end":0,"list":[{"pre_id":"72","pre_uid":"3","pre_info":"ghbb","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1473848101","pre_status":"1","pre_report_status":"0","pre_endtime":"1476933313"},{"pre_id":"74","pre_uid":"3","pre_info":"fhj","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474266821","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"75","pre_uid":"3","pre_info":"hjkk","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474267015","pre_status":"1","pre_report_status":"0","pre_endtime":"1477121745"},{"pre_id":"76","pre_uid":"3","pre_info":"a\u2006lou\u2006s","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272039","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"77","pre_uid":"3","pre_info":"h\u2006lo\u2006qin","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272050","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"78","pre_uid":"3","pre_info":"咯女人","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272122","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"79","pre_uid":"3","pre_info":"mo\u2006mo\u2006ti","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474274316","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"80","pre_uid":"3","pre_info":"？喝酒？a\u2006kou","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100115,103.746595","pre_addtime":"1474276022","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"81","pre_uid":"3","pre_info":"天外有天咯","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100096,103.746588","pre_addtime":"1474336010","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"82","pre_uid":"3","pre_info":"哦哦哦起来","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474336196","pre_status":"0","pre_report_status":"0","pre_endtime":null}]}
     */

    private int status;
    /**
     * pages : 4
     * end : 0
     * list : [{"pre_id":"72","pre_uid":"3","pre_info":"ghbb","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1473848101","pre_status":"1","pre_report_status":"0","pre_endtime":"1476933313"},{"pre_id":"74","pre_uid":"3","pre_info":"fhj","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474266821","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"75","pre_uid":"3","pre_info":"hjkk","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474267015","pre_status":"1","pre_report_status":"0","pre_endtime":"1477121745"},{"pre_id":"76","pre_uid":"3","pre_info":"a\u2006lou\u2006s","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272039","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"77","pre_uid":"3","pre_info":"h\u2006lo\u2006qin","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272050","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"78","pre_uid":"3","pre_info":"咯女人","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474272122","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"79","pre_uid":"3","pre_info":"mo\u2006mo\u2006ti","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474274316","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"80","pre_uid":"3","pre_info":"？喝酒？a\u2006kou","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100115,103.746595","pre_addtime":"1474276022","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"81","pre_uid":"3","pre_info":"天外有天咯","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100096,103.746588","pre_addtime":"1474336010","pre_status":"0","pre_report_status":"0","pre_endtime":null},{"pre_id":"82","pre_uid":"3","pre_info":"哦哦哦起来","pre_cityname":"甘肃省兰州市安宁区建宁东路","pre_jingwei":"36.100111,103.746595","pre_addtime":"1474336196","pre_status":"0","pre_report_status":"0","pre_endtime":null}]
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
         * pre_id : 72
         * pre_uid : 3
         * pre_info : ghbb
         * pre_cityname : 甘肃省兰州市安宁区建宁东路
         * pre_jingwei : 36.100111,103.746595
         * pre_addtime : 1473848101
         * pre_status : 1
         * pre_report_status : 0
         * pre_endtime : 1476933313
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
            private String pre_id;
            private String pre_uid;
            private String pre_info;
            private String pre_cityname;
            private String pre_jingwei;
            private String pre_addtime;
            private String pre_status;
            private String pre_report_status;
            private String pre_endtime;

            public String getPre_id() {
                return pre_id;
            }

            public void setPre_id(String pre_id) {
                this.pre_id = pre_id;
            }

            public String getPre_uid() {
                return pre_uid;
            }

            public void setPre_uid(String pre_uid) {
                this.pre_uid = pre_uid;
            }

            public String getPre_info() {
                return pre_info;
            }

            public void setPre_info(String pre_info) {
                this.pre_info = pre_info;
            }

            public String getPre_cityname() {
                return pre_cityname;
            }

            public void setPre_cityname(String pre_cityname) {
                this.pre_cityname = pre_cityname;
            }

            public String getPre_jingwei() {
                return pre_jingwei;
            }

            public void setPre_jingwei(String pre_jingwei) {
                this.pre_jingwei = pre_jingwei;
            }

            public String getPre_addtime() {
                return pre_addtime;
            }

            public void setPre_addtime(String pre_addtime) {
                this.pre_addtime = pre_addtime;
            }

            public String getPre_status() {
                return pre_status;
            }

            public void setPre_status(String pre_status) {
                this.pre_status = pre_status;
            }

            public String getPre_report_status() {
                return pre_report_status;
            }

            public void setPre_report_status(String pre_report_status) {
                this.pre_report_status = pre_report_status;
            }

            public String getPre_endtime() {
                return pre_endtime;
            }

            public void setPre_endtime(String pre_endtime) {
                this.pre_endtime = pre_endtime;
            }
        }
    }
}

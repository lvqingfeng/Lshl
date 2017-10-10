package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月26日
 * 时间：14:15
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HelpWaitGetMemberListBean {


    /**
     * end : 1
     * list : [{"avatar":"5804a00a6cea0.png","gre_distance":"1","gre_id":"145","gre_jingwei":"103.743583,36.096553","gre_status":"0","realname":"局长","uid":"41"}]
     * pages : 1
     */

    private InfoBean info;
    /**
     * info : {"end":1,"list":[{"avatar":"5804a00a6cea0.png","gre_distance":"1","gre_id":"145","gre_jingwei":"103.743583,36.096553","gre_status":"0","realname":"局长","uid":"41"}],"pages":1}
     * status : 1
     */

    private int status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class InfoBean {
        private int end;
        private int pages;
        /**
         * avatar : 5804a00a6cea0.png
         * gre_distance : 1
         * gre_id : 145
         * gre_jingwei : 103.743583,36.096553
         * gre_status : 0
         * realname : 局长
         * uid : 41
         */

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

        public static class ListBean {
            private String avatar;//头像
            private String gre_distance;//距离
            private String gre_id;//救人id
            private String gre_jingwei;//经纬度
            private String gre_status;//状态
            private String realname;//真是姓名
            private String uid;//id


            public enum HelpStatus {
                //0等待被确认(按钮都不显示)，1表示成功到达（按钮显示为已到达），2表示救援人员发布到达（显示到达和未到），3举报（显示到达和未到），4虚假到达信息（未到达） 5,警告处理（显示到达和未到）
                WAIT_CONFIRM("0"), GET_SUC("1"), RESCUE_GET_SUC("2"), REPORT("3"), FALSE_INFO("4"), WARNING("5");

                private String statusCode;

                HelpStatus(String statusCode) {
                    this.statusCode = statusCode;
                }

                public String getStatusCode() {
                    return statusCode;
                }
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getGre_distance() {
                return gre_distance;
            }

            public void setGre_distance(String gre_distance) {
                this.gre_distance = gre_distance;
            }

            public String getGre_id() {
                return gre_id;
            }

            public void setGre_id(String gre_id) {
                this.gre_id = gre_id;
            }

            public String getGre_jingwei() {
                return gre_jingwei;
            }

            public void setGre_jingwei(String gre_jingwei) {
                this.gre_jingwei = gre_jingwei;
            }

            public String getGre_status() {
                return gre_status;
            }

            public void setGre_status(String gre_status) {
                this.gre_status = gre_status;
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
}

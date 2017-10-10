package com.lshl.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class NotJoinBean {

    /**
     * status : 1
     * info : [{"businesscityno":"1298","buid":"1","businessname":"甘肃省山东商会","business_briefing":null,"business_shxx":null,"businesslogo":""},{"businesscityno":"1298","buid":"2","businessname":"西固区山东商会","business_briefing":null,"business_shxx":null,"businesslogo":""},{"businesscityno":"1298","buid":"3","businessname":"兰州市山东商会","business_briefing":"商会简介\r\n(商会的简单介绍，不超过200个汉字)\r\n","business_shxx":"243657c686a05c238.jpeg","businesslogo":""}]
     */

    private int status;
    /**
     * businesscityno : 1298
     * buid : 1
     * businessname : 甘肃省山东商会
     * business_briefing : null
     * business_shxx : null
     * businesslogo :
     */

    private List<InfoEntity> info;

    public static NotJoinBean objectFromData(String str) {

        return new Gson().fromJson(str, NotJoinBean.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InfoEntity> getInfo() {
        return info;
    }

    public void setInfo(List<InfoEntity> info) {
        this.info = info;
    }

    public static class InfoEntity {
        private String businesscityno;
        private String buid;
        private String businessname;
        private Object business_briefing;
        private Object business_shxx;
        private String businesslogo;

        public static InfoEntity objectFromData(String str) {

            return new Gson().fromJson(str, InfoEntity.class);
        }

        public String getBusinesscityno() {
            return businesscityno;
        }

        public void setBusinesscityno(String businesscityno) {
            this.businesscityno = businesscityno;
        }

        public String getBuid() {
            return buid;
        }

        public void setBuid(String buid) {
            this.buid = buid;
        }

        public String getBusinessname() {
            return businessname;
        }

        public void setBusinessname(String businessname) {
            this.businessname = businessname;
        }

        public Object getBusiness_briefing() {
            return business_briefing;
        }

        public void setBusiness_briefing(Object business_briefing) {
            this.business_briefing = business_briefing;
        }

        public Object getBusiness_shxx() {
            return business_shxx;
        }

        public void setBusiness_shxx(Object business_shxx) {
            this.business_shxx = business_shxx;
        }

        public String getBusinesslogo() {
            return businesslogo;
        }

        public void setBusinesslogo(String businesslogo) {
            this.businesslogo = businesslogo;
        }
    }
}

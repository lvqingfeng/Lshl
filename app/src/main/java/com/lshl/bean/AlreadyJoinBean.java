package com.lshl.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class AlreadyJoinBean {

    /**
     * status : 1
     * info : [{"business_id":"4","business_name":"天水市山东商会","business_briefing":"商会简介","business_shxx":"1845957c683642c880.jpeg","muid":"3","position":"副会长","add_time":"1471338575","businesslogo":"/Data/Uploads/businesslogo/4_200.jpg"},{"business_id":"3","business_name":"兰州市山东商会","business_briefing":"商会简介\r\n(商会的简单介绍，不超过200个汉字)\r\n","business_shxx":"243657c686a05c238.jpeg","muid":"3","position":"会长","add_time":"1471338575","businesslogo":"/Data/Uploads/businesslogo/3_200.jpg"}]
     */

    private int status;
    /**
     * business_id : 4
     * business_name : 天水市山东商会
     * business_briefing : 商会简介
     * business_shxx : 1845957c683642c880.jpeg
     * muid : 3
     * position : 副会长
     * add_time : 1471338575
     * businesslogo : /Data/Uploads/businesslogo/4_200.jpg
     */

    private List<InfoEntity> info;

    public static AlreadyJoinBean objectFromData(String str) {

        return new Gson().fromJson(str, AlreadyJoinBean.class);
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
        private String business_id;
        private String business_name;
        private String business_briefing;
        private String business_shxx;
        private String muid;
        private String position;
        private String add_time;
        private String businesslogo;

        public static InfoEntity objectFromData(String str) {

            return new Gson().fromJson(str, InfoEntity.class);
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getBusiness_briefing() {
            return business_briefing;
        }

        public void setBusiness_briefing(String business_briefing) {
            this.business_briefing = business_briefing;
        }

        public String getBusiness_shxx() {
            return business_shxx;
        }

        public void setBusiness_shxx(String business_shxx) {
            this.business_shxx = business_shxx;
        }

        public String getMuid() {
            return muid;
        }

        public void setMuid(String muid) {
            this.muid = muid;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getBusinesslogo() {
            return businesslogo;
        }

        public void setBusinesslogo(String businesslogo) {
            this.businesslogo = businesslogo;
        }
    }
}

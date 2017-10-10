package com.lshl.bean;

/**
 * Created by Administrator on 2016/11/15.
 */

public class WorkBean {

    /**
     * status : 1
     * info : {"id":"3","juid":"3","is_gudong":"1","worknature":"测试工作性质","workunit":"测试工作单位","workpost":"测试工作岗位","workaddress":"测试工作地址","workscope":"测试经营范围","add_time":"1470211546"}
     */

    private int status;
    /**
     * id : 3
     * juid : 3
     * is_gudong : 1
     * worknature : 测试工作性质
     * workunit : 测试工作单位
     * workpost : 测试工作岗位
     * workaddress : 测试工作地址
     * workscope : 测试经营范围
     * add_time : 1470211546
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
        private String id;
        private String juid;
        private String is_gudong;
        private String worknature;
        private String workunit;
        private String workpost;
        private String workaddress;
        private String workscope;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJuid() {
            return juid;
        }

        public void setJuid(String juid) {
            this.juid = juid;
        }

        public String getIs_gudong() {
            return is_gudong;
        }

        public void setIs_gudong(String is_gudong) {
            this.is_gudong = is_gudong;
        }

        public String getWorknature() {
            return worknature;
        }

        public void setWorknature(String worknature) {
            this.worknature = worknature;
        }

        public String getWorkunit() {
            return workunit;
        }

        public void setWorkunit(String workunit) {
            this.workunit = workunit;
        }

        public String getWorkpost() {
            return workpost;
        }

        public void setWorkpost(String workpost) {
            this.workpost = workpost;
        }

        public String getWorkaddress() {
            return workaddress;
        }

        public void setWorkaddress(String workaddress) {
            this.workaddress = workaddress;
        }

        public String getWorkscope() {
            return workscope;
        }

        public void setWorkscope(String workscope) {
            this.workscope = workscope;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}

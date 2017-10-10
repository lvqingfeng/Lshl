package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月29日
 * 时间：17:22
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AddressBean {


    /**
     * cityname : 北京市
     * id : 1
     * sub : [{"cityname":"北京","id":"1001"}]
     */

    private String cityname;
    private String id;
    /**
     * cityname : 北京
     * id : 1001
     */

    private List<SubBean> sub;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubBean> getSub() {
        return sub;
    }

    public void setSub(List<SubBean> sub) {
        this.sub = sub;
    }

    public static class SubBean {
        private String cityname;
        private String id;

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}

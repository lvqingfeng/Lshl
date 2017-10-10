package com.lshl.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class CityBean {

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

    private List<SubEntity> sub;

    public static CityBean objectFromData(String str) {

        return new Gson().fromJson(str, CityBean.class);
    }

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

    public List<SubEntity> getSub() {
        return sub;
    }

    public void setSub(List<SubEntity> sub) {
        this.sub = sub;
    }

    public static class SubEntity {
        private String cityname;
        private String id;

        public static SubEntity objectFromData(String str) {

            return new Gson().fromJson(str, SubEntity.class);
        }

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

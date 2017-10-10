package com.lshl.bean;

import java.util.List;

/**
 * @author yangqiangyu on 6/1/16 23:09
 * @for 地区
 */
public class AreaInfo {

    /**
     * cityname : 北京
     * id : 10
     * parentid : 0
     * sub : [{"cityname":"北京市","id":"1099","parentid":"10","sub":[{"cityname":"东城区","id":"1001","parentid":"1099","sub":[]},{"cityname":"西城区","id":"1002","parentid":"1099","sub":[]},{"cityname":"朝阳区","id":"1005","parentid":"1099","sub":[]},{"cityname":"海淀区","id":"1006","parentid":"1099","sub":[]},{"cityname":"丰台区","id":"1007","parentid":"1099","sub":[]},{"cityname":"石景山区","id":"1008","parentid":"1099","sub":[]},{"cityname":"顺义区","id":"1009","parentid":"1099","sub":[]},{"cityname":"昌平区","id":"1010","parentid":"1099","sub":[]},{"cityname":"门头沟区","id":"1011","parentid":"1099","sub":[]},{"cityname":"通州区","id":"1012","parentid":"1099","sub":[]},{"cityname":"房山区","id":"1013","parentid":"1099","sub":[]},{"cityname":"大兴区","id":"1014","parentid":"1099","sub":[]},{"cityname":"延庆县","id":"1015","parentid":"1099","sub":[]},{"cityname":"怀柔区","id":"1016","parentid":"1099","sub":[]},{"cityname":"平谷区","id":"1017","parentid":"1099","sub":[]},{"cityname":"密云县","id":"1018","parentid":"1099","sub":[]},{"cityname":"亦庄开发区","id":"1019","parentid":"1099","sub":[]}]}]
     */

    private String cityname;
    private String id;
    private String parentid;
    private List<SubBeanX> sub;

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

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<SubBeanX> getSub() {
        return sub;
    }

    public void setSub(List<SubBeanX> sub) {
        this.sub = sub;
    }

    public static class SubBeanX {
        /**
         * cityname : 北京市
         * id : 1099
         * parentid : 10
         * sub : [{"cityname":"东城区","id":"1001","parentid":"1099","sub":[]},{"cityname":"西城区","id":"1002","parentid":"1099","sub":[]},{"cityname":"朝阳区","id":"1005","parentid":"1099","sub":[]},{"cityname":"海淀区","id":"1006","parentid":"1099","sub":[]},{"cityname":"丰台区","id":"1007","parentid":"1099","sub":[]},{"cityname":"石景山区","id":"1008","parentid":"1099","sub":[]},{"cityname":"顺义区","id":"1009","parentid":"1099","sub":[]},{"cityname":"昌平区","id":"1010","parentid":"1099","sub":[]},{"cityname":"门头沟区","id":"1011","parentid":"1099","sub":[]},{"cityname":"通州区","id":"1012","parentid":"1099","sub":[]},{"cityname":"房山区","id":"1013","parentid":"1099","sub":[]},{"cityname":"大兴区","id":"1014","parentid":"1099","sub":[]},{"cityname":"延庆县","id":"1015","parentid":"1099","sub":[]},{"cityname":"怀柔区","id":"1016","parentid":"1099","sub":[]},{"cityname":"平谷区","id":"1017","parentid":"1099","sub":[]},{"cityname":"密云县","id":"1018","parentid":"1099","sub":[]},{"cityname":"亦庄开发区","id":"1019","parentid":"1099","sub":[]}]
         */

        private String cityname;
        private String id;
        private String parentid;
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

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public List<SubBean> getSub() {
            return sub;
        }

        public void setSub(List<SubBean> sub) {
            this.sub = sub;
        }

        public static class SubBean {
            /**
             * cityname : 东城区
             * id : 1001
             * parentid : 1099
             * sub : []
             */

            private String cityname;
            private String id;
            private String parentid;
            private List<?> sub;

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

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public List<?> getSub() {
                return sub;
            }

            public void setSub(List<?> sub) {
                this.sub = sub;
            }
        }
    }
}

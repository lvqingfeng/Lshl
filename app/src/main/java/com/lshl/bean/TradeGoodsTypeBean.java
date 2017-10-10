package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月01日
 * 时间：9:23
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class TradeGoodsTypeBean {

    /**
     * status : 1
     * info : [{"gd_id":1,"gd_name":"服装鞋包"},{"gd_id":2,"gd_name":"手机数码"},{"gd_id":3,"gd_name":"家用电器"},{"gd_id":4,"gd_name":"美妆饰品"},{"gd_id":5,"gd_name":"母婴用品"},{"gd_id":6,"gd_name":"家居建材"},{"gd_id":7,"gd_name":"百货食品"},{"gd_id":8,"gd_name":"户外用品"},{"gd_id":9,"gd_name":"休闲娱乐"},{"gd_id":10,"gd_name":"生活服务"},{"gd_id":11,"gd_name":"汽配摩托"},{"gd_id":12,"gd_name":"餐饮住宿"},{"gd_id":13,"gd_name":"医疗服务"},{"gd_id":14,"gd_name":"建筑机械"},{"gd_id":15,"gd_name":"科教服务"}]
     */

    private String status;
    /**
     * gd_id : 1
     * gd_name : 服装鞋包
     */

    private List<InfoBean> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        private String gd_id;
        private String gd_name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getGd_id() {
            return gd_id;
        }

        public void setGd_id(String gd_id) {
            this.gd_id = gd_id;
        }

        public String getGd_name() {
            return gd_name;
        }

        public void setGd_name(String gd_name) {
            this.gd_name = gd_name;
        }
    }
}

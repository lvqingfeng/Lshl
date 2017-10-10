package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public class IndustryBean {

    /**
     * status : 1
     * info : [{"in_id":1,"in_name":"建筑及装修"},{"in_id":2,"in_name":"教育培训"},{"in_id":3,"in_name":"旅游及票务"},{"in_id":4,"in_name":"休闲娱乐"},{"in_id":5,"in_name":"办公文教"},{"in_id":6,"in_name":"彩票"},{"in_id":7,"in_name":"游戏软件"},{"in_id":8,"in_name":"电子电工"},{"in_id":9,"in_name":"房地产"},{"in_id":10,"in_name":"生活服务"},{"in_id":11,"in_name":"图书音像"},{"in_id":12,"in_name":"安全安保"},{"in_id":13,"in_name":"服装鞋帽"},{"in_id":14,"in_name":"礼品饰品"},{"in_id":15,"in_name":"网络服务"},{"in_id":16,"in_name":"农林牧渔"},{"in_id":17,"in_name":"食品餐饮"},{"in_id":18,"in_name":"法律服务"},{"in_id":19,"in_name":"广告及包装"},{"in_id":20,"in_name":"机械设备"},{"in_id":21,"in_name":"生活用品"},{"in_id":22,"in_name":"医疗健康"},{"in_id":23,"in_name":"电脑配件"},{"in_id":24,"in_name":"交通运输"},{"in_id":25,"in_name":"金融服务"},{"in_id":26,"in_name":"商务服务"},{"in_id":27,"in_name":"家用电器"},{"in_id":28,"in_name":"招商加盟"},{"in_id":29,"in_name":"化工及材料"},{"in_id":30,"in_name":"成人用品"},{"in_id":31,"in_name":"广播通信"},{"in_id":32,"in_name":"节能环保"},{"in_id":33,"in_name":"铃声短信"},{"in_id":34,"in_name":"化妆品"},{"in_id":35,"in_name":"孕婴用品"}]
     */

    private int status;
    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * in_id : 1
         * in_name : 建筑及装修
         */

        private int in_id;
        private String in_name;

        public int getIn_id() {
            return in_id;
        }

        public void setIn_id(int in_id) {
            this.in_id = in_id;
        }

        public String getIn_name() {
            return in_name;
        }

        public void setIn_name(String in_name) {
            this.in_name = in_name;
        }
    }
}

package com.lshl.bean;

import com.lshl.db.bean.HxUserBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月14日
 * 时间：10:35
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class MemberBasicBean {


    /**
     * status : 1
     * info : [{"uid":"3","realname":"魁大圣","phone":"18366189918","avatar":"57fa4a479c6b4.png","hx_id":"2fcb13c32827c61a5d040c82e03eab28"}]
     */

    private int status;
    /**
     * uid : 3
     * realname : 魁大圣
     * phone : 18366189918
     * avatar : 57fa4a479c6b4.png
     * hx_id : 2fcb13c32827c61a5d040c82e03eab28
     */

    private List<HxUserBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<HxUserBean> getInfo() {
        return info;
    }

    public void setInfo(List<HxUserBean> info) {
        this.info = info;
    }

}

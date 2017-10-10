package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class DianzanListBean {

    /**
     * status : 1
     * info : [{"realname":"王小川"},{"realname":"I'm "}]
     */

    private int status;
    /**
     * realname : 王小川
     */

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
        private String realname;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}

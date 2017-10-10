package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public class ImageWallListBean {

    /**
     * status : 1
     * info : ["587f7ab5100e212648.png","587f7ab5100e267346.png","587f7ab513deb14600.png","587f7ab513deb18224.png","587f7ab513deb97817.png","587f7ab513deb19030.png","587f7ab513deb33711.png","587f7ab513deb52032.png"]
     */

    private int status;
    private List<String> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}

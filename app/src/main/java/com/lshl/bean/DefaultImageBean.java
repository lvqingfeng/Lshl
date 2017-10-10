package com.lshl.bean;

import java.util.List;

/***
 * Created by Administrator on 2017/2/9.
 */

public class DefaultImageBean {

    /**
     * status : 1
     * info : [{"img":"dbp1.jpg"},{"img":"dbp2.jpg"},{"img":"dbp3.jpg"},{"img":"dbp4.jpg"},{"img":"dbp5.jpg"},{"img":"dbp6.jpg"},{"img":"dbp7.jpg"},{"img":"dbp8.jpg"},{"img":"dbp9.jpg"},{"img":"dbp10.jpg"}]
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
         * img : dbp1.jpg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

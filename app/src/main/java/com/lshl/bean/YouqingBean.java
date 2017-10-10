package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class YouqingBean {

    /**
     * status : 1
     * info : [{"id":"1","name":"特优购商城","url":"http://lsteyougou.com/mobile/","source":"辽宁省山东商会","img":"1451758ca04ca2fbb7.jpeg"}]
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
         * id : 1
         * name : 特优购商城
         * url : http://lsteyougou.com/mobile/
         * source : 辽宁省山东商会
         * img : 1451758ca04ca2fbb7.jpeg
         */

        private String id;
        private String name;
        private String url;
        private String source;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}

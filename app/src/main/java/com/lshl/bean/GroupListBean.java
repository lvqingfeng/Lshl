package com.lshl.bean;

import com.lshl.db.bean.HxGroupBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月12日
 * 时间：19:11
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class GroupListBean {


    /**
     * end : 1
     * list : [{"add_time":"1476263195","gid":"45","group_id":"1476263193859","group_img":"57fdfd1ba784b.jpg","group_info":"美丽的真实故事张海迪","group_name":"帅哥美女"}]
     * pages : 1
     */

    private InfoBean info;
    /**
     * info : {"end":1,"list":[{"add_time":"1476263195","gid":"45","group_id":"1476263193859","group_img":"57fdfd1ba784b.jpg","group_info":"美丽的真实故事张海迪","group_name":"帅哥美女"}],"pages":1}
     * status : 1
     */

    private String status;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class InfoBean {
        private int end;
        private int pages;
        /**
         * add_time : 1476263195
         * gid : 45
         * group_id : 1476263193859
         * group_img : 57fdfd1ba784b.jpg
         * group_info : 美丽的真实故事张海迪
         * group_name : 帅哥美女
         */

        private List<HxGroupBean> list;

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<HxGroupBean> getList() {
            return list;
        }

        public void setList(List<HxGroupBean> list) {
            this.list = list;
        }

    }
}

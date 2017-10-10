package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月16日
 * 时间：21:33
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SHDynamListBean {


    /**
     * end : 1
     * list : [{"bd_addtime":"1475918070","bd_buid":"1","bd_comment_number":"0","bd_id":"22","bd_img":"2445157f8b8f6ab8f3.jpeg","bd_title":"CCTV\u201c中国好学生\u201d英语大赛上海赛区总决赛成功举行"}]
     * pages : 1
     */

    private int end;
    private int pages;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * bd_addtime : 1475918070
         * bd_buid : 1
         * bd_comment_number : 0
         * bd_id : 22
         * bd_img : 2445157f8b8f6ab8f3.jpeg
         * bd_title : CCTV“中国好学生”英语大赛上海赛区总决赛成功举行
         */

        private String bd_addtime;
        private String bd_buid;
        private String bd_comment_number;
        private String bd_id;
        private String bd_img;
        private String bd_title;

        public String getBd_addtime() {
            return bd_addtime;
        }

        public void setBd_addtime(String bd_addtime) {
            this.bd_addtime = bd_addtime;
        }

        public String getBd_buid() {
            return bd_buid;
        }

        public void setBd_buid(String bd_buid) {
            this.bd_buid = bd_buid;
        }

        public String getBd_comment_number() {
            return bd_comment_number;
        }

        public void setBd_comment_number(String bd_comment_number) {
            this.bd_comment_number = bd_comment_number;
        }

        public String getBd_id() {
            return bd_id;
        }

        public void setBd_id(String bd_id) {
            this.bd_id = bd_id;
        }

        public String getBd_img() {
            return bd_img;
        }

        public void setBd_img(String bd_img) {
            this.bd_img = bd_img;
        }

        public String getBd_title() {
            return bd_title;
        }

        public void setBd_title(String bd_title) {
            this.bd_title = bd_title;
        }
    }
}

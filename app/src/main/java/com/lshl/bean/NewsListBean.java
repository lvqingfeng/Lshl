package com.lshl.bean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：11:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsListBean {


    /**
     * end : 1
     * list : [{"add_time":"1478055158","description":"","id":"3","img":"pay_suc.jpg","onlylabel":"yaowen","source":"要闻","title":"韩海警对中国渔船执法首用机枪射击 中国渔船被押走"},{"add_time":"1478055158","description":"中联办主任：个别候任议员严重触碰一国两制底线","id":"4","img":"pay_suc.jpg","onlylabel":"yaowen","source":"要闻","title":"中联办主任：个别候任议员严重触碰一国两制底线"}]
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
         * add_time : 1478055158
         * description :
         * id : 3
         * img : pay_suc.jpg
         * onlylabel : yaowen
         * source : 要闻
         * title : 韩海警对中国渔船执法首用机枪射击 中国渔船被押走
         */

        private String add_time;
        private String description;
        private String id;
        private String img;
        private String onlylabel;
        private String source;
        private String title;
        private String project_id;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOnlylabel() {
            return onlylabel;
        }

        public void setOnlylabel(String onlylabel) {
            this.onlylabel = onlylabel;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：15:22
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsInfoBean {


    /**
     * id : 3
     * add_time : 1478055158
     * title : 韩海警对中国渔船执法首用机枪射击 中国渔船被押走
     * onlylabel : yaowen
     * project_id : null
     * sharepeople : null
     * sharepeople_type : 1
     * key :
     * comment : 0
     * name :
     */

    private String id;
    private String add_time;
    private String title;
    private String onlylabel;
    private String project_id;
    private Object sharepeople;
    private String sharepeople_type;
    private String comment_nums;
    private String key;
    private int comment;
    private String name;

    public String getComment_nums() {
        return comment_nums;
    }

    public void setComment_nums(String comment_nums) {
        this.comment_nums = comment_nums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnlylabel() {
        return onlylabel;
    }

    public void setOnlylabel(String onlylabel) {
        this.onlylabel = onlylabel;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Object getSharepeople() {
        return sharepeople;
    }

    public void setSharepeople(Object sharepeople) {
        this.sharepeople = sharepeople;
    }

    public String getSharepeople_type() {
        return sharepeople_type;
    }

    public void setSharepeople_type(String sharepeople_type) {
        this.sharepeople_type = sharepeople_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：10:16
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsTabBean {

    private int id;
    private int position;
    private boolean isAdd;
    private boolean isFixed;

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    /**
     * key : gongyi
     * val : 公益
     */

    private String key;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof NewsTabBean && ((NewsTabBean) obj).key.equals(this.key);
    }
}

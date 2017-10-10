package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：12月08日
 * 时间：10:34
 * 版本：v1.0.0
 * 类描述：用于存储权限判断的实体类
 * 修改时间：
 */

public class AuthorityCheckBean {

    /**
     * realname : 0
     * dues : 1
     * donation : 0
     */

    private int realname;
    private int dues;
    private int donation;
    private int basic;

    public int getBasic() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic = basic;
    }

    public int getRealname() {
        return realname;
    }

    public void setRealname(int realname) {
        this.realname = realname;
    }

    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }

    public int getDonation() {
        return donation;
    }

    public void setDonation(int donation) {
        this.donation = donation;
    }
}

package com.lshl.bean.request;

/**
 * 作者：吕振鹏
 * 创建时间：11月23日
 * 时间：20:31
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PublishHelpRequestBean {

    /**
     * token	string	Y
     * file	file	Y		互助图片
     * name	string	Y		互助名称
     * money	string	Y		互助金额
     * info	string	Y		互助信息
     * guarantee	string	Y		担保人列表
     * donate_money	string	Y		承诺捐款
     */

    private String token;
    private String name;
    private String money;
    private String info;
    private String guarantee;
    private String repayment;//还款时间
    private String repayment_time;//还款时间戳


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public String getRepayment_time() {
        return repayment_time;
    }

    public void setRepayment_time(String repayment_time) {
        this.repayment_time = repayment_time;
    }
}

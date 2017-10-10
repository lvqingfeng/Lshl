package com.lshl.bean;

import com.lshl.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月07日
 * 时间：16:25
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class DscsProjectDetailsBean implements Serializable {

    private boolean isHelp;
    private boolean isGood;

    private String id;//id
    private String status;//状态
    private String type;//类型(商会或个人)
    private String uid;//发起人uid
    private String title;//标题
    private String money;//筹款目标
    private String info;//详情
    private List<String> images;//上传的图片
    private String addTime;//添加时间
    private String address;//地址
    private String auditTime;//审核时间
    private String auditInfo;//后台审核信息

    private String f_vote_total_number; //初审的总人数
    private String f_vote_support; //初审的支持人数
    private String f_vote_opposition; //初审的反对
    private String f_vote_wairer; //初审的弃权
    private String f_vote_endtime; //初审的结束时间
    private String s_vote_total_number;
    private String s_vote_support;
    private String s_vote_opposition;
    private String s_vote_wairer;
    private String s_vote_enttime;
    private String t_vote_total_number;
    private String t_vote_support;
    private String t_vote_opposition;
    private String t_vote_wairer;
    private String t_vote_enttime;

    private String name;//发起人姓名
    private String allmember; //在初审,复审,终审 时的及时可投票人数，其他状态无用
    private String payment;//最终付款金额
    private String payment_time; //打款时间
    private String payment_info;//打款说明
    private String payment_img; //打款凭证

    //公益的特殊字段
    private String pw_receipt_info;//回馈说明
    private List<String> feedback_img;//回馈图片列表

    public boolean isHelp() {
        return isHelp;
    }

    public void setHelp(boolean help) {
        isHelp = help;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return Constant.getStatusForMutual(status);
    }

    public String getStatusCode() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getF_vote_total_number() {
        return f_vote_total_number;
    }

    public void setF_vote_total_number(String f_vote_total_number) {
        this.f_vote_total_number = f_vote_total_number;
    }

    public String getF_vote_support() {
        return f_vote_support;
    }

    public void setF_vote_support(String f_vote_support) {
        this.f_vote_support = f_vote_support;
    }

    public String getF_vote_opposition() {
        return f_vote_opposition;
    }

    public void setF_vote_opposition(String f_vote_opposition) {
        this.f_vote_opposition = f_vote_opposition;
    }

    public String getF_vote_wairer() {
        return f_vote_wairer;
    }

    public void setF_vote_wairer(String f_vote_wairer) {
        this.f_vote_wairer = f_vote_wairer;
    }

    public String getF_vote_endtime() {
        return f_vote_endtime;
    }

    public void setF_vote_endtime(String f_vote_endtime) {
        this.f_vote_endtime = f_vote_endtime;
    }

    public String getS_vote_total_number() {
        return s_vote_total_number;
    }

    public void setS_vote_total_number(String s_vote_total_number) {
        this.s_vote_total_number = s_vote_total_number;
    }

    public String getS_vote_support() {
        return s_vote_support;
    }

    public void setS_vote_support(String s_vote_support) {
        this.s_vote_support = s_vote_support;
    }

    public String getS_vote_opposition() {
        return s_vote_opposition;
    }

    public void setS_vote_opposition(String s_vote_opposition) {
        this.s_vote_opposition = s_vote_opposition;
    }

    public String getS_vote_wairer() {
        return s_vote_wairer;
    }

    public void setS_vote_wairer(String s_vote_wairer) {
        this.s_vote_wairer = s_vote_wairer;
    }

    public String getS_vote_enttime() {
        return s_vote_enttime;
    }

    public void setS_vote_enttime(String s_vote_enttime) {
        this.s_vote_enttime = s_vote_enttime;
    }

    public String getT_vote_total_number() {
        return t_vote_total_number;
    }

    public void setT_vote_total_number(String t_vote_total_number) {
        this.t_vote_total_number = t_vote_total_number;
    }

    public String getT_vote_support() {
        return t_vote_support;
    }

    public void setT_vote_support(String t_vote_support) {
        this.t_vote_support = t_vote_support;
    }

    public String getT_vote_opposition() {
        return t_vote_opposition;
    }

    public void setT_vote_opposition(String t_vote_opposition) {
        this.t_vote_opposition = t_vote_opposition;
    }

    public String getT_vote_wairer() {
        return t_vote_wairer;
    }

    public void setT_vote_wairer(String t_vote_wairer) {
        this.t_vote_wairer = t_vote_wairer;
    }

    public String getT_vote_enttime() {
        return t_vote_enttime;
    }

    public void setT_vote_enttime(String t_vote_enttime) {
        this.t_vote_enttime = t_vote_enttime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllmember() {
        return allmember;
    }

    public void setAllmember(String allmember) {
        this.allmember = allmember;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(String payment_info) {
        this.payment_info = payment_info;
    }

    public String getPayment_img() {
        return payment_img;
    }

    public void setPayment_img(String payment_img) {
        this.payment_img = payment_img;
    }

    public String getPw_receipt_info() {
        return pw_receipt_info;
    }

    public void setPw_receipt_info(String pw_receipt_info) {
        this.pw_receipt_info = pw_receipt_info;
    }

    public List<String> getFeedback_img() {
        return feedback_img;
    }

    public void setFeedback_img(List<String> feedback_img) {
        this.feedback_img = feedback_img;
    }
}

package com.lshl.db.bean;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：10月20日
 * 时间：11:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxNewContactNotifyBean implements Serializable {

    private int id;//数据库自动增id
    private long addTime;//申请的时间
    private String inviteName;//申请人的姓名（hx的id）
    private String reason;//附加条件
    private String avatar;//头像
    private String hxId;//环信id
    private boolean isUnRead;//是否未读

    private boolean isDispose;//是否处理过了
    private String disposeRequest;//处理后的结果（同意或者是拒绝）

    public boolean isDispose() {
        return isDispose;
    }

    public void setDispose(boolean dispose) {
        isDispose = dispose;
    }

    public String getDisposeRequest() {
        return disposeRequest;
    }

    public void setDisposeRequest(String disposeRequest) {
        this.disposeRequest = disposeRequest;
    }

    public boolean isUnRead() {
        return isUnRead;
    }

    public void setUnRead(boolean unRead) {
        isUnRead = unRead;
    }

    public String getHxId() {
        return hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getInviteName() {
        return inviteName;
    }

    public void setInviteName(String inviteName) {
        this.inviteName = inviteName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

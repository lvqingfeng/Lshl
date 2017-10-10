/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lshl.db.bean;

import java.io.Serializable;

public class InviteMessage implements Serializable {

    private String from;//请求人
    private long time;//请求时间
    private String reason;//申请的资料

    private InviteMesageStatus status;
    private String groupId;//群id
    private String groupName;//群名称
    private String groupInviter;//群邀请人

    private int id;//自增id

    private boolean unreadStatus;//未阅读状态
    private String realname;

    private String phone;
    private String avatar;

    private boolean isDispose;//是否处理过了
    private String disposeRequest;//处理后的结果（同意或者是拒绝）

    private String groupImage;
    private boolean isGroup;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getDisposeRequest() {
        return disposeRequest;
    }

    public void setDisposeRequest(String disposeRequest) {
        this.disposeRequest = disposeRequest;
    }

    public boolean isDispose() {
        return isDispose;
    }

    public void setDispose(boolean dispose) {
        isDispose = dispose;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public boolean isUnread() {
        return unreadStatus;
    }

    public void setUnreadStatus(boolean unreadStatus) {
        this.unreadStatus = unreadStatus;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InviteMesageStatus getStatus() {
        return status;
    }

    public void setStatus(InviteMesageStatus status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupInviter(String inviter) {
        groupInviter = inviter;
    }

    public String getGroupInviter() {
        return groupInviter;
    }


    public enum InviteMesageStatus {

        //==contact
        /**
         * being invited
         * 被邀请的
         */
        BEINVITEED(2010, "邀请你加入群"),
        /**
         * being refused
         * 被拒绝的
         */
        BEREFUSED(2011, "拒绝让你加入群"),
        /**
         * remote user already agreed
         * 远程用户已经同意
         */
        BEAGREED(2012, "同意加入群"),

        //==group application
        /**
         * remote user apply to join
         * 远程用户申请加入
         */
        BEAPPLYED(2013, "申请加入群"),
        /**
         * you have agreed to join
         * 你已经同意加入
         */
        AGREED(2014, "你已同意入群"),
        /**
         * you refused the join request
         * 您拒绝了连接请求
         */
        REFUSED(2015, "拒绝了连接请求"),

        //==group invitation
        /**
         * received remote user's invitation
         * 收到远程用户的邀请
         **/
        GROUPINVITATION(2016, "收到远程用户的邀请"),
        /**
         * remote user accept your invitation
         * 远程用户接受您的邀请
         */
        GROUPINVITATION_ACCEPTED(2017, "已加入"),

        /**
         * remote user declined your invitation
         * 远程用户拒绝了你的邀请
         */
        GROUPINVITATION_DECLINED(2018, "拒绝加入"),
        /**
         * group is dissolved
         */
        GROUP_DISSOLVED(2009, "已解散群"),
        /**
         * 被管理员移除出群组
         */
        GROUP_REMOVE(2008, "已将你移出群");


        private String value;
        private int code;

        InviteMesageStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}




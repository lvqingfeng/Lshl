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
package com.lshl.db;

import android.content.ContentValues;

import com.lshl.db.bean.InviteMessage;

import java.util.List;

/**
 * 群的邀请通知
 */
public class InviteMessageDao {

    static final String TABLE_NAME = "group_msgs";
    static final String COLUMN_NAME_ID = "id";//自整id
    static final String COLUMN_NAME_FROM = "username";//请求名称
    static final String COLUMN_NAME_GROUP_ID = "groupid";//群id
    public static final String COLUMN_NAME_GROUP_NAME = "groupname";//群名称

    static final String COLUMN_NAME_TIME = "time";//请求时间
    static final String COLUMN_NAME_REASON = "reason";//备注信息
    public static final String COLUMN_NAME_STATUS = "status";//通知类型
    static final String COLUMN_NAME_ISINVITEFROMME = "isInviteFromMe";//是否是来自我的邀请
    static final String COLUMN_NAME_GROUPINVITER = "groupinviter";//群邀请人
    @Deprecated //因为有了未读状态的标记，就可以废弃这个字段了
    static final String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";//未读信息的数量
    static final String COLUMN_NAME_UNREAD_STATUS = "unreadstatus";//标记信息是否为未读状态

    public static final String COLUMN_NAME_REALNAME = "realname";//真实姓名
    public static final String COLUMN_NAME_PHONE = "phone";//手机号
    public static final String COLUMN_NAME_AVATAR = "avatar";//头像

    public static final String COLUMN_NAME_IS_DISPOSE = "is_dispose";//是否对通知进行过操作（有些通知不需要操作，可以直接对这个值进行赋值）
    public static final String COLUMN_NAME_DISPOSE_REQUEST = "dispose_request";//操作消息的结果

    public static final String COLUMN_NAME_GROUP_IMAGE = "group_image";//群图片
    public static final String COLUMN_NAME_IS_GROUP = "is_group";//是否是群类型消息（因为请求加群的处理为个人类型的，所以要在这里加上一个标记）

    public InviteMessageDao() {
    }

    /**
     * save message
     *
     * @param message
     * @return return cursor of the message
     */
    public int saveMessage(InviteMessage message) {
        List<InviteMessage> inviteMessageList = ChatDBManager.getInstance().getMessagesList();
        for (InviteMessage inviteMessage : inviteMessageList) {
            if (inviteMessage.getFrom().equals(message.getFrom()) && inviteMessage.getGroupId().equals(message.getGroupId())) {
                if (!inviteMessage.isDispose()) {
                    deleteMessage(inviteMessage.getId());
                }
            }
        }
        return ChatDBManager.getInstance().saveMessage(message);
    }

    /**
     * update message
     *
     * @param msgId
     * @param values
     */
    public void updateMessage(int msgId, ContentValues values) {
        ChatDBManager.getInstance().updateMessage(msgId, values);
    }

    public InviteMessage getMessage(int id) {
        return ChatDBManager.getInstance().getMessages(String.valueOf(id));
    }

    /**
     * get messges
     *
     * @return
     */
    public List<InviteMessage> getMessagesList() {
        return ChatDBManager.getInstance().getMessagesList();
    }

    public void deleteMessage(int id) {
        ChatDBManager.getInstance().deleteMessage(id);
    }

    public int getUnreadMessagesCount() {
        return ChatDBManager.getInstance().getUnreadNotifyCount();
    }

    public void saveUnreadMessageCount(int count) {
        ChatDBManager.getInstance().setUnreadNotifyCount(count);
    }

    public void setUnReadStatus(boolean isUnread) {
        ChatDBManager.getInstance().setUnreadNotifyStatus(isUnread);
    }
}

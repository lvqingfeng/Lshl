package com.lshl.db;

import android.content.ContentValues;

import com.lshl.db.bean.HxNewContactNotifyBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月20日
 * 时间：12:18
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxNewContactNotifyDao {

    static final String TABLE_NAME = "new_friend_msg";

    static final String COLUMN_NAME_ID = "id";//数据库自增加id
    static final String COLUMN_NAME_ADD_TIME = "add_time";//添加时间
    public static final String COLUMN_NAME_INVITE_NAME = "invite_name";//申请人姓名
    public static final String COLUMN_NAME_AVATAR = "avatar";//头像
    static final String COLUMN_NAME_REASON = "reason";//申请条件
    static final String COLUMN_NAME_HX_ID = "hx_id";
    static final String COLUMN_NAME_IS_UNREAD = "is_unread";
    static final String COLUMN_NAME_IS_DISPOSE = "is_dispose";//是否对通知进行过操作（有些通知不需要操作，可以直接对这个值进行赋值）
    static final String COLUMN_NAME_DISPOSE_REQUEST = "dispose_request";//操作消息的结果


    public int saveContactNotify(HxNewContactNotifyBean userBean) {

        List<HxNewContactNotifyBean> list = getContactNotifyList();
        for (HxNewContactNotifyBean bean : list) {
            if (bean.getHxId().equals(userBean.getHxId())) {
                deleteContactNotify(bean.getId());
            }
        }

        return ChatDBManager.getInstance().saveContactNotify(userBean);
    }


    public void deleteContactNotify(int id) {
        ChatDBManager.getInstance().deleteContactNotify(id);
    }


    public void updateContactNotify(int id, ContentValues values) {
        ChatDBManager.getInstance().updateContactNotify(id, values);
    }


    public List<HxNewContactNotifyBean> getContactNotifyList() {
        return ChatDBManager.getInstance().getContactNotifyList();
    }

    public int getUnreadNotifyCount(){
        return ChatDBManager.getInstance().getContactUnreadNotifyCount();
    }

    public void setUnreadStatus(boolean isUnread){
        ChatDBManager.getInstance().setUnreadNewContactNotifyStatus(isUnread);
    }

}

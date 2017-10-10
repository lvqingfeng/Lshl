package com.lshl.db;

import android.content.ContentValues;

import com.lshl.db.bean.HxGroupBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月17日
 * 时间：22:08
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxGroupDao {

    static final String TABLE_NAME = "group_info";

    static final String COLUMN_NAME_ID = "id";
    static final String COLUMN_NAME_ADD_TIME = "add_time";
    static final String COLUMN_NAME_GID = "gid";
    static final String COLUMN_NAME_GROUP_ID = "group_id";
    static final String COLUMN_NAME_GROUP_IMG = "group_img";
    static final String COLUMN_NAME_GROUP_INFO = "group_info";
    static final String COLUMN_NAME_GROUP_NAME = "group_name";


    public List<HxGroupBean> getGroupBeanList() {
        return ChatDBManager.getInstance().getGroupInfoList();
    }

    public int saveGroupInfo(HxGroupBean groupBean) {
        return ChatDBManager.getInstance().saveGroupInfo(groupBean);
    }

    public void deleteGroupInfo(int id) {
        ChatDBManager.getInstance().deleteGroupInfo(id);
    }

    public void updateGroupInfo(int id, ContentValues values) {
        ChatDBManager.getInstance().updateGroupInfo(id, values);
    }

    public void updateGroupInfo(int id, HxGroupBean bean) {
        ContentValues values = new ContentValues();
        values.put(HxGroupDao.COLUMN_NAME_GROUP_NAME, bean.getGroup_name());
        values.put(HxGroupDao.COLUMN_NAME_GROUP_IMG, bean.getGroup_img());
        values.put(HxGroupDao.COLUMN_NAME_GROUP_INFO, bean.getGroup_info());
        ChatDBManager.getInstance().updateGroupInfo(id, values);
    }

    public HxGroupBean getGroupInfo(String hxId) {
        return ChatDBManager.getInstance().getGroupInfo(hxId);
    }

    public int getGroupDbId(String hxId) {
        return ChatDBManager.getInstance().getGroupDbId(hxId);
    }

}

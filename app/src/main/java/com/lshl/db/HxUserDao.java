package com.lshl.db;

import android.content.ContentValues;
import android.util.Log;

import com.lshl.db.bean.HxUserBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月15日
 * 时间：15:04
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxUserDao {


    public static final String TABLE_NAME = "user_details";
    public static final String TEMP_USER_TABLE = "temp_user_details";

    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_REAL_NAME = "real_name";
    public static final String COLUMN_NAME_AVATAR = "avatar";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_UID = "uid";
    public static final String COLUMN_NAME_HX_ID = "hx_id";
    public static final String COLUMN_NAME_ADDRESS = "address";
    public static final String COLUMN_NAME_GRADE = "grade";

    public void saveTempUserDetails(HxUserBean userBean) {
        ChatDBManager.getInstance().saveTempUserDetails(userBean);
    }

    public List<HxUserBean> getTempUserDetailsList() {
        return ChatDBManager.getInstance().getUserDetailsList();
    }

    public void updateTempUserDetails(int id, HxUserBean bean) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AVATAR, bean.getAvatar());
        values.put(COLUMN_NAME_REAL_NAME, bean.getRealname());
        values.put(COLUMN_NAME_PHONE, bean.getPhone());
        values.put(COLUMN_NAME_ADDRESS, bean.getLive_cityname());
        values.put(COLUMN_NAME_GRADE, bean.getGrade());
        ChatDBManager.getInstance().updateTempUserDetails(id, values);
    }

    public void saveUserDetails(HxUserBean userBean) {
        int id = ChatDBManager.getInstance().saveUserDetails(userBean);
        Log.d("lvzp", "保存的id为= " + id);
    }

    public void deleteUserDetails(int id) {
        ChatDBManager.getInstance().deleteUserDetails(id);
    }


    public void updateUserDetails(int id, HxUserBean bean) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AVATAR, bean.getAvatar());
        values.put(COLUMN_NAME_REAL_NAME, bean.getRealname());
        values.put(COLUMN_NAME_PHONE, bean.getPhone());
        values.put(COLUMN_NAME_ADDRESS, bean.getLive_cityname());
        values.put(COLUMN_NAME_GRADE, bean.getGrade());
        ChatDBManager.getInstance().updateUserDetails(id, values);
    }


    public List<HxUserBean> getUserDetailsList() {
        return ChatDBManager.getInstance().getUserDetailsList();
    }

    public int getUserDetailsDbId(String hxId) {
        return ChatDBManager.getInstance().getUserDetails(hxId).getId();
    }

    public void cleanUser() {
        ChatDBManager.getInstance().clearFeedTable();
    }
}

package com.lshl.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.lshl.base.LshlApplication;
import com.lshl.bean.NewsTabBean;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.db.bean.HxNewContactNotifyBean;
import com.lshl.db.bean.HxUserBean;
import com.lshl.db.bean.InviteMessage;
import com.lshl.db.bean.InviteMessage.InviteMesageStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatDBManager {
    static private ChatDBManager dbMgr = new ChatDBManager();
    private DbOpenHelper dbHelper;

    private ChatDBManager() {
        dbHelper = DbOpenHelper.getInstance(LshlApplication.getInstance().getApplicationContext());
    }

    public static synchronized ChatDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new ChatDBManager();
        }
        return dbMgr;
    }

    synchronized List<HxNewContactNotifyBean> getContactNotifyList() {
        List<HxNewContactNotifyBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxNewContactNotifyDao.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                HxNewContactNotifyBean bean = new HxNewContactNotifyBean();

                readContactNotify(cursor, bean);

                list.add(0, bean);
            }
            cursor.close();
        }


        return list;
    }

    private void readContactNotify(Cursor cursor, HxNewContactNotifyBean bean) {

        bean.setAvatar(cursor.getString(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_AVATAR)));
        bean.setAddTime(cursor.getLong(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_ADD_TIME)));
        bean.setId(cursor.getInt(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_ID)));
        bean.setInviteName(cursor.getString(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_INVITE_NAME)));
        bean.setReason(cursor.getString(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_REASON)));
        bean.setHxId(cursor.getString(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_HX_ID)));
        bean.setUnRead(cursor.getInt(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_IS_UNREAD)) == 1);
        bean.setDispose(cursor.getInt(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_IS_DISPOSE)) == 1);
        bean.setDisposeRequest(cursor.getString(cursor.getColumnIndex(HxNewContactNotifyDao.COLUMN_NAME_DISPOSE_REQUEST)));

    }

    public int saveContactNotify(HxNewContactNotifyBean notifyBean) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(HxNewContactNotifyDao.COLUMN_NAME_ADD_TIME, notifyBean.getAddTime());
            values.put(HxNewContactNotifyDao.COLUMN_NAME_AVATAR, notifyBean.getAvatar());
            values.put(HxNewContactNotifyDao.COLUMN_NAME_INVITE_NAME, notifyBean.getInviteName());
            values.put(HxNewContactNotifyDao.COLUMN_NAME_REASON, notifyBean.getReason());
            values.put(HxNewContactNotifyDao.COLUMN_NAME_HX_ID, notifyBean.getHxId());
            values.put(HxNewContactNotifyDao.COLUMN_NAME_IS_UNREAD, notifyBean.isUnRead() ? 1 : 0);
            values.put(HxNewContactNotifyDao.COLUMN_NAME_IS_DISPOSE, notifyBean.isDispose() ? 1 : 0);
            values.put(HxNewContactNotifyDao.COLUMN_NAME_DISPOSE_REQUEST, notifyBean.getDisposeRequest());

            db.insert(HxNewContactNotifyDao.TABLE_NAME, null, values);
        }

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + HxNewContactNotifyDao.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    synchronized void deleteContactNotify(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(HxNewContactNotifyDao.TABLE_NAME, HxNewContactNotifyDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }

    public void updateContactNotify(int id, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(HxNewContactNotifyDao.TABLE_NAME, values, HxNewContactNotifyDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }

    synchronized void deleteGroupInfo(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(HxGroupDao.TABLE_NAME, HxGroupDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }

    public void updateGroupInfo(int id, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(HxGroupDao.TABLE_NAME, values, HxGroupDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }

    /*    Class classT = groupBean.getClass();
            Field[] fields = classT.getFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    values.put(fieldName, String.valueOf(field.get(fieldName)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }*/
    synchronized int saveGroupInfo(HxGroupBean groupBean) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(HxGroupDao.COLUMN_NAME_GROUP_ID, groupBean.getGroup_id());
            values.put(HxGroupDao.COLUMN_NAME_GID, groupBean.getGid());
            values.put(HxGroupDao.COLUMN_NAME_GROUP_IMG, groupBean.getGroup_img());
            values.put(HxGroupDao.COLUMN_NAME_GROUP_INFO, groupBean.getGroup_info());
            values.put(HxGroupDao.COLUMN_NAME_GROUP_NAME, groupBean.getGroup_name());
            values.put(HxGroupDao.COLUMN_NAME_ADD_TIME, groupBean.getAdd_time());

            db.insert(HxGroupDao.TABLE_NAME, null, values);
        }

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + HxGroupDao.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    synchronized List<HxGroupBean> getGroupInfoList() {
        List<HxGroupBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxGroupDao.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                HxGroupBean bean = new HxGroupBean();

                readGroupInfo(cursor, bean);

                list.add(bean);
            }
            cursor.close();
        }


        return list;
    }

    private synchronized void readGroupInfo(Cursor cursor, HxGroupBean bean) {
        bean.setAdd_time(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_ADD_TIME)));
        bean.setGid(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_GID)));
        bean.setGroup_id(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_GROUP_ID)));
        bean.setGroup_img(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_GROUP_IMG)));
        bean.setGroup_info(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_GROUP_INFO)));
        bean.setGroup_name(cursor.getString(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_GROUP_NAME)));
        bean.setId(cursor.getInt(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_ID)));
    }

    public HxGroupBean getGroupInfo(String hxId) {

        HxGroupBean bean = new HxGroupBean();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxGroupDao.TABLE_NAME, null, HxGroupDao.COLUMN_NAME_GROUP_ID + " = ?", new String[]{hxId}, null, null, null);
            while (cursor.moveToNext()) {
                readGroupInfo(cursor, bean);
            }
            cursor.close();
        }
        return bean;
    }

    public int getGroupDbId(String hxId) {

        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxGroupDao.TABLE_NAME, null, HxGroupDao.COLUMN_NAME_GROUP_ID + " = ?", new String[]{hxId}, null, null, null);
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(HxGroupDao.COLUMN_NAME_ID));
            }
            cursor.close();
        }
        return id;
    }

    synchronized int saveTempUserDetails(HxUserBean userBean) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(HxUserDao.COLUMN_NAME_UID, userBean.getUid());
            values.put(HxUserDao.COLUMN_NAME_PHONE, userBean.getPhone());
            values.put(HxUserDao.COLUMN_NAME_AVATAR, userBean.getAvatar());
            values.put(HxUserDao.COLUMN_NAME_REAL_NAME, userBean.getRealname());
            values.put(HxUserDao.COLUMN_NAME_HX_ID, userBean.getHx_id());

            db.insert(HxUserDao.TEMP_USER_TABLE, null, values);
        }

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + HxUserDao.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    public List<HxUserBean> getTempUserDetailsList() {

        List<HxUserBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxUserDao.TEMP_USER_TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                HxUserBean bean = new HxUserBean();

                getReadUserDetails(cursor, bean);

                list.add(bean);
            }
            cursor.close();
        }


        return list;
    }

    public void updateTempUserDetails(int id, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(HxUserDao.TEMP_USER_TABLE, values, HxUserDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }
    }

    synchronized int saveUserDetails(HxUserBean userBean) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(HxUserDao.COLUMN_NAME_UID, userBean.getUid());
            values.put(HxUserDao.COLUMN_NAME_PHONE, userBean.getPhone());
            values.put(HxUserDao.COLUMN_NAME_AVATAR, userBean.getAvatar());
            values.put(HxUserDao.COLUMN_NAME_REAL_NAME, userBean.getRealname());
            values.put(HxUserDao.COLUMN_NAME_HX_ID, userBean.getHx_id());
            values.put(HxUserDao.COLUMN_NAME_ADDRESS, userBean.getLive_cityname());
            values.put(HxUserDao.COLUMN_NAME_GRADE, userBean.getGrade());

            db.insert(HxUserDao.TABLE_NAME, null, values);
        }

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + HxUserDao.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    synchronized void deleteUserDetails(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(HxUserDao.TABLE_NAME, HxUserDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }

    public void updateUserDetails(int id, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(HxUserDao.TABLE_NAME, values, HxUserDao.COLUMN_NAME_ID + " = ? ", new String[]{String.valueOf(id)});
        }

    }


    public List<HxUserBean> getUserDetailsList() {

        List<HxUserBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxUserDao.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                HxUserBean bean = new HxUserBean();

                getReadUserDetails(cursor, bean);

                list.add(bean);
            }
            cursor.close();
        }


        return list;
    }

    private void getReadUserDetails(Cursor cursor, HxUserBean bean) {
        bean.setId(cursor.getInt(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_ID)));
        bean.setUid(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_UID)));
        bean.setRealname(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_REAL_NAME)));
        bean.setAvatar(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_AVATAR)));
        bean.setHx_id(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_HX_ID)));
        bean.setPhone(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_PHONE)));
        bean.setLive_cityname(cursor.getString(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_ADDRESS)));
        bean.setGrade(cursor.getInt(cursor.getColumnIndex(HxUserDao.COLUMN_NAME_GRADE)));
    }


    public HxUserBean getUserDetails(String hxId) {
        HxUserBean bean = new HxUserBean();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {

            Cursor cursor = db.query(HxUserDao.TABLE_NAME, null, HxUserDao.COLUMN_NAME_HX_ID + " = ?", new String[]{hxId}, null, null, null);

            while (cursor.moveToNext()) {
                getReadUserDetails(cursor, bean);
            }

        }
        return bean;
    }

    public void clearFeedTable() {

        String sql = "DELETE FROM " + HxUserDao.TABLE_NAME + ";";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL(sql);


    }

    /**
     * save a message
     *
     * @param message
     * @return return cursor of the message
     */
    public synchronized Integer saveMessage(InviteMessage message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {

            ContentValues values = new ContentValues();
            values.put(InviteMessageDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessageDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(InviteMessageDao.COLUMN_NAME_GROUP_NAME, message.getGroupName());
            values.put(InviteMessageDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessageDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessageDao.COLUMN_NAME_STATUS, message.getStatus().getCode());
            values.put(InviteMessageDao.COLUMN_NAME_DISPOSE_REQUEST, message.getDisposeRequest());
            values.put(InviteMessageDao.COLUMN_NAME_GROUPINVITER, message.getGroupInviter());
            values.put(InviteMessageDao.COLUMN_NAME_REALNAME, message.getRealname());
            values.put(InviteMessageDao.COLUMN_NAME_PHONE, message.getPhone());
            values.put(InviteMessageDao.COLUMN_NAME_GROUP_IMAGE, message.getGroupImage());
            values.put(InviteMessageDao.COLUMN_NAME_AVATAR, message.getAvatar());

            values.put(InviteMessageDao.COLUMN_NAME_UNREAD_STATUS, message.isUnread() ? 1 : 0);
            values.put(InviteMessageDao.COLUMN_NAME_IS_DISPOSE, message.isDispose() ? 1 : 0);
            values.put(InviteMessageDao.COLUMN_NAME_IS_GROUP, message.isGroup() ? 1 : 0);
            db.insert(InviteMessageDao.TABLE_NAME, null, values);

            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessageDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }

            cursor.close();
        }
        return id;
    }

    /**
     * update message
     *
     * @param msgId
     * @param values
     */
    synchronized void updateMessage(int msgId, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(InviteMessageDao.TABLE_NAME, values, InviteMessageDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }


    /**
     * get messges
     *
     * @return
     */
    synchronized public List<InviteMessage> getMessagesList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + InviteMessageDao.TABLE_NAME + " desc", null);
            while (cursor.moveToNext()) {
                InviteMessage msg = getInviteMessage(cursor);
                msgs.add(0, msg);
            }
            cursor.close();
        }
        return msgs;
    }

    @NonNull
    private InviteMessage getInviteMessage(Cursor cursor) {
        InviteMessage msg = new InviteMessage();
        int id = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_ID));
        String from = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_FROM));
        String groupid = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUP_ID));
        String groupname = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUP_NAME));
        int isGroup = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_IS_GROUP));
        String groupImage = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUP_IMAGE));

        String reason = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_REASON));
        String realname = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_REALNAME));
        String phone = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_PHONE));
        String avatar = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_AVATAR));
        long time = cursor.getLong(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_TIME));
        int status = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_STATUS));
        String groupInviter = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUPINVITER));
        int unreadStatus = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_UNREAD_STATUS));
        int isDispose = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_IS_DISPOSE));
        String disposeRequest = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_DISPOSE_REQUEST));

        msg.setId(id);
        msg.setFrom(from);
        msg.setGroupId(groupid);
        msg.setGroupName(groupname);
        msg.setReason(reason);
        msg.setTime(time);
        msg.setGroupInviter(groupInviter);
        msg.setUnreadStatus(unreadStatus == 1);
        msg.setDispose(isDispose == 1);
        msg.setRealname(realname);
        msg.setGroup(isGroup == 1);
        msg.setGroupImage(groupImage);
        msg.setPhone(phone);
        msg.setAvatar(avatar);
        msg.setDisposeRequest(disposeRequest);

        for (InviteMesageStatus inviteMesageStatus : InviteMesageStatus.values()) {
            if (status == inviteMesageStatus.getCode()) {
                msg.setStatus(inviteMesageStatus);
            }
        }
       /* if (status == InviteMesageStatus.BEINVITEED.getCode())
            msg.setStatus(InviteMesageStatus.BEINVITEED);
        else if (status == InviteMesageStatus.BEAGREED.getCode())
            msg.setStatus(InviteMesageStatus.BEAGREED);
        else if (status == InviteMesageStatus.BEREFUSED.getCode())
            msg.setStatus(InviteMesageStatus.BEREFUSED);
        else if (status == InviteMesageStatus.AGREED.getCode())
            msg.setStatus(InviteMesageStatus.AGREED);
        else if (status == InviteMesageStatus.REFUSED.getCode())
            msg.setStatus(InviteMesageStatus.REFUSED);
        else if (status == InviteMesageStatus.BEAPPLYED.getCode())
            msg.setStatus(InviteMesageStatus.BEAPPLYED);
        else if (status == InviteMesageStatus.GROUPINVITATION.getCode())
            msg.setStatus(InviteMesageStatus.GROUPINVITATION);
        else if (status == InviteMesageStatus.GROUPINVITATION_ACCEPTED.getCode())
            msg.setStatus(InviteMesageStatus.GROUPINVITATION_ACCEPTED);
        else if (status == InviteMesageStatus.GROUPINVITATION_DECLINED.getCode())
            msg.setStatus(InviteMesageStatus.GROUPINVITATION_DECLINED);
        else if (status == InviteMesageStatus.GROUP_REMOVE.getCode())
            msg.setStatus();*/
        return msg;
    }

    /**
     * 取存储在数据库中，对应id的数据
     *
     * @param mgsId
     * @return
     */
    public InviteMessage getMessages(String mgsId) {
        InviteMessage msg = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(InviteMessageDao.TABLE_NAME, null, InviteMessageDao.COLUMN_NAME_ID + " = ? ", new String[]{mgsId}, null, null, null);
            while (cursor.moveToNext()) {
                msg = getInviteMessage(cursor);
            }
            cursor.close();
        }

        return msg;
    }

    /**
     * delete invitation message
     *
     * @param id
     */
    synchronized public void deleteMessage(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(InviteMessageDao.TABLE_NAME, InviteMessageDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        }
    }

 /*   synchronized int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + InviteMessageDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessageDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }*/

    synchronized void setUnreadNotifyCount(int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessageDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);
            db.update(InviteMessageDao.TABLE_NAME, values, null, null);
        }
    }

    synchronized int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(InviteMessageDao.TABLE_NAME, null, InviteMessageDao.COLUMN_NAME_UNREAD_STATUS + " = ? ", new String[]{String.valueOf(1)}, null, null, null);
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    synchronized void setUnreadNotifyStatus(boolean isUnread) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessageDao.COLUMN_NAME_UNREAD_STATUS, isUnread ? 1 : 0);
            db.update(InviteMessageDao.TABLE_NAME, values, null, null);
        }
    }

    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
        dbMgr = null;
    }

    public int getContactUnreadNotifyCount() {

        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(HxNewContactNotifyDao.TABLE_NAME, null, HxNewContactNotifyDao.COLUMN_NAME_IS_UNREAD + " = ? ", new String[]{String.valueOf(1)}, null, null, null);
            count = cursor.getCount();
            cursor.close();
        }
        return count;

    }

    public void setUnreadNewContactNotifyStatus(boolean isUnread) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(HxNewContactNotifyDao.COLUMN_NAME_IS_UNREAD, isUnread ? 1 : 0);
            db.update(HxNewContactNotifyDao.TABLE_NAME, values, null, null);
        }
    }

    /**
     * 新闻页的新闻频道管理
     */
    synchronized List<NewsTabBean> getNewsTabList() {
        List<NewsTabBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(NewsTabDao.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                NewsTabBean bean = new NewsTabBean();
                readNewsTab(cursor, bean);
                list.add(bean);
            }
            cursor.close();
        }


        return list;
    }

    /**
     * 获取已定制的频道（有序的排列）
     *
     * @return
     */
    synchronized List<NewsTabBean> getAddNewsTab() {
        List<NewsTabBean> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(NewsTabDao.TABLE_NAME, null, NewsTabDao.COLUMN_NAME_IS_ADD + " = ? ", new String[]{String.valueOf(1)}, null, null, null);
            NewsTabBean[] tabBeans = new NewsTabBean[cursor.getCount()];
            while (cursor.moveToNext()) {
                NewsTabBean bean = new NewsTabBean();
                readNewsTab(cursor, bean);
                list.add(bean);
            }
            cursor.close();

            for (NewsTabBean bean : list) {
                tabBeans[bean.getPosition()] = bean;
            }
            list.clear();
            Collections.addAll(list, tabBeans);
        }
        return list;

    }

    private void readNewsTab(Cursor cursor, NewsTabBean bean) {
        bean.setId(cursor.getInt(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_ID)));
        bean.setKey(cursor.getString(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_KEY)));
        bean.setVal(cursor.getString(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_VALUE)));
        bean.setPosition(cursor.getInt(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_POSITION)));
        bean.setFixed(cursor.getInt(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_IS_FIXED)) == 1);
        bean.setAdd(cursor.getInt(cursor.getColumnIndex(NewsTabDao.COLUMN_NAME_IS_ADD)) == 1);
    }

    synchronized int saveNewsTab(NewsTabBean userBean) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            values.put(NewsTabDao.COLUMN_NAME_KEY, userBean.getKey());
            values.put(NewsTabDao.COLUMN_NAME_VALUE, userBean.getVal());
            values.put(NewsTabDao.COLUMN_NAME_POSITION, userBean.getPosition());
            values.put(NewsTabDao.COLUMN_NAME_IS_ADD, userBean.isAdd() ? 1 : 0);
            values.put(NewsTabDao.COLUMN_NAME_IS_FIXED, userBean.isFixed() ? 1 : 0);

            db.insert(NewsTabDao.TABLE_NAME, null, values);
        }

        Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessageDao.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;
    }

    synchronized void updateNewsTab(int id, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(NewsTabDao.TABLE_NAME, values, NewsTabDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        }
    }

    
/*    *//**
     * Save Robot list
     *//*
    synchronized public void saveRobotList(List<RobotUser> robotList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(UserDao.ROBOT_TABLE_NAME, null, null);
			for (RobotUser item : robotList) {
				ContentValues values = new ContentValues();
				values.put(UserDao.ROBOT_COLUMN_NAME_ID, item.getUsername());
				if (item.getNick() != null)
					values.put(UserDao.ROBOT_COLUMN_NAME_NICK, item.getNick());
				if (item.getAvatar() != null)
					values.put(UserDao.ROBOT_COLUMN_NAME_AVATAR, item.getAvatar());
				db.replace(UserDao.ROBOT_TABLE_NAME, null, values);
			}
		}
	}
    
    *//**
     * load robot list
     *//*
    synchronized public Map<String, RobotUser> getRobotList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, RobotUser> users = null;
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + UserDao.ROBOT_TABLE_NAME, null);
			if(cursor.getCount()>0){
				users = new Hashtable<String, RobotUser>();
			}
            while (cursor.moveToNext()) {
				String username = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_ID));
				String nick = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_NICK));
				String avatar = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_AVATAR));
				RobotUser user = new RobotUser(username);
				user.setNick(nick);
				user.setAvatar(avatar);
				String headerName = null;
				if (!TextUtils.isEmpty(user.getNick())) {
					headerName = user.getNick();
				} else {
					headerName = user.getUsername();
				}
				if(Character.isDigit(headerName.charAt(0))){
					user.setInitialLetter("#");
				}else{
					user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target
							.substring(0, 1).toUpperCase());
					char header = user.getInitialLetter().toLowerCase().charAt(0);
					if (header < 'a' || header > 'z') {
						user.setInitialLetter("#");
					}
				}

                try {
                    users.put(username, user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
			cursor.close();
		}
		return users;
	}*/
}

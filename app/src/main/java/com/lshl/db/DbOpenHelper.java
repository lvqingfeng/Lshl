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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lshl.base.LshlApplication;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static DbOpenHelper instance;
    private static final String NEWS_TAB_TABLE_CREATE = "CREATE TABLE "
            + NewsTabDao.TABLE_NAME + " ("
            + NewsTabDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NewsTabDao.COLUMN_NAME_KEY + " TEXT, "
            + NewsTabDao.COLUMN_NAME_VALUE + " TEXT, "
            + NewsTabDao.COLUMN_NAME_POSITION + " INTEGER, "
            + NewsTabDao.COLUMN_NAME_IS_FIXED + " INTEGER, "
            + NewsTabDao.COLUMN_NAME_IS_ADD + " INTEGER);";
    private static final String USER_TEMP_DETAILS_TABLE_CREATE = "CREATE TABLE "
            + HxUserDao.TEMP_USER_TABLE + " ("
            + HxUserDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HxUserDao.COLUMN_NAME_REAL_NAME + " TEXT, "
            + HxUserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + HxUserDao.COLUMN_NAME_PHONE + " TEXT, "
            + HxUserDao.COLUMN_NAME_UID + " TEXT, "
            + HxUserDao.COLUMN_NAME_ADDRESS + " TEXT, "
            + HxUserDao.COLUMN_NAME_GRADE + " INTEGER, "
            + HxUserDao.COLUMN_NAME_HX_ID + " TEXT);";
    private static final String USER_DETAILS_TABLE_CREATE = "CREATE TABLE "
            + HxUserDao.TABLE_NAME + " ("
            + HxUserDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HxUserDao.COLUMN_NAME_REAL_NAME + " TEXT, "
            + HxUserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + HxUserDao.COLUMN_NAME_PHONE + " TEXT, "
            + HxUserDao.COLUMN_NAME_UID + " TEXT, "
            + HxUserDao.COLUMN_NAME_ADDRESS + " TEXT, "
            + HxUserDao.COLUMN_NAME_GRADE + " INTEGER, "
            + HxUserDao.COLUMN_NAME_HX_ID + " TEXT);";

    private static final String GROUP_INFO_TABLE_CREATE = "CREATE TABLE "
            + HxGroupDao.TABLE_NAME + " ("
            + HxGroupDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HxGroupDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + HxGroupDao.COLUMN_NAME_GROUP_IMG + " TEXT, "
            + HxGroupDao.COLUMN_NAME_GID + " TEXT, "
            + HxGroupDao.COLUMN_NAME_GROUP_INFO + " TEXT, "
            + HxGroupDao.COLUMN_NAME_GROUP_NAME + " TEXT, "
            + HxGroupDao.COLUMN_NAME_ADD_TIME + " TEXT);";

    private static final String NEW_FRIEND_NOTIFY_TABLE_CREATE = "CREATE TABLE "
            + HxNewContactNotifyDao.TABLE_NAME + " ("
            + HxNewContactNotifyDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HxNewContactNotifyDao.COLUMN_NAME_INVITE_NAME + " TEXT, "
            + HxNewContactNotifyDao.COLUMN_NAME_AVATAR + " TEXT, "
            + HxNewContactNotifyDao.COLUMN_NAME_ADD_TIME + " TEXT, "
            + HxNewContactNotifyDao.COLUMN_NAME_HX_ID + " TEXT, "
            + HxNewContactNotifyDao.COLUMN_NAME_IS_DISPOSE + " INTEGER, "
            + HxNewContactNotifyDao.COLUMN_NAME_DISPOSE_REQUEST + " TEXT, "
            + HxNewContactNotifyDao.COLUMN_NAME_IS_UNREAD + " INTEGER, "
            + HxNewContactNotifyDao.COLUMN_NAME_REASON + " TEXT);";

    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessageDao.TABLE_NAME + " ("
            + InviteMessageDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InviteMessageDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_GROUP_NAME + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_REALNAME + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_PHONE + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_AVATAR + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_DISPOSE_REQUEST + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_GROUP_IMAGE + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_IS_GROUP + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_IS_DISPOSE + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_UNREAD_STATUS + " INTEGER, "
            + InviteMessageDao.COLUMN_NAME_TIME + " TEXT, "
            + InviteMessageDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";

	/*private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
            + HxUserDao.ROBOT_TABLE_NAME + " ("
			+ HxUserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
			+ HxUserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
			+ HxUserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";*/

/*	private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + HxUserDao.PREF_TABLE_NAME + " ("
            + HxUserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + HxUserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";*/

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return LshlApplication.getCurrentUserName() + "_demo.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_DETAILS_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(GROUP_INFO_TABLE_CREATE);
        db.execSQL(NEW_FRIEND_NOTIFY_TABLE_CREATE);
        db.execSQL(NEWS_TAB_TABLE_CREATE);
        db.execSQL(USER_TEMP_DETAILS_TABLE_CREATE);
      /*   db.execSQL(ROBOT_TABLE_CREATE);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= 2) {
            db.execSQL("ALTER TABLE " + NewsTabDao.TABLE_NAME + " ADD COLUMN " +
                    NewsTabDao.COLUMN_NAME_IS_FIXED + " INTEGER ;");
        }
        if (oldVersion <= 3) {
            db.execSQL(USER_TEMP_DETAILS_TABLE_CREATE);
        }

        if (oldVersion <= 4) {
            db.execSQL("ALTER TABLE " + HxUserDao.TABLE_NAME + " ADD COLUMN " +
                    HxUserDao.COLUMN_NAME_ADDRESS + " TEXT ;");
            db.execSQL("ALTER TABLE " + HxUserDao.TABLE_NAME + " ADD COLUMN " +
                    HxUserDao.COLUMN_NAME_GRADE + " INTEGER ;");
        }
    }
   /*     if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + HxUserDao.TABLE_NAME + " ADD COLUMN " +
                    HxUserDao.COLUMN_NAME_AVATAR + " TEXT ;");
        }

        if (oldVersion < 3) {
            db.execSQL(CREATE_PREF_TABLE);
        }
        if (oldVersion < 4) {
            db.execSQL(ROBOT_TABLE_CREATE);
        }*/
     /*   if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + InviteMessageDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessageDao.COLUMN_NAME_UNREAD_STATUS + " INTEGER ;");
        }  */
       /* if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + InviteMessageDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessageDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
        }*/

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}

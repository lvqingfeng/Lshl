package com.lshl.db;

import android.content.ContentValues;

import com.lshl.bean.NewsTabBean;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月28日
 * 时间：23:20
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsTabDao {

    public static final String TABLE_NAME = "news_tab";

    public static final String COLUMN_NAME_ID = "id";//数据库中自增id
    public static final String COLUMN_NAME_KEY = "key";//名称
    public static final String COLUMN_NAME_VALUE = "value";//值
    public static final String COLUMN_NAME_POSITION = "position";//被定制的显示为第几个
    public static final String COLUMN_NAME_IS_ADD = "isAdd";//是否被定制
    public static final String COLUMN_NAME_IS_FIXED = "isFixed";//是否是固定的

    public List<NewsTabBean> getNewsTabList() {
        return ChatDBManager.getInstance().getNewsTabList();
    }

    public List<NewsTabBean> getAddNewsTabList() {
        return ChatDBManager.getInstance().getAddNewsTab();
    }

    public void saveNewsTabList(List<NewsTabBean> list) {
        if (list != null) {
            for (NewsTabBean bean : list) {
                ChatDBManager.getInstance().saveNewsTab(bean);
            }
        }
    }

    public void saveNewsTab(NewsTabBean bean) {
        ChatDBManager.getInstance().saveNewsTab(bean);
    }

    public void updateNewsTab(int id, NewsTabBean bean) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_KEY, bean.getKey());
        values.put(COLUMN_NAME_VALUE, bean.getVal());
        values.put(COLUMN_NAME_POSITION, bean.getPosition());
        values.put(COLUMN_NAME_IS_ADD, bean.isAdd() ? 1 : 0);
        ChatDBManager.getInstance().updateNewsTab(id, values);
    }

}

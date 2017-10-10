package com.lshl.ui.info;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.lshl.bean.MailListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：鲁商互联
 * 类描述：读取手机数据库中的通讯录
 * 创建人：吕清锋
 * 创建时间：2017/3/21 15:52
 * 修改备注：
 */
public class GetPhoneNumberFromMobile {
    private List<MailListBean> mList;
    public List<MailListBean> getPhoneNumberFromMobile(Context mContext){
        mList=new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            MailListBean phoneInfo = new MailListBean(name, number);
            mList.add(phoneInfo);
        }
        return mList;
    }
}

package com.lshl;

import android.content.Context;

import com.lshl.bean.User;
import com.lshl.utils.UserUtils;

/**
 
 */

public class LoginHelper {

    private static LoginHelper sInstance;
    private Context mContext;
    private User mUserBean;
    private boolean isOnline;

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LoginHelper();
        }
        return sInstance;
    }

    public LoginHelper init(Context context) {
        mContext = context;
        isOnline = checkIsOnline();
        return this;
    }

    private boolean checkIsOnline() {
        boolean isonline;
        User user = UserUtils.getUser(mContext);
        System.out.println("---获取到的用户的key--" + user.getToken());
        if (user.getToken() != null) {
            isonline = true;
            System.out.println("--用户id为" + user.getToken());
        } else {
            System.out.println("--用户对象为空");
            isonline = false;
        }

        if (isonline) {
            mUserBean = user;
        }

        return isonline;
    }


    public boolean userExit() {
        mUserBean = null;
        return UserUtils.quit(mContext);
    }

    public User getUserBean() {
        return mUserBean;
    }

    public String getUserToken() {
        if (mUserBean != null)
            return mUserBean.getToken();
        return "";
    }

    public String getHxId() {
        if (mUserBean != null)
            return mUserBean.getHxid();
        return "";
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        if (online) {
            mUserBean = UserUtils.getUser(mContext);
        } else {
            userExit();
        }
        isOnline = online;
    }


}

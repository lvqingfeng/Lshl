package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseGroup;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

public class EaseUserUtils {

    public static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    public static void getTempUserList(String hxIds, EMUserListCallback callback) {
        userProvider.getTempUserList(hxIds, callback);
    }

    public static void getTempUserInfo(String username, EMUserInfoCallback callback) {
        userProvider.getTempUser(username, callback);
    }

    public static EaseGroup getGroupInfo(String groupId) {
        if (userProvider != null)
            return userProvider.getGroup(groupId);
        return null;
    }

    public static void setGoupAvatar(String groupId, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        EaseGroup group = getGroupInfo(groupId);
        if (group != null && group.getAvatar() != null && !group.getAvatar().contains("null")) {
            userProvider.setHxAvatar(group.getAvatar(), imageView);
        }
    }

    public static void setUserShow(String username, final TextView textView, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        EaseUser user = getUserInfo(username);
        //从好友列表中获取，判断如果是空的，或者是头像地址为空，就通过网络获取他的头像和名称
        if (user == null || TextUtils.isEmpty(user.getAvatar())) {
            userProvider.setUserAvatar(textView, imageView, username,"");
        } else {//否则就去直接设置
            setUserAvatar(username, imageView);
            setUserNick(username, textView);
        }
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(String username, ImageView imageView) {
        Context context = imageView.getContext();
        EaseUser user = getUserInfo(username);
        if (user != null && user.getAvatar() != null && !user.getAvatar().contains("null")) {
            try {
                String avatar = user.getAvatar();
                System.out.println("----- 通讯录列表头像地址 -----" + avatar);
                userProvider.setHxAvatar(avatar, imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context)
                        .load(user.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ease_default_avatar)
                        .into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNick() != null) {
                textView.setText(user.getNick());
            } else {
                textView.setText(username);
            }
        }
    }

    /**
     * set user's address
     */
    public static void setUserAddress(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getAddress() != null) {
                textView.setText(user.getAddress());
            }
        }
    }

    public static String getFriendRealName(String hxName) {
        return userProvider.getFriendRealName(hxName);
    }

    public interface EMUserInfoCallback {
        void onDataCallback(EaseUser user);
    }

    public interface EMUserListCallback {
        void onUserListCallback(List<EaseUser> list);
    }
}

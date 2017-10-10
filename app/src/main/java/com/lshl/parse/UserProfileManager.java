package com.lshl.parse;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.lshl.LoginHelper;
import com.lshl.api.ApiClient;
import com.lshl.db.bean.HxUserBean;
import com.lshl.task.TaskBase;
import com.lshl.utils.PreferenceManager;

public class UserProfileManager {

    /**
     * application context
     */
    protected Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init
     * again
     */
    private boolean sdkInited = false;

    /**
     * HuanXin sync contact nick and avatar listener
     */
/*	private List<DataSyncListener> syncContactInfosListeners;*/

    private boolean isSyncingContactInfosWithServer = false;

    private EaseUser currentUser;

    public UserProfileManager() {
    }

    public synchronized boolean init(Context context) {
        if (sdkInited) {
            return true;
        }
/*    	ParseManager.getInstance().onInit(context);*/
      /*  syncContactInfosListeners = new ArrayList<DataSyncListener>();*/
        sdkInited = true;
        return true;
    }


    public synchronized EaseUser getCurrentUserInfo() {
        if (currentUser == null) {
            final String username = EMClient.getInstance().getCurrentUser();
            currentUser = new EaseUser(username);
            TaskBase.getUserDetails(LoginHelper.getInstance().getUserToken(), username, new TaskBase.OnGetDataCallBack<HxUserBean>() {
                @Override
                public void onResult(HxUserBean userBean) {
                    currentUser.setAvatar(ApiClient.getHxFriendsImage(userBean.getAvatar()));
                    currentUser.setNickname(userBean.getRealname());
                    currentUser.setNick(userBean.getRealname());
                }

                @Override
                public void onError(String err) {

                }
            });
         /*   String nick = getCurrentUserNick();
            currentUser.setNick((nick != null) ? nick : username);
            currentUser.setAvatar(getCurrentUserAvatar());*/
        }
        return currentUser;
    }

/*    public boolean updateCurrentUserNickName(final String nickname) {
        boolean isSuccess = ParseManager.getInstance().updateParseNickName(nickname);
        if (isSuccess) {
            setCurrentUserNick(nickname);
        }
        return isSuccess;
    }

    public String uploadUserAvatar(byte[] data) {
        String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
        if (avatarUrl != null) {
            setCurrentUserAvatar(avatarUrl);
        }
        return avatarUrl;
    }*/

    /*    public void asyncGetCurrentUserInfo() {
            ParseManager.getInstance().asyncGetCurrentUserInfo(new EMValueCallBack<EaseUser>() {

                @Override
                public void onSuccess(EaseUser value) {
                    if (value != null) {
                        setCurrentUserNick(value.getNick());
                        setCurrentUserAvatar(value.getAvatar());
                    }
                }

                @Override
                public void onError(int error, String errorMsg) {

                }
            });

        }

        public void asyncGetUserInfo(final String username, final EMValueCallBack<EaseUser> callback) {
            ParseManager.getInstance().asyncGetUserInfo(username, callback);
        }*/
    public synchronized void reset() {
        isSyncingContactInfosWithServer = false;
        currentUser = null;
        PreferenceManager.getInstance().removeCurrentUserInfo();
    }

    private void setCurrentUserNick(String nickname) {
        getCurrentUserInfo().setNick(nickname);
        PreferenceManager.getInstance().setCurrentUserNick(nickname);
    }

    private void setCurrentUserAvatar(String avatar) {
        getCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }

}

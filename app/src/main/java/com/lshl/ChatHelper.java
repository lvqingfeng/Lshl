package com.lshl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseGroup;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.EMLog;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.LshlApplication;
import com.lshl.bean.GroupListBean;
import com.lshl.bean.MemberBasicBean;
import com.lshl.db.ChatDBManager;
import com.lshl.db.HxGroupDao;
import com.lshl.db.HxNewContactNotifyDao;
import com.lshl.db.HxUserDao;
import com.lshl.db.InviteMessageDao;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.db.bean.HxNewContactNotifyBean;
import com.lshl.db.bean.HxUserBean;
import com.lshl.db.bean.InviteMessage;
import com.lshl.parse.UserProfileManager;
import com.lshl.receiver.CallReceiver;
import com.lshl.task.TaskBase;
import com.lshl.ui.MainActivity;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.utils.AppManager;
import com.lshl.utils.EmojiconExampleGroupData;
import com.lshl.utils.UserUtils;
import com.yunzhanghu.redpacketui.RedPacketConstant;
import com.yunzhanghu.redpacketui.utils.RedPacketUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 作者：吕振鹏
 * 创建时间：10月10日
 * 时间：18:07
 * 版本：v1.0.0
 * 类描述：用于注册聊天信息监听的
 * 修改时间：
 */

public class ChatHelper {

    protected static final String TAG = "DemoHelper";
    private Map<Integer, Boolean[]> mRequestStatusMap;


    private static ChatHelper sInstance = null;

    private Context mContext;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;


    private LocalBroadcastManager broadcastManager;
    private InviteMessageDao mInviteMessageDao;
    private HxUserDao mHxUserDao;
    private HxNewContactNotifyDao mNewContactNotifyDao;

    private Map<String, EaseUser> mContactList;
    private Map<String, EaseUser> mTempContactList;
    private UserProfileManager userProManager;

    private EaseUI mEaseUI;
    public boolean isVoiceCalling;
    public boolean isVideoCalling;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;
    private CallReceiver callReceiver;
    private ChatModel mChatMode;
    private HxGroupDao mHxGroupDao;
    private EMOptions emOptions;
    private String realName;
    private String portrait;
    private HxUserBean infoBean;

    private ChatHelper() {

    }

    public synchronized static ChatHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ChatHelper();
        }
        return sInstance;
    }

    public void init(Context context) {

        mContext = context;
        mChatMode = new ChatModel(context);
        emOptions = getOptions();

        if (EaseUI.getInstance().init(context, emOptions)) {

            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(false);
            initEMUINotification();
            mEaseUI = getEaseUI();
            broadcastManager = LocalBroadcastManager.getInstance(context);
            //to set user's profile and avatar
            //设置用户的配置文件和头像+/
            setEaseUIProviders();
            //注册消息的监听
            registerMessageListener();
            //注册群组变化的监听
            registerGroupListener();
            //注册好友的监听
            registerContactListener();

            registerCallListener();

            getUserInfo(EMClient.getInstance().getCurrentUser());
        }


    }


    private void initEMUINotification() {

        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return "鲁商互联";
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return R.mipmap.ic_launcher;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, mContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                //设置点击通知栏跳转事件
                Intent intent = new Intent(mContext, ChatActivity.class);
                //有电话时优先跳转到通话页面
             /*   if(isVideoCalling){
                    intent = new Intent(isDeviceProtectedStorage(), VideoCallActivity.class);
                }else if(isVoiceCalling){
                    intent = new Intent(sApl, VoiceCallActivity.class);
                }else{*/
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                    intent.putExtra("userId", message.getFrom());
                    intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                } else { // 群聊信息
                    // message.getTo()为群聊id
                    intent.putExtra("userId", message.getTo());
                    if (chatType == EMMessage.ChatType.GroupChat) {
                        intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                    } else {
                        intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                    }

                    //}
                }
                return intent;
            }
        });

    }

    private EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        //谷歌云通知的消息身份配置 you need apply & set your own id if you want to use google cloud messaging.
        options.setGCMNumber("324169311137");
        //小米推送身份配置 you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        // 华为推送配置 you need apply & set your own id if you want to use Huawei push notification
        options.setHuaweiPushAppId("10492024");

    /*    //set custom servers, commonly used in private deployment
        if(demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
            options.setRestServer(demoModel.getRestServer());
            options.setIMServer(demoModel.getIMServer());
            if(demoModel.getIMServer().contains(":")) {
                options.setIMServer(demoModel.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
            }
        }*/

        options.allowChatroomOwnerLeave(false);
        options.setAutoAcceptGroupInvitation(false);

        return options;
    }


    private void registerCallListener() {

        // create the global connection listener
        EMConnectionListener connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                if (error == EMError.USER_REMOVED) {
                    onCurrentAccountRemoved();
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onConnectionConflict();
                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
                    EMLog.d(TAG, "group and contact already synced with servre");
                } else {
                    if (!isGroupsSyncedWithServer) {
                        //asyncFetchGroupsFromServer(null);
                    }

                    if (!isContactsSyncedWithServer) {
                        //asyncFetchContactsFromServer(null);
                    }

                    if (!isBlackListSyncedWithServer) {
                        //asyncFetchBlackListFromServer(null);
                    }
                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }

        //register incoming call receiver
        mContext.registerReceiver(callReceiver, callFilter); /**/
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);

    }

    /**
     * account is removed
     */
    private void onCurrentAccountRemoved() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.ACCOUNT_REMOVED, true);
        mContext.startActivity(intent);
    }

    /**
     * user has logged into another device
     * 用户已登录到另一个设备
     */
    private void onConnectionConflict() {
        if (LoginHelper.getInstance().isOnline()) {
            AppManager.getAppManager().finishOtherActivity();
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.ACCOUNT_CONFLICT, true);
            mContext.startActivity(intent);
        }

    }

    private void registerGroupListener() {
        EMClient.getInstance().groupManager().addGroupChangeListener(new EMGroupChangeListener() {

            @Override
            public void onUserRemoved(String groupId, String groupName) {
                //当前用户被管理员移除出群组

                Log.d("EMGroupChangeListener", "--- 当前用户被管理员移除出群组---- groupId = " + groupId + "---groupName = " + groupName);
                InviteMessage msg = new InviteMessage();
                msg.setFrom(groupName);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setGroup(true);
                msg.setRealname("管理员");
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.GROUP_REMOVE);
                notifyNewInviteMessage(msg);

                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
            }

            @Override
            public void onGroupDestroyed(String groupId, String groupName) {
                //群组被创建者解散

                //当前用户被管理员移除出群组

                Log.d("EMGroupChangeListener", "--- 当前用户被管理员移除出群组---- groupId = " + groupId + "---groupName = " + groupName);
                InviteMessage msg = new InviteMessage();
                msg.setFrom(groupName);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setGroup(true);
                msg.setRealname("管理员");
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.GROUP_DISSOLVED);
                notifyNewInviteMessage(msg);

                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));

            }

            @Override
            public void onAutoAcceptInvitationFromGroup(String groupId, String s1, String s2) {
                //自动同意邀请入群
                Log.d("EMGroupChangeListener", "--- 自动同意邀请入群---- groupId = " + groupId + "---s1 = " + s1 + " ----s2 = " + s2);

            }

            @Override
            public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
                //收到加入群组的邀请
                Log.d("EMGroupChangeListener", "--- 收到加入群组的邀请---- groupId = " + groupId + "---groupName = " + groupName + " ----邀请人 = " + inviter + " --- reason = " + reason);

                InviteMessage msg = new InviteMessage();
                msg.setFrom(inviter);
                msg.setUserId(inviter);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setReason("");
                msg.setUnreadStatus(true);
                msg.setGroup(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
                notifyNewInviteMessage(msg);
            }

            @Override
            public void onInvitationDeclined(String groupId, String invitee, String reason) {
                //群组邀请被拒绝
                Log.d("EMGroupChangeListener", "--- 群组邀请被拒绝---- groupId = " + groupId + "---groupName = " + invitee + " ----reason = " + reason);

                InviteMessage msg = new InviteMessage();
                msg.setFrom(invitee);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName("");
                msg.setGroup(false);
                msg.setUserId(invitee);
                msg.setReason(reason);
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED);
                notifyNewInviteMessage(msg);
            }

            @Override
            public void onInvitationAccepted(String groupId, String invitee, String reason) {
                //群组邀请被接受
                Log.d("EMGroupChangeListener", "--- 群组邀请被接受---- groupId = " + groupId + "---reason = " + reason + " ----被邀请人：invitee = " + invitee);
                InviteMessage msg = new InviteMessage();
                msg.setFrom(invitee);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName("");
                msg.setUserId(invitee);
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setGroup(false);
                msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED);
                notifyNewInviteMessage(msg);
            }

            @Override
            public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {
                //收到加群申请
                Log.d("EMGroupChangeListener", "--- 收到加群申请---- groupId = " + groupId + "---groupName = " + groupName + " ----applyer = " + applyer + " --- reason = " + reason);
                // user apply to join group
                InviteMessage msg = new InviteMessage();
                msg.setFrom(applyer);
                msg.setUserId(applyer);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setReason(reason);
                msg.setUnreadStatus(true);
                msg.setGroup(false);
                msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                notifyNewInviteMessage(msg);


            }

            @Override
            public void onApplicationAccept(String groupId, String groupName, String accepter) {
                //加群申请被同意
                Log.d("EMGroupChangeListener", "--- 加群申请被同意---- groupId = " + groupId + "---groupName = " + groupName + " ----accepter = " + accepter);
                InviteMessage msg = new InviteMessage();
                msg.setFrom(groupName);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setUserId(accepter);
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setGroup(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                notifyNewInviteMessage(msg);
                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
            }

            @Override
            public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
                // 加群申请被拒绝
                Log.d("EMGroupChangeListener", "--- 加群申请被拒绝---- groupId = " + groupId + "---groupName = " + groupName + " ----decliner = " + decliner + " --- reason = " + reason);
                InviteMessage msg = new InviteMessage();
                msg.setFrom(groupName);
                msg.setTime(System.currentTimeMillis());
                msg.setGroupId(groupId);
                msg.setGroupName(groupName);
                msg.setGroup(true);
                msg.setUserId(decliner);
                msg.setReason(reason);
                msg.setRealname("管理员");
                msg.setUnreadStatus(true);
                msg.setDispose(true);
                msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                notifyNewInviteMessage(msg);

            }


        });
    }

    private void registerContactListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactAgreed(String username) {
                //好友请求被同意
                Log.d("EMContactListener", "--- 好友请求被同意---- username = " + username);
            }

            @Override
            public void onContactRefused(String username) {
                //好友请求被拒绝
                Log.d("EMContactListener", "--- 好友请求被拒绝---- username = " + username);

            }

            @Override
            public void onContactInvited(String username, String reason) {

                HxNewContactNotifyBean bean = new HxNewContactNotifyBean();
                bean.setReason(reason);
                bean.setHxId(username);
                bean.setAddTime(System.currentTimeMillis());
                bean.setUnRead(true);
                bean.setDispose(false);

                notifyNewContactMessage(bean);
              /*  //收到好友邀请
                Log.d("EMContactListener", "--- 收到好友邀请---- username = " + username + "---reason = " + reason);
                //收到好友邀请
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(username);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
                Log.d("EMContactListener", "--- 被删除时回调此方法---- username = " + username);
                broadcastManager.sendBroadcast(new Intent().setAction(Constant.ACTION_CONTACT_REFRESU));
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                Log.d("EMContactListener", "--- 增加了联系人时回调此方法---- username = " + username);
                broadcastManager.sendBroadcast(new Intent().setAction(Constant.ACTION_CONTACT_REFRESU));
            }
        });
    }

    private void notifyNewContactMessage(final HxNewContactNotifyBean bean) {

        final int dbId = getHxNewContactNotifyDao().saveContactNotify(bean);
        TaskBase.getUserDetails(LoginHelper.getInstance().getUserToken(), bean.getHxId(), new TaskBase.OnGetDataCallBack<HxUserBean>() {
            @Override
            public void onResult(HxUserBean userBean) {
                if (userBean != null) {

                    String realName = userBean.getRealname();
                    String avatar = userBean.getAvatar();

                    ContentValues values = new ContentValues();
                    values.put(HxNewContactNotifyDao.COLUMN_NAME_AVATAR, avatar);
                    values.put(HxNewContactNotifyDao.COLUMN_NAME_INVITE_NAME, realName);
                    getHxNewContactNotifyDao().updateContactNotify(dbId, values);

                    bean.setId(dbId);
                    bean.setAvatar(avatar);
                    bean.setInviteName(realName);

                    Intent intent = new Intent().setAction(Constant.ACTION_NEW_CONTACT_NOTIFY);
                    intent.putExtra(Constant.NEW_CONTACT_NOTIFY_EXTRA, bean);
                    broadcastManager.sendBroadcast(intent);
                }
                getNotifier().vibrateAndPlayTone(null);
            }

            @Override
            public void onError(String err) {
                Log.e("ChatHelpre", "notifyNewContactMessage方法中出现问题");
            }
        });
    }

    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {

        messageListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if (!mEaseUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }
                    if (message.getChatType() == EMMessage.ChatType.GroupChat)
                        broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                    else
                        broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //red packet code : 处理红包回执透传消息
                    if (!mEaseUI.hasForegroundActivies()) {
                        if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                            RedPacketUtil.receiveRedPacketAckMessage(message);
                            broadcastManager.sendBroadcast(new Intent(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION));
                        }
                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    /**
     * save and notify invitation message
     *
     * @param msg
     */
    private void notifyNewInviteMessage(InviteMessage msg) {

        if (mInviteMessageDao == null) {
            mInviteMessageDao = new InviteMessageDao();
        }
        int id = mInviteMessageDao.saveMessage(msg);
        getRequestStatusMap().put(id, new Boolean[]{false, false});

        requestMemberBasic(msg.getUserId(), id);
        requestGroupBasic(msg.getGroupId(), id);
    }

    /**
     * get sInstance of EaseNotifier
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return mEaseUI.getNotifier();
    }

    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 检测是否需要退出环信
     */
    public void checkIsLogout() {
        if (!LoginHelper.getInstance().isOnline() && ChatHelper.getInstance().isLoggedIn()) {
            getOptions().setAutoLogin(false);
            ChatHelper.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(int i, final String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }

    public void login(EMCallBack callBack) {
        String userName = UserUtils.getUser(mContext).getHxid();
        String password = UserUtils.getUserPassword(mContext);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password))
            return;
        EMClient.getInstance().login(userName, password, callBack);
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                LshlApplication.getApplication().setAuthorityBean(null);
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    private void reset() {


        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;


        setContactList(null);

        getUserProfileManager().reset();
        ChatDBManager.getInstance().closeDB();

    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestGroupBasic(String groupId, final int mgsId) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.Object)
                        .lookupGroupList(groupId, String.valueOf(0)),
                new Subscriber<GroupListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getRequestStatusMap().get(mgsId)[1] = true;
                        sendBroadcast(mgsId);
                    }

                    @Override
                    public void onNext(GroupListBean groupListBean) {
                        HxGroupBean groupInfo = groupListBean.getInfo().getList().get(0);
                        if (groupInfo != null) {
                            ContentValues values = new ContentValues();
                            values.put(InviteMessageDao.COLUMN_NAME_GROUP_IMAGE, groupInfo.getGroup_img());
                            values.put(InviteMessageDao.COLUMN_NAME_GROUP_NAME, groupInfo.getGroup_name());
                            mInviteMessageDao.updateMessage(mgsId, values);
                            System.out.println("------ group信息已更新完成 ----");
                        }
                        getRequestStatusMap().get(mgsId)[1] = true;
                        sendBroadcast(mgsId);
                    }
                }
        );
    }

    /**
     * 这个方法的作用是，根据环信的账号(就是id)，请求自己的网络数据库，获取他的一些基本信息
     * 然后更新到数据中
     *
     * @param memberId 环信中的个人账号
     * @param mgsId    存到数据库中的id
     */
    private void requestMemberBasic(String memberId, final int mgsId) {
        if (TextUtils.isEmpty(memberId)) {
            getRequestStatusMap().get(mgsId)[0] = true;
            sendBroadcast(mgsId);
            return;
        }
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).getMemberRealname(LoginHelper.getInstance().getUserToken(), memberId)
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getRequestStatusMap().get(mgsId)[0] = true;
                        sendBroadcast(mgsId);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            MemberBasicBean memBerBasicBean = null;

                            if (status.equals(ApiService.STATUS_SUC))
                                memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);

                            //这里为空判断，只有当返回的状态码为真时，这个才不会是空的
                            if (memBerBasicBean != null) {
                                //虽然返回的是一个列表 因为我们查询的至是一个账号的id,所以直接取第0个数据就可以
                                HxUserBean infoBean = memBerBasicBean.getInfo().get(0);
                                if (infoBean != null) {
                                    ContentValues values = new ContentValues();
                                    values.put(InviteMessageDao.COLUMN_NAME_REALNAME, infoBean.getRealname());
                                    values.put(InviteMessageDao.COLUMN_NAME_PHONE, infoBean.getPhone());
                                    values.put(InviteMessageDao.COLUMN_NAME_AVATAR, infoBean.getAvatar());
                                    mInviteMessageDao.updateMessage(mgsId, values);
                                    System.out.println("------ user信息已更新完成 ----");
                                }

                            }
                            getRequestStatusMap().get(mgsId)[0] = true;
                            sendBroadcast(mgsId);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }


    private void sendBroadcast(Integer mgsId) {
        boolean isUserReady = getRequestStatusMap().get(mgsId)[0];
        boolean isGroupReady = getRequestStatusMap().get(mgsId)[1];
        System.out.println("----- isUserReady = " + isUserReady);
        System.out.println("----- isGroupReady = " + isGroupReady);
        if (isUserReady && isGroupReady) {
            // notify there is new message
            getNotifier().vibrateAndPlayTone(null);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_NOTIFY).putExtra(Constant.GROUP_NOTIFY_EXTRA, mgsId));
            getRequestStatusMap().clear();
        }

    }

    public ChatModel getModel() {
        return mChatMode;
    }

    /**
     * update contact list
     * mContactList
     *
     * @param
     */
    public void setContactList(Map<String, EaseUser> aContactList) {
        if (aContactList == null) {
            if (mContactList != null) {
                mContactList.clear();
            }
            return;
        }
        mContactList = aContactList;
    }

    public Map<String, EaseUser> getContactList(List<HxUserBean> userBeanList) {

        if (isLoggedIn() && mContactList == null) {
            mContactList = new HashMap<>();
            addUserBeanToMap(userBeanList);
        } else if (mContactList.size() != userBeanList.size()) {
            mContactList.clear();
            addUserBeanToMap(userBeanList);
        }

        return mContactList;
    }

    public Map<String, EaseUser> getTempContactList(List<HxUserBean> userBeanList) {

        if (isLoggedIn() && mTempContactList == null) {
            mTempContactList = new HashMap<>();
            addUserBeanToTempMap(userBeanList);
        } else if (mTempContactList.size() != userBeanList.size()) {
            mTempContactList.clear();
            addUserBeanToTempMap(userBeanList);
        }

        return mTempContactList;
    }

    private void addUserBeanToMap(List<HxUserBean> userBeanList) {
        for (HxUserBean bean : userBeanList) {
            EaseUser user = new EaseUser(bean.getHx_id());
            user.setAvatar(ApiClient.getHxFriendsImage(bean.getAvatar()));
            user.setNick(bean.getRealname());
            user.setNickname(bean.getRealname());
            user.setAddress(bean.getLive_cityname());
            user.setGrade(bean.getGrade());
            EaseCommonUtils.setUserInitialLetter(user);
            mContactList.put(bean.getHx_id(), user);
        }
    }

    private void addUserBeanToTempMap(List<HxUserBean> userBeanList) {
        for (HxUserBean bean : userBeanList) {
            EaseUser user = new EaseUser(bean.getHx_id());
            user.setAvatar(ApiClient.getHxFriendsImage(bean.getAvatar()));
            user.setNick(bean.getRealname());
            user.setNickname(bean.getRealname());
            user.setAddress(bean.getLive_cityname());
            user.setGrade(bean.getGrade());
            EaseCommonUtils.setUserInitialLetter(user);
            mTempContactList.put(bean.getHx_id(), user);
        }
    }

    private EaseGroup getGroupInfo(String groupId) {
        HxGroupBean bean = getHxGroupDao().getGroupInfo(groupId);
        EaseGroup emaGroup = new EaseGroup();
        if (bean != null) {
            emaGroup.setAvatar(ApiClient.getHxGroupImage(bean.getGroup_img()));
            emaGroup.setName(bean.getGroup_name());
            emaGroup.setGroupId(bean.getGroup_id());
        }
        return emaGroup;
    }

    private EaseUser getUserInfo(String username) {
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server

        List<HxUserBean> userBeanList = getHxUserDao().getUserDetailsList();
        EaseUser user = null;

        if (username.equals(LoginHelper.getInstance().getHxId()))
            return getUserProfileManager().getCurrentUserInfo();

        user = getContactList(userBeanList).get(username);

        // if user is not in your contacts, set inital letter for him/her
        if (user == null) {
            List<HxUserBean> tempUser = mHxUserDao.getTempUserDetailsList();
            user = getTempContactList(tempUser).get(username);
            if (user != null) {
                EaseCommonUtils.setUserInitialLetter(user);
            } else {
                user = new EaseUser(username);
                EaseCommonUtils.setUserInitialLetter(user);
            }
        }
        return user;
    }

    private void setEaseUIProviders() {
        // set profile provider if you want easeUI to handle avatar and nickname
        mEaseUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }

            @Override
            public void setUserAvatar(final TextView name, final ImageView avatar, String hxId, String flag) {
                List<HxUserBean> userBeanList = mHxUserDao.getTempUserDetailsList();
                EaseUser user = getTempContactList(userBeanList).get(hxId);
                if (user != null) {
                    if (user.getAvatar().contains("http://")) {
                        setHxAvatar(user.getAvatar(), avatar);
                    } else {
                        setHxAvatar(ApiClient.getHxFriendsImage(user.getAvatar()), avatar);
                    }
                } else {
                    if ("chatRow".equals(flag)) {
                        getHxTempUserInfo(hxId, avatar, name);
                    } else {
                        getTempUser(hxId, new EaseUserUtils.EMUserInfoCallback() {
                            @Override
                            public void onDataCallback(EaseUser user) {
                                if (user != null) {
                                    name.setText(user.getNick());
                                    if (user.getAvatar().contains("http://")) {
                                        setHxAvatar(user.getAvatar(), avatar);
//                                    Glide.with(mContext).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.ease_default_avatar).into(avatar);
                                    } else {
                                        setHxAvatar(ApiClient.getHxFriendsImage(user.getAvatar()), avatar);
//                                    Glide.with(mContext).load(ApiClient.getHxFriendsImage(user.getAvatar())).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.ease_default_avatar).into(avatar);
                                    }
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void setHxAvatar(String url, ImageView view) {
                Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).error(R.drawable.ease_default_avatar).into(view);
            }

            @Override
            public EaseGroup getGroup(String groupId) {
                return getGroupInfo(groupId);
            }

            @Override
            public void getTempUser(String username, final EaseUserUtils.EMUserInfoCallback callback) {
                ChatHelper.this.getTempUserInfo(username, callback);
            }

            @Override
            public void getTempUserList(String hxIds, final EaseUserUtils.EMUserListCallback callback) {
                TaskBase.getUserDetailList(hxIds, new TaskBase.UpdateCallBack<HxUserBean>() {
                    @Override
                    public void onSuccess(List<HxUserBean> userBeanList) {
                        List<EaseUser> userList = new ArrayList<>();
                        for (HxUserBean bean : userBeanList) {
                            EaseUser user = getTempContactList(userBeanList).get(bean.getHx_id());
                            userList.add(user);
                        }
                        callback.onUserListCallback(userList);
                    }

                    @Override
                    public void onError(String err) {

                    }
                });
            }

            @Override
            public String getFriendRealName(String hxName) {
                String realName = hxName;
                List<HxUserBean> beanList = getHxUserDao().getUserDetailsList();
                for (HxUserBean bean : beanList) {
                    if (bean.getHx_id().equals(hxName)) {
                        realName = bean.getRealname();
                    }
                }
                if (realName.equals(hxName)) {
                    List<HxUserBean> tempBeanList = getHxUserDao().getTempUserDetailsList();
                    for (HxUserBean bean : tempBeanList) {
                        if (bean.getHx_id().equals(hxName)) {
                            realName = bean.getRealname();
                        }
                    }
                }
                return realName;
            }
        });
        //options
        mEaseUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return mChatMode.getSettingMsgSpeaker();
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return mChatMode.getSettingMsgVibrate();
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return mChatMode.getSettingMsgSound();
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if (message == null) {
                    return mChatMode.getSettingMsgNotification();
                }
                if (!mChatMode.getSettingMsgNotification()) {
                    return false;
                } /*else {
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = mChatMode.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = mChatMode.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }*/
                return true;
            }
        });
        //emoji icon provider
        mEaseUI.setEmojiconInfoProvider(new EaseUI.EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
                for (EaseEmojicon emojicon : data.getEmojiconList()) {
                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                return null;
            }
        });

    }

    private void getTempUserInfo(String username, final EaseUserUtils.EMUserInfoCallback callback) {
        TaskBase.getUserDetails(username, new TaskBase.OnGetDataCallBack<HxUserBean>() {
            @Override
            public void onResult(HxUserBean userBean) {
                List<HxUserBean> beanList = new ArrayList<>();
                beanList.add(userBean);
                EaseUser user = getTempContactList(beanList).get(userBean.getHx_id());
                callback.onDataCallback(user);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    private EMOptions getOptions() {
        if (emOptions == null) {
            emOptions = initChatOptions();
        }
        return emOptions;
    }

    private Map<Integer, Boolean[]> getRequestStatusMap() {
        if (mRequestStatusMap == null)
            mRequestStatusMap = new HashMap<>();
        return mRequestStatusMap;
    }

    private UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }


    private EaseUI getEaseUI() {
        if (mEaseUI == null) {
            mEaseUI = EaseUI.getInstance();
        }
        return mEaseUI;
    }

    private HxUserDao getHxUserDao() {
        if (mHxUserDao == null) {
            mHxUserDao = new HxUserDao();
        }
        return mHxUserDao;
    }

    private HxGroupDao getHxGroupDao() {
        if (mHxGroupDao == null) {
            mHxGroupDao = new HxGroupDao();
        }
        return mHxGroupDao;
    }

    private HxNewContactNotifyDao getHxNewContactNotifyDao() {
        if (mNewContactNotifyDao == null)
            mNewContactNotifyDao = new HxNewContactNotifyDao();
        return mNewContactNotifyDao;
    }

    private void getHxTempUserInfo(String username, final ImageView imageView, final TextView textView) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).getMemberRealname(LoginHelper.getInstance().getUserToken(), username)
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            MemberBasicBean memBerBasicBean = null;
                            if (status.equals(ApiService.STATUS_SUC)) {
                                memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);
                                if (memBerBasicBean != null) {
                                    //虽然返回的是一个列表 因为我们查询的至是一个账号的id,所以直接取第0个数据就可以
                                    HxUserBean infoBean = memBerBasicBean.getInfo().get(0);
                                    if (infoBean != null) {
                                        realName = infoBean.getRealname();
                                        portrait = infoBean.getAvatar();
                                        if (portrait.contains("http://")) {
                                            Glide.with(mContext).load(portrait).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.account_logo).into(imageView);
                                        } else {
                                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(portrait)).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.account_logo).into(imageView);
                                        }
                                        if (!TextUtils.isEmpty(realName)) {
                                            textView.setText(realName);
                                        }
                                        HxUserBean mUserBean = new HxUserBean();
                                        mUserBean.setId(infoBean.getId());
                                        mUserBean.setRealname(infoBean.getRealname());
                                        mUserBean.setAvatar(infoBean.getAvatar());
                                        mUserBean.setPhone(infoBean.getPhone());
                                        mUserBean.setUid(infoBean.getUid());
                                        mUserBean.setLive_cityname(infoBean.getLive_cityname());
                                        mUserBean.setGrade(infoBean.getGrade());
                                        mUserBean.setHx_id(infoBean.getHx_id());
                                        HxUserDao mHxUserDao = new HxUserDao();
                                        mHxUserDao.saveUserDetails(mUserBean);

                                    }
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}

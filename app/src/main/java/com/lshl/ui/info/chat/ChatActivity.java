package com.lshl.ui.info.chat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.CheckBox;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.GroupListBean;
import com.lshl.databinding.ActivityChatBinding;
import com.lshl.ui.info.group.RequestJoinGroupActivity;
import com.lshl.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChatActivity extends BaseActivity<ActivityChatBinding> implements ChatFragment.TitleNameCallback {

    public static final int REQUEST_CODE_GROUP_DETAIL = 13;
    public static final int REQUEST_CODE_CONTACT_SETTING = 102;

    private LocalBroadcastManager mBroadcastManager;

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    private int chatType;
    private String toChatUsername;

    public CheckBox mRightView;

    /**
     * 启动ChatActivity的方式
     * Activity和Fragment只需要传递一个就行了，另外一个可以传递null
     *
     * @param activity
     * @param fragment
     * @param id          群或者是个人的id号
     * @param chatType    聊天的类型
     * @param requestCode 请求码
     */
    public static void actionStart(Activity activity, Fragment fragment, String id, int chatType, int requestCode) {
        Intent intent = new Intent();
        intent.putExtra(EaseConstant.EXTRA_USER_ID, id);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);

        if (activity != null) {
            intent.setClass(activity, ChatActivity.class);
            activity.startActivityForResult(intent, requestCode);
        } else if (fragment != null) {
            intent.setClass(fragment.getContext(), ChatActivity.class);
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        if (!EMClient.getInstance().isConnected()) {
            ChatHelper.getInstance().login(new EMCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
        super.onCreate(arg0);
    }

    @Override
    protected void initFieldBeforeMethods() {

        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        activityInstance = this;
        Bundle bundle = getIntent().getExtras();
        //获取聊天人的id
        toChatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID);
        //获取聊天类型
        chatType = bundle.getInt(EaseConstant.EXTRA_CHAT_TYPE);

        if (chatType == EaseConstant.CHATTYPE_GROUP) {

            EMClient.getInstance().groupManager().asyncGetGroupFromServer(toChatUsername, new EMValueCallBack<EMGroup>() {
                @Override
                public void onSuccess(EMGroup emGroup) {

                    if (emGroup == null || !emGroup.getMembers().contains(LoginHelper.getInstance().getHxId())) {
                        if (emGroup != null && emGroup.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Dialog dialog = DialogUtils.alertDialog(mContext, "温馨提示", "您已不是当前群聊的会员，是否申请进入", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                        finish();
                                    }
                                }, new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                                        RetrofitManager.toSubscribe(
                                                ApiClient.getApiService(ApiService.class,
                                                        RetrofitManager.RetrofitType.Object)
                                                        .lookupGroupList(toChatUsername, String.valueOf(0)),
                                                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<GroupListBean>() {
                                                    @Override
                                                    public void onNext(GroupListBean result) {
                                                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                                            sweetAlertDialog.dismissWithAnimation();
                                                            finish();
                                                            RequestJoinGroupActivity.actionStart(ChatActivity.this, result.getInfo().getList().get(0));
                                                        }
                                                    }
                                                })
                                        );
                                    }
                                });
                                dialog.setCancelable(false);
                            }
                        });
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Log.d("发生错误", s);
                }
            });


        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        CheckBox rightView = getRightView();
        mRightView = rightView;
        if (chatType == Constant.CHATTYPE_GROUP) {
            rightView.setButtonDrawable(R.drawable.ic_vector_chat_group_settring);

        } else if (chatType == Constant.CHATTYPE_SINGLE) {
            rightView.setButtonDrawable(R.mipmap.remove_icon);
        }
    }

    @Override
    protected void initViews() {
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatFragment.hideTitleBar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GROUP_DETAIL:
                    finish();
                    mBroadcastManager.sendBroadcast(new Intent().setAction(Constant.ACTION_PAGE_REFRESH));
                    break;
                case REQUEST_CODE_CONTACT_SETTING:
                    if (data != null) {
                        ChatSettingActivity.ResultType resultType = (ChatSettingActivity.ResultType) data.getSerializableExtra("result");
                        switch (resultType) {
                            case DELETE_USER:
                                setResult(RESULT_OK);
                                finish();
                                break;
                            case DELETE_HISTORY:
                                chatFragment.getChatMessageList().refresh();
                                break;
                            case ADD_BLACK_LIST:

                                break;
                        }
                    }

                    break;
            }
        }
    }

    @Override
    public void onCallback(String titleName) {
        setTextTitleView(titleName, DEFAULT_TITLE_TEXT_COLOR);
    }
}

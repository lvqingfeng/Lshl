package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lshl.ChatHelper;
import com.lshl.ChatModel;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityNewMessageBinding;
import com.lshl.utils.SharedPreferencesUtils;
import com.lshl.widget.SwitchView;

/**
 * 新消息通知
 */
public class NewMessageActivity extends BaseActivity<ActivityNewMessageBinding> {

    private boolean isOpenNewMessage;//是否开启接受新消息
    private boolean isOpenSound;//是否开启声音
    private boolean isOpenVibrate;//是否开启震动
    private boolean isOpenAaronLi;//是否开启勿扰模式
    private boolean isOpenRadar;//是否开启好友雷达
    private ChatModel mChatMode;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, NewMessageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("新消息通知", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mChatMode = ChatHelper.getInstance().getModel();
    }

    @Override
    protected void initViews() {
        isOpenRadar = (boolean) SharedPreferencesUtils.getParam(mContext, Constant.FRIEND_RADAR_KEY, false);
        isOpenNewMessage = mChatMode.getSettingMsgNotification();
        isOpenSound = mChatMode.getSettingMsgSound();
        isOpenVibrate = mChatMode.getSettingMsgVibrate();
        if (isOpenNewMessage || isOpenSound || isOpenVibrate) {
            isOpenAaronLi = false;
        } else {
            isOpenAaronLi = true;
        }
        mDataBinding.radarRequest.setOpened(isOpenRadar);
        mDataBinding.newMessage.setOpened(isOpenNewMessage);
        mDataBinding.sounds.setOpened(isOpenSound);
        mDataBinding.zhendong.setOpened(isOpenVibrate);
        mDataBinding.wurao.setOpened(isOpenAaronLi);

        //接收新消息
        mDataBinding.newMessage.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                isOpenNewMessage = true;
                mDataBinding.newMessage.toggleSwitch(isOpenNewMessage);
                mDataBinding.sounds.setOpened(isOpenSound);
                mDataBinding.zhendong.setOpened(isOpenVibrate);
                mChatMode.setSettingMsgNotification(isOpenNewMessage);
                isOpenAaronLi = false;
                mDataBinding.wurao.toggleSwitch(false);

            }

            @Override
            public void toggleToOff(View view) {
                isOpenNewMessage = false;
                mDataBinding.newMessage.toggleSwitch(isOpenNewMessage);
                mDataBinding.sounds.toggleSwitch(isOpenNewMessage);
                mDataBinding.zhendong.toggleSwitch(isOpenNewMessage);
                mChatMode.setSettingMsgNotification(isOpenNewMessage);
                isOpenAaronLi = true;
                mDataBinding.wurao.toggleSwitch(true);
            }
        });
        //声音
        mDataBinding.sounds.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                isOpenSound = true;
                mChatMode.setSettingMsgSound(isOpenSound);
                mDataBinding.sounds.toggleSwitch(isOpenSound);
                checkIsOpenAaronLi();
            }

            @Override
            public void toggleToOff(View view) {
                isOpenSound = false;
                mChatMode.setSettingMsgSound(isOpenSound);
                mDataBinding.sounds.toggleSwitch(isOpenSound);
                checkIsOpenAaronLi();
            }
        });
        //震动
        mDataBinding.zhendong.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                isOpenVibrate = true;
                mChatMode.setSettingMsgVibrate(true);
                mDataBinding.zhendong.toggleSwitch(isOpenVibrate);
                checkIsOpenAaronLi();
            }

            @Override
            public void toggleToOff(View view) {
                isOpenVibrate = false;
                mChatMode.setSettingMsgVibrate(false);
                mDataBinding.zhendong.toggleSwitch(isOpenVibrate);
                checkIsOpenAaronLi();
            }
        });
        //勿扰模式
        mDataBinding.wurao.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                setAaronLiStatus(true);
            }

            @Override
            public void toggleToOff(View view) {
                setAaronLiStatus(false);
            }
        });

        mDataBinding.radarRequest.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                SharedPreferencesUtils.setParam(mContext, Constant.FRIEND_RADAR_KEY, true);
                mDataBinding.radarRequest.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(View view) {
                SharedPreferencesUtils.setParam(mContext, Constant.FRIEND_RADAR_KEY, false);
                mDataBinding.radarRequest.toggleSwitch(false);
            }
        });
    }

    private void setAaronLiStatus(boolean flag) {
        isOpenAaronLi = flag;
        boolean currentStatus = !isOpenAaronLi;
        if (currentStatus) {
            mDataBinding.newMessage.toggleSwitch(isOpenNewMessage);
            mDataBinding.sounds.toggleSwitch(isOpenSound);
            mDataBinding.zhendong.toggleSwitch(isOpenVibrate);
            mChatMode.setSettingMsgSound(isOpenNewMessage);
            mChatMode.setSettingMsgNotification(isOpenSound);
            mChatMode.setSettingMsgVibrate(isOpenVibrate);
        } else {
            mDataBinding.newMessage.toggleSwitch(false);
            mDataBinding.sounds.toggleSwitch(false);
            mDataBinding.zhendong.toggleSwitch(false);
            mChatMode.setSettingMsgSound(false);
            mChatMode.setSettingMsgNotification(false);
            mChatMode.setSettingMsgVibrate(false);
        }

        mDataBinding.wurao.toggleSwitch(isOpenAaronLi);
    }

    /**
     * 判断是否开启勿扰模式
     */
    private void checkIsOpenAaronLi() {
        if (!isOpenNewMessage && !isOpenSound && !isOpenVibrate) {
            setAaronLiStatus(true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_message;
    }
}

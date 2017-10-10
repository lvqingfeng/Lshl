package com.lshl.ui.info.chat;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.databinding.ActivityMediaCallBinding;
import com.lshl.db.bean.HxUserBean;
import com.lshl.task.TaskBase;
import com.lshl.view.GlideCircleTransform;

import java.util.UUID;

/**
 * 多媒体语音聊天界面，包含语音聊天和视频聊天
 */
public class MediaCallActivity extends CallActivity {

    private ActivityMediaCallBinding mDataBinding;

    private HxUserBean mUserBean;
    private Constant.CallType mCallType;
    private String mHxId;

    private boolean isHandsfreeState = true;
    private boolean isMuteState = false;

    private boolean endCallTriggerByMe = false;
    private boolean isRecording = false;

    private EMCallManager.EMVideoCallHelper callHelper;

    private boolean isInCalling;
    private boolean monitor = true;


    public static Intent actionStart(Context activity, Constant.CallType type, boolean isComingCall, HxUserBean userBean, String hxId, boolean isStart) {
        Intent intent = new Intent(activity, MediaCallActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bean", userBean);
        intent.putExtra("isComingCall", isComingCall);
        intent.putExtra("hxId", hxId);
        if (isStart)
            activity.startActivity(intent);
        return intent;
    }


    public class Presenter {

        public void onClickRootView() {
            if (mCallType == Constant.CallType.VIDEO) {
                if (callingState == CallingState.NORMAL) {
                    if (mDataBinding.llControlBottom.getVisibility() == View.VISIBLE) {
                        mDataBinding.llControlBottom.setVisibility(View.GONE);
                        mDataBinding.llInfoTop.setVisibility(View.GONE);
                    } else {
                        mDataBinding.llControlBottom.setVisibility(View.VISIBLE);
                        mDataBinding.llInfoTop.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        public void onClickCallHangup(View view) {//
            view.setEnabled(false);
            mDataBinding.chronometer.stop();
            endCallTriggerByMe = true;
            mDataBinding.tvCallState.setText(getResources().getString(R.string.hanging_up));
            if (isRecording) {
                callHelper.stopVideoRecord();
            }
            handler.sendEmptyMessage(MSG_CALL_END);
        }

        public void onClickAnswerCall(View view) {//接听
            view.setEnabled(false);
            view.setVisibility(View.GONE);
            openSpeakerOn();
            if (ringtone != null)
                ringtone.stop();
            mDataBinding.tvCallState.setText("接听中...");
            handler.sendEmptyMessage(MSG_CALL_ANSWER);

            isAnswered = true;
            isHandsfreeState = true;
            if (mCallType == Constant.CallType.VIDEO) {
                localSurface.setVisibility(View.VISIBLE);
                mDataBinding.ivCameraSwitch.setVisibility(View.VISIBLE);
            }
            mDataBinding.llCallSetting.setVisibility(View.VISIBLE);

         /*   comingBtnContainer.setVisibility(View.INVISIBLE);
            hangupBtn.setVisibility(View.VISIBLE);
            voiceContronlLayout.setVisibility(View.VISIBLE);*/

        }


        public void onClickMute() {//点击静音
            if (isMuteState) {
                // resume voice transfer
                mDataBinding.ivSound.setImageResource(R.drawable.ic_vector_sound_open);
                try {
                    EMClient.getInstance().callManager().resumeVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = false;
            } else {
                // pause voice transfer
                mDataBinding.ivSound.setImageResource(R.drawable.ic_vector_sound_close);
                try {
                    EMClient.getInstance().callManager().pauseVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = true;
            }
        }

        public void onClickHandsfree() {//免提按钮
            if (isHandsfreeState) {
                // turn off speaker
                mDataBinding.ivHandsfree.setImageResource(R.drawable.ic_vector_handsfree_close);
                closeSpeakerOn();
                isHandsfreeState = false;
            } else {
                mDataBinding.ivHandsfree.setImageResource(R.drawable.ic_vector_handsfree_open);
                openSpeakerOn();
                isHandsfreeState = true;
            }
        }

        public void onClickCameraSwitch() {
            handler.sendEmptyMessage(MSG_CALL_SWITCH_CAMERA);
        }
    }

    private BrightnessDataProcess dataProcessor = new BrightnessDataProcess();

    // dynamic adjust brightness
    class BrightnessDataProcess implements EMCallManager.EMCameraDataProcessor {
        byte yDelta = 0;

        synchronized void setYDelta(byte yDelta) {
            Log.d("VideoCallActivity", "brigntness uDelta:" + yDelta);
            this.yDelta = yDelta;
        }

        // data size is width*height*2
        // the first width*height is Y, second part is UV
        // the storage layout detailed please refer 2.x demo CameraHelper.onPreviewFrame
        @Override
        public void onProcessData(byte[] bytes, Camera camera, int width, int height, int i2) {
            int wh = width * height;
            for (int i = 0; i < wh; i++) {
                int d = (bytes[i] & 0xFF) + yDelta;
                d = d < 16 ? 16 : d;
                d = d > 235 ? 235 : d;
                bytes[i] = (byte) d;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_media_call);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        localSurface = mDataBinding.localSurface;//前置摄像头
        localSurface.setZOrderMediaOverlay(true);
        localSurface.setZOrderOnTop(true);

        oppositeSurface = mDataBinding.oppositeSurface;//大摄像头

        msgid = UUID.randomUUID().toString();


        initField();
        initUserInfo();
        addCallStateListener();

        username = mHxId;
        if (!isInComingCall) {// outgoing call
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.em_outgoing, 1);

        /*    comingBtnContainer.setVisibility(View.INVISIBLE);
            hangupBtn.setVisibility(View.VISIBLE);*/
            String st = getResources().getString(R.string.Are_connected_to_each_other);

            mDataBinding.tvCallState.setText(st);

            if (mCallType == Constant.CallType.VIDEO) {
                mDataBinding.ivCameraSwitch.setVisibility(View.VISIBLE);
                localSurface.setVisibility(View.VISIBLE);
                EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
                handler.sendEmptyMessage(MSG_CALL_MAKE_VIDEO);
            } else {
                handler.sendEmptyMessage(MSG_CALL_MAKE_VOICE);
            }
        } else { // incoming call
            // voiceContronlLayout.setVisibility(View.INVISIBLE);
            if (mCallType == Constant.CallType.VIDEO) {
                localSurface.setVisibility(View.INVISIBLE);
                EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
            }
            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();

        }

        // get instance of call helper, should be called after setSurfaceView was called
        callHelper = EMClient.getInstance().callManager().getVideoCallHelper();


        EMClient.getInstance().callManager().setCameraDataProcessor(dataProcessor);
        dataProcessor.setYDelta((byte) (20.0f * (100 - 50) / 50.0f));

        mDataBinding.setPresenter(new Presenter());
    }

    private void addCallStateListener() {
        callStateListener = new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, final CallError callError) {
                EMLog.d("EMCallManager", "onCallStateChanged:" + callState);
                switch (callState) {

                    case CONNECTING://链接成功的回调
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mDataBinding.tvCallState.setText("正在链接中....");
                            }
                        });
                        break;
                    case CONNECTED://已链接成功，等待对方回应
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                String st3 = getResources().getString(R.string.have_connected_with);
                                mDataBinding.tvCallState.setText(st3);
                            }
                        });
                        break;

                    case ACCEPTED://已建立链接，对方已接受
                        handler.removeCallbacks(timeoutHangup);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if (soundPool != null)
                                        soundPool.stop(streamID);
                                } catch (Exception e) {
                                }
                                if (mCallType == Constant.CallType.VIDEO) {
                                    mDataBinding.inComingCall.setVisibility(View.GONE);
                                    openSpeakerOn();
                                    //startMonitor();
                                } else if (mCallType == Constant.CallType.VOICE) {
                                    if (!isHandsfreeState)
                                        closeSpeakerOn();
                                }
                                //show relay or direct call, for testing purpose
                                ((TextView) findViewById(R.id.tv_is_p2p)).setText(EMClient.getInstance().callManager().isDirectCall()
                                        ? R.string.direct_call : R.string.relay_call);

                                isHandsfreeState = true;
                                isInCalling = true;
                                mDataBinding.chronometer.setVisibility(View.VISIBLE);
                                mDataBinding.chronometer.setBase(SystemClock.elapsedRealtime());
                                // duration start
                                mDataBinding.chronometer.start();
                                String str4 = getResources().getString(R.string.In_the_call);
                                mDataBinding.tvCallState.setText(str4);
                                callingState = CallingState.NORMAL;

                            }
                        });
                        break;
                    case NETWORK_UNSTABLE://网络不稳定
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (callError == CallError.ERROR_NO_DATA) {
                                    mDataBinding.tvNetworkStatus.setText(R.string.no_call_data);
                                } else {
                                    mDataBinding.tvNetworkStatus.setText(R.string.network_unstable);
                                }
                            }
                        });
                        break;
                    case NETWORK_NORMAL://网络正常
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mDataBinding.tvNetworkStatus.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;
                    case VOICE_PAUSE://语音暂停
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "对方已关闭语音", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case VOICE_RESUME://语音恢复
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "对方已开启语音", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case DISCONNECTED://断开挂断
                        handler.removeCallbacks(timeoutHangup);
                        @SuppressWarnings("UnnecessaryLocalVariable") final CallError fError = callError;
                        runOnUiThread(new Runnable() {
                            private void postDelayedCloseMsg() {
                                handler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("AAA", "CALL DISCONNETED");
                                                saveCallRecord();
                                                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                                                animation.setDuration(800);
                                                findViewById(R.id.root_layout).startAnimation(animation);
                                                finish();
                                            }
                                        });
                                    }
                                }, 200);
                            }

                            @Override
                            public void run() {
                                mDataBinding.chronometer.stop();
                                callDruationText = mDataBinding.chronometer.getText().toString();
                                String st2 = getResources().getString(R.string.The_other_party_refused_to_accept);
                                String st3 = getResources().getString(R.string.Connection_failure);
                                String st4 = getResources().getString(R.string.The_other_party_is_not_online);
                                String st5 = getResources().getString(R.string.The_other_is_on_the_phone_please);

                                String st6 = getResources().getString(R.string.The_other_party_did_not_answer_new);
                                String st7 = getResources().getString(R.string.hang_up);
                                String st8 = getResources().getString(R.string.The_other_is_hang_up);

                                String st9 = getResources().getString(R.string.did_not_answer);
                                String st10 = getResources().getString(R.string.Has_been_cancelled);
                                String st11 = getResources().getString(R.string.hang_up);

                                if (fError == CallError.REJECTED) {
                                    callingState = CallingState.BEREFUESD;
                                    mDataBinding.tvCallState.setText(st2);
                                } else if (fError == CallError.ERROR_TRANSPORT) {
                                    mDataBinding.tvCallState.setText(st3);
                                } else if (fError == CallError.ERROR_UNAVAILABLE) {
                                    callingState = CallingState.OFFLINE;
                                    mDataBinding.tvCallState.setText(st4);
                                } else if (fError == CallError.ERROR_BUSY) {
                                    callingState = CallingState.BUSY;
                                    mDataBinding.tvCallState.setText(st5);
                                } else if (fError == CallError.ERROR_NORESPONSE) {
                                    callingState = CallingState.NORESPONSE;
                                    mDataBinding.tvCallState.setText(st6);
                                } else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                                    callingState = CallingState.VERSION_NOT_SAME;
                                    mDataBinding.tvCallState.setText(R.string.call_version_inconsistent);
                                } else {
                                    if (isAnswered) {
                                        callingState = CallingState.NORMAL;
                                        if (endCallTriggerByMe) {
//                                        callStateTextView.setText(st7);
                                        } else {
                                            mDataBinding.tvCallState.setText(st8);
                                        }
                                    } else {
                                        if (isInComingCall) {
                                            callingState = CallingState.UNANSWERED;
                                            mDataBinding.tvCallState.setText(st9);
                                        } else {
                                            if (callingState != CallingState.NORMAL) {
                                                callingState = CallingState.CANCED;
                                                mDataBinding.tvCallState.setText(st10);
                                            } else {
                                                mDataBinding.tvCallState.setText(st11);
                                            }
                                        }
                                    }
                                }
                                postDelayedCloseMsg();
                            }

                        });

                        break;

                    default:
                        break;
                }
            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener);
    }

    private void initField() {
        Intent intent = getIntent();
        mCallType = (Constant.CallType) intent.getSerializableExtra("type");
        isInComingCall = intent.getBooleanExtra("isComingCall", false);
        mHxId = intent.getStringExtra("hxId");
        if (!isInComingCall) {
            mUserBean = ((HxUserBean) intent.getSerializableExtra("bean"));
            mDataBinding.btnAnswer.setVisibility(View.GONE);
        } else {
            mDataBinding.llCallSetting.setVisibility(View.GONE);
        }


    }

    private void initUserInfo() {
        if (isInComingCall || mUserBean == null) {
            TaskBase.getUserDetails(LoginHelper.getInstance().getUserToken(), mHxId, new TaskBase.OnGetDataCallBack<HxUserBean>() {
                @Override
                public void onResult(HxUserBean userBean) {
                    if (userBean != null) {
                        mUserBean = userBean;
                        setupUserInfo();
                    }
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(MediaCallActivity.this, "错误信息：" + err, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            setupUserInfo();
        }
    }

    /**
     * for debug & testing, you can remove this when release
     */
    void startMonitor() {
        new Thread(new Runnable() {
            public void run() {
                while (monitor) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mDataBinding.tvCallMonitor.setText("WidthxHeight：" + callHelper.getVideoWidth() + "x" + callHelper.getVideoHeight()
                                    + "\nDelay：" + callHelper.getVideoLatency()
                                    + "\nFramerate：" + callHelper.getVideoFrameRate()
                                    + "\nLost：" + callHelper.getVideoLostRate()
                                    + "\nLocalBitrate：" + callHelper.getLocalBitrate()
                                    + "\nRemoteBitrate：" + callHelper.getRemoteBitrate());
                        }
                    });
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private void setupUserInfo() {
        Glide.with(MediaCallActivity.this).load(ApiClient.getHxFriendsImage(mUserBean.getAvatar()))
                .error(R.drawable.ease_default_avatar)
                .transform(new GlideCircleTransform(MediaCallActivity.this))
                .into(mDataBinding.ivAvatar);
        mDataBinding.tvToChatName.setText(mUserBean.getRealname());
    }

    void stopMonitor() {
        monitor = false;
    }

    @Override
    protected void onDestroy() {
        ChatHelper.getInstance().isVoiceCalling = false;
        stopMonitor();
        if (isRecording) {
            callHelper.stopVideoRecord();
            isRecording = false;
        }
        localSurface = null;
        oppositeSurface = null;
        super.onDestroy();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().pauseVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().resumeVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        callDruationText = mDataBinding.chronometer.getText().toString();
    }
}

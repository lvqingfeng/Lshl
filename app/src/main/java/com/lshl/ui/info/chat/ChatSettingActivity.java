package com.lshl.ui.info.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityContactSettingBinding;
import com.lshl.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChatSettingActivity extends BaseActivity<ActivityContactSettingBinding> {

    private static final String HX_ID = "hx_id";

    private String mUserId;

    private boolean isAddBlackList;

    public static void actionStart(Activity activity, String hxId, int requestCode) {

        Intent intent = new Intent(activity, ChatSettingActivity.class);
        intent.putExtra(HX_ID, hxId);
        activity.startActivityForResult(intent, requestCode);

    }

    public enum ResultType {
        DELETE_USER, DELETE_HISTORY, ADD_BLACK_LIST
    }

    public class Presenter {
        //删除联系人
        public void deleteContact() {
            DialogUtils.alertDialog(mContext, "温馨提示", "确认要删除该好友", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    sweetAlertDialog.dismissWithAnimation();

                    DialogUtils.showProgressDialog(mContext, "正在删除..", null, null);
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().deleteContact(mUserId);
                                setResult(RESULT_OK, new Intent().putExtra("result", ResultType.DELETE_USER));
                                finish();
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage(e.getMessage());
                                    }
                                });
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogUtils.hideDialog(null);
                                }
                            });
                        }
                    }.start();

                }
            });
        }

        /**
         * clear the conversation history
         */
        public void deleteHistory() {
            String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
            new EaseAlertDialog(mContext, null, msg, null, new EaseAlertDialog.AlertDialogUser() {

                @Override
                public void onResult(boolean confirmed, Bundle bundle) {
                    if (confirmed) {
                        EMClient.getInstance().chatManager().deleteConversation(mUserId, true);
                        setResult(RESULT_OK, new Intent().putExtra("result", ResultType.DELETE_HISTORY));
                        finish();
                    }
                }
            }, true).show();
        }

        public void addBlackList() {
            DialogUtils.alertDialog(mContext, "温馨提示", "您是否要将好友加入黑名单", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    //第二个参数如果为true，则把用户加入到黑名单后双方发消息时对方都收不到；false，则我能给黑名单的中用户发消息，但是对方发给我时我是收不到的
                    new Thread() {
                        @Override
                        public void run() {
                            EMClient.getInstance().contactManager().aysncAddUserToBlackList(mUserId, true, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showMessage("加入黑名单成功");
                                            isAddBlackList = true;
                                        }
                                    });
                                }

                                @Override
                                public void onError(int i, final String s) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showMessage("加入黑名单异常：" + s);
                                        }
                                    });
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        }
                    }.start();
                }
            });
        }
    }

    @Override
    public void finish() {
        if (isAddBlackList) {
            setResult(RESULT_OK, new Intent().putExtra("result", ResultType.ADD_BLACK_LIST));
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mUserId = getIntent().getStringExtra(HX_ID);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("好友设置", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_setting;
    }


}

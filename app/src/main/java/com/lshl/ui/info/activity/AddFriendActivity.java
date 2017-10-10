package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityAddFriendBinding;

public class AddFriendActivity extends BaseActivity<ActivityAddFriendBinding> {
    private String reason="";
    private String id;
    public static void actionStart(Activity activity, String id, int resultCode) {
        Intent intent = new Intent(activity, AddFriendActivity.class);
        intent.putExtra("id",id);
        activity.startActivityForResult(intent,resultCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("好友认证",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id=getIntent().getStringExtra("id");

        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.reasonss.getText())){
                    reason=mDataBinding.reasonss.getText().toString().trim();
                }
                new Thread() {
                    @Override
                    public void run() {
                        //参数为要添加的好友的username和添加理由
                        try {
                            EMClient.getInstance().contactManager().addContact(id, reason);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "请求发送完成", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });
                        } catch (final HyphenateException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage("操作异常：" + e.getMessage());
                                    finish();
                                }
                            });
                        }

                    }
                }.start();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }
}

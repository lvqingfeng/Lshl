package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivityFeedBackSuggestBinding;
import com.lshl.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FeedBackSuggestActivity extends BaseActivity<ActivityFeedBackSuggestBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, FeedBackSuggestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("意见建议", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = mDataBinding.info.getText().toString().trim();
                if (!TextUtils.isEmpty(info)) {
                    info = mDataBinding.info.getText().toString();
                    final String finalInfo = info;
                    DialogUtils.alertDialog(mContext, "温馨提示", "您确定要提交以上内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            updateSuggest(finalInfo);
                        }
                    });

                } else {
                    showMessage("请输入您要反馈的内容");
                }
            }


        });
    }

    private void updateSuggest(String info) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).doSuggest(LoginHelper.getInstance().getUserToken(), info), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back_suggest;
    }
}

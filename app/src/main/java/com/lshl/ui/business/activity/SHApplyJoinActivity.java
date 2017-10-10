package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.lshl.databinding.ActivityShapplyJoinBinding;
import com.lshl.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SHApplyJoinActivity extends BaseActivity<ActivityShapplyJoinBinding> {

    private String mBuid;

    public static void actionStart(Activity activity, String id) {
        Intent intent = new Intent(activity, SHApplyJoinActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickAppJoin() {
            final String info = mDataBinding.editContent.getText().toString().trim();
            if (!TextUtils.isEmpty(info)) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否确认提交以上信息到商会",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
//                                String info = mDataBinding.editContent.getText().toString().trim();
                                applyJoin(info);
                            }
                        });
            } else {
                Toast.makeText(mContext, "入会说明不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void applyJoin(String info) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).applyJoinBusiness(LoginHelper.getInstance().getUserToken(), mBuid, info)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 200);
                    }
                })
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("申请加入商会", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mBuid = getIntent().getStringExtra("id");
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
//        mDataBinding.setJoinName(LoginHelper.getInstance().getUserBean().getRealName());
//        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnSubmit, mDataBinding.editContent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shapply_join;
    }
}

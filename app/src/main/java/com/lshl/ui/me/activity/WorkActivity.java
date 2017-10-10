package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.WorkBean;
import com.lshl.databinding.ActivityWorkBinding;
import com.lshl.databinding.WorkPopWindowBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.view.AddPopWindow;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class WorkActivity extends BaseActivity<ActivityWorkBinding> {
    private String rd;
    private String jobtype;
    private String jobname;
    private String jobpost;
    private String jobaddress;
    private String business_scope;
    private AddPopWindow popWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WorkActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("单位信息", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.llFocus.setEnabled(true);
        mDataBinding.llFocus.setFocusable(true);
        initPopuWondow();
        commitWorkMessage();
        mDataBinding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
                lightOff();
            }
        });
        mDataBinding.tvTitle.setText("温馨提示" + "\n" + "请上传完整的个人信息,我们平台坚决保护个人隐私");
        loadData();
    }

    private void initPopuWondow() {
        popWindow = new AddPopWindow(this, R.layout.item_layout_work_popuwindow);
        final WorkPopWindowBinding bind = DataBindingUtil.bind(popWindow.getWindowRootView());
        bind.itemWorkFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkFirst.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkTwo.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkThree.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkFour.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    private void loadData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).workMessage(LoginHelper.getInstance().getUserToken(), null), new ProgressSubscriber<WorkBean>(mContext, new SubscriberOnNextListener<WorkBean>() {
            @Override
            public void onNext(WorkBean result) {
                if (result != null) {
                    mDataBinding.jobname.setText(result.getInfo().getWorkunit());
                    mDataBinding.jobpost.setText(result.getInfo().getWorkpost());
                    mDataBinding.jobaddress.setText(result.getInfo().getWorkaddress());
                    mDataBinding.businessScope.setText(result.getInfo().getWorkscope());
                    mDataBinding.type.setText(result.getInfo().getWorknature());
                    if (result.getInfo().getIs_gudong().equals("1")) {
                        mDataBinding.rd.setText("是");
                    }
                    mDataBinding.rd.setText("否");
                } else {
                    DialogUtils.alertDialog(mContext, "单位信息不完整", "是否进入编辑", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            mDataBinding.btnCommit.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }));
    }

    private void commitWorkMessage() {
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.btnCommit.setVisibility(View.VISIBLE);
                mDataBinding.llFocus.setEnabled(false);
                mDataBinding.llFocus.setFocusable(false);
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(mDataBinding.jobname.getText())) {
                            jobname = mDataBinding.jobname.getText().toString();
                        } else {
                            showMessage("请输入您的工作单位");
                            return;
                        }
                        if (!TextUtils.isEmpty(mDataBinding.jobpost.getText())) {
                            jobpost = mDataBinding.jobpost.getText().toString();
                        } else {
                            showMessage("请输入您的工作岗位");
                            return;
                        }
                        if (!TextUtils.isEmpty(mDataBinding.type.getText())) {
                            jobtype = mDataBinding.type.getText().toString();
                        } else {
                            showMessage("请输入您的工作性质");
                            return;
                        }
                        if (!TextUtils.isEmpty(mDataBinding.jobaddress.getText())) {
                            jobaddress = mDataBinding.jobaddress.getText().toString();
                        } else {
                            showMessage("请输入您的工作地址");
                            return;
                        }
                        if (!TextUtils.isEmpty(mDataBinding.businessScope.getText())) {
                            business_scope = mDataBinding.businessScope.getText().toString();
                        } else {
                            showMessage("请输入您的经营范围");
                            return;
                        }
                        if (!TextUtils.isEmpty(mDataBinding.rd.getText())) {
                            rd = mDataBinding.rd.getText().toString();
                        } else {
                            showMessage("请输入您的是否股东");
                            return;
                        }
                        updaateWorkMessage(rd, jobtype, jobname, jobpost, jobaddress, business_scope);
                    }
                });
            }
        });
    }

    private void updaateWorkMessage(String rd, String jobtype, String jobname, String jobpost, String jobaddress, String business_scope) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String).workMessageCommit(LoginHelper.getInstance().getUserToken(), rd, jobtype, jobname, jobpost, jobaddress, business_scope), new ProgressSubscriber<ResponseBody>(mContext, new SubscriberOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody result) {
                if (result != null) {
                    try {
                        String string = result.string();
                        JSONObject object = JSON.parseObject(string);
                        String status = object.getString("status");
                        String message = object.getString("message");
                        if (status.equals(ApiService.STATUS_SUC)) {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work;
    }
}

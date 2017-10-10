package com.lshl.ui.appliance.job;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.RecruitDetailBean;
import com.lshl.databinding.ActivityRecruitDetailsBinding;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.ui.me.activity.EnterPriseDetailsActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

public class RecruitDetailsActivity extends BaseActivity<ActivityRecruitDetailsBinding> {
    private String erid;
    private String er_phone;
    private String hx_id;
    private String er_enid;
    public final static int FROM_ME = 0x000123;
    public final static int FROM_OTHERS = 0x000124;
    private int fromWhere;

    public static void actionStart(Activity activity, String erid, int fromWhere) {
        Intent intent = new Intent(activity, RecruitDetailsActivity.class);
        intent.putExtra("erid", erid);
        intent.putExtra("fromWhere", fromWhere);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("招聘详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        erid = getIntent().getStringExtra("erid");
        fromWhere = getIntent().getIntExtra("fromWhere", -1);
        if (fromWhere == FROM_ME) {
            mDataBinding.llContact.setVisibility(View.GONE);
        }
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateRecruitDetail(erid), new ProgressSubscriber<RecruitDetailBean>(mContext, new SubscriberOnNextListener<RecruitDetailBean>() {

            @Override
            public void onNext(RecruitDetailBean result) {
                if (result.getStatus() == 1) {
                    RecruitDetailBean.InfoBean bean = result.getInfo().get(0);
                    er_phone = bean.getEr_phone();
                    hx_id = bean.getHx_id();
                    er_enid = bean.getEr_enid();
                    mDataBinding.title.setText(bean.getEr_title());
                    mDataBinding.money.setText(bean.getEr_min_money() + "k-" + bean.getEr_max_money() + "k/月");
                    mDataBinding.companyName.setText(bean.getEn_name());
                    mDataBinding.address.setText(bean.getEr_cityname());
                    mDataBinding.demand.setText(bean.getEr_demand());
                    mDataBinding.info.setText(bean.getEr_info());
                    mDataBinding.time.setText("发布时间:  " + DateUtils.getDateToString2(Long.decode(bean.getEr_addtime()) * 1000));
                    mDataBinding.numLook.setText("浏览量:  " + bean.getEr_nums());
                    if (LoginHelper.getInstance().getUserBean().getUid().equals(bean.getEr_uid())) {
                        mDataBinding.llContact.setVisibility(View.GONE);
                    }
                }
            }
        }));
        mDataBinding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    DialogUtils.alertDialog(mContext, "温馨提示", "您是否要拨打" + er_phone + "号码",
                                            new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            }, new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    //用intent启动拨打电话
                                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + er_phone));
                                                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        // TODO: Consider calling
                                                        //    ActivityCompat#requestPermissions
                                                        // here to request the missing permissions, and then overriding
                                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                        //                                          int[] grantResults)
                                                        // to handle the case where the user grants the permission. See the documentation
                                                        // for ActivityCompat#requestPermissions for more details.
                                                        return;
                                                    }
                                                    startActivity(intent);
                                                }
                                            });
                                } else {
                                    showMessage("拨号权限被禁用");
                                }
                            }
                        });
            }
        });
        mDataBinding.talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        ChatActivity.actionStart(RecruitDetailsActivity.this, null, hx_id, EaseConstant.CHATTYPE_SINGLE, 0);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        });
        mDataBinding.companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterPriseDetailsActivity.actionStart(RecruitDetailsActivity.this, er_enid);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recruit_details;
    }
}

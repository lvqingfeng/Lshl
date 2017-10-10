package com.lshl.ui.appliance.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.StationDetailsBean;
import com.lshl.databinding.ActivityServiceStationBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.StationFunctionAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.GlideCircleTransform;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

public class ServiceStationActivity extends BaseActivity<ActivityServiceStationBinding> {
    private String title;
    private String sid;
    private List<StationDetailsBean.InfoBean.ServiceBean> mList;
    private StationFunctionAdapter stationFunctionAdapter;

    public static void actionStart(Activity activity, String title, String sid) {
        Intent intent = new Intent(activity, ServiceStationActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("sid", sid);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void callToStation() {//打电话
            RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要拨打" + mDataBinding.phone.getText().toString().substring(3) + "号码",
                                        new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        }, new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mDataBinding.phone.getText().toString().substring(3)));
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

        public void statement() {//服务申明
            ServiceStatementActivity.actionStart(ServiceStationActivity.this, sid);
        }

        public void introduce() {//服务介绍
            ServiceIntroduceActivity.actionStart(ServiceStationActivity.this, sid);
        }

        public void complaint() {//在线投诉
            ServiceComplaintActivity.actionStart(ServiceStationActivity.this, sid);
        }

        public void opinion() {//意见建议
            OpinionActivity.actionStart(ServiceStationActivity.this, sid);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        title = getIntent().getStringExtra("title");
        openTitleLeftView(true);
        setTextTitleView(title, DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        sid = getIntent().getStringExtra("sid");
        GridLayoutManager manager = new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        stationFunctionAdapter = new StationFunctionAdapter(mList);
        mDataBinding.recyclerView.setAdapter(stationFunctionAdapter);
        loadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String si_id = mList.get(vh.getLayoutPosition()).getSi_id();
                String title = mList.get(vh.getLayoutPosition()).getSi_title();
                StationFuctionDetailsActivity.actionStart(ServiceStationActivity.this, title, si_id);
            }
        });
    }

    private void loadData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateStationFunction(sid), new ProgressSubscriber<StationDetailsBean>(mContext, new SubscriberOnNextListener<StationDetailsBean>() {
            @Override
            public void onNext(StationDetailsBean result) {
                if (result != null) {
                    mDataBinding.name.setText(result.getInfo().getRealname());
                    mDataBinding.phone.setText("电话:" + result.getInfo().getSs_phone());
                    mDataBinding.address.setText("地址:" + result.getInfo().getSs_address());
                    Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).transform(new GlideCircleTransform(mContext)).into(mDataBinding.headImage);
                    mList.addAll(result.getInfo().getService());
                    stationFunctionAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_station;
    }
}

package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.ShanghuiBannerBean;
import com.lshl.databinding.ActivityShangHuiDetailsBinding;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 商会基本信息
 */
public class ShangHuiDetailsActivity extends BaseActivity<ActivityShangHuiDetailsBinding> {

    private String mBuid;
    private List<String> mImageList;
    private String mTitle;
    private boolean isJoin;

    public static void actionStart(Activity activity, Fragment fragment, String buid, String title, boolean isJoin, int requestCode) {
        Intent intent = new Intent();
        intent.putExtra("buid", buid);
        intent.putExtra("title", title);
        intent.putExtra("isJoin", isJoin);
        if (activity == null) {
            intent.setClass(fragment.getContext(), ShangHuiDetailsActivity.class);
            fragment.startActivityForResult(intent, requestCode);
        } else {
            intent.setClass(activity, ShangHuiDetailsActivity.class);
            activity.startActivity(intent);
        }
    }

    public class Presenter {
        public void onClickInfo() {
            CommerceIntroductionActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickDynamic() {
            SHDynamListActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickMember() {
            CommerceMemberActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickConsultation() {//咨询商会
            SHTalkAboutActivity.actionStart(ShangHuiDetailsActivity.this, mTitle, mBuid);
        }

        public void onClickContact() {//联系我们
            ShanghuiAddressActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickJoin() {
            SHApplyJoinActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickMembership() {//缴纳会费
            MembershipActivity.actionStart(ShangHuiDetailsActivity.this, mBuid);
        }

        public void onClickExit() {
            DialogUtils.alertDialog(mContext, "温馨提示", "您是否要退出该商会",
                    new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(
                                    ApiClient.getApiService(
                                            ApiService.class, RetrofitManager.RetrofitType.Object
                                    ).editMyBusiness(LoginHelper.getInstance().getUserToken(), mBuid)
                                    , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                        @Override
                                        public void onNext(HttpResult<String> result) {
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    })
                            );
                        }
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView(mTitle, DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {

        Intent intent = getIntent();
        mBuid = intent.getStringExtra("buid");
        mTitle = intent.getStringExtra("title");
        isJoin = intent.getBooleanExtra("isJoin", false);

        mImageList = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        if (isJoin) {
            mDataBinding.tvJoin.setVisibility(View.GONE);
            mDataBinding.tvExit.setVisibility(View.VISIBLE);
        }
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).updateShanghuiBanner(mBuid),
                new Subscriber<ShanghuiBannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShanghuiBannerBean shanghuiBannerBean) {
                        final List<ShanghuiBannerBean.InfoBean> info = shanghuiBannerBean.getInfo();
                        if (info.size() > 0) {
                            for (int i = 0; i < info.size(); i++) {
                                mImageList.add(ApiClient.getShanghuiBannerImage(info.get(i).getBub_img()));
                            }
                            setupBanner();
                            mDataBinding.cbBanner.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    /****
                                     * url其实是一个ID
                                     * */
                                    String bub_url = info.get(position).getBub_url();
                                    if (!bub_url.equals("0")) {
                                        SHDynamDetailsicActivity.actionStart(ShangHuiDetailsActivity.this, bub_url);
                                    }
                                }
                            });
                        } else {
                            mDataBinding.cbBanner.setBackgroundResource(R.mipmap.account_logo);
                        }
                    }
                });

        mDataBinding.setPresenter(new Presenter());
    }

    private void setupBanner() {
        mDataBinding.cbBanner.startTurning(3000);
        mDataBinding.cbBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {
            @Override
            public LoadImageHolder createHolder() {
                return new LoadImageHolder();
            }
        }, mImageList).setPageIndicator(new int[]{R.drawable.bg_banner_unselect, R.drawable.bg_banner_select});

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shang_hui_details;
    }


}

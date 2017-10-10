package com.lshl.ui.appliance.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.RecruitDetailsBean;
import com.lshl.databinding.ActivityPositionDetailsBinding;
import com.lshl.ui.me.activity.EnterPriseDetailsActivity;

public class PositionDetailsActivity extends BaseActivity<ActivityPositionDetailsBinding> {
    private String erid;
    private String er_enid;
    private boolean isCollection = false;
    private boolean isCommit = true;

    public static void actionStart(Activity activity, String erid) {
        Intent intent = new Intent(activity, PositionDetailsActivity.class);
        intent.putExtra("erid", erid);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void enterpriseToDetails() {//查看企业详情
            EnterPriseDetailsActivity.actionStart(PositionDetailsActivity.this, er_enid);
        }

        public void btnCommit() {//提交简历
            if (isCommit) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                .deliverResume(LoginHelper.getInstance().getUserToken(), erid)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    mDataBinding.commit.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightGray));
                                    isCommit = false;
                                } else {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
            } else {
                showMessage("您已成功投递,无需重复投递");
            }
        }

        public void ivCollection() {//收藏职位
            if (isCollection) {
                collectionJob("2");
                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_uncollection);
            } else {
                collectionJob("1");
                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_collection);
            }
        }

    }

    private void collectionJob(String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).followRecruit(LoginHelper.getInstance().getUserToken(), erid, status),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            isCollection = !isCollection;
                        } else {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("职位详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        erid = getIntent().getStringExtra("erid");
        loadBaseData();
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateRecruitDetails(erid, LoginHelper.getInstance().getUserToken())
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<RecruitDetailsBean>() {

                    @Override
                    public void onNext(RecruitDetailsBean result) {
                        if (result != null) {
                            er_enid = result.getInfo().getEr_enid();
                            mDataBinding.name.setText(result.getInfo().getEr_title());
                            mDataBinding.address.setText(result.getInfo().getEn_address());
                            mDataBinding.money.setText(result.getInfo().getEr_money());
                            mDataBinding.education.setText(result.getInfo().getEr_education());
                            mDataBinding.introduce.setText(result.getInfo().getEr_info());
                            mDataBinding.peopleNum.setText(result.getInfo().getEn_address());
                            mDataBinding.companyName.setText(result.getInfo().getEn_name());
                            Glide.with(mContext).load(ApiClient.getEnterPriseImage(result.getInfo().getImg()))
                                    .error(R.mipmap.account_logo).into(mDataBinding.companyLogo);
                            if (result.getInfo().getCollection() == 1) {
                                isCollection = true;
                                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_collection);
                            }
                            if (result.getInfo().getDelivery() == 1) {
                                isCommit = true;
                            } else {
                                isCommit = false;
                                mDataBinding.commit.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lightGray));
                            }
                        }
                    }
                }));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_position_details;
    }
}

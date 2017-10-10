package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.ActivityMemberCenterBinding;
import com.lshl.utils.CacheActivity;

public class MemberCenterActivity extends BaseActivity<ActivityMemberCenterBinding> {

    private String mBuyNum = "1";
    private static final double mVipPrice = 120.00;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MemberCenterActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        //购买会员
        public void purchase() {
            MemberRuleActivity.actionStart(MemberCenterActivity.this, String.valueOf(Integer.decode(mBuyNum) * mVipPrice));
        }

        //增加
        public void increase(TextView textView) {
            changeVipNum(textView, true);
        }

        //减少
        public void subtract(TextView textView) {
            changeVipNum(textView, false);
        }


        public void privilege() {
            PrivilegeActivity.actionStart(MemberCenterActivity.this);
        }
    }

    private void changeVipNum(TextView textView, boolean isAdd) {

        String buyNum = textView.getText().toString();
        if (isAdd) {
            buyNum = String.valueOf(Integer.decode(buyNum) + 1);
        } else {
            if (Integer.decode(buyNum) > 1)
                buyNum = String.valueOf(Integer.decode(buyNum) - 1);
        }

        textView.setText(buyNum);
        mDataBinding.prise.setText("￥" + String.valueOf(mVipPrice * Integer.decode(buyNum)));
        mBuyNum = buyNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CacheActivity.activityList.contains(MemberCenterActivity.this)) {
            CacheActivity.addActivity(MemberCenterActivity.this);
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("会员中心", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        loadBaseData();
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                        .updatePersonData(LoginHelper.getInstance().getUserToken(), null)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<PersonBasedataBean>() {
                    @Override
                    public void onNext(PersonBasedataBean result) {
                        Glide.with(mContext).load(ApiClient.getHxFriendsImage(LoginHelper.getInstance().getUserBean().getAvatar()));
                        if (result != null) {
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar()))
                                    .error(R.mipmap.account_logo).into(mDataBinding.headImage);
                            mDataBinding.name.setText(LoginHelper.getInstance().getUserBean().getRealName() + "\n" + LoginHelper.getInstance().getUserBean().getAddress());
                            mDataBinding.name.setText(result.getInfo().getRealname() + "\n" + result.getInfo().getLj_county());
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_center;
    }
}

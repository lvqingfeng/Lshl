package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.MailFriendBean;
import com.lshl.bean.MailListBean;
import com.lshl.databinding.ActivityMailListBinding;
import com.lshl.ui.info.GetPhoneNumberFromMobile;
import com.lshl.ui.info.adapter.MailFriendAdaper;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MailListActivity extends BaseActivity<ActivityMailListBinding>
        implements SpringView.OnFreshListener {
    private GetPhoneNumberFromMobile getPhoneNumberFromMobile;
    private List<MailListBean> mListData = new ArrayList<>();
    private boolean isEnd = true;
    private List<MailFriendBean.InfoBean> mList = new ArrayList<>();
    private MailFriendAdaper mailFriendAdaper;
    private String mData;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MailListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("通讯录列表", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mListData.clear();
        getPhoneNumberFromMobile = new GetPhoneNumberFromMobile();
        mListData = getPhoneNumberFromMobile.getPhoneNumberFromMobile(mContext);
        StringBuilder builder = new StringBuilder();
        if (mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                if (i < mListData.size() - 1) {
                    builder.append(mListData.get(i).getName() + "|" + mListData.get(i).getPhoneNum().replaceAll(" ", "") + ",");
                } else {
                    builder.append(mListData.get(i).getName() + "|" + mListData.get(i).getPhoneNum().replaceAll(" ", ""));
                }
            }
        }
        mData = builder.toString();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mailFriendAdaper = new MailFriendAdaper(mList, MailListActivity.this);
        mDataBinding.recyclerView.setAdapter(mailFriendAdaper);
        setListData(mListData);
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMailFriendList(mData, LoginHelper.getInstance().getUserBean().getUid()), new ProgressSubscriber<MailFriendBean>(mContext, new SubscriberOnNextListener<MailFriendBean>() {
            @Override
            public void onNext(MailFriendBean result) {
                if (result.getStatus() == 1) {
                    mList.addAll(result.getInfo());
                    mailFriendAdaper.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onRefresh();
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mail_list;
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mListData.clear();
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}

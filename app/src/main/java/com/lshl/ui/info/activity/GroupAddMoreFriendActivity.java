package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.lshl.bean.AddContactBean;
import com.lshl.databinding.ActivityGroupAddMoreFriend;
import com.lshl.ui.info.adapter.AddNewContactAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class GroupAddMoreFriendActivity extends BaseActivity<ActivityGroupAddMoreFriend> implements SpringView.OnFreshListener {
    private boolean isEnd = true;
    private List<AddContactBean.InfoBean.ContactListBean> contactListBean;
    private AddNewContactAdapter mAdapter;
    private String groupId;

    public static void actionStart(Activity activity, String groupId) {
        Intent intent = new Intent(activity, GroupAddMoreFriendActivity.class);
        intent.putExtra("groupId", groupId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        groupId = getIntent().getStringExtra("groupId");
        loadListData(1);
        contactListBean = new ArrayList<>();
        mDataBinding.recyclerGroupMoreList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerGroupMoreList.addItemDecoration(new DividerGridItemDecoration(mContext));
        mAdapter = new AddNewContactAdapter(mContext, contactListBean, "moreFriend", groupId);
        mDataBinding.recyclerGroupMoreList.setAdapter(mAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_add_more_friend;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        getAddContact(1, 1, 0, 0, 0, "", page);
    }

    private void getAddContact(int key, int all, int industry, int origin,
                               int nearby, String info, int p) {
        //获取推荐的人
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .getAddContact(LoginHelper.getInstance().getUserToken(), key, all, industry, origin, nearby, info, p), new ProgressSubscriber<AddContactBean>(mContext,
                new SubscriberOnNextListener<AddContactBean>() {
                    @Override
                    public void onNext(AddContactBean result) {
                        if (result.getStatus() == 1) {
                            isEnd = result.getInfo().getEnd() == 1;
                            contactListBean.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }

                    }
                }));
    }
}

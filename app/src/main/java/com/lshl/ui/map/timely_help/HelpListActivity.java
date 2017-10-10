package com.lshl.ui.map.timely_help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.lshl.bean.HelpWaitGetMemberListBean;
import com.lshl.databinding.ActivityHelpListBinding;
import com.lshl.ui.MainActivity;
import com.lshl.ui.map.adapter.HelpWaitGetAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class HelpListActivity extends BaseActivity<ActivityHelpListBinding> implements SpringView.OnFreshListener {


    private String mHelpId;
    private MyRefreshHandler mRefreshHandler;
    private boolean isEnd;
    private List<HelpWaitGetMemberListBean.InfoBean.ListBean> mListData = new ArrayList<>();
    private boolean isReturnHome;

    private HelpWaitGetAdapter mAdapter;

    public static void actionStart(Activity activity, String helpId) {

        Intent intent = new Intent(activity, HelpListActivity.class);
        intent.putExtra("help_id", helpId);
        activity.startActivity(intent);

    }

    public class Presenter {

        public void onClickRescueEnd() {

            if (isReturnHome) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                                .earlyEndRescue(LoginHelper.getInstance().getUserToken(), mHelpId)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                try {
                                    String resultStr = result.string();
                                    JSONObject object = JSON.parseObject(resultStr);
                                    String status = object.getString("status");
                                    if (status.equals(ApiService.STATUS_SUC)) {
                                        mDataBinding.btnRescueEnd.setBackgroundResource(R.drawable.bg_blue_btn);
                                        mDataBinding.btnRescueEnd.setText("返回首页");
                                        isReturnHome = true;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                );

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("救援详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mHelpId = getIntent().getStringExtra("help_id");
        Log.d("救援列表", "救援id = " + mHelpId);
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(new Presenter());

        mRefreshHandler = new MyRefreshHandler(this);
        mRefreshHandler.onPullDownToRefresh(null);

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);

        mDataBinding.recyclerHelpList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerHelpList.addItemDecoration(new DividerGridItemDecoration(mContext));
        mAdapter = new HelpWaitGetAdapter(mContext, mListData);
        mDataBinding.recyclerHelpList.setAdapter(mAdapter);

    }

    private void loadData(int pager) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .getRescueEvent(LoginHelper.getInstance().getUserToken(), mHelpId, String.valueOf(pager))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HelpWaitGetMemberListBean>() {
                    @Override
                    public void onNext(HelpWaitGetMemberListBean result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_list;
    }

    @Override
    public void onRefresh() {
        mRefreshHandler.onPullDownToRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        mRefreshHandler.onPullUpToRefresh(isEnd);
    }


    private static class MyRefreshHandler extends RefreshHandler {
        private WeakReference<HelpListActivity> mActivity;

        MyRefreshHandler(HelpListActivity activity) {
            super(activity.mDataBinding.springView);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (mActivity.get().mListData != null && page == 1)
                mActivity.get().mListData.clear();
            mActivity.get().loadData(page);
        }
    }

}

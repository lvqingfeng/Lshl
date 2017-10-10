package com.lshl.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseFragment;
import com.lshl.bean.NewsTabBean;
import com.lshl.databinding.FragmentNewsBinding;
import com.lshl.db.NewsTabDao;
import com.lshl.ui.MainActivity;
import com.lshl.ui.login.LoginActivity;
import com.lshl.ui.map.timely_help.HelpListActivity;
import com.lshl.ui.map.timely_help.HelpMapActivity;
import com.lshl.ui.news.activity.AddTabActivity;
import com.lshl.ui.news.adapter.NewTabFragmentPagerAdapter;
import com.lshl.ui.news.fragment.NewsListFragment;
import com.lshl.ui.news.fragment.NewsListYaoWenFragment;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.JsonFormFileUtils;
import com.lshl.view.NewsTabLayout;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 新闻Fragment
 */
public class NewsFragment extends BaseFragment<FragmentNewsBinding> {

    private static final int ADD_CODE_REQUEST_CODE = 0x0000132;

    private NewsTabLayout mNewsTabLayout;

    private NewsTabDao mNewsTabDao;

    private NewsCallback mCallback;
    private CheckBox mRightView;
    private NewTabFragmentPagerAdapter mPagerAdapter;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public class Presenter {

        public void callSosListener() {
            if (LoginHelper.getInstance().isOnline()) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                                .rescueIsBeginning(LoginHelper.getInstance().getUserToken())
                        , new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String resultStr = responseBody.string();
                                    JSONObject object = JSON.parseObject(resultStr);
                                    String status = object.getString("status");
                                    if (status.equals(ApiService.STATUS_SUC)) {
                                        String helpId = object.getString("info");
                                        HelpListActivity.actionStart(getActivity(), helpId);
                                    } else
                                        HelpMapActivity.actionStart(NewsFragment.this);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            } else {
                DialogUtils.alertDialog(mContext, "温馨提示", "检测到您尚未登陆，是否进行登陆", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        LoginActivity.actionStart(getActivity(), false, "");
                    }
                });
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (NewsCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsTabDao = new NewsTabDao();
        mRightView = ((MainActivity) getActivity()).getRightViews();
        if (getArguments() != null) {

        }
    }

    private List<NewsTabBean> mNewsTabList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mNewsTabList = mNewsTabDao.getAddNewsTabList();

        if (mNewsTabList == null || mNewsTabList.size() < 1) {
            try {
                List<NewsTabBean> tabList = JsonFormFileUtils.getJsonArray(getContext(), "news_tab.json", NewsTabBean.class);
                mNewsTabDao.saveNewsTabList(tabList);
                mNewsTabList = mNewsTabDao.getAddNewsTabList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mNewsTabLayout = ((MainActivity) getActivity()).mNewsTabGroup;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(new Presenter());

        initTabLayout();
        setupTabLayout();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTabActivity.actionStart(NewsFragment.this, ADD_CODE_REQUEST_CODE);
            }
        };
        mCallback.rightViewOnClick(listener);
        if (mRightView != null) {
            mRightView.setOnClickListener(listener);
        }

    }

    private void initTabLayout() {
        mPagerAdapter = new NewTabFragmentPagerAdapter(getChildFragmentManager());
        mDataBinding.vpNews.setAdapter(mPagerAdapter);
        mNewsTabLayout.setupViewPager(mDataBinding.vpNews);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CODE_REQUEST_CODE) {
            if (mNewsTabDao != null && mNewsTabList != null) {
                mNewsTabList.clear();
                mNewsTabList.addAll(mNewsTabDao.getAddNewsTabList());
                setupTabLayout();
            } else {
                Toast.makeText(mContext, "数据库异常", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupTabLayout() {
        mPagerAdapter.resetTabs();
        for (NewsTabBean newsTabBean : mNewsTabList) {
            if (newsTabBean == null)
                return;
            if (newsTabBean.isAdd()) {
                if ("要闻".equals(newsTabBean.getVal())) {
                    mPagerAdapter.addTabs(NewsListYaoWenFragment.newInstance(newsTabBean.getKey()), newsTabBean.getVal());
                } else {
                    mPagerAdapter.addTabs(NewsListFragment.newInstance(newsTabBean.getKey()), newsTabBean.getVal());
                }

            }
        }

        mDataBinding.vpNews.setAdapter(mPagerAdapter);
//        mPagerAdapter.notifyDataSetChanged();
    }

    public interface NewsCallback {
        void rightViewOnClick(View.OnClickListener listener);
    }

}

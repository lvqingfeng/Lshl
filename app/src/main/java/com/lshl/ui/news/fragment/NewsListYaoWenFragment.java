package com.lshl.ui.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LazyFragment;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.NewsListBean;
import com.lshl.databinding.FragmentYaowenBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;
import com.lshl.ui.news.adapter.NewsMenuItemAdapter;
import com.lshl.ui.news.adapter.YaoWenAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/******
 * Created by Administrator on 2017/1/13.
 */

public class NewsListYaoWenFragment extends LazyFragment<FragmentYaowenBinding>
        implements SpringView.OnFreshListener {
    private String mType;
    private List<NewsListBean.ListBean> mListData;
    private YaoWenAdapter mAdapter;
    private boolean isEnd;
    private NewsMenuItemAdapter newsMenuItemAdapter;

    public static NewsListYaoWenFragment newInstance(String type) {
        NewsListYaoWenFragment fragment = new NewsListYaoWenFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yaowen;
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        setListData(mListData);
        initRefreshView();
        onRefresh();
    }

    private void initRefreshView() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new YaoWenAdapter(mListData, "yaowen", (BaseActivity) getActivity());
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                final int position = vh.getLayoutPosition();
                if (position > 0) {
                    checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                        @Override
                        public void onSuccess(AuthorityCheckBean bean) {
                            NewsListBean.ListBean listBean = mListData.get(position - 2);
                            if ("new_member".equals(listBean.getOnlylabel())) {
                                HxMemberDetailsActivity.actionStart(getActivity(), "", listBean.getProject_id(), false);
                            } else if ("xiangmu".equals(listBean.getOnlylabel())) {
                                ProjectDetailActivity.actionStart(getActivity(), listBean.getProject_id(), ProjectDetailActivity.FROM_APPLICEN);
                            } else if ("koubei".equals(listBean.getOnlylabel())) {
                                KouBeiDetailsActivity.actionStart(getActivity(), listBean.getProject_id(), KouBeiDetailsActivity.FROM_APPLICEN);
                            } else if ("shanghui".equals(listBean.getOnlylabel())) {
                                SHDynamDetailsicActivity.actionStart(getActivity(), listBean.getProject_id());
                            } else if ("gongyi".equals(listBean.getOnlylabel())) {
                                DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MB, listBean.getProject_id());
                            } else if ("huzhu".equals(listBean.getOnlylabel())) {
                                DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MA, listBean.getProject_id());
                            } else if ("zimaoqu".equals(listBean.getOnlylabel())) {
                                GoodsDetailsActivity.actionStart(getActivity(), listBean.getProject_id(), GoodsDetailsActivity.FROM_OTHER);
                            } else if ("findhelper".equals(listBean.getOnlylabel())) {
                                LookHelpDetailsActivity.actionStart(getActivity(), listBean.getProject_id());
                            } else {
                                NewsInfoActivity.actionStart(getActivity(), listBean.getId(), ApiClient.getNewsImage(listBean.getImg()));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getNewsListData(mType, String.valueOf(page))
                , new Subscriber<HttpResult<NewsListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        mDataBinding.pbLoad.setVisibility(View.GONE);
//                        mDataBinding.tvError.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(HttpResult<NewsListBean> newsListBeanHttpResult) {
                        if (newsListBeanHttpResult.getStatus().equals(ApiService.STATUS_SUC)) {
//                            mDataBinding.pbLoad.setVisibility(View.GONE);
                            isEnd = newsListBeanHttpResult.getInfo().getEnd() == 1;
                            if (newsListBeanHttpResult.getInfo().getList() != null) {
                                mListData.addAll(newsListBeanHttpResult.getInfo().getList());
                                if (newsListBeanHttpResult.getInfo().getList().size() > 0) {
                                    mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                                } else {
                                    mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        } else if (newsListBeanHttpResult.getStatus().equals(ApiService.TOKEN_EX)) {
//                            mDataBinding.pbLoad.setVisibility(View.GONE);
//                            mDataBinding.tvError.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, "登陆信息异常，请重新登陆！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.bannerStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.bannerStop();
        }
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}

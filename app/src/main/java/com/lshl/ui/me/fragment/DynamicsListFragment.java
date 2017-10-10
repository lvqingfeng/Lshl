package com.lshl.ui.me.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LazyFragment;
import com.lshl.bean.DynamicBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.FragmentDynamicsListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.activity.DynamicNewsInfoActivity;
import com.lshl.ui.me.activity.DefaultImageActivity;
import com.lshl.ui.me.activity.DynamicDetailsActivity;
import com.lshl.ui.me.activity.PersonDynamicActivity;
import com.lshl.ui.me.activity.SendDynamicActivity;
import com.lshl.ui.me.adapter.DynamicsListAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicsListFragment extends LazyFragment<FragmentDynamicsListBinding>
        implements SpringView.OnFreshListener {

    private final static int DEFAULT_IMAGE_REQUEST_CODE = 0x000125;
    private List<DynamicBean.InfoBean.ListBean> mListData;
    private boolean isEnd;
    private DynamicsListAdapter mAdapter;
    private DynamicBean.InfoBean infoBean;

    public static DynamicsListFragment newInstance() {
        DynamicsListFragment fragment = new DynamicsListFragment();
        /*Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PersonDynamicActivity) {
            ((PersonDynamicActivity) context).getRightViews().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendDynamicActivity.actionStart(getActivity(), SendDynamicActivity.SEND_DYNAMIC);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SendDynamicActivity.SEND_DYNAMIC:
                    onRefresh();
                    break;
                case DynamicDetailsActivity.DynamicDetails:
                    onRefresh();
                    break;
                case DEFAULT_IMAGE_REQUEST_CODE:
                    onRefresh();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBaseData();
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamics_list;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initViews() {

        mListData = new ArrayList<>();
        setListData(mListData);

        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));

        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                DynamicDetailsActivity.actionStart(getActivity(), mListData.get(vh.getLayoutPosition()), DynamicDetailsActivity.DynamicDetails);
            }

        });
        mAdapter = new DynamicsListAdapter(mListData, (BaseActivity) getActivity());
        mDataBinding.recyclerView.setAdapter(mAdapter);
        loadBaseData();
        mDataBinding.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultImageActivity.actionStart(getActivity(), DEFAULT_IMAGE_REQUEST_CODE);
            }
        });
        mDataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDynamicActivity.actionStart(getActivity(), SendDynamicActivity.SEND_DYNAMIC);
            }
        });
        mDataBinding.newInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDynamciNewdInfo();
                DynamicNewsInfoActivity.actionStart(getActivity());
            }
        });
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updatePersonBaseData(LoginHelper.getInstance().getUserToken(), LoginHelper.getInstance().getUserBean().getUid(), ""),
                new Subscriber<PersonBasedataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PersonBasedataBean result) {
                        if (result != null) {
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).error(R.mipmap.account_logo).into(mDataBinding.ivHeadImage);
                            mDataBinding.tvName.setText(result.getInfo().getRealname());
                            mDataBinding.tvAddress.setText(result.getInfo().getLj_county());
                            Constant.getGradeForAll(result.getInfo().getGrade(), mDataBinding.tvGrade);
                            if (result.getInfo().getImage_wall().size() > 0) {
                                Glide.with(mContext).load(ApiClient.getImageWallImage(result.getInfo().getImage_wall().get(0))).error(R.mipmap.default_background).into(mDataBinding.background);
                            } else {
                                mDataBinding.background.setImageResource(R.mipmap.default_background);
                            }
                            if (result.getInfo().getDynamic_num() > 0) {
                                mDataBinding.newInfo.setText(result.getInfo().getDynamic_num() + "条新消息");
                            } else {
                                mDataBinding.newInfo.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).updateDynamicList(LoginHelper.getInstance().getUserToken(), String.valueOf(page))
                , new Subscriber<DynamicBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DynamicBean dynamicBean) {
                        if (dynamicBean.getStatus().equals(ApiService.STATUS_SUC)) {
                            isEnd = dynamicBean.getInfo().getEnd() == 1;
                            infoBean = dynamicBean.getInfo();
                            List<DynamicBean.InfoBean.ListBean> listBeen = dynamicBean.getInfo().getList();
                            if (listBeen.size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }

                            mListData.addAll(listBeen);
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                            if (dynamicBean.getInfo().getBackground_map() != null) {
                                Glide.with(mContext).load(ApiService.BASE_URL + dynamicBean.getInfo().getBackground_map()).into(mDataBinding.background);
                            } else {
                                mDataBinding.background.setImageResource(R.mipmap.default_background);
                            }
                        } else if (dynamicBean.getStatus().equals(ApiService.TOKEN_EX)) {
                            Toast.makeText(mContext, "登录异常，请重试！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void clearDynamciNewdInfo() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).cleanDynamicNewsInfo(LoginHelper.getInstance().getUserToken()), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {

            }
        });
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

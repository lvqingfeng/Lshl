package com.lshl.ui.me.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.LazyFragment;
import com.lshl.bean.DynamicBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.ItemLayoutDynamicBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.activity.DynamicDetailsActivity;
import com.lshl.ui.me.activity.PersonDynamicActivity;
import com.lshl.ui.me.activity.SendDynamicActivity;
import com.lshl.ui.me.adapter.PerSonDynamicAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static android.app.Activity.RESULT_OK;

/***
 * Created by 吕清锋 on 2016/11/28.
 * 圈子的Fragment
 */

public class PerSonDynamicFragment extends LazyFragment<ItemLayoutDynamicBinding> implements SpringView.OnFreshListener {
    private List<DynamicBean.InfoBean.ListBean> mList;
    private boolean isEnd;
    private PerSonDynamicAdapter perSonDynamicAdapter;

    public static PerSonDynamicFragment newInstance() {
        Bundle args = new Bundle();
        PerSonDynamicFragment fragment = new PerSonDynamicFragment();
        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {
        loadBaseData();
        initLoadData();
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updatePersonData(LoginHelper.getInstance().getUserToken(), null),
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
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).transform(new GlideCircleTransform(mContext)).error(R.mipmap.account_logo).into(mDataBinding.ivHeadImage);
                            mDataBinding.tvName.setText(result.getInfo().getRealname());
                            mDataBinding.tvAddress.setText(result.getInfo().getLj_county());
                            Constant.getGradeForAll(result.getInfo().getGrade(), mDataBinding.tvGrade);
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout_dynamic;
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        perSonDynamicAdapter = new PerSonDynamicAdapter(mList, getActivity());
        mDataBinding.recyclerView.setAdapter(perSonDynamicAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                DynamicBean.InfoBean.ListBean bean = mList.get(position);
                DynamicDetailsActivity.actionStart(getActivity(), bean, DynamicDetailsActivity.DynamicDetails);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateDynamicList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<DynamicBean>() {
            @Override
            public void onNext(DynamicBean result) {
                if (result != null) {
                    List<DynamicBean.InfoBean.ListBean> list = result.getInfo().getList();
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(list);
                    perSonDynamicAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() <= 0) {
                        mDataBinding.springView.setBackgroundResource(R.mipmap.kongkongruye);
                    } else {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    }
                } else {
                    Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DynamicDetailsActivity.DynamicDetails:
                    onRefresh();
                    break;
                case SendDynamicActivity.SEND_DYNAMIC:
                    onRefresh();
                    break;
                default:
                    break;
            }
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

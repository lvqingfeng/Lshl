package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.lshl.base.HttpResult;
import com.lshl.bean.MyCollectionBean;
import com.lshl.databinding.ActivityMyCollectionBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.adapter.MyCollectionAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 我的关注页面
 */
public class MyCollectionActivity extends BaseActivity<ActivityMyCollectionBinding> implements
        SpringView.OnFreshListener {
    private List<MyCollectionBean.InfoBean.ListBean> mList;
    private MyCollectionAdapter myCollectionAdapter;
    private boolean isEnd;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyCollectionActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的关注", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spCollection;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerCollection.setLayoutManager(manager);
        mDataBinding.recyclerCollection.addItemDecoration(new DividerGridItemDecoration(mContext));

        mList = new ArrayList<>();
        setListData(mList);
        initLoadData();

        mDataBinding.recyclerCollection.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerCollection) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String id = mList.get(position).getCg_goodsid();
                GoodsDetailsActivity.actionStart(MyCollectionActivity.this, id, GoodsDetailsActivity.FROM_OTHER);
            }

            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                final int fPosition = vh.getLayoutPosition();
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要取消该商品收藏", new SweetAlertDialog.OnSweetClickListener() {
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
                                ).cancelCollectionGoods(LoginHelper.getInstance().getUserToken(), mList.get(fPosition).getGd_id())
                                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                            onRefresh();
                                        } else {
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        );
                    }
                });
            }
        });
        myCollectionAdapter = new MyCollectionAdapter(mList);
        mDataBinding.recyclerCollection.setAdapter(myCollectionAdapter);
        mDataBinding.spCollection.setHeader(new RotationHeader(mContext));
        mDataBinding.spCollection.setFooter(new RotationFooter(mContext));
        mDataBinding.spCollection.setListener(this);
    }

    @Override
    protected void loadListData(int page) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateMyCollection(LoginHelper.getInstance().getUserToken(), page + "")
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<MyCollectionBean>() {


                    @Override
                    public void onNext(MyCollectionBean result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<MyCollectionBean.InfoBean.ListBean> list = result.getInfo().getList();
                            mList.addAll(list);
                            myCollectionAdapter.notifyDataSetChanged();
                            mDataBinding.spCollection.onFinishFreshAndLoad();
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.recyclerCollection.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerCollection.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
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

package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.MyGoodsBean;
import com.lshl.databinding.ActivityMyShopBinding;
import com.lshl.recycler_listener.OnRecyclerItemListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.me.adapter.MyGoodsAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 自贸区中个人发布的商品
 */
public class MyGoodsActivity extends BaseActivity<ActivityMyShopBinding>
        implements SpringView.OnFreshListener {

    private static final int REQUEST_CODE_ADD_GOODS = 0x000432;//添加商品的请求码
    private static final int REQUEST_CODE_EDIT_GOODS = 0x000433;//修改商品的请求码

    private boolean isEnd;

    private List<MyGoodsBean.ListBean> mList;
    private MyGoodsAdapter myGoodsAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyGoodsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_GOODS) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        } else if (requestCode == REQUEST_CODE_EDIT_GOODS) {
            if (resultCode == RESULT_OK) {
                onRefresh();
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
        setTextTitleView("我的商品", DEFAULT_TITLE_TEXT_COLOR);
        CheckBox rightView = getRightView();
        rightView.setButtonDrawable(R.drawable.ic_vector_add);
        //右上角按钮，添加商品
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserIsDues(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        EditGoodsActivity.actionStart(MyGoodsActivity.this, null, "", EditGoodsActivity.FROM_ADD_GOODS, REQUEST_CODE_ADD_GOODS);
                    }
                });
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spShop;
    }

    @Override
    protected void initViews() {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerShop.setLayoutManager(manager);
        mDataBinding.recyclerShop.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerShop.setItemAnimator(new DefaultItemAnimator());
        mDataBinding.spShop.setHeader(new RotationHeader(mContext));
        mDataBinding.spShop.setFooter(new RotationFooter(mContext));
        mDataBinding.spShop.setListener(this);
        mList = new ArrayList<>();
        setListData(mList);
        myGoodsAdapter = new MyGoodsAdapter(MyGoodsActivity.this, mList);
        mDataBinding.recyclerShop.setAdapter(myGoodsAdapter);

        mDataBinding.recyclerShop.addOnItemTouchListener(new OnRecyclerItemListener(mDataBinding.recyclerShop) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(MyGoodsActivity.this, mList.get(vh.getLayoutPosition()).getGd_id(), GoodsDetailsActivity.FROM_MY_SHOP, REQUEST_CODE_EDIT_GOODS);
            }

            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要删除该商品", new SweetAlertDialog.OnSweetClickListener() {
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
                                ).delMyGoods(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getGd_id())
                                , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                        onRefresh();
                                    }
                                })
                        );
                    }
                });
            }
        });
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateMyGoodsList(LoginHelper.getInstance().getUserToken(), page + "")
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<MyGoodsBean>>() {
                    @Override
                    public void onNext(HttpResult<MyGoodsBean> result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<MyGoodsBean.ListBean> list = result.getInfo().getList();
                            mList.addAll(list);
                            myGoodsAdapter.notifyDataSetChanged();
                            mDataBinding.spShop.onFinishFreshAndLoad();
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.recyclerShop.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerShop.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                }));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shop;
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

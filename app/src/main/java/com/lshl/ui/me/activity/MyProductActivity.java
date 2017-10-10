package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.lshl.bean.MyProductBean;
import com.lshl.databinding.ActivityMyProductBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.adapter.MyProductAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyProductActivity extends BaseActivity<ActivityMyProductBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String enid;
    private List<MyProductBean.InfoBean.ListBean> mList;
    private MyProductAdapter myProductAdapter;
    private static final int REQUESR_CODE_PRODECT = 0x000123;

    public static void actionStart(Activity activity, String enid) {
        Intent intent = new Intent(activity, MyProductActivity.class);
        intent.putExtra("enid", enid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的商品", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        enid = getIntent().getStringExtra("enid");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditGoodsActivity.actionStart(MyProductActivity.this, null
                        , enid, EditGoodsActivity.FROM_ADD_GOODS, REQUESR_CODE_PRODECT);
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        myProductAdapter = new MyProductAdapter(mList, MyProductActivity.this);
        mDataBinding.recyclerView.setAdapter(myProductAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(MyProductActivity.this, mList.get(vh.getLayoutPosition()).getGd_id(), GoodsDetailsActivity.FROM_MY_SHOP, 0);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
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
                                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMyProduct(enid, String.valueOf(page)), new ProgressSubscriber<MyProductBean>(mContext, new SubscriberOnNextListener<MyProductBean>() {
            @Override
            public void onNext(MyProductBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    myProductAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_product;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESR_CODE_PRODECT) {
            if (resultCode == RESULT_OK) {
                onRefresh();
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

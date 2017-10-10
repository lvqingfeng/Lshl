package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.MemberGoodsImageBean;
import com.lshl.databinding.ActivitySellerOtherGoodsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.SellerOtherGoodsAdapter;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.List;

public class SellerOtherGoodsActivity extends BaseActivity<ActivitySellerOtherGoodsBinding> {

    public static void actionStart(Activity activity, MemberGoodsImageBean imageBean) {
        Intent intent = new Intent(activity, SellerOtherGoodsActivity.class);
        intent.putExtra("bean", imageBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("卖家其他商品", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        MemberGoodsImageBean goodsImageBean = (MemberGoodsImageBean) getIntent().getSerializableExtra("bean");
        final List<MemberGoodsImageBean.ListBean> infoList = goodsImageBean.getList();
        mDataBinding.recyclerGoods.setLayoutManager(new LinearLayoutManager(mContext));
        SellerOtherGoodsAdapter adapter = new SellerOtherGoodsAdapter(infoList);
        mDataBinding.recyclerGoods.setAdapter(adapter);
        mDataBinding.recyclerGoods.addItemDecoration(new DividerGridItemDecoration(mContext));

        mDataBinding.recyclerGoods.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerGoods) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(SellerOtherGoodsActivity.this, infoList.get(vh.getLayoutPosition()).getGd_id(), GoodsDetailsActivity.FROM_OTHER);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_other_goods;
    }
}

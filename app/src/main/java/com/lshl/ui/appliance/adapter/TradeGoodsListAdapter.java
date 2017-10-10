package com.lshl.ui.appliance.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.LshlApplication;
import com.lshl.bean.TradeGoodsListBean;
import com.lshl.databinding.TradeGoodsItemBinding;
import com.lshl.ui.me.activity.GoodsDetailsActivity;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月31日
 * 时间：11:18
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class TradeGoodsListAdapter extends RecyclerView.Adapter<BindingViewHolder<TradeGoodsItemBinding>> {


    private List<TradeGoodsListBean.ListBean> mListData;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public TradeGoodsListAdapter(Activity context, List<TradeGoodsListBean.ListBean> listData) {
        mActivity = context;
        mInflater = LayoutInflater.from(context);
        mListData = listData;

    }


    @Override
    public BindingViewHolder<TradeGoodsItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View goodsView = mInflater.inflate(R.layout.item_layout_trade_goods, parent, false);
        return new BindingViewHolder<>(TradeGoodsItemBinding.bind(goodsView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<TradeGoodsItemBinding> holder, final int position) {
        TradeGoodsItemBinding goodsItemBinding = holder.getBinding();
        goodsItemBinding.setGoodsBean(mListData.get(position));
        goodsItemBinding.setImageSize(LshlApplication.sWindowWidth / 2);

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


}

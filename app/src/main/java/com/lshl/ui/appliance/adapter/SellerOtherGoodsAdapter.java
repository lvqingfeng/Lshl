package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MemberGoodsImageBean;
import com.lshl.databinding.SellerOtherGoodsItemBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月05日
 * 时间：9:05
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SellerOtherGoodsAdapter extends RecyclerView.Adapter<BindingViewHolder<SellerOtherGoodsItemBinding>> {

    private List<MemberGoodsImageBean.ListBean> mListData;

    public SellerOtherGoodsAdapter(List<MemberGoodsImageBean.ListBean> listData) {
        mListData = listData;
    }

    @Override
    public BindingViewHolder<SellerOtherGoodsItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_seller_other_goods, parent, false);
        return new BindingViewHolder<>(SellerOtherGoodsItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<SellerOtherGoodsItemBinding> holder, int position) {
        holder.getBinding().setInfoBean(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

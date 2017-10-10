package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.SellOrderBean;
import com.lshl.databinding.SellOrderItemBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月05日
 * 时间：16:47
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SellOrderListAdapter extends RecyclerView.Adapter<BindingViewHolder<SellOrderItemBinding>> {

    private List<SellOrderBean.InfoBean.ListBean> mListData;

    public SellOrderListAdapter(List<SellOrderBean.InfoBean.ListBean> listData) {
        mListData = listData;
    }

    @Override
    public BindingViewHolder<SellOrderItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_sell_order, parent, false);
        return new BindingViewHolder<>(SellOrderItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<SellOrderItemBinding> holder, int position) {
        holder.getBinding().setListBean(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

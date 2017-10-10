package com.lshl.ui.appliance.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.GoodsOrderBean;
import com.lshl.databinding.ItemSelectGroupTypeBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月01日
 * 时间：20:32
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class TradeGoodsOrderSelectAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemSelectGroupTypeBinding>> {

    private List<GoodsOrderBean> mListData;

    public TradeGoodsOrderSelectAdapter(List<GoodsOrderBean> listData) {
        mListData = listData;
    }

    @Override
    public BindingViewHolder<ItemSelectGroupTypeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_select_group_type, parent, false);
        return new BindingViewHolder<>(ItemSelectGroupTypeBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemSelectGroupTypeBinding> holder, int position) {
        GoodsOrderBean typeBean = mListData.get(position);
        holder.getBinding().tvGroupTypeName.setText(typeBean.getName());
        holder.getBinding().tvGroupTypeName.setGravity(Gravity.CENTER_HORIZONTAL);
        if (typeBean.isSelect()) {
            holder.getBinding().tvGroupTypeName.setTextColor(ContextCompat.getColor(holder.mContext, android.R.color.holo_red_light));
        } else {
            holder.getBinding().tvGroupTypeName.setTextColor(0xff333333);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

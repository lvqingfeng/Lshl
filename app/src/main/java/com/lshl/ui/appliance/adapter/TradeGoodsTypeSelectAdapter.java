package com.lshl.ui.appliance.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.TradeGoodsTypeBean;
import com.lshl.databinding.ItemSelectGroupTypeBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月01日
 * 时间：10:09
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class TradeGoodsTypeSelectAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemSelectGroupTypeBinding>> {

    private List<TradeGoodsTypeBean.InfoBean> mGoodsTypeData;

    public TradeGoodsTypeSelectAdapter(List<TradeGoodsTypeBean.InfoBean> typeData) {
        mGoodsTypeData = typeData;
    }

    @Override
    public BindingViewHolder<ItemSelectGroupTypeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_select_group_type, parent, false);
        return new BindingViewHolder<>(ItemSelectGroupTypeBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemSelectGroupTypeBinding> holder, int position) {
        TradeGoodsTypeBean.InfoBean typeBean = mGoodsTypeData.get(position);
        holder.getBinding().tvGroupTypeName.setText(typeBean.getGd_name());
        if (typeBean.isSelect()) {
            holder.getBinding().ivGroupTypeChecked.setVisibility(View.VISIBLE);
            holder.getBinding().tvGroupTypeName.setTextColor(ContextCompat.getColor(holder.mContext, android.R.color.holo_red_light));
        } else {
            holder.getBinding().tvGroupTypeName.setTextColor(0xff333333);
            holder.getBinding().ivGroupTypeChecked.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsTypeData == null ? 0 : mGoodsTypeData.size();
    }
}

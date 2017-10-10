package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.AppliceGongYiBean;
import com.lshl.databinding.ItemLayoutAppliceGongyiBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */

public class AppliceGongyiAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutAppliceGongyiBinding>> {
    private List<AppliceGongYiBean.ListBean> mList;

    public AppliceGongyiAdapter(List<AppliceGongYiBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutAppliceGongyiBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_applice_gongyi,parent,false);
        return new BindingViewHolder<>(ItemLayoutAppliceGongyiBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutAppliceGongyiBinding> holder, int position) {
        AppliceGongYiBean.ListBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getImg()).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().status.setText(Constant.getStatusForMutual(bean.getPw_status()));
        holder.getBinding().setBean(bean);
    }

    public int getItemCount() {
        return mList.size();
    }
}

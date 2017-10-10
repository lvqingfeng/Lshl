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
import com.lshl.bean.ForHelpBean;
import com.lshl.databinding.AppliceHelpItemBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class ForHelpAdapter extends RecyclerView.Adapter<BindingViewHolder<AppliceHelpItemBinding>> {
    private List<ForHelpBean.ListBean> mList;

    public ForHelpAdapter(List<ForHelpBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<AppliceHelpItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_applice_help,parent,false);
        return new BindingViewHolder<>(AppliceHelpItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<AppliceHelpItemBinding> holder, int position) {
        ForHelpBean.ListBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getImg()).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().setBean(bean);
        holder.getBinding().status.setText(Constant.getStatusForMutual(bean.getMa_status()));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}

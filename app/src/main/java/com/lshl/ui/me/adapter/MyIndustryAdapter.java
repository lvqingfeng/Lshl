package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.IndustryBean;
import com.lshl.databinding.ItemLayoutMyIndustryBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */

public class MyIndustryAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutMyIndustryBinding>>{
    private List<IndustryBean.InfoBean> mList;

    public MyIndustryAdapter(List<IndustryBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutMyIndustryBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_my_industry,parent,false);
        return new BindingViewHolder<>(ItemLayoutMyIndustryBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutMyIndustryBinding> holder, int position) {
        IndustryBean.InfoBean bean = mList.get(position);
        holder.getBinding().industry.setText(bean.getIn_name());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

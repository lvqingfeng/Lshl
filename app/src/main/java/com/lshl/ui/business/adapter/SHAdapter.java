package com.lshl.ui.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.ShangHuiBean;
import com.lshl.databinding.RecycleritemShanghuiShBinding;

import java.util.List;

/***
 * Created by Administrator on 2016/11/3.
 */

public class SHAdapter extends RecyclerView.Adapter<BindingViewHolder<RecycleritemShanghuiShBinding>> {
    private List<ShangHuiBean.ListBean> mList;

    public SHAdapter(List<ShangHuiBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecycleritemShanghuiShBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_shanghui_sh, parent, false);
        return new BindingViewHolder<>(RecycleritemShanghuiShBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecycleritemShanghuiShBinding> holder, int position) {
        ShangHuiBean.ListBean entity = mList.get(position);
        if (entity != null) {
            Glide.with(holder.mContext).load(ApiClient.getBuninessImages(entity.getBus_business_logo())).error(R.drawable.ease_default_image).into(holder.getBinding().ivRecyclershXx);
            holder.getBinding().setListBean(entity);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

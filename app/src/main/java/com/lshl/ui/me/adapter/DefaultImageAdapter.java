package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.DefaultImageBean;
import com.lshl.databinding.ItemLayoutDefaultImageBinding;

import java.util.List;

/**
 * Created by 吕清锋 on 2017/2/9.
 * 默认背景图的适配器
 */

public class DefaultImageAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutDefaultImageBinding>> {
    private List<DefaultImageBean.InfoBean> mList;
    private boolean isSelect;

    public DefaultImageAdapter(List<DefaultImageBean.InfoBean> mList, boolean isSelect) {
        this.mList = mList;
        this.isSelect = isSelect;
    }

    @Override
    public BindingViewHolder<ItemLayoutDefaultImageBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_default_image,parent,false);
        return new BindingViewHolder<>(ItemLayoutDefaultImageBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutDefaultImageBinding> holder, int position) {
        DefaultImageBean.InfoBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getDefaultImage(bean.getImg())).into(holder.getBinding().image);
        if (isSelect){
            holder.getBinding().icon.setVisibility(View.VISIBLE);
        }else {
            holder.getBinding().icon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

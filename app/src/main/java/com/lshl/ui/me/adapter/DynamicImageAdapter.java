package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLayoutDynamicImageBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class DynamicImageAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutDynamicImageBinding>> {
    private List<String> mList;

    public DynamicImageAdapter(List<String> mList) {
        this.mList = mList;
    }


    @Override
    public BindingViewHolder<ItemLayoutDynamicImageBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dynamic_image, parent, false);
        return new BindingViewHolder<>(ItemLayoutDynamicImageBinding.bind(view));
    }

    @Override

    public void onBindViewHolder(BindingViewHolder<ItemLayoutDynamicImageBinding> holder, int position) {
        String s = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getDynamicImage(s)).into(holder.getBinding().imageView);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}

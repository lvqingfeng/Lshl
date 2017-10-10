package com.lshl.ui.me.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLayoutImageBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class EnterpriseImageAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutImageBinding>>{
    private List<String> mList;

    public EnterpriseImageAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutImageBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_image,parent,false);
        return new BindingViewHolder<>(ItemLayoutImageBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutImageBinding> holder, int position) {
        String s = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getEnterPriseImage(s)).into(holder.getBinding().imageView);
        if (position+1==mList.size()){
            holder.getBinding().view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

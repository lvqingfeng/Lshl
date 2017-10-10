package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLayoutImageWallBinding;

import java.util.List;

/***
 * Created by Administrator on 2017/1/20.
 */

public class ImageWallAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutImageWallBinding>> {
    private List<String> mList;

    public ImageWallAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutImageWallBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_image_wall,parent,false);
        return new BindingViewHolder<>(ItemLayoutImageWallBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutImageWallBinding> holder, int position) {
        String s = mList.get(position);
        Glide.with(holder.mContext).load(s)
                .error(R.mipmap.account_logo).into(holder.getBinding().imageView);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

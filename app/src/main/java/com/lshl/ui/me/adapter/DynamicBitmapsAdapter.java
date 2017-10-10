package com.lshl.ui.me.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLayoutDynamicImageBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class DynamicBitmapsAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutDynamicImageBinding>> {
    private List<Bitmap> mList;

    public DynamicBitmapsAdapter(List<Bitmap> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutDynamicImageBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dynamic_image,parent,false);
        return new BindingViewHolder<>(ItemLayoutDynamicImageBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutDynamicImageBinding> holder, int position) {
        Bitmap bitmap = mList.get(position);
        holder.getBinding().imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.YouqingBean;
import com.lshl.databinding.ItemLayoutYouqingBinding;

import java.util.List;

/***
 * 友情链接的适配器
 */

public class YouqingAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutYouqingBinding>> {
    private List<YouqingBean.InfoBean> mListData;
    private BaseActivity activity;

    public YouqingAdapter(List<YouqingBean.InfoBean> mListData, BaseActivity activity) {
        this.mListData = mListData;
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<ItemLayoutYouqingBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_youqing,parent,false);
        return new BindingViewHolder<>(ItemLayoutYouqingBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutYouqingBinding> holder, int position) {
        YouqingBean.InfoBean infoBean = mListData.get(position);
        holder.getBinding().title.setText(infoBean.getName());
        holder.getBinding().name.setText(infoBean.getSource());
        Glide.with(holder.mContext).load(ApiClient.getbannerImage(infoBean.getImg())).error(R.mipmap.account_logo).into(holder.getBinding().image);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

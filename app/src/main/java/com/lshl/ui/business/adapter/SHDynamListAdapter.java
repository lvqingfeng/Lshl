package com.lshl.ui.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.SHDynamListBean;
import com.lshl.databinding.SHDynamItemBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月16日
 * 时间：22:16
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SHDynamListAdapter extends RecyclerView.Adapter<BindingViewHolder<SHDynamItemBinding>> {

    private List<SHDynamListBean.ListBean> mListData;

    public SHDynamListAdapter(List<SHDynamListBean.ListBean> listData) {
        mListData = listData;
    }

    @Override
    public BindingViewHolder<SHDynamItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_sh_dynam, parent, false);
        return new BindingViewHolder<>(SHDynamItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<SHDynamItemBinding> holder, int position) {
        holder.getBinding().setListBean(mListData.get(position));
        Glide.with(holder.mContext).load(ApiClient.getBuinessDynamicImage(mListData.get(position).getBd_img())).into(holder.getBinding().ivImage);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

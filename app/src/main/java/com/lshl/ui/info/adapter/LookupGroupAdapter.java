package com.lshl.ui.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLookupGroupListBinding;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.view.GlideCircleTransform;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月12日
 * 时间：19:49
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class LookupGroupAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLookupGroupListBinding>> {

    private List<HxGroupBean> mListData;
    private LayoutInflater mInflater;

    public LookupGroupAdapter(Context context, List<HxGroupBean> listData) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    @Override
    public BindingViewHolder<ItemLookupGroupListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_lookup_group_list, parent, false);
        return new BindingViewHolder<>(ItemLookupGroupListBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLookupGroupListBinding> holder, int position) {
        ItemLookupGroupListBinding binding = holder.getBinding();
        HxGroupBean listBean = mListData.get(position);
        Context context = holder.getBinding().getRoot().getContext();
        if (TextUtils.isEmpty(listBean.getGroup_img()))
            Glide.with(context).load(R.drawable.ease_group_icon).transform(new GlideCircleTransform(context)).into(binding.ivGroupHead);
        else
            Glide.with(context).load(ApiClient.getHxGroupImage(listBean.getGroup_img())).transform(new GlideCircleTransform(context)).into(binding.ivGroupHead);
        binding.tvGroupName.setText(listBean.getGroup_name());
        if (TextUtils.isEmpty(listBean.getGroup_info()))
            binding.tvGroupSummary.setText(context.getString(R.string.group_empty_summary));
        else
            binding.tvGroupSummary.setText(listBean.getGroup_info());

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }
}

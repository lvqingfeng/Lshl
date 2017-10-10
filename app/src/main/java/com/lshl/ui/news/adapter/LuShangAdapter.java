package com.lshl.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.LuShangBean;
import com.lshl.databinding.ItemLayoutHomePageMemberBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class LuShangAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutHomePageMemberBinding>> {
    private List<LuShangBean.InfoBean.ListBean> mList;

    public LuShangAdapter(List<LuShangBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutHomePageMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home_page_member,parent,false);
        return new BindingViewHolder<>(ItemLayoutHomePageMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutHomePageMemberBinding> holder, int position) {
        LuShangBean.InfoBean.ListBean listBean = mList.get(position);
        holder.getBinding().title.setText(listBean.getTitle());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(listBean.getAdd_time())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+listBean.getImg()).error(R.drawable.account_logo).into(holder.getBinding().headImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

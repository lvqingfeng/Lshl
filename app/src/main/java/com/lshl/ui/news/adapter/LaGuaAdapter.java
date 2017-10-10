package com.lshl.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.HomePageBean;
import com.lshl.databinding.ItemLayoutHomePageMemberBinding;
import com.lshl.databinding.ItemLayoutHomepageGoodsBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class LaGuaAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutHomePageMemberBinding>>{
    private List<HomePageBean.InfoBean.LaguaBean> mList;

    public LaGuaAdapter(List<HomePageBean.InfoBean.LaguaBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutHomePageMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home_page_member,parent,false);
        return new BindingViewHolder<>(ItemLayoutHomePageMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutHomePageMemberBinding> holder, int position) {
        HomePageBean.InfoBean.LaguaBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getTitle());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getAdd_time())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getImg()).error(R.drawable.account_logo).into(holder.getBinding().headImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

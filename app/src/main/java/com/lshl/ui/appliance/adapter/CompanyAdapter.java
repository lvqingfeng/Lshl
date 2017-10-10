package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.CityQiyeBean;
import com.lshl.databinding.ItemLayoutHomepageGoodsBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/*****
 * Created by Administrator on 2017/3/17.
 */

public class CompanyAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutHomepageGoodsBinding>> {
    private List<CityQiyeBean.ListBean> mList;

    public CompanyAdapter(List<CityQiyeBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutHomepageGoodsBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_homepage_goods,parent,false);
        return new BindingViewHolder<>(ItemLayoutHomepageGoodsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutHomepageGoodsBinding> holder, int position) {
        CityQiyeBean.ListBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getEn_name());
        holder.getBinding().name.setText(bean.getRealname());
        holder.getBinding().time.setText(bean.getEn_address());
        Glide.with(holder.mContext).load(ApiClient.getEnterPriseImage(bean.getImg())).error(R.drawable.account_logo).into(holder.getBinding().image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

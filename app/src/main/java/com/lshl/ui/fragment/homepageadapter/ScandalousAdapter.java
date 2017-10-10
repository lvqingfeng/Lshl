package com.lshl.ui.fragment.homepageadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.HomePageBean;
import com.lshl.databinding.ItemLayoutHomepageGoodsBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * Created by Administrator on 2017/3/14.
 */

public class ScandalousAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutHomepageGoodsBinding>> {
    private List<HomePageBean.InfoBean.ScandalousBean> mList;

    public ScandalousAdapter(List<HomePageBean.InfoBean.ScandalousBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutHomepageGoodsBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_homepage_goods,parent,false);
        return new BindingViewHolder<>(ItemLayoutHomepageGoodsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutHomepageGoodsBinding> holder, int position) {
        HomePageBean.InfoBean.ScandalousBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getTitle());
        holder.getBinding().name.setText(bean.getRealname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getAdd_time())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getImg()).error(R.drawable.account_logo).into(holder.getBinding().image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

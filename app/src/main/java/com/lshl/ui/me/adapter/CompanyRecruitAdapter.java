package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.CompanySecondBean;
import com.lshl.databinding.ItemLayoutRecruitBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class CompanyRecruitAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutRecruitBinding>> {
    private List<CompanySecondBean.InfoBean.ErBean> mList;

    public CompanyRecruitAdapter(List<CompanySecondBean.InfoBean.ErBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutRecruitBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_recruit,parent,false);
        return new BindingViewHolder<>(ItemLayoutRecruitBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutRecruitBinding> holder, int position) {
        CompanySecondBean.InfoBean.ErBean bean = mList.get(position);
        holder.getBinding().companyName.setText(bean.getEr_title());
        holder.getBinding().education.setText(bean.getEr_education());
        holder.getBinding().money.setText(bean.getEr_min_money()+"K-"+bean.getEr_max_money()+"K/月");
        holder.getBinding().address.setText(bean.getEr_cityname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getEr_addtime()) * 1000));
        Glide.with(holder.mContext).load(ApiClient.getEnterPriseImage(bean.getEn_img())).error(R.drawable.account_logo).into(holder.getBinding().companyLogo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

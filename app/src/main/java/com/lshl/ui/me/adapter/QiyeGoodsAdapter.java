package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.CompanySecondBean;
import com.lshl.databinding.ItemLayoutQiyeGoodsBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class QiyeGoodsAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutQiyeGoodsBinding>> {
    private List<CompanySecondBean.InfoBean.GoodBean> mList;

    public QiyeGoodsAdapter(List<CompanySecondBean.InfoBean.GoodBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutQiyeGoodsBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_qiye_goods,parent,false);
        return new BindingViewHolder<>(ItemLayoutQiyeGoodsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutQiyeGoodsBinding> holder, int position) {
        CompanySecondBean.InfoBean.GoodBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getGoodsInfoImage(bean.getGd_img())).error(R.drawable.account_logo).into(holder.getBinding().image);
        holder.getBinding().address.setText(bean.getGd_cityname());
        holder.getBinding().title.setText(bean.getGd_goodname());
        holder.getBinding().price.setText("老乡价: "+bean.getGd_special_offer());
        holder.getBinding().priceLx.setText("市场价: "+bean.getGd_original_price());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

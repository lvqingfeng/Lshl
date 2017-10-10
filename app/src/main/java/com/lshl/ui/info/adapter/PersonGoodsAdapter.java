package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.ItemLayoutPersonGoodsBinding;

import java.util.List;

/***
 * Created by Administrator on 2017/1/14.
 */

public class PersonGoodsAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutPersonGoodsBinding>> {
    private List<PersonBasedataBean.InfoBean.GoodListBean> mList;

    public PersonGoodsAdapter(List<PersonBasedataBean.InfoBean.GoodListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutPersonGoodsBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_person_goods,parent,false);
        return new BindingViewHolder<>(ItemLayoutPersonGoodsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutPersonGoodsBinding> holder, int position) {
        PersonBasedataBean.InfoBean.GoodListBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getGd_goodname());
        holder.getBinding().tvPrice.setText("价格:"+bean.getGd_original_price());
        holder.getBinding().priceLx.setText("老乡价:"+bean.getGd_special_offer());
        Glide.with(holder.mContext).load(ApiClient.getGoodsInfoImage(bean.getImg())).error(R.mipmap.account_logo).into(holder.getBinding().image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

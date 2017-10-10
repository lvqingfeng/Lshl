package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.RankingBean;
import com.lshl.databinding.RecyclerItemRankBinding;
import com.lshl.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class AllRankAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemRankBinding>> {
    private List<RankingBean.InfoBean.ListBean> mList;

    public AllRankAdapter(List<RankingBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecyclerItemRankBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_rank,parent,false);
        return new BindingViewHolder<>(RecyclerItemRankBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemRankBinding> holder, int position) {
        RankingBean.InfoBean.ListBean bean = mList.get(position);
        holder.getBinding().name.setText(bean.getName());
        holder.getBinding().address.setText(bean.getLive_cityname());
        holder.getBinding().money.setText("￥"+bean.getAll_money());
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getImg()).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

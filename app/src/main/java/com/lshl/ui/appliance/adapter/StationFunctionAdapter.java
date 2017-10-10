package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.StationDetailsBean;
import com.lshl.databinding.ItemLayoutStationFunctionBinding;

import java.util.List;

/***
 * Created by Administrator on 2016/11/19.
 */

public class StationFunctionAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutStationFunctionBinding>>{
    private List<StationDetailsBean.InfoBean.ServiceBean> mList;

    public StationFunctionAdapter(List<StationDetailsBean.InfoBean.ServiceBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutStationFunctionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_station_function,parent,false);
        return new BindingViewHolder<>(ItemLayoutStationFunctionBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutStationFunctionBinding> holder, int position) {
        StationDetailsBean.InfoBean.ServiceBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getServiceImage(bean.getSi_img())).into(holder.getBinding().headImage);
        holder.getBinding().name.setText(bean.getSi_title());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

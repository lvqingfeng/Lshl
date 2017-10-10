package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.ServiceStationBean;
import com.lshl.databinding.ItemLayoutStationListBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */

public class StationListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutStationListBinding>> {
    private List<ServiceStationBean.InfoBean.ListBean> mList;

    public StationListAdapter(List<ServiceStationBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutStationListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_station_list,parent,false);
        return new BindingViewHolder<>(ItemLayoutStationListBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutStationListBinding> holder, int position) {
        ServiceStationBean.InfoBean.ListBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getServiceImage(bean.getSs_img())).into(holder.getBinding().headImage);
        holder.getBinding().name.setText(bean.getSs_name());
        holder.getBinding().address.setText(bean.getSs_cityname());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

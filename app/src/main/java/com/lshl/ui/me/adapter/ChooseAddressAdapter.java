package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.request.ChooseAddressBean;
import com.lshl.databinding.ItemLayoutChooseAddressBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ChooseAddressAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutChooseAddressBinding>>{
    private List<ChooseAddressBean.InfoBean> mList;

    public ChooseAddressAdapter(List<ChooseAddressBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutChooseAddressBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_choose_address,parent,false);
        return new BindingViewHolder<>(ItemLayoutChooseAddressBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutChooseAddressBinding> holder, int position) {
        holder.getBinding().address.setText(mList.get(position).getAddress());
        holder.getBinding().describe.setText(mList.get(position).getDescribe());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

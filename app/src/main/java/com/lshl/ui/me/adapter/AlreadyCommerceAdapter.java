package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.AleradyCommerceBean;
import com.lshl.databinding.ItemLayoutAlreadyCommerceBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class AlreadyCommerceAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutAlreadyCommerceBinding>> {
    private List<AleradyCommerceBean.InfoBean.ListBean> mList;

    public AlreadyCommerceAdapter(List<AleradyCommerceBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutAlreadyCommerceBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_already_commerce,parent,false);
        return new BindingViewHolder<>(ItemLayoutAlreadyCommerceBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutAlreadyCommerceBinding> holder, int position) {
        holder.getBinding().setInfo(mList.get(position));
        holder.getBinding().time.setText(DateUtils.getDateToString(Long.decode(mList.get(position).getBus_business_addtime())*1000)+"加入商会");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.ItemLayoutBusinessPostBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public class BusinessPostAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutBusinessPostBinding>> {
    private List<PersonBasedataBean.InfoBean.BusinessInfoBean> mList;

    public BusinessPostAdapter(List<PersonBasedataBean.InfoBean.BusinessInfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutBusinessPostBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_business_post,parent,false);
        return new BindingViewHolder<>(ItemLayoutBusinessPostBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutBusinessPostBinding> holder, int position) {
        PersonBasedataBean.InfoBean.BusinessInfoBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getBusiness_name()+""+bean.getBusiness_position());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

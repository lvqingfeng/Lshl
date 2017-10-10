package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.base.BindingViewHolder;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.databinding.ItemLayoutBusinessPostBinding;
import com.lshl.R;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public class PersonQiyeAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutBusinessPostBinding>>{
    private List<PersonBasedataBean.InfoBean.EnterpriseListBean> mList;

    public PersonQiyeAdapter(List<PersonBasedataBean.InfoBean.EnterpriseListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutBusinessPostBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_business_post,parent,false);
        return new BindingViewHolder<>(ItemLayoutBusinessPostBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutBusinessPostBinding> holder, int position) {
        PersonBasedataBean.InfoBean.EnterpriseListBean bean = mList.get(position);
        holder.getBinding().title.setText(bean.getEn_name());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

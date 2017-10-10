package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.LookHelpListBean;
import com.lshl.databinding.ItemLayoutLookHelpBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * 找帮手的适配器
 * Created by Administrator on 2017/1/10.
 */

public class LookHelpListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutLookHelpBinding>> {
    private List<LookHelpListBean.InfoBean.ListBean> mList;

    public LookHelpListAdapter(List<LookHelpListBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutLookHelpBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_look_help,parent,false);
        return new BindingViewHolder<>(ItemLayoutLookHelpBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutLookHelpBinding> holder, int position) {
        LookHelpListBean.InfoBean.ListBean listBean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getLookHelpImage(listBean.getImage()))
                .error(R.drawable.look_help).into(holder.getBinding().image);
        holder.getBinding().title.setText(listBean.getFh_title());
        holder.getBinding().time.setText("发布时间:"+ DateUtils.getDateToString2(Long.decode(listBean.getFh_addtime())*1000));
        holder.getBinding().address.setText(listBean.getFh_cityname());
        holder.getBinding().name.setText("发布人:【"+listBean.getRealname()+"】");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemMemberBinding;
import com.lshl.bean.MemberBean;
import com.lshl.view.GlideCircleTransform;

import java.util.List;


/**
 * Created by Administrator on 2016/10/24.
 */

public class MemberAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemMemberBinding>> {
    private List<MemberBean.InfoBean.ListBean> mList;

    public MemberAdapter(List<MemberBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }
    @Override
    public BindingViewHolder<ItemMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member,parent,false);
        return new BindingViewHolder<>(ItemMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemMemberBinding> holder, int position) {
        MemberBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvMemberName.setText(bean.getRealname());
            holder.getBinding().tvMemberAddress.setText(bean.getCityname());
            holder.getBinding().tvMemberGrade.setText("会员等级");
            String url= ApiService.BASE_URL+bean.getAvatar();
            Glide.with(holder.mContext).load(url).transform(new GlideCircleTransform(holder.mContext)).into(holder.getBinding().ivMemberHeadimg);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

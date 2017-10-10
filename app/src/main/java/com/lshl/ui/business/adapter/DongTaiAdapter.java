package com.lshl.ui.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.RecyclerItemDongtaiBinding;
import com.lshl.bean.DongTaiBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class DongTaiAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemDongtaiBinding>> {
    private List<DongTaiBean.InfoEntity.ListEntity> mList;

    public DongTaiAdapter(List<DongTaiBean.InfoEntity.ListEntity> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecyclerItemDongtaiBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_dongtai,parent,false);
        return new BindingViewHolder<>(RecyclerItemDongtaiBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemDongtaiBinding> holder, int position) {
        DongTaiBean.InfoEntity.ListEntity entity = mList.get(position);
        if (entity!=null){
            holder.getBinding().tvName.setText(entity.getTitle());
            Glide.with(holder.mContext).load(ApiClient.getBuinessDynamicImage(entity.getImgs())).into(holder.getBinding().ivQyxx);
            String ss=entity.getAdd_time().toString();
            Long date=Long.parseLong(ss)*1000;
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String s = format.format(date);
            holder.getBinding().tvTime.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

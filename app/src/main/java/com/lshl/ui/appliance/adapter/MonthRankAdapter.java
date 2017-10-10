package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.RankBean;
import com.lshl.databinding.RecyclerItemRankBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class MonthRankAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemRankBinding>> {
    private List<RankBean.InfoEntity.ListEntity> mList;

    public MonthRankAdapter(List<RankBean.InfoEntity.ListEntity> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecyclerItemRankBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_rank,parent,false);
        return new BindingViewHolder<>(RecyclerItemRankBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemRankBinding> holder, int position) {
        RankBean.InfoEntity.ListEntity entity = mList.get(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}


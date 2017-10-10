package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.WorkHistoryListBean;
import com.lshl.databinding.WorkHistoryItemBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月22日
 * 时间：15:06
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class WorkHistoryAdapter extends RecyclerView.Adapter<BindingViewHolder<WorkHistoryItemBinding>> {

    private List<WorkHistoryListBean.ListBean> mListData;

    public WorkHistoryAdapter(List<WorkHistoryListBean.ListBean> listData) {
        mListData = listData;
    }

    @Override
    public BindingViewHolder<WorkHistoryItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_work_history, parent, false);
        return new BindingViewHolder<>(WorkHistoryItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<WorkHistoryItemBinding> holder, int position) {
        holder.getBinding().setWorkBean(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

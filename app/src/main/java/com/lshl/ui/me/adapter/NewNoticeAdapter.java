package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.NewNoticeBean;
import com.lshl.databinding.NewNoticeItemBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：12月12日
 * 时间：12:33
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewNoticeAdapter extends RecyclerView.Adapter<BindingViewHolder<NewNoticeItemBinding>> {

    private List<NewNoticeBean.ListBean> mListData;

    public NewNoticeAdapter(List<NewNoticeBean.ListBean> list) {
        mListData = list;
    }

    @Override
    public BindingViewHolder<NewNoticeItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_new_notice, parent, false);
        return new BindingViewHolder<>(NewNoticeItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<NewNoticeItemBinding> holder, int position) {
        holder.getBinding().setNoticeBean(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

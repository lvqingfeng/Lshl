package com.lshl.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月17日
 * 时间：15:37
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public abstract class SimpleBindingAdapter<D extends ViewDataBinding, T> extends RecyclerView.Adapter<BindingViewHolder<D>> {

    private List<T> mListData;
    private int mLayoutRes;

    public SimpleBindingAdapter(List<T> listData, int layoutRes) {
        mLayoutRes = layoutRes;
        mListData = listData;
    }

    @Override
    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(mLayoutRes, parent, false);
        D d = DataBindingUtil.bind(rootView);
        return new BindingViewHolder<>(d);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
        onBindViewHolder(holder.getBinding(), position);

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public abstract void onBindViewHolder(D binding, int position);

    public T getItem(int position) {
        return mListData.get(position);
    }

    public void removItem(int position) {
        mListData.remove(position);
        notifyItemRemoved(position);
    }
}

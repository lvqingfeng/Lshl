package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.GroupTypeResBean;
import com.lshl.databinding.ItemSelectGroupTypeBinding;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月11日
 * 时间：20:39
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SelectGroupTypeAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemSelectGroupTypeBinding>> {

    private List<GroupTypeResBean> mListData;

    public SelectGroupTypeAdapter(List<GroupTypeResBean> listData) {
        mListData = listData;
    }


    @Override
    public BindingViewHolder<ItemSelectGroupTypeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemGroupTypeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_select_group_type, parent, false);
        return new BindingViewHolder<>(ItemSelectGroupTypeBinding.bind(itemGroupTypeView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemSelectGroupTypeBinding> holder, int position) {
        GroupTypeResBean bean = mListData.get(position);
        holder.getBinding().tvGroupTypeName.setText(bean.getGroupName());
        if (bean.isChecked())
            holder.getBinding().ivGroupTypeChecked.setVisibility(View.VISIBLE);
        else
            holder.getBinding().ivGroupTypeChecked.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }
}

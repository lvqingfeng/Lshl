package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.SearchResultBean;
import com.lshl.databinding.ItemLayoutSearchResultBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutSearchResultBinding>>{
    private List<SearchResultBean.InfoBean.ListBean> mList;

    public SearchResultAdapter(List<SearchResultBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutSearchResultBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_search_result,parent,false);
        return new BindingViewHolder<>(ItemLayoutSearchResultBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutSearchResultBinding> holder, int position) {
        SearchResultBean.InfoBean.ListBean listBean = mList.get(position);
        holder.getBinding().money.setText(listBean.getDm_money()+"元");
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(listBean.getDm_addtime())*1000));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

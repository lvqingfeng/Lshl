package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MyCollectionBean;
import com.lshl.databinding.RecyclerItemMycollectionBinding;

import java.util.List;

/***
 * Created by Administrator on 2016/10/31.
 */

public class MyCollectionAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemMycollectionBinding>> {
    private List<MyCollectionBean.InfoBean.ListBean> mList;

    public MyCollectionAdapter(List<MyCollectionBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecyclerItemMycollectionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_mycollection,parent,false);
        return new BindingViewHolder<>(RecyclerItemMycollectionBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemMycollectionBinding> holder, int position) {
        MyCollectionBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().price.setText("￥"+bean.getGd_original_price()+"/");
           holder.getBinding().setGoodsInfo(bean);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

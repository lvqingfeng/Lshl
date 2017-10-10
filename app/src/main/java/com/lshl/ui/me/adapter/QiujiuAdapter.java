package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemShijiuBinding;
import com.lshl.bean.QiuJiuBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class QiujiuAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemShijiuBinding>>{

    private List<QiuJiuBean.InfoBean.ListBean> mList;

    public QiujiuAdapter(List<QiuJiuBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shijiu,parent,false);
        return new BindingViewHolder<>(ItemShijiuBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemShijiuBinding> holder, int position) {
        QiuJiuBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvInfo.setText(bean.getPre_info());
            holder.getBinding().tvAddress.setText(bean.getPre_cityname());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

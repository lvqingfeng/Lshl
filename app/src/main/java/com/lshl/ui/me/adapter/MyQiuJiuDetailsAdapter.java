package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemMyqiujiuMemberBinding;
import com.lshl.bean.QiujiuDetailsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class MyQiuJiuDetailsAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemMyqiujiuMemberBinding>> {
    private List<QiujiuDetailsBean.InfoBean> mList;

    public MyQiuJiuDetailsAdapter(List<QiujiuDetailsBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemMyqiujiuMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myqiujiu_member,parent,false);
        return new BindingViewHolder<>(ItemMyqiujiuMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemMyqiujiuMemberBinding> holder, int position) {
        QiujiuDetailsBean.InfoBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvName.setText(bean.getRealname());
            holder.getBinding().tvInfo.setText(bean.getGre_complaints_info());
            holder.getBinding().btnArrives.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

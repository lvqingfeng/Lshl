package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.FundrecordBean;
import com.lshl.databinding.ItemLayoutFundRecordBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class FundRecordAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutFundRecordBinding>> {
    private List<FundrecordBean.InfoBean.ListBean> mList;

    public FundRecordAdapter(List<FundrecordBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutFundRecordBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_fund_record,parent,false);
        return new BindingViewHolder<>(ItemLayoutFundRecordBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutFundRecordBinding> holder, int position) {
        FundrecordBean.InfoBean.ListBean listBean = mList.get(position);
        holder.getBinding().type.setText(listBean.getType());
        holder.getBinding().money.setText("￥"+listBean.getDm_money());
        holder.getBinding().time.setText(DateUtils.getDateToString(Long.decode(listBean.getDm_addtime())*1000));
        if (listBean.getDm_status().equals("1")){
            holder.getBinding().status.setText("交易成功");
        }else {
            holder.getBinding().status.setText("交易失败");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MutualRepayBean;
import com.lshl.databinding.ItemLayoutMutualRepayBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * Created by Administrator on 2016/12/30.
 */

public class MutualRepaymentAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutMutualRepayBinding>> {
    private List<MutualRepayBean.InfoBean> mList;

    public MutualRepaymentAdapter(List<MutualRepayBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutMutualRepayBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_mutual_repay,parent,false);
        return new BindingViewHolder<>(ItemLayoutMutualRepayBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutMutualRepayBinding> holder, int position) {
        MutualRepayBean.InfoBean infoBean = mList.get(position);
        holder.getBinding().title.setText("项目名称:  "+infoBean.getMa_project_name()+"");
        holder.getBinding().money.setText(infoBean.getMa_project_money());
        holder.getBinding().time.setText(DateUtils.getDateToStringText(Long.decode(infoBean.getMa_repayment_time())*1000));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

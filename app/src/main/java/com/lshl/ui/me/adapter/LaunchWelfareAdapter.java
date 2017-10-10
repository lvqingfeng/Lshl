package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.AppliceWelfareBean;
import com.lshl.databinding.ItemRecyclerWelfareBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class LaunchWelfareAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemRecyclerWelfareBinding>> {
    private List<AppliceWelfareBean.ListBean> mList;

    public LaunchWelfareAdapter(List<AppliceWelfareBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemRecyclerWelfareBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_welfare, parent, false);
        return new BindingViewHolder<>(ItemRecyclerWelfareBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemRecyclerWelfareBinding> holder, int position) {
        AppliceWelfareBean.ListBean bean = mList.get(position);

        holder.getBinding().setBean(bean);
        Glide.with(holder.mContext).load(ApiService.BASE_URL + bean.getImg()).error(R.mipmap.account_logo).into(holder.getBinding().ivInfoImage);
        int nimei = Integer.parseInt(TextUtils.isEmpty(bean.getPw_status())?"0":bean.getPw_status());

        switch (nimei) {
            case 2:
                holder.getBinding().status.setText("待审");
                break;
            case 0:
                holder.getBinding().status.setText("失败");
                break;
            case 99:
                holder.getBinding().status.setText("完成");
                break;
            case 12:
                holder.getBinding().status.setText("初审投票");
                break;
            case 10:
                holder.getBinding().status.setText("初审失败");
                break;
            case 11:
                holder.getBinding().status.setText("初审成功");
                break;
            case 20:
                holder.getBinding().status.setText("复审失败");
                break;
            case 21:
                holder.getBinding().status.setText("复审成功");
                break;
            case 22:
                holder.getBinding().status.setText("复审投票中");
                break;
            case 32:
                holder.getBinding().status.setText("终审投票中");
                break;
            case 31:
                holder.getBinding().status.setText("终审成功");
                break;
            case 30:
                holder.getBinding().status.setText("终审失败");
                break;
            case 42:
                holder.getBinding().status.setText("等待打款");
                break;
            case 41:
                holder.getBinding().status.setText("打款成功");
                break;
            case 52:
                holder.getBinding().status.setText("执行中");
                break;
            case 62:
                holder.getBinding().status.setText("执行回馈");
                break;
            default:
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
}

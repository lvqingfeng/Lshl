package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemRecyclerMyorderBinding;
import com.lshl.bean.MyOrderBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemRecyclerMyorderBinding>> {
    private List<MyOrderBean.InfoBean.ListBean> mList;

    public MyOrderAdapter(List<MyOrderBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemRecyclerMyorderBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_myorder,parent,false);
        return new BindingViewHolder<>(ItemRecyclerMyorderBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemRecyclerMyorderBinding> holder, int position) {
        MyOrderBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvName.setText(bean.getGd_goodname());
            String str="￥"+bean.getGd_original_price()+"/<font color='#CD6600'>￥"+bean.getGd_special_offer()+"</font>";
            holder.getBinding().tvPrice.setText(Html.fromHtml(str));
            holder.getBinding().tvNums.setText(bean.getGo_num());
            Long timeLong=Long.decode(bean.getGo_addtime())*1000;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String s = format.format(timeLong);
            holder.getBinding().tvTime.setText(s);
            Glide.with(holder.mContext).load(ApiClient.getGoodsInfoImage(bean.getGd_img1())).into(holder.getBinding().ivImgs);
            String status = bean.getGo_status();
            if ("1".equals(status)){

            }else if ("2".equals(status)){
                holder.getBinding().btnReserve.setText("卖家回绝");
            }else if ("0".equals(status)){
                holder.getBinding().btnReserve.setText("订单已取消");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

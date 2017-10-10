package com.lshl.ui.me.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MyGoodsBean;
import com.lshl.bean.MyProductBean;
import com.lshl.databinding.RecyclerItemMygoodsBinding;
import com.lshl.ui.me.activity.GoodUnLookActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class MyProductAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemMygoodsBinding>> {
    private List<MyProductBean.InfoBean.ListBean> mList;
    private Activity mActivity;

    public MyProductAdapter(List<MyProductBean.InfoBean.ListBean> mList, Activity mActivity) {
        this.mList = mList;
        this.mActivity = mActivity;
    }

    @Override
    public BindingViewHolder<RecyclerItemMygoodsBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_mygoods, parent, false);
        return new BindingViewHolder<>(RecyclerItemMygoodsBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemMygoodsBinding> holder, int position) {
        final MyProductBean.InfoBean.ListBean bean = mList.get(position);
        if (bean != null) {
            holder.getBinding().tvName.setText(bean.getGd_goodname());
            // holder.getBinding().tvNum.setText("订单量(" + bean.getGd_orders() + ")");
            String str = "￥" + bean.getGd_original_price() +"\n\r"+ "<font color='#CD6600'>￥" + bean.getGd_special_offer() + "</font>";
            holder.getBinding().tvPrice.setText("市场价￥" +bean.getGd_original_price());
            holder.getBinding().tvPrice2.setText("老乡价￥" +bean.getGd_special_offer());
            Glide.with(holder.mContext).load(ApiClient.getGoodsInfoImage(bean.getImg())).into(holder.getBinding().ivGoodimg);
            holder.getBinding().lookNum.setText("浏览量:"+bean.getGd_nums());
            holder.getBinding().collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodUnLookActivity.actionStart(mActivity,bean.getGd_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

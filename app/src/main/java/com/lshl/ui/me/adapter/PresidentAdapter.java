package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.PresidentListBean;
import com.lshl.databinding.ItemLayoutPresidentBinding;

import java.text.SimpleDateFormat;
import java.util.List;

/***
 * Created by 张三 on 2016/12/7.
 */

public class PresidentAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutPresidentBinding>> {
    private List<PresidentListBean.InfoBean.ListBean> mList;

    public PresidentAdapter(List<PresidentListBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutPresidentBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_president,parent,false);
        return new BindingViewHolder<>(ItemLayoutPresidentBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutPresidentBinding> holder, int position) {
        PresidentListBean.InfoBean.ListBean bean = mList.get(position);
        holder.getBinding().info.setText(bean.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String format1 = format.format(Long.decode(bean.getAdd_time())*1000);
        holder.getBinding().time.setText(format1);
        if (TextUtils.isEmpty(bean.getImgs())){
            holder.getBinding().image.setVisibility(View.GONE);
        }else {
            holder.getBinding().image.setVisibility(View.VISIBLE);
            Glide.with(holder.mContext).load(ApiClient.getPresident(bean.getImgs())).into(holder.getBinding().image);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

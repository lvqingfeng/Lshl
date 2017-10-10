package com.lshl.ui.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.CityQiyeBean;
import com.lshl.databinding.LvitemQiyeListBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class QiYeAdapter extends RecyclerView.Adapter<BindingViewHolder<LvitemQiyeListBinding>> {
    private List<CityQiyeBean.ListBean> mList;

    public QiYeAdapter(List<CityQiyeBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<LvitemQiyeListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lvitem_qiye_list, parent, false);
        return new BindingViewHolder<>(LvitemQiyeListBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<LvitemQiyeListBinding> holder, int position) {
        CityQiyeBean.ListBean entity = mList.get(position);
        if (entity != null) {
            holder.getBinding().setListBean(entity);
            Glide.with(holder.mContext).load(ApiClient.getEnterPriseImage(entity.getImg())).into(holder.getBinding().ivQiyeitemImg);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

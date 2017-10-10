package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.RadarListBean;
import com.lshl.databinding.ItemLayoutRadarListBinding;
import com.lshl.view.GlideRoundTransform;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Administrator on 2016/12/22.
 */

public class RadarListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutRadarListBinding>> {
    private List<RadarListBean.InfoBean.ListBean> mList;

    public RadarListAdapter(List<RadarListBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutRadarListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_radar_list,parent,false);
        return new BindingViewHolder<>(ItemLayoutRadarListBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutRadarListBinding> holder, int position) {
        RadarListBean.InfoBean.ListBean bean = mList.get(position);
        holder.getBinding().name.setText(bean.getRealname());
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String format1 = format.format(Long.decode(bean.getPi_addtime()) * 1000);
        holder.getBinding().time.setText(format1);
        holder.getBinding().info.setText(bean.getPi_title());
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).error(R.mipmap.account_logo).transform(new GlideRoundTransform(holder.mContext)).into(holder.getBinding().headImage);
        Constant.getGradeForAll(bean.getGrade(),holder.getBinding().grade);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

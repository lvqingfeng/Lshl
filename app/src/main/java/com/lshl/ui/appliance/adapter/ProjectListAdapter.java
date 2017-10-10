package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.ProjectBean;
import com.lshl.databinding.ItemLayoutProjectBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutProjectBinding>>{
    private List<ProjectBean.InfoBean.ListBean> mList;

    public ProjectListAdapter(List<ProjectBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutProjectBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_project,parent,false);
        return new BindingViewHolder<>(ItemLayoutProjectBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutProjectBinding> holder, int position) {
        ProjectBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            Glide.with(holder.mContext).load(ApiClient.getProjectImage(bean.getImg())).into(holder.getBinding().ivImage);
            String time= DateUtils.getDateToString2(Long.decode(bean.getPp_addtime())*1000);
            String status=bean.getPp_status();
            if ("2".equals(status)){
                holder.getBinding().tvInfo.setText(bean.getPp_title()+"\n地址:"+bean.getPp_address()+"\n时间:"+time+"\n状态:审核中");
            }else {
                holder.getBinding().tvInfo.setText(bean.getPp_title()+"\n地址:"+bean.getPp_address()+"\n时间:"+time);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

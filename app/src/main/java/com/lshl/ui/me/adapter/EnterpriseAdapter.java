package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.EnterpeiseBean;
import com.lshl.databinding.ItemLayoutEnterpriseBinding;
import com.lshl.ui.me.activity.MyProductActivity;
import com.lshl.ui.me.activity.MyRecruitActivity;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * Created by Administrator on 2016/11/12.
 */

public class EnterpriseAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutEnterpriseBinding>> {
    private List<EnterpeiseBean.InfoBean> mList;
    private BaseActivity activity;

    public EnterpriseAdapter(List<EnterpeiseBean.InfoBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
    }


    @Override
    public BindingViewHolder<ItemLayoutEnterpriseBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_enterprise,parent,false);
        return new BindingViewHolder<>(ItemLayoutEnterpriseBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutEnterpriseBinding> holder, int position) {
        final EnterpeiseBean.InfoBean bean = mList.get(position);
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getEn_addtime())*1000));
        holder.getBinding().tvName.setText(bean.getEn_name());
        if (bean.getEn_status().equals("2")){
            holder.getBinding().status.setText("审核状态:待审核");
            holder.getBinding().llButton.setVisibility(View.GONE);
        }else {
            holder.getBinding().status.setText("已通过");
            holder.getBinding().llButton.setVisibility(View.GONE);
        }
        Glide.with(holder.mContext).load(ApiClient.getEnterPriseImage(bean.getImg())).into(holder.getBinding().ivQyxx);
        holder.getBinding().recruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRecruitActivity.actionStart(activity,bean.getEn_id());
            }
        });
        holder.getBinding().goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProductActivity.actionStart(activity,bean.getEn_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

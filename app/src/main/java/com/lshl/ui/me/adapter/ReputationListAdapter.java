package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.ReputationBean;
import com.lshl.databinding.ItemLayoutKoubeiBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月14日
 * 时间：12:10
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ReputationListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutKoubeiBinding>> {
    private List<ReputationBean.ListBean> mListData;

    public ReputationListAdapter(List<ReputationBean.ListBean> mListData) {
        this.mListData = mListData;
    }

    @Override
    public BindingViewHolder<ItemLayoutKoubeiBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_koubei,parent,false);
        return new BindingViewHolder<>(ItemLayoutKoubeiBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutKoubeiBinding> holder, int position) {
        ReputationBean.ListBean bean = mListData.get(position);
        holder.getBinding().title.setText(bean.getS_title());
        holder.getBinding().info.setText(bean.getS_info());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getS_addtime())*1000));
        holder.getBinding().name.setText("发起人:"+bean.getRealname());
        if (bean.getS_type().equals("1")){
            holder.getBinding().image.setImageResource(R.mipmap.hongbang);
        }else {
            holder.getBinding().image.setImageResource(R.mipmap.heibang);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}

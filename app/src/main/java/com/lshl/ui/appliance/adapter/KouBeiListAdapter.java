package com.lshl.ui.appliance.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.KouBeiListBean;
import com.lshl.databinding.ItemLayoutKoubeiBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class KouBeiListAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutKoubeiBinding>> {
    private Activity activity;

    private List<KouBeiListBean.InfoBean.ListBean> mList;

    public KouBeiListAdapter(Activity activity, List<KouBeiListBean.InfoBean.ListBean> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutKoubeiBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_koubei,parent,false);
        return new BindingViewHolder<>(ItemLayoutKoubeiBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutKoubeiBinding> holder, int position) {
        KouBeiListBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().name.setText("发起人:"+bean.getRealname());
            holder.getBinding().info.setText(bean.getS_info());
            holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getS_addtime())*1000));
            holder.getBinding().title.setText(bean.getS_title());
            if (bean.getS_type().equals("2")){
                holder.getBinding().image.setImageResource(R.mipmap.heibang);
            }else {
                holder.getBinding().image.setImageResource(R.mipmap.hongbang);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

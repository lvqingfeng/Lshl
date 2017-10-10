package com.lshl.ui.appliance.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.YingyongJuanKuanBean;
import com.lshl.databinding.RecyclerItemJuankuanBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DateUtils;
import com.lshl.view.GlideCircleTransform;

import java.util.List;

/***
 * Created by 李四 on 2016/11/3.
 */

public class JuanKuanListAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemJuankuanBinding>> {
    private List<YingyongJuanKuanBean.InfoBean.ListBean> mList;
    private Activity activity;

    public JuanKuanListAdapter(List<YingyongJuanKuanBean.InfoBean.ListBean> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }
    @Override
    public BindingViewHolder<RecyclerItemJuankuanBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_juankuan,parent,false);
        return new BindingViewHolder<>(RecyclerItemJuankuanBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemJuankuanBinding> holder,int position) {
        final int fPosition=position;
        YingyongJuanKuanBean.InfoBean.ListBean entity = mList.get(fPosition);
        holder.getBinding().name.setText(entity.getName());
        holder.getBinding().address.setText(entity.getCityname());
        holder.getBinding().money.setText("￥"+entity.getDm_money());
        holder.getBinding().time.setText(DateUtils.getDateToString(Long.decode(entity.getDm_addtime())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+entity.getImg()).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

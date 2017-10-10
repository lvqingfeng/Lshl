package com.lshl.ui.appliance.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.KouBeiCommmentBean;
import com.lshl.databinding.RecyclerItemGoodscommentBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DateUtils;
import com.lshl.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class KouBeiCommentAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemGoodscommentBinding>> {
    private Activity activity;
    private List<KouBeiCommmentBean.InfoBean.ListBean> mList;

    public KouBeiCommentAdapter(Activity activity, List<KouBeiCommmentBean.InfoBean.ListBean> mList) {
        this.activity = activity;
        this.mList = mList;
    }


    @Override
    public BindingViewHolder<RecyclerItemGoodscommentBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_goodscomment,parent,false);
        return new BindingViewHolder<>(RecyclerItemGoodscommentBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<RecyclerItemGoodscommentBinding> holder, int position) {
        final KouBeiCommmentBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvName.setText(bean.getSc_active_realname());
            holder.getBinding().tvTime.setText(bean.getSc_active_cityname());
            Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getSc_active_avatar()))
                    .error(R.mipmap.account_logo).into(holder.getBinding().ivHeadimg);
            holder.getBinding().tvGrade.setText(DateUtils.getDateToString2(Long.decode(bean.getSc_addtime())*1000));
            try {
                if (!bean.getSc_passive_uid().equals("0")){
                    holder.getBinding().tvInfo.setText("回复"+bean.getSc_passive_realname()+":"+ bean.getSc_info());
                }else {
                    holder.getBinding().tvInfo.setText(bean.getSc_info());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            holder.getBinding().ivHeadimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bean.getSc_active_realname().equals("匿名评论")){
                        HxMemberDetailsActivity.actionStart(activity,"",bean.getSc_active_uid(),false);
                    }else {
                        Toast.makeText(holder.mContext,"无法查看",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

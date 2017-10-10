package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.RecyclerItemGoodscommentBinding;
import com.lshl.bean.GoodsCommentBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */

public class GoodsCommentAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemGoodscommentBinding>> {
    private List<GoodsCommentBean.InfoBean.ListBean> mList;

    public GoodsCommentAdapter(List<GoodsCommentBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<RecyclerItemGoodscommentBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_goodscomment,parent,false);
        return new BindingViewHolder<>(RecyclerItemGoodscommentBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RecyclerItemGoodscommentBinding> holder, int position) {
        GoodsCommentBean.InfoBean.ListBean bean = mList.get(position);
        if (bean!=null){
            holder.getBinding().tvName.setText(bean.getGc_buy_realname());
            holder.getBinding().tvInfo.setText(bean.getGc_comment_info());
            Long ss=Long.decode(bean.getGc_addtime())*1000;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = format.format(ss);
            holder.getBinding().tvTime.setText(time);
            Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getGc_buy_avatar())).into(holder.getBinding().ivHeadimg);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

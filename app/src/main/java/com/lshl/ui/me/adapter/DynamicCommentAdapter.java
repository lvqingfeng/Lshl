package com.lshl.ui.me.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.DynamicCommentBean;
import com.lshl.databinding.ItemLayoutDynamicCommentBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DateUtils;
import com.lshl.view.GlideCircleTransform;

import java.util.List;

/***
 * Created by 张三 on 2016/11/28.
 * 动态评论的适配器
 */

public class DynamicCommentAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutDynamicCommentBinding>> {
    private List<DynamicCommentBean.InfoBean.ListBean> mList;
    private Activity activity;
    public DynamicCommentAdapter(List<DynamicCommentBean.InfoBean.ListBean> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<ItemLayoutDynamicCommentBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dynamic_comment,parent,false);
        return new BindingViewHolder<>(ItemLayoutDynamicCommentBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutDynamicCommentBinding> holder, final int position) {
        DynamicCommentBean.InfoBean.ListBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getPassive_realname())){
            holder.getBinding().info.setText("回复"+bean.getPassive_realname()+":"+bean.getCo_comment_info());
        }else {
            holder.getBinding().info.setText(bean.getCo_comment_info());
        }
        holder.getBinding().name.setText(bean.getActive_realname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getCo_addtime())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getActive_avatar()).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mList.get(position).getCo_active_member().equals("0")){
                    HxMemberDetailsActivity.actionStart(activity,"",mList.get(position).getCo_active_member(),false);
                }else if (!mList.get(position).getCo_passive_member().equals("0")){
                    HxMemberDetailsActivity.actionStart(activity,"",mList.get(position).getCo_passive_member(),false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

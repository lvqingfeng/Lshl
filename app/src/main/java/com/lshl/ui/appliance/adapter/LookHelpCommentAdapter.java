package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.LookHelpCommentBean;
import com.lshl.databinding.LookHelpComment;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * Created by Administrator on 2017/1/11.
 */

public class LookHelpCommentAdapter extends RecyclerView.Adapter<BindingViewHolder<LookHelpComment>> {
    private List<LookHelpCommentBean.InfoBean.ListBean> mList;
    private BaseActivity activity;

    public LookHelpCommentAdapter(List<LookHelpCommentBean.InfoBean.ListBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<LookHelpComment> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_look_help_comment,parent,false);
        return new BindingViewHolder<>(LookHelpComment.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<LookHelpComment> holder, int position) {
        String uid;
        final LookHelpCommentBean.InfoBean.ListBean bean = mList.get(position);
        holder.getBinding().comment.setText(bean.getFhc_question_info());
        holder.getBinding().name.setText(bean.getRealname());
        holder.getBinding().address.setText(DateUtils.getDateToString2(Long.decode(bean.getFhc_question_time())*1000));
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).error(R.drawable.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getFhc_question_time())));
        uid=bean.getFhc_question_uid();
        if (!TextUtils.isEmpty(bean.getFhc_answer_info())){
            holder.getBinding().answer.setText("回复"+bean.getRealname()+":"+bean.getFhc_answer_info());
            uid=bean.getFhc_answer_uid();
        }else {
            holder.getBinding().answer.setVisibility(View.GONE);
        }
        final String finalUid = uid;
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HxMemberDetailsActivity.actionStart(activity,"", finalUid,false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

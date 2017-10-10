package com.lshl.ui.appliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.ProjectQuestionBean;
import com.lshl.databinding.ItemLayoutProjectQuestionBinding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ProjectQuestionAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutProjectQuestionBinding>> {
    private List<ProjectQuestionBean.InfoBean.ListBean> mList;
    public ProjectQuestionAdapter(List<ProjectQuestionBean.InfoBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutProjectQuestionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_project_question,parent,false);
        return new BindingViewHolder<>(ItemLayoutProjectQuestionBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutProjectQuestionBinding> holder, int position) {
        ProjectQuestionBean.InfoBean.ListBean bean = mList.get(position);
        if (!TextUtils.isEmpty(bean.getPc_answer_info())){
            String ss="回复内容:    "+bean.getPc_answer_info();
            holder.getBinding().tvAnswer.setText(ss);
            holder.getBinding().name.setText("咨询人:    "+bean.getRealname());
            holder.getBinding().tvQuestion.setText("咨询内容:    "+bean.getPc_question_info());
            holder.getBinding().tvAnswer.setVisibility(View.VISIBLE);
        }else {
            holder.getBinding().name.setText("咨询人:    "+bean.getRealname());
            holder.getBinding().tvQuestion.setText("咨询内容:    "+bean.getPc_question_info());
            holder.getBinding().tvAnswer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.lshl.ui.dscs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.databinding.ProjectGrantMontyItemBinding;
import com.lshl.databinding.ProjectInfoItemBinding;
import com.lshl.databinding.ProjectPromptMessageItemBinding;
import com.lshl.databinding.ProjectVoteTypeBinding;

/**
 * 作者：吕振鹏
 * 创建时间：11月07日
 * 时间：22:24
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ProjectStatusAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private static final int TYPE_INFO = 0;
    private static final int TYPE_VOTE_ONE = 1;
    private static final int TYPE_VOTE_TWO = 2;
    private static final int TYPE_VOTE_THREE = 3;
    private static final int TYPE_GRANT_MONEY = 4;
    private static final int TYPE_EXECUTE = 5;//

    private DscsProjectDetailsBean mDetailsBean;
    private int mItemCount;
    private int mFromWhere;

    public ProjectStatusAdapter(DscsProjectDetailsBean bean, int itemCount, int fromWhere) {
        mDetailsBean = bean;
        mItemCount = itemCount;
        mFromWhere = fromWhere;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_INFO;
            case 1:
                return TYPE_VOTE_ONE;
            case 2:
                return TYPE_VOTE_TWO;
            case 3:
                return TYPE_VOTE_THREE;
            case 4:
                return TYPE_GRANT_MONEY;
            case 5:
                return TYPE_EXECUTE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_INFO:
                View infoView = inflater.inflate(R.layout.item_layout_project_info_type, parent, false);
                return new ProjectInfoType(infoView);
            case TYPE_VOTE_ONE:
                View voteOneView = inflater.inflate(R.layout.item_layout_vote_info_type, parent, false);
                return new ProjectVoteOneType(voteOneView);
            case TYPE_VOTE_TWO:
                View voteTwoView = inflater.inflate(R.layout.item_layout_vote_info_type, parent, false);
                return new ProjectVoteTwoType(voteTwoView);
            case TYPE_VOTE_THREE:
                View voteThreeView = inflater.inflate(R.layout.item_layout_vote_info_type, parent, false);
                return new ProjectVoteThreeType(voteThreeView);
            case TYPE_GRANT_MONEY:
                View grantMonty = inflater.inflate(R.layout.item_layout_project_grant_monty, parent, false);
                return new ProjectGrantMontyType(grantMonty);
            case TYPE_EXECUTE:
                View executeView = inflater.inflate(R.layout.item_layout_project_prompt_message, parent, false);
                return new ProjectPromptMessageType(executeView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
         /*   case TYPE_INFO:
                ((ProjectInfoItemBinding) holder.getBinding()).setProjectInfo(mDetailsBean.getProjectInfo());
                break;
            case TYPE_VOTE_ONE:
                ProjectVoteTypeBinding firstBinding = (ProjectVoteTypeBinding) holder.getBinding();
                firstBinding.cbProgressBar.setMax(Integer.decode(mDetailsBean.getFistVote().getAllMember()));
                firstBinding.cbProgressBar.setProgress(Integer.decode(mDetailsBean.getFistVote().getSupportNum()));
                firstBinding.setVoteInfo(mDetailsBean.getFistVote());
                break;
            case TYPE_VOTE_TWO:
                ProjectVoteTypeBinding secondBinding = (ProjectVoteTypeBinding) holder.getBinding();
                secondBinding.cbProgressBar.setMax(Integer.decode(mDetailsBean.getSecondVote().getAllMember()));
                secondBinding.cbProgressBar.setProgress(Integer.decode(mDetailsBean.getSecondVote().getSupportNum()));
                secondBinding.setVoteInfo(mDetailsBean.getSecondVote());
                break;
            case TYPE_VOTE_THREE:
                ProjectVoteTypeBinding thirdBinding = (ProjectVoteTypeBinding) holder.getBinding();
                thirdBinding.cbProgressBar.setMax(Integer.decode(mDetailsBean.getThirdVote().getAllMember()));
                thirdBinding.cbProgressBar.setProgress(Integer.decode(mDetailsBean.getThirdVote().getSupportNum()));
                thirdBinding.setVoteInfo(mDetailsBean.getThirdVote());
                break;
            case TYPE_GRANT_MONEY:
                ((ProjectGrantMontyItemBinding) holder.getBinding()).setGrantMoney(mDetailsBean.getProjectInfo().getTargetMoney());
                break;
            case TYPE_EXECUTE:

                ProjectPromptMessageItemBinding promptMessageBinding = (ProjectPromptMessageItemBinding) holder.getBinding();
                if (mFromWhere == ProjectStatusActivity.FROM_MA) {
                    promptMessageBinding.tvProjectStatus.setText("担保人信息");
                    promptMessageBinding.tvProjectMessage.setText("担保人数：" + mDetailsBean.getGuarantee().size() + "人");

                } else if (mFromWhere == ProjectStatusActivity.FROM_MB) {
                    promptMessageBinding.tvProjectStatus.setText("发起人回执");
                    promptMessageBinding.tvProjectMessage.setText("回执详情");
                }
                break;*/
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    private class ProjectInfoType extends BindingViewHolder<ProjectInfoItemBinding> {

        ProjectInfoType(View view) {
            super(ProjectInfoItemBinding.bind(view));
        }
    }

    private class ProjectVoteOneType extends BindingViewHolder<ProjectVoteTypeBinding> {

        ProjectVoteOneType(View view) {
            super(ProjectVoteTypeBinding.bind(view));
        }
    }

    private class ProjectVoteTwoType extends BindingViewHolder<ProjectVoteTypeBinding> {

        ProjectVoteTwoType(View view) {
            super(ProjectVoteTypeBinding.bind(view));
        }
    }

    private class ProjectVoteThreeType extends BindingViewHolder<ProjectVoteTypeBinding> {

        ProjectVoteThreeType(View view) {
            super(ProjectVoteTypeBinding.bind(view));
        }
    }

    private class ProjectGrantMontyType extends BindingViewHolder<ProjectGrantMontyItemBinding> {

        public ProjectGrantMontyType(View view) {
            super(ProjectGrantMontyItemBinding.bind(view));
        }
    }

    private class ProjectPromptMessageType extends BindingViewHolder<ProjectPromptMessageItemBinding> {

        public ProjectPromptMessageType(View view) {
            super(ProjectPromptMessageItemBinding.bind(view));
        }
    }
}

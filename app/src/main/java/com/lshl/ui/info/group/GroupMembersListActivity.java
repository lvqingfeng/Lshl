package com.lshl.ui.info.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.databinding.ActivityGroupMembersListBinding;
import com.lshl.databinding.GroupMembersListItemBinding;
import com.lshl.db.bean.HxUserBean;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 群成员列表
 */
public class GroupMembersListActivity extends BaseActivity<ActivityGroupMembersListBinding> {

    private List<HxUserBean> mListData;

    public static void actionStart(Activity activity, ArrayList<HxUserBean> userList) {
        Intent intent = new Intent(activity, GroupMembersListActivity.class);
        intent.putParcelableArrayListExtra("members", userList);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("群成员", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = getIntent().getParcelableArrayListExtra("members");
    }

    @Override
    protected void initViews() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setAdapter(new SimpleBindingAdapter<GroupMembersListItemBinding, HxUserBean>(mListData, R.layout.item_layout_group_members_list) {

            @Override
            public void onBindViewHolder(GroupMembersListItemBinding binding, int position) {
                if (position == 0) {
                    binding.tvPlace.setVisibility(View.VISIBLE);
                }
                binding.setUserBean(mListData.get(position));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_members_list;
    }
}

package com.lshl.ui.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.databinding.ActivityGuarantorListBinding;
import com.lshl.databinding.RightGoMenuBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 担保人列表
 */
public class GuarantorListActivity extends BaseActivity<ActivityGuarantorListBinding> {

    private List<String> mListData = new ArrayList<>();
    private DscsProjectDetailsBean mProjectDetailsBean;

    public static void actionStart(Activity activity, DscsProjectDetailsBean bean) {
        Intent intent = new Intent(activity, GuarantorListActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("担保人", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mProjectDetailsBean = (DscsProjectDetailsBean) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void initViews() {
/*
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        final List<DscsProjectDetailsBean.GuaranteeBean> guaranteeList = mProjectDetailsBean.getGuarantee();
        for (DscsProjectDetailsBean.GuaranteeBean guaranteeBean : guaranteeList) {
            mListData.add(guaranteeBean.getName());
        }
        RecyclerAdapter adapter = new RecyclerAdapter();
        mDataBinding.recyclerView.setAdapter(adapter);
        Log.d("logssss", "长度为：" + mProjectDetailsBean.getGuarantee().size());
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                loadMemberInfo(vh.getLayoutPosition());
            }
        });
        mDataBinding.tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AgreementListActivity.actionStart(GuarantorListActivity.this, mProjectDetailsBean);
            }
        });*/
    }

    private void loadMemberInfo(int position) {
       /* TaskBase.getMemberBasicInfo(mProjectDetailsBean.getGuarantee().get(position).getUid(), new TaskBase.OnGetDataCallBack<MemberBasicInfoBean>() {
            @Override
            public void onResult(MemberBasicInfoBean basicInfoBean) {
                SellerInfoActivity.actionStart(GuarantorListActivity.this, basicInfoBean, SellerInfoActivity.FROM_GUARANTOR_INFO);
            }

            @Override
            public void onError(String err) {
                showMessage(err);
            }
        });*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guarantor_list;
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder<RightGoMenuBinding>> {

        @Override
        public BindingViewHolder<RightGoMenuBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = getLayoutInflater().inflate(R.layout.item_layout_right_go_menu, parent, false);
            return new BindingViewHolder<>(RightGoMenuBinding.bind(rootView));
        }

        @Override
        public void onBindViewHolder(BindingViewHolder<RightGoMenuBinding> holder, int position) {
            holder.getBinding().tvName.setText(mListData.get(position));
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }
    }

}

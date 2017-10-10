package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.ProjectBean;
import com.lshl.databinding.ActivityProjectBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.ProjectListAdapter;
import com.lshl.ui.me.activity.ReleaseProjectActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends BaseActivity<ActivityProjectBinding>
        implements SpringView.OnFreshListener {

    private List<ProjectBean.InfoBean.ListBean> mList;
    private boolean isEnd;
    private ProjectListAdapter projectListAdapter;
    private ArrayList<String> list;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ProjectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("项目", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
    }

    @Override
    protected void initViews() {
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = new ArrayList<>();
                ReleaseProjectActivity.actionStart(ProjectActivity.this, list, null, null, null, ReleaseProjectActivity.MYPROJECT);
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        projectListAdapter = new ProjectListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(projectListAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String pp_id = mList.get(position).getPp_id();
                ProjectDetailActivity.actionStart(ProjectActivity.this, pp_id, ProjectDetailActivity.FROM_APPLICEN);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateProjectList(String.valueOf(page), null), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ProjectBean>() {
            @Override
            public void onNext(ProjectBean result) {
                if (result != null) {
                    isEnd = result.getStatus() == 1;
                    mList.addAll(result.getInfo().getList());
                    projectListAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ReleaseProjectActivity.MYPROJECT) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        }
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

}

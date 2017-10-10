package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.ProjectBean;
import com.lshl.databinding.ActivityMyProjectBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.appliance.adapter.ProjectListAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyProjectActivity extends BaseActivity<ActivityMyProjectBinding> implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<ProjectBean.InfoBean.ListBean> mList;
    private ArrayList<String> list;
    private ProjectListAdapter projectListAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyProjectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的项目", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ReleaseProjectActivity.MYPROJECT) {
                onRefresh();
            }
        }
    }

    @Override
    protected void initViews() {
        list = new ArrayList<>();
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserIsDues(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        ReleaseProjectActivity.actionStart(MyProjectActivity.this, list, null, null, null, ReleaseProjectActivity.MYPROJECT);
                    }
                });
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        projectListAdapter = new ProjectListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(projectListAdapter);
        setListData(mList);
        loadListData(1);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String pp_id = mList.get(vh.getLayoutPosition()).getPp_id();
                ProjectDetailActivity.actionStart(MyProjectActivity.this, pp_id, ProjectDetailActivity.FROM_PERSON);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "确定要删除这个项目?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).projectDelete(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getPp_id()), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    onRefresh();
                                } else {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
                    }
                });
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateProjectList(String.valueOf(page), LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<ProjectBean>(mContext, new SubscriberOnNextListener<ProjectBean>() {
            @Override
            public void onNext(ProjectBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    projectListAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() <= 0) {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    } else {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    }
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_project;
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

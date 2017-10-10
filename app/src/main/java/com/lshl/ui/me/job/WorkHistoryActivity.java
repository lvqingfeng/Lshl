package com.lshl.ui.me.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.lshl.bean.WorkHistoryListBean;
import com.lshl.databinding.ActivityWorkHistoryBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.adapter.WorkHistoryAdapter;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class WorkHistoryActivity extends BaseActivity<ActivityWorkHistoryBinding>
        implements SpringView.OnFreshListener {

    private static final int REQUEST_CODE_ADD_WORK_HISTORY = 0x000123;
    private static final int REQUEST_CODE_EDIT_WORK_HISTORY = 0x000124;

    private boolean isEnd;
    private WorkHistoryAdapter mAdapter;

    private String mUid;

    private List<WorkHistoryListBean.ListBean> mListData;

    private int mEditPosioton;

    private boolean isBrowse;

    public static void actionStart(Activity activity, String uid) {
        Intent intent = new Intent(activity, WorkHistoryActivity.class);
        intent.putExtra("uid", uid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initFieldBeforeMethods() {
        mUid = getIntent().getStringExtra("uid");
        mListData = new ArrayList<>();
        setListData(mListData);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("工作经历", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        CheckBox rightView = getRightView();
        rightView.setButtonDrawable(R.drawable.ic_vector_add);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateWorkHistoryActivity.actionStart(WorkHistoryActivity.this, REQUEST_CODE_ADD_WORK_HISTORY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADD_WORK_HISTORY:
                if (resultCode == RESULT_OK) {
                    onRefresh();
//                    if (data != null) {
//                        WorkHistoryListBean.ListBean bean = (WorkHistoryListBean.ListBean) data.getSerializableExtra("result_bean");
//                        mListData.add(bean);
//                        mAdapter.notifyItemInserted(0);
//                    }
                }
                break;
            case REQUEST_CODE_EDIT_WORK_HISTORY:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mListData.remove(mEditPosioton);
                        WorkHistoryListBean.ListBean bean = (WorkHistoryListBean.ListBean) data.getSerializableExtra("result_bean");
                        mListData.add(mEditPosioton, bean);
                        mAdapter.notifyItemChanged(mEditPosioton);
                    }
                }
                break;
        }
    }

    @Override
    protected void initViews() {

        if (!mUid.equals(LoginHelper.getInstance().getUserBean().getUid())) {
            isBrowse = true;
            getRightView().setVisibility(View.GONE);
        }

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WorkHistoryAdapter(mListData);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mEditPosioton = vh.getLayoutPosition();
                String workId = mListData.get(vh.getLayoutPosition()).getWe_id();
                CreateWorkHistoryActivity.actionStart(WorkHistoryActivity.this, isBrowse, workId, REQUEST_CODE_EDIT_WORK_HISTORY);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final String we_id = mListData.get(vh.getLayoutPosition()).getWe_id();
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条信息", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                .deleteJob(LoginHelper.getInstance().getUserToken(), we_id), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).workInfoList(mUid, String.valueOf(page))
                , new Subscriber<HttpResult<WorkHistoryListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<WorkHistoryListBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                    }
                }
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_history;
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

package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.lshl.bean.RecruitBean;
import com.lshl.databinding.ActivityMyRecruit2Binding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.job.EnterpriseRecruitActivity;
import com.lshl.ui.appliance.job.RecruitDetailsActivity;
import com.lshl.ui.me.adapter.MyRecruitAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class MyRecruitActivity extends BaseActivity<ActivityMyRecruit2Binding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String enid;
    private static final int REQUEST_CODE = 0x000123;
    private List<RecruitBean.InfoBean.ListBean> mList;
    private MyRecruitAdapter myRecruitAdapter;

    public static void actionStart(Activity activity, String enid) {
        Intent intent = new Intent(activity, MyRecruitActivity.class);
        intent.putExtra("enid", enid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的招聘", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        enid = getIntent().getStringExtra("enid");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterpriseRecruitActivity.actionStart(MyRecruitActivity.this, enid, REQUEST_CODE);
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        myRecruitAdapter = new MyRecruitAdapter(mList);
        mDataBinding.recyclerView.setAdapter(myRecruitAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                RecruitDetailsActivity.actionStart(MyRecruitActivity.this, mList.get(vh.getLayoutPosition()).getEr_id(), RecruitDetailsActivity.FROM_ME);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此内容?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                                , RetrofitManager.RetrofitType.Object)
                                        .deleteRecruit(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getEr_id())
                                , new Subscriber<HttpResult<String>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(HttpResult<String> stringHttpResult) {
                                        if (stringHttpResult.getStatus().equals(ApiService.STATUS_SUC)) {
                                            Toast.makeText(mContext, stringHttpResult.getInfo(), Toast.LENGTH_SHORT).show();
                                            onRefresh();
                                        } else {
                                            Toast.makeText(mContext, stringHttpResult.getInfo(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updateRrcruitList(enid, "", "", String.valueOf(page)), new ProgressSubscriber<RecruitBean>(mContext, new SubscriberOnNextListener<RecruitBean>() {
            @Override
            public void onNext(RecruitBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    myRecruitAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
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
        return R.layout.activity_my_recruit2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        }
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

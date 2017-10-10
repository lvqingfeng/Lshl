package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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
import com.lshl.bean.ProjectQuestionBean;
import com.lshl.bean.ResultInfoBean;
import com.lshl.databinding.ActivityProjectQuestionBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.adapter.ProjectQuestionAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProjectQuestionActivity extends BaseActivity<ActivityProjectQuestionBinding>
        implements SpringView.OnFreshListener {
    private List<ProjectQuestionBean.InfoBean.ListBean> mList;
    private String name;
    private String pid;
    private boolean isEnd;
    private String puid;
    private String pc_id;
    private String answer_uid;
    private ProjectQuestionAdapter projectQuestionAdapter;
    private SendOpinionPopWindow mPopWindow;
    private SendOpinionPopWindow sendOpinionPopWindow;

    public static void actionStart(Activity activity, String name, String pid, String puid) {
        Intent intent = new Intent(activity, ProjectQuestionActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("pid", pid);
        intent.putExtra("puid", puid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("问答列表", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("咨询");
    }

    @Override
    protected void initViews() {
        mPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.Object).projectQuestion(LoginHelper.getInstance().getUserToken(),
                        pid, inputText, puid),
                        new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                            @Override
                            public void onNext(ResultInfoBean result) {
                                if (result != null) {
                                    if (result.getStatus() == 1) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        onRefresh();
                                    }
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mPopWindow.setAnonymousGone();
        sendOpinionPopWindow = new SendOpinionPopWindow(this
                , new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                        , RetrofitManager.RetrofitType.Object)
                        .projectAnswer(LoginHelper.getInstance().getUserToken(), pc_id, inputText), new ProgressSubscriber<ResultInfoBean>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                    @Override
                    public void onNext(ResultInfoBean result) {
                        if (result != null) {
                            if (result.getStatus() == 1) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                onRefresh();
                            } else {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }));
            }
        });
        sendOpinionPopWindow.setAnonymousGone();
        sendOpinionPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        name = getIntent().getStringExtra("name");
        pid = getIntent().getStringExtra("pid");
        puid = getIntent().getStringExtra("puid");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().getUserBean().getUid().equals(puid)) {
                    lightOff();
                    mPopWindow.showPopupWindow(mDataBinding.getRoot());
                } else {
                    showMessage("不能咨询自己");
                }
            }
        });

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        projectQuestionAdapter = new ProjectQuestionAdapter(mList);
        mDataBinding.recyclerView.setAdapter(projectQuestionAdapter);
        initLoadData();
        loadDeleteQuestion();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                pc_id = mList.get(position).getPc_id();
                String info = mList.get(position).getPc_answer_info();
                answer_uid = mList.get(position).getPc_answer_uid();
                if (LoginHelper.getInstance().getUserBean().getUid().equals(answer_uid)) {
                    if (TextUtils.isEmpty(info)) {
                        lightOff();
                        sendOpinionPopWindow.showPopupWindow(mDataBinding.getRoot());
                    } else {
                        showMessage("已回复");
                    }
                } else {
                    showMessage("不能回复");
                }
            }
        });

    }

    private void loadDeleteQuestion() {
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                if (LoginHelper.getInstance().getUserBean().getUid().equals(mList.get(vh.getLayoutPosition()).getPc_question_uid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "确定删除咨询内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).projectQuectionDelete(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getPc_id()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                        onRefresh();
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }));
                        }
                    });
                } else if (LoginHelper.getInstance().getUserBean().getUid().equals(mList.get(vh.getLayoutPosition()).getPc_answer_uid())) {
                    if (!TextUtils.isEmpty(mList.get(vh.getLayoutPosition()).getPc_answer_info())) {
                        DialogUtils.alertDialog(mContext, "温馨提示", "确定删除回复内容?", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).projectAnswerDelete(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getPc_id()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                            onRefresh();
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }));
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateProjectQuestionList(pid, page + "'"), new ProgressSubscriber<ProjectQuestionBean>(mContext, new SubscriberOnNextListener<ProjectQuestionBean>() {
            @Override
            public void onNext(ProjectQuestionBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    projectQuestionAdapter.notifyDataSetChanged();
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
    protected int getLayoutId() {
        return R.layout.activity_project_question;
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

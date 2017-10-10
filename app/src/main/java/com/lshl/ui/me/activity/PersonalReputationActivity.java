package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.Constant;
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
import com.lshl.bean.ReputationBean;
import com.lshl.databinding.ActivityPersonalReputationBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.me.adapter.ReputationListAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

/**
 * 个人心中的我的口碑
 */
public class PersonalReputationActivity extends BaseActivity<ActivityPersonalReputationBinding>
        implements SpringView.OnFreshListener {
    /**
     * 发布红榜的请求码
     */
    private static final int RELEASE_RED_REQUEST_CODE = 0x0000132;
    /**
     * 发布黑榜的请求码
     */
    private static final int RELEASE_BLACK_REQUEST_CODE = 0x0000133;
    private boolean isEnd;
    private List<ReputationBean.ListBean> mListData;
    private ReputationListAdapter mAdapter;
    private AddPopWindow mReputationTypePopWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PersonalReputationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RELEASE_RED_REQUEST_CODE:
                    onRefresh();
                    break;
                case RELEASE_BLACK_REQUEST_CODE:
                    onRefresh();
                    break;
            }
        }
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("我的口碑", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        CheckBox rightView = getRightView();
        rightView.setButtonDrawable(R.drawable.ic_vector_add);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserIsDues(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        lightOff();
                        mReputationTypePopWindow.showPopupWindow(mDataBinding.getRoot());
                    }
                });
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {
        mReputationTypePopWindow = new AddPopWindow(this, R.layout.layout_pop_select_reputation_type);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mListData = new ArrayList<>();
        setListData(mListData);
        mAdapter = new ReputationListAdapter(mListData);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        loadListData(1);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                KouBeiDetailsActivity.actionStart(PersonalReputationActivity.this, mListData.get(vh.getLayoutPosition()).getS_id(), KouBeiDetailsActivity.FROM_PERSON);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确认要删除此条信息？", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        String s_id = mListData.get(vh.getLayoutPosition()).getS_id();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                                RetrofitManager.RetrofitType.String).deleteKoubei(LoginHelper.getInstance().getUserToken(), s_id),
                                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                                    @Override
                                    public void onNext(ResponseBody result) {
                                        try {
                                            String string = result.string();
                                            JSONObject object = JSON.parseObject(string);
                                            String status = object.getString("status");
                                            if (ApiService.STATUS_SUC.equals(status)) {
                                                onRefresh();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }));
                    }
                });
            }
        });
        initPopWindow();
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).personalReputationList(LoginHelper.getInstance().getUserToken(), String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<ReputationBean>>() {
                    @Override
                    public void onNext(HttpResult<ReputationBean> result) {
                        if (result != null) {
                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            isEnd = result.getInfo().getEnd() == 1;
                            mListData.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                })
        );
    }

    private void initPopWindow() {
        View rootView = mReputationTypePopWindow.getWindowRootView();
        Button redPut = (Button) rootView.findViewById(R.id.btn_put_red_reputation);
        Button blackPut = (Button) rootView.findViewById(R.id.btn_put_black_reputation);

        redPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseReputationActivity.actionStart(PersonalReputationActivity.this, Constant.ReputationType.RED, RELEASE_RED_REQUEST_CODE);
                mReputationTypePopWindow.closePopupWindow();
            }
        });
        blackPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseReputationActivity.actionStart(PersonalReputationActivity.this, Constant.ReputationType.BLACK, RELEASE_BLACK_REQUEST_CODE);
                mReputationTypePopWindow.closePopupWindow();
            }
        });
        mReputationTypePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_reputation;
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

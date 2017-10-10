package com.lshl.ui.appliance.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

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
import com.lshl.bean.RecruitBean;
import com.lshl.databinding.ActivityRecruitBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.activity.EnterpriseActivity;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 招贤纳士
 */
public class RecruitActivity extends BaseActivity<ActivityRecruitBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<RecruitBean.InfoBean.ListBean> mList;
    private RecruitAdapter recruitAdapter;
    private String searchText = "";
    private String addressNo = "0";
    private Constant.JobListType mJobType = Constant.JobListType.COMMON;
    private AddressSelectPopWindow addressSelectPopWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RecruitActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("招贤纳士", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("全国");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterpriseActivity.actionStart(RecruitActivity.this);
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        recruitAdapter = new RecruitAdapter(mList, mJobType);
        mDataBinding.recyclerView.setAdapter(recruitAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                RecruitDetailsActivity.actionStart(RecruitActivity.this, mList.get(vh.getLayoutPosition()).getEr_id(), RecruitDetailsActivity.FROM_OTHERS);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                if (mList.get(vh.getLayoutPosition()).getEr_uid().equals(LoginHelper.getInstance().getUserBean().getUid())) {
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
                } else {
                    showMessage("只能删除自己发布的招聘");
                }
            }
        });
        initPop();
        mDataBinding.tvSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.editSearchContent.getText())) {
                    searchText = mDataBinding.editSearchContent.getText().toString();
                    onRefresh();
                }
            }
        });
    }

    private void initPop() {
        addressSelectPopWindow = new AddressSelectPopWindow(this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                addressNo = cityNo;
                getRightView().setText(cityData);
                onRefresh();
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updateRrcruitList("", searchText, addressNo, String.valueOf(page)), new ProgressSubscriber<RecruitBean>(mContext, new SubscriberOnNextListener<RecruitBean>() {
            @Override
            public void onNext(RecruitBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    recruitAdapter.notifyDataSetChanged();
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
        return R.layout.activity_recruit;
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

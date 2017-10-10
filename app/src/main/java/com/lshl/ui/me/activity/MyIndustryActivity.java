package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.lshl.bean.IndustryBean;
import com.lshl.databinding.ActivityMyIndustryBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.adapter.MyIndustryAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class MyIndustryActivity extends BaseActivity<ActivityMyIndustryBinding> implements SpringView.OnFreshListener {
    private boolean isEnd = true;
    private List<IndustryBean.InfoBean> mList;
    private List<String> newList;
    private List<IndustryBean.InfoBean> infoList;
    private static final int REQUEST_CODE_INDUSTRY = 0x000123;
    private MyIndustryAdapter myIndustryAdapter;
    private List<IndustryBean.InfoBean> mArrayList;
    private List<String> mPopList;
    private ListPopupWindow listPopupWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyIndustryActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_INDUSTRY) {
            mList.clear();
            onRefresh();
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("行业分类", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("添加", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMyIndustry(LoginHelper.getInstance().getUserToken())
                        , new Subscriber<IndustryBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(IndustryBean result) {
                                if (result.getStatus() == 1) {
                                    List<IndustryBean.InfoBean> info = result.getInfo();
                                    if (info.size() >= 5) {
                                        Toast.makeText(mContext, "最多可选择五种行业", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (newList.size() > 0) {
                                        newList.clear();
                                    }
                                    if (info.size() > 0) {
                                        for (int i = 0; i < info.size(); i++) {
                                            newList.add(info.get(i).getIn_id() + "-" + info.get(i).getIn_name());
                                        }
                                        StringBuilder builder = new StringBuilder();
                                        ListUtils.appendIdList(newList, builder);
                                        IndustryActivity.actionStart(MyIndustryActivity.this, builder.toString(), info.size(), REQUEST_CODE_INDUSTRY);
                                    } else {
                                        IndustryActivity.actionStart(MyIndustryActivity.this, "", info.size(), REQUEST_CODE_INDUSTRY);
                                    }
                                }
                            }
                        });


            }
        });
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        newList = new ArrayList<>();
        infoList = new ArrayList<>();
        mPopList = new ArrayList<>();
        mArrayList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        myIndustryAdapter = new MyIndustryAdapter(mList);
        mDataBinding.recyclerView.setAdapter(myIndustryAdapter);
        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条信息?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        mList.remove(vh.getAdapterPosition());
                        myIndustryAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() != infoList.size()) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        list.add(mList.get(i).getIn_id() + "-" + mList.get(i).getIn_name());
                    }
                    StringBuilder builder = new StringBuilder();
                    ListUtils.appendIdList(list, builder);
                    updateIndustry(builder.toString());
                } else {
                    showMessage("没做修改,无需保存");
                }
            }
        });
        initPopuWindow();
    }

    private void initPopuWindow() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateIndustry("")
                , new Subscriber<IndustryBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IndustryBean result) {
                        if (result.getStatus() == 1) {
                            mArrayList.addAll(result.getInfo());
                        }
                    }
                });
        if (mArrayList.size() > 0) {
            for (int i = 0; i < mArrayList.size(); i++) {
                mPopList.add(mArrayList.get(i).getIn_name());
            }
        }
        Log.i("屮艸芔茻", mPopList.size() + "======");
        listPopupWindow = new ListPopupWindow(mContext);
        listPopupWindow.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mPopList));
        listPopupWindow.setHeight(ActionMenuView.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setWidth(ActionMenuView.LayoutParams.MATCH_PARENT);

    }

    private void updateIndustry(String industry) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .setIndustry(LoginHelper.getInstance().getUserToken(), industry), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMyIndustry(LoginHelper.getInstance().getUserToken())
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<IndustryBean>() {
                    @Override
                    public void onNext(IndustryBean result) {
                        if (result.getStatus() == 1) {
                            infoList = result.getInfo();
                            mList.addAll(result.getInfo());
                            myIndustryAdapter.notifyDataSetChanged();
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
        return R.layout.activity_my_industry;
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

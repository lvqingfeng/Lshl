package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

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
import com.lshl.bean.KouBeiListBean;
import com.lshl.databinding.ActivityKoubeiListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.KouBeiListAdapter;
import com.lshl.ui.me.activity.ReleaseReputationActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddPopWindow;

import java.util.ArrayList;
import java.util.List;

public class KouBeiListActivity extends BaseActivity<ActivityKoubeiListBinding>
        implements SpringView.OnFreshListener {
    /**
     * 发布红榜的请求码
     */
    private static final int RELEASE_RED_REQUEST_CODE = 0x0000122;
    /**
     * 发布黑榜的请求码
     */
    private static final int RELEASE_BLACK_REQUEST_CODE = 0x0000123;
    private List<KouBeiListBean.InfoBean.ListBean> mList;
    private KouBeiListAdapter kouBeiListAdapter;
    private boolean isEnd;
    private AddPopWindow mReputationTypePopWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, KouBeiListActivity.class);
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
        openTitleLeftView(true);
        setTextTitleView("口碑", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mReputationTypePopWindow = new AddPopWindow(this, R.layout.layout_pop_select_reputation_type);
    }

    @Override
    protected void initViews() {
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mReputationTypePopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.spKoubei.setHeader(new RotationHeader(mContext));
        mDataBinding.spKoubei.setFooter(new RotationFooter(mContext));
        mDataBinding.spKoubei.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerKoubei.setLayoutManager(manager);
        mDataBinding.recyclerKoubei.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerKoubei.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        setListData(mList);
        kouBeiListAdapter = new KouBeiListAdapter(KouBeiListActivity.this, mList);
        mDataBinding.recyclerKoubei.setAdapter(kouBeiListAdapter);
        initLoadData();
        mDataBinding.recyclerKoubei.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerKoubei) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String s_id = mList.get(position).getS_id();
                KouBeiDetailsActivity.actionStart(KouBeiListActivity.this, s_id, KouBeiDetailsActivity.FROM_APPLICEN);
            }
        });
        initPopWindow();
    }

    private void initPopWindow() {
        View rootView = mReputationTypePopWindow.getWindowRootView();
        Button redPut = (Button) rootView.findViewById(R.id.btn_put_red_reputation);
        Button blackPut = (Button) rootView.findViewById(R.id.btn_put_black_reputation);

        redPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseReputationActivity.actionStart(KouBeiListActivity.this, Constant.ReputationType.RED, RELEASE_RED_REQUEST_CODE);
                mReputationTypePopWindow.closePopupWindow();
            }
        });
        blackPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseReputationActivity.actionStart(KouBeiListActivity.this, Constant.ReputationType.BLACK, RELEASE_BLACK_REQUEST_CODE);
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
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateKouBeiList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<KouBeiListBean>(mContext, new SubscriberOnNextListener<KouBeiListBean>() {
            @Override
            public void onNext(KouBeiListBean result) {
                if (result != null) {
                    isEnd = result.getStatus() == 1;
                    List<KouBeiListBean.InfoBean.ListBean> list = result.getInfo().getList();
                    mList.addAll(list);
                    kouBeiListAdapter.notifyDataSetChanged();
                    mDataBinding.spKoubei.onFinishFreshAndLoad();
                    if (list.size() > 0) {
                        mDataBinding.recyclerKoubei.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerKoubei.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                } else {
                    showMessage("数据异常，请重试");
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spKoubei;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_koubei_list;
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

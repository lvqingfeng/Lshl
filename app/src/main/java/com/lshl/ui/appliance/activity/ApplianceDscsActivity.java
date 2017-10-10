package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoadBannerHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.BannerBean;
import com.lshl.bean.YingyongJuanKuanBean;
import com.lshl.databinding.ActivityApplianceDscsBinding;
import com.lshl.ui.appliance.adapter.JuanKuanListAdapter;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ApplianceDscsActivity extends BaseActivity<ActivityApplianceDscsBinding>
        implements SpringView.OnFreshListener {
    private List<YingyongJuanKuanBean.InfoBean.ListBean> mList;
    private JuanKuanListAdapter juanKuanListAdapter;
    private boolean isEnd;
    private PopupWindow popupWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ApplianceDscsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    public class Presenter {
        public void historyBill() {//资金流向(账单)
            DscsRankActivity.actionStart(ApplianceDscsActivity.this);
        }

        public void mutual() {//公益
            WelfareActivity.actionStart(ApplianceDscsActivity.this, WelfareActivity.FROM_THIS);
        }

        public void forHelp() {//互助
            MutualActivity.actionStart(ApplianceDscsActivity.this, MutualActivity.FROM_THIS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("滴水穿石", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_more);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mDataBinding.spYydscsSp.setHeader(new RotationHeader(mContext));
        mDataBinding.spYydscsSp.setFooter(new RotationFooter(mContext));
        mDataBinding.spYydscsSp.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerDscs.setLayoutManager(manager);
        mDataBinding.recyclerDscs.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerDscs.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        setListData(mList);
        juanKuanListAdapter = new JuanKuanListAdapter(mList, ApplianceDscsActivity.this);
        mDataBinding.recyclerDscs.setAdapter(juanKuanListAdapter);
        initLoadData();

        initPopu();
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - 150, location[1] + v.getHeight());
            }
        });

        LoadBannerHelper.getBanner("contribution", new LoadBannerHelper.InitBannerImage() {
            @Override
            public void setBanner(List<String> mList, final BannerBean bannerBean) {
                if (mList.size() > 0) {
                    mDataBinding.viewPager.setPages(new CBViewHolderCreator<LoadImageHolder>() {
                        @Override
                        public LoadImageHolder createHolder() {
                            return new LoadImageHolder();
                        }
                    }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_select, R.drawable.bg_banner_unselect});
                } else {
                    mDataBinding.viewPager.setBackgroundResource(R.mipmap.account_logo);
                }
                mDataBinding.viewPager.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BannerBean.InfoBean infoBean = bannerBean.getInfo().get(position);
                        if (TextUtils.isEmpty(infoBean.getBn_url())) {
                            return;
                        }
                        switch (infoBean.getBn_url()) {
                            case "shanghui":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    SHDynamDetailsicActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid());
                                }
                                break;
                            case "new_member":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    HxMemberDetailsActivity.actionStart(ApplianceDscsActivity.this, "", infoBean.getBn_urlid(), false);
                                }
                                break;
                            case "xiangmu":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    ProjectDetailActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                }
                                break;
                            case "koubei":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    KouBeiDetailsActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                }
                                break;
                            case "gongyi":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    DscsProjectDetailsActivity.actionStart(ApplianceDscsActivity.this, DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                }
                                break;
                            case "huzhu":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    DscsProjectDetailsActivity.actionStart(ApplianceDscsActivity.this, DscsProjectDetailsActivity.FROM_MA, infoBean.getBn_urlid());
                                }
                                break;
                            case "zimaoqu":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    GoodsDetailsActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid(), GoodsDetailsActivity.FROM_OTHER);
                                }
                                break;
                            case "findhelper":
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    LookHelpDetailsActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid());
                                }
                                break;
                            default:
                                if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                    NewsInfoActivity.actionStart(ApplianceDscsActivity.this, infoBean.getBn_urlid(), ApiClient.getNewsImage(infoBean.getBn_img()));
                                }
                                break;
                        }
                    }
                });
            }

        });
    }

    private void initPopu() {
        popupWindow = new PopupWindow(mContext);
        View view = getLayoutInflater().inflate(R.layout.item_layout_dscs_popu, null);
        popupWindow.setContentView(view);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        final RelativeLayout ll_parent = (RelativeLayout) view.findViewById(R.id.ll_shanghui_parent);
        final TextView person = (TextView) view.findViewById(R.id.item_geren);
        final TextView shanghui = (TextView) view.findViewById(R.id.item_shanghui);
        final TextView qiye = (TextView) view.findViewById(R.id.item_qiye);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContributionTypeActivity.actionStart(ApplianceDscsActivity.this, "个人", "1");
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
        shanghui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContributionTypeActivity.actionStart(ApplianceDscsActivity.this, "商会", "2");
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
        qiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContributionTypeActivity.actionStart(ApplianceDscsActivity.this, "企业", "3");
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateJuanKuanList(String.valueOf(page), null), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<YingyongJuanKuanBean>() {
            @Override
            public void onNext(YingyongJuanKuanBean result) {
                if (result != null) {
                    List<YingyongJuanKuanBean.InfoBean.ListBean> list = result.getInfo().getList();
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(list);
                    juanKuanListAdapter.notifyDataSetChanged();
                    if (list.size() > 0) {
                        mDataBinding.recyclerDscs.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerDscs.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                } else {
                    showMessage("数据异常，请重试");
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spYydscsSp;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDataBinding.viewPager.startTurning(3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDataBinding.viewPager.stopTurning();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appliance_dscs;
    }

}

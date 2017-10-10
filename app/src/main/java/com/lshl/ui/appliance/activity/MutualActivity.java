package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

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
import com.lshl.base.HttpResult;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.BannerBean;
import com.lshl.bean.ForHelpBean;
import com.lshl.databinding.ActivityWelfareBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.ForHelpAdapter;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.me.dscs.activity.PublishDscsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MutualActivity extends BaseActivity<ActivityWelfareBinding>
        implements SpringView.OnFreshListener {
    private List<ForHelpBean.ListBean> mList;
    private boolean isEnd;
    private ForHelpAdapter forHelpAdapter;
    private static final int HUZHU = 0x000124;
    public static final int FROM_SUCCESS = 0x000223;
    public static final int FROM_THIS = 0x000224;
    private int FROM_WHERE;

    public static void actionStart(Activity activity, int FROM_WHERE) {
        Intent intent = new Intent(activity, MutualActivity.class);
        intent.putExtra("FROM_WHERE", FROM_WHERE);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HUZHU) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("互助列表", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
    }

    @Override
    protected void initViews() {
        FROM_WHERE = getIntent().getIntExtra("FROM_WHERE", -1);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublishDscsActivity.actionStart(MutualActivity.this, PublishDscsActivity.PublishType.HELP, HUZHU);
            }
        });
        LoadBannerHelper.getBanner("mutualaid", new LoadBannerHelper.InitBannerImage() {
            @Override
            public void setBanner(List<String> mList, final BannerBean bannerBean) {
                if (mList.size() > 0) {
                    mDataBinding.convenientBanner.startTurning(2000);
                    mDataBinding.convenientBanner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new LoadImageHolder();
                        }
                    }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_select, R.drawable.bg_banner_unselect});
                    mDataBinding.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            BannerBean.InfoBean infoBean = bannerBean.getInfo().get(position);
                            if (TextUtils.isEmpty(infoBean.getBn_url())) {
                                return;
                            }
                            switch (infoBean.getBn_url()) {
                                case "shanghui":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        SHDynamDetailsicActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "new_member":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        HxMemberDetailsActivity.actionStart(MutualActivity.this, "", infoBean.getBn_urlid(), false);
                                    }
                                    break;
                                case "xiangmu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        ProjectDetailActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "koubei":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        KouBeiDetailsActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "gongyi":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        DscsProjectDetailsActivity.actionStart(MutualActivity.this, DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "huzhu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        DscsProjectDetailsActivity.actionStart(MutualActivity.this, DscsProjectDetailsActivity.FROM_MA, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "zimaoqu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        GoodsDetailsActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid(), GoodsDetailsActivity.FROM_OTHER);
                                    }
                                    break;
                                case "findhelper":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        LookHelpDetailsActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid());
                                    }
                                    break;
                                default:
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        NewsInfoActivity.actionStart(MutualActivity.this, infoBean.getBn_urlid(), ApiClient.getNewsImage(infoBean.getBn_img()));
                                    }
                                    break;
                            }
                        }
                    });
                } else {
                    mDataBinding.convenientBanner.setBackgroundResource(R.mipmap.account_logo);
                }
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        forHelpAdapter = new ForHelpAdapter(mList);
        mDataBinding.recyclerView.setAdapter(forHelpAdapter);
        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ForHelpBean.ListBean bean = mList.get(vh.getLayoutPosition());
                String ma_id = bean.getMa_id();
                DscsProjectDetailsActivity.actionStart(MutualActivity.this, DscsProjectDetailsActivity.FROM_MA, ma_id);
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        if (FROM_WHERE == FROM_THIS) {
            loadData(page, "");
        } else {
            loadData(page, "success");
        }
    }

    private void loadData(int page, String type) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .pdateAppliceMutualList(String.valueOf(page), type), new ProgressSubscriber<HttpResult<ForHelpBean>>(mContext, new SubscriberOnNextListener<HttpResult<ForHelpBean>>() {
            @Override
            public void onNext(HttpResult<ForHelpBean> result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    forHelpAdapter.notifyDataSetChanged();
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
        return R.layout.activity_welfare;
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

package com.lshl.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseFragment;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.HomePageBean;
import com.lshl.databinding.FragmentHomePageBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.activity.CompanyListActivity;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.KouBeiListActivity;
import com.lshl.ui.appliance.activity.LookHelpListActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.appliance.activity.SpecificShoppingActivity;
import com.lshl.ui.appliance.activity.TradeActivity;
import com.lshl.ui.appliance.job.RecruitActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.fragment.homepageadapter.FindHelperAdapter;
import com.lshl.ui.fragment.homepageadapter.GoodsAdapter;
import com.lshl.ui.fragment.homepageadapter.LuShangAdapter;
import com.lshl.ui.fragment.homepageadapter.MemberAdapter;
import com.lshl.ui.fragment.homepageadapter.NoticeAdapter;
import com.lshl.ui.fragment.homepageadapter.ScandalousAdapter;
import com.lshl.ui.info.activity.AddContactActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.LuShangActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;
import com.lshl.ui.news.adapter.LaGuaAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/***
 * Created by Administrator on 2017/3/14.
 */

public class HomepageFragment extends BaseFragment<FragmentHomePageBinding> {
    private List<HomePageBean.InfoBean.MemberBean> memberBeanList;
    private List<HomePageBean.InfoBean.BannerBean> bannerBeanList;
    private List<HomePageBean.InfoBean.FindhelperBean> findhelperBeanList;
    private List<HomePageBean.InfoBean.GoodsBean> goodsBeanList;
    private List<HomePageBean.InfoBean.LushangBean> lushangBeanList;
    private List<HomePageBean.InfoBean.NoticeBean> noticeBeanList;
    private List<HomePageBean.InfoBean.ScandalousBean> scandalousBeanList;
    private List<HomePageBean.InfoBean.LaguaBean> laguaBeanList;
    private List<String> mList;
    private MemberAdapter memberAdapter;
    private LinearLayoutManager goods;
    private GoodsAdapter goodsAdapter;
    private LinearLayoutManager luShang;
    private LuShangAdapter luShangAdapter;
    private LinearLayoutManager findHelper;
    private FindHelperAdapter findHelperAdapter;
    private LinearLayoutManager notice;
    private NoticeAdapter noticeAdapter;
    private LinearLayoutManager scandalous;
    private ScandalousAdapter scandalousAdapter;
    private LaGuaAdapter laGuaAdapter;

    public static HomepageFragment newInstance() {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //蓝色更多的点击事件
    public class Presenter {
        //拉呱
        public void onClickLaGua() {
            LuShangActivity.actionStart(getActivity(), "lagua", "拉呱");
        }

        //鲁商通知
        public void onClickNotice() {
            LuShangActivity.actionStart(getActivity(), "notice", "鲁商通知");
        }

        //新会员
        public void onClickNewMember() {
            AddContactActivity.actionStart(getActivity(), "moreNewMember");
        }

        //自贸区
        public void onClickGoods() {
            TradeActivity.actionStart(getActivity());
        }

        //鲁商
        public void onClickLuShang() {
            LuShangActivity.actionStart(getActivity(), "lushang", "鲁商");
        }

        //找帮手
        public void onClickFindHelper() {
            LookHelpListActivity.actionStart(getActivity());
        }

        //口碑
        public void onClickKouBei() {
            KouBeiListActivity.actionStart(getActivity());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mDataBinding.recyclerNotice.setFocusable(false);
        mDataBinding.recyclerScandalous.setFocusable(false);
        mDataBinding.recyclerFindhelper.setFocusable(false);
        mDataBinding.recyclerGoods.setFocusable(false);
        mDataBinding.recyclerMember.setFocusable(false);
        mDataBinding.recyclerLushang.setFocusable(false);
        int a[] = {R.color.text_red_color, R.color.text_blue, R.color.text_black, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bannerBeanList.clear();
                findhelperBeanList.clear();
                goodsBeanList.clear();
                lushangBeanList.clear();
                memberBeanList.clear();
                scandalousBeanList.clear();
                laguaBeanList.clear();
                noticeBeanList.clear();
                loadBaseData();
                mDataBinding.swipeRefresh.setRefreshing(false);
            }
        });
        initRadioGroupListener();
        initArrayList();
        initRecyclerView();
        loadBaseData();
    }

    private void initRadioGroupListener() {
        mDataBinding.groupFirst.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.homepage_new_member:
                        AddContactActivity.actionStart(getActivity(), "");
                        mDataBinding.homepageNewMember.setChecked(false);
                        break;
                    case R.id.homepage_qiye:
                        CompanyListActivity.actionStart(getActivity());
                        mDataBinding.homepageQiye.setChecked(false);
                        break;
                    case R.id.homepage_lushang:
                        LuShangActivity.actionStart(getActivity(), "lushang", "鲁商");
                        mDataBinding.homepageLushang.setChecked(false);
                        break;
                    case R.id.homepage_findHelper:
                        LookHelpListActivity.actionStart(getActivity());
                        mDataBinding.homepageFindHelper.setChecked(false);
                        break;
                }
            }
        });
        mDataBinding.groupSecond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mDataBinding.groupFirst.setFocusable(false);
                switch (i) {
                    case R.id.homepage_koubei:
                        KouBeiListActivity.actionStart(getActivity());
                        mDataBinding.homepageKoubei.setChecked(false);
                        break;
                    case R.id.homepage_zhaoping:
                        RecruitActivity.actionStart(getActivity());
                        mDataBinding.homepageZhaoping.setChecked(false);
                        break;
                    case R.id.homepage_youqing:
                        SpecificShoppingActivity.actionStart(getActivity());
                        mDataBinding.homepageYouqing.setChecked(false);
                        break;
                    case R.id.homepage_notice:
                        LuShangActivity.actionStart(getActivity(), "notice", "鲁商通知");
                        mDataBinding.homepageNotice.setChecked(false);
                        break;
                }
            }
        });
    }


    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateHomePageList(), new Subscriber<HomePageBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomePageBean homePageBean) {
                HomePageBean.InfoBean info = homePageBean.getInfo();
                if (info.getLagua().size() > 0) {
                    mDataBinding.llLagua.setVisibility(View.VISIBLE);
                    laguaBeanList.addAll(info.getLagua());
                    laGuaAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llLagua.setVisibility(View.GONE);
                }
                if (info.getMember().size() > 0) {
                    mDataBinding.llMember.setVisibility(View.VISIBLE);
                    memberBeanList.addAll(info.getMember());
                    memberAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llMember.setVisibility(View.GONE);
                }

                if (info.getGoods().size() > 0) {
                    mDataBinding.llGoods.setVisibility(View.VISIBLE);
                    goodsBeanList.addAll(info.getGoods());
                    goodsAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llGoods.setVisibility(View.GONE);
                }

                if (info.getLushang().size() > 0) {
                    mDataBinding.llLushang.setVisibility(View.VISIBLE);
                    lushangBeanList.addAll(info.getLushang());
                    luShangAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llLushang.setVisibility(View.GONE);
                }

                if (info.getFindhelper().size() > 0) {
                    mDataBinding.llFindhelper.setVisibility(View.VISIBLE);
                    findhelperBeanList.addAll(info.getFindhelper());
                    findHelperAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llFindhelper.setVisibility(View.GONE);
                }

                if (info.getNotice().size() > 0) {
                    mDataBinding.llNotice.setVisibility(View.VISIBLE);
                    noticeBeanList.addAll(info.getNotice());
                    noticeAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llNotice.setVisibility(View.GONE);
                }

                if (info.getScandalous().size() > 0) {
                    mDataBinding.llScandalous.setVisibility(View.VISIBLE);
                    scandalousBeanList.addAll(info.getScandalous());
                    scandalousAdapter.notifyDataSetChanged();
                } else {
                    mDataBinding.llScandalous.setVisibility(View.GONE);
                }
                if (info.getBanner().size() > 0) {
                    bannerBeanList.addAll(info.getBanner());
                    mList.clear();
                    for (int i = 0; i < bannerBeanList.size(); i++) {
                        mList.add(ApiService.BASE_URL + bannerBeanList.get(i).getBn_img());
                    }
                    mDataBinding.cbBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {
                        @Override
                        public LoadImageHolder createHolder() {
                            return new LoadImageHolder();
                        }
                    }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_unselect, R.drawable.bg_banner_select});
                    mDataBinding.cbBanner.startTurning(3000);
                    mDataBinding.cbBanner.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            HomePageBean.InfoBean.BannerBean bean = bannerBeanList.get(position);
                            if (!TextUtils.isEmpty(bean.getBn_url())) {
                                switch (bean.getBn_url()) {
                                    case "new_member":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            HxMemberDetailsActivity.actionStart(getActivity(), "", bean.getBn_urlid(), false);
                                        }
                                        break;
                                    case "xiangmu":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            ProjectDetailActivity.actionStart(getActivity(), bean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                        }
                                        break;
                                    case "koubei":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            KouBeiDetailsActivity.actionStart(getActivity(), bean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                        }
                                        break;
                                    case "gongyi":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MB, bean.getBn_urlid());
                                        }
                                        break;
                                    case "huzhu":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MA, bean.getBn_urlid());
                                        }
                                        break;
                                    case "zimaoqu":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            GoodsDetailsActivity.actionStart(getActivity(), bean.getBn_urlid(), GoodsDetailsActivity.FROM_OTHER);
                                        }
                                        break;
                                    case "findhelper":
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            LookHelpDetailsActivity.actionStart(getActivity(), bean.getBn_urlid());
                                        }
                                        break;
                                    default:
                                        if (!TextUtils.isEmpty(bean.getBn_urlid())) {
                                            NewsInfoActivity.actionStart(getActivity(), bean.getBn_urlid(), ApiClient.getNewsImage(bean.getBn_img()));
                                        }
                                        break;
                                }
                            } else {
                                return;
                            }
                        }
                    });
                } else {
                    mDataBinding.cbBanner.setBackgroundResource(R.mipmap.account_logo);
                }
            }
        });
    }

    private void initArrayList() {
        memberBeanList = new ArrayList<>();
        bannerBeanList = new ArrayList<>();
        findhelperBeanList = new ArrayList<>();
        goodsBeanList = new ArrayList<>();
        lushangBeanList = new ArrayList<>();
        noticeBeanList = new ArrayList<>();
        scandalousBeanList = new ArrayList<>();
        laguaBeanList = new ArrayList<>();
        mList = new ArrayList<>();
    }

    private void initRecyclerView() {
        /*****
         * 拉呱
         * */
        LinearLayoutManager lagua = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerLagua.setLayoutManager(lagua);
        mDataBinding.recyclerLagua.addItemDecoration(new DividerGridItemDecoration(mContext));
        laGuaAdapter = new LaGuaAdapter(laguaBeanList);
        mDataBinding.recyclerLagua.setAdapter(laGuaAdapter);
        mDataBinding.recyclerLagua.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerLagua) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HomePageBean.InfoBean.LaguaBean laguaBean = laguaBeanList.get(vh.getLayoutPosition());
                NewsInfoActivity.actionStart(getActivity(), laguaBean.getId(), ApiService.BASE_URL + laguaBean.getImg());
            }
        });
        /****
         * 新会员
         * */
        LinearLayoutManager member = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerMember.setLayoutManager(member);
        mDataBinding.recyclerMember.addItemDecoration(new DividerGridItemDecoration(mContext));
        memberAdapter = new MemberAdapter(getActivity(), memberBeanList);
        mDataBinding.recyclerMember.setAdapter(memberAdapter);
        mDataBinding.recyclerMember.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerMember) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = memberBeanList.get(vh.getLayoutPosition()).getUid();
                HxMemberDetailsActivity.actionStart(getActivity(), "", uid, false);
            }
        });
        /****
         * 自贸区
         * */
        goods = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerGoods.setLayoutManager(goods);
        mDataBinding.recyclerGoods.addItemDecoration(new DividerGridItemDecoration(mContext));
        goodsAdapter = new GoodsAdapter(goodsBeanList);
        mDataBinding.recyclerGoods.setAdapter(goodsAdapter);

        mDataBinding.recyclerGoods.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerGoods) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(getActivity(), goodsBeanList.get(vh.getLayoutPosition()).getId(), GoodsDetailsActivity.FROM_OTHER);
            }
        });
        /****
         * 鲁商
         * */
        luShang = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerLushang.setLayoutManager(luShang);
        mDataBinding.recyclerLushang.addItemDecoration(new DividerGridItemDecoration(mContext));
        luShangAdapter = new LuShangAdapter(lushangBeanList);
        mDataBinding.recyclerLushang.setAdapter(luShangAdapter);
        mDataBinding.recyclerLushang.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerLushang) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HomePageBean.InfoBean.LushangBean bean = lushangBeanList.get(vh.getLayoutPosition());
                NewsInfoActivity.actionStart(getActivity(), bean.getId(), ApiService.BASE_URL + bean.getImg());
            }
        });
        /****
         * 找帮手
         * */
        findHelper = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerFindhelper.setLayoutManager(findHelper);
        mDataBinding.recyclerFindhelper.addItemDecoration(new DividerGridItemDecoration(mContext));
        findHelperAdapter = new FindHelperAdapter(findhelperBeanList);
        mDataBinding.recyclerFindhelper.setAdapter(findHelperAdapter);
        mDataBinding.recyclerFindhelper.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerFindhelper) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                LookHelpDetailsActivity.actionStart(getActivity(), findhelperBeanList.get(vh.getLayoutPosition()).getId());
            }
        });
        /*****
         * 鲁商通知
         * */
        notice = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerNotice.setLayoutManager(notice);
        mDataBinding.recyclerNotice.addItemDecoration(new DividerGridItemDecoration(mContext));
        noticeAdapter = new NoticeAdapter(noticeBeanList);
        mDataBinding.recyclerNotice.setAdapter(noticeAdapter);
        mDataBinding.recyclerNotice.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerNotice) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HomePageBean.InfoBean.NoticeBean bean = noticeBeanList.get(vh.getLayoutPosition());
                NewsInfoActivity.actionStart(getActivity(), bean.getId(), ApiService.BASE_URL + bean.getImg());
            }
        });
        /*****
         * 口碑
         * */
        scandalous = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerScandalous.setLayoutManager(scandalous);
        mDataBinding.recyclerScandalous.addItemDecoration(new DividerGridItemDecoration(mContext));
        scandalousAdapter = new ScandalousAdapter(scandalousBeanList);
        mDataBinding.recyclerScandalous.setAdapter(scandalousAdapter);
        mDataBinding.recyclerScandalous.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerScandalous) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                KouBeiDetailsActivity.actionStart(getActivity(), scandalousBeanList.get(vh.getLayoutPosition()).getId(), KouBeiDetailsActivity.FROM_APPLICEN);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mDataBinding.cbBanner.stopTurning();
    }
}

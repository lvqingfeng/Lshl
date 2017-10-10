package com.lshl.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lshl.LoadBannerHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseFragment;
import com.lshl.base.LoadImageHolder;
import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.BannerBean;
import com.lshl.databinding.FragmentApplianceBinding;
import com.lshl.task.TaskBase;
import com.lshl.ui.appliance.activity.ApplianceDscsActivity;
import com.lshl.ui.appliance.activity.CompanyListActivity;
import com.lshl.ui.appliance.activity.HotServiceActivity;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.KouBeiListActivity;
import com.lshl.ui.appliance.activity.LookHelpListActivity;
import com.lshl.ui.appliance.activity.ProjectActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.appliance.activity.SpecificShoppingActivity;
import com.lshl.ui.appliance.activity.TradeActivity;
import com.lshl.ui.appliance.job.RecruitActivity;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;

import java.util.List;

/**
 * 应用Fragment
 */
public class ApplianceFragment extends BaseFragment<FragmentApplianceBinding> {

    public ApplianceFragment() {
        // Required empty public constructor
    }


    public static ApplianceFragment newInstance() {
        ApplianceFragment fragment = new ApplianceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public class Presenter {

        //点击项目
        public void onClickItemProject() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    ProjectActivity.actionStart(getActivity());
                }
            });
        }

        //点击口碑
        public void onClickItemReputation() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    Log.i("屮艸芔茻", LoginHelper.getInstance().getUserToken()+"===");
                    KouBeiListActivity.actionStart(getActivity());
                }
            });
        }

        //点击滴水穿石
        public void onClickItemDscs() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    ApplianceDscsActivity.actionStart(getActivity());
                }
            });

        }

        //自贸区
        public void onClickItemTrade() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    TradeActivity.actionStart(ApplianceFragment.this);
                }
            });
        }
        //点击热门服务
        public void onClickItemHot() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    HotServiceActivity.actionStart(getActivity());
                }
            });

        }
        //点击招贤纳士
        public void onClickItemRecruit(){
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    RecruitActivity.actionStart(getActivity());
                }
            });
        }
        //找帮手
        public void onClickLookHelp(){
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    LookHelpListActivity.actionStart(getActivity());
                }
            });
        }
        //友情链接
        public void onClickyouqing(){
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    SpecificShoppingActivity.actionStart(getActivity());
                }
            });
        }
        //企业
        public void onClickCompany(){
            CompanyListActivity.actionStart(getActivity());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_appliance;
    }


    @Override
    protected void initViews() {
        mDataBinding.setImageSize(LshlApplication.sWindowWidth / 10);
        mDataBinding.setPresenter(new Presenter());
        LoadBannerHelper.getBanner("application", new LoadBannerHelper.InitBannerImage() {
            @Override
            public void setBanner(List<String> mList, final BannerBean bannerBean) {
                if (mList.size() > 0) {
                    mDataBinding.convenientBanner.startTurning(3000);
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
                            if (TextUtils.isEmpty(infoBean.getBn_url())){
                                return;
                            }
                            switch (infoBean.getBn_url()){
                                case "shanghui":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        SHDynamDetailsicActivity.actionStart(getActivity(), infoBean.getBn_urlid());
                                    }
                                    break;
                                case "new_member":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        HxMemberDetailsActivity.actionStart(getActivity(),"",infoBean.getBn_urlid(),false);
                                    }
                                    break;
                                case "xiangmu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        ProjectDetailActivity.actionStart(getActivity(), infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "koubei":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        KouBeiDetailsActivity.actionStart(getActivity(), infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "gongyi":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "huzhu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MA,infoBean.getBn_urlid());
                                    }
                                    break;
                                case "zimaoqu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        GoodsDetailsActivity.actionStart(getActivity(),infoBean.getBn_urlid(),GoodsDetailsActivity.FROM_OTHER);
                                    }
                                    break;
                                case "findhelper":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        LookHelpDetailsActivity.actionStart(getActivity(),infoBean.getBn_urlid());
                                    }
                                    break;
                                default:
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                        NewsInfoActivity.actionStart(getActivity(), infoBean.getBn_urlid(), ApiClient.getNewsImage(infoBean.getBn_img()));
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
    }


    @Override
    public void onStop() {
        super.onStop();
        mDataBinding.convenientBanner.stopTurning();
    }


}

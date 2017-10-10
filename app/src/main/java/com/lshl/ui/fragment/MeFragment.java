package com.lshl.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseFragment;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.IconBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.PraiseBean;
import com.lshl.bean.ResultInfoBean;
import com.lshl.databinding.FragmentMeBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.MainActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.ClickPraiseActivity;
import com.lshl.ui.me.activity.CommerceActivity;
import com.lshl.ui.me.activity.EnterpriseActivity;
import com.lshl.ui.me.activity.FtaActivity;
import com.lshl.ui.me.activity.JoinUnionActivity;
import com.lshl.ui.me.activity.LookMeActivity;
import com.lshl.ui.me.activity.MemberCenterActivity;
import com.lshl.ui.me.activity.MyLookHelpListActivity;
import com.lshl.ui.me.activity.MyWealthActivity;
import com.lshl.ui.me.activity.NewNoticeActivity;
import com.lshl.ui.me.activity.PersonDataActivity;
import com.lshl.ui.me.activity.PersonDynamicActivity;
import com.lshl.ui.me.activity.PersonalReputationActivity;
import com.lshl.ui.me.activity.PresidentListActivity;
import com.lshl.ui.me.activity.TimelyRainActivity;
import com.lshl.ui.me.adapter.MeMenuItemAdapter;
import com.lshl.ui.me.job.JobInfoActivity;
import com.lshl.ui.me.setting.SettingActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import q.rorbin.badgeview.QBadgeView;
import rx.Subscriber;

/**
 * 个人Fragment
 */
public class MeFragment extends BaseFragment<FragmentMeBinding> {
    private PersonBasedataBean.InfoBean bean;
    private static final int GO_PRAISE_LIST_REQUEST_CODE = 0x000121;

    private ArrayList<String> mList;
    private MeCallback mCallback;
    private CheckBox mRightView;
    private MeMenuItemAdapter mMenuAdapter;
    private TextView personBadgeView;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MeCallback) context;
        mRightView = ((MainActivity) context).getRightViews();
        personBadgeView = ((MainActivity) context).getPersonBadgeView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_PRAISE_LIST_REQUEST_CODE) {
            if (data != null) {
                String clickNum = data.getStringExtra("num");
                mDataBinding.tvClickPraise.setText(clickNum);
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        if (LoginHelper.getInstance().getUserBean() != null) {
            Glide.with(mContext).load(ApiClient.getHxFriendsImage(LoginHelper.getInstance().getUserBean().getAvatar())).error(R.mipmap.account_logo).into(mDataBinding.imageView2);
        }

        //进入点赞的页面
        mDataBinding.llClickPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        ClickPraiseActivity.actionStart(MeFragment.this, GO_PRAISE_LIST_REQUEST_CODE);
                    }
                });
                /* CreateCardsActivity.actionStart(getActivity());*/
            }
        });
        if (mRightView != null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewNoticeActivity.actionStart(getActivity());
                }
            };
            mCallback.onNotifyClick(listener);
            mRightView.setOnClickListener(listener);
        }
        mList = new ArrayList<>();
        loadBaseData();
        loadIconData();
        loadOtherData();
        loadBackGround();
        final GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (position >= 3)
                    return 3;
                return 1;
            }
        });

        mDataBinding.recyclerMenu.setLayoutManager(layoutManager);
        mDataBinding.recyclerMenu.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerMenu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                switch (vh.getLayoutPosition()) {
                    case 0://圈子
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                PersonDynamicActivity.actionStart(getActivity());
                            }
                        });
                        break;
                    case 1://会员中心
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                MemberCenterActivity.actionStart(getActivity());
                            }
                        });
                        break;
                    case 2://设置
                        SettingActivity.actionStart(getActivity(), bean.getHide_account(), Constant.REQUEST_CODE_LOGOUT);
                        break;
                    case 4://我的资料
                        Log.i("屮艸芔茻", LoginHelper.getInstance().getUserToken());
                        PersonDataActivity.actionStart(getActivity());
                        break;
                    case 5://我的财富
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                MyWealthActivity.actionStart(getActivity());
                            }
                        });
                        break;
                    case 6://我的商会
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                CommerceActivity.actionStart(getActivity());
                            }
                        });

                        break;
                    case 8://找帮手
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                MyLookHelpListActivity.actionStart(getActivity());
                            }
                        });
                        //我的项目
//                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
//                            @Override
//                            public void onSuccess(AuthorityCheckBean bean) {
//                                MyProjectActivity.actionStart(getActivity());
//                            }
//                        });

                        break;
                    case 9://我的口碑
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                PersonalReputationActivity.actionStart(getActivity());
                            }
                        });

//                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
//                            @Override
//                            public void onSuccess(AuthorityCheckBean bean) {
//                                MyRecruitActivity.actionStart(getActivity());
//                            }
//                        });
                        break;
                    case 10://自贸区
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                FtaActivity.actionStart(getActivity());
                            }
                        });
                        //找帮手
//                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
//                            @Override
//                            public void onSuccess(AuthorityCheckBean bean) {
//                                MyLookHelpListActivity.actionStart(getActivity());
//                            }
//                        });
                        break;
                    case 11://会长说
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                PresidentListActivity.actionStart(getActivity());
                            }
                        });
//我的滴水穿石
//                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
//                            @Override
//                            public void onSuccess(AuthorityCheckBean bean) {
//                                WelfareMutualActivity.actionStart(getActivity());
//                            }
//                        });

                        break;
                    case 12:

                        break;
                    case 13://我的自贸区

                        break;
                    case 14://招聘
                        checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                            @Override
                            public void onSuccess(AuthorityCheckBean bean) {
                                JobInfoActivity.actionStart(getActivity(), LoginHelper.getInstance().getUserBean().getUid(), "");
                            }
                        });
                        break;
                    case 15://会长说

                        break;
                }
            }
        });
        mMenuAdapter = new MeMenuItemAdapter(mContext);
        mDataBinding.recyclerMenu.setAdapter(mMenuAdapter);
        mDataBinding.lookMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LookMeActivity.actionStart(getActivity());
            }
        });
    }

    public class Presenter {
        //企业
        public void onClickMyQiye() {
            EnterpriseActivity.actionStart(getActivity());
        }

        //直营产品
        public void onClickMyProduct() {
            Toast.makeText(mContext, "正在开发中....", Toast.LENGTH_SHORT).show();
        }

        //订单
        public void onClickMyOrder() {
            Toast.makeText(mContext, "正在开发中....", Toast.LENGTH_SHORT).show();
        }

        //动态
        public void onClickDynamic() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    PersonDynamicActivity.actionStart(getActivity());
                }
            });
        }

        //会员中心
        public void onClickVipCenter() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    MemberCenterActivity.actionStart(getActivity());
                }
            });
        }

        //设置
        public void onClickSetting() {
            SettingActivity.actionStart(getActivity(), bean.getHide_account(), Constant.REQUEST_CODE_LOGOUT);
        }

        //我的资料
        public void onClickMyInfo() {
            Log.i("屮艸芔茻", LoginHelper.getInstance().getUserToken());
            PersonDataActivity.actionStart(getActivity());
        }

        //我的财富
        public void onClickMyRiches() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    MyWealthActivity.actionStart(getActivity());
                }
            });
        }

        //我的商会
        public void onClickMyShanghui() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    CommerceActivity.actionStart(getActivity());
                }
            });
        }

        //找帮手
        public void onClickLookHelp() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    MyLookHelpListActivity.actionStart(getActivity());
                }
            });
        }

        //口碑
        public void onClickKoubei() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    PersonalReputationActivity.actionStart(getActivity());
                }
            });
        }

        //自贸区
        public void onClickZimaoqu() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    FtaActivity.actionStart(getActivity());
                }
            });
        }

        //会长说
        public void onClickHuizs() {
            checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                @Override
                public void onSuccess(AuthorityCheckBean bean) {
                    PresidentListActivity.actionStart(getActivity());
                }
            });
        }

    }

    /*****
     * 设置背景图片
     */
    private void loadBackGround() {

    }

    /****
     * 图标
     */
    private void loadIconData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateIcon(LoginHelper.getInstance().getUserBean().getUid(), ""), new Subscriber<IconBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IconBean iconBean) {
                if (iconBean.getStatus() == 1) {
                    IconBean.InfoBean mInfo = iconBean.getInfo();
                    if (mInfo.getAuthenticate() == 1) {
                        mDataBinding.yihsiming.setVisibility(View.VISIBLE);
                    } else {
                        mDataBinding.weishiming.setVisibility(View.VISIBLE);
                    }
                    if (mInfo.getPresident() == 1) {
                        mDataBinding.president.setVisibility(View.VISIBLE);
                    }
                    if (mInfo.getCharityfund() == 1) {
                        mDataBinding.juankuan.setVisibility(View.VISIBLE);
                    }
                    if (mInfo.getDues() == 1) {
                        mDataBinding.member.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void loadOtherData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getPraiseInfo(LoginHelper.getInstance().getUserToken(), null)
                , new Subscriber<HttpResult<PraiseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<PraiseBean> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            mDataBinding.tvClickPraise.setText(result.getInfo().getCount());
                        }
                    }
                }
        );
    }

    /**
     * 加载用户的基本资料
     */
    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updatePersonData(LoginHelper.getInstance()
                .getUserToken(), null), new Subscriber<PersonBasedataBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final PersonBasedataBean result) {
                if (result.getStatus() == 1) {
                    bean = result.getInfo();
                    String address = result.getInfo().getLj_county();
                    mDataBinding.tvUserName.setText(result.getInfo().getRealname());
                    mDataBinding.tvUserAddress.setText(address);
                    if (result.getInfo().getPresident().equals("1")) {
                        mDataBinding.presnetTalk.setVisibility(View.GONE);
//                        mMenuAdapter.hintItem(11);
                    } else {
                        mDataBinding.presnetTalk.setVisibility(View.GONE);
                    }
                    if (result.getInfo().getAuthenticate().equals("1")) {
                        LshlApplication.getApplication().getAuthorityBean().setRealname(1);
                    }
                    Glide.with(mContext).load(ApiClient
                            .getHxFriendsImage(result.getInfo().getAvatar()))
                            .error(R.mipmap.account_logo).into(mDataBinding.imageView2);
                    Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo()
                            .getAvatar())).thumbnail(0.01f).error(R.mipmap.default_background)
                            .into(mDataBinding.background);
//                    Constant.getGradeForAll(result.getInfo().getGrade(), mDataBinding.grade);

                    if (bean.getReadme_allnum() > 0) {
                        mDataBinding.numLookZong.setText("" + bean.getReadme_allnum());
                    }
                    if (bean.getReadme_num() > 0) {
                        mDataBinding.numLook.setText("+" + bean.getReadme_num());
                    } else {
                        mDataBinding.numLook.setText("");
                    }
                    if (bean.getDynamic_num() > 0) {
                        mDataBinding.dynamicNum.setText("+" + bean.getDynamic_num());
                    } else {
                        mDataBinding.dynamicNum.setText("");
                    }
                    if (!TextUtils.isEmpty(result.getInfo().getAvatar())) {
                        mDataBinding.imageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HxMemberDetailsActivity.actionStart(getActivity(), "", bean.getUid(), false);
//                                mList.clear();
//                                mList.add(ApiClient.getHxFriendsImage(result.getInfo().getAvatar()));
//                                Intent intent = new Intent();
//                                intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
//                                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
//                                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
//                                intent.setClass(mContext, PhotoShowActivity.class);
//                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

    }

    private void loadUnreadNum() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getPushUnreadNum(LoginHelper.getInstance().getUserToken())
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        String unreadNum = stringHttpResult.getInfo();
                        QBadgeView badgeView = new QBadgeView(mContext);
                        if (!unreadNum.equals("0")) {
                            mRightView.setText(unreadNum);
                            badgeView.bindTarget(personBadgeView);
                            badgeView.setBadgeNumber(Integer.parseInt(unreadNum));
                            badgeView.setBadgeTextSize(8, true);
                            badgeView.setGravityOffset(18, 2, true);
                        } else {
                            mRightView.setText("");
                            badgeView.setBadgeNumber(Integer.parseInt("0"));
                        }
                    }
                }
        );
    }

    private void judgeJoinUnion() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.String).judgeBelongUnion(LoginHelper.getInstance().getUserToken()), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody result) {
                if (result != null) {
                    try {
                        String string = result.string();
                        Gson gson = new Gson();
                        ResultInfoBean bean = gson.fromJson(string, ResultInfoBean.class);
                        if (bean != null) {
                            if ("1".equals(bean.getInfo())) {
                                TimelyRainActivity.actionStart(getActivity());
                            } else if ("2".equals(bean.getInfo())) {
                                JoinUnionActivity.actionStart(getActivity());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBaseData();
        loadUnreadNum();
    }

    public interface MeCallback {
        void onNotifyClick(View.OnClickListener listener);
    }
}

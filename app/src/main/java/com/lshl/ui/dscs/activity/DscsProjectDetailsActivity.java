package com.lshl.ui.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.bean.GongYiDetailsBean;
import com.lshl.bean.HuzhuDetailsBean;
import com.lshl.databinding.ActivityDscsProjectDetailsBinding;
import com.lshl.ui.dscs.fragment.ExecutivePersonListFragment;
import com.lshl.ui.dscs.fragment.FeedbackFragment;
import com.lshl.ui.dscs.fragment.GuarantorInfoFragment;
import com.lshl.ui.dscs.fragment.GuarantorListFragment;
import com.lshl.ui.dscs.fragment.PayMoneyFragment;
import com.lshl.ui.dscs.fragment.ProjectProgressFragment;
import com.lshl.ui.dscs.fragment.VoteFragment;
import com.lshl.utils.JsonUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

public class DscsProjectDetailsActivity extends BaseActivity<ActivityDscsProjectDetailsBinding>
        implements ProjectProgressFragment.OnClickGoCallBack, GuarantorListFragment.OnClickGoCallBack
        , ProjectProgressFragment.StartUploadDataCallback
        , VoteFragment.SetupDateCallback {

    public static final int FROM_MA = 0x0000123;//来自于互助页面
    public static final int FROM_MB = 0x0000124;//来自于公益页面

    private String mProjectId;
    private DscsProjectDetailsBean mDetailsBean;
    private int mFromWhere;

    private ProjectProgressFragment mProjectProgressFragment;
    private PayMoneyFragment mPayMoneyFragment;
    private FeedbackFragment mFeedbackFragment;
    private VoteFragment mVoteFragment;
    private GuarantorListFragment mGuarantorListFragment;
    private ExecutivePersonListFragment mExecutivePersonListFragment;
    private GuarantorInfoFragment mGuarantorInfoFragment;
    private boolean isInitFragment;
    private String imagePath;
    private HuzhuDetailsBean.InfoBean huzhuBean;
    private GongYiDetailsBean.InfoBean gongyiBean;
    private ShareAction mShareAction;
    private String title = "分享", content = "分享内容是。。。。",
            url = "http://www.baidu.com";
    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享成功了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(mContext, "分享失败了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public static void actionStart(Activity activity, int fromWhere, String id) {
        Intent intent = new Intent(activity, DscsProjectDetailsActivity.class);
        intent.putExtra("from", fromWhere);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mProjectId = intent.getStringExtra("id");
        mFromWhere = intent.getIntExtra("from", -1);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("审核详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);
        getRightView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mFromWhere == FROM_MA) {
                    title = huzhuBean.getMa_project_name();
                    content = huzhuBean.getMa_project_info();
                    imagePath = ApiClient.getMutualImage(huzhuBean.getMa_project_img());
                    mShareAction.withTitle(title)
                            .withText(content)
                            .withMedia(new UMImage(mContext, imagePath))
                            .withTargetUrl(ApiService.SHARE_URL + "huzhu/id/" + huzhuBean.getMa_id())
                            .open();
                } else {
                    title = gongyiBean.getPw_project_name();
                    content = gongyiBean.getPw_project_info();
                    imagePath = ApiClient.getPublicWelfare(gongyiBean.getPw_project_img());
                    mShareAction.withTitle(title)
                            .withText(content)
                            .withMedia(new UMImage(mContext, imagePath))
                            .withTargetUrl(ApiService.SHARE_URL + "gongyi/id/" + gongyiBean.getPw_id())
                            .open();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
            return;
        }
        super.onBackPressed();
        setTextTitleView("审核详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mShareAction = new ShareAction(DscsProjectDetailsActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);
        loadData();
        loadShareDetails();
    }

    private void loadShareDetails() {
        if (mFromWhere == FROM_MA) {
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateHUzhu(mProjectId), new Subscriber<HuzhuDetailsBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HuzhuDetailsBean huzhuDetailsBean) {
                    huzhuBean = huzhuDetailsBean.getInfo();
                }
            });
        } else {
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateGOngyi(mProjectId), new Subscriber<GongYiDetailsBean>() {

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(GongYiDetailsBean gongYiDetailsBean) {
                    gongyiBean = gongYiDetailsBean.getInfo();
                }
            });
        }
    }

    private void loadData() {
        if (mFromWhere == FROM_MA) {//来自于互助页面
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).maProjectDetails(mProjectId)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            try {
                                String resultStr = result.string();
                                JSONObject object = new JSONObject(resultStr);
                                readJson(object, R.array.AidDetails);
                            } catch (Exception e) {
                                showMessage("数据异常：" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    })
            );/**/
        } else if (mFromWhere == FROM_MB) {//来自于公益界面
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).mbProjectDetails(mProjectId)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            try {
                                String resultStr = result.string();
                                JSONObject object = new JSONObject(resultStr);
                                readJson(object, R.array.WelfareDetails);
                            } catch (Exception e) {
                                showMessage("数据异常：" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    })
            );
        }
        if (mFromWhere == FROM_MA) {

        }
    }

    private void readJson(JSONObject object, int... arrayConfig) {

        try {
            String status = object.getString("status");
            if (status.equals(ApiService.STATUS_SUC)) {
                JSONObject infoObject = object.getJSONObject("info");

                mDetailsBean = JsonUtils.json2Object(mContext, infoObject, arrayConfig[0], DscsProjectDetailsBean.class);
                JSONArray imageArray = infoObject.getJSONArray("imgs");
                List<String> imageList = new ArrayList<>();
                for (String s : JSON.parseArray(imageArray.toString(), String.class)) {
                    if (mFromWhere == FROM_MB) {
                        imageList.add(ApiClient.getPublicWelfare(s));
                    } else if (mFromWhere == FROM_MA) {
                        imageList.add(ApiClient.getMutualImage(s));
                    }
                }
                mDetailsBean.setImages(imageList);

                if (mFromWhere == FROM_MB) {
                    JSONArray feedbackImage = infoObject.getJSONArray("feedback_img");
                    mDetailsBean.setFeedback_img(JSON.parseArray(feedbackImage.toString(), String.class));
                    mDetailsBean.setGood(true);
                } else {
                    mDetailsBean.setHelp(true);
                }
                mDataBinding.setProjectInfo(mDetailsBean);
                initFragment();
                setupProjectProgressFragment();
            } else if (status.equals(ApiService.TOKEN_EX)) {
                showMessage("登陆信息异常，请重新登陆");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Json数据转换异常：" + e.getMessage());
        }
    }

    private void initFragment() {

        if (!isInitFragment) {
            mProjectProgressFragment = ProjectProgressFragment.newInstance(mDetailsBean);
            mVoteFragment = VoteFragment.newInstance(mDetailsBean);

            mPayMoneyFragment = PayMoneyFragment.newInstance(mFromWhere, mProjectId);
            mFeedbackFragment = FeedbackFragment.newInstance(mProjectId);
            mGuarantorListFragment = GuarantorListFragment.newInstance(mProjectId);

            mExecutivePersonListFragment = ExecutivePersonListFragment.newInstance(mProjectId);
            isInitFragment = true;
        }


    }

    private void setupProjectProgressFragment() {
        if (mProjectProgressFragment.isVisible()) {
            mProjectProgressFragment.uploadVoteStatus(mDetailsBean);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_content, mProjectProgressFragment)
                    .addToBackStack("dscs")
                    .show(mProjectProgressFragment)
                    .commit();

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dscs_project_details;
    }

    @Override
    public void onClickGoVote() {
        setTextTitleView("投票讯息", DEFAULT_TITLE_TEXT_COLOR);

        getSupportFragmentManager().beginTransaction()
                .hide(mProjectProgressFragment)
                .add(R.id.fl_content, mVoteFragment)
                .addToBackStack("dscs")
                .show(mVoteFragment)
                .commit();
    }

    @Override
    public void onClickGoMoneyInfo() {
        getSupportFragmentManager().beginTransaction()
                .hide(mProjectProgressFragment)
                .add(R.id.fl_content, mPayMoneyFragment)
                .addToBackStack("dscs")
                .show(mPayMoneyFragment)
                .commit();
    }

    @Override
    public void onClickFeedback() {
        getSupportFragmentManager().beginTransaction()
                .hide(mProjectProgressFragment)
                .add(R.id.fl_content, mFeedbackFragment)
                .addToBackStack("dscs")
                .show(mFeedbackFragment)
                .commit();
    }

    @Override
    public void onClickGoGuarantorList() {
        getSupportFragmentManager().beginTransaction()
                .hide(mProjectProgressFragment)
                .add(R.id.fl_content, mGuarantorListFragment)
                .addToBackStack("dscs")
                .show(mGuarantorListFragment)
                .commit();
    }

    @Override
    public void onClickExecutivePersonList() {
        getSupportFragmentManager().beginTransaction()
                .hide(mProjectProgressFragment)
                .add(R.id.fl_content, mExecutivePersonListFragment)
                .addToBackStack("dscs")
                .show(mExecutivePersonListFragment)
                .commit();
    }

    @Override
    public void onClickGoGuarantorInfo(String image) {
        mGuarantorInfoFragment = GuarantorInfoFragment.newInstance(image);
        getSupportFragmentManager().beginTransaction()
                .hide(mGuarantorListFragment)
                .add(R.id.fl_content, mGuarantorInfoFragment)
                .addToBackStack("dscs")
                .show(mGuarantorInfoFragment)
                .commit();
    }

    @Override
    public void onUploadCallback() {
        loadData();
    }

    @Override
    public void onDateCallback() {
        mVoteFragment.uploadVoteStatus(mDetailsBean);
    }
}

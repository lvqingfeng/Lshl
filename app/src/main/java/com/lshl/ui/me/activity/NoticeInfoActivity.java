package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.NewNoticeBean;
import com.lshl.bean.NewNoticeShBean;
import com.lshl.databinding.ActivityNoticeInfoBinding;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.business.activity.ShangHuiDetailsActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;

public class NoticeInfoActivity extends BaseActivity<ActivityNoticeInfoBinding> {

    private NewNoticeBean.ListBean mNoticeBean;

    public class Presenter {
        public void onClickGoInfo() {
            String type = mNoticeBean.getPi_catid();
            final String id = mNoticeBean.getPi_project();
            switch (type) {
                case "public_welfare"://公益
                    DscsProjectDetailsActivity.actionStart(NoticeInfoActivity.this, DscsProjectDetailsActivity.FROM_MB, id);
                    break;
                case "mutual_aid"://互助
                    DscsProjectDetailsActivity.actionStart(NoticeInfoActivity.this, DscsProjectDetailsActivity.FROM_MA, id);
                    break;
                case "project"://项目
                    ProjectDetailActivity.actionStart(NoticeInfoActivity.this, id, ProjectDetailActivity.FROM_APPLICEN);
                    break;
                case "reputation"://口碑
                    KouBeiDetailsActivity.actionStart(NoticeInfoActivity.this, id, KouBeiDetailsActivity.FROM_APPLICEN);
                    break;
                case "goods"://自贸区
                    GoodsDetailsActivity.actionStart(NoticeInfoActivity.this, id, GoodsDetailsActivity.FROM_OTHER);
                    break;
                case "realname"://真实姓名
                    PersonDataActivity.actionStart(NoticeInfoActivity.this);
                    break;
                case "enterprise"://企业认证
                    EnterPriseDetailsActivity.actionStart(NoticeInfoActivity.this, id);
                    break;
                case "business"://商会
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .updateSHNewNotice(LoginHelper.getInstance().getUserBean().getUid(), id)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<NewNoticeShBean>() {
                                @Override
                                public void onNext(NewNoticeShBean result) {
                                    if (result.getStatus() == 1) {
                                        String title = result.getBusiness_name();
                                        boolean isJoin = result.getInfo() == 1;
                                        ShangHuiDetailsActivity.actionStart(NoticeInfoActivity.this, null, id, title, isJoin, 0);
                                    } else {
                                        Toast.makeText(mContext, "数据异常", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }));
                    break;
            }
        }
    }

    public static void actionStart(Activity activity, NewNoticeBean.ListBean listBean) {
        Intent intent = new Intent(activity, NoticeInfoActivity.class);
        intent.putExtra("bean", listBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mNoticeBean = (NewNoticeBean.ListBean) getIntent().getSerializableExtra("bean");
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mDataBinding.setNoticeBean(mNoticeBean);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_info;
    }
}

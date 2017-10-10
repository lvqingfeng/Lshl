package com.lshl.ui.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityProjectDetailsBinding;

/**
 * 项目详情
 */
public class ProjectDetailsActivity extends BaseActivity<ActivityProjectDetailsBinding> {
    private int mFromWhere;
    private String mid;
    private int status;
    public static void actionStart(Activity activity, int mFromWhere,String mid,int status) {
        Intent intent = new Intent(activity, ProjectDetailsActivity.class);
        intent.putExtra("id",mid);
        intent.putExtra("mFromWhere",mFromWhere);
        intent.putExtra("status",status);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
   /*     mFromWhere=getIntent().getIntExtra("mFromWhere",0);
        mid=getIntent().getStringExtra("id");
        status=getIntent().getIntExtra("status",0);
        bean=(DscsProjectDetailsBean.ProjectInfo)getIntent().getSerializableExtra("bean");
        Glide.with(mContext).load(ApiClient.getMutualImage(bean.getAvatar())).into(mDataBinding.imageView);
        mDataBinding.textView.setText(bean.getProjectName()+"\n筹款目标:"+bean.getTargetMoney()+"元\n发起人:"
                +bean.getInitiatorName()+"\n发起时间:"+
                DateUtils.getDateToString2(Long.decode(bean.getAddTime())*1000));
        if (mFromWhere==ProjectStatusActivity.FROM_MA){
            if (status==4){
                mDataBinding.webView.loadUrl(ApiService.MUTUAL_MONEY+mid);
            }else{
                mDataBinding.webView.loadUrl(ApiService.HELP+mid);
            }

        }else if (mFromWhere==ProjectStatusActivity.FROM_MB){
            if (status==4){
                mDataBinding.webView.loadUrl(ApiService.WELFARE_MONEY+mid);
            }else if (status==5){
                mDataBinding.webView.loadUrl(ApiService.WELFARE_RECEIPT+mid);
            }else {
                mDataBinding.webView.loadUrl(ApiService.WELFARE+mid);
            }
        }*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_details;
    }
}

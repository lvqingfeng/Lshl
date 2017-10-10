package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.BaseFragment;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.databinding.ActivityWelfareMutualBinding;
import com.lshl.task.TaskBase;
import com.lshl.ui.me.adapter.WelfareMutualPagerAdapter;
import com.lshl.ui.me.dscs.activity.PublishDscsActivity;
import com.lshl.ui.me.fragment.LaunchMutualFragment;
import com.lshl.ui.me.fragment.LaunchWelfareFragment;
import com.lshl.view.AddPopWindow;

import java.util.ArrayList;
import java.util.List;

/***
 * 个人中心  公益互助
 ***/
public class WelfareMutualActivity extends BaseActivity<ActivityWelfareMutualBinding> {
    private List<BaseFragment> mList;
    private WelfareMutualPagerAdapter welfareMutualPagerAdapter;

    private AddPopWindow mPublishPop;
    private TextView mTvGoCommonweal;
    private TextView mTvMutual;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WelfareMutualActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mPublishPop = new AddPopWindow(this, R.layout.layout_pop_publish_select);
        mPublishPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvGoCommonweal = (TextView) mPublishPop.getWindowRootView().findViewById(R.id.tv_commonweal);
        mTvMutual = (TextView) mPublishPop.getWindowRootView().findViewById(R.id.tv_mutual);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的公益互助", DEFAULT_TITLE_TEXT_COLOR);
        CheckBox rightView = getRightView();
        rightView.setButtonDrawable(R.drawable.ic_vector_add);
        //R.drawable.ic_vector_publish原来的
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                checkUserIsDues(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        if (bean.getDonation() == 1) {
                            lightOff();
                            int[] location = new int[2];
                            v.getLocationOnScreen(location);

                            mPublishPop.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - 150, location[1] + v.getHeight());
                        } else {
                            Toast.makeText(mContext, "您当前的捐款额度不足，无法发布", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mList.add(LaunchWelfareFragment.newInstance());
        mList.add(LaunchMutualFragment.newInstance());
        welfareMutualPagerAdapter = new WelfareMutualPagerAdapter(getSupportFragmentManager(), mList);
        mDataBinding.vpWelfare.setAdapter(welfareMutualPagerAdapter);
        mDataBinding.tabWelfare.setupWithViewPager(mDataBinding.vpWelfare);
        mDataBinding.tabWelfare.setTabMode(TabLayout.MODE_FIXED);

        mTvGoCommonweal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublishPop.dismiss();
                PublishDscsActivity.actionStart(WelfareMutualActivity.this, PublishDscsActivity.PublishType.GOOD,0);
            }
        });
        mTvMutual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublishPop.dismiss();
                PublishDscsActivity.actionStart(WelfareMutualActivity.this, PublishDscsActivity.PublishType.HELP,0);
            }
        });
        mPublishPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare_mutual;
    }
}

package com.lshl.ui.appliance.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.ActivityRescueAllianceBinding;
import com.lshl.ui.appliance.adapter.RescueAlianceTabAdapter;
import com.lshl.ui.appliance.fragment.ForHelpFragment;
import com.lshl.ui.appliance.fragment.MemberFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class RescueAllianceActivity extends BaseActivity<ActivityRescueAllianceBinding> {
  private List<BaseFragment> fragments;
  private RescueAlianceTabAdapter rescueAlianceTabAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  public class Presenter {

  }
  @Override
  protected void setupTitle() {
    openTitleLeftView(true);
    setTextTitleView("救援联盟",DEFAULT_TITLE_TEXT_COLOR);
  }

  @Override
  protected void initViews() {
    mDataBinding.setPresenter(new Presenter());
    fragments=new ArrayList<>();
    fragments.add(ForHelpFragment.newInstance());
    fragments.add(MemberFragment.newInstance());
    rescueAlianceTabAdapter = new RescueAlianceTabAdapter(getSupportFragmentManager(),fragments);
    mDataBinding.vpParent.setAdapter(rescueAlianceTabAdapter);
    mDataBinding.tabTitle.setupWithViewPager(mDataBinding.vpParent);
    mDataBinding.tabTitle.setTabMode(TabLayout.MODE_FIXED);
    mDataBinding.tabTitle.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        mDataBinding.vpParent.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_rescue_alliance;
  }
}

package com.lshl.ui.me.activity;

import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPersonalGoodsDetailsBinding;

public class PersonalGoodsDetailsActivity extends BaseActivity<ActivityPersonalGoodsDetailsBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("产品详情",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_goods_details;
    }
}

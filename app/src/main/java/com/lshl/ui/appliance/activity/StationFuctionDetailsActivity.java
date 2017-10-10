package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityStationFuctionDetailsBinding;

public class StationFuctionDetailsActivity extends BaseActivity<ActivityStationFuctionDetailsBinding> {
    private String title;
    private String sid;
    public static void actionStart(Activity activity,String title,String sid) {
        Intent intent = new Intent(activity, StationFuctionDetailsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("sid",sid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        title=getIntent().getStringExtra("title");
        setTextTitleView(title,DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        sid=getIntent().getStringExtra("sid");
        mDataBinding.webView.loadUrl(ApiService.STATION_FUNCTION_DETAILS+sid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_fuction_details;
    }
}

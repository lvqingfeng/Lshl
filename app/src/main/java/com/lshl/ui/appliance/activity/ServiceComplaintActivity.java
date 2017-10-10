package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityStationComplaintBinding;

import java.io.IOException;

import okhttp3.ResponseBody;

public class ServiceComplaintActivity extends BaseActivity<ActivityStationComplaintBinding> {
    private String sid;

    public static void actionStart(Activity activity, String sid) {
        Intent intent = new Intent(activity, ServiceComplaintActivity.class);
        intent.putExtra("sid", sid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("在线投诉", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        sid = getIntent().getStringExtra("sid");
        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.editText.getText())) {
                    String info = mDataBinding.editText.getText().toString();
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String).stationComplaint(LoginHelper.getInstance().getUserToken(), sid, info), new ProgressSubscriber<ResponseBody>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            if (result != null) {
                                try {
                                    String string = result.string();
                                    JSONObject object = JSON.parseObject(string);
                                    String status = object.getString("status");
                                    String objectString = object.getString("info");
                                    if (ApiService.STATUS_SUC.equals(status)) {
                                        Toast.makeText(mContext, objectString, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    Toast.makeText(mContext, objectString, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }));
                } else {
                    showMessage("请输入投诉内容");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_complaint;
    }
}

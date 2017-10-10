package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivityEditNowAddressBinding;
import com.lshl.view.AddressSelectPopWindow;

public class EditNowAddressActivity extends BaseActivity<ActivityEditNowAddressBinding> {
    private String nowNo;
    private String nowProvence;
    private String nowCity;
    private String nowDetails;
    private AddressSelectPopWindow mPopWindow;
    public static final int EDIT_NOW_ADDRESS = 0x0000126;

    public static void actionStart(Activity activity, int resultCode) {
        Intent intent = new Intent(activity, EditNowAddressActivity.class);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("修改常驻地址", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mPopWindow = new AddressSelectPopWindow(EditNowAddressActivity.this);
        mDataBinding.shi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mPopWindow.addressReset();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mDataBinding.shi.setText(provinceData + "" + cityData);
                if (!"0".equals(cityNo)) {
                    if (!TextUtils.isEmpty(cityData)) {
                        if (!TextUtils.isEmpty(provinceData)) {
                            nowNo = cityNo;
                            nowCity = cityData;
                            nowProvence = provinceData;
                            mDataBinding.shi.setText(nowProvence + "" + nowCity);
                        }
                    }
                }
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nowNo)) {
                    showMessage("请选择城市");
                    return;
                }
                if (TextUtils.isEmpty(mDataBinding.xian.getText())) {
                    showMessage("请输入县区");
                    return;
                }
                if (!TextUtils.isEmpty(mDataBinding.details.getText())) {
                    nowDetails = mDataBinding.details.getText().toString();
                } else {
                    nowDetails = "";
                }
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).editNowAddress(LoginHelper.getInstance().getUserToken(), nowProvence + nowCity, mDataBinding.xian.getText().toString(), nowDetails, nowNo), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_now_address;
    }
}

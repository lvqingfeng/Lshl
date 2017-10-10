package com.lshl.ui.appliance.job;

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
import com.lshl.databinding.ActivityEnterpriseRecruitBinding;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.SelectMenuPopWindow;

import java.util.Arrays;
import java.util.List;

public class EnterpriseRecruitActivity extends BaseActivity<ActivityEnterpriseRecruitBinding> {
    private String enid;
    private String education;
    private String cityNos;
    private String mAddress;
    private SelectMenuPopWindow selectMenuPopWindow;
    private List<String> mList;
    private AddressSelectPopWindow addressSelectPopWindow;

    public static void actionStart(Activity activity, String enid, int requestCode) {
        Intent intent = new Intent(activity, EnterpriseRecruitActivity.class);
        intent.putExtra("enid", enid);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("企业招聘", DEFAULT_TITLE_TEXT_COLOR);

    }

    private void initPopWindow() {

        selectMenuPopWindow = new SelectMenuPopWindow(this);
        mList = Arrays.asList(getResources().getStringArray(R.array.education_menu));
        selectMenuPopWindow.bindingList(mList);
        selectMenuPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        selectMenuPopWindow.setOnClickSelectListener(new SelectMenuPopWindow.OnClickSelectListener() {
            @Override
            public void onClickSelectItem(String itemName) {
                education = itemName;
                mDataBinding.education.setText(itemName);
            }
        });
        addressSelectPopWindow = new AddressSelectPopWindow(this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                cityNos = cityNo;
                mAddress = cityData;
                mDataBinding.address.setText(cityData);
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    protected void initViews() {
        enid = getIntent().getStringExtra("enid");
        mDataBinding.phone.setText(LoginHelper.getInstance().getUserBean().getPhone());
        initPopWindow();
        mDataBinding.education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                selectMenuPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightOff();
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mDataBinding.jobName.getText().toString().trim();
                String minMoney = mDataBinding.lowMoney.getText().toString().trim();
                String maxMoney = mDataBinding.highMoney.getText().toString().trim();
                String phone = mDataBinding.phone.getText().toString().trim();
                String info = mDataBinding.info.getText().toString().trim();
                String demand = mDataBinding.demand.getText().toString().trim();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(minMoney)
                        || TextUtils.isEmpty(maxMoney)
                        || TextUtils.isEmpty(info) || TextUtils.isEmpty(demand)) {
                    Toast.makeText(EnterpriseRecruitActivity.this, "请将信息补充完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cityNos)) {
                    Toast.makeText(EnterpriseRecruitActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(education)) {
                    Toast.makeText(EnterpriseRecruitActivity.this, "请选择学历", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Float.parseFloat(maxMoney) <= Float.parseFloat(minMoney)) {
                    Toast.makeText(mContext, "薪资范围有误,请注意提示信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCompanyRecruit(title, minMoney, maxMoney, education, info, mAddress, cityNos, phone, demand);
            }
        });
    }

    private void sendCompanyRecruit(String title, String min_money, String max_money
            , String education, String info, String cityname, String cityno, String phone, String demand) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).sendRecruit(LoginHelper.getInstance().getUserToken()
                , title, enid, min_money, max_money, education, info, cityname, cityno, phone, demand), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                    Toast.makeText(EnterpriseRecruitActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enterprise_recruit;
    }
}

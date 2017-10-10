package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityNowAddressBinding;
import com.lshl.view.AddressSelectPopWindow;

public class NowAddressActivity extends BaseActivity<ActivityNowAddressBinding> {
    private String nowNo;
    private String nowProvence;
    private String nowCity;
    private String nowCountry;
    private String nowDetails="";
    private AddressSelectPopWindow mPopWindow;
    public static final int NOW_ADDRESS=0x0000125;
    public static void actionStart(Activity activity,int resultCode) {
        Intent intent = new Intent(activity, NowAddressActivity.class);
        activity.startActivityForResult(intent,resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("常驻地址", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mPopWindow = new AddressSelectPopWindow(NowAddressActivity.this);
        mDataBinding.shi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowAddressActivity.this.lightOff();
                mPopWindow.addressReset();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mDataBinding.shi.setText(provinceData + cityData);
                if (!"0".equals(cityNo)){
                    if (!TextUtils.isEmpty(cityData)){
                        if (!TextUtils.isEmpty(provinceData)){
                            nowNo=cityNo;
                            nowCity=cityData;
                            nowProvence=provinceData;
                        }
                    }
                }
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                NowAddressActivity.this.lightOn();
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.xian.getText())&&!TextUtils.isEmpty(nowCity)){
                    if (!TextUtils.isEmpty(mDataBinding.details.getText())){
                        nowDetails=mDataBinding.details.getText().toString();
                    }
                    nowCountry=mDataBinding.xian.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("nowNo",nowNo);
                    intent.putExtra("nowProvence",nowProvence);
                    intent.putExtra("nowCity",nowCity);
                    intent.putExtra("nowCountry",nowCountry);
                    intent.putExtra("nowDetails",nowDetails);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(mContext, "请将地址填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_now_address;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

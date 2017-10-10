package com.lshl.ui.me.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.lshl.base.LshlApplication;
import com.lshl.bean.User;
import com.lshl.databinding.ActivityBaseDataBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.UserUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

/****
 * 基本资料的提交(完善资料的提交)
 */
public class BaseDataActivity extends BaseActivity<ActivityBaseDataBinding> {
    private Calendar mCalendar;
    private String name;
    private String useShi = "济南";
    private String useXian;
    private String useDetails = "";
    private PopupWindow sexPopupWindow;
    private String sex = "2";
    private String nowSheng;
    private String nowShi;
    private String nowXian;
    private String nowDetails = "";
    private String cityNo;
    private String time;
    private String profile;
    private PopupWindow farenPopupWindow;
    private String identity = "1";
    private View rootView;
    public static final int BASEDATA = 0x0000125;

    public static void actionStart(Activity activity, int resultCode) {
        Intent intent = new Intent(activity, BaseDataActivity.class);
        activity.startActivityForResult(intent, resultCode);
    }

    public class Presenter {
        public void sex() {
            sexPopupWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
        }

        public void time() {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            final StringBuilder sb = new StringBuilder();
            DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    sb.append(year).append("年").append(month + 1).append("月").append(dayOfMonth).append("日").append(" ");
                    time = sb.toString();
                    mDataBinding.time.setText(time);
                }
            }, year, month, day);
            dialog.setTitle("请选择时间");
            dialog.show();
        }

        public void useAddress() {
            UseAddressActivity.actionStart(BaseDataActivity.this, UseAddressActivity.USE_ADDRESS);
        }

        public void nowAddress() {
            NowAddressActivity.actionStart(BaseDataActivity.this, NowAddressActivity.NOW_ADDRESS);
        }

        public void introduce() {
            PersonIntroductionActivity.actionStart(BaseDataActivity.this, PersonIntroductionActivity.INTRODUCE);
        }

        public void faren() {
            farenPopupWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
        }

        public void commit() {
            if (!TextUtils.isEmpty(mDataBinding.name.getText()) && !TextUtils.isEmpty(time) &&
                    !TextUtils.isEmpty(useShi)
                    && !TextUtils.isEmpty(nowShi) && !TextUtils.isEmpty(profile)) {
                name = mDataBinding.name.getText().toString();
                loadCommit(name, sex, time, useShi, useXian, useDetails, nowSheng, nowShi, nowXian, nowDetails, cityNo, profile, identity);
            } else {
                showMessage("请补充完整您的个人信息,提交后将不能修改!");
            }
        }
    }

    private void loadCommit(final String name, String sex, String age, String useShi, String useXian, String useDetails, String nowSheng, String nowShi, String nowXian, String nowDetails, String cityNo
            , String profile, String identity) {

        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.String)
                .baseDatacommit(new HashMap<String, String>()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody result) {
                if (result != null) {
                    try {
                        String string = result.string();
                        JSONObject object = JSON.parseObject(string);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if (status.equals(ApiService.STATUS_SUC)) {
                            Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
                            LshlApplication.getApplication().getAuthorityBean().setBasic(1);
                            User user = LoginHelper.getInstance().getUserBean();
                            user.setRealName(name);
                            UserUtils.saveUser(mContext, user);
                            AuthenticationActivity.actionStart(BaseDataActivity.this, AuthenticationActivity.Authentication);
                            setResult(RESULT_OK);
                            finish();
                        }
                        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("完善资料", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("跳过", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.alertDialog(mContext, "是否跳过?", "真实的个人信息有助于信息的交流", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void initViews() {
        if (!TextUtils.isEmpty(mDataBinding.name.getText())) {
            name = mDataBinding.name.getText().toString();
        }
        mDataBinding.name.setText(name);
        rootView = getLayoutInflater().inflate(R.layout.activity_base_data, null);
        mDataBinding.setPres(new Presenter());
        mCalendar = Calendar.getInstance();
        initPopuWindow();
    }

    private void initPopuWindow() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_sex_popuwindow, null);
        sexPopupWindow = new PopupWindow(mContext);
        sexPopupWindow.setContentView(contentView);
        sexPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        sexPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        sexPopupWindow.setFocusable(true);
        sexPopupWindow.setOutsideTouchable(true);
        final RelativeLayout parent = (RelativeLayout) contentView.findViewById(R.id.ll_sex_parent);
        final Button boyBtn = (Button) contentView.findViewById(R.id.item_popuwindow_boy);
        final Button girlBtn = (Button) contentView.findViewById(R.id.item_popupwindow_girl);
        final Button cancelBtn = (Button) contentView.findViewById(R.id.item_popupwindow_cancel);
        boyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.sex.setText("男");
                sex = "1";
                sexPopupWindow.dismiss();
                parent.clearAnimation();
            }
        });
        girlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.sex.setText("女");
                sex = "2";
                sexPopupWindow.dismiss();
                parent.clearAnimation();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexPopupWindow.dismiss();
                parent.clearAnimation();
            }
        });
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_faren_popuwuindow, null);
        farenPopupWindow = new PopupWindow(mContext);
        farenPopupWindow.setContentView(view);
        farenPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        farenPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        farenPopupWindow.setFocusable(true);
        farenPopupWindow.setOutsideTouchable(true);
        final RelativeLayout parent1 = (RelativeLayout) view.findViewById(R.id.ll_faren_parent);
        final Button yesBtn = (Button) view.findViewById(R.id.item_popuwindow_yes);
        final Button noBtn = (Button) view.findViewById(R.id.item_popupwindow_no);
        final Button cancelBtn1 = (Button) view.findViewById(R.id.item_popupwindow_faren_cancel);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.faren.setText("是");
                identity = "1";
                farenPopupWindow.dismiss();
                parent1.clearAnimation();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.faren.setText("否");
                identity = "2";
                farenPopupWindow.dismiss();
                parent1.clearAnimation();
            }
        });
        cancelBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farenPopupWindow.dismiss();
                parent1.clearAnimation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PersonIntroductionActivity.INTRODUCE:
                    profile = data.getStringExtra("profile");
                    mDataBinding.introduce.setText(profile);
                    break;
                case UseAddressActivity.USE_ADDRESS:
                    useShi = data.getStringExtra("useShi");
                    useXian = data.getStringExtra("useXian");
                    useDetails = data.getStringExtra("useDetails");
                    mDataBinding.useAddress.setText(useShi + useXian + useDetails);
                    break;
                case NowAddressActivity.NOW_ADDRESS:
                    nowSheng = data.getStringExtra("nowProvence");
                    nowShi = data.getStringExtra("nowCity");
                    nowXian = data.getStringExtra("nowCountry");
                    nowDetails = data.getStringExtra("nowDetails");
                    cityNo = data.getStringExtra("nowNo");
                    mDataBinding.nowAddress.setText(nowSheng + nowShi + nowXian + nowDetails);
                default:
                    break;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_data;
    }


}


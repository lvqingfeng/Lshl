package com.lshl.ui.me.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import com.lshl.bean.EnterpriseAuthionBean;
import com.lshl.bean.QiyeintroductionBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityEnterpriseAuthenticationBinding;
import com.lshl.databinding.WorkPopWindowBinding;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.view.AddPopWindow;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.SinglePictureSelectPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import top.zibin.luban.OnCompressListener;

public class EnterpriseAuthenticationActivity extends BaseActivity<ActivityEnterpriseAuthenticationBinding> {
    private String name;
    private String qixian;
    private AddPopWindow popWindow;
    private SinglePictureSelectHelper selectHelper;
    private SinglePictureSelectPopWindow mPopWindow;
    private String image;
    private AddressSelectPopWindow addressSelectPopWindow;
    private String address;
    private String cityNos;
    private String enid;
    private String jobtype;
    private Calendar mCalendar;
    private String time;
    private File fileImage;
    private String code;
    private String money;
    private String fanwei;
    private List<String> mList;
    private QiyeintroductionBean beans;

    public static void actionStart(Activity activity, QiyeintroductionBean bean, ArrayList<String> mList, String name, int requestCode) {
        Intent intent = new Intent(activity, EnterpriseAuthenticationActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("mList", mList);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("添加新企业", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        mDataBinding.money.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        mDataBinding.faren.setText(LoginHelper.getInstance().getUserBean().getRealName());
        name = getIntent().getStringExtra("name");
        mList = getIntent().getStringArrayListExtra("mList");
        beans = (QiyeintroductionBean) getIntent().getSerializableExtra("bean");
        mDataBinding.name.setText(name);
        mDataBinding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                popWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
            }
        });
        popWindow = new AddPopWindow(this, R.layout.item_layout_work_popuwindow);
        final WorkPopWindowBinding bind = DataBindingUtil.bind(popWindow.getWindowRootView());
        bind.itemWorkFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkFirst.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkTwo.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkThree.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobtype = bind.itemWorkFour.getText().toString();
                mDataBinding.type.setText(jobtype);
                popWindow.dismiss();
            }
        });
        bind.itemWorkCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        addressSelectPopWindow = new AddressSelectPopWindow(this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                address = provinceData + cityData;
                cityNos = cityNo;
                mDataBinding.address.setText(address);
            }
        });
        mCalendar = Calendar.getInstance();
        mDataBinding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        mDataBinding.qixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                final StringBuilder sb = new StringBuilder();
                DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sb.append(year).append("年").append(month + 1).append("月").append(dayOfMonth).append("日").append(" ");
                        qixian = sb.toString();
                        mDataBinding.qixian.setText(qixian);
                    }
                }, year, month, day);
                dialog.setTitle("请选择时间");
                dialog.show();
            }
        });


        selectHelper = new SinglePictureSelectHelper().getInstance().init(EnterpriseAuthenticationActivity.this);
        mPopWindow = new SinglePictureSelectPopWindow(this);
        mPopWindow.bindHelper(selectHelper);
        mDataBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowAddressActivity.actionStart(EnterpriseAuthenticationActivity.this, NowAddressActivity.NOW_ADDRESS);
//                lightOff();
//                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.code.getText())) {
                    code = mDataBinding.code.getText().toString();
                } else {
                    showMessage("请输入社会统一信用代码");
                    return;
                }
                if (!TextUtils.isEmpty(jobtype)) {

                } else {
                    showMessage("请选择企业类型");
                    return;
                }
                if (!TextUtils.isEmpty(cityNos)) {

                } else {
                    showMessage("请选择地址");
                    return;
                }

                if (!TextUtils.isEmpty(mDataBinding.money.getText())) {
                    money = mDataBinding.money.getText().toString();
                } else {
                    showMessage("请输入注册资金");
                    return;
                }
                if (!TextUtils.isEmpty(time)) {

                } else {
                    showMessage("请选择注册时间");
                    return;
                }
                if (!TextUtils.isEmpty(qixian)) {

                } else {
                    showMessage("请选择营业期限");
                    return;
                }
                if (!TextUtils.isEmpty(mDataBinding.fanwei.getText())) {

                } else {
                    showMessage("请输入经营范围");
                    return;
                }
                if (!TextUtils.isEmpty(image)) {
                    fanwei = mDataBinding.fanwei.getText().toString();
                } else {
                    showMessage("请上传营业执照");
                    return;
                }
                loadAddCommit(ApiClient.createBodyMap(beans));
            }
        });
    }

    private void loadAddCommit(final Map<String, String> body) {
        BitmapUtils.pictureCompression(mContext, mList, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(final List<String> imageUrl) {
                DialogUtils.showProgressDialog(mContext, "正在上传中", "", "");
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.Object)
                        .qiyeDesctrubion(ApiClient.getImageParams("imgfile", imageUrl), body), new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                        Toast.makeText(mContext, "恭喜您已上传成功！", Toast.LENGTH_SHORT).show();
                        enid = result.getInfo();
                        EnterpriseAuthionBean bean = new EnterpriseAuthionBean();
                        bean.setToken(LoginHelper.getInstance().getUserToken());
                        bean.setType(jobtype);
                        bean.setAddress(address);
                        bean.setCode(code);
                        bean.setCapital(money);
                        bean.setCityno(cityNos);
                        bean.setEstablish(time);
                        bean.setDeadline(qixian);
                        bean.setBranched(fanwei);
                        bean.setEnid(enid);
                        Map<String, String> bodyMap = ApiClient.createBodyMap(bean);
                        loadCommit(bodyMap);
                    }
                });
            }
        });
    }

    private void loadCommit(Map<String, String> body) {
        Map<String, RequestBody> params = new HashMap<>();
        if (fileImage.length() > 0) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), fileImage);
            params.put("imgfile[]\"; filename=\"icon" + image + ".png", photo);
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                    .qiyeAuthion(params, body), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                @Override
                public void onNext(HttpResult<String> result) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }));
        } else {
            Toast.makeText(mContext, "请上传营业执照", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image = selectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.image, false);
        if (image != null) {
            File file2 = new File(image);
            if (file2.length() > 200 * 1024) {
                pictureCompression(file2);
            } else {
                fileImage = file2;
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == NowAddressActivity.NOW_ADDRESS) {
                cityNos = data.getStringExtra("nowNo");
                String sheng = data.getStringExtra("nowProvence");
                String shi = data.getStringExtra("nowCity");
                String xian = data.getStringExtra("nowCountry");
                String xiang = data.getStringExtra("nowDetails");
                address = sheng + shi + xian + xiang;
                mDataBinding.address.setText(address);
            }
        }
    }

    private void pictureCompression(File file) {
        ApiClient.pictureCompression(mContext, file, new OnCompressListener() {
            @Override
            public void onStart() {
                DialogUtils.showProgressDialog(mContext, "请稍候...", null, "压缩失败");
            }

            @Override

            public void onSuccess(File file) {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                fileImage = file;
            }

            @Override
            public void onError(Throwable e) {
                Log.d("图片压缩出现失败", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enterprise_authentication;
    }
}

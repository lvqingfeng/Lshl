package com.lshl.ui.me.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
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
import com.lshl.bean.BusinessDetailsBean;
import com.lshl.bean.request.EditEnterpriseBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityEditEnterpriseBinding;
import com.lshl.databinding.SelectPhotoPopBinding;
import com.lshl.databinding.WorkPopWindowBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.view.AddPopWindow;
import com.lshl.view.AddressSelectPopWindow;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.functions.Action1;
import top.zibin.luban.OnCompressListener;

public class EditEnterpriseActivity extends BaseActivity<ActivityEditEnterpriseBinding> {
    private String name = "";
    private String qixian;
    private AddPopWindow popWindow;
    private SinglePictureSelectHelper selectHelper;
    private String image;
    private AddPopWindow addPopWindow;
    private AddressSelectPopWindow addressSelectPopWindow;
    private String address = "";
    private String cityNos;
    private String enid;
    private String jobtype = "";
    private Calendar mCalendar;
    private String time = "";
    private File fileImage;
    private String code = "";
    private String money = "";
    private String fanwei = "";
    private BusinessDetailsBean.InfoBean info;
    private ArrayList<String> mList;

    public static void actionStart(Activity activity, BusinessDetailsBean.InfoBean info, int requestCode) {
        Intent intent = new Intent(activity, EditEnterpriseActivity.class);
        intent.putExtra("info", info);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("修改企业", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("保存", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        selectHelper = SinglePictureSelectHelper.getInstance().init(this);
        addPopWindow = new AddPopWindow(this, R.layout.layout_pop_select_photo);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        info = (BusinessDetailsBean.InfoBean) getIntent().getSerializableExtra("info");
        enid = info.getEn_id();
        initBaseData();
        initPopWindow();
        initPopSelectPhoto();
        mDataBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopWindow.showPopupWindow(mDataBinding.llZhizhao);
                lightOff();
            }
        });
        mDataBinding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                popWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
            }
        });
        mDataBinding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowAddressActivity.actionStart(EditEnterpriseActivity.this, NowAddressActivity.NOW_ADDRESS);
//                lightOff();
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
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
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (name.equals(info.getEn_name())
//                        &&jobtype.equals(info.getEn_type())
//                        &&address.equals(info.getEn_address())
//                        &&money.equals(info.getEn_capital())
//                        &&time.equals(info.getEn_establish())
//                        &&fanwei.equals(info.getEn_branched())
//                        &&qixian.equals(info.getEn_deadline())
//                        &&code.equals(info.getEn_code())
//                        &&TextUtils.isEmpty(image)){
//                    showMessage("没做修改,无需保存");
//                }else {
                EditEnterpriseBean bean = new EditEnterpriseBean();
                bean.setEnid(enid);
                bean.setToken(LoginHelper.getInstance().getUserToken());
                bean.setAddress(address);
                bean.setCode(code);
                bean.setCityno(cityNos);
                bean.setType(jobtype);
                bean.setCapital(money);
                bean.setBranched(fanwei);
                bean.setName(name);
                bean.setEstablish(time);
                bean.setDeadline(qixian);
                Map<String, String> bodyMap = ApiClient.createBodyMap(bean);
                if (TextUtils.isEmpty(image)) {
                    editTextEnterprise(bean);
                } else {
                    updateEditEnterprise(bodyMap);
                }
//                }
            }
        });
        /***
         * 修改更多资料
         * */
        mDataBinding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditEnterpriseImageActivity.actionStart(EditEnterpriseActivity.this, info);
            }
        });
    }

    private void editTextEnterprise(EditEnterpriseBean bean) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .enterpriseEditText(info.getEn_id(), bean.getCode(), bean.getAddress(), bean.getCityno(), bean.getType(), bean.getCapital(), bean.getEstablish(), bean.getDeadline(), bean.getBranched(), bean.getName(), bean.getToken()), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void updateEditEnterprise(final Map<String, String> body) {
        Map<String, RequestBody> params = new HashMap<>();
        if (!TextUtils.isEmpty(image)) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), fileImage);
            params.put("imgfile\"; filename=\"icon" + image + ".png", photo);
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                    , RetrofitManager.RetrofitType.Object).enterpriseMessageModify(params, body),
                    new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }));
        }
    }

    private void initBaseData() {
        mDataBinding.name.setText(info.getEn_name());
        mDataBinding.code.setText(info.getEn_code());
        mDataBinding.address.setText(info.getEn_address());
        mDataBinding.time.setText(info.getEn_establish());
        mDataBinding.qixian.setText(info.getEn_deadline());
        mDataBinding.money.setText(info.getEn_capital());
        mDataBinding.fanwei.setText(info.getEn_branched());
        mDataBinding.type.setText(info.getEn_type());
        if (!TextUtils.isEmpty(mDataBinding.name.getText())) {
            name = mDataBinding.name.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.type.getText())) {
            jobtype = mDataBinding.type.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.fanwei.getText())) {
            fanwei = mDataBinding.fanwei.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.money.getText())) {
            money = mDataBinding.money.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.time.getText())) {
            time = mDataBinding.time.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.code.getText())) {
            code = mDataBinding.code.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.address.getText())) {
            address = mDataBinding.address.getText().toString();
        }
        if (!TextUtils.isEmpty(mDataBinding.qixian.getText())) {
            qixian = mDataBinding.qixian.getText().toString();
        }
    }

    private void initPopWindow() {
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
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    /**
     * PopWindow按钮的点击事件
     */
    private class PopClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    break;
                case R.id.tv_open_camera:
                    RxPermissions.getInstance(mContext)
                            .request(Manifest.permission.CAMERA)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean granted) {
                                    if (granted) {
                                        selectHelper.openCameraForFile();
                                    } else {
                                        Toast.makeText(mContext, "只有同意权限后才能进行拍照,请您在应用详情中进行权限修改", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                case R.id.tv_open_album:
                    selectHelper.openAlbum();
                    break;
            }
            closeSelectPhotoPop();
        }
    }

    private void initPopSelectPhoto() {

        addPopWindow.setAnimationStyle(R.style.PopWindow_y_anim_style);
        SelectPhotoPopBinding selectPhotoPopBinding = SelectPhotoPopBinding.bind(addPopWindow.getWindowRootView());
        EditEnterpriseActivity.PopClickListener listener = new EditEnterpriseActivity.PopClickListener();
        selectPhotoPopBinding.btnCancel.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenCamera.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenAlbum.setOnClickListener(listener);
        addPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    /**
     * 关闭SelectPhotoPop页面
     */
    private void closeSelectPhotoPop() {
        addPopWindow.closePopupWindow();
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
                DialogUtils.showProgressDialog(mContext, "请稍后...", null, "压缩失败");
            }

            @Override

            public void onSuccess(File file) {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                fileImage = file;
            }

            @Override
            public void onError(Throwable e) {
                Log.d("图片压缩出现问题", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_enterprise;
    }
}

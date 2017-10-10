package com.lshl.ui.me.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.User;
import com.lshl.bean.request.UserInfoRequestBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityCreateCardsBinding;
import com.lshl.ui.addressthree.AddressThActivity;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.UserUtils;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.ShanDongSelectPopWindow;
import com.lshl.view.SinglePictureSelectPopWindow;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import top.zibin.luban.OnCompressListener;

public class CreateCardsActivity extends BaseActivity<ActivityCreateCardsBinding> {

    private static final int ADD_OLD_ADDRESS = 0x0000123;
    private static final int ADD_NEW_ADDRESS = 0x0000124;
    private static final int ADD_ADDRESS_TH = 0x0000129;

    private SinglePictureSelectHelper mSinglePictureHelper;
    private SinglePictureSelectPopWindow mSinglePictureWindow;

    private String mHeadImageUrl;

    private Calendar mCalendar;

    private UserInfoRequestBean mUserRequestBean;

    private PersonBasedataBean.InfoBean mPersonDataBean;
    private PopupWindow popupWindow;
    private ShanDongSelectPopWindow shanDongSelectPopWindow;
    private String cityId = "";
    private String addressName = "";

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CreateCardsActivity.class);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, PersonBasedataBean.InfoBean infoBean) {
        Intent intent = new Intent(activity, CreateCardsActivity.class);
        intent.putExtra("bean", infoBean);
        activity.startActivity(intent);
    }

    public class Presenter {

        public void onClickInputRealName(EditText text) {
            text.setCursorVisible(true);
        }

        /*开启拍照*/
        public void onClickOpenCamera() {
            lightOff();
            mSinglePictureWindow.showPopupWindow(mDataBinding.getRoot());
        }

        /*点击提交基本信息*/
        public void onClickSubmit() {
            if (TextUtils.isEmpty(mHeadImageUrl)) {
                showMessage("请选择您的头像");
                return;
            }
            String name = mDataBinding.editRealname.getText().toString().trim();
            String birthday = mDataBinding.tvBirthday.getText().toString();
            String sex = "";
            String oldAddr = mDataBinding.tvOldAddress.getText().toString();
            String nowAddr = mDataBinding.tvNowAddress.getText().toString();
            if (mDataBinding.radioMan.isChecked()) {
                sex = "1";
            } else if (mDataBinding.radioWoman.isChecked()) {
                sex = "2";
            }
            mUserRequestBean.setRealname(name);
            mUserRequestBean.setAge(birthday);
            mUserRequestBean.setRd(sex);

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(birthday) || TextUtils.isEmpty(sex)) {
                showMessage("请填写您的基本资料");
            } else {
                mUserRequestBean.setToken(LoginHelper.getInstance().getUserToken());
                if (mUserRequestBean.hasEmpty()) {
                    showMessage("请将信息填写完整");
                } else {
                    Map<String, String> bodyMap = ApiClient.createBodyMap(mUserRequestBean);
                    AuthorityCheckBean bean = LshlApplication.getApplication().getAuthorityBean();
                    if (bean == null) {
                        loadUserAuthority(bodyMap);
                    } else {
                        loadCommit(bodyMap);
                    }

                }
            }

        }

        /*选择出生年月日*/
        public void onClickSelectBirthday() {
            mDataBinding.editRealname.setCursorVisible(false);
            final int yearNow = mCalendar.get(Calendar.YEAR);
            int monthNow = mCalendar.get(Calendar.MONTH);
            int dayNow = mCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if (year > (yearNow)) {
                        Toast.makeText(mContext, "请选择正确的出生日期^_^", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mDataBinding.tvBirthday.setText(String.valueOf(year) + "年" + (month + 1) + "月" + dayOfMonth + "日" + " ");
                }
            }, yearNow, monthNow, dayNow);
            dialog.setTitle("请选择时间");
            dialog.show();
        }

        /*选择老家地址*/
        public void useAddress() {
            mDataBinding.editRealname.setCursorVisible(false);
            lightOff();
            shanDongSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
//            UseAddressActivity.actionStart(CreateCardsActivity.this, ADD_OLD_ADDRESS);
        }

        /*选择现居住地址*/
        public void nowAddress() {
            mDataBinding.editRealname.setCursorVisible(false);
//            NowAddressActivity.actionStart(CreateCardsActivity.this, ADD_NEW_ADDRESS);
            Intent intent = new Intent(CreateCardsActivity.this, AddressThActivity.class);
            startActivityForResult(intent, ADD_ADDRESS_TH);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imageUrl = mSinglePictureHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.ivHead, true);
        if (!TextUtils.isEmpty(imageUrl)) {
            mHeadImageUrl = imageUrl;
        }
        switch (requestCode) {
            case ADD_OLD_ADDRESS:
                if (resultCode == RESULT_OK) {
                    mUserRequestBean.setOrigin_cityname(data.getStringExtra("useShi"));
                    mUserRequestBean.setOrigin_county(data.getStringExtra("useXian"));
                    mUserRequestBean.setOrigin_address(data.getStringExtra("useDetails"));

                    mDataBinding.tvOldAddress.setText(mUserRequestBean.getOrigin_cityname() + mUserRequestBean.getOrigin_county() + mUserRequestBean.getOrigin_address());
                }
                break;
            case ADD_NEW_ADDRESS:
                if (resultCode == RESULT_OK) {
                    String nowSheng = data.getStringExtra("nowProvence");
                    String nowShi = data.getStringExtra("nowCity");

                    mUserRequestBean.setLive_cityname(nowSheng + nowShi);
                    mUserRequestBean.setLive_county(data.getStringExtra("nowCountry"));
                    mUserRequestBean.setLive_address(data.getStringExtra("nowDetails"));
                    mUserRequestBean.setLive_cityno(data.getStringExtra("nowNo"));

                    mDataBinding.tvNowAddress.setText(nowSheng + nowShi + mUserRequestBean.getLive_county() + mUserRequestBean.getLive_address());
                }
                break;
            case ADD_ADDRESS_TH:
                if (resultCode == RESULT_OK) {
                    cityId = data.getStringExtra("cityId");
                    addressName = data.getStringExtra("addressName");
                    mDataBinding.tvNowAddress.setText(addressName);
                }
                mUserRequestBean.setLive_county(" ");
                mUserRequestBean.setLive_address(" ");
                mUserRequestBean.setLive_cityname(addressName);
                mUserRequestBean.setLive_cityno(cityId);
                break;
            default:
                if (resultCode == RESULT_OK) {
                    File headImageFile = new File(mHeadImageUrl);
                    if (headImageFile.length() >= 1024 * 1024 * 1.5) {
                        //对图片进行压缩处理
                        ApiClient.pictureCompression(mContext, new File(mHeadImageUrl), new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {
                                updateHeadImage(file);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                    } else {
                        updateHeadImage(headImageFile);
                    }
                }
                break;
        }

    }

    /****
     * 上传头像
     */
    private void updateHeadImage(File file) {
        MultipartBody.Part part = ApiClient.getFileBody(file);
        DialogUtils.showProgressDialog(mContext, "头像上传中...", "", "");
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).postHeadImage(part, RequestBody.create(null, LoginHelper.getInstance().getUserToken()))
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(mContext, "头像上传成功", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("创建名片", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("跳过", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {

        mPersonDataBean = (PersonBasedataBean.InfoBean) getIntent().getSerializableExtra("bean");

        mUserRequestBean = new UserInfoRequestBean();
        mSinglePictureHelper = SinglePictureSelectHelper.getInstance();
        mSinglePictureWindow = new SinglePictureSelectPopWindow(this);
        mCalendar = Calendar.getInstance();

    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mSinglePictureHelper.init(this);
        mSinglePictureWindow.bindHelper(mSinglePictureHelper);
        mSinglePictureWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        /****
         * 回车不换行  名字不能换行
         * */
        mDataBinding.editRealname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        if (mPersonDataBean != null) {
            getRightView().setVisibility(View.GONE);
            openTitleLeftView(true);
            mHeadImageUrl = mPersonDataBean.getAvatar();
            mDataBinding.setPersonDataBean(mPersonDataBean);
            mUserRequestBean.setOrigin_cityname(mPersonDataBean.getOrigin_cityname());
            mUserRequestBean.setOrigin_county(mPersonDataBean.getOrigin_county());
            mUserRequestBean.setOrigin_address(mPersonDataBean.getOrigin_address());
            mUserRequestBean.setLive_cityno(mPersonDataBean.getLive_cityno());
            mUserRequestBean.setLive_cityname(mPersonDataBean.getLive_cityname());
            mUserRequestBean.setLive_county(mPersonDataBean.getLive_county());
            mUserRequestBean.setLive_address(mPersonDataBean.getLive_address());
        }
        initPopuWindow();
        shanDongSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mUserRequestBean.setOrigin_cityname(provinceData + cityData);
                mUserRequestBean.setOrigin_county(" ");
                mUserRequestBean.setOrigin_address(" ");
                mDataBinding.tvOldAddress.setText(provinceData + " " + cityData);
            }
        });
        shanDongSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    private void initPopuWindow() {
        popupWindow = new PopupWindow(mContext);
        shanDongSelectPopWindow = new ShanDongSelectPopWindow(CreateCardsActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_cards;
    }

    private void loadUserAuthority(final Map<String, String> bodyMap) {
        DialogUtils.showProgressDialog(mContext, "正在获取个人基本资料", "", "");
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).authortyCheck(LoginHelper.getInstance().getUserToken()), new Subscriber<HttpResult<AuthorityCheckBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "获取失败，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(HttpResult<AuthorityCheckBean> authorityCheckBeanHttpResult) {
                        LshlApplication.getApplication().setAuthorityBean(authorityCheckBeanHttpResult.getInfo());
                        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                        loadCommit(bodyMap);
                    }
                }
        );
    }

    /*提交基本信息*/
    private void loadCommit(final Map<String, String> bodyMap) {

        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.String)
                .baseDatacommit(bodyMap), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody result) {
                if (result != null) {
                    try {
                        String string = result.string();
                        JSONObject object = JSON.parseObject(string);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if (status.equals(ApiService.STATUS_SUC)) {
                            LshlApplication.getApplication().getAuthorityBean().setBasic(1);
                            User user = LoginHelper.getInstance().getUserBean();
                            user.setRealName(bodyMap.get("realname"));
                            UserUtils.saveUser(mContext, user);
                            setResult(RESULT_OK);
                            finish();
                            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }
}

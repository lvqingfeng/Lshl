package com.lshl.ui.me.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityPersonDataBinding;
import com.lshl.databinding.SelectPhotoPopBinding;
import com.lshl.task.TaskBase;
import com.lshl.ui.addressthree.AddressThActivity;
import com.lshl.utils.AuthorityHelp;
import com.lshl.view.AddPopWindow;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * 个人基本资料
 */
public class PersonDataActivity extends BaseActivity<ActivityPersonDataBinding> {
    private boolean isCompany;
    private String uid;
    private String realname;
    private AddPopWindow addPopWindow;
    private SinglePictureSelectHelper selectHelper;
    private String mImageUrl;
    private PersonBasedataBean.InfoBean bean;
    private String authenticate;
    private static final int ADD_ADDRESS_TH = 0x0000130;
    private String cityId;
    private String addressName;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PersonDataActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("个人资料", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("修改");
    }

    @Override
    protected void initFieldBeforeMethods() {
        selectHelper = SinglePictureSelectHelper.getInstance().init(this);
        addPopWindow = new AddPopWindow(this, R.layout.layout_pop_select_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageUrl = selectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.headImage, true);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BaseDataActivity.BASEDATA:
                    loadBaseData();
                    break;
                case AuthenticationActivity.Authentication:
                    loadBaseData();
                    mDataBinding.llCompany.setVisibility(View.GONE);
                    break;
                case EditNowAddressActivity.EDIT_NOW_ADDRESS:
                    loadBaseData();
                    break;
                case ADD_ADDRESS_TH:
                    cityId = data.getStringExtra("cityId");
                    addressName = data.getStringExtra("addressName");
                    updateNowAddress(addressName, cityId);
                    break;

            }
        }
    }

    private void updateNowAddress(String address, String cityNo) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .editNowAddress(LoginHelper.getInstance().getUserToken(), address, "", "", cityNo), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    loadBaseData();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    /**
     * 关闭SelectPhotoPop页面
     */
    private void closeSelectPhotoPop() {
        addPopWindow.closePopupWindow();
    }

    private void initPopSelectPhoto() {

        addPopWindow.setAnimationStyle(R.style.PopWindow_y_anim_style);
        SelectPhotoPopBinding selectPhotoPopBinding = SelectPhotoPopBinding.bind(addPopWindow.getWindowRootView());
        PersonDataActivity.PopClickListener listener = new PersonDataActivity.PopClickListener();
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

    @Override
    protected void initViews() {
//        mDataBinding.headImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addPopWindow.showPopupWindow(mDataBinding.llUserInfo);
//                lightOff();
//            }
//        });
        initPopSelectPhoto();
        loadBaseData();

        mDataBinding.enterpriseMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        EnterpriseActivity.actionStart(PersonDataActivity.this);
                    }
                });
            }
        });

        mDataBinding.llCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.actionStart(PersonDataActivity.this, AuthenticationActivity.Authentication);
            }
        });

        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCardsActivity.actionStart(PersonDataActivity.this, bean);
            }
        });
//        if (bean != null) {
//            if (TextUtils.isEmpty(realname)) {
//                DialogUtils.alertDialog(mContext, "个人资料不完整", "是否进入编辑", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//                    }
//                }, new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismissWithAnimation();
//                        BaseDataActivity.actionStart(PersonDataActivity.this,BaseDataActivity.BASEDATA);
//                    }
//                });
//            }
//        }
        mDataBinding.authentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!authenticate.equals("1")) {
                    mDataBinding.authentication.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AuthenticationActivity.actionStart(PersonDataActivity.this, AuthenticationActivity.Authentication);
                        }
                    });
                }
            }
        });
        mDataBinding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditNowAddressActivity.actionStart(PersonDataActivity.this, EditNowAddressActivity.EDIT_NOW_ADDRESS);
                Intent intent = new Intent(PersonDataActivity.this, AddressThActivity.class);
                startActivityForResult(intent, ADD_ADDRESS_TH);
            }
        });
        mDataBinding.zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRcodeActivity.actionStart(PersonDataActivity.this);
            }
        });
        mDataBinding.imageWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageWallActivity.actionStart(PersonDataActivity.this);
            }
        });
        mDataBinding.industry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyIndustryActivity.actionStart(PersonDataActivity.this);
            }
        });
    }

    //    private void pictureCompression(File file){
//        ApiClient.pictureCompression(mContext, file, new OnCompressListener() {
//            @Override
//            public void onStart() {
//                DialogUtils.showProgressDialog(mContext, "请稍后...", null, "压缩失败");
//            }
//
//            @Override
//
//            public void onSuccess(File file) {
//                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
//                updateHeadIamge(file);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("图片压缩出现问题", e.getMessage());
//                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
//            }
//        });
//    }
//    private void updateHeadIamge(File file){
//        MultipartBody.Part part = ApiClient.getFileBody(file);
//        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String).postHeadImage(part, RequestBody.create(null, LoginHelper.getInstance().getUserToken())), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
//            @Override
//            public void onNext(ResponseBody result) {
//                try {
//                    String string = result.string();
//                    JSONObject object = JSON.parseObject(string);
//                    String status = object.getString("status");
//                    String info = object.getString("info");
//                    if (status.equals(ApiService.STATUS_SUC)) {
//                        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
//                        getRightView().setText("");
//                    }else {
//                        Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }));
//    }
    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updatePersonBaseData(LoginHelper.getInstance()
                .getUserToken(), LoginHelper.getInstance().getUserBean().getUid(), ""), new ProgressSubscriber<>(mContext
                , new SubscriberOnNextListener<PersonBasedataBean>() {

            @Override
            public void onNext(PersonBasedataBean result) {
                if (result != null) {
                    if (result.getStatus() == 1) {
                        bean = result.getInfo();
//                        if (TextUtils.isEmpty(bean.getRealname())) {
//                            DialogUtils.alertDialog(mContext, "个人资料不完整", "是否进入编辑", new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                }
//                            }, new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    sweetAlertDialog.dismissWithAnimation();
//                                    BaseDataActivity.actionStart(PersonDataActivity.this,BaseDataActivity.BASEDATA);
//                                }
//                            });
//                        }
                        authenticate = bean.getAuthenticate();
                        if (bean.getAuthenticate().equals("0")) {
                            mDataBinding.authentication.setText("未认证");
                            mDataBinding.llCompany.setVisibility(View.VISIBLE);
                        } else if (bean.getAuthenticate().equals("1")) {
                            mDataBinding.authentication.setText("已认证");
                            AuthorityHelp.setRealnameStatus(1);
                            getRightView().setVisibility(View.GONE);
                        } else if (bean.getAuthenticate().equals("2")) {
                            mDataBinding.authentication.setText("待审核");
                        }
                        Constant.getGradeForAll(bean.getGrade(), mDataBinding.grade);
                        uid = bean.getUid();
                        realname = bean.getRealname();
                        mDataBinding.etName.setText(bean.getRealname());
                        if (result.getInfo().getAvatar() != null) {
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).error(R.mipmap.account_logo).into(mDataBinding.headImage);
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).thumbnail(0.1f).error(R.mipmap.default_background).into(mDataBinding.background);
                        }
//                        if (result.getInfo().getImage_wall().size()>0){
//                            Glide.with(mContext).load(ApiClient.getImageWallImage(result.getInfo().getImage_wall().get(0))).into(mDataBinding.background);
//                        }else {
//                            mDataBinding.background.setImageResource(R.mipmap.default_background);
//                        }
                        if (bean.getSex().equals("1")) {
                            mDataBinding.etSex.setText("男");
                        } else {
                            mDataBinding.etSex.setText("女");
                        }
                        mDataBinding.nowAddress.setText(bean.getLj_county());
                        mDataBinding.etAddress.setText(bean.getXj_county());
                        mDataBinding.etBirthday.setText(bean.getAge());
                    }
                }
            }
        }));
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

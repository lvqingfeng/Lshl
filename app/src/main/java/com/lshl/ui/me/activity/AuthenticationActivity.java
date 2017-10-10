package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthenticationBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.ResultInfoBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityAuthenticationBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.LogInUtils;
import com.lshl.view.SinglePictureSelectPopWindow;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import top.zibin.luban.OnCompressListener;

/**
 * 实名认证
 */
public class AuthenticationActivity extends BaseActivity<ActivityAuthenticationBinding> {

    private boolean type;
    private String name;
    private File fileZ;
    private File fileF;
    private String image1, image2;
    private SinglePictureSelectHelper mSinglePictureSelectHelper;
    private SinglePictureSelectPopWindow mPopWindow;
    public static final int Authentication = 0x0000221;

    public static void actionStart(Activity activity, int resultCode) {
        Intent intent = new Intent(activity, AuthenticationActivity.class);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class Presenter {
        public void savePersonData() {
            String idCard = null;
            if (!TextUtils.isEmpty(mDataBinding.etCardNum.getText())) {
                idCard = mDataBinding.etCardNum.getText().toString();
                if (LogInUtils.IDCardValidate(idCard)) {

                } else {
                    Toast.makeText(mContext, "请输入正确的身份证号码", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(mContext, "请输入身份证号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (fileZ == null || fileF == null) {
                Toast.makeText(mContext, "请选择身份证照片", Toast.LENGTH_SHORT).show();
                return;
            }
            final String finalIdCard = idCard;
            DialogUtils.alertDialog(mContext, "温馨提示", "是否要提交以上内容?", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                    AuthenticationBean bean = new AuthenticationBean();
                    bean.setToken(LoginHelper.getInstance().getUserToken());
                    bean.setIdcard(finalIdCard);
                    Map<String, String> bodyMap = ApiClient.createBodyMap(bean);
                    authentiCation(bodyMap);
                }
            });
        }

        public void cardGetPhotof() {
            type = false;
            lightOff();
            mPopWindow.showPopupWindow(mDataBinding.getRoot());
        }

        public void cardGetPhotoz() {
            type = true;
            lightOff();
            mPopWindow.showPopupWindow(mDataBinding.getRoot());
        }
    }

    private void authentiCation(Map<String, String> body) {
        if (fileZ != null && fileF != null) {
            Log.i("屮艸芔茻", fileZ.length() + "===" + fileF.length());
            DialogUtils.showProgressDialog(mContext, "正在上传中", "", "");
            Map<String, RequestBody> params = new HashMap<>();
            RequestBody photo1 = RequestBody.create(MediaType.parse("image/png"), fileZ);
            RequestBody photo2 = RequestBody.create(MediaType.parse("image/png"), fileF);
            params.put("file[]\"; filename=\"icon" + image1 + ".png", photo1);
            params.put("file[]\"; filename=\"icon" + image2 + ".png", photo2);
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).authentication(params, body), new Subscriber<ResultInfoBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ResultInfoBean resultInfoBean) {
                    if (resultInfoBean != null) {
                        if (resultInfoBean.getStatus() == 1) {
                            Log.i("屮艸芔茻", resultInfoBean.getInfo() + "==" + image1 + image2);
                            LshlApplication.getApplication().getAuthorityBean().setBasic(2);
                            Toast.makeText(mContext, resultInfoBean.getInfo(), Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
                            DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
                        }
                    }
                }
            });
        } else {
            Toast.makeText(mContext, "请选择身份证照片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("实名认证", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("跳过");
        getRightView().setOnClickListener(new View.OnClickListener() {
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
        loadBaseData();
        mDataBinding.etName.setText(name);
        mDataBinding.setPresenter(new Presenter());
        mSinglePictureSelectHelper = new SinglePictureSelectHelper().getInstance().init(AuthenticationActivity.this);
        mPopWindow = new SinglePictureSelectPopWindow(this);
        mPopWindow.bindHelper(mSinglePictureSelectHelper);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updatePersonData(LoginHelper.getInstance().getUserToken(), null),
                new Subscriber<PersonBasedataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PersonBasedataBean result) {
                        if (result != null) {
                            mDataBinding.etName.setText(result.getInfo().getRealname());
                            mDataBinding.etCardNum.setText(result.getInfo().getIdcard());
//                            Glide.with(mContext).load(ApiClient.getIdCardImage(result.getInfo().getIdcard_img_z())).error(R.mipmap.s).into(mDataBinding.ivCardZ);
//                            Glide.with(mContext).load(ApiClient.getIdCardImage(result.getInfo().getIdcard_img_f())).error(R.mipmap.id_card_f).into(mDataBinding.ivCardF);
                            if (!TextUtils.isEmpty(result.getInfo().getIdcard())) {
                                mDataBinding.button.setVisibility(View.GONE);
                                mDataBinding.etCardNum.setEnabled(false);
                                mDataBinding.ivCardF.setEnabled(false);
                                mDataBinding.ivCardZ.setEnabled(false);
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (type) {
            image1 = mSinglePictureSelectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.ivCardZ, false);
            if (image1 != null) {
                File file = new File(image1);
                if (file.length() > 200 * 1024) {
                    pictureCompression(file);
                } else {
                    fileZ = file;
                }
            }
        } else {
            image2 = mSinglePictureSelectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.ivCardF, false);
            if (image2 != null) {
                File file = new File(image2);
                if (file.length() > 200 * 1024) {
                    pictureCompression(file);
                } else {
                    fileF = file;
                }
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
                if (type) {
                    fileZ = file;
                } else {
                    fileF = file;
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("压缩失败", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });
    }

}

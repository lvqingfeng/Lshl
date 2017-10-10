package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.request.SendLookHelpBean;
import com.lshl.databinding.ActivityPersonLookHelpsBinding;
import com.lshl.ui.me.adapter.ReleaseReputationImageAdapter;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ShareHelper;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

import static com.lshl.ui.me.imagegrid.ImageGridActivity.RESULT_DATA;

public class SendLookHelpsActivity extends BaseActivity<ActivityPersonLookHelpsBinding> {
    public static final int MAX_IMAGE = 6;

    private static final int REQUEST_CODE_ADD_IMAGE = 0x000132;
    private ReleaseReputationImageAdapter mImageAdapter;
    private BaiduMapLocationHelper mLocationHelper;
    private AddressSelectPopWindow mAddressPop;
    private List<String> mShareList;
    private List<String> mImageList;
    private String address = "";
    private String cityNos;
    private ShareHelper mShareHelper;
    private String mInfo;
    private String mTitle;
    public static final int REQUEST_CODE_LOOK = 0x000123;

    public static void actionStart(Activity activity, int RequestCode) {
        Intent intent = new Intent(activity, SendLookHelpsActivity.class);
        activity.startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationHelper.locationStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityForResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_IMAGE) {
            if (resultCode == RESULT_OK) {
                mImageList.clear();
                mImageList.addAll(data.getStringArrayListExtra(RESULT_DATA));
                mImageAdapter.addImageUrl(mImageList);
            }
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("找帮手", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mShareHelper = new ShareHelper(mContext);
        mImageList = new ArrayList<>();
        mShareList = new ArrayList<>();
        mAddressPop = new AddressSelectPopWindow(this);
        mLocationHelper = BaiduMapLocationHelper.getInstance();
        mLocationHelper.init(mContext);
        mImageAdapter = new ReleaseReputationImageAdapter(this, REQUEST_CODE_ADD_IMAGE, 6);
    }

    @Override
    protected void initViews() {
        mShareHelper.bindShareLayout(mDataBinding.shareLayout);
        mDataBinding.recyclerImage.setLayoutManager(new GridLayoutManager(mContext, 3));
        mDataBinding.recyclerImage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                if (position == 2 || position == 5) {
                    outRect.set(0, 0, 0, 10);
                    return;
                }
                outRect.set(0, 0, 10, 10);
            }
        });
        mDataBinding.recyclerImage.setAdapter(mImageAdapter);
        mLocationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                address = location.getProvince() + location.getCity();
                mDataBinding.tvCityName.setText(address);
            }
        });
        mAddressPop.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                cityNos = cityNo;
                mDataBinding.tvCityName.setText(provinceData + "" + cityData);
            }
        });
        mDataBinding.llSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mAddressPop.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mAddressPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.btnSubmitReputation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = mDataBinding.editInfo.getText().toString().trim();
                String title = mDataBinding.editTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    showMessage("请输入标题");
                    return;
                } else {
                    mTitle = title;
                }
                if (TextUtils.isEmpty(info)) {
                    showMessage("请输入内容");
                    return;
                } else {
                    mInfo = info;
                }
                DialogUtils.alertDialog(mContext, "温馨提示", "是否确认要提交以上内容"
                        , new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                SendLookHelpBean bean = new SendLookHelpBean();
                                bean.setToken(LoginHelper.getInstance().getUserToken());
                                bean.setInfo(mInfo);
                                bean.setTitle(mTitle);
                                bean.setCityname(address);
                                bean.setCityno(cityNos);
                                Map<String, String> map = ApiClient.createBodyMap(bean);
                                updateLookHelp(map);
                            }
                        });

            }
        });
    }

    private void updateLookHelp(final Map<String, String> body) {
        if (mImageList.size() <= 0) {
            DialogUtils.showProgressDialog(mContext, "正在上传中", "", "");
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                    .sendLookHelp(body), new Subscriber<HttpResult<String>>() {
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
                    startOpenShare(result.getInfo());
                }
            });
        } else {

            BitmapUtils.pictureCompression(mContext, mImageList, new BitmapUtils.OnPhotoCompressionCallback() {
                @Override
                public void onCompressionCallback(final List<String> imageUrl) {
                    DialogUtils.showProgressDialog(mContext, "正在上传中", "", "");
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                            .sendLookHelpImage(ApiClient.getImageParams("file", imageUrl)
                                    , body), new Subscriber<HttpResult<String>>() {
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
                            mShareList.addAll(imageUrl);
                            startOpenShare(result.getInfo());
                        }
                    });
                }
            });
        }
    }

    private void startOpenShare(String projectId) {
        ShareHelper.ShareToNewsBean toNewsBean = new ShareHelper.ShareToNewsBean();
        ShareHelper.ShareBean toMediaBean = new ShareHelper.ShareBean();

        String title = mDataBinding.editTitle.getText().toString();
        String content = mDataBinding.editInfo.getText().toString();

        toNewsBean.project_id = projectId;
        toNewsBean.uid = LoginHelper.getInstance().getUserBean().getUid();
        toNewsBean.onlylabel = Constant.SHARE_KEY_LOOK_HELP;
        toNewsBean.title = title;
        toNewsBean.content = content;
        toNewsBean.description = "";
        if (mShareList.size() > 0) {
            toNewsBean.filePath = mShareList.get(0);
        }
        toMediaBean.title = title;

        toMediaBean.content = content;
        toMediaBean.url = ApiService.SHARE_KB + projectId;

        mShareHelper.setShareToNewsContent(toNewsBean);
        mShareHelper.setShareContent(toMediaBean);
        mShareHelper.startStare(new ShareHelper.OnShareCompleteCallback() {
            @Override
            public void onComplete() {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_look_helps;
    }
}

package com.lshl.ui.me.dscs.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.Toast;

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
import com.lshl.bean.request.PublishGoodRequestBean;
import com.lshl.bean.request.PublishHelpRequestBean;
import com.lshl.databinding.ActivityPublishDscsBinding;
import com.lshl.ui.me.adapter.ReleaseReputationImageAdapter;
import com.lshl.ui.me.dscs.adapter.AddGuaranteeAdapter;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DateUtils;
import com.lshl.utils.ShareHelper;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.lshl.ui.me.imagegrid.ImageGridActivity.RESULT_DATA;

/**
 * 发布滴水穿石
 */
public class PublishDscsActivity extends BaseActivity<ActivityPublishDscsBinding> {


    private static final int REQUEST_CODE_ADD_IMAGE = 0x000123;//添加图片
    private static final int REQUEST_CODE_ADD_GUARANTEE = 0x000124;//添加担保人

    private List<String> mImageList;
    private ReleaseReputationImageAdapter mImageAdapter;
    private ArrayList<AddGuaranteeAdapter.GuaranteeBean> mGuaranteeListData;

    private PublishGoodRequestBean mGoodRequestBean;//发布公益时需要上传的参数拼接的实体类
    private PublishHelpRequestBean mHelpRequestBean;//发布互助时需要上传的参数拼接出来的实体类

    private AddressSelectPopWindow mAddressSelectPop;

    private String mAddressData;

    private PublishType mType;

    private ShareHelper mShareHelper;
    private Calendar mCalendar;

    private long mTimeStamp;
    private String mTimeStr;

    /**
     * 发布滴水穿石的类型
     */
    public enum PublishType {

        GOOD("公益"), HELP("互助");

        private final String name;

        PublishType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void actionStart(Activity activity, PublishType type, int requestCode) {
        Intent intent = new Intent(activity, PublishDscsActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, requestCode);
    }

    public class Presenter {
        public void onClickShowPop() {
            mAddressSelectPop.showPopupWindow(mDataBinding.getRoot());
            lightOff();
        }

        public void onClickSelectDate() {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            final StringBuilder sb = new StringBuilder();
            DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    sb.append(year).append("年").append(month + 1).append("月").append(dayOfMonth).append("日").append(" ");
                    mTimeStr = sb.toString();
                    mTimeStamp = DateUtils.getStringToDate(mTimeStr) / 1000;
                    mDataBinding.tvDate.setText(mTimeStr);
                }
            }, year, month, day);
            dialog.setTitle("请选择时间");
            dialog.show();
        }

        public void onClickAddGuarantee() {
            AddGuaranteeActivity.actionStart(PublishDscsActivity.this, mGuaranteeListData, REQUEST_CODE_ADD_GUARANTEE);
        }

        public void onClickSubmit() {

            String name = mDataBinding.editName.getText().toString();
            String money = mDataBinding.editMoney.getText().toString();
            String info = mDataBinding.editInfo.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(money) || TextUtils.isEmpty(info)) {
                showMessage("请填写完整基本信息");
                return;
            }

            if (mImageList.size() <= 0) {
                showMessage("您至少要上传一张相关照片，否则平台会不予以通过");
                return;
            }
            switch (mType) {
                case GOOD:
                    if (TextUtils.isEmpty(mAddressData)) {
                        showMessage("请选择您的项目发起地址");
                        return;
                    }
                    mGoodRequestBean.setName(name);
                    mGoodRequestBean.setInfo(info);
                    mGoodRequestBean.setAddress(mAddressData);
                    mGoodRequestBean.setMoney(money);

                    uploadCommonweal();
                    break;
                case HELP:
                    String guarantee = getGuarantee();
                    if (TextUtils.isEmpty(guarantee)) {
                        showMessage("请点击担保人列，填写您担保人的信息");
                        return;
                    }
                    if (TextUtils.isEmpty(mTimeStr)) {
                        showMessage("请选择您的还款时间");
                        return;
                    }
                    long currentTime = System.currentTimeMillis();
                    if (mTimeStamp < currentTime / 1000) {
                        showMessage("您选择的还款时间已过期，或者即将到期，请重试");
                        return;
                    }

                    mHelpRequestBean.setName(name);
                    mHelpRequestBean.setMoney(money);
                    mHelpRequestBean.setInfo(info);
                    mHelpRequestBean.setRepayment(mTimeStr);
                    mHelpRequestBean.setRepayment_time(String.valueOf(mTimeStamp));
                    mHelpRequestBean.setGuarantee(guarantee);
                    uploadMutual();

                    break;
            }
        }
    }


    private String getGuarantee() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mGuaranteeListData.size(); i++) {
            AddGuaranteeAdapter.GuaranteeBean guaranteeBean = mGuaranteeListData.get(i);
            sb.append(guaranteeBean.getName()).append(",").append(guaranteeBean.getIdCard());
            if (i + 1 < mGuaranteeListData.size()) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    /**
     * 发起互助
     */
    private void uploadMutual() {
        mHelpRequestBean.setToken(LoginHelper.getInstance().getUserToken());
        final Map<String, String> body = ApiClient.createBodyMap(mHelpRequestBean);
        BitmapUtils.pictureCompression(mContext, mImageList, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(List<String> imageUrl) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).launchMutualAid(ApiClient.getImageParams("file", imageUrl), body)
                        , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                Toast.makeText(mContext, "发起成功", Toast.LENGTH_SHORT).show();
                                startOpenShare(result.getInfo());
                            }
                        })
                );
            }
        });

    }


    /**
     * 发起公益
     */
    private void uploadCommonweal() {

        mGoodRequestBean.setToken(LoginHelper.getInstance().getUserToken());
        final Map<String, String> body = ApiClient.createBodyMap(mGoodRequestBean);

        BitmapUtils.pictureCompression(mContext, mImageList, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(List<String> imageUrl) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).launchPublicWelfare(ApiClient.getImageParams("file", imageUrl), body)
                        , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                Toast.makeText(mContext, "发起成功", Toast.LENGTH_SHORT).show();
                                startOpenShare(result.getInfo());
                            }
                        })
                );
            }
        });
    }

    private void startOpenShare(String projcetId) {

        ShareHelper.ShareToNewsBean bean = new ShareHelper.ShareToNewsBean();
        ShareHelper.ShareBean shareBean = new ShareHelper.ShareBean();

        switch (mType) {
            case GOOD:
                bean.title = mGoodRequestBean.getName();
                bean.content = mGoodRequestBean.getInfo();
                bean.onlylabel = Constant.SHARE_KEY_GOOD;

                shareBean.title = mGoodRequestBean.getName();
                shareBean.content = mGoodRequestBean.getInfo();
                shareBean.url = ApiService.SHARE_GOOD + projcetId;

                break;
            case HELP:

                bean.title = mHelpRequestBean.getName();
                bean.content = mHelpRequestBean.getInfo();
                bean.onlylabel = Constant.SHARE_KEY_HELP;

                shareBean.title = mHelpRequestBean.getName();
                shareBean.content = mHelpRequestBean.getInfo();
                shareBean.url = ApiService.SHARE_HELP + projcetId;

                break;
        }
        if (mImageList != null && mImageList.size() >= 1) {
            bean.filePath = mImageList.get(0);
        }
        bean.project_id = projcetId;
        bean.uid = LoginHelper.getInstance().getUserBean().getUid();
        mShareHelper.setShareToNewsContent(bean);
        mShareHelper.setShareContent(shareBean);
        mShareHelper.startStare(new ShareHelper.OnShareCompleteCallback() {
            @Override
            public void onComplete() {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        } else if (requestCode == REQUEST_CODE_ADD_GUARANTEE) {
            mGuaranteeListData.clear();
            ArrayList<AddGuaranteeAdapter.GuaranteeBean> list = data.getParcelableArrayListExtra("result_bean");
            mGuaranteeListData.addAll(list);
            mDataBinding.setGuaranteeNum(mGuaranteeListData.size());
        }
    }

    @Override
    protected void initFieldBeforeMethods() {
        mShareHelper = new ShareHelper(mContext);
        mType = (PublishType) getIntent().getSerializableExtra("type");
        mImageAdapter = new ReleaseReputationImageAdapter(this, REQUEST_CODE_ADD_IMAGE, 6);
        mImageList = new ArrayList<>();
        mAddressSelectPop = new AddressSelectPopWindow(this);
        mGuaranteeListData = new ArrayList<>();
        mGoodRequestBean = new PublishGoodRequestBean();
        mHelpRequestBean = new PublishHelpRequestBean();
        mCalendar = Calendar.getInstance();
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("发表" + mType.getName(), DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {

        mShareHelper.bindShareLayout(mDataBinding.shareLayout);

        mDataBinding.setGuaranteeNum(0);
        mDataBinding.setType(mType);
        mDataBinding.setPresenter(new Presenter());
        mDataBinding.recyclerImages.setLayoutManager(new GridLayoutManager(mContext, 3));
        mDataBinding.recyclerImages.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        mDataBinding.recyclerImages.setAdapter(mImageAdapter);
        mAddressSelectPop.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mAddressData = provinceData + cityData;
                mDataBinding.tvAddress.setText(mAddressData);
            }
        });
        mAddressSelectPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_dscs;
    }


}

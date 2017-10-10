package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.lshl.bean.GoodsDetailsBean;
import com.lshl.bean.TradeGoodsTypeBean;
import com.lshl.bean.UpdateGoodsBean;
import com.lshl.databinding.ActivityEditGoodsBinding;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.ListUtils;
import com.lshl.utils.ShareHelper;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.GoodsTypeSelectPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * 发布自贸区商品（或编辑自贸区商品）
 */
public class EditGoodsActivity extends BaseActivity<ActivityEditGoodsBinding> {

    public static final int FROM_ADD_GOODS = 0x0000121;
    public static final int FROM_EDIT_GOODS = 0x0000122;

    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 9;

    private GoodsDetailsBean.InfoBean mGoodsBean;
    private int mFromWhere;
    private List<TradeGoodsTypeBean.InfoBean> mGoodsTypeList;

    private String mGoodsTypeId = "1001";
    private UpdateGoodsBean mUpdateBean;

    private List<String> mListImageData;
    private List<String> mNewImageData;
    private List<String> mDeleImageList;
    private GoodsImageEditAdapter mImageEditAdapter;
    private AddressSelectPopWindow mAddressPopWindow;
    private String mCityNo;
    private String mCityName;
    private List<String> mShareList;
    private ShareHelper mShareHelper;
    private String oneClass;
    private String twoClass;
    private boolean isEditGoods;
    private boolean isChangeImage;
    private GoodsTypeSelectPopWindow goodsTypeSelectPopWindow;
    private String enid;

    public static void actionStart(Activity activity, GoodsDetailsBean.InfoBean goodsBean, String enid, int fromWhere, int requestCode) {
        Intent intent = new Intent(activity, EditGoodsActivity.class);
        intent.putExtra("where", fromWhere);
        intent.putExtra("goods", goodsBean);
        intent.putExtra("enid", enid);
        activity.startActivityForResult(intent, requestCode);
    }

    public class Presenter {
        public void onClickAddGoodsImg() {
            ImageGridActivity.actionStart(EditGoodsActivity.this, MAX_GOODS_IMAGE_COUNT, (ArrayList<String>) mListImageData, REQUEST_CODE_GOODS_IMAGE);
        }

        public void onClickAddressSelect() {
            lightOff();
            mAddressPopWindow.showPopupWindow(mDataBinding.getRoot());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityForResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOODS_IMAGE) {
            if (resultCode == RESULT_OK) {
                mNewImageData.clear();
                mListImageData.clear();
                List<String> imageList = data.getStringArrayListExtra(ImageGridActivity.RESULT_DATA);
                if (imageList.size() == MAX_GOODS_IMAGE_COUNT)
                    mDataBinding.llAddPhoto.setVisibility(View.GONE);
                mListImageData.addAll(imageList);
                if (imageList.size() > 0) {
                    for (String s : imageList) {
                        if (!s.contains("http")) {
                            mNewImageData.add(s);
                        }
                    }
                }
                mImageEditAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void setupTitle() {
        String title = "";
        if (mFromWhere == FROM_ADD_GOODS)
            title = "发布商品";
        else if (mFromWhere == FROM_EDIT_GOODS)
            title = "编辑商品";
        setTextTitleView(title, DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mDeleImageList = new ArrayList<>();
        mNewImageData = new ArrayList<>();
        mShareList = new ArrayList<>();
        Intent intent = getIntent();
        mFromWhere = intent.getIntExtra("where", -1);
        enid = intent.getStringExtra("enid");
        mShareHelper = new ShareHelper(mContext);
        mAddressPopWindow = new AddressSelectPopWindow(this);
        goodsTypeSelectPopWindow = new GoodsTypeSelectPopWindow(this);
        initGoodsImage();
        if (intent.hasExtra("goods") && intent.getSerializableExtra("goods") != null) {
            mGoodsBean = (GoodsDetailsBean.InfoBean) intent.getSerializableExtra("goods");
            if (mGoodsBean != null) {
                isEditGoods = true;
                setupGoodsBean();
            }
            mDataBinding.shareLayout.setVisibility(View.GONE);
        } else {
            mDataBinding.setPhoneNum(LoginHelper.getInstance().getUserBean().getPhone());
            loadGoodsType();
        }
    }

    private void initGoodsImage() {
        mListImageData = new ArrayList<>();
        mDataBinding.recyclerPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mImageEditAdapter = new GoodsImageEditAdapter(mListImageData);
        mDataBinding.recyclerPhoto.setAdapter(mImageEditAdapter);
    }


    @Override
    protected void initViews() {

        mShareHelper.bindShareLayout(mDataBinding.shareLayout);

        mDataBinding.setPresenter(new Presenter());

        mImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {

            @Override
            public void onChangeCallback(int num, int position) {
                isChangeImage = true;
                if (isEditGoods)
                    if (mGoodsBean.getImg().size() > 0) {
                        for (int i = 0; i < mGoodsBean.getImg().size(); i++) {
                            String imageUrl = mGoodsBean.getImg().remove(i);
                            mDeleImageList.add(imageUrl);
                            i--;
                        }
                    }
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.llAddPhoto.getVisibility() == View.GONE) {
                    mDataBinding.llAddPhoto.setVisibility(View.VISIBLE);
                }
            }
        });

        mAddressPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mCityNo = cityNo;
                mCityName = provinceData + cityData;
                mDataBinding.setAddress(provinceData + "·" + cityData);
            }
        });
        goodsTypeSelectPopWindow.setWheelChangeListener(new GoodsTypeSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                oneClass = provinceData;
                twoClass = cityData;
                mGoodsTypeId = cityNo;
                mDataBinding.setGoodsType(cityData);
            }
        });
        goodsTypeSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mAddressPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        mDataBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateGoodsBean bean = new UpdateGoodsBean();

                String goodsName = mDataBinding.editGoodsName.getText().toString().trim();
                String specialOffer = "0";
                String goodsPrice = mDataBinding.editGoodsPrice.getText().toString().trim();
                String goodsSelling = mDataBinding.editGoodsSelling.getText().toString().trim();
                String goodsPhone = mDataBinding.editPhone.getText().toString().trim();
                String address = mDataBinding.tvAddress.getText().toString().trim();

                if (!isEditGoods || mGoodsBean.getImg().size() < 0)
                    if (mListImageData.size() <= 0) {
                        Toast.makeText(mContext, "您至少要上传一张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }

                if (TextUtils.isEmpty(goodsName) || TextUtils.isEmpty(specialOffer) || TextUtils.isEmpty(goodsSelling) || TextUtils.isEmpty(goodsPhone)) {
                    Toast.makeText(mContext, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mCityNo) || TextUtils.isEmpty(address)) {
                    Toast.makeText(mContext, "请选择商品地址城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(specialOffer) || TextUtils.isEmpty(goodsPrice)) {
                    Toast.makeText(mContext, "请输入商品价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                Float priceYou = Float.parseFloat(specialOffer);
                Float price = Float.parseFloat(goodsPrice);
                if (priceYou >= price) {
                    Toast.makeText(mContext, "老乡价不能高于市场价", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(oneClass)) {
                    Toast.makeText(mContext, "请选择产品类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                bean.setEnid(enid);
                bean.setType(mGoodsTypeId);
                bean.setCityno(mCityNo);
                bean.setCityname(mCityName);
                bean.setInfo(goodsSelling);
                bean.setGoodname(goodsName);
                bean.setPhone(goodsPhone);

                bean.setOriginal_price(goodsPrice);
                bean.setSpecial_offer(specialOffer);
                bean.setOne_class(oneClass);
                bean.setTwo_class(twoClass);
//                bean.setInfo();
                bean.setToken(LoginHelper.getInstance().getUserToken());
                if (mUpdateBean != null) {
                    if (TextUtils.isEmpty(mCityNo)) {
                        bean.setCityno(mUpdateBean.getCityno());
                    }
                    if (TextUtils.isEmpty(mCityName)) {
                        bean.setCityname(mUpdateBean.getCityname());
                    }
                }

                if (isEditGoods) {
                    bean.setGid(mGoodsBean.getGd_id());
                    List<String> image = mGoodsBean.getImg();
                    StringBuilder sb = new StringBuilder();
                    ListUtils.appendIdList(image, sb);
                    bean.setOld_img(sb.toString());
                    if (isChangeImage) {
                        StringBuilder deleSb = new StringBuilder();
                        ListUtils.appendIdList(mDeleImageList, deleSb);
                        bean.setDel_img(deleSb.toString());
                    }
                }

                final Map<String, String> bodyMap = ApiClient.createBodyMap(bean);
                if (mNewImageData.size() > 0) {
                    BitmapUtils.pictureCompression(mContext, mNewImageData, new BitmapUtils.OnPhotoCompressionCallback() {
                        @Override
                        public void onCompressionCallback(final List<String> imageUrl) {
                            if (isEditGoods) {
                                RetrofitManager.toSubscribe(
                                        ApiClient.getApiService(
                                                ApiService.class, RetrofitManager.RetrofitType.Object)
                                                .editGoodsHasImage(ApiClient.getImageParams("imgfile", imageUrl), bodyMap)
                                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                            @Override
                                            public void onNext(HttpResult<String> result) {
                                                Toast.makeText(mContext, "编辑商品成功", Toast.LENGTH_SHORT).show();
                                                editGoodsSuccess();
                                            }
                                        })
                                );
                            } else {
                                RetrofitManager.toSubscribe(
                                        ApiClient.getApiService(
                                                ApiService.class, RetrofitManager.RetrofitType.Object)
                                                .updateGoods(ApiClient.getImageParams("imgfile", imageUrl), bodyMap)
                                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                            @Override
                                            public void onNext(HttpResult<String> result) {
                                                Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                                                mShareList.addAll(imageUrl);
                                                MessageShareToNews(mShareList.get(0), result.getInfo());
//                                                startOpenShare(result.getInfo());
                                            }
                                        })
                                );
                            }
                        }
                    });
                } else {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .editGoodsNoImage(bodyMap)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    Toast.makeText(mContext, "编辑商品成功", Toast.LENGTH_SHORT).show();
                                    editGoodsSuccess();
                                }
                            })
                    );
                }

            }
        });
        //商品类型的点击回调
        mDataBinding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightOff();
                goodsTypeSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.spinnerGoodsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mGoodsTypeList != null) {
                    TradeGoodsTypeBean.InfoBean infoBean = mGoodsTypeList.get(position);
                    mGoodsTypeId = infoBean.getGd_id();
                    mDataBinding.setGoodsType(infoBean.getGd_name());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void editGoodsSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private void startOpenShare(String projectId) {

        ShareHelper.ShareToNewsBean toNewsBean = new ShareHelper.ShareToNewsBean();
        ShareHelper.ShareBean toMediaBean = new ShareHelper.ShareBean();

        String goodsName = mDataBinding.editGoodsName.getText().toString();
        String goodsSelling = mDataBinding.editGoodsSelling.getText().toString();

        if (mShareList.size() > 0) {
            toNewsBean.filePath = mShareList.get(0);
        }
        toNewsBean.content = goodsSelling;
        toNewsBean.title = goodsName;
        toNewsBean.project_id = projectId;
        toNewsBean.onlylabel = Constant.SHARE_KEY_TRADE;
        toNewsBean.uid = LoginHelper.getInstance().getUserBean().getUid();
        toNewsBean.description = "";
        toMediaBean.title = goodsName;
        toMediaBean.content = goodsSelling;
        toMediaBean.url = ApiService.SHARE_GOODS + projectId;

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

    /**
     * 加载商品类型
     */
    private void loadGoodsType() {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .getGoodsType()
                , new Subscriber<TradeGoodsTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TradeGoodsTypeBean tradeGoodsTypeBean) {
                        mGoodsTypeList = tradeGoodsTypeBean.getInfo();
                        initGoodsTypeList();
                    }
                }
        );
    }

    /**
     * 初始化商品类型列表
     */
    private void initGoodsTypeList() {
        List<String> goodsType = new ArrayList<>();
        for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeList) {
            goodsType.add(infoBean.getGd_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1);
        adapter.addAll(goodsType);
        mDataBinding.spinnerGoodsType.setAdapter(adapter);
        checkGoodsType();
    }

    /**
     * 检测当前选中的商品类型的名称
     */
    private void checkGoodsType() {
        if (mGoodsBean != null && mGoodsTypeList != null) {
            for (int i = 0; i < mGoodsTypeList.size(); i++) {
                TradeGoodsTypeBean.InfoBean infoBean = mGoodsTypeList.get(i);
                if (infoBean.getGd_id().equals(mGoodsBean.getGd_type())) {
                    mGoodsTypeId = infoBean.getGd_id();
                    mDataBinding.spinnerGoodsType.setSelection(i);
                    mDataBinding.setGoodsType(infoBean.getGd_name());
                }
            }
        }
    }

    private void setupGoodsBean() {
        mUpdateBean = new UpdateGoodsBean();
        mUpdateBean.setGoodname(mGoodsBean.getGd_goodname());
        mUpdateBean.setInfo(mGoodsBean.getGd_info());
        mUpdateBean.setGid(mGoodsBean.getGd_id());
        mUpdateBean.setToken(LoginHelper.getInstance().getUserToken());
        mUpdateBean.setType(mGoodsBean.getGd_type());
        mUpdateBean.setOriginal_price(mGoodsBean.getGd_original_price());
        mUpdateBean.setSpecial_offer(mGoodsBean.getGd_special_offer());
        mUpdateBean.setPhone(mGoodsBean.getPhone());
        mUpdateBean.setCityno(mGoodsBean.getGd_cityno());
        mUpdateBean.setCityname(mGoodsBean.getGd_cityname());
        mUpdateBean.setOld_img(mGoodsBean.getGd_img());
        mUpdateBean.setOne_class(mGoodsBean.getGd_one_class());
        mUpdateBean.setTwo_class(mGoodsBean.getGd_two_class());
        List<String> imageList = mGoodsBean.getImg();
        mCityNo = mGoodsBean.getGd_cityno();
        for (String s : imageList) {
            mListImageData.add(ApiClient.getGoodsInfoImage(s));
        }
        mImageEditAdapter.notifyDataSetChanged();
        loadGoodsType();
        mDataBinding.setPhoneNum(mGoodsBean.getGd_phone());
        mDataBinding.setAddress(mGoodsBean.getGd_cityname());
        mDataBinding.setGoodsBean(mGoodsBean);
    }/**/

    private void MessageShareToNews(String url, String projectId) {
        String goodsName = mDataBinding.editGoodsName.getText().toString();
        String goodsSelling = mDataBinding.editGoodsSelling.getText().toString();
        MultipartBody.Part imageBody = ApiClient.getFileNews(new File(url));
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).shareToNews(
                        imageBody,
                        RequestBody.create(null, goodsName),
                        RequestBody.create(null, goodsSelling),
                        RequestBody.create(null, ""),
                        RequestBody.create(null, Constant.SHARE_KEY_TRADE),
                        RequestBody.create(null, projectId),
                        RequestBody.create(null, LoginHelper.getInstance().getUserBean().getUid()))
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_goods;
    }
}

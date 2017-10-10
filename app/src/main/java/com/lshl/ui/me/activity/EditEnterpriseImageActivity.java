package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

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
import com.lshl.bean.request.EditEnterpriseImageBean;
import com.lshl.databinding.ActivityEditEnterpriseImageBinding;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditEnterpriseImageActivity extends BaseActivity<ActivityEditEnterpriseImageBinding> {
    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 6;
    private GoodsImageEditAdapter goodsImageEditAdapter;

    private BusinessDetailsBean.InfoBean mInfoBean;
    private List<String> mListImageData;
    private List<String> mNewImageData;
    private List<String> mDeleImageList;
    private List<String> mOldImageData;

    public static void actionStart(Activity activity, BusinessDetailsBean.InfoBean mInfoBean) {
        Intent intent = new Intent(activity, EditEnterpriseImageActivity.class);
        intent.putExtra("mInfoBean", mInfoBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mDeleImageList = new ArrayList<>();
        mListImageData = new ArrayList<>();
        mNewImageData = new ArrayList<>();
        mOldImageData = new ArrayList<>();
        mInfoBean = (BusinessDetailsBean.InfoBean) getIntent().getSerializableExtra("mInfoBean");
        mDataBinding.name.setText("项目名称:  " + mInfoBean.getEn_name());
        mDataBinding.info.setText(mInfoBean.getEn_info());
        List<String> image = new ArrayList<>();
        String[] split = mInfoBean.getEn_img().split(",");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                image.add(ApiClient.getEnterPriseImage(split[i]));
            }
        }
        mListImageData.addAll(image);
        mOldImageData.addAll(image);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("修改企业信息", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        goodsImageEditAdapter = new GoodsImageEditAdapter(mListImageData);
        mDataBinding.recyclerView.setAdapter(goodsImageEditAdapter);
        goodsImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                if (mInfoBean.getEn_img().length() > 0) {
                    List<String> image = new ArrayList<>();
                    String[] split = mInfoBean.getEn_img().split(",");
                    if (split.length > 0) {
                        for (int i = 0; i < split.length; i++) {
                            image.add(ApiClient.getEnterPriseImage(split[i]));
                        }
                    }
                    for (int i = 0; i < image.size(); i++) {
                        String imageUrl = image.remove(i);
                        mDeleImageList.add(imageUrl);
                        i--;
                    }
                }
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.llAddPhoto.getVisibility() == View.GONE) {
                    mDataBinding.llAddPhoto.setVisibility(View.VISIBLE);
                }
            }
        });
        mDataBinding.llAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGridActivity.actionStart(EditEnterpriseImageActivity.this, MAX_GOODS_IMAGE_COUNT, (ArrayList<String>) mListImageData, REQUEST_CODE_GOODS_IMAGE);
            }
        });

        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInfoBean.getEn_img().length() <= mDeleImageList.size() && mListImageData.size() <= 0) {
                    showMessage("您至少要上传一张图片");
                    return;
                }
                if (TextUtils.isEmpty(mDataBinding.info.getText())) {
                    showMessage("请提交完整的信息");
                    return;
                }
                String profile = mDataBinding.info.getText().toString();
                EditEnterpriseImageBean bean = new EditEnterpriseImageBean();
                bean.setToken(LoginHelper.getInstance().getUserToken());
                bean.setInfo(profile);
                bean.setEnid(mInfoBean.getEn_id());

//                String mLength=ApiService.BASE_URL + "Data/Uploads/enterprises/";
//                if (mListImageData.size()>0){
//                    for (int i = 0; i < mListImageData.size(); i++) {
//                        image.add(mListImageData.get(i).substring(mLength.length()));
//                    }
//                }
                StringBuilder sb = new StringBuilder();
                List<String> list = ListUtils.checkDifferent(mOldImageData, mInfoBean.getImg());
                if (list.size() > 0) {
                    ListUtils.appendIdList(mOldImageData, sb);
                } else {
                    List<String> image = mInfoBean.getImg();
                    ListUtils.appendIdList(image, sb);
                }
                bean.setOld_img(sb.toString());
                StringBuilder deleSb = new StringBuilder();
                ListUtils.appendIdList(mDeleImageList, deleSb);
                bean.setDel_img(deleSb.toString());
                final Map<String, String> body = ApiClient.createBodyMap(bean);
                DialogUtils.alertDialog(mContext, "温馨提示", "是否要提交以上内容?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        editEnterpriseImage(body);
                    }
                });
            }
        });
    }

    private void editEnterpriseImage(final Map<String, String> body) {
        BitmapUtils.pictureCompression(mContext, mNewImageData, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(List<String> imageUrl) {
                if (imageUrl.size() <= 0) {
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                            RetrofitManager.RetrofitType.Object).EditEnterprise(body)
                            , new ProgressSubscriber<>(mContext
                                    , new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }));
                } else {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .EditEnterpriseImage(ApiClient.getImageParams("imgfile", imageUrl), body),
                            new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOODS_IMAGE) {
            if (resultCode == RESULT_OK) {
                mNewImageData.clear();
                mListImageData.clear();
                mOldImageData.clear();
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
                    for (String s : imageList) {
                        if (s.contains("http")) {
                            mOldImageData.add(s);
                        }
                    }
                }
                goodsImageEditAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_enterprise_image;
    }
}

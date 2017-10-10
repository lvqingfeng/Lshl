package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.lshl.bean.ProjectDetailsBean;
import com.lshl.bean.request.EditLaunchProjectRequestBean;
import com.lshl.bean.request.UpdatePeojectBean;
import com.lshl.databinding.ActivityEditProjectBinding;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditProjectActivity extends BaseActivity<ActivityEditProjectBinding> {
    private ProjectDetailsBean.InfoBean infoBean;

    private boolean isChangeImage;
    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 6;//图片最大个数
    private GoodsImageEditAdapter goodsImageEditAdapter;
    public static final int EDIT_MYPROJECT = 0x0000121;

    private List<String> mListImageData;
    private List<String> mNewImageData;
    private List<String> mDeleImageList;
    private UpdatePeojectBean mPeojectBean;

    public static void actionStart(Activity activity, ProjectDetailsBean.InfoBean infoBean, int requestCode) {
        Intent intent = new Intent(activity, EditProjectActivity.class);
        intent.putExtra("info", infoBean);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mDeleImageList = new ArrayList<>();
        mNewImageData = new ArrayList<>();
        mListImageData = new ArrayList<>();
        mDataBinding.shareLayout.setVisibility(View.GONE);
        Intent intent = getIntent();
        infoBean = (ProjectDetailsBean.InfoBean) intent.getSerializableExtra("info");
        mDataBinding.info.setText(infoBean.getPp_info());
        mDataBinding.address.setText(infoBean.getPp_address());
        mDataBinding.title.setText(infoBean.getPp_title());
        setupGoodsBean();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mDataBinding.recyclerPhoto.setLayoutManager(manager);
        goodsImageEditAdapter = new GoodsImageEditAdapter(mListImageData);
        mDataBinding.recyclerPhoto.setAdapter(goodsImageEditAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("编辑项目", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("发布", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoBean.getImgs().size() <= mDeleImageList.size() && mListImageData.size() <= 0) {
                    showMessage("您至少要上传一张图片");
                    return;
                }

                String title = mDataBinding.title.getText().toString();
                String address = mDataBinding.address.getText().toString();
                String info = mDataBinding.info.getText().toString();


                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(address) || TextUtils.isEmpty(info)) {
                    showMessage("请提交完整的信息");
                    return;
                }
                EditLaunchProjectRequestBean bean = new EditLaunchProjectRequestBean();
                bean.setToken(LoginHelper.getInstance().getUserToken());
                bean.setPid(infoBean.getPp_id());
                bean.setTitle(title);
                bean.setInfo(info);
                bean.setAddress(address);
                List<String> image = infoBean.getImgs();
                StringBuilder sb = new StringBuilder();
                ListUtils.appendIdList(image, sb);
                bean.setOld_img(sb.toString());
                if (isChangeImage) {
                    StringBuilder deleSb = new StringBuilder();
                    ListUtils.appendIdList(mDeleImageList, deleSb);
                    bean.setDel_img(deleSb.toString());
                }
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
                        editProject(body);
                    }
                });

            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.llAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGridActivity.actionStart(EditProjectActivity.this, MAX_GOODS_IMAGE_COUNT, (ArrayList<String>) mListImageData, REQUEST_CODE_GOODS_IMAGE);
            }
        });
        mDataBinding.name.setText("发起人:" + LoginHelper.getInstance().getUserBean().getRealName());
        goodsImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                isChangeImage = true;
                if (infoBean.getImgs().size() > 0) {
                    for (int i = 0; i < infoBean.getImgs().size(); i++) {
                        String imageUrl = infoBean.getImgs().remove(i);
                        mDeleImageList.add(imageUrl);
                        i--;
                    }
                }
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.llAddPhoto.getVisibility() == View.GONE) {
                    mDataBinding.llAddPhoto.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void editProject(final Map<String, String> body) {
        if (mNewImageData.size() > 0) {
            BitmapUtils.pictureCompression(mContext, mNewImageData, new BitmapUtils.OnPhotoCompressionCallback() {
                @Override
                public void onCompressionCallback(List<String> imageUrl) {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .editLaunchProjectHasImage(ApiClient.getImageParams("imgfile", imageUrl), body),
                            new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    editGoodsSuccess();
                                }
                            }));
                }
            });
        } else {
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                            .editLaunchProjectNoImage(body),
                    new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            editGoodsSuccess();
                        }
                    }));
        }
    }

    private void editGoodsSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private void setupGoodsBean() {
        mPeojectBean = new UpdatePeojectBean();
        mPeojectBean.setToken(LoginHelper.getInstance().getUserToken());
        mPeojectBean.setAddress(infoBean.getPp_address());
        mPeojectBean.setInfo(infoBean.getPp_info());
        mPeojectBean.setPid(infoBean.getPp_id());
        mPeojectBean.setTitle(infoBean.getPp_title());
        mPeojectBean.setOld_img(infoBean.getPp_imgs());
        List<String> imageList = infoBean.getImgs();
        for (String s : imageList) {
            mListImageData.add(ApiClient.getProjectImage(s));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                goodsImageEditAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_project;
    }
}

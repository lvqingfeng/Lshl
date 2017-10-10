package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.ImageWallListBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.request.ImageWallBean;
import com.lshl.databinding.ActivityImageWallBinding;
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
import rx.Subscriber;

public class ImageWallActivity extends BaseActivity<ActivityImageWallBinding> {
    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 8;//图片最大个数
    private GoodsImageEditAdapter goodsImageEditAdapter;
    private List<String> info;
    private List<String> mListImageData;
    private List<String> mNewImageData;
    private List<String> mDeleImageList;
    private List<String> mOldImageData;
    private StringBuilder deleSb = null;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ImageWallActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("照片墙", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("保存", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListImageData.size() <= 0) {
                    showMessage("至少上传一张图片");
                    return;
                }
                ImageWallBean bean = new ImageWallBean();
                bean.setToken(LoginHelper.getInstance().getUserToken());
                final StringBuilder sb = new StringBuilder();
                ListUtils.appendIdList(info, sb);
                bean.setOld_img(sb.toString());
                if (mDeleImageList.size() > 0) {
                    deleSb = new StringBuilder();
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
                        if (mNewImageData.size() <= 0) {
                            if (deleSb.toString().length() > 0) {
                                deleteImageWall(sb.toString(), deleSb.toString());
                            }
                        } else {
                            updateImageWall(body);
                        }
                    }
                });
            }
        });
    }

    private void deleteImageWall(String oldImage, String newImage) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .deleteImageWall(LoginHelper.getInstance().getUserToken(), oldImage, newImage), new ProgressSubscriber<HttpResult<String>>(mContext
                , new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void updateImageWall(final Map<String, String> body) {
        if (mNewImageData.size() > 0 || mDeleImageList.size() > 0) {
            BitmapUtils.pictureCompression(mContext, mNewImageData, new BitmapUtils.OnPhotoCompressionCallback() {
                @Override
                public void onCompressionCallback(List<String> imageUrl) {
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                            .updateImageWall(ApiClient.getImageParams("file", imageUrl), body), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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
            });
        }
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListImageData = new ArrayList<>();
        mDeleImageList = new ArrayList<>();
        mNewImageData = new ArrayList<>();
        mOldImageData = new ArrayList<>();
        info = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        initBackGround();
        GridLayoutManager manager = new GridLayoutManager(mContext, 4) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        goodsImageEditAdapter = new GoodsImageEditAdapter(mListImageData);
        mDataBinding.recyclerView.setAdapter(goodsImageEditAdapter);
        loadBaseData();
        goodsImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                if (info.size() > 0) {
                    for (int i = 0; i < info.size(); i++) {
                        String imageUrl = info.remove(i);
                        mDeleImageList.add(imageUrl);
                        i--;
                    }
                }
            }
        });
        mDataBinding.addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListImageData.size() <= 8) {
                    ImageGridActivity.actionStart(ImageWallActivity.this, 8 - mListImageData.size(), (ArrayList<String>) mListImageData, REQUEST_CODE_GOODS_IMAGE);
                } else {
                    Toast.makeText(mContext, "最多可以添加八张图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initBackGround() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updatePersonBaseData(LoginHelper.getInstance().getUserToken(), LoginHelper.getInstance().getUserBean().getUid(), ""), new Subscriber<PersonBasedataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PersonBasedataBean bean) {
                String avatar = bean.getInfo().getAvatar();
                Glide.with(mContext).load(ApiClient.getHxFriendsImage(avatar)).error(R.mipmap.account_logo).into(mDataBinding.headImage);
                Glide.with(mContext).load(ApiClient.getHxFriendsImage(avatar)).thumbnail(0.01f).error(R.mipmap.default_background).into(mDataBinding.background);
            }
        });
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateImageWallList(LoginHelper.getInstance().getUserBean().getUid()), new ProgressSubscriber<ImageWallListBean>(mContext, new SubscriberOnNextListener<ImageWallListBean>() {
            @Override
            public void onNext(ImageWallListBean result) {
                info = result.getInfo();
                if (result.getStatus() == 1) {
                    if (result.getInfo().size() > 0) {
                        for (int i = 0; i < result.getInfo().size(); i++) {
                            mListImageData.add(ApiClient.getImageWallImage(result.getInfo().get(i)));
                            mOldImageData.add(ApiClient.getImageWallImage(result.getInfo().get(i)));
                        }
                    }
                    goodsImageEditAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GOODS_IMAGE) {
            if (resultCode == RESULT_OK) {
                mNewImageData.clear();
                mListImageData.clear();
                List<String> imageList = data.getStringArrayListExtra(ImageGridActivity.RESULT_DATA);
                mListImageData.addAll(imageList);
                if (imageList.size() > 0) {
                    for (String s : imageList) {
                        if (!s.contains("http")) {
                            mNewImageData.add(s);
                        }
                        if (s.contains("http")) {
                            mListImageData.add(s);
                        }
                    }
                }
                goodsImageEditAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_wall;
    }
}

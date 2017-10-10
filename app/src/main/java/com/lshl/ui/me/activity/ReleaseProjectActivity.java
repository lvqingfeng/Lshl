package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.lshl.bean.request.ReleaseProjectBean;
import com.lshl.databinding.ActivityReleaseProjectBinding;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ShareHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 发布项目
 */
public class ReleaseProjectActivity extends BaseActivity<ActivityReleaseProjectBinding> {

    private ArrayList<String> mImageList;
    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 6;
    private GoodsImageEditAdapter goodsImageEditAdapter;
    private List<String> mList;
    private String title;
    private String address;
    private String info;
    public static final int MYPROJECT = 0x0000121;
    private ShareHelper mShareHelper;

    public static void actionStart(Activity activity, ArrayList<String> mList, String title, String address, String info, int resultCode) {
        Intent intent = new Intent(activity, ReleaseProjectActivity.class);
        intent.putStringArrayListExtra("mList", mList);
        intent.putExtra("title", title);
        intent.putExtra("address", address);
        intent.putExtra("info", info);
        activity.startActivityForResult(intent, resultCode);
    }

    public class Presenter {
        public void onClickAddGoodsImg() {
            ImageGridActivity.actionStart(ReleaseProjectActivity.this, MAX_GOODS_IMAGE_COUNT, (ArrayList<String>) mImageList, REQUEST_CODE_GOODS_IMAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mShareHelper = new ShareHelper(mContext);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("发布项目", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        setRightViewText("发布", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageList.size() <= 0) {
                    showMessage("您至少要上传一张图片");
                    return;
                }
                if (!TextUtils.isEmpty(mDataBinding.title.getText())) {
                    if (!TextUtils.isEmpty(mDataBinding.address.getText())) {
                        if (!TextUtils.isEmpty(mDataBinding.info.getText())) {
                            DialogUtils.alertDialog(mContext, "温馨提示", "是否要提交以上内容?", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }, new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    final ReleaseProjectBean bean = new ReleaseProjectBean();
                                    bean.setToken(LoginHelper.getInstance().getUserToken());
                                    bean.setTitle(mDataBinding.title.getText().toString());
                                    bean.setAddress(mDataBinding.address.getText().toString());
                                    bean.setInfo(mDataBinding.info.getText().toString());
                                    BitmapUtils.pictureCompression(mContext, mImageList, new BitmapUtils.OnPhotoCompressionCallback() {
                                        @Override
                                        public void onCompressionCallback(List<String> imageUrl) {
                                            Map<String, String> body = ApiClient.createBodyMap(bean);
                                            updateReleaseProjrct(body);
                                        }
                                    });
                                }
                            });
                        } else {
                            showMessage("请提交完整的信息");
                        }
                    } else {
                        showMessage("请提交完整的信息");
                    }
                } else {
                    showMessage("请提交完整的信息");
                }
            }
        });
    }

    @Override
    protected void initViews() {

        mShareHelper.bindShareLayout(mDataBinding.shareLayout);

        mImageList = new ArrayList<>();
        mList = getIntent().getStringArrayListExtra("mList");
        title = getIntent().getStringExtra("title");
        address = getIntent().getStringExtra("address");
        info = getIntent().getStringExtra("info");
        Log.i("屮艸芔茻", mList.size() + "===" + title + "===" + address + "===" + info);
        if (!TextUtils.isEmpty(title)) {
            if (!TextUtils.isEmpty(address)) {
                if (!TextUtils.isEmpty(info)) {
                    if (mList.size() > 0) {
                        mDataBinding.title.setText(title);
                        mDataBinding.address.setText(address);
                        mDataBinding.info.setText(info);
                        mImageList.addAll(mList);
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(LoginHelper.getInstance().getUserBean().getRealName())) {
            mDataBinding.name.setText("发起人:" + LoginHelper.getInstance().getUserBean().getRealName());
        } else {
            mDataBinding.name.setText("发起人:" + "");
        }
        mDataBinding.setPresenter(new Presenter());
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mDataBinding.recyclerPhoto.setLayoutManager(manager);
        goodsImageEditAdapter = new GoodsImageEditAdapter(mImageList);
        mDataBinding.recyclerPhoto.setAdapter(goodsImageEditAdapter);
        goodsImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.llAddPhoto.getVisibility() == View.GONE) {
                    mDataBinding.llAddPhoto.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateReleaseProjrct(final Map<String, String> body) {
        BitmapUtils.pictureCompression(mContext, mList, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(List<String> imageUrl) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class,
                                RetrofitManager.RetrofitType.Object)
                                .addproject(ApiClient.getImageParams("imgfile", imageUrl), body)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                String projectId = result.getInfo();
                                Toast.makeText(ReleaseProjectActivity.this, "压缩成功", Toast.LENGTH_SHORT).show();
                                startOpenShare(projectId);
                            }
                        }));
            }
        });

    }

    private void startOpenShare(String projectId) {

        ShareHelper.ShareToNewsBean toNewsBean = new ShareHelper.ShareToNewsBean();
        ShareHelper.ShareBean bean = new ShareHelper.ShareBean();

        String title = mDataBinding.title.getText().toString();
        String info = mDataBinding.info.getText().toString();
        toNewsBean.title = title;
        toNewsBean.content = info;
        toNewsBean.onlylabel = Constant.SHARE_KEY_PROJECT;
        toNewsBean.project_id = projectId;
        toNewsBean.uid = LoginHelper.getInstance().getUserBean().getUid();
        toNewsBean.filePath = mImageList.get(0);

        bean.title = title;
        bean.content = info;
        bean.url = ApiService.SHARE_PROJECT + projectId;

        mShareHelper.setShareToNewsContent(toNewsBean);
        mShareHelper.setShareContent(bean);

        mShareHelper.startStare(new ShareHelper.OnShareCompleteCallback() {
            @Override
            public void onComplete() {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareHelper.onActivityForResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOODS_IMAGE) {
            if (resultCode == RESULT_OK) {
                mImageList.clear();
                mList.clear();
                List<String> list = data.getStringArrayListExtra(ImageGridActivity.RESULT_DATA);
                mList.addAll(list);
                mImageList.addAll(list);
                goodsImageEditAdapter.notifyDataSetChanged();
                if (list.size() == MAX_GOODS_IMAGE_COUNT) {
                    mDataBinding.llAddPhoto.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_project;
    }
}

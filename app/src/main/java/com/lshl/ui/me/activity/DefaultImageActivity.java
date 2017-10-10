package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.DefaultImageBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityDefaultImageBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.adapter.DefaultImageAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SinglePictureSelectPopWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import top.zibin.luban.OnCompressListener;

public class DefaultImageActivity extends BaseActivity<ActivityDefaultImageBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd = true;
    private List<DefaultImageBean.InfoBean> mList;
    private DefaultImageAdapter defaultImageAdapter;
    private boolean isSelect = false;

    private SinglePictureSelectHelper mSinglePictureHelper;
    private SinglePictureSelectPopWindow mSinglePictureWindow;
    private String mImageUrl;

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, DefaultImageActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("选择背景图", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("保存");
    }

    @Override
    protected void initFieldBeforeMethods() {
        mSinglePictureHelper = SinglePictureSelectHelper.getInstance();
        mSinglePictureWindow = new SinglePictureSelectPopWindow(this);
    }

    @Override
    protected void initViews() {
        mSinglePictureHelper.init(this);
        mSinglePictureWindow.bindHelper(mSinglePictureHelper);
        mSinglePictureWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        defaultImageAdapter = new DefaultImageAdapter(mList, isSelect);
        mDataBinding.recyclerView.setAdapter(defaultImageAdapter);
        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(final RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() == mList.size() - 1) {
                    lightOff();
                    mSinglePictureWindow.showPopupWindow(mDataBinding.getRoot());
                } else {
//                    Toast.makeText(mContext, "第"+(vh.getLayoutPosition()+1)+"张图片", Toast.LENGTH_SHORT).show();
//                    isSelect=true;
//                    onRefresh();
                    DialogUtils.alertDialog(mContext, "温馨提示", "是否上传此图片作为背景图?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            String substring = mList.get(vh.getLayoutPosition()).getImg();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateDefaultImage(LoginHelper.getInstance().getUserToken(), substring), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (result.getStatus().equals("1")) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }
                            }));
                        }
                    });
                }
            }
        });
        mDataBinding.defaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mSinglePictureWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mImageUrl)) {
                    showMessage("请在图库选择图片");
                    return;
                }
                File headImageFile = new File(mImageUrl);
                if (headImageFile.length() >= 1024 * 1024 * 1.5) {
                    //对图片进行压缩处理
                    ApiClient.pictureCompression(mContext, new File(mImageUrl), new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            updateDefaultImage(file);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
                } else {
                    updateDefaultImage(headImageFile);
                }
            }
        });
    }

    /*****
     * 上传本地图片
     */
    private void updateDefaultImage(File file) {
        MultipartBody.Part part = ApiClient.getFileBody(file);
        DialogUtils.showProgressDialog(mContext, "图片上传中...", "", "");
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateDeImage(part, RequestBody.create(null, LoginHelper.getInstance().getUserToken())), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals("1")) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(mContext, "上传失败,请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateDefaultBackGroundImage(), new ProgressSubscriber<DefaultImageBean>(mContext, new SubscriberOnNextListener<DefaultImageBean>() {
            @Override
            public void onNext(DefaultImageBean result) {
                if (result.getStatus() == 1) {
                    mList.addAll(result.getInfo());
                    defaultImageAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_default_image;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mDataBinding.springView.setVisibility(View.GONE);
            mDataBinding.defaultImage.setVisibility(View.VISIBLE);
            String imageUrl = mSinglePictureHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.defaultImage, false);
            if (!TextUtils.isEmpty(imageUrl)) {
                mImageUrl = imageUrl;
            }
        }
    }
}

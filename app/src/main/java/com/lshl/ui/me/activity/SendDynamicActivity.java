package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivitySendDynamicBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.ui.me.luxiang.LuXiangActivity;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ShareHelper;
import com.lshl.video.util.VideoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 发表圈子
 */
public class SendDynamicActivity extends BaseActivity<ActivitySendDynamicBinding> {
    private View parent;
    private BaiduMapLocationHelper locationHelper;
    private ShareHelper mShareHelper;
    private PopupWindow popupWindow;
    private LinearLayout ll_popup;
    private List<String> mList;//用于存放上传的图片列表
    //录像
    protected static final int AN_ZHE_PAI = 4;
    protected static final int REQUESTCODE_TAKE = 0;
    private static final int GO_IMAGE_GIRD_REQUEST_CODE = 0x00101;
    private GoodsImageEditAdapter imageAdapter;
    private static final int MAX_GOODS_IMAGE_COUNT = 6;
    private String path;//视频的存放地址
    private String message;
    public static final int SEND_DYNAMIC = 0x000121;
    private boolean isVoide;
    private String info;
    private String address;
    private String formart;
    private ArrayList<String> mImages;
    private Map<String, RequestBody> bodyMap = null;

    public static void actionStart(Activity activity, int resultCode) {
        Intent intent = new Intent(activity, SendDynamicActivity.class);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);

    }

    public class Presenter {

        public void addPhoto() {//添加图片或者视频
            parent = getLayoutInflater().inflate(R.layout.activity_send_dynamic, null);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(SendDynamicActivity.this, R.anim.activity_translate_in));
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            //先关闭软键盘
            closeKeybord(mDataBinding.etInfo, mContext);
        }
    }

    @Override
    protected void initFieldBeforeMethods() {
        mShareHelper = new ShareHelper(mContext);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("发布动态", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("发布");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                info = mDataBinding.etInfo.getText().toString().trim();
                address = mDataBinding.address.getText().toString();
                if (TextUtils.isEmpty(info)) {
                    Toast.makeText(mContext, "发布的内容不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                formart = "1";
                if (isVoide) {
                    mList.clear();
                    mList.add(path);
                    formart = "3";
                    bodyMap = ApiClient.getVideoParam(path);
                    sendDynamic(bodyMap);
                }
                if (mList.size() > 0) {
                    if (!isVoide) {
                        formart = "2";
                        BitmapUtils.pictureCompression(mContext, mList, new BitmapUtils.OnPhotoCompressionCallback() {
                            @Override
                            public void onCompressionCallback(List<String> imageUrl) {
                                bodyMap = ApiClient.getImageParams("file", imageUrl);
                                sendDynamic(bodyMap);
                            }
                        });

                    }
                } else {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(ApiService.class
                                    , RetrofitManager.RetrofitType.Object)
                                    .sendDynamics(
                                            RequestBody.create(null, LoginHelper.getInstance().getUserToken()),
                                            RequestBody.create(null, formart),
                                            RequestBody.create(null, info),
                                            RequestBody.create(null, address))
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                                    finish();
//                                    startOpenShare(result.getInfo());
                                }
                            }));
                }
            }
        });
    }

    @Override
    protected void initViews() {
        mImages = new ArrayList<>();
        mDataBinding.shareLayout.getShareNewsButton().setVisibility(View.GONE);
        mDataBinding.videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(path)) {
                    Uri uri = Uri.parse(path);
                    //调用系统自带的播放器
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Log.v("URI:::::::::", uri.toString());
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);
                }
            }
        });
        mShareHelper.bindShareLayout(mDataBinding.shareLayout);
        mDataBinding.setPresenter(new Presenter());
        initPopupWindowEvent();
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerImage.setLayoutManager(manager);
        mDataBinding.recyclerImage.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerImage.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        imageAdapter = new GoodsImageEditAdapter(mList);
        mDataBinding.recyclerImage.setAdapter(imageAdapter);
        imageAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.ivVideo.getVisibility() == View.GONE) {
                    mDataBinding.ivVideo.setVisibility(View.VISIBLE);
                }
            }
        });
        mDataBinding.recyclerImage.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerImage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                for (int i = 0; i < mList.size(); i++) {
                    mImages.add(mList.get(i));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }
        });
        //设置当前位置
        locationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        locationHelper.locationStart();
        locationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                mDataBinding.address.setText(location.getProvince() + location.getCity());
            }
        });
    }

    private void sendDynamic(Map<String, RequestBody> bodyMap) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class
                        , RetrofitManager.RetrofitType.Object)
                        .sendDynamics(
                                RequestBody.create(null, LoginHelper.getInstance().getUserToken()),
                                RequestBody.create(null, formart),
                                RequestBody.create(null, info),
                                bodyMap,
                                RequestBody.create(null, address))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
//                        startOpenShare(result.getInfo());
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_dynamic;
    }

    private void initPopupWindowEvent() {
        popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_video);
        Button bt4 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//拍照
                photo();
                popupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//从相册获取
                ImageGridActivity.actionStart(SendDynamicActivity.this, 6, (ArrayList<String>) mList, GO_IMAGE_GIRD_REQUEST_CODE);
              /*  Intent intent = new Intent(SendDynamicActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.MAX_IMAGE_COUNT, 6);
                startActivityForResult(intent, GO_IMAGE_GIRD_REQUEST_CODE);*/
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                popupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//录制视频
                Intent intent = new Intent(SendDynamicActivity.this, LuXiangActivity.class);
                intent.putExtra("message", mDataBinding.etInfo.getText().toString().trim());
                startActivityForResult(intent, AN_ZHE_PAI);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AN_ZHE_PAI://录像
                if (resultCode == RESULT_OK) {
                    path = data.getStringExtra("path");
                    message = data.getStringExtra("message");
                    Bitmap bitmap = VideoUtils.createVideoThumbnail(path);
                    mDataBinding.videoImage.setImageBitmap(bitmap);
                    mDataBinding.videoLayout.setVisibility(View.VISIBLE);
                    mDataBinding.ivVideo.setVisibility(View.GONE);
                    isVoide = true;
                    popupWindow.dismiss();
                }
                break;
            case REQUESTCODE_TAKE://拍照
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mList.clear();
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        mDataBinding.ivVideo.setImageBitmap(bm);
                        mDataBinding.ivVideo.setEnabled(false);
                    } else {
                        popupWindow.dismiss();
                    }
                }
                break;
            case GO_IMAGE_GIRD_REQUEST_CODE://从相册获取
                if (resultCode == RESULT_OK) {
                    mList.clear();
                    mList.addAll(data.getStringArrayListExtra(ImageGridActivity.RESULT_DATA));
                    imageAdapter.notifyDataSetChanged();
                    if (mList.size() > 5) {
                        mDataBinding.ivVideo.setVisibility(View.GONE);
                    }
                }
                break;
        }

    }

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, REQUESTCODE_TAKE);
    }

    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}

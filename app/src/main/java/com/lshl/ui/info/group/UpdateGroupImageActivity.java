package com.lshl.ui.info.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.UpdateGroupImageBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.view.SinglePictureSelectPopWindow;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import top.zibin.luban.OnCompressListener;

public class UpdateGroupImageActivity extends BaseActivity<UpdateGroupImageBinding> {

    private SinglePictureSelectPopWindow mPopWindow;
    private SinglePictureSelectHelper mPictureSelectHelper;

    private String mGroupAvatarUrl;

    private String mGroupId;

    public static void actionStart(Activity activity, String groupId, int requestCode) {
        Intent intent = new Intent(activity, UpdateGroupImageActivity.class);
        intent.putExtra("group_id", groupId);
        activity.startActivityForResult(intent, requestCode);
    }

    public class Presenter {

        public void groupHeadImageClick() {
            mPopWindow.showPopupWindow(mDataBinding.getRoot());
            lightOff();
        }

        public void submitGroupAvatar() {
            if (TextUtils.isEmpty(mGroupAvatarUrl)) {
                showMessage("请选择您要上传的图片");
                return;
            }
            File file = new File(mGroupAvatarUrl);
            if (file.length() <= 200 * 1024)//小于400k就不压缩图片
                uploadGroupAvatar(file);
            else//如果大于400k进行图片压缩处理
                pictureCompression(file);
        }


    }

    private void pictureCompression(File file) {

        ApiClient.pictureCompression(mContext, file, new OnCompressListener() {
            @Override
            public void onStart() {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                DialogUtils.showProgressDialog(mContext, "图片过大，正在压缩...", null, "压缩失败");
            }

            @Override
            public void onSuccess(File file) {
                // TODO 压缩成功后调用，返回压缩后的图片文件
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                uploadGroupAvatar(file);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("图片压缩出现问题", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });

    }

    private void uploadGroupAvatar(File file) {

        MultipartBody.Part photo = ApiClient.getFileBody(file);

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).updateHxGruopHead(photo, RequestBody.create(null, mGroupId))
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            String message = object.getString("message");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                setResult(RESULT_OK);
                                finish();
                            }
                            Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initFieldBeforeMethods() {
        mPopWindow = new SinglePictureSelectPopWindow(this);
        mPictureSelectHelper = new SinglePictureSelectHelper();
        mGroupId = getIntent().getStringExtra("group_id");
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("修改群头像", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mPictureSelectHelper.init(this);
        mPopWindow.bindHelper(mPictureSelectHelper);
        mDataBinding.setPresenter(new Presenter());
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_group_image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGroupAvatarUrl = mPictureSelectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.ivAddGroupHead, true);
    }
}

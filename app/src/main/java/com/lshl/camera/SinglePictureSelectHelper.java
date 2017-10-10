package com.lshl.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.view.GlideCircleTransform;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.lang.ref.WeakReference;

import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

/**
 * 作者：吕振鹏
 * 创建时间：10月11日
 * 时间：14:48
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SinglePictureSelectHelper {


    private static final int CAMERA_REQUEST_CODE = 0x0000707;//开启相机的请求码

    private static final int ALBUM_REQUEST_CODE = 0x0000708;//开启相册的请求码

    //定义一个成员变量，用于保存相机拍照的图片
    private String capturePath = null;

    private WeakReference<Activity> mActivity;

    private static SinglePictureSelectHelper sInstance;

    private boolean isThumbnail = false;

    public synchronized static SinglePictureSelectHelper getInstance() {
        if (sInstance == null) {
            sInstance = new SinglePictureSelectHelper();
        }
        return sInstance;
    }

    public SinglePictureSelectHelper init(Activity activity) {
        mActivity = new WeakReference<>(activity);
        return sInstance;
    }

    /**
     * 开启本地相机
     * 这种方法获取到的图片是被压缩处理过的，所以会非常的不清晰，建议使用
     */
    @Deprecated
    public void openCamera() {
        if (mActivity.get() == null) return;
        isThumbnail = true;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.get().startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启相机
     */
    public void openCameraForFile() {
        if (mActivity.get() == null) return;

        isThumbnail = false;

        final Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //获取保存的路径
        final String out_file_path = FileUtils.getInstance().getFilePath(mActivity.get());
        final File dir = new File(out_file_path);
        //申请权限（存储卡权限）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            RxPermissions.getInstance(mActivity.get()).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                openCamera(dir, out_file_path, getImageByCamera);
                            } else {
                                Toast.makeText(mActivity.get(), "读取存储卡权限获取失败，请在设置的应用中同意该权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            openCamera(dir, out_file_path, getImageByCamera);
        }

    }

    private void openCamera(File dir, String out_file_path, Intent getImageByCamera) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //给成员变量赋值
        capturePath = out_file_path + File.separator + System.currentTimeMillis() + ".jpg";

        Log.d("pricture", "创建临时存储图片的位置是" + capturePath);

        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(capturePath)));
        getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        mActivity.get().startActivityForResult(getImageByCamera, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启本地相册
     */
    public void openAlbum() {
        if (mActivity.get() == null) return;
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.get().startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);

    }

    public String onActivityResult(int requestCode, int resultCode, Intent data, ImageView showView, boolean isCircleImage) {
        Log.d("pricture", "在onActivityResult中Activity是否为空" + mActivity.get());
        if (mActivity.get() == null) return "";
        String imageUrl = "";
        switch (requestCode) {
            case CAMERA_REQUEST_CODE://相机的回调
                if (resultCode == RESULT_OK) {
                    if (isThumbnail) {
                        if (data != null && showView != null)
                            showView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        // Glide.with(this).load().into(mIvResultPhoto);
                    } else {

                        imageUrl = capturePath;
                        if (!TextUtils.isEmpty(capturePath)) {
                            if (showView != null) {
                                if (isCircleImage)
                                    Glide.with(mActivity.get()).load(new File(capturePath)).transform(new GlideCircleTransform(mActivity.get())).into(showView);
                                else
                                    Glide.with(mActivity.get()).load(new File(capturePath)).into(showView);
                            }
                        } else
                            Toast.makeText(mActivity.get(), "图片获取失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ALBUM_REQUEST_CODE://相册的回掉
                if (resultCode == RESULT_OK) {
                    imageUrl = FileUtils.getRealFilePath(mActivity.get(), data.getData());
                    if (showView != null) {
                        if (isCircleImage)
                            Glide.with(mActivity.get()).load(imageUrl).transform(new GlideCircleTransform(mActivity.get())).into(showView);
                        else
                            Glide.with(mActivity.get()).load(imageUrl).into(showView);
                    }
                }
                break;
        }
        return imageUrl;
    }

}

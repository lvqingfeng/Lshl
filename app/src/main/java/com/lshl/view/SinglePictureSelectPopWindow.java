package com.lshl.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.SelectPhotoPopBinding;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * 作者：吕振鹏
 * 创建时间：10月19日
 * 时间：14:16
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SinglePictureSelectPopWindow extends AddPopWindow {

    private Context mContext;
    private SinglePictureSelectHelper mSinglePictureSelectHelper;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public SinglePictureSelectPopWindow(Activity context) {

        super(context, R.layout.layout_pop_select_photo);
        mContext = context;

        setAnimationStyle(R.style.PopWindow_y_anim_style);

        SelectPhotoPopBinding selectPhotoPopBinding = SelectPhotoPopBinding.bind(getWindowRootView());

        PopClickListener listener = new PopClickListener();
        selectPhotoPopBinding.btnCancel.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenCamera.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenAlbum.setOnClickListener(listener);
    }

    public void bindHelper(SinglePictureSelectHelper helper) {
        mSinglePictureSelectHelper = helper;
    }

    /**
     * PopWindow按钮的点击事件
     */
    private class PopClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    break;
                case R.id.tv_open_camera:
                    RxPermissions.getInstance(mContext)
                            .request(Manifest.permission.CAMERA)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean granted) {
                                    if (granted) {
                                        if (mSinglePictureSelectHelper != null)
                                            mSinglePictureSelectHelper.openCameraForFile();
                                    } else {
                                        Toast.makeText(mContext, "只有同意权限后才能进行拍照,请您在应用详情中进行权限修改", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    break;
                case R.id.tv_open_album:
                    if (mSinglePictureSelectHelper != null)
                        mSinglePictureSelectHelper.openAlbum();
                    break;
            }
            closePopupWindow();
        }
    }

}

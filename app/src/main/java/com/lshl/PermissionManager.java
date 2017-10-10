package com.lshl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 作者：吕振鹏
 * 创建时间：12月12日
 * 时间：22:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PermissionManager {


    public static void applyPermission(final Context context, final OnApplyPermissionCallback callback) {
        //版本判断
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.CAMERA
                    , Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.RECORD_AUDIO};
            //检查是否拥有相应的权限
            List<String> permissionList = new ArrayList<>();
            for (String permissionStr : permissions) {
                int permission = ActivityCompat.checkSelfPermission(context, permissionStr);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissionStr);
                }
            }
            final String[] needPermission = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                needPermission[i] = permissionList.get(i);
            }
            if (needPermission.length <= 0) {
                callback.onSuccess();
                return;
            }
            RxPermissions.getInstance(context).request(needPermission).subscribe(new Action1<Boolean>() {
                private int permission = 0;

                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        permission++;
                        if (permission >= needPermission.length) {
                            callback.onSuccess();
                        }
                    } else {
                        System.exit(0);
                    }
                }
            });
         /*   RxPermissions.getInstance(context).shouldShowRequestPermissionRationale((SplashActivity) callback, permission).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        new AlertDialog.Builder(context)
                                .setTitle("温馨提示")
                                .setMessage("为了您能够正常的使用app,请同意一下权限")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        System.exit(0);//正常退出App
                                    }
                                })
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {


                                        }
                                    }
                                }).show();

                    }
                }
            });*/

        }
    }

    public interface OnApplyPermissionCallback {
        void onSuccess();
    }

}

package com.lshl.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 作者：吕振鹏
 * 创建时间：08月27日
 * 时间：16:46
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class ProgressDialog extends SweetAlertDialog {

    private Button mConfirmButton;

    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int alertType) {
        super(context, alertType);
    }


    @Override
    public SweetAlertDialog setConfirmText(String text) {
        mConfirmButton = (Button) findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        if (TextUtils.isEmpty(text)) {
            mConfirmButton.setVisibility(View.GONE);
        }
        return super.setConfirmText(text);
    }
}

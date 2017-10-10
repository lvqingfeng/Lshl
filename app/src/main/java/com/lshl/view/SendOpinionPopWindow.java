package com.lshl.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.databinding.SendOpinionPopBinding;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：17:06
 * 版本：v1.0.0
 * 类描述：发表评论的PopWindow
 * 修改时间：
 */

public class SendOpinionPopWindow extends AddPopWindow {

    private SendOpinionPopBinding mBinding;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public SendOpinionPopWindow(Activity context, final SendOpinionCallback callback) {
        super(context, R.layout.layout_pop_send_opinion);
        mBinding = SendOpinionPopBinding.bind(getWindowRootView());

        mBinding.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (TextUtils.isEmpty(mBinding.editContent.getText().toString().trim())) {
                    Toast.makeText(mContext, "请填写您要说的话^_^", Toast.LENGTH_SHORT).show();
                    return;
                }
                callback.onCallback(mBinding.editContent.getText().toString(), mBinding.cbxAnonymous.isChecked());
                mBinding.editContent.setText("");
            }
        });
    }

    /**
     * 如果不需要匿名的话，可以调用该方法隐藏
     */
    public void setAnonymousGone() {
        mBinding.cbxAnonymous.setVisibility(View.GONE);
    }

    @Override
    public void showPopupWindow(View parent) {
        super.showPopupWindow(parent);
        //在开启PopupWindow的同时弹出输入法
        InputMethodManager inputMethodManager = (InputMethodManager) parent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    public interface SendOpinionCallback {
        void onCallback(String inputText, boolean isAnonymous);
    }

}

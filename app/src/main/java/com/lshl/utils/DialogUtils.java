package com.lshl.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.lshl.R;
import com.lshl.ui.MainActivity;
import com.lshl.widget.IosDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 作者：吕振鹏
 * 创建时间：08月27日
 * 时间：16:02
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class DialogUtils {

    private static LshlLogoLoadingDialog sSweetAlertDialog;//计时器
    private static CountDownTimer sTimer;//计时器
    private static LoadCompleteType sLoadCompleteType;//加载类型

    public static IosDialog iosAlertDialog(Context context, CharSequence titleText, CharSequence messageText,
                                           IosDialog.OnClickListener cancelListener, IosDialog.OnClickListener confirmListener) {
        IosDialog dialog = new IosDialog(context)
                .setTitleText(titleText)
                .setMessageText(messageText)
                .setCancelListener("", cancelListener)
                .setConfirmListener("", confirmListener);
        dialog.show();
        return dialog;
    }

    /****
     * 评论需要输入文本的弹出式对话框
     */
    public static AlertDialog CommentDialog(Context context, CharSequence title, View view, CharSequence confirm, CharSequence cancle, AlertDialog.OnClickListener onClickListener, AlertDialog.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setView(view).setNegativeButton(confirm, onClickListener).setPositiveButton(cancle, clickListener);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static SweetAlertDialog alertDialog(Context context, String titleText, String messageText,
                                               SweetAlertDialog.OnSweetClickListener cancelListener, SweetAlertDialog.OnSweetClickListener confirmListener) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText(messageText)
                .setCancelText("取消")
                .setConfirmText("确认")
                .showCancelButton(true)
                .setCancelClickListener(cancelListener)
                .setConfirmClickListener(confirmListener);
        dialog.show();
        return dialog;
    }

    public static SweetAlertDialog alertDialogNoCancel(Context context, String titleText, String messageText, SweetAlertDialog.OnSweetClickListener confirmListener) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(titleText)
                .setContentText(messageText)
                .setConfirmText("确认")
                .showCancelButton(false)
                .setConfirmClickListener(confirmListener);
        dialog.show();
        return dialog;
    }

    public static void showProgressDialog(Context context, final String titleText, final String successText, final String errorText) {
        sSweetAlertDialog = new LshlLogoLoadingDialog(context);
        sSweetAlertDialog.setMessage(titleText);
        sSweetAlertDialog.show();
    }


    public static void hideDialog(LoadCompleteType type) {
        if (sSweetAlertDialog != null) {
            sSweetAlertDialog.hide();
        }
    }

    /**
     * 加载完成以后的类型
     */
    public enum LoadCompleteType {
        Success, Error
    }

    /**
     * 更新对话框
     *
     * @param context
     * @param title
     * @param message
     * @param positiveBtnName
     * @param negativeBtnName
     * @param positiveBtnListener
     * @param negativeBtnListener
     * @return
     */
    public static Dialog createUpdateDialog(Context context, String title, String message,
                                            String positiveBtnName, String negativeBtnName,
                                            DialogInterface.OnClickListener positiveBtnListener,
                                            DialogInterface.OnClickListener negativeBtnListener, int updateFlag) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置对话框标题
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        //设置对话框消息
        builder.setMessage(message);
        if (MainActivity.FORCE_UPDATE_FLAG == updateFlag) {
            //设置确定按钮
            builder.setPositiveButton(positiveBtnName, positiveBtnListener);
        } else if (MainActivity.NORMAL_UPDATE_FLAG == updateFlag) {
            //设置确定按钮
            builder.setPositiveButton(positiveBtnName, positiveBtnListener);
            //设置取消按钮
            builder.setNegativeButton(negativeBtnName, negativeBtnListener);
        }
        //创建一个消息对话框
        dialog = builder.create();
        dialog.setCancelable(false);

        return dialog;
    }


}

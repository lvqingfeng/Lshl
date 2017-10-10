package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityQrcodeBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.zxing.activity.CaptureActivity;
import com.lshl.zxing.encoding.EncodingHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class QRcodeActivity extends BaseActivity<ActivityQrcodeBinding> {
    private Bitmap bitmap;
    private ShareAction mShareAction;
    private String mUrl="http://lushanghulian.com/Download/index/";
    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享成功了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(mContext, "分享失败了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, QRcodeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle!=null){
                String result = bundle.getString("result");
                if (result.contains(mUrl)){
                    String substring = result.substring(mUrl.length());
//                    Toast.makeText(mContext, mUrl.length()+"==="+result+"==="+substring, Toast.LENGTH_LONG).show();
                    HxMemberDetailsActivity.actionStart(QRcodeActivity.this,"",substring,false);
                }else {
                    if (result.contains("http")){
                        ScanningResultActivity.actionStart(QRcodeActivity.this,result);
                    }else {
                        Toast.makeText(mContext,"您的扫描结果是"+result,Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(mContext, "抱歉!只支持本应用的二维码扫描", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的二维码", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.name.setText(LoginHelper.getInstance().getUserBean().getRealName());
        Glide.with(mContext).load(ApiClient.getHxFriendsImage(LoginHelper.getInstance().getUserBean().getAvatar())).error(R.mipmap.account_logo).into(mDataBinding.headImage);
        try {
            if (!TextUtils.isEmpty(LoginHelper.getInstance().getHxId())) {
                bitmap= encodeAsBitmap("http://lushanghulian.com/Download/index/"+LoginHelper.getInstance().getUserBean().getUid());
                mDataBinding.qrCode.setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext, "暂时无法生成", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mDataBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap(bitmap);
                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        mDataBinding.sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(QRcodeActivity.this,CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
        mShareAction = new ShareAction(QRcodeActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ).setCallback(mShareListener);
        mDataBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mShareAction.withMedia(new UMImage(mContext,bitmap))
//                        .withTitle("扫一扫加我为好友").setCallback(mShareListener)
//                        .open();

            }
        });
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtils.isEmpty(LoginHelper.getInstance().getHxId())) {
                        bitmap= encodeAsBitmap("http://lushanghulian.com/Download/index/"+LoginHelper.getInstance().getUserBean().getUid());
                        mDataBinding.qrCode.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(mContext, "暂时无法生成", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    /****
     * 生成二维码的方法
     * */
    private Bitmap encodeAsBitmap(String str){
        Bitmap bitmap = null;
        try {
            // 使用 ZXing Android Embedded 要写的代码
            bitmap= EncodingHandler.createQRCode(str,280);
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }
        return bitmap;
    }
    /**
     * 将Bitmap保存在本地
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + "/sdcard/namecard/")));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }
}

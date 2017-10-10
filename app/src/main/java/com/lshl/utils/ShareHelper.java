package com.lshl.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.HttpResult;
import com.lshl.widget.ShareLinerLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * 作者：吕振鹏
 * 创建时间：11月30日
 * 时间：14:10
 * 版本：v1.0.0
 * 类描述：这是一个分享的帮助类，主要是和{@link ShareLinerLayout}相结合
 * <p>使用方式
 * ************* 准备****************
 * 1.获取当前Helper的实例
 * 2.通过{@link #setShareContent(ShareBean)}设置要分享的内容
 * 3.通过{@link #bindShareLayout(ShareLinerLayout)}绑定用于控制分享的布局
 * 4.在Activity中的onActivityForResult()中调用本类中的{@link #onActivityForResult(int, int, Intent)}方法
 * 5.--------特别处理-----------
 * 当需要分享到新闻的时候必须通过{@link #setShareToNewsContent(ShareToNewsBean)}方法设置需要分享到新闻的内容
 * -----------------end--------------
 * *********开启发布**********
 * 通过{@link #startStare(OnShareCompleteCallback)} 开启分享分享
 * </p>
 * <p>
 * 目前需要分享的类包含一下内容
 *
 * @see com.lshl.ui.me.dscs.activity.PublishDscsActivity 发布滴水穿石
 * @see com.lshl.ui.me.activity.ReleaseReputationActivity 发布口碑
 * @see com.lshl.ui.me.activity.SendDynamicActivity 发布动态（圈子）
 * @see com.lshl.ui.me.activity.EditGoodsActivity 发表自贸商品
 * @see com.lshl.ui.me.activity.ReleaseProjectActivity 发布项目
 * 以上内容为需要分享的内容
 * </p>
 * 修改时间：
 */

public class ShareHelper {

    private List<SHARE_MEDIA> mShareMediaList;
    private Context mContext;
    private ShareLinerLayout mShareLayout;
    private ShareListener mShareListener;

    private ShareBean mShareBean;
    private ShareToNewsBean mShareToNewsBean;

    private int mShareCount;//总分享的条目
    private int mShareCurrentPosition;//当前的分享的位置
    private OnShareCompleteCallback mShareCompleteCallback;
    private boolean isShareNews;

    public static class ShareBean {
        public String title;
        public String content;
        public String url;
    }

    public static class ShareToNewsBean {
        public String title;//项目标题
        public String filePath;//图片地址（非必传）
        public String content;//内容
        public String onlylabel;//唯一标识
        public String project_id;//项目id
        public String uid;//分享人id
        public String description;//口碑的红黑标识(非必传)
    }

    public ShareHelper(Context context) {
        mContext = context;
        mShareListener = new ShareListener();
        mShareMediaList = new ArrayList<>();
    }

    public void setShareContent(ShareBean bean) {
        mShareBean = bean;
    }

    public void setShareToNewsContent(ShareToNewsBean bean) {
        mShareToNewsBean = bean;
    }

    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 开启分享
     */
    public void startStare(OnShareCompleteCallback callback) {
        mShareCompleteCallback = callback;
        mShareLayout.getSelectResult();

    }

    /**
     * @param layout
     */
    public void bindShareLayout(ShareLinerLayout layout) {
        mShareLayout = layout;
        mShareLayout.addOnShareSelectCallback(new ShareLinerLayout.OnShareSelectCallback() {
            @Override
            public void onSelectCallback(boolean isShareNews, boolean isShareQQ, boolean isShareWx, boolean isShareSina, boolean isShareWxCircle) {
                addShareMedia(isShareSina ? SHARE_MEDIA.SINA : SHARE_MEDIA.MORE);
                addShareMedia(isShareQQ ? SHARE_MEDIA.QQ : SHARE_MEDIA.MORE);
                addShareMedia(isShareWx ? SHARE_MEDIA.WEIXIN : SHARE_MEDIA.MORE);
                addShareMedia(isShareWxCircle ? SHARE_MEDIA.WEIXIN_CIRCLE : SHARE_MEDIA.MORE);
                startShare(isShareNews);
            }
        });
    }

    private void startShare(boolean isShareNews) {
        mShareCount = mShareMediaList.size();
        if (isShareNews) {
            this.isShareNews = isShareNews;
            startShareToNews();
        } else {
            startShareToMedia();
        }

    }

    private void startShareToMedia() {
        if (mShareMediaList.size() > 0) {
            final SHARE_MEDIA shareMedia = mShareMediaList.remove(0);
            mShareCurrentPosition++;
            DialogUtils.alertDialog(mContext, "温馨提示", "正在分享到" + getShareMediaName(shareMedia) + "\r\n第" + mShareCurrentPosition + "/" + mShareCount + "条"
                    , new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            startShareToMedia();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            if (mShareBean != null) {
                                new ShareAction((Activity) mContext)
                                        .withText(mShareBean.content)
                                        .withTargetUrl(mShareBean.url)
                                        .withTitle(mShareBean.title)
                                        .setPlatform(shareMedia)
                                        .setCallback(mShareListener)
                                        .share();

                            }
                        }
                    });
        } else {
            if (mShareCount > 0)
                Toast.makeText(mContext, "分享已结束", Toast.LENGTH_SHORT).show();
            mShareCurrentPosition = 0;
            mShareCount = 0;
            mShareCompleteCallback.onComplete();

        }

    }

    private void startShareToNews() {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("正在分享到首页...");
        dialog.show();
        if (mShareToNewsBean != null) {
            if (mShareToNewsBean.filePath!=null){
                MultipartBody.Part imageBody = ApiClient.getFileNews(new File(mShareToNewsBean.filePath));
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).shareToNews(
                                imageBody,
                                RequestBody.create(null, mShareToNewsBean.title),
                                RequestBody.create(null, mShareToNewsBean.content),
                                RequestBody.create(null,mShareToNewsBean.description),
                                RequestBody.create(null, mShareToNewsBean.onlylabel),
                                RequestBody.create(null, mShareToNewsBean.project_id),
                                RequestBody.create(null, mShareToNewsBean.uid))
                        , new Subscriber<HttpResult<String>>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                                startShareToMedia();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult<String> stringHttpResult) {
                                Toast.makeText(mContext, "首页分享结果:" + stringHttpResult.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }else {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).shareToNewsIsText(
                                RequestBody.create(null, mShareToNewsBean.title),
                                RequestBody.create(null, mShareToNewsBean.content),
                                RequestBody.create(null,mShareToNewsBean.description),
                                RequestBody.create(null, mShareToNewsBean.onlylabel),
                                RequestBody.create(null, mShareToNewsBean.project_id),
                                RequestBody.create(null, mShareToNewsBean.uid))
                        , new Subscriber<HttpResult<String>>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                                startShareToMedia();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult<String> stringHttpResult) {
                                Toast.makeText(mContext, "首页分享结果:" + stringHttpResult.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        }
    }

    private void addShareMedia(SHARE_MEDIA media) {
        if (media == SHARE_MEDIA.MORE) return;
        mShareMediaList.add(media);
    }

    private void cleanShareMedia() {
        mShareMediaList.clear();
    }

    private class ShareListener implements UMShareListener {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享成功了", Toast.LENGTH_SHORT).show();
            startShareToMedia();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
            startShareToMedia();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "分享已取消", Toast.LENGTH_SHORT).show();
            startShareToMedia();
        }
    }

    private void showShareResult(SHARE_MEDIA share_media, String status) {

    }


    private String getShareMediaName(SHARE_MEDIA media) {
        switch (media) {
            case QQ:
                return "QQ";
            case WEIXIN:
                return "微信";
            case WEIXIN_CIRCLE:
                return "微信朋友圈";
            case SINA:
                return "新浪微博";
            default:
                return "";
        }
    }

    public interface OnShareCompleteCallback {
        void onComplete();
    }

}

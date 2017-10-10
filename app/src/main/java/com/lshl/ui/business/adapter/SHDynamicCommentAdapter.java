package com.lshl.ui.business.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.NewsCommentBean;
import com.lshl.bean.SHDynamCommentBean;
import com.lshl.databinding.ItemLayoutWebviewBinding;
import com.lshl.databinding.ShanghuiDynamCommentItemBinding;
import com.lshl.ui.business.activity.SHDynamCommentListActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.ui.news.adapter.NewsInfoAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/******
 * Created by 吕清锋on 2017/1/18.
 * 商会动态评论页 适配器
 * 多布局的实现  第一条为webview剩下的为评论item
 */

public class SHDynamicCommentAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private static final int TYPE_WEB = 0x0000123;
    private List<SHDynamCommentBean.ListBean> mListData;
    private String mUrl;
    private BaseActivity activity;
    private ArrayList<String> mImages;

    public SHDynamicCommentAdapter(BaseActivity activity, String mUrl, List<SHDynamCommentBean.ListBean> mListData) {
        this.activity = activity;
        this.mUrl = mUrl;
        this.mListData = mListData;
        mImages=new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_WEB;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case TYPE_WEB:
                View view=inflater.inflate(R.layout.item_layout_webview,parent,false);
                return new SHDynamicCommentAdapter.WebViewTypeHolder(view);
            default:
                View rootView=inflater.inflate(R.layout.item_layout_shanghui_dynam_comment,parent,false);
                return new SHDynamicCommentAdapter.ItemHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_WEB:
                ((SHDynamicCommentAdapter.WebViewTypeHolder)holder).getBinding().webView
                        .setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                    }
                });
                ((SHDynamicCommentAdapter.WebViewTypeHolder)holder).getBinding().webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        super.onReceivedSslError(view, handler, error);
                        handler.proceed();
                    }
                });
                clearCacheFolder(holder.mContext.getCacheDir(),System.currentTimeMillis());
                ((SHDynamicCommentAdapter.WebViewTypeHolder)holder).getBinding().webView.loadUrl(mUrl);

                break;
            default:
                final SHDynamCommentBean.ListBean listBean = mListData.get(position-1);
                ((SHDynamicCommentAdapter.ItemHolder) holder).getBinding().setCommentBean(listBean);
                Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(listBean.getAvatar())).into(((ItemHolder) holder).getBinding().ivHead);
                if (position>=2){
                    Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(listBean.getAvatar())).error(R.mipmap.account_logo).into(((ItemHolder) holder).getBinding().ivHead);
                }
                ((ItemHolder) holder).getBinding().ivHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImages.clear();
                        mImages.add(ApiClient.getHxFriendsImage(listBean.getAvatar()));
                        Intent intent = new Intent(holder.mContext, PhotoShowActivity.class);
                        intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                        intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                        intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                        activity.startActivity(intent);
                    }
                });
//                if (mListData.size()<=1&&mListData.get(0).getUrl()!=null){
//
//                }else {
//
//                }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size() > 0 ? mListData.size() + 1 : 0;
    }
    private class WebViewTypeHolder extends BindingViewHolder<ItemLayoutWebviewBinding>{

        public WebViewTypeHolder(View view) {
            super(ItemLayoutWebviewBinding.bind(view));
        }
    }
    private class ItemHolder extends BindingViewHolder<ShanghuiDynamCommentItemBinding>{

        public ItemHolder(View view) {
            super(ShanghuiDynamCommentItemBinding.bind(view));
        }
    }
    private int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}

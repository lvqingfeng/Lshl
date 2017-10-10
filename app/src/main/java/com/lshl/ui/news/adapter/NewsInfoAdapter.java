package com.lshl.ui.news.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.NewsCommentBean;
import com.lshl.databinding.ItemLayoutWebviewBinding;
import com.lshl.databinding.NewsCommentItemBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/******
 *要闻H5详情页的评论
 */
public class NewsInfoAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private static final int TYPE_WEB = 1;
    private List<NewsCommentBean.ListBean> mListData;
    private String mUrl;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public NewsInfoAdapter(List<NewsCommentBean.ListBean> mListData, String mUrl, BaseActivity activity) {
        this.mListData = mListData;
        this.mUrl = mUrl;
        this.activity = activity;
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
                return new NewsInfoAdapter.WebViewTypeHolder(view);
            default:
                View rootViews=inflater.inflate(R.layout.item_layout_news_comment,parent,false);
                return new NewsInfoAdapter.ItemHolder(rootViews);
        }
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case TYPE_WEB:
                clearCacheFolder(holder.mContext.getCacheDir(),System.currentTimeMillis());
//                WebSettings webSettings = ((WebViewTypeHolder) holder).getBinding().webView.getSettings();
//                // 让WebView能够执行javaScript
//                webSettings.setJavaScriptEnabled(true);
//                // 让JavaScript可以自动打开windows
//                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//                // 设置缓存
//                webSettings.setAppCacheEnabled(true);
//                // 设置缓存模式,一共有四种模式
//                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//                webSettings.setDefaultTextEncodingName("UTF-8");
//                webSettings.setLoadsImagesAutomatically(true);
                ((WebViewTypeHolder)holder).getBinding().webView.loadUrl(mUrl);
                break;
           default:
               final NewsCommentBean.ListBean bean = mListData.get(position - 1);
               Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).into(((ItemHolder) holder).getBinding().ivAvatar);
               if (mListData.size()>1){
                   Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).error(R.mipmap.account_logo).into(((ItemHolder) holder).getBinding().ivAvatar);
               }
               ((NewsInfoAdapter.ItemHolder) holder).getBinding().setCommentBean(bean);
               ((ItemHolder) holder).getBinding().ivAvatar.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mImages.clear();
                       mImages.add(ApiClient.getHxFriendsImage(bean.getAvatar()));
                       Intent intent = new Intent(holder.mContext, PhotoShowActivity.class);
                       intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                       intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                       intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                       activity.startActivity(intent);
                   }
               });
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
    private class ItemHolder extends BindingViewHolder<NewsCommentItemBinding>{

        public ItemHolder(View view) {
            super(NewsCommentItemBinding.bind(view));
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

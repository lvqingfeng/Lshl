package com.lshl.ui.news.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.lshl.LoadBannerHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.BannerBean;
import com.lshl.bean.NewsListBean;
import com.lshl.databinding.ItemLayoutKoubeiBinding;
import com.lshl.databinding.ItemLayoutKoubeiNewsBinding;
import com.lshl.databinding.NewsBannerHeadBinding;
import com.lshl.databinding.NewsListItemBinding;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：11:51
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsListAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_KOUBEI=2;
    private List<NewsListBean.ListBean> mListData;
    private String label;
    private boolean isInitBanner;
    private ConvenientBanner mHeadBanner;
    private Activity activity;
    public NewsListAdapter(List<NewsListBean.ListBean> mListData, String label, Activity activity) {
        this.mListData = mListData;
        this.label = label;
        this.activity=activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (mListData.size() > 0 && position == 0) {
            return TYPE_BANNER;
        }
        if ("koubei".equals(mListData.get(position-1).getOnlylabel())){
            return TYPE_KOUBEI;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_BANNER:
                View rootView = inflater.inflate(R.layout.item_head_news_banner, parent, false);
                return new BannerTypeHolder(rootView);
            case TYPE_KOUBEI:
                View view=inflater.inflate(R.layout.item_layout_koubei_news,parent,false);
                return new NewsListAdapter.KouBeiHolder(view);
            default:
                View itemView = inflater.inflate(R.layout.item_layout_news, parent, false);
                return new ItemHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_BANNER:
                if (!isInitBanner) {
                    LoadBannerHelper.getBanner(label, new LoadBannerHelper.InitBannerImage() {
                        @Override
                        public void setBanner(List<String> mList, final BannerBean bannerBean) {
                            mHeadBanner = ((BannerTypeHolder) holder).getBinding().convenientBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {
                                @Override
                                public LoadImageHolder createHolder() {
                                    return new LoadImageHolder();
                                }
                            }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_select, R.drawable.bg_banner_unselect}).startTurning(3200);
                            mHeadBanner.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    BannerBean.InfoBean infoBean = bannerBean.getInfo().get(position);
                                    if (TextUtils.isEmpty(infoBean.getBn_url())){
                                        return;
                                    }
                                    switch (infoBean.getBn_url()){
                                        case "shanghui":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                SHDynamDetailsicActivity.actionStart(activity, infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "new_member":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                HxMemberDetailsActivity.actionStart(activity,"",infoBean.getBn_urlid(),false);
                                            }
                                            break;
                                        case "xiangmu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                ProjectDetailActivity.actionStart(activity, infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                            }
                                            break;
                                        case "koubei":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                KouBeiDetailsActivity.actionStart(activity, infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                            }
                                            break;
                                        case "gongyi":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "huzhu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MA,infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "zimaoqu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                GoodsDetailsActivity.actionStart(activity,infoBean.getBn_urlid(),GoodsDetailsActivity.FROM_OTHER);
                                            }
                                            break;
                                        case "findhelper":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                LookHelpDetailsActivity.actionStart(activity,infoBean.getBn_urlid());
                                            }
                                            break;
                                        default:
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())){
                                                NewsInfoActivity.actionStart(activity, infoBean.getBn_urlid(), ApiClient.getNewsImage(infoBean.getBn_img()));
                                            }
                                            break;
                                    }
                                }
                            });
                        }

                    });
                    isInitBanner = true;

                }
                break;
            case TYPE_KOUBEI:
                NewsListBean.ListBean koubeis=mListData.get(position-1);
                ((NewsListAdapter.KouBeiHolder)holder).getBinding().setNewsBean(koubeis);
                if (ApiService.STATUS_SUC.equals(koubeis.getDescription())){
                    ((KouBeiHolder)holder).getBinding().hongbang.setImageResource(R.mipmap.hongbang);
                }else {
                    ((KouBeiHolder)holder).getBinding().hongbang.setImageResource(R.mipmap.heibang);
                }
                break;
            default:
                NewsListBean.ListBean listBean = mListData.get(position - 1);
                String imageUrl = "";
                switch (listBean.getOnlylabel()){
                    case "shanghui":
                        imageUrl = ApiClient.getBuinessDynamicImage(listBean.getImg());
                        break;
                    default:
                        imageUrl = ApiClient.getNewsImage(listBean.getImg());
                        break;
                }
                Log.i("屮艸芔茻", imageUrl);
                Glide.with(((ItemHolder) holder).mContext).load(imageUrl).error(R.drawable.account_logo)
                        .into(((ItemHolder) holder).getBinding().imageView5);
                ((ItemHolder) holder).getBinding().setImageUrl(imageUrl);
                ((ItemHolder) holder).getBinding().setNewsBean(listBean);
                break;
        }
    }

    public void bannerStart() {
        if (mHeadBanner != null) {
            mHeadBanner.startTurning(3000);
        }
    }

    public void bannerStop() {
        if (mHeadBanner != null) {
            mHeadBanner.stopTurning();
        }
    }


    @Override
    public int getItemCount() {
        return mListData.size() > 0 ? mListData.size() + 1 : 0;
    }

    private class BannerTypeHolder extends BindingViewHolder<NewsBannerHeadBinding> {

        public BannerTypeHolder(View view) {
            super(NewsBannerHeadBinding.bind(view));
        }
    }

    private class ItemHolder extends BindingViewHolder<NewsListItemBinding> {

        public ItemHolder(View view) {
            super(NewsListItemBinding.bind(view));
        }
    }
    private class KouBeiHolder extends BindingViewHolder<ItemLayoutKoubeiNewsBinding>{

        public KouBeiHolder(View view) {
            super(ItemLayoutKoubeiNewsBinding.bind(view));
        }
    }
}

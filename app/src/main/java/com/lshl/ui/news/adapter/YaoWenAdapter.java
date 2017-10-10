package com.lshl.ui.news.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.lshl.LoadBannerHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.BannerBean;
import com.lshl.bean.NewsListBean;
import com.lshl.databinding.ItemLayoutBusinessBinding;
import com.lshl.databinding.ItemLayoutFindHelperBinding;
import com.lshl.databinding.ItemLayoutKoubeisBinding;
import com.lshl.databinding.ItemLayoutNewMemberBinding;
import com.lshl.databinding.ItemLayoutYingyongBinding;
import com.lshl.databinding.NewsBannerHeadBinding;
import com.lshl.databinding.NewsListItemBinding;
import com.lshl.ui.appliance.activity.HotServiceActivity;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.KouBeiListActivity;
import com.lshl.ui.appliance.activity.LookHelpListActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.appliance.activity.SpecificShoppingActivity;
import com.lshl.ui.appliance.activity.TradeActivity;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Administrator on 2017/1/16.
 */

public class YaoWenAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_RECYCLER = 2;
    private static final int TYPE_NEWMEMBER = 3;
    private static final int TYPE_BUSINESS = 4;
    private static final int TYPE_FIND_HELP = 5;
    private static final int TYPE_KOUBEI = 6;
    private List<NewsListBean.ListBean> mListData;
    private String label;
    private boolean isInitBanner;
    private ConvenientBanner mHeadBanner;
    private BaseActivity activity;
    private ArrayList<String> mImages;

    public YaoWenAdapter(List<NewsListBean.ListBean> mListData, String label, BaseActivity activity) {
        this.mListData = mListData;
        this.label = label;
        this.activity = activity;
        mImages = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListData.size() > 0 && position == 0) {
            return TYPE_BANNER;
        }
        if (mListData.size() > 0 && position == 1) {
            return TYPE_RECYCLER;
        }
        if ("new_member".equals(mListData.get(position - 2).getOnlylabel())) {
            return TYPE_NEWMEMBER;
        }
        if ("shanghui".equals(mListData.get(position - 2).getOnlylabel())) {
            return TYPE_BUSINESS;
        }
        if ("juankuan".equals(mListData.get(position - 2).getOnlylabel()) || "huizhang".equals(mListData.get(position - 1).getOnlylabel()) || "findhelper".equals(mListData.get(position - 1).getOnlylabel())) {
            return TYPE_FIND_HELP;
        }
        if ("koubei".equals(mListData.get(position - 2).getOnlylabel())) {
            return TYPE_KOUBEI;
        }
        return super.getItemViewType(position);
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
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_BANNER:
                View rootView = inflater.inflate(R.layout.item_head_news_banner, parent, false);
                return new YaoWenAdapter.BannerTypeHolder(rootView);
            case TYPE_RECYCLER:
                View view = inflater.inflate(R.layout.item_layout_yingyong, parent, false);
                return new YaoWenAdapter.YingyongHolder(view);
            case TYPE_NEWMEMBER:
                View newViews = inflater.inflate(R.layout.item_layout_new_member, parent, false);
                return new YaoWenAdapter.NewMemberHolder(newViews);
            case TYPE_BUSINESS:
                View business = inflater.inflate(R.layout.item_layout_business, parent, false);
                return new YaoWenAdapter.BusinessHolder(business);
            case TYPE_FIND_HELP:
                View findHelper = inflater.inflate(R.layout.item_layout_find_helper, parent, false);
                return new YaoWenAdapter.FindHelperHolder(findHelper);
            case TYPE_KOUBEI:
                View koubeis = inflater.inflate(R.layout.item_layout_koubeis, parent, false);
                return new YaoWenAdapter.KoubeiHolder(koubeis);
            default:
                View itemView = inflater.inflate(R.layout.item_layout_news, parent, false);
                return new YaoWenAdapter.ItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_BANNER:
                if (!isInitBanner) {
                    LoadBannerHelper.getBanner(label, new LoadBannerHelper.InitBannerImage() {
                        @Override
                        public void setBanner(List<String> mList, final BannerBean bannerBean) {
                            mHeadBanner = ((YaoWenAdapter.BannerTypeHolder) holder).getBinding().convenientBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {

                                @Override
                                public LoadImageHolder createHolder() {
                                    return new LoadImageHolder();
                                }
                            }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_select, R.drawable.bg_banner_unselect}).startTurning(3200);
                            mHeadBanner.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    BannerBean.InfoBean infoBean = bannerBean.getInfo().get(position);
                                    if (TextUtils.isEmpty(infoBean.getBn_url())) {
                                        return;
                                    }
                                    switch (infoBean.getBn_url()) {
                                        case "shanghui":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                SHDynamDetailsicActivity.actionStart(activity, infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "new_member":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                HxMemberDetailsActivity.actionStart(activity, "", infoBean.getBn_urlid(), false);
                                            }
                                            break;
                                        case "xiangmu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                ProjectDetailActivity.actionStart(activity, infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                            }
                                            break;
                                        case "koubei":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                KouBeiDetailsActivity.actionStart(activity, infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                            }
                                            break;
                                        case "gongyi":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "huzhu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MA, infoBean.getBn_urlid());
                                            }
                                            break;
                                        case "zimaoqu":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                GoodsDetailsActivity.actionStart(activity, infoBean.getBn_urlid(), GoodsDetailsActivity.FROM_OTHER);
                                            }
                                            break;
                                        case "findhelper":
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                                LookHelpDetailsActivity.actionStart(activity, infoBean.getBn_urlid());
                                            }
                                            break;
                                        default:
                                            if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
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
            case TYPE_RECYCLER:
//                ((YingyongHolder)holder).getBinding().dscs.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ApplianceDscsActivity.actionStart(activity);
//                    }
//                });
                ((YingyongHolder) holder).getBinding().goods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TradeActivity.actionStart(activity);
                    }
                });
                ((YingyongHolder) holder).getBinding().koubei.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KouBeiListActivity.actionStart(activity);
                    }
                });
//                ((YingyongHolder)holder).getBinding().project.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ProjectActivity.actionStart(activity);
//                    }
//                });
                ((YingyongHolder) holder).getBinding().service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HotServiceActivity.actionStart(activity);
                    }
                });
                ((YingyongHolder) holder).getBinding().lookHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LookHelpListActivity.actionStart(activity);
                    }
                });
                ((YingyongHolder) holder).getBinding().youqing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpecificShoppingActivity.actionStart(activity);
                    }
                });
                break;
            case TYPE_NEWMEMBER:
                String headimage = "";
                final NewsListBean.ListBean newMember = mListData.get(position - 2);
                headimage = ApiClient.getNewsImage(newMember.getImg());
                ((YaoWenAdapter.NewMemberHolder) holder).getBinding().setImageUrl(headimage);
                ((YaoWenAdapter.NewMemberHolder) holder).getBinding().setNewsBean(newMember);
                if (!TextUtils.isEmpty(newMember.getImg())) {
                    ((NewMemberHolder) holder).getBinding().headImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mImages.clear();
                            mImages.add(ApiClient.getNewsImage(newMember.getImg()));
                            Intent intent = new Intent(holder.mContext, PhotoShowActivity.class);
                            intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                            intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                            intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                            activity.startActivity(intent);
                        }
                    });
                }
                break;
            case TYPE_BUSINESS:
                String businessImage = "";
                NewsListBean.ListBean business = mListData.get(position - 2);
                businessImage = ApiClient.getBuinessDynamicImage(business.getImg());
                ((YaoWenAdapter.BusinessHolder) holder).getBinding().setImageUrl(businessImage);
                ((YaoWenAdapter.BusinessHolder) holder).getBinding().setNewsBean(business);
                break;
            case TYPE_FIND_HELP:
                NewsListBean.ListBean findHelper = mListData.get(position - 2);
                ((YaoWenAdapter.FindHelperHolder) holder).getBinding().setNewsBean(findHelper);
                break;
            case TYPE_KOUBEI:
                NewsListBean.ListBean koubeis = mListData.get(position - 2);
                ((YaoWenAdapter.KoubeiHolder) holder).getBinding().setNewsBean(koubeis);
                if (ApiService.STATUS_SUC.equals(koubeis.getDescription())) {
                    ((KoubeiHolder) holder).getBinding().hongbang.setImageResource(R.mipmap.hongbang);
                } else {
                    ((KoubeiHolder) holder).getBinding().hongbang.setImageResource(R.mipmap.heibang);
                }
                break;
            default:
                NewsListBean.ListBean listBean = mListData.get(position - 2);
                String imageUrl = "";
                if (listBean.getOnlylabel().equals("shanghui")) {
                    imageUrl = ApiClient.getBuinessDynamicImage(listBean.getImg());
                } else {
                    imageUrl = ApiClient.getNewsImage(listBean.getImg());
                }
                Glide.with(((YaoWenAdapter.ItemHolder) holder).mContext).load(imageUrl).error(R.drawable.account_logo)
                        .into(((YaoWenAdapter.ItemHolder) holder).getBinding().imageView5);
                ((YaoWenAdapter.ItemHolder) holder).getBinding().setImageUrl(imageUrl);
                ((YaoWenAdapter.ItemHolder) holder).getBinding().setNewsBean(listBean);
                break;
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

    private class YingyongHolder extends BindingViewHolder<ItemLayoutYingyongBinding> {

        public YingyongHolder(View view) {
            super(ItemLayoutYingyongBinding.bind(view));
        }
    }

    private class NewMemberHolder extends BindingViewHolder<ItemLayoutNewMemberBinding> {

        public NewMemberHolder(View view) {
            super(ItemLayoutNewMemberBinding.bind(view));
        }
    }

    private class BusinessHolder extends BindingViewHolder<ItemLayoutBusinessBinding> {

        public BusinessHolder(View view) {
            super(ItemLayoutBusinessBinding.bind(view));
        }
    }

    private class FindHelperHolder extends BindingViewHolder<ItemLayoutFindHelperBinding> {

        public FindHelperHolder(View view) {
            super(ItemLayoutFindHelperBinding.bind(view));
        }
    }

    private class KoubeiHolder extends BindingViewHolder<ItemLayoutKoubeisBinding> {

        public KoubeiHolder(View view) {
            super(ItemLayoutKoubeisBinding.bind(view));
        }
    }
}

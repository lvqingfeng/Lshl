package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.bean.InvestListBean;
import com.lshl.bean.MemberBasicInfoBean;
import com.lshl.bean.MemberGoodsImageBean;
import com.lshl.databinding.ActivitySellerInfoBinding;
import com.lshl.ui.appliance.adapter.SellerInfoItemAdapter;
import com.lshl.ui.dscs.activity.InvestListActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 卖家信息界面
 */
public class SellerInfoActivity extends BaseActivity<ActivitySellerInfoBinding> {
    private ArrayList<String> mList;
    public static final int FROM_SELLER_INFO = 0x00321;
    public static final int FROM_GUARANTOR_INFO = 0x00322;

    private SellerInfoItemAdapter mItemAdapter;
    private MemberBasicInfoBean.InfoBean mMemberInfoBean;
    private MemberGoodsImageBean mImageBean;
    private List<MemberGoodsImageBean.ListBean> mListImageData;
    private ImageAdapter mOtherImageAdapter;

    private int mFromWhere;


    public static void actionStart(Activity activity, MemberBasicInfoBean bean, int fromWhere) {
        Intent intent = new Intent(activity, SellerInfoActivity.class);
        intent.putExtra("bean", bean);
        intent.putExtra("where", fromWhere);
        activity.startActivity(intent);
    }

    public class Presenter {

        public void onClickOtherImage() {
            SellerOtherGoodsActivity.actionStart(SellerInfoActivity.this, mImageBean);
        }

        public void onClickInvestList() {
            InvestListActivity.actionStart(SellerInfoActivity.this, mMemberInfoBean.getUid());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("商家详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        MemberBasicInfoBean infoBean = (MemberBasicInfoBean) intent.getSerializableExtra("bean");
        mFromWhere = intent.getIntExtra("where", -1);
        mMemberInfoBean = infoBean.getInfo();
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        if (mFromWhere == FROM_SELLER_INFO) {

            mDataBinding.llOtherGoods.setVisibility(View.VISIBLE);
            mOtherImageAdapter = new ImageAdapter();
            loadOtherImage(mMemberInfoBean.getUid());

        } else if (mFromWhere == FROM_GUARANTOR_INFO) {
            mDataBinding.llDscs.setVisibility(View.VISIBLE);
            loadDscsInfo(mMemberInfoBean.getUid());
        }


        mDataBinding.recyclerSellerInfo.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerSellerInfo.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mItemAdapter = new SellerInfoItemAdapter(mContext, mMemberInfoBean);
        mDataBinding.recyclerSellerInfo.setAdapter(mItemAdapter);

        if (TextUtils.isEmpty(mMemberInfoBean.getAvatar()))
            Glide.with(this).load(R.drawable.ease_default_avatar).into(mDataBinding.ivAvatar);
        else {
            Glide.with(this).load(ApiClient.getHxFriendsImage(mMemberInfoBean.getAvatar())).into(mDataBinding.ivAvatar);
        }

        mDataBinding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(ApiClient.getHxFriendsImage(mMemberInfoBean.getAvatar()));
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadDscsInfo(String uid) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getInvestListBean(uid, "1")
                , new Subscriber<HttpResult<InvestListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<InvestListBean> result) {
                        String allMoney = result.getInfo().getAllmoney();
                        if (TextUtils.isEmpty(allMoney))
                            allMoney = "0";
                        allMoney = String.format(getString(R.string.price_s), allMoney);
                        mDataBinding.tvAllMoney.setText(allMoney);
                    }
                }
        );
    }

    private void loadOtherImage(String uid) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .getMemberOtherImage(uid, "1")
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<MemberGoodsImageBean>>() {
                    @Override
                    public void onNext(HttpResult<MemberGoodsImageBean> result) {
                        mImageBean = result.getInfo();
                        mListImageData = result.getInfo().getList();
                        mDataBinding.gvOtherImage.setAdapter(mOtherImageAdapter);
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_info;
    }

    private class ImageAdapter extends BaseAdapter {
        private int mImageSize;

        public ImageAdapter() {
            mImageSize = LshlApplication.sWindowWidth / 7;
        }

        @Override
        public int getCount() {
            return mListImageData.size() < 4 ? mListImageData.size() : 4;
        }

        @Override
        public MemberGoodsImageBean.ListBean getItem(int position) {
            return mListImageData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = new ImageView(mContext);
            ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
            GridLayoutManager.LayoutParams lp = new GridLayoutManager.LayoutParams(mImageSize, mImageSize);
            convertView.setLayoutParams(lp);
            if (TextUtils.isEmpty(getItem(position).getGd_img())) {
                Glide.with(mContext).load(R.drawable.ease_default_image).into((ImageView) convertView);
            } else {
                Glide.with(mContext).load(ApiClient.getGoodsInfoImage(getItem(position).getImg())).into((ImageView) convertView);
            }
            return convertView;
        }
    }

}

package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.SHDynamCommentBean;
import com.lshl.bean.ShanghuiDetailsBean;
import com.lshl.bean.User;
import com.lshl.databinding.ActivityShdynamDetailsicBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.adapter.SHDynamicCommentAdapter;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 商会动态详情
 */
public class SHDynamDetailsicActivity extends BaseActivity<ActivityShdynamDetailsicBinding>
        implements SpringView.OnFreshListener {

    private List<SHDynamCommentBean.ListBean> mListData;
    private String did;
    private String mUrl;
    private boolean isEnd;
    private SendOpinionPopWindow mSendCommentPop;
    private SHDynamicCommentAdapter shDynamicCommentAdapter;
    private ShanghuiDetailsBean.InfoBean infoBean;

    private String imagePath;
    private ShareAction mShareAction;
    private String title = "分享", content = "分享内容",
            url = "http://www.baidu.com";
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

    public static void actionStart(Activity activity, String id) {
        Intent intent = new Intent(activity, SHDynamDetailsicActivity.class);
        intent.putExtra("did", id);
        activity.startActivity(intent);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    public class Presenter {

        public void sendComment() {
            lightOff();
            mSendCommentPop.showPopupWindow(mDataBinding.getRoot());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("详细信息", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);
        mShareAction = new ShareAction(SHDynamDetailsicActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);

    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        setListData(mListData);
        mSendCommentPop = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).sendDynamicComment(LoginHelper.getInstance().getUserToken(), did, inputText)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                showMessage("评论成功");
                                onRefresh();
                            }
                        })
                );
//                updateCommentInfo(inputText);
            }
        });
        mSendCommentPop.setAnonymousGone();
        mSendCommentPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    private void updateCommentInfo(String inputText) {


        SHDynamCommentBean.ListBean bean = new SHDynamCommentBean.ListBean();
        User user = LoginHelper.getInstance().getUserBean();
        bean.setAvatar(user.getAvatar());
        bean.setBc_addtime(String.valueOf(System.currentTimeMillis() / 1000));
        bean.setRealname(user.getRealName());
        bean.setBc_info(inputText);
        bean.setUid(user.getUid());
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        did = getIntent().getStringExtra("did");
        mUrl = ApiService.SHDYNAMICDETAILS + did;
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        shDynamicCommentAdapter = new SHDynamicCommentAdapter(SHDynamDetailsicActivity.this, mUrl, mListData);
        mDataBinding.recyclerView.setAdapter(shDynamicCommentAdapter);
        setListData(mListData);
        initLoadData();
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadShareDetails(did);
                if (infoBean != null) {
                    title = infoBean.getBd_title();
                    content = infoBean.getBd_info();
                    imagePath = ApiClient.getBuninessImages(infoBean.getBd_img());
                    if (!TextUtils.isEmpty(imagePath)) {
                        mShareAction.withTitle(title)
                                .withText(content)
                                .withMedia(new UMImage(mContext, imagePath))
                                .withTargetUrl(ApiService.SHARE_URL + "shanghui/id/" + infoBean.getBd_id())
                                .open();
                    } else {
                        mShareAction.withTitle(title)
                                .withText(content)
                                .withMedia(new UMImage(mContext, R.mipmap.ic_launcher))
                                .withTargetUrl(ApiService.SHARE_URL + "shanghui/id/" + infoBean.getBd_id())
                                .open();
                    }
                }
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = mListData.get(vh.getLayoutPosition()).getBc_uid();
                HxMemberDetailsActivity.actionStart(SHDynamDetailsicActivity.this, "", uid, false);
            }
        });
//        getDynamComment();
    }

    private void loadShareDetails(String mDid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateshanghuiDetails(mDid)
                , new Subscriber<ShanghuiDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShanghuiDetailsBean shanghuiDetailsBean) {
                        infoBean = shanghuiDetailsBean.getInfo();
                    }
                });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getSHDynamicCommentList(did, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<SHDynamCommentBean>>() {
                    @Override
                    public void onNext(HttpResult<SHDynamCommentBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        shDynamicCommentAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                        if (mListData.size() <= 0) {
                            SHDynamCommentBean.ListBean bean = new SHDynamCommentBean.ListBean();
                            bean.setUrl(mUrl);
                            mListData.add(bean);
                            shDynamicCommentAdapter.notifyDataSetChanged();
                        }
                    }
                })
        );
    }

    private void getDynamComment() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getSHDynamicCommentList(did, "1")
                , new ProgressSubscriber<HttpResult<SHDynamCommentBean>>(mContext, new SubscriberOnNextListener<HttpResult<SHDynamCommentBean>>() {
                    @Override
                    public void onNext(HttpResult<SHDynamCommentBean> result) {
                        mListData.addAll(result.getInfo().getList());
                        if (mListData.size() > 0) {
                            /*ShanghuiDynamCommentItemBinding binding = mDataBinding.commentList;
                            Glide
                                    .with(mContext)
                                    .load(ApiClient.getHxFriendsImage(mListData.get(0).getAvatar()))
                                    .transform(new GlideCircleTransform(mContext))
                                    .error(R.drawable.ease_default_avatar)
                                    .into(binding.ivHead);*/
                            final SHDynamCommentBean.ListBean bean = mListData.get(0);

                        } else {
                        }
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shdynam_detailsic;
    }
}

package com.lshl.ui.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.NewsCommentBean;
import com.lshl.bean.NewsInfoBean;
import com.lshl.databinding.ActivityNewsInfoBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.news.adapter.NewsInfoAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 新闻详情界面
 */
public class NewsInfoActivity extends BaseActivity<ActivityNewsInfoBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String mId;
    private SendOpinionPopWindow mPopWinow;
    private String mImagePath;
    private List<NewsCommentBean.ListBean> mListData;
    private NewsInfoBean mNewsInfo;
    private ShareAction mShareAction;
    private String address = "";
    private NewsInfoAdapter newsInfoAdapter;
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

    public static void actionStart(Activity activity, String id, String imagePath) {
        Intent intent = new Intent(activity, NewsInfoActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("image", imagePath);
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
        public void onClickSendOpinion() {
            lightOff();
            mPopWinow.showPopupWindow(mDataBinding.getRoot());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaiduMapLocationHelper.getInstance().init(mContext).locationStart();
        BaiduMapLocationHelper.getInstance().setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                address = location.getCity();
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {
        mId = getIntent().getStringExtra("id");
        mImagePath = getIntent().getStringExtra("image");
        //发表评论的PopupWindow
        mPopWinow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                String anonymous = isAnonymous ? "1" : "0";
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).sendNewsComment(LoginHelper.getInstance().getUserToken()
                                , mId, anonymous, inputText, address)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                Toast.makeText(mContext, "评论成功", Toast.LENGTH_SHORT).show();
                                onRefresh();
                            }
                        })
                );
            }
        });
        mPopWinow.setAnonymousGone();
    }


    @Override
    protected void setupTitle() {
        setTextTitleView("详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        CheckBox rightView = getRightView();
        rightView.setButtonDrawable(R.drawable.ic_vector_share_details);
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAction.withTitle(mNewsInfo.getTitle())
                        .withText(mNewsInfo.getTitle())
                        .withMedia(new UMImage(mContext, mImagePath))
                        .withTargetUrl(ApiService.SHARE_URL + mNewsInfo.getOnlylabel() + "/id/" + mNewsInfo.getId())
                        .open();
            }
        });
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        newsInfoAdapter = new NewsInfoAdapter(mListData, ApiService.NEWS_INFO + mId, NewsInfoActivity.this);
        mDataBinding.recyclerView.setAdapter(newsInfoAdapter);
        setListData(mListData);
        initLoadData();

        initData();
        mDataBinding.setPresenter(new Presenter());
        mPopWinow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mShareAction = new ShareAction(NewsInfoActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE).setCallback(mShareListener);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = mListData.get(vh.getLayoutPosition() - 1).getAm_uid();
                if (TextUtils.isEmpty(uid)) {
                    return;
                }
                HxMemberDetailsActivity.actionStart(NewsInfoActivity.this, "", uid, false);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                String am_id = mListData.get(vh.getLayoutPosition()).getAm_id();
                deleteComment(am_id);
            }
        });
    }

    private void deleteComment(String amid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .deleNewsinfoComment(LoginHelper.getInstance().getUserToken(), amid), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    onRefresh();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).newsCommentList(mId, String.valueOf(page))
                , new Subscriber<HttpResult<NewsCommentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<NewsCommentBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        newsInfoAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                        if (mListData.size() <= 0) {
                            NewsCommentBean.ListBean bean = new NewsCommentBean.ListBean();
                            bean.setAm_uid("0");
                            mListData.add(bean);
                            newsInfoAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    private void initData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getNewsInfo(mId)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<NewsInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<NewsInfoBean> result) {
                        mNewsInfo = result.getInfo();
                        mDataBinding.setNewsInfo(result.getInfo());
                        Log.i("屮艸芔茻", mNewsInfo.getKey() + "======");
                        if (mNewsInfo != null) {
                            if (!TextUtils.isEmpty(mNewsInfo.getKey())) {
                                if (mNewsInfo.getKey().equals("xiangmu")) {
                                    mDataBinding.tvSendOpinion.setEnabled(false);
                                    mDataBinding.tvSendOpinion.setText("暂不支持评论");
                                }
                            }
                        }
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_info;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}

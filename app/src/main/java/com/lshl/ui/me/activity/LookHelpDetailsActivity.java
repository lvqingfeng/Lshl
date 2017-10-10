package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.LookHelpCommentBean;
import com.lshl.bean.LookHelpDetailsBean;
import com.lshl.databinding.ActivityLookHelpDetailsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.adapter.LookHelpCommentAdapter;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.adapter.LookHelpImageAdapter;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class LookHelpDetailsActivity extends BaseActivity<ActivityLookHelpDetailsBinding> {
    private String fid;
    private List<String> mList;
    private ArrayList<String> mImages;
    private ArrayList<String> mBigImages;
    private LookHelpImageAdapter lookHelpImageAdapter;
    private List<LookHelpCommentBean.InfoBean.ListBean> mComments;
    private LookHelpCommentAdapter lookHelpCommentAdapter;
    private String fh_uid;
    private String fhc_id;
    private SendOpinionPopWindow mPopWindow;
    private SendOpinionPopWindow sendOpinionPopWindow;
    private String imagePath;
    private LookHelpDetailsBean.InfoBean infoBean;
    private ShareAction mShareAction;
    private String title = "分享", content = "分享内容是。。。。",
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

    public static void actionStart(Activity activity, String fid) {
        Intent intent = new Intent(activity, LookHelpDetailsActivity.class);
        intent.putExtra("fid", fid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("找帮手详情", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);

    }

    @Override
    protected void initViews() {
        fid = getIntent().getStringExtra("fid");
        mList = new ArrayList<>();
        mComments = new ArrayList<>();
        mImages = new ArrayList<>();
        mBigImages = new ArrayList<>();
        initPopWindow();
        loadBaseData();
        loadLookHelpImage();
        loadLookHelpCommentList();
        loadCommnet();
        int a[] = {R.color.text_red_color, R.color.text_blue, R.color.text_black, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLookHelpCommentList();
                mDataBinding.swipeRefresh.setRefreshing(false);
            }
        });

        mShareAction = new ShareAction(LookHelpDetailsActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = infoBean.getFh_title();
                content = infoBean.getFh_info();
                imagePath = ApiClient.getLookHelpImage(infoBean.getFh_img());
                mShareAction.withTitle(title)
                        .withText(content)
                        .withMedia(new UMImage(mContext, imagePath))
                        .withTargetUrl(ApiService.SHARE_URL + "findhelper/id/" + infoBean.getFh_id())
                        .open();
            }
        });
        mDataBinding.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HxMemberDetailsActivity.actionStart(LookHelpDetailsActivity.this, "", infoBean.getUid(), false);
            }
        });
        mDataBinding.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBigImages.clear();
                mBigImages.add(ApiClient.getHxFriendsImage(infoBean.getAvatar()));
                Intent intent = new Intent(mContext, PhotoShowActivity.class);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mBigImages);
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                startActivity(intent);
            }
        });
    }

    private void initPopWindow() {
        mPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .lookHelpComment(LoginHelper.getInstance().getUserToken(), fid, inputText, fh_uid), new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                            loadLookHelpCommentList();
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mPopWindow.setAnonymousGone();
        sendOpinionPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object
                ).lookHelpAnswer(LoginHelper.getInstance().getUserToken(), fhc_id, inputText), new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                            loadLookHelpCommentList();
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        sendOpinionPopWindow.setAnonymousGone();
        sendOpinionPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    private void loadCommnet() {
        mDataBinding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginHelper.getInstance().getUserBean().getUid().equals(fh_uid)) {
                    showMessage("不能评论自己");
                    return;
                }
                lightOff();
                mComments.clear();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
    }

    private void loadLookHelpCommentList() {
        mComments.clear();
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerViewComment.setLayoutManager(manager);
        mDataBinding.recyclerViewComment.addItemDecoration(new DividerGridItemDecoration(mContext));
        lookHelpCommentAdapter = new LookHelpCommentAdapter(mComments, LookHelpDetailsActivity.this);
        mDataBinding.recyclerViewComment.setAdapter(lookHelpCommentAdapter);
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateLookHelpCommentList(fid, "1"), new Subscriber<LookHelpCommentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LookHelpCommentBean result) {
                if (result.getStatus() == 1) {
                    mComments.addAll(result.getInfo().getList());
                    lookHelpCommentAdapter.notifyDataSetChanged();
                }
            }
        });
        mDataBinding.recyclerViewComment.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerViewComment) {

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (!TextUtils.isEmpty(mComments.get(vh.getLayoutPosition()).getFhc_answer_info())) {
                    showMessage("已回复");
                    return;
                }
                if (!fh_uid.equals(LoginHelper.getInstance().getUserBean().getUid())) {
                    showMessage("不能回复");
                    return;
                }
                fhc_id = mComments.get(vh.getLayoutPosition()).getFhc_id();
                lightOff();
                sendOpinionPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.recyclerViewComment.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerViewComment) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final String fhc_ids = mComments.get(vh.getLayoutPosition()).getFhc_id();
                if (LoginHelper.getInstance().getUserBean().getUid().equals(mComments.get(vh.getLayoutPosition()).getFhc_question_uid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "确定删除评论内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                                    , RetrofitManager.RetrofitType.Object).deleteLookHelpComment(LoginHelper
                                    .getInstance().getUserToken(), fhc_ids), new Subscriber<HttpResult<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        loadLookHelpCommentList();
                                    } else {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } else if (LoginHelper.getInstance().getUserBean().getUid().equals(mComments.get(vh.getLayoutPosition()).getFhc_answer_uid()) &&
                        !TextUtils.isEmpty(mComments.get(vh.getLayoutPosition()).getFhc_answer_info())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "确定删除回复内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .deleteLookHelpAnswer(LoginHelper.getInstance().getUserToken(), fhc_ids), new Subscriber<HttpResult<String>>() {
                                @Override
                                public void onCompleted() {


                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        loadLookHelpCommentList();
                                    } else {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void loadLookHelpImage() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mDataBinding.recyclerViewImage.setLayoutManager(manager);
        lookHelpImageAdapter = new LookHelpImageAdapter(mList);
        mDataBinding.recyclerViewImage.setAdapter(lookHelpImageAdapter);
        mDataBinding.recyclerViewImage.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerViewImage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                for (int i = 0; i < mList.size(); i++) {
                    mImages.add(ApiClient.getLookHelpImage(mList.get(i)));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateLookHelpDetails(fid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<LookHelpDetailsBean>() {
                    @Override
                    public void onNext(LookHelpDetailsBean result) {
                        if (result != null) {
                            if (result.getStatus() == 1) {
                                infoBean = result.getInfo();
                                fh_uid = result.getInfo().getFh_uid();
                                mDataBinding.title.setText(result.getInfo().getFh_title());
                                mDataBinding.info.setText(result.getInfo().getFh_info());
                                Glide.with(mContext).load(ApiClient.getHxFriendsImage(result.getInfo().getAvatar())).error(R.drawable.account_logo).into(mDataBinding.headImage);
                                mDataBinding.name.setText(result.getInfo().getRealname());
                                mDataBinding.address.setText(result.getInfo().getFh_cityname());
                                mDataBinding.time.setText(DateUtils.getDateToString2(Long.decode(result.getInfo().getFh_addtime()) * 1000));
                                if (result.getInfo().getImage() != null) {
                                    if (result.getInfo().getImage().size() > 0) {
                                        mList.addAll(result.getInfo().getImage());
                                        lookHelpImageAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    mDataBinding.recyclerViewImage.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_help_details;
    }

}

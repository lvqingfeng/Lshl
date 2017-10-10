package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.KouBeiCommmentBean;
import com.lshl.bean.KouBeiDetailsBean;
import com.lshl.databinding.ActivityKoubeiDetailsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.adapter.ImageAdapter;
import com.lshl.ui.appliance.adapter.KouBeiCommentAdapter;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class KouBeiDetailsActivity extends BaseActivity<ActivityKoubeiDetailsBinding>
        implements SpringView.OnFreshListener {
    public final static int FROM_PERSON = 0x0000123;
    public final static int FROM_APPLICEN = 0x0000124;
    private List<String> images;
    private ArrayList<String> mList;
    private int fromWhere;
    private String sc_passive_uid;
    private List<KouBeiCommmentBean.InfoBean.ListBean> comments;
    private String s_id;
    private KouBeiCommentAdapter kouBeiCommentAdapter;
    private boolean isEnd;
    private String anonymous;
    private String sc_sid;
    private ImageAdapter imageAdapter;
    private BaiduMapLocationHelper locationHelper;
    private String city;
    private String sc_passive_realname;
    private SendOpinionPopWindow mPopWindow;
    private SendOpinionPopWindow sendOpinionPopWindow;
    private String sc_active_realname;
    private KouBeiDetailsBean.InfoBean bean;

    private String imagePath;
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

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    public class Presenter {

        public void koubeiComment() {
            lightOff();
            mPopWindow.showPopupWindow(mDataBinding.getRoot());
        }

    }

    public static void actionStart(Activity activity, String s_id, int fromWhere) {
        Intent intent = new Intent(activity, KouBeiDetailsActivity.class);
        intent.putExtra("s_id", s_id);
        intent.putExtra("fromWhere", fromWhere);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        locationHelper.locationStart();
        locationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {

            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                city = location.getCity();
            }
        });
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("口碑详情", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);
    }

    /***
     * 评论
     */
    private void doComment(String anonymous, String info, String uid, String realName) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.String).koubeiCommment(
                LoginHelper.getInstance().getUserToken(), s_id, anonymous, city, info, uid, realName), new ProgressSubscriber<>(
                mContext, new SubscriberOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody result) {
                try {
                    String string = result.string();
                    JSONObject object = JSON.parseObject(string);
                    String status = object.getString("status");
                    String message = object.getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        loadCommentData();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }));
    }

    @Override
    protected void initViews() {
        mPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                if (isAnonymous) {
                    anonymous = "1";
                } else {
                    anonymous = "2";
                }
                doComment(anonymous, inputText, "0", sc_active_realname);
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        sendOpinionPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                if (isAnonymous) {
                    anonymous = "1";
                } else {
                    anonymous = "2";
                }
                doComment(anonymous, inputText, sc_passive_uid, sc_active_realname);
            }
        });
        sendOpinionPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mList = new ArrayList<>();
        fromWhere = getIntent().getIntExtra("fromWhere", -1);
//        if (fromWhere == FROM_PERSON) {
//            getRightView().setText("删除");
//            getRightView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DialogUtils.alertDialog(mContext, "删除", "确认删除该信息?", new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismissWithAnimation();
//                        }
//                    }, new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismissWithAnimation();
//                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
//                                    RetrofitManager.RetrofitType.String).deleteKoubei(LoginHelper.getInstance().getUserToken(), s_id),
//                                    new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
//                                        @Override
//                                        public void onNext(ResponseBody result) {
//                                            try {
//                                                String string = result.string();
//                                                JSONObject object = JSON.parseObject(string);
//                                                String status = object.getString("status");
//                                                if (ApiService.STATUS_SUC.equals(status)) {
//                                                    finish();
//                                                }
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }));
//                        }
//                    });
//                }
//            });
//        }
        mDataBinding.setPresenter(new Presenter());
        s_id = getIntent().getStringExtra("s_id");
        images = new ArrayList<>();
        imageAdapter = new ImageAdapter(images);
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mDataBinding.recyclerKoubeiImages.setLayoutManager(manager);
        mDataBinding.recyclerKoubeiImages.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerKoubeiImages.setAdapter(imageAdapter);
        loadImage();
        loadCommentData();
        mDataBinding.recyclerKoubeiImages.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerKoubeiImages) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mList.clear();
                for (int i = 0; i < images.size(); i++) {
                    mList.add(ApiClient.getKouBeiDetailsImage(images.get(i)));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }
        });
        mDataBinding.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HxMemberDetailsActivity.actionStart(KouBeiDetailsActivity.this, "", bean.getUid(), false);
            }
        });

        mShareAction = new ShareAction(KouBeiDetailsActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null) {
                    title = bean.getS_title();
                    content = bean.getS_info();
                    imagePath = ApiClient.getKouBeiDetailsImage(bean.getS_img());
                    mShareAction.withTitle(title)
                            .withText(content)
                            .withMedia(new UMImage(mContext, imagePath))
                            .withTargetUrl(ApiService.SHARE_URL + "koubei/id/" + bean.getS_id())
                            .open();
                }
            }
        });
        int a[] = {R.color.text_red_color, R.color.text_blue, R.color.text_black, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCommentData();
                mDataBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadImage() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).updateKouBeiDetails(s_id),
                new Subscriber<KouBeiDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(KouBeiDetailsBean result) {
                        if (result != null) {
                            bean = result.getInfo();
                            if (bean.getS_type().equals("2")) {
                                mDataBinding.ivHonghei.setImageResource(R.mipmap.heibang);
                            }
                            mDataBinding.tvTitle.setText(bean.getS_title());
                            Constant.getGradeForAll(bean.getGrade(), mDataBinding.tvGrade);
                            mDataBinding.tvContent.setText(bean.getS_info());
                            mDataBinding.tvName.setText(bean.getRealname());
                            mDataBinding.tvAddress.setText(bean.getOrigin_cityname() + "  " + bean.getOrigin_county());
                            String format1 = DateUtils.getDateToString2(Long.decode(bean.getS_addtime()) * 1000);
                            mDataBinding.tvTime.setText(format1);
                            Glide.with(mContext).load(ApiClient.getHxFriendsImage(bean
                                    .getAvatar()))
                                    .error(R.mipmap.account_logo).into(mDataBinding.headImage);
                            if (bean.getS_img() != null) {
                                List<String> list = new ArrayList();
                                String[] split = bean.getS_img().split(",");
                                for (int i = 0; i < split.length; i++) {
                                    list.add(split[i]);
                                }
                                if (list.size() > 0) {
                                    images.addAll(list);
                                    imageAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }

    /***
     * 评论
     **/
    public void loadCommentData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerKoubeiComment.setLayoutManager(manager);
        mDataBinding.recyclerKoubeiComment.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerKoubeiComment.setItemAnimator(new DefaultItemAnimator());
        comments = new ArrayList<>();
        setListData(comments);
        kouBeiCommentAdapter = new KouBeiCommentAdapter(KouBeiDetailsActivity.this, comments);
        mDataBinding.recyclerKoubeiComment.setAdapter(kouBeiCommentAdapter);
        loadListData(1);
        mDataBinding.recyclerKoubeiComment.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerKoubeiComment) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                sc_passive_uid = comments.get(vh.getLayoutPosition()).getSc_active_uid();
                sc_passive_realname = comments.get(vh.getLayoutPosition()).getSc_passive_realname();
                sc_active_realname = comments.get(vh.getLayoutPosition()).getSc_active_realname();
                lightOff();
                sendOpinionPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.recyclerKoubeiComment.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerKoubeiComment) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                sc_sid = comments.get(position).getSc_id();
                if (comments.get(position).getSc_active_uid().equals(LoginHelper.getInstance().getUserBean().getUid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除以上内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            deleteCpmment(sc_sid);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "不能删除", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteCpmment(String sid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                        .deleteComment(LoginHelper.getInstance().getUserToken(), sid),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        try {
                            String string = result.string();
                            JSONObject object = JSON.parseObject(string);
                            String status = object.getString("status");
                            String message = object.getString("info");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                loadCommentData();
                            } else {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }));

    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).updateKouBeicommentList(LoginHelper
                .getInstance().getUserToken(), s_id, page + ""), new Subscriber<KouBeiCommmentBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(KouBeiCommmentBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    comments.addAll(result.getInfo().getList());
                    kouBeiCommentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_koubei_details;
    }

}

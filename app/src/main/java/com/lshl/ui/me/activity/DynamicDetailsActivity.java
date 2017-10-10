package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.DianzanListBean;
import com.lshl.bean.DynamicBean;
import com.lshl.bean.DynamicCommentBean;
import com.lshl.databinding.ActivityDynamicDetailsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.adapter.DynamicCommentAdapter;
import com.lshl.ui.me.adapter.DynamicImageAdapter;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.SendOpinionPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 圈子详情页面
 */
public class DynamicDetailsActivity extends BaseActivity<ActivityDynamicDetailsBinding> {
    private boolean isEnd;
    private boolean type = false;
    private List<String> mList;
    private ArrayList<String> mImages;
    private DynamicImageAdapter imageAdapter;
    private String md_id;
    private List<DynamicCommentBean.InfoBean.ListBean> comments;
    private DynamicCommentAdapter dynamicCommentAdapter;
    private DynamicBean.InfoBean.ListBean bean;
    public static final int DynamicDetails = 0x0000222;
    private SendOpinionPopWindow mPopWindow;
    private SendOpinionPopWindow sendOpinionPopWindow;
    private String name;
    private String md_uid;

    public static void actionStart(Activity activity, DynamicBean.InfoBean.ListBean bean, int resultCode) {
        Intent intent = new Intent(activity, DynamicDetailsActivity.class);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

        mPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                doComment(inputText, null);
            }
        });
        mPopWindow.setAnonymousGone();
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        sendOpinionPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                doComment(inputText, name);
            }
        });
        sendOpinionPopWindow.setAnonymousGone();
        sendOpinionPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        bean = (DynamicBean.InfoBean.ListBean) getIntent().getSerializableExtra("bean");
        md_id = bean.getMd_id();
        if (bean.getMd_uid().equals(LoginHelper.getInstance().getUserBean().getUid())) {
            getRightView().setButtonDrawable(R.drawable.ic_vector_delete);
        }
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此内容", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                        .deletDynamicDetails(LoginHelper.getInstance().getUserToken(), md_id)
                                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }));
                    }
                });
            }
        });
        mImages = new ArrayList<>();
        mDataBinding.videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bean.getMb_dynamic_voide())) {
                    Uri uri = Uri.parse(ApiService.DYNAMIC_VIDEO + bean.getMb_dynamic_voide());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);
                }
            }
        });
        loadImage();
        loadDianZanList(md_id);
        initBaseData();
        int a[] = {R.color.text_red_color, R.color.text_blue, R.color.text_black, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadComment();
                mDataBinding.swipeRefresh.setRefreshing(false);
            }
        });
        mDataBinding.dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type) {//点赞状态,点击取消
                    loadDianzan(md_id, "2");
                    loadDianZanList(md_id);
                    mDataBinding.dianzan.setImageResource(R.drawable.ic_vector_dianzan);
                    type = false;
                } else {//点赞
                    loadDianzan(md_id, "1");
                    loadDianZanList(md_id);
                    mDataBinding.dianzan.setImageResource(R.drawable.ic_vector_dianzan_already);
                    type = true;
                }
            }
        });

        loadComment();
        mDataBinding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.recyclerComment.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerComment) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                name = comments.get(vh.getLayoutPosition()).getCo_active_member();
                lightOff();
                sendOpinionPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                for (int i = 0; i < mList.size(); i++) {
                    mImages.add(ApiClient.getDynamicImage(mList.get(i)));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }

        });
        mDataBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HxMemberDetailsActivity.actionStart(DynamicDetailsActivity.this, "", md_uid, false);
            }
        });
    }

    private void doComment(String info, String name) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String).dynamicComment(LoginHelper.getInstance().getUserToken(),
                md_id, name, info),
                new ProgressSubscriber<>(mContext,
                        new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                try {
                                    String string = result.string();
                                    JSONObject object = JSON.parseObject(string);
                                    String status = object.getString("status");
                                    String message = object.getString("info");
                                    if (ApiService.STATUS_SUC.equals(status)) {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                        loadComment();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }));
    }

    private void initBaseData() {
        Glide.with(mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).into(mDataBinding.imageView);
        mDataBinding.name.setText(bean.getRealname());
        mDataBinding.tvAddress.setText(bean.getMb_cityname());
        mDataBinding.tvTime.setText(DateUtils.getDateToString2(Long.decode(bean.getMb_addtime()) * 1000));
        Constant.getGradeForAll(bean.getGrade(), mDataBinding.grade);
        mDataBinding.tvInfo.setText(bean.getMb_dynamic_info());
        md_uid = bean.getMd_uid();
        if (Integer.parseInt(bean.getMb_comment_nums()) > 0) {
            mDataBinding.commentNum.setText(bean.getMb_comment_nums());
        }
        if (Integer.parseInt(bean.getMb_thing_nums()) > 0) {
            mDataBinding.zanNum.setText(bean.getMb_thing_nums());
        }
        if (!TextUtils.isEmpty(bean.getMb_dynamic_voide())) {
            mDataBinding.videoLayout.setVisibility(View.VISIBLE);
            mDataBinding.videoImage.setImageBitmap(getVideoBitmap(ApiService.DYNAMIC_VIDEO + bean.getMb_dynamic_voide()));
        }
        if (!TextUtils.isEmpty(bean.getMb_dynamic_img())) {
            String[] split = bean.getMb_dynamic_img().split(",");
            for (int i = 0; i < split.length; i++) {
                mList.add(split[i]);
            }
            imageAdapter.notifyDataSetChanged();
        }
        if ("1".equals(bean.getMb_thingstatus())) {
            mDataBinding.dianzan.setImageResource(R.drawable.ic_vector_dianzan_already);
            type = true;
        } else {
            type = false;
        }
    }

    private void loadDianZanList(String mdid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object)
                .dianzanList(LoginHelper.getInstance().getUserToken(), mdid), new Subscriber<DianzanListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DianzanListBean result) {
                if (result != null) {
                    String ss = "";
                    List<DianzanListBean.InfoBean> info = result.getInfo();
                    if (info.size() > 0) {
                        mDataBinding.dianzanList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < info.size(); i++) {
                            ss = ss + info.get(i).getRealname() + "  ";
                        }
                        mDataBinding.dianzanList.setText(ss);
                    }
                }
            }
        });
    }

    public static Bitmap getVideoBitmap(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url, new HashMap<String, String>());
        Bitmap bitmap = retriever.getFrameAtTime();
        return bitmap;
    }

    private void loadComment() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerComment.setLayoutManager(manager);
        mDataBinding.recyclerComment.addItemDecoration(new DividerGridItemDecoration(mContext));
        comments = new ArrayList<>();
        setListData(comments);
        dynamicCommentAdapter = new DynamicCommentAdapter(comments, DynamicDetailsActivity.this);
        mDataBinding.recyclerComment.setAdapter(dynamicCommentAdapter);
        loadListData(1);
        mDataBinding.recyclerComment.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerComment) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final int position = vh.getLayoutPosition();
                if (LoginHelper.getInstance().getUserBean().getRealName().
                        equals(comments.get(vh.getLayoutPosition()).getActive_realname()) ||
                        LoginHelper.getInstance().getUserBean().getRealName().
                                equals(comments.get(vh.getLayoutPosition()).
                                        getPassive_realname())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "你是否要删除此条评论", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                                    RetrofitManager.RetrofitType.Object).deleteDynamicComment(LoginHelper.getInstance().getUserToken()
                                    , comments.get(position).getCo_id()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        loadComment();
                                    }
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }));
                        }
                    });
                } else {
                    showMessage("不能删除");
                }
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).dynamicCommentList(LoginHelper.getInstance().getUserToken(), md_id, String.valueOf(page)), new Subscriber<DynamicCommentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DynamicCommentBean result) {
                if (result != null) {
                    comments.addAll(result.getInfo().getList());
                    dynamicCommentAdapter.notifyDataSetChanged();
                    if (comments.size() > 0) {
                        mDataBinding.recyclerComment.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerComment.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        });
    }

    public void loadDianzan(String mdid, String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .dynamicDianzan(LoginHelper.getInstance().
                        getUserToken(), mdid, status), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void loadImage() {
        mList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(manager);
        imageAdapter = new DynamicImageAdapter(mList);
        mDataBinding.recyclerView.setAdapter(imageAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_details;
    }

}

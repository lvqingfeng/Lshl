package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
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
import com.lshl.bean.ProjectDetailsBean;
import com.lshl.bean.ProjectQuestionBean;
import com.lshl.bean.ResultInfoBean;
import com.lshl.databinding.ProjectDetails;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.adapter.ProJectImageAdapter;
import com.lshl.ui.appliance.adapter.ProjectQuestionAdapter;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.EditProjectActivity;
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

public class ProjectDetailActivity extends BaseActivity<ProjectDetails> {
    private ArrayList<String> images;
    private ArrayList<String> mImages;
    private String id;
    private List<ProjectQuestionBean.InfoBean.ListBean> mList;
    private ProjectQuestionAdapter projectQuestionAdapter;
    private ProJectImageAdapter imageAdapter;
    private String realname;
    private String pc_answer_uid;
    private String pp_id;
    private String pp_uid;
    private String info;
    private String address;
    private int FromWhere;
    private String pc_id;
    public final static int FROM_PERSON = 0x0000123;//从个人进入
    public final static int FROM_APPLICEN = 0x0000124;//从应用中进入
    private ProjectDetailsBean.InfoBean bean;
    private SendOpinionPopWindow mPopWindow;
    private SendOpinionPopWindow sendOpinionPopWindow;
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

    public static void actionStart(Activity activity, String id, int FromWhere) {
        Intent intent = new Intent(activity, ProjectDetailActivity.class);
        intent.putExtra("pp_id", id);
        intent.putExtra("FromWhere", FromWhere);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public  class Presenter{
//        public void question(){
//            ProjectQuestionActivity.actionStart(ProjectDetailActivity.this,realname,pp_id,pp_uid);
//        }
//    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("项目详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mImages = new ArrayList<>();
        mShareAction = new ShareAction(ProjectDetailActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);
//        mDataBinding.setPresenter(new Presenter());
        id = getIntent().getStringExtra("pp_id");
        FromWhere = getIntent().getIntExtra("FromWhere", -1);
        images = new ArrayList<>();
        loadDataImage();
        loadDetail();
        initQuestion();
        if (FromWhere == FROM_PERSON) {
            getRightView().setText("编辑");
            getRightView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditProjectActivity.actionStart(ProjectDetailActivity.this, bean, EditProjectActivity.EDIT_MYPROJECT);
                }
            });
        }
        if (FromWhere == FROM_APPLICEN) {
            getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);
            getRightView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    title = bean.getPp_title();
                    content = bean.getPp_info();
                    imagePath = ApiClient.getProjectImage(bean.getPp_imgs());
                    mShareAction.withTitle(title)
                            .withText(content)
                            .withMedia(new UMImage(mContext, imagePath))
                            .withTargetUrl(ApiService.SHARE_URL + "xiangmu/id/" + bean.getPp_id())
                            .open();
                }
            });
        }
        mDataBinding.zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().getUserBean().getUid().equals(pp_uid)) {
                    lightOff();
                    mList.clear();
                    mPopWindow.showPopupWindow(mDataBinding.getRoot());
                } else {
                    showMessage("不能咨询自己");
                }
            }
        });
        int a[] = {R.color.text_black, R.color.text_red_color, R.color.text_blue, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                loadData(1, id);
                mDataBinding.swipeRefresh.setRefreshing(false);
            }
        });
        initPopWindow();


    }

    private void initPopWindow() {
        mPopWindow = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.Object).projectQuestion(LoginHelper.getInstance().getUserToken(),
                        id, inputText, pp_uid),
                        new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                            @Override
                            public void onNext(ResultInfoBean result) {
                                if (result != null) {
                                    if (result.getStatus() == 1) {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        loadData(1, id);
                                    }
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
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
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .projectAnswer(LoginHelper.getInstance().getUserToken(), pc_id,
                                inputText), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                    @Override
                    public void onNext(ResultInfoBean result) {
                        if (result != null) {
                            if (result.getStatus() == 1) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                loadData(1, id);
                            } else {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }));
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

    private void initQuestion() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        projectQuestionAdapter = new ProjectQuestionAdapter(mList);
        mDataBinding.recyclerView.setAdapter(projectQuestionAdapter);
        loadData(1, id);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                pc_id = mList.get(vh.getLayoutPosition()).getPc_id();
                pc_answer_uid = mList.get(vh.getLayoutPosition()).getPc_answer_uid();
                String infos = mList.get(vh.getLayoutPosition()).getPc_answer_info();
                if (LoginHelper.getInstance().getUserBean().getUid().equals(pc_answer_uid)) {
                    if (TextUtils.isEmpty(infos)) {
                        lightOff();
                        mList.clear();
                        sendOpinionPopWindow.showPopupWindow(mDataBinding.getRoot());
                    } else {
                        showMessage("已回复");
                    }
                } else {
                    showMessage("不能回复");
                }
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                if (LoginHelper.getInstance().getUserBean().getUid().equals(mList.get(vh.getLayoutPosition()).getPc_question_uid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "确定删除咨询内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).projectQuectionDelete(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getPc_id()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                        mList.clear();
                                        loadData(1, id);
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }));
                        }
                    });
                } else if (LoginHelper.getInstance().getUserBean().getUid().equals(mList.get(vh.getLayoutPosition()).getPc_answer_uid())) {
                    if (!TextUtils.isEmpty(mList.get(vh.getLayoutPosition()).getPc_answer_info())) {
                        DialogUtils.alertDialog(mContext, "温馨提示", "确定删除回复内容?", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).projectAnswerDelete(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getPc_id()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                            mList.clear();
                                            loadData(1, id);
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }));
                            }
                        });
                    }
                }
            }
        });
    }

    private void loadData(int page, String id) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updateProjectQuestionList(id, page + "'"), new Subscriber<ProjectQuestionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProjectQuestionBean result) {
                if (result != null) {
                    mList.addAll(result.getInfo().getList());
                    projectQuestionAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void loadDetail() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateProjectDetails(id), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ProjectDetailsBean>() {
            @Override
            public void onNext(ProjectDetailsBean result) {
                if (result != null) {
                    images.clear();
                    bean = result.getInfo().get(0);
                    Glide.with(mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar()))
                            .into(mDataBinding.ivAvatar);
                    mDataBinding.tvTitle.setText("发起人:" + bean.getRealname() + "\n" + bean.getPp_title());
                    mDataBinding.tvAddress.setText("地址:" + bean.getPp_address() + "\n时间:" +
                            DateUtils.getDateToString2(Long.decode(bean.getPp_addtime()) * 1000));
                    mDataBinding.tvDescribe.setText(bean.getPp_info());
                    realname = bean.getRealname();
                    pp_id = bean.getPp_id();
                    pp_uid = bean.getPp_uid();
                    title = bean.getPp_title();
                    address = bean.getPp_address();
                    ProjectDetailActivity.this.info = bean.getPp_info();
                    String[] split = bean.getPp_imgs().split(",");
                    if (split.length > 0) {
                        for (int i = 0; i < split.length; i++) {
                            images.add(ApiClient.getProjectImage(split[i]));
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                }
            }
        }));
        mDataBinding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = bean.getUid();
                HxMemberDetailsActivity.actionStart(ProjectDetailActivity.this, "", uid, false);
            }
        });
    }

    private void loadDataImage() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        imageAdapter = new ProJectImageAdapter(images);
        mDataBinding.recyclerImage.setLayoutManager(manager);
        mDataBinding.recyclerImage.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerImage.setAdapter(imageAdapter);
        mDataBinding.recyclerImage.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerImage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                for (int i = 0; i < images.size(); i++) {
                    mImages.add(images.get(i));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditProjectActivity.EDIT_MYPROJECT) {
            if (resultCode == RESULT_OK) {
                loadDetail();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.priject_details;
    }

}

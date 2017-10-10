package com.lshl.ui.info.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.bean.IconBean;
import com.lshl.bean.ImageWallListBean;
import com.lshl.bean.MemberBasicInfoBean;
import com.lshl.bean.PersonBasedataBean;
import com.lshl.bean.PraiseBean;
import com.lshl.databinding.ActivityHxMemberDetailsBinding;
import com.lshl.db.HxUserDao;
import com.lshl.db.bean.HxUserBean;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.business.activity.ShangHuiDetailsActivity;
import com.lshl.ui.info.adapter.BusinessPostAdapter;
import com.lshl.ui.info.adapter.InfoIndustryAdapter;
import com.lshl.ui.info.adapter.PersonGoodsAdapter;
import com.lshl.ui.info.adapter.PersonQiyeAdapter;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.ui.info.chat.ChatSettingActivity;
import com.lshl.ui.me.activity.EnterPriseDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.adapter.ImageWallAdapter;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ObjectCloneUtils;
import com.lshl.widget.SwitchView;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.functions.Action1;

public class HxMemberDetailsActivity extends BaseActivity<ActivityHxMemberDetailsBinding> {
    private PersonBasedataBean.InfoBean infoBean;
    private String mHxId;
    private String mUid;
    private List<String> mImageList;
    private HxUserBean mHxUserBean;
    private HxUserDao mHxUserDao;
    private boolean isFriend;
    private boolean isAgreen;
    private List<PersonBasedataBean.InfoBean.BusinessInfoBean> mBusinessList;
    private List<PersonBasedataBean.InfoBean.EnterpriseListBean> mEnterpriseList;
    private List<PersonBasedataBean.InfoBean.GoodListBean> mGoodList;
    private List<PersonBasedataBean.InfoBean.IndustryBean> mIndustryList;
    private boolean isClickPraise;
    private boolean isOwn;
    private ImageAdapter mImageAdapter;
    private static final int REQUEST_CODES = 0x000124;
    private BusinessPostAdapter businessPostAdapter;
    private PersonQiyeAdapter personQiyeAdapter;
    private PersonGoodsAdapter personGoodsAdapter;

    private ArrayList<String> mImages;
    private List<String> mList;
    private ImageWallAdapter imageWallAdapter;
    private InfoIndustryAdapter infoIndustryAdapter;
    private List<String> mContactList;

    public static void actionStart(Activity activity, String hxId, String uid, boolean isAgreen) {
        Intent intent = new Intent(activity, HxMemberDetailsActivity.class);
        intent.putExtra("hxId", hxId);
        intent.putExtra("uid", uid);
        intent.putExtra("isAgreen", isAgreen);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickPraise() {
            if (isOwn) {
                showMessage("无法为自己点赞");
            }
            if (isClickPraise || isOwn)
                return;
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).clickPraise(LoginHelper.getInstance().getUserToken(), infoBean.getUid())
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            mDataBinding.ivClickPraise.setImageResource(R.drawable.ic_vector_new_yidianzan);
                            isClickPraise = true;
                        }
                    })
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
    }


    private void checkIsFriend(String hxId) {
        List<HxUserBean> userList = mHxUserDao.getUserDetailsList();
        new Thread() {
            @Override
            public void run() {
                try {
                    mContactList = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(500);
            if (mContactList != null && mContactList.size() > 0) {
                for (String mHxId : mContactList) {
                    //将本地存储的环信id与传递过来的环信id进行匹配，判断是否是好友
                    if (mHxId.equals(hxId)) {
                        isFriend = true;
                        return;
                    }
                }

                if (!isFriend) {
                    mDataBinding.llDynamic.setVisibility(View.GONE);
                    if (isOwn) {
                        mDataBinding.callTo.setVisibility(View.VISIBLE);
                        mDataBinding.rememberSv.setVisibility(View.GONE);
                    } else {
                        mDataBinding.callTo.setVisibility(View.GONE);
                    }
                    if (isAgreen) {
                        mDataBinding.btnAddAgreen.setVisibility(View.VISIBLE);
                        mDataBinding.btnSendMessage.setVisibility(View.GONE);
                        mDataBinding.btnAddFriend.setVisibility(View.GONE);
                        mDataBinding.viewWhite.setVisibility(View.GONE);
                    } else {
                        mDataBinding.btnAddAgreen.setVisibility(View.GONE);
                        mDataBinding.btnSendMessage.setVisibility(View.VISIBLE);
                        mDataBinding.btnAddFriend.setVisibility(View.VISIBLE);
                        mDataBinding.viewWhite.setVisibility(View.VISIBLE);
                    }
                } else {
                    mDataBinding.btnSendMessage.setVisibility(View.VISIBLE);
                    mDataBinding.btnAddFriend.setVisibility(View.GONE);
                    mDataBinding.btnAddAgreen.setVisibility(View.GONE);
                    mDataBinding.viewWhite.setVisibility(View.GONE);
                    if (infoBean.getHide_account().equals("1")) {
                        mDataBinding.rememberSv.setOpened(false);
                    } else {
                        mDataBinding.phone.setText("*****");
                    }
                }
            } else {
                if (isAgreen) {
                    mDataBinding.btnSendMessage.setVisibility(View.GONE);
                    mDataBinding.btnAddAgreen.setVisibility(View.VISIBLE);
                } else {
                    mDataBinding.btnSendMessage.setVisibility(View.VISIBLE);
                    mDataBinding.btnAddAgreen.setVisibility(View.GONE);
                    mDataBinding.btnAddFriend.setVisibility(View.VISIBLE);
                    mDataBinding.viewWhite.setVisibility(View.VISIBLE);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mBusinessList = new ArrayList<>();
        mEnterpriseList = new ArrayList<>();
        mGoodList = new ArrayList<>();
        mList = new ArrayList<>();
        mImages = new ArrayList<>();
        mIndustryList = new ArrayList<>();
        final Intent intent = getIntent();
        mHxId = intent.getStringExtra("hxId");
        mUid = intent.getStringExtra("uid");
        isAgreen = intent.getBooleanExtra("isAgreen", false);
//        if (isAgreen){
//            if (!isFriend){
//                mDataBinding.callTo.setVisibility(View.GONE);
//                mDataBinding.btnAddFriend.setVisibility(View.GONE);
//                mDataBinding.btnSendMessage.setVisibility(View.GONE);
//                mDataBinding.btnAddAgreen.setVisibility(View.VISIBLE);
//            }else {
//                mDataBinding.btnAddFriend.setVisibility(View.GONE);
//                mDataBinding.btnSendMessage.setVisibility(View.VISIBLE);
//                mDataBinding.btnAddAgreen.setVisibility(View.GONE);
//            }
//        }
        if (isFriend && !isOwn) {
            getRightView().setText("更多");
            getRightView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatSettingActivity.actionStart(HxMemberDetailsActivity.this, infoBean.getHx_id(), ChatActivity.REQUEST_CODE_CONTACT_SETTING);
                }
            });
        }
        /****
         * 是否隐藏手机号码
         * */
        mDataBinding.rememberSv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                mDataBinding.rememberSv.toggleSwitch(true);
                hidePhone("2");
            }

            @Override
            public void toggleToOff(View view) {
                mDataBinding.rememberSv.toggleSwitch(false);
                hidePhone("1");
            }
        });
        mImageList = new ArrayList<>();
        mHxUserDao = new HxUserDao();
        initRecyclerView();
        mImageAdapter = new ImageAdapter();
        mDataBinding.gridView.setAdapter(mImageAdapter);

        if (!TextUtils.isEmpty(mHxId)) {
            loadUidBaseData("", mHxId);
            loadIcon("", mHxId);
//            loadHxUserBean();
        } else if (!TextUtils.isEmpty(mUid)) {
            loadUidBaseData(mUid, "");
            loadIcon(mUid, "");
//            loadUidUserBean();
        }
        mDataBinding.llDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsDynamicActivity.actionStart(HxMemberDetailsActivity.this, infoBean.getRealname(), infoBean.getUid());
            }
        });
        mDataBinding.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsDynamicActivity.actionStart(HxMemberDetailsActivity.this, infoBean.getRealname(), infoBean.getUid());
            }
        });
        mDataBinding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendsDynamicActivity.actionStart(HxMemberDetailsActivity.this, infoBean.getRealname(), infoBean.getUid());
            }
        });
        mDataBinding.callTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNum = mDataBinding.phone.getText().toString();
                RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    DialogUtils.alertDialog(mContext, "温馨提示", "您是否要拨打" + phoneNum + "号码",
                                            new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            }, new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    //用intent启动拨打电话
                                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                                                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        // TODO: Consider calling
                                                        //    ActivityCompat#requestPermissions
                                                        // here to request the missing permissions, and then overriding
                                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                        //                                          int[] grantResults)
                                                        // to handle the case where the user grants the permission. See the documentation
                                                        // for ActivityCompat#requestPermissions for more details.
                                                        return;
                                                    }
                                                    startActivity(intent);
                                                }
                                            });
                                } else {
                                    showMessage("拨号权限被禁用");
                                }
                            }
                        });
            }
        });
        mDataBinding.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // demo中直接进入聊天页面，实际一般是进入用户详情页
                ChatActivity.actionStart(HxMemberDetailsActivity.this, null, infoBean.getHx_id(), EaseConstant.CHATTYPE_SINGLE, 0);
            }
        });
        mDataBinding.btnAddAgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(infoBean.getHx_id() + "");
                    finish();

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        mDataBinding.btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendActivity.actionStart(HxMemberDetailsActivity.this, infoBean.getHx_id(), REQUEST_CODES);
//                new Thread() {
//                    @Override
//                    public void run() {
//                        //参数为要添加的好友的username和添加理由
//                        try {
//                            EMClient.getInstance().contactManager().addContact(mHxUserBean.getHx_id(), "求加好友");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    showMessage("验证信息发送完成");
//                                }
//                            });
//                        } catch (final HyphenateException e) {
//                            e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    showMessage("操作异常：" + e.getMessage());
//                                }
//                            });
//                        }
//
//                    }
//                }.start();
            }
        });
    }

    private void hidePhone(String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updatehidePhone(LoginHelper.getInstance().getUserToken(), status), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals("1")) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    mDataBinding.rememberSv.setOpened(false);
                }
            }
        }));
    }

    private void loadImageWall(String uid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateImageWallList(uid), new Subscriber<ImageWallListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ImageWallListBean result) {
                if (result.getStatus() == 1) {
                    if (result.getInfo().size() > 0) {
                        for (int i = 0; i < result.getInfo().size(); i++) {
                            String s = ApiClient.getImageWallImage(result.getInfo().get(i));
                            mList.add(s);
                        }
                    } else {
                        mList.add(ApiClient.getHxFriendsImage(infoBean.getAvatar()));
                    }
                    imageWallAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initRecyclerView() {
        //照片墙的列表
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerImageWall.setLayoutManager(layoutManager);
        mDataBinding.recyclerImageWall.addItemDecoration(new DividerGridItemDecoration(mContext));
        imageWallAdapter = new ImageWallAdapter(mList);
        mDataBinding.recyclerImageWall.setAdapter(imageWallAdapter);
        mDataBinding.recyclerImageWall.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerImageWall) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                if (infoBean.getImage_wall().size() > 0) {
                    for (int i = 0; i < infoBean.getImage_wall().size(); i++) {
                        String image = ApiClient.getImageWallImage(infoBean.getImage_wall().get(i));
                        mImages.add(image);
                    }
                } else {
                    mImages.add(ApiClient.getHxFriendsImage(infoBean.getAvatar()));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.setClass(mContext, PhotoShowActivity.class);
                startActivity(intent);
            }
        });

        //个人商会职务列表
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerViewBusiness.setLayoutManager(manager);
        mDataBinding.recyclerViewBusiness.addItemDecoration(new DividerGridItemDecoration(mContext));
        businessPostAdapter = new BusinessPostAdapter(mBusinessList);
        mDataBinding.recyclerViewBusiness.setAdapter(businessPostAdapter);
        mDataBinding.recyclerViewBusiness.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerViewBusiness) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                PersonBasedataBean.InfoBean.BusinessInfoBean bean = mBusinessList.get(vh.getLayoutPosition());
                ShangHuiDetailsActivity.actionStart(HxMemberDetailsActivity.this, null, bean.getBusiness_id(), bean.getBusiness_name(), true, 200);
            }
        });
        //个人企业列表
        LinearLayoutManager managers = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerViewQiye.setLayoutManager(managers);
        mDataBinding.recyclerViewQiye.addItemDecoration(new DividerGridItemDecoration(mContext));
        personQiyeAdapter = new PersonQiyeAdapter(mEnterpriseList);
        mDataBinding.recyclerViewQiye.setAdapter(personQiyeAdapter);
        mDataBinding.recyclerViewQiye.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerViewQiye) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                EnterPriseDetailsActivity.actionStart(HxMemberDetailsActivity.this, mEnterpriseList.get(vh.getLayoutPosition()).getEn_id());
            }
        });
        //个人商品的列表
        LinearLayoutManager managerss = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerViewGoods.setLayoutManager(managerss);
        mDataBinding.recyclerViewGoods.addItemDecoration(new DividerGridItemDecoration(mContext));
        personGoodsAdapter = new PersonGoodsAdapter(mGoodList);
        mDataBinding.recyclerViewGoods.setAdapter(personGoodsAdapter);
        mDataBinding.recyclerViewGoods.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerViewGoods) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(HxMemberDetailsActivity.this, mGoodList.get(vh.getLayoutPosition()).getGd_id(), GoodsDetailsActivity.FROM_OTHER);
            }
        });

        //行业分类的列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        mDataBinding.recyclerViewIndustry.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerViewIndustry.addItemDecoration(new DividerGridItemDecoration(mContext));
        infoIndustryAdapter = new InfoIndustryAdapter(mIndustryList);
        mDataBinding.recyclerViewIndustry.setAdapter(infoIndustryAdapter);

    }

    private void loadIcon(String uid, String hxid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateIcon(uid, hxid), new Subscriber<IconBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IconBean iconBean) {
                updateIcon(iconBean.getInfo());
            }
        });
    }

    private void loadUidBaseData(String uid, String hxid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updatePersonBaseData(LoginHelper.getInstance().getUserToken()
                        , uid, hxid), new ProgressSubscriber<>(mContext
                , new SubscriberOnNextListener<PersonBasedataBean>() {
            @Override
            public void onNext(PersonBasedataBean result) {
                infoBean = result.getInfo();
                updateBaseData(infoBean);
                lookDetails();
            }
        }));
    }

    /****
     * 查看详情
     */
    private void lookDetails() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .lookMeDetails(LoginHelper.getInstance().getUserToken(), mUid), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {
                if (result.getStatus().equals("1")) {
                }
            }
        });
    }

    private void updateIcon(IconBean.InfoBean mInfo) {
        if (mInfo.getAuthenticate() == 1) {
            mDataBinding.yihsiming.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.weishiming.setVisibility(View.VISIBLE);
        }
        if (mInfo.getPresident() == 1) {
            mDataBinding.president.setVisibility(View.VISIBLE);
        }
        if (mInfo.getCharityfund() == 1) {
            mDataBinding.juankuan.setVisibility(View.VISIBLE);
        }
        if (mInfo.getDues() == 1) {
            mDataBinding.member.setVisibility(View.VISIBLE);
        }
    }

    private void updateBaseData(PersonBasedataBean.InfoBean infoBean) {
        if (LoginHelper.getInstance().getUserBean().getUid().equals(infoBean.getUid())) {
            isOwn = true;
            isFriend = true;
            mDataBinding.btnAddFriend.setVisibility(View.GONE);
            mDataBinding.btnSendMessage.setVisibility(View.GONE);
            mDataBinding.callTo.setVisibility(View.VISIBLE);
            mDataBinding.rememberSv.setVisibility(View.GONE);
        } else if (LoginHelper.getInstance().getUserBean().getHxid().equals(infoBean.getHx_id())) {
            isOwn = true;
            isFriend = true;
            mDataBinding.btnAddFriend.setVisibility(View.GONE);
            mDataBinding.btnSendMessage.setVisibility(View.GONE);
            mDataBinding.callTo.setVisibility(View.VISIBLE);
            mDataBinding.rememberSv.setVisibility(View.GONE);
        }
        checkIsFriend(infoBean.getHx_id());
        getIsClickPraise();
        loadImageWall(infoBean.getUid());

        if (infoBean.getDynamic().size() > 0) {
            mImageList.addAll(infoBean.getDynamic());
            if (mImageList.size() <= 0) {
                mDataBinding.tvDynamic.setVisibility(View.VISIBLE);
                mDataBinding.gridView.setVisibility(View.GONE);
            } else
                mImageAdapter.notifyDataSetChanged();
        }
        mDataBinding.ljCounty.setText(infoBean.getLj_county());
        mDataBinding.xjCounty.setText(infoBean.getXj_county());

        if ("1".equals(infoBean.getSex())) {
            mDataBinding.sexs.setImageResource(R.drawable.ic_vector_mans);
            mDataBinding.sexAge.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sexMan));
        } else {
            mDataBinding.sexs.setImageResource(R.drawable.ic_vector_womans);
            mDataBinding.sexAge.setBackgroundColor(ContextCompat.getColor(mContext, R.color.sexwoMan));
        }
        setTextTitleView(infoBean.getRealname(), DEFAULT_TITLE_TEXT_COLOR);
        mDataBinding.ages.setText(DateUtils.getAgeBirthDate(infoBean.getAge()));
        if (infoBean.getHide_account().equals("1")) {
            mDataBinding.phone.setText(infoBean.getPhone());
            mDataBinding.rememberSv.setOpened(false);
        } else {
            mDataBinding.phone.setText("*****");
            mDataBinding.rememberSv.setOpened(true);
        }

        if (infoBean.getBusiness_info().size() > 0) {
            mBusinessList.addAll(infoBean.getBusiness_info());
            businessPostAdapter.notifyDataSetChanged();
        } else {
            mDataBinding.itemBusiness.setVisibility(View.GONE);
        }
        if (infoBean.getEnterprise_list().size() > 0) {
            mEnterpriseList.addAll(infoBean.getEnterprise_list());
            personQiyeAdapter.notifyDataSetChanged();
        } else {
            mDataBinding.itemQiye.setVisibility(View.GONE);
        }
        if (infoBean.getGood_list().size() > 0) {
            mGoodList.addAll(infoBean.getGood_list());
            personGoodsAdapter.notifyDataSetChanged();
        } else {
            mDataBinding.itemGoods.setVisibility(View.GONE);
        }
        if (infoBean.getIndustry().size() > 0) {
            mIndustryList.addAll(infoBean.getIndustry());
            infoIndustryAdapter.notifyDataSetChanged();
        } else {
            mDataBinding.itemIndustry.setVisibility(View.GONE);
        }
    }

    private void loadUidUserBean() {
        TaskBase.getMemberBasicInfo(mUid, new TaskBase.OnGetDataCallBack<MemberBasicInfoBean>() {
            @Override
            public void onResult(MemberBasicInfoBean basicInfoBean) {
                mHxUserBean = ObjectCloneUtils.Object2Object(HxUserBean.class, basicInfoBean.getInfo());
                mHxUserBean.setLive_cityname(basicInfoBean.getInfo().getAll_live());
                mDataBinding.setUserBean(mHxUserBean);
                mHxId = basicInfoBean.getInfo().getHx_id();
                checkIsFriend(mHxId);
                getIsClickPraise();
                if (isFriend)
                    getUserDynammic(mImageAdapter);
                else {
                    if (TextUtils.isEmpty(mHxId)) {
                        mDataBinding.setInfo(basicInfoBean.getInfo().getProfile());
                    }

                }
            }

            @Override
            public void onError(String err) {
                Toast.makeText(mContext, "错误:" + err, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHxUserBean() {

        TaskBase.getUserDetails(LoginHelper.getInstance().getUserToken(), mHxId, new TaskBase.OnGetDataCallBack<HxUserBean>() {
            @Override
            public void onResult(HxUserBean userBean) {
                mHxUserBean = userBean;
                mDataBinding.setUserBean(userBean);
                checkIsFriend(mHxUserBean.getHx_id());
                getIsClickPraise();
//                if (isFriend)
//                    getUserDynammic(mImageAdapter);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    /**
     * 获取是否进行过点赞
     */
    private void getIsClickPraise() {
        loadClickPrise(infoBean.getUid());
    }

    private void loadClickPrise(String uid) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getPraiseInfo(LoginHelper.getInstance().getUserToken(), uid)
                , new Subscriber<HttpResult<PraiseBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<PraiseBean> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            if (Integer.parseInt(result.getInfo().getCount()) > 0) {
                                mDataBinding.zanNum.setText(result.getInfo().getCount());
                            }
                            if (result.getInfo().getStatus() == 1) {
                                mDataBinding.ivClickPraise.setImageResource(R.drawable.ic_vector_new_yidianzan);
                                isClickPraise = true;
                            } else {
                                mDataBinding.ivClickPraise.setImageResource(R.drawable.ic_vector_news_weidianzan);
                                isClickPraise = false;
                            }
                        }

                    }
                }
        );
    }

    private void getUserDynammic(final ImageAdapter adapter) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getMyDynammicImages(LoginHelper.getInstance().getUserToken(), mHxUserBean.getUid())
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<List<String>>>() {
                    @Override
                    public void onNext(HttpResult<List<String>> result) {
                        for (String s : result.getInfo()) {
                            if (!TextUtils.isEmpty(s)) {
                                mImageList.add(s);
                            }
                        }
                        if (mImageList.size() <= 0) {
                            mDataBinding.tvDynamic.setVisibility(View.VISIBLE);
                            mDataBinding.gridView.setVisibility(View.GONE);
                        } else
                            adapter.notifyDataSetChanged();
                    }
                })
        );
    }

    private void getUserInfo() {
        TaskBase.getMemberBasicInfo(mHxUserBean.getUid(), new TaskBase.OnGetDataCallBack<MemberBasicInfoBean>() {
            @Override
            public void onResult(MemberBasicInfoBean basicInfoBean) {
                mDataBinding.setInfo(basicInfoBean.getInfo().getProfile());
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODES) {
            if (resultCode == RESULT_OK) {
                finish();
            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hx_member_details;
    }

    private class ImageAdapter extends BaseAdapter {
        private int mImageSize;

        ImageAdapter() {
            mImageSize = LshlApplication.sWindowWidth / 7;
        }

        @Override
        public int getCount() {
            return mImageList.size() < 4 ? mImageList.size() : 4;
        }

        @Override
        public String getItem(int position) {
            return mImageList.get(position);
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
            if (TextUtils.isEmpty(getItem(position))) {
//                Glide.with(mContext).load(R.drawable.ease_default_image).into((ImageView) convertView);
            } else {
                Glide.with(mContext).load(ApiClient.getDynamicImage(getItem(position))).into((ImageView) convertView);
            }
            return convertView;
        }
    }
}

package com.lshl.ui.me.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.GoodsDetailsBean;
import com.lshl.bean.MemberBasicInfoBean;
import com.lshl.databinding.ActivityMyGoodsDetailsBinding;
import com.lshl.task.TaskBase;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DialogUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

public class GoodsDetailsActivity extends BaseActivity<ActivityMyGoodsDetailsBinding> {

    public static final int FROM_MY_SHOP = 0x000123;
    public static final int FROM_OTHER = 0x0000124;

    private static final int REQUEST_CODE_EDIT_GOODS = 0X000111;

    private String mGdid;
    private List<String> imgs;

    private GoodsDetailsBean.InfoBean mGoodsInfoBean;
    private MemberBasicInfoBean mMemberInfoBean;


    private Calendar mCalendar;

    private int mFromWhere;
    private boolean isCollection;

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

    /**
     * @param activity
     * @param gdid
     * @param fromWhere 来自于那里 目前可以如到当前Activity的页面有{@value FROM_MY_SHOP 个人店铺} 和 {@value FROM_OTHER}来自于其他页面
     */
    public static void actionStart(Activity activity, String gdid, int fromWhere) {
        Intent intent = new Intent(activity, GoodsDetailsActivity.class);
        intent.putExtra("gdid", gdid);
        intent.putExtra("where", fromWhere);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, String gdid, int fromWhere, int requestCode) {
        Intent intent = new Intent(activity, GoodsDetailsActivity.class);
        intent.putExtra("gdid", gdid);
        intent.putExtra("where", fromWhere);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 处理点击事件的类
     */
    public class Presenter {
        /**
         * 设置订单的备注信息
         */
        public void setOrderPs(final TextView tv) {

            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = mCalendar.get(Calendar.MINUTE);

            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            final StringBuilder sb = new StringBuilder();

            final TimePickerDialog dialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    sb.append(hourOfDay).append(":").append(minute);
                    tv.setText(sb.toString());
                    Log.d("dataLog", "hourOfDay = " + hourOfDay + "minute = " + minute);
                }
            }, hour, minute, DateFormat.is24HourFormat(mContext));

            DatePickerDialog dateDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    sb.append(year).append("年").append(month).append("月").append(dayOfMonth).append("日").append(" ");
                    Log.d("dataLog", "year = " + year + "month = " + month + "dayOfMonth = " + dayOfMonth);
                    dialog.show();
                }
            }, year, month, day);

            dateDialog.show();

        }

        public void contactSeller() {//联系卖家(打电话)
            RxPermissions.getInstance(mContext).request(Manifest.permission.CALL_PHONE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要拨打" + mGoodsInfoBean.getGd_phone() + "号码",
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
                                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mGoodsInfoBean.getGd_phone()));
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

        public void orderImmediately() {//立即预购
            lightOff();
        }

        public void sendMessage() {//发送消息
            EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                @Override
                public void onSuccess(List<String> strings) {
//                    if (strings.contains(mGoodsInfoBean.getHx_id())) {
                    ChatActivity.actionStart(GoodsDetailsActivity.this, null, mGoodsInfoBean.getHx_id(), EaseConstant.CHATTYPE_SINGLE, 0);
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                DialogUtils.alertDialog(mContext, "温馨提示", "检测到该用户不是您好友,是否申请添加该用户为好友", new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        sweetAlertDialog.dismissWithAnimation();
//                                    }
//                                }, new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        sweetAlertDialog.dismissWithAnimation();
//                                        HxMemberDetailsActivity.actionStart(GoodsDetailsActivity.this, "", mGoodsInfoBean.getUid(),false);
//                                    }
//                                });
//                            }
//                        });
//                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

        public void collection() {//收藏
            if (isCollection) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).cancelCollectionGoods(LoginHelper.getInstance().getUserToken(), mGdid)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_uncollection);
                                isCollection = false;
                            }
                        })
                );
            } else {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object)
                                .collectionGoods(LoginHelper.getInstance().getUserToken(), mGdid)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                //收藏成功
                                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_collection);
                                isCollection = true;
                            }
                        }));
            }

        }

        public void lookShopMessage() {//查看商家信息
            if (mMemberInfoBean != null)
                HxMemberDetailsActivity.actionStart(GoodsDetailsActivity.this, "", mMemberInfoBean.getInfo().getUid(), false);
//                SellerInfoActivity.actionStart(GoodsDetailsActivity.this, mMemberInfoBean, SellerInfoActivity.FROM_SELLER_INFO);
        }

        public void lookComment() {//查看评论
            GoodsCommentActivity.actionStart(GoodsDetailsActivity.this, mGdid);
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("产品详情", DEFAULT_TITLE_TEXT_COLOR);
        if (mFromWhere == FROM_MY_SHOP) {
            setRightViewText("编辑", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditGoodsActivity.actionStart(GoodsDetailsActivity.this, mGoodsInfoBean, "", EditGoodsActivity.FROM_EDIT_GOODS, REQUEST_CODE_EDIT_GOODS);
                }
            });
        }
        if (mFromWhere == FROM_OTHER) {
            getRightView().setButtonDrawable(R.drawable.ic_vector_share_details);
            getRightView().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    title = mGoodsInfoBean.getGd_goodname();
                    content = mGoodsInfoBean.getGd_info();
                    imagePath = ApiClient.getGoodsInfoImage(mGoodsInfoBean.getGd_img());
                    mShareAction.withTitle(title)
                            .withText(content)
                            .withMedia(new UMImage(mContext, imagePath))
                            .withTargetUrl(ApiService.SHARE_URL + "zimaoqu/id/" + mGoodsInfoBean.getGd_id())
                            .open();
                }
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mDataBinding.vpBanner.stopTurning();
    }

    @Override
    protected void initFieldBeforeMethods() {

        Intent intent = getIntent();
        mGdid = intent.getStringExtra("gdid");
        mFromWhere = intent.getIntExtra("where", -1);

        imgs = new ArrayList<>();
        mCalendar = Calendar.getInstance();

    }

    @Override
    protected void initViews() {
        mShareAction = new ShareAction(GoodsDetailsActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.QQ).setCallback(mShareListener);
        mDataBinding.setPresenter(new Presenter());

        mDataBinding.setIsMyGoods(mFromWhere == FROM_MY_SHOP);

        loadData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_GOODS) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void loadData() {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateGoodsDetails(LoginHelper.getInstance().getUserToken(), mGdid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<GoodsDetailsBean>() {
                    @Override
                    public void onNext(GoodsDetailsBean result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            mGoodsInfoBean = result.getInfo().get(0);
                            mDataBinding.setGoodsInfo(mGoodsInfoBean);
                            if ("1".equals(mGoodsInfoBean.getIsCollection())) {
                                isCollection = true;
                                mDataBinding.ivCollection.setImageResource(R.drawable.ic_vector_collection);
                            }
                            List<String> imageList = mGoodsInfoBean.getImg();
                            for (String s : imageList) {
                                imgs.add(ApiClient.getGoodsInfoImage(s));
                            }
                            if (LoginHelper.getInstance().getUserBean().getUid().equals(mGoodsInfoBean.getGd_uid())) {
                                mDataBinding.setIsMyGoods(true);
                            }
                            getMemberBasicInfo();
                            setupBanner();
                        } else if (result.getStatus().equals(ApiService.TOKEN_EX)) {
                            showMessage("登陆身份异常,请重新登陆");
                        }

                    }
                }));

    }

    private void getMemberBasicInfo() {
        TaskBase.getMemberBasicInfo(mGoodsInfoBean.getGd_uid(), new TaskBase.OnGetDataCallBack<MemberBasicInfoBean>() {
            @Override
            public void onResult(MemberBasicInfoBean memberBasicInfoBean) {
                mMemberInfoBean = memberBasicInfoBean;
            }

            @Override
            public void onError(String err) {
                Toast.makeText(mContext, "错误信息：" + err, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupBanner() {
        mDataBinding.vpBanner.startTurning(2500);
        mDataBinding.vpBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {
            @Override
            public LoadImageHolder createHolder() {
                return new LoadImageHolder();
            }
        }, imgs).setPageIndicator(new int[]{R.drawable.bg_banner_unselect, R.drawable.bg_banner_select});
        mDataBinding.vpBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, PhotoShowActivity.class);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, (ArrayList<String>) imgs);
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, position);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_goods_details;
    }
}

package com.lshl.ui.me.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseConstant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityMyOrderDetailsBinding;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.bean.MyOrderBean;
import com.lshl.bean.ResultInfoBean;
import com.lshl.utils.DialogUtils;

import java.text.SimpleDateFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyOrderDetailsActivity extends BaseActivity<ActivityMyOrderDetailsBinding> {
    private int position;
    private String go_goodsid;//产品id
    private String go_id;
    private String go_sell_uid;//卖家id
    private String k;

    public static void actionStart(Activity activity, int position) {
        Intent intent = new Intent(activity, MyOrderDetailsActivity.class);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    public class Presenter {

        public void comment() {//评论
            if (TextUtils.isEmpty(k)) {
                final EditText editTextss = new EditText(MyOrderDetailsActivity.this);
                editTextss.setMaxLines(5);
                editTextss.setHint("请输入评论内容");
                AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderDetailsActivity.this);
                AlertDialog dialog = builder.setTitle("评论内容").setView(editTextss).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!editTextss.getText().toString().isEmpty()) {
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                            .updateOrderComment(LoginHelper.getInstance().getUserToken(), go_goodsid, go_id, go_sell_uid, editTextss.getText().toString().trim()),
                                    new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                                        @Override
                                        public void onNext(ResultInfoBean result) {
                                            k = result.getInfo();
                                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                        }
                                    }));
                        } else {
                            Toast.makeText(mContext, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create();
                dialog.show();
            } else {
                showMessage("您已评论，无需重复操作");
            }


        }

        public void goodsDetails() {//商品详情
            GoodsDetailsActivity.actionStart(MyOrderDetailsActivity.this, go_goodsid, GoodsDetailsActivity.FROM_OTHER);
        }

        public void cancelOrder() {//取消订单
            Log.i("取消订单", 1 + "===");
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).cancelOrder(LoginHelper.getInstance().getUserToken(), go_id, go_goodsid), new ProgressSubscriber<ResultInfoBean>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                @Override
                public void onNext(ResultInfoBean result) {
                    Log.i("取消订单", result.getStatus() + "===" + result.getInfo());
                    if (result.getStatus() == 1) {
                        Toast.makeText(mContext, result.getInfo() + "", Toast.LENGTH_SHORT).show();
                        mDataBinding.btnOrderCancel.setVisibility(View.GONE);
                        mDataBinding.btnReserve.setText("订单已取消");
                        Log.i("取消订单", result.getInfo() + "===");
                    }
                }
            }));
        }

        public void call() {//打电话
            if (mDataBinding.tvPhone != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                AlertDialog alertDialog = builder.setTitle("是否拨打该号码?")
                        .setMessage(mDataBinding.tvPhone.getText().toString())
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("拨打", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + mDataBinding.tvPhone.getText().toString()));
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
                        }).create();
                alertDialog.show();
            }
        }

        public void sendMessage() {//发送消息
            ChatActivity.actionStart(MyOrderDetailsActivity.this, null, go_sell_uid, EaseConstant.CHATTYPE_SINGLE, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("订单详情", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("删除订单", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要删除该订单", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(
                                ApiClient.getApiService(
                                        ApiService.class, RetrofitManager.RetrofitType.Object
                                ).byOrderDelete(LoginHelper.getInstance().getUserToken(), go_id)
                                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                                    @Override
                                    public void onNext(ResultInfoBean result) {
                                        finish();
                                        Toast.makeText(mContext, "" + result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                        );
                    }
                });
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        position = getIntent().getIntExtra("position", 0);
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMyOrder(LoginHelper.getInstance().getUserToken(), null), new ProgressSubscriber<MyOrderBean>(mContext, new SubscriberOnNextListener<MyOrderBean>() {

            @Override
            public void onNext(MyOrderBean result) {
                MyOrderBean.InfoBean.ListBean bean = result.getInfo().getList().get(position);
                if (bean != null) {
                    Long times = Long.decode(bean.getGo_addtime()) * 1000;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String format1 = format.format(times);
                    go_id = bean.getGo_id();//订单id
                    go_goodsid = bean.getGo_goodsid();//产品id
                    go_sell_uid = bean.getGo_sell_uid();//卖家uid
                    k = bean.getK();
                    mDataBinding.tvTime.setText(format1);
                    mDataBinding.tvOrderNum.setText("订单号:" + bean.getGo_id());
                    mDataBinding.tvGoodsName.setText(bean.getGd_goodname());
                    mDataBinding.tvOldPrice.setText("原价:￥" + bean.getGd_original_price());
                    mDataBinding.tvFellowPrice.setText("老乡价:￥" + bean.getGd_special_offer());
                    double i1 = Double.parseDouble(bean.getGd_special_offer());
                    double i2 = Double.parseDouble(bean.getGo_num());
                    double total = i1 * i2;
                    mDataBinding.tvPriceTotal.setText("金额:￥" + total);
                    Glide.with(mContext).load(ApiClient.getGoodsInfoImage(bean.getGd_img1())).into(mDataBinding.ivGoodsImg);
                    String status = bean.getGo_status();
                    if ("1".equals(status)) {
                    } else if ("2".equals(status)) {
                        mDataBinding.btnReserve.setText("卖家驳回");
                        mDataBinding.btnOrderCancel.setVisibility(View.GONE);
                    } else if ("0".equals(status)) {
                        mDataBinding.btnReserve.setText("订单已取消");
                        mDataBinding.btnOrderCancel.setVisibility(View.GONE);
                    }
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_details;
    }
}

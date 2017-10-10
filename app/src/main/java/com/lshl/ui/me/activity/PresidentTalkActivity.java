package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.request.PresidentTalkBean;
import com.lshl.databinding.ActivityPresidentTalkBinding;
import com.lshl.ui.me.adapter.ReleaseReputationImageAdapter;
import com.lshl.utils.BitmapUtils;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

import static com.lshl.ui.me.imagegrid.ImageGridActivity.RESULT_DATA;

public class PresidentTalkActivity extends BaseActivity<ActivityPresidentTalkBinding> {
    private ReleaseReputationImageAdapter mImageAdapter;
    private static final int REQUEST_CODE_ADD_IMAGE = 0x000132;
    private List<String> mImageList;
    public static final int REQUESTCODE_PRESIDENT_TALK = 0x000123;

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PresidentTalkActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("会长说", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mImageList = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        mImageAdapter = new ReleaseReputationImageAdapter(this, REQUEST_CODE_ADD_IMAGE, 6);
        mDataBinding.recyclerImage.setLayoutManager(new GridLayoutManager(mContext, 3));
        mDataBinding.recyclerImage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                if (position == 2 || position == 5) {
                    outRect.set(0, 0, 0, 10);
                    return;
                }
                outRect.set(0, 0, 10, 10);
            }
        });
        mDataBinding.recyclerImage.setAdapter(mImageAdapter);
        mDataBinding.btnSubmitReputation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要提交以上内容", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        String title = mDataBinding.editTitle.getText().toString();
                        String content = mDataBinding.editInfo.getText().toString();
                        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                            showMessage("请将基本信息填写完整");
                            return;
                        }
                        if (mImageList.size() > 0) {
                            PresidentTalkBean bean = new PresidentTalkBean();
                            bean.setToken(LoginHelper.getInstance().getUserToken());
                            bean.setTitle(title);
                            bean.setContent(content);
                            Map<String, String> body = ApiClient.createBodyMap(bean);
                            sendTalkInfo(body);
                        } else {
                            updateText(title, content);
                        }
                    }

                });
            }
        });
    }

    private void updateText(String title, String content) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updatePresidentTalktext(LoginHelper.getInstance().getUserToken(), null, title, content), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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

    private void sendTalkInfo(final Map<String, String> body) {
        BitmapUtils.pictureCompression(mContext, mImageList, new BitmapUtils.OnPhotoCompressionCallback() {
            @Override
            public void onCompressionCallback(List<String> imageUrl) {
                DialogUtils.showProgressDialog(mContext, "正在上传中", "", "");
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                        , RetrofitManager.RetrofitType.Object).updatePresidentTalk(ApiClient.getImageParams("file", imageUrl), body)
                        , new Subscriber<HttpResult<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
                            }

                            @Override
                            public void onNext(HttpResult<String> body) {
                                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                                Toast.makeText(mContext, body.getInfo(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_IMAGE) {
            if (resultCode == RESULT_OK) {
                mImageList.clear();
                mImageList.addAll(data.getStringArrayListExtra(RESULT_DATA));
                mImageAdapter.addImageUrl(mImageList);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_president_talk;
    }
}

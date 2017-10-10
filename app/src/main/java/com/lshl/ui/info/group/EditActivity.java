package com.lshl.ui.info.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityEditBinding;

import java.io.IOException;

import okhttp3.ResponseBody;


public class EditActivity extends BaseActivity<ActivityEditBinding> {

    private static final String TITLE = "title";
    private static final String DATA = "data";
    private static final String GROUP_ID = "group_id";

    private String mTitle;
    private String mEditGroupName;
    private String mGroupId;

    public static void actionStart(Activity activity, String title, String groupName, String groupId, int requestCode) {
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(GROUP_ID, groupId);
        intent.putExtra(DATA, groupName);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE);
        mEditGroupName = intent.getStringExtra(DATA);
        mGroupId = intent.getStringExtra(GROUP_ID);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView(mTitle, DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("保存", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.edittext.setText(mEditGroupName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }


    public void save(View view) {

        final String groupName = mDataBinding.edittext.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            Toast.makeText(mContext, "请填写您要修改的群名称", Toast.LENGTH_SHORT).show();
//            showMessage("请填写您要修改的群名称");
        } else
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).updateHxGroupName(mGroupId, groupName)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody result) {
                            try {
                                String resultStr = result.string();
                                JSONObject object = JSON.parseObject(resultStr);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals(ApiService.STATUS_SUC)) {
                                    setResult(RESULT_OK, new Intent().putExtra("data", groupName));
                                    finish();
                                }
                                if (!TextUtils.isEmpty(info))
                                    Toast.makeText(mContext, info, Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
            );

    }

}

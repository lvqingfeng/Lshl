package com.lshl.ui.me.job;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import com.lshl.bean.JobInfoBean;
import com.lshl.databinding.ActivityJobInfoBinding;
import com.lshl.view.SelectMenuPopWindow;

import java.util.Arrays;
import java.util.List;

/**
 * 求职简介
 */
public class JobInfoActivity extends BaseActivity<ActivityJobInfoBinding> {

    private List<String> mEducationList;
    private SelectMenuPopWindow mEducationPopWindow;

    private String mEducationName;

    private boolean isEdit;
    private InputMethodManager mInputMethod;

    private String mUid;
    private String mResumeId;

    public static void actionStart(Activity activity, String uid, String resumeId) {
        Intent intent = new Intent(activity, JobInfoActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("rid", resumeId);
        activity.startActivity(intent);
    }

    public class Presenter {
        /**
         * 筛选学历/学位
         */
        public void onClickSelectEducation() {
            if (isEdit) {
                lightOff();
                mEducationPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        }

        /**
         * 输入毕业院校
         *
         * @param inputText
         */
        public void onClickInputSchool(EditText inputText) {
            if (isEdit) {
                changeEditView(inputText, true);
                //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
                mInputMethod.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);
            }
        }

        /**
         * 输入相关专业
         *
         * @param inputText
         */
        public void onClickInputMajor(EditText inputText) {
            if (isEdit) {
                changeEditView(inputText, true);
                //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
                mInputMethod.showSoftInput(inputText, InputMethodManager.SHOW_FORCED);
            }
        }

        /**
         * 收藏列表
         */
        public void onClickCollectList() {
            JobCollectActivity.actionStart(JobInfoActivity.this);
        }

        /**
         * 投递列表
         */
        public void onClickDeliverList() {
            JobDeliverListActivity.actionStart(JobInfoActivity.this);
        }

        /**
         * 工作经历信息
         */
        public void onClickGoWorkHistory() {
            WorkHistoryActivity.actionStart(JobInfoActivity.this, mUid);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {

        Intent intent = getIntent();
        mUid = intent.getStringExtra("uid");
        mResumeId = intent.getStringExtra("");

        mEducationPopWindow = new SelectMenuPopWindow(this);
        mEducationList = Arrays.asList(getResources().getStringArray(R.array.education_menu));
        //1.得到InputMethodManager对象
        mInputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    protected void setupTitle() {
        setTextTitleView("求职简介", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        setRightViewText("编辑", DEFAULT_TITLE_TEXT_COLOR).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String rightName;
                if (isChecked) {
                    isEdit = true;
                    rightName = "保存";
                } else {
                    isEdit = false;
                    rightName = "编辑";
                    changeEditView(mDataBinding.editSchool, isEdit);
                    changeEditView(mDataBinding.editMajor, isEdit);


                    String schoolName = mDataBinding.editSchool.getText().toString();
                    String majorName = mDataBinding.editMajor.getText().toString();
                    String education = mDataBinding.tvEducation.getText().toString();
                    if (TextUtils.isEmpty(schoolName) && TextUtils.isEmpty(majorName) && TextUtils.isEmpty(education)) {
                        showMessage("保存失败，至少填写一项，才能进行保存");
                    } else {
                        RetrofitManager.toSubscribe(
                                ApiClient.getApiService(
                                        ApiService.class, RetrofitManager.RetrofitType.Object
                                ).launchRecruit(LoginHelper.getInstance().getUserToken(), schoolName, education, majorName)
                                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                    @Override
                                    public void onNext(HttpResult<String> result) {
                                        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        );
                    }


                }
                setRightViewText(rightName, DEFAULT_TITLE_TEXT_COLOR);
            }
        });
    }

    @Override
    protected void initViews() {
        if (!LoginHelper.getInstance().getUserBean().getUid().equals(mUid)) {
            getRightView().setVisibility(View.GONE);
            mDataBinding.tvCollect.setVisibility(View.GONE);
            mDataBinding.tvDeliver.setVisibility(View.GONE);
        }
        mDataBinding.setPresenter(new Presenter());
        mEducationPopWindow.bindingList(mEducationList);
        mEducationPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mEducationPopWindow.setOnClickSelectListener(new SelectMenuPopWindow.OnClickSelectListener() {
            @Override
            public void onClickSelectItem(String itemName) {
                mEducationName = itemName;
                mDataBinding.tvEducation.setText(itemName);
            }
        });
        loadData();
    }

    private void loadData() {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).resumeDetails(mResumeId, mUid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<JobInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<JobInfoBean> result) {
                        mDataBinding.setInfoBean(result.getInfo());
                    }
                })
        );

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_info;
    }

    private void changeEditView(EditText editText, boolean flag) {

        editText.setEnabled(flag);
        editText.setCursorVisible(flag);
        if (!flag) {
            editText.setTextColor(0xff888888);
        }
        editText.setFocusable(true);
        editText.requestFocus();
        editText.setFocusableInTouchMode(true);
        editText.setSelection(editText.length());
    }

}

package com.lshl.ui.me.job;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import com.lshl.bean.WorkHistoryInfoBean;
import com.lshl.bean.WorkHistoryListBean;
import com.lshl.databinding.ActivityCreateWorkHistoryBinding;

import java.util.Calendar;

/**
 * 创建工作经历或编辑工作经历
 */
public class CreateWorkHistoryActivity extends BaseActivity<ActivityCreateWorkHistoryBinding> {

    private Calendar mCalendar;
    private String mWorkId;

    private boolean isBrowse;

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CreateWorkHistoryActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void actionStart(Activity activity, boolean isBrowse, String weid, int requestCode) {
        Intent intent = new Intent(activity, CreateWorkHistoryActivity.class);
        intent.putExtra("is_browse", isBrowse);
        intent.putExtra("weid", weid);
        activity.startActivityForResult(intent, requestCode);
    }

    public class Presenter {
        public void onClickSelectStartTime(TextView tv) {
            showDateSelectPop(tv);
        }

        public void onClickSelectEndTime(TextView tv) {
            showDateSelectPop(tv);
        }

        public void onClickEditCompanyName(EditText editText) {

            if (editText.getSelectionStart() < editText.length())
                return;
            editText.setSelection(editText.length());//设置光标显示在文字的最后面
        }

        public void onClickEditJobName(EditText editText) {
            if (editText.getSelectionStart() < editText.length())
                return;
            editText.setSelection(editText.length());//设置光标显示在文字的最后面
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mWorkId = intent.getStringExtra("weid");
        isBrowse = intent.getBooleanExtra("is_browse", false);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("工作/实习经验", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        setRightViewText("保存", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startTime = mDataBinding.tvStartTime.getText().toString();
                String endTime = mDataBinding.tvEndTime.getText().toString();
                String jobName = mDataBinding.editJobName.getText().toString();
                String companyName = mDataBinding.editCompanyName.getText().toString();
                String jobInfo = mDataBinding.editJobInfo.getText().toString();
                if (TextUtils.isEmpty(jobName) || TextUtils.isEmpty(companyName) || TextUtils.isEmpty(jobInfo)) {
                    showMessage("请将您的基本信息填写完整");
                    return;
                }
                if (TextUtils.isEmpty(startTime)) {
                    showMessage("请选择您的工作开始时间");
                    return;
                }
                if (TextUtils.isEmpty(endTime)) {
                    showMessage("请选择您的结束时间");
                    return;
                }

                final WorkHistoryListBean.ListBean bean = new WorkHistoryListBean.ListBean();
                bean.setWe_company_starttime(startTime);
                bean.setWe_company_endtime(endTime);
                bean.setWe_company_name(companyName);
                bean.setWe_jobs(jobName);

                if (TextUtils.isEmpty(mWorkId)) {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object
                            ).launchWorkInfo(LoginHelper.getInstance().getUserToken(), companyName, startTime, endTime, jobInfo, jobName)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    Toast.makeText(mContext, "创建完成", Toast.LENGTH_SHORT).show();
                                    String id = result.getInfo();
                                    bean.setWe_id(id);
                                    Intent intent = new Intent();
                                    intent.putExtra("result_bean", bean);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            })
                    );
                } else {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object
                            ).saveWorkInfo(LoginHelper.getInstance().getUserToken(), mWorkId, companyName, startTime, endTime, jobInfo, jobName)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    Toast.makeText(mContext, "编辑成功", Toast.LENGTH_SHORT).show();
                                    bean.setWe_id(mWorkId);
                                    Intent intent = new Intent();
                                    intent.putExtra("result_bean", bean);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            })
                    );
                }

            }
        });
    }

    @Override
    protected void initViews() {

        if (isBrowse) {
            getRightView().setVisibility(View.GONE);
            mDataBinding.editCompanyName.setEnabled(false);
            mDataBinding.editJobInfo.setEnabled(false);
            mDataBinding.editJobName.setEnabled(false);
            mDataBinding.llEnd.setEnabled(false);
            mDataBinding.llStart.setEnabled(false);
        }

        mDataBinding.setPresenter(new Presenter());
        mCalendar = Calendar.getInstance();

        if (!TextUtils.isEmpty(mWorkId)) {
            loadWorkInfo();
        }

    }

    private void loadWorkInfo() {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).workInfo(mWorkId)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<WorkHistoryInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<WorkHistoryInfoBean> result) {
                        if (result.getInfo() != null) {
                            mDataBinding.setInfoBean(result.getInfo());
                        }
                    }
                })
        );

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_work_history;
    }

    private void showDateSelectPop(final TextView timeView) {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        final StringBuilder sb = new StringBuilder();
        DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sb.append(year).append("年").append(month + 1).append("月");
                timeView.setText(sb.toString());
            }
        }, year, month, day);
        dialog.setTitle("请选择时间");
        dialog.show();
    }
}

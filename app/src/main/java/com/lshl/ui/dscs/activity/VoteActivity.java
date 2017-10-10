package com.lshl.ui.dscs.activity;

/**
 * 项目投票页面
 */
/*public class VoteActivity extends BaseActivity<ActivityVoteBinding> {

    private int mFromWhere;
    private boolean isSuccess;
    private int mCurrentStatus;

    public static void actionStart(Activity activity,int currentStatus, int fromWhere, int requestCode) {
        Intent intent = new Intent(activity, VoteActivity.class);
        intent.putExtra("where", fromWhere);
        intent.putExtra("status", currentStatus);
        activity.startActivityForResult(intent, requestCode);
    }

   public class Presenter {

        public void onClickAgree() {//支持
            clickVote("1");
        }

        public void onClickOppose() {//反对
            clickVote("0");
        }

        private void clickVote(final String status) {
           if (mFromWhere == ProjectStatusActivity.FROM_MA) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.String
                        ).maClickVote(LoginHelper.getInstance().getUserToken(), mVoteInfo.getStatusCode(), mVoteInfo.getId(), status)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                checkIsClickVote(result, status);
                            }
                        })
                );
            } else if (mFromWhere == ProjectStatusActivity.FROM_MB) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.String
                        ).mbClickVote(LoginHelper.getInstance().getUserToken(), mVoteInfo.getStatusCode(), mVoteInfo.getId(), status)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                checkIsClickVote(result, status);
                            }
                        })
                );
            }
        }

        private void checkIsClickVote(ResponseBody responseBody, String voteStatus) {
            try {
                String resultStr = responseBody.string();
                JSONObject object = JSON.parseObject(resultStr);
                String status = object.getString("status");
                if (status.equals(ApiService.STATUS_SUC)) {
                    isSuccess = true;

                    setupButtonNotClick();
                    if (voteStatus.equals("1")) {
                        int supportNum = Integer.decode(mVoteInfo.getSupportNum()) + 1;
                        mVoteInfo.setSupportNum(String.valueOf(supportNum));
                        mDataBinding.cbProgressBar.setProgress(supportNum);
                        mDataBinding.tvAgreeNum.setText(String.format(getString(R.string.support_num_s), "" + supportNum));
                    } else if (voteStatus.equals("0")) {
                        String opposeNum = String.valueOf(Integer.decode(mVoteInfo.getOpposeNum()) + 1);
                        mVoteInfo.setOpposeNum(opposeNum);
                        mDataBinding.tvOpposeNum.setText(String.format(getString(R.string.support_num_s), opposeNum));
                    }

                } else if (status.equals(ApiService.TOKEN_EX)) {
                    showMessage("登陆信息异常，请重新登陆");
                }
                showMessage(object.getString("info"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("投票", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    public void finish() {
        if (isSuccess) {
            setResult(RESULT_OK, new Intent().putExtra("resultBean", mVoteInfo));
        }
        super.finish();
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mVoteInfo = (DscsProjectDetailsBean.VoteList) intent.getSerializableExtra("bean");
        mFromWhere = intent.getIntExtra("where", -1);
        mCurrentStatus = intent.getIntExtra("status", -1);
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(new Presenter());
        mDataBinding.cbProgressBar.setMax(Integer.decode(mVoteInfo.getAllMember()));
        mDataBinding.cbProgressBar.setProgress(Integer.decode(mVoteInfo.getSupportNum()));

        int statusCode = Integer.decode(mVoteInfo.getStatusCode());
        if (mCurrentStatus > statusCode) {
            mVoteInfo.setStatus("审核通过");
            setupButtonNotClick();
        } else if (mCurrentStatus == statusCode && statusCode == 2) {
            mVoteInfo.setStatus("审核中");
            setupButtonNotClick();
        } else {
            mDataBinding.tvStatusS.setVisibility(View.VISIBLE);
            if (mFromWhere == ProjectStatusActivity.FROM_MB) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.String
                        ).mbIfClickVote(LoginHelper.getInstance().getUserToken(), mVoteInfo.getStatusCode(), mVoteInfo.getId(), "1")
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                checkIsClickVote(result);
                            }
                        })
                );
            } else if (mFromWhere == ProjectStatusActivity.FROM_MA) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.String
                        ).maIfClickVote(LoginHelper.getInstance().getUserToken(), mVoteInfo.getStatusCode(), mVoteInfo.getId(), "1")
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody result) {
                                checkIsClickVote(result);
                            }
                        })
                );
            }
        }
        mDataBinding.setVoteInfo(mVoteInfo);
    }

    private void checkIsClickVote(ResponseBody result) {
        try {
            String resultStr = result.string();
            JSONObject object = JSON.parseObject(resultStr);
            String status = object.getString("status");
            if (status.equals(ApiService.STATUS_SUC)) {
                String info = object.getString("info");
                if ("2".equals(info) || "3".equals(info)) {
                    setupButtonNotClick();
                }
            } else if (status.equals(ApiService.TOKEN_EX)) {
                showMessage("登陆身份异常，请重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupButtonNotClick() {
        mDataBinding.btnAgree.setEnabled(false);
        mDataBinding.btnOppose.setEnabled(false);
        mDataBinding.btnAgree.setBackgroundResource(R.drawable.bg_gray_btn);
        mDataBinding.btnOppose.setBackgroundResource(R.drawable.bg_gray_btn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote;
    }
}
*/

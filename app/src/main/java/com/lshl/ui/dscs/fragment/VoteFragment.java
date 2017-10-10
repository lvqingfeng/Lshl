package com.lshl.ui.dscs.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseFragment;
import com.lshl.base.HttpResult;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.databinding.FragmentVoteBinding;

/**
 * 投票界面
 */
public class VoteFragment extends BaseFragment<FragmentVoteBinding> {

    private DscsProjectDetailsBean mBean;
    private String mVerifyType;
    private SetupDateCallback mCallback;

    /**
     */
    public static VoteFragment newInstance(DscsProjectDetailsBean bean) {
        VoteFragment fragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        fragment.setArguments(args);
        return fragment;
    }

    public void uploadVoteStatus(DscsProjectDetailsBean detailsBean) {
        mBean = detailsBean;
    }

    public class Presenter {
        public void onClickVoteSuc() {//支持票
            onClickVote("1");
        }

        public void onClickVoteOppose() {//反对票
            onClickVote("2");
        }

        public void onClickVoteWaiver() {//弃权票
            onClickVote("3");
        }

        public void onClickUnVoteSuc() {
            Toast.makeText(mContext, "无法投票", Toast.LENGTH_SHORT).show();
        }

        public void onClickUnVoteOppose() {
            Toast.makeText(mContext, "无法投票", Toast.LENGTH_SHORT).show();
        }

        public void onClickUnVoteWaiver() {
            Toast.makeText(mContext, "无法投票", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击投票按钮
     *
     * @param s
     */
    private void onClickVote(final String s) {
        if (mBean.isHelp()) {//是否是互助
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).maClickVote(LoginHelper.getInstance().getUserToken(), s, mBean.getId(), mVerifyType)
                    , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            Toast.makeText(mContext, "投票成功", Toast.LENGTH_SHORT).show();
                            setupColumnarGrow(s, true);
                            setupVoteResult(s);
                        }
                    })
            );
        } else if (mBean.isGood()) {//是否是公益
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).mbClickVote(LoginHelper.getInstance().getUserToken(), s, mBean.getId(), mVerifyType)
                    , new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            Toast.makeText(mContext, "投票成功", Toast.LENGTH_SHORT).show();
                            setupColumnarGrow(s, true);
                            setupVoteResult(s);
                        }
                    })
            );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (SetupDateCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBean = (DscsProjectDetailsBean) getArguments().getSerializable("bean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    protected void initViews() {
        mCallback.onDateCallback();
        mDataBinding.setPresenter(new Presenter());
        mVerifyType = getVerifyType();
        if (mBean.isHelp()) {//是否是互助（判断当前是否可以投票）
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).maIfClickVote(LoginHelper.getInstance().getUserToken(), mVerifyType, mBean.getId())
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            setupVoteStatus(result.getInfo());
                        }
                    })
            );
        } else if (mBean.isGood()) {//是否是公益（判断当前是否可以投票）
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).mbIfClickVote(LoginHelper.getInstance().getUserToken(), mBean.getId(), mVerifyType)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            setupVoteStatus(result.getInfo());
                        }
                    })
            );
        }
        String status = "";
        switch (mBean.getStatusCode()) {
            case Constant.VOTE_FIRST_STATUS:
                status = "初审审核";
                break;
            case Constant.VOTE_SEND_STATUS:
                status = "复审审核";
                break;
            case Constant.VOTE_THIRD_STATUS:
                status = "终审审核";
                break;
        }
        mDataBinding.tvStatus.setText(status + "(审核人数:" + mBean.getAllmember() + "人)");


    }

    private void setupVoteStatus(String info) {
        //设置投票的信息
        setupColumnarGrow(info, false);
        if (info.equals("-1")) {//无投票权
            mDataBinding.llVote.setVisibility(View.GONE);
            mDataBinding.unLlVote.setVisibility(View.VISIBLE);
            mDataBinding.tvVoteOver.setVisibility(View.VISIBLE);
            mDataBinding.tvVoteOver.setText("您当前不拥有投票权(具体原因可联系客服)");
        } else if (!info.equals("0")) {//不具有投票权并且无投票权的时候，代表当时已经投票完成
            setupVoteResult(info);
        }
    }

    private void setupVoteResult(String info) {
        String voteInfo = getVoteInfo(info);
        mDataBinding.llVote.setVisibility(View.GONE);
        mDataBinding.unLlVote.setVisibility(View.VISIBLE);
        mDataBinding.tvVoteOver.setVisibility(View.VISIBLE);
        String str = "您已完成投票：您投的是" + voteInfo;
        SpannableString spanText = new SpannableString(str);
        spanText.setSpan(new ForegroundColorSpan(Color.RED), 11, str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mDataBinding.tvVoteOver.setText(spanText);
    }

    /*  public void onClickVoteSuc() {//支持票
                onClickVote("1");
            }

            public void onClickVoteOppose() {//反对票
                onClickVote("2");
            }

            public void onClickVoteWaiver() {//弃权票
                onClickVote("3");
            }*/

    /**
     * 设置当前投票的数量占总人数的百分比
     *
     * @param info        投票的类型
     * @param isClickVote 是否是点击了投票
     */
    private void setupColumnarGrow(String info, boolean isClickVote) {
        String type = getVerifyType();
        int currentNum = Integer.decode(mBean.getAllmember());
        int sucNum = getSucNum(type);
        int opposeNum = getOpposeNum(type);
        int waiverNum = getWaiverNum(type);
        if (isClickVote) {
            switch (info) {
                case "1":
                    sucNum++;
                    mDataBinding.rectangleSupport.setProgress(((int) ((((float) sucNum) / (float) currentNum) * 100)));
                    break;
                case "2":
                    opposeNum++;
                    mDataBinding.rectangleOpposition.setProgress(((int) ((((float) opposeNum) / (float) currentNum) * 100)));
                    break;
                case "3":
                    waiverNum++;
                    mDataBinding.rectangleWairer.setProgress(((int) ((((float) waiverNum) / (float) currentNum) * 100)));
                    break;

            }
        } else {
            mDataBinding.rectangleSupport.setProgress((int) (((((float) sucNum) / (float) currentNum) * 100)));
            mDataBinding.rectangleOpposition.setProgress((int) (((((float) opposeNum) / (float) currentNum) * 100)));
            mDataBinding.rectangleWairer.setProgress((int) (((((float) waiverNum) / (float) currentNum) * 100)));
        }

    }

    /**
     * 获取当前的弃权票
     *
     * @param type
     * @return
     */
    private int getWaiverNum(String type) {
        String waiverNum = "0";
        switch (type) {
            case "1":
                waiverNum = mBean.getF_vote_wairer();
                break;
            case "2":
                waiverNum = mBean.getS_vote_wairer();
                break;
            case "3":
                waiverNum = mBean.getT_vote_wairer();
                break;
        }
        return TextUtils.isEmpty(waiverNum) ? 0 : Integer.decode(waiverNum);
    }

    /**
     * 获取反对票
     *
     * @param type
     * @return
     */
    private int getOpposeNum(String type) {
        String opposeNum = "0";
        switch (type) {
            case "1":
                opposeNum = mBean.getF_vote_opposition();
                break;
            case "2":
                opposeNum = mBean.getS_vote_opposition();
                break;
            case "3":
                opposeNum = mBean.getT_vote_opposition();
                break;
        }
        return TextUtils.isEmpty(opposeNum) ? 0 : Integer.decode(opposeNum);
    }

    /**
     * 获取当前投票支持票的人数
     *
     * @param type
     * @return
     */
    private int getSucNum(String type) {
        String sucNum = "0";
        switch (type) {
            case "1":
                sucNum = mBean.getF_vote_support();
                break;
            case "2":
                sucNum = mBean.getS_vote_support();
                break;
            case "3":
                sucNum = mBean.getT_vote_support();
                break;
        }
        return TextUtils.isEmpty(sucNum) ? 0 : Integer.decode(sucNum);
    }

    /**
     * 根据当前的状态获取投票的类型（初审，复审，终审）
     *
     * @return
     */
    private String getVerifyType() {
        switch (mBean.getStatusCode()) {
            case Constant.VOTE_FIRST_STATUS:
                return "1";
            case Constant.VOTE_SEND_STATUS:
                return "2";
            case Constant.VOTE_THIRD_STATUS:
                return "3";
        }
        return "";
    }

    /**
     * 获取投票的类型（支持票，反对票，弃权票）
     *
     * @param info
     * @return
     */
    private String getVoteInfo(String info) {
        switch (info) {
            case "1":
                return "支持票";
            case "2":
                return "反对票";
            case "3":
                return "弃权票";
            default:
                return "";
        }
    }

    public interface SetupDateCallback {
        void onDateCallback();
    }
}

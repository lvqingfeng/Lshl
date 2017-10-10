package com.lshl.ui.dscs.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lshl.R;
import com.lshl.base.BaseFragment;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.DscsProjectDetailsBean;
import com.lshl.databinding.FragmentProjectDetailsBinding;
import com.lshl.databinding.ImageBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;

import static com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity.PREVIEW_RANDOM_TYPE;

/**
 * 项目的进度
 */
public class ProjectProgressFragment extends BaseFragment<FragmentProjectDetailsBinding> {

    private DscsProjectDetailsBean mDetailsBean;
    private OnClickGoCallBack mCallBack;
    private StartUploadDataCallback mDataCallback;

    private int mSelectPosition;
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;


    public interface OnClickGoCallBack {

        void onClickGoVote();

        void onClickGoMoneyInfo();

        void onClickFeedback();

        void onClickGoGuarantorList();

        void onClickExecutivePersonList();

    }

    public interface StartUploadDataCallback {
        void onUploadCallback();
    }

    public static ProjectProgressFragment newInstance(DscsProjectDetailsBean bean) {
        ProjectProgressFragment fragment = new ProjectProgressFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        fragment.setArguments(args);
        return fragment;
    }

    public class Presenter {
        public void onClickShowMoneyInfo() {
            mCallBack.onClickGoMoneyInfo();
        }

        public void onClickFeedback() {
            mCallBack.onClickFeedback();
        }

        public void onClickGuarantorList() {
            mCallBack.onClickGoGuarantorList();
        }

        public void onClickExecutivePersonList() {
            mCallBack.onClickExecutivePersonList();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (OnClickGoCallBack) context;
        mDataCallback = (StartUploadDataCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDetailsBean = (DscsProjectDetailsBean) getArguments().getSerializable("bean");
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
        return R.layout.fragment_project_details;
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(new Presenter());
        mDataBinding.setProjectBean(mDetailsBean);

        initImages();
        initVoteStatus();

        mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_first:
                        mSelectPosition = 0;
                        setVoteAllMember(mDataBinding.tvNum);
                        mDataBinding.tvSupport.setText(mDetailsBean.getF_vote_support());
                        mDataBinding.tvOppose.setText(mDetailsBean.getF_vote_opposition());
                        mDataBinding.tvWaiver.setText(mDetailsBean.getF_vote_wairer());
                        break;
                    case R.id.radio_btn_second:
                        mSelectPosition = 1;
                        setVoteAllMember(mDataBinding.tvNum);
                        mDataBinding.tvSupport.setText(mDetailsBean.getS_vote_support());
                        mDataBinding.tvOppose.setText(mDetailsBean.getS_vote_opposition());
                        mDataBinding.tvWaiver.setText(mDetailsBean.getS_vote_wairer());
                        break;
                    case R.id.radio_btn_third:
                        mSelectPosition = 2;
                        setVoteAllMember(mDataBinding.tvNum);
                        mDataBinding.tvSupport.setText(mDetailsBean.getT_vote_support());
                        mDataBinding.tvOppose.setText(mDetailsBean.getT_vote_opposition());
                        mDataBinding.tvWaiver.setText(mDetailsBean.getT_vote_wairer());
                        break;
                }
            }
        };
        mDataBinding.radioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        mDataBinding.tvGoVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onClickGoVote();
            }
        });
        ((RadioButton) mDataBinding.radioGroup.getChildAt(0)).setChecked(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mDataCallback.onUploadCallback();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    public void uploadVoteStatus(DscsProjectDetailsBean bean) {
        mDetailsBean = bean;
        mOnCheckedChangeListener.onCheckedChanged(null, (mDataBinding.radioGroup.getChildAt(mSelectPosition)).getId());
    }

    /**
     * 根据审核的进度，显示不同的投票人数量
     *
     * @param allMember
     */
    private void setVoteAllMember(TextView allMember) {
        String status = mDetailsBean.getStatusCode();
        int statusCode = getInt(status);
        switch (statusCode) {
            case 12:
                allMember.setText(mDetailsBean.getAllmember());
                break;
            case 22:
                if (mDataBinding.radioBtnFirst.isChecked()) {
                    allMember.setText(mDetailsBean.getF_vote_total_number());
                } else {
                    allMember.setText(mDetailsBean.getAllmember());
                }
                break;
            default:
                if (mDataBinding.radioBtnFirst.isChecked()) {
                    allMember.setText(mDetailsBean.getF_vote_total_number());
                } else if (mDataBinding.radioBtnSecond.isChecked()) {
                    allMember.setText(mDetailsBean.getS_vote_total_number());
                } else if (mDataBinding.radioBtnThird.isChecked()) {
                    if (statusCode == 32) {
                        allMember.setText(mDetailsBean.getAllmember());
                    } else {
                        allMember.setText(mDetailsBean.getT_vote_total_number());
                    }
                }
                break;
        }
    }

    /**
     * 初始化项目的图片
     */
    private void initImages() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Intent intent = new Intent(mContext, PhotoShowActivity.class);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PREVIEW_RANDOM_TYPE);
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, (ArrayList<String>) mDetailsBean.getImages());
                startActivity(intent);
            }
        });
        mDataBinding.recyclerView.setAdapter(new SimpleBindingAdapter<ImageBinding, String>(mDetailsBean.getImages(), R.layout.item_layout_image_fill) {
            @Override
            public void onBindViewHolder(ImageBinding binding, int position) {
                binding.setImageUrl(mDetailsBean.getImages().get(position));
            }
        });
    }

    /**
     * 初始化审核的状态（根据审核状态显示不同的内容）
     */
    private void initVoteStatus() {

        String status = mDetailsBean.getStatusCode();

        if (!TextUtils.isEmpty(status)) {

            int statusCode = getInt(status);
            if (statusCode == 12) {
                mDataBinding.radioBtnSecond.setEnabled(false);
                mDataBinding.radioBtnThird.setEnabled(false);
            } else if (statusCode == 22) {
                mDataBinding.radioBtnThird.setEnabled(false);
            }else if (statusCode<12){
                mDataBinding.radioBtnFirst.setEnabled(false);
                mDataBinding.radioBtnSecond.setEnabled(false);
                mDataBinding.radioBtnThird.setEnabled(false);
            }

            if (mDetailsBean.isGood()) {
                if (statusCode > 32) {
                    mDataBinding.llGood.setVisibility(View.VISIBLE);
                    if (statusCode >= 42) {
                        mDataBinding.tvExecutor.setVisibility(View.VISIBLE);
                    }
                    if (statusCode >= 52) {
                        mDataBinding.tvGoodLoan.setVisibility(View.VISIBLE);
                    }
                    if (statusCode >= 62) {
                        mDataBinding.tvReceipt.setVisibility(View.VISIBLE);
                    }
                }
            } else if (mDetailsBean.isHelp()) {
                mDataBinding.llHelp.setVisibility(View.VISIBLE);
                mDataBinding.tvSecurity.setVisibility(View.VISIBLE);
                if (statusCode >= 41) {
                    mDataBinding.tvHelpLoan.setVisibility(View.VISIBLE);
                }
            }

            if (statusCode >= 12 && statusCode <= 32) {
                mDataBinding.tvGoVote.setVisibility(View.VISIBLE);
            }

        }
    }

    private int getInt(String string) {
        try {
            return Integer.decode(string);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int checkIsZero(int num) {
        return num == 0 ? 1 : num;
    }


}

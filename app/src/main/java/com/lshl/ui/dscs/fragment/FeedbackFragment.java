package com.lshl.ui.dscs.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.FragmentFeedbackBinding;

/**
 * 执行回馈
 */
public class FeedbackFragment extends BaseFragment<FragmentFeedbackBinding> {

    private String mId;

    public static FeedbackFragment newInstance(String id) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString("id");
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
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.PUBLIC_WELFARE_BACK + mId);
    }

}

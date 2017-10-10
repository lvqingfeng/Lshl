package com.lshl.ui.dscs.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.FragmentGuarantorInfoBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;

/**
 * 担保信息详情
 */
public class GuarantorInfoFragment extends BaseFragment<FragmentGuarantorInfoBinding> {

    private ArrayList<String> mList;
    private String mImage;

    public static GuarantorInfoFragment newInstance(String image) {
        GuarantorInfoFragment fragment = new GuarantorInfoFragment();
        Bundle args = new Bundle();
        args.putString("image", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImage = getArguments().getString("image");
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
        return R.layout.fragment_guarantor_info;
    }

    @Override
    protected void initViews() {
        mList=new ArrayList<>();
        final String imageUrl = ApiClient.getMutualImage(mImage);
        Glide.with(mContext)
                .load(imageUrl)
                .error(R.mipmap.account_logo)
                .into(mDataBinding.imageView);
       if (!TextUtils.isEmpty(imageUrl)){
           mDataBinding.imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mList.clear();
                   mList.add(imageUrl);
                   Intent intent = new Intent();
                   intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                   intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                   intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
                   intent.setClass(mContext, PhotoShowActivity.class);
                   startActivity(intent);
               }
           });
       }
    }

}

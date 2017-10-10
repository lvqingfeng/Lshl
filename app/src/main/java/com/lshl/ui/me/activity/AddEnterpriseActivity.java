package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.QiyeintroductionBean;
import com.lshl.databinding.ActivityAddEnterpriseBinding;
import com.lshl.ui.me.adapter.GoodsImageEditAdapter;
import com.lshl.ui.me.imagegrid.ImageGridActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AddEnterpriseActivity extends BaseActivity<ActivityAddEnterpriseBinding> {
    private ArrayList<String> mImageList;
    private static final int REQUEST_CODE_GOODS_IMAGE = 0x0000221;
    private static final int MAX_GOODS_IMAGE_COUNT = 6;
    private GoodsImageEditAdapter goodsImageEditAdapter;
    public static final int REQUEST_ADD_ENTERPRISE=0x000121;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AddEnterpriseActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickAddGoodsImg() {
            ImageGridActivity.actionStart(AddEnterpriseActivity.this, MAX_GOODS_IMAGE_COUNT, (ArrayList<String>) mImageList, REQUEST_CODE_GOODS_IMAGE);
        }

        public void nextOperation() {
            if (!TextUtils.isEmpty(mDataBinding.name.getText())) {
                if (!TextUtils.isEmpty(mDataBinding.info.getText())) {
                    if (mImageList.size()<=0){
                        showMessage("请至少要上传一张图片");
                        return;
                    }
                    QiyeintroductionBean bean = new QiyeintroductionBean();
                    bean.setToken(LoginHelper.getInstance().getUserToken());
                    bean.setName(mDataBinding.name.getText().toString());
                    bean.setInfo(mDataBinding.info.getText().toString());
                    EnterpriseAuthenticationActivity.actionStart(AddEnterpriseActivity.this,bean,mImageList,mDataBinding.name.getText().toString(),REQUEST_ADD_ENTERPRISE);
                }else {
                    showMessage("请输入企业简介");
                }
            }else {
                showMessage("请输入企业名称");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("添加新企业", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mImageList = new ArrayList<>();
        goodsImageEditAdapter = new GoodsImageEditAdapter(mImageList);
        mDataBinding.recyclerView.setAdapter(goodsImageEditAdapter);
        goodsImageEditAdapter.setOnImageNumChangeCallback(new GoodsImageEditAdapter.OnImageNumChangeCallback() {
            @Override
            public void onChangeCallback(int num, int position) {
                if (num < MAX_GOODS_IMAGE_COUNT && mDataBinding.llAddPhoto.getVisibility() == View.GONE) {
                    mDataBinding.llAddPhoto.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GOODS_IMAGE) {
            if (resultCode == RESULT_OK) {
                mImageList.clear();
                List<String> mList = data.getStringArrayListExtra(ImageGridActivity.RESULT_DATA);
                if (mList.size() == MAX_GOODS_IMAGE_COUNT) {
                    mDataBinding.llAddPhoto.setVisibility(View.GONE);
                }
                mImageList.addAll(mList);
                goodsImageEditAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode==REQUEST_ADD_ENTERPRISE){
            if (resultCode==RESULT_OK){
                finish();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_enterprise;
    }
}

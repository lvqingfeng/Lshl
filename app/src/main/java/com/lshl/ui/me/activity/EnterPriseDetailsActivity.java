package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.BusinessDetailsBean;
import com.lshl.databinding.ActivityEnterPriseDetailsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/*****
 * 企业详情
 */
public class EnterPriseDetailsActivity extends BaseActivity<ActivityEnterPriseDetailsBinding> {
    private String enid;
    private String en_uid;
    private List<String> mList;
    private EnterpriseImageAdapter enterpriseImageAdapter;
    private ArrayList<String> mImages;
    private final int EDIT_REQUEST = 0x000123;
    private BusinessDetailsBean.InfoBean info;

    public static void actionStart(Activity activity, String enid) {
        Intent intent = new Intent(activity, EnterPriseDetailsActivity.class);
        intent.putExtra("enid", enid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("企业详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mImages = new ArrayList<>();
        enid = getIntent().getStringExtra("enid");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditEnterpriseActivity.actionStart(EnterPriseDetailsActivity.this, info, EDIT_REQUEST);
            }
        });
        /*****
         * 企业招聘的点击事件
         * */
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecruitActivity.actionStart(EnterPriseDetailsActivity.this, enid);
            }
        });
        /*****
         * 企业发布产品的点击事件
         * */
        mDataBinding.btnGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProductActivity.actionStart(EnterPriseDetailsActivity.this, enid);
            }
        });
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).bubinessDetails(enid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BusinessDetailsBean>() {

                    @Override
                    public void onNext(BusinessDetailsBean result) {
                        if (result != null) {
                            info = result.getInfo();
                            en_uid = result.getInfo().getEn_uid();
                            if (!result.getInfo().getEn_status().equals("2") && en_uid.equals(LoginHelper.getInstance().getUserBean().getUid())) {
                                mDataBinding.llRecruit.setVisibility(View.VISIBLE);
                            }
                            mDataBinding.code.setText(info.getEn_deadline());
                            mDataBinding.name.setText(info.getEn_name());
                            mDataBinding.address.setText(info.getEn_address());
                            mDataBinding.faren.setText(info.getRealname());
                            mDataBinding.money.setText(info.getEn_capital());
                            mDataBinding.type.setText(info.getEn_type());
                            mDataBinding.time.setText(info.getEn_establish());
                            mDataBinding.fanwei.setText(info.getEn_branched());
                            mDataBinding.jieshao.setText(info.getEn_info());
                            String[] split = info.getEn_img().split(",");
                            if (split.length > 0) {
                                for (int i = 0; i < split.length; i++) {
                                    mList.add(split[i]);
                                }
                                enterpriseImageAdapter.notifyDataSetChanged();
                            }
                            if (info.getEn_status().equals("2") && en_uid.equals(LoginHelper.getInstance().getUserBean().getUid())) {
                                getRightView().setText("编辑");
                            }
                        }
                    }
                }));

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        enterpriseImageAdapter = new EnterpriseImageAdapter(mList);
        mDataBinding.recyclerView.setAdapter(enterpriseImageAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mImages.clear();
                if (mList.size() > 0) {
                    for (int i = 0; i < mList.size(); i++) {
                        mImages.add(ApiClient.getEnterPriseImage(mList.get(i)));
                    }
                    Intent intent = new Intent();
                    intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                    intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                    intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                    intent.setClass(mContext, PhotoShowActivity.class);
                    startActivity(intent);
                }
            }
        });
        mDataBinding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = info.getUid();
                HxMemberDetailsActivity.actionStart(EnterPriseDetailsActivity.this, "", uid, false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter_prise_details;
    }
}

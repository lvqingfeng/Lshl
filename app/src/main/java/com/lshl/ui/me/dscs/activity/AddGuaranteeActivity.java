package com.lshl.ui.me.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityAddGuaranteeBinding;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.dscs.adapter.AddGuaranteeAdapter;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddGuaranteeActivity extends BaseActivity<ActivityAddGuaranteeBinding> {

    private List<AddGuaranteeAdapter.GuaranteeBean> mListData;
    private AddGuaranteeAdapter mAdapter;

    public static void actionStart(Activity activity, ArrayList<AddGuaranteeAdapter.GuaranteeBean> listData, int requestCode) {
        Intent intent = new Intent(activity, AddGuaranteeActivity.class);
        intent.putParcelableArrayListExtra("list", listData);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = getIntent().getParcelableArrayListExtra("list");
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("添加担保人", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mAdapter = new AddGuaranteeAdapter();
        mListData.add(new AddGuaranteeAdapter.GuaranteeBean());
        mAdapter.setListData(mListData);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()+2==mListData.size()){
                    DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此担保人?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            mListData.remove(vh.getAdapterPosition());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    Toast.makeText(mContext, "不能删除", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListData.size()>1){
                    finish();
                }else {
                    Toast.makeText(mContext, "至少添加一个担保人", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_guarantee;
    }

    @Override
    public void finish() {
        ArrayList<AddGuaranteeAdapter.GuaranteeBean> resultList = (ArrayList<AddGuaranteeAdapter.GuaranteeBean>) mAdapter.getListData();
        resultList.remove(resultList.size() - 1);
        setResult(RESULT_OK, new Intent().putParcelableArrayListExtra("result_bean", resultList));
        super.finish();
    }
}

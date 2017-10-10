/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lshl.ui.info.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.AddContactBean;
import com.lshl.databinding.ActivityAddContactBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.adapter.AddNewContactAdapter;
import com.lshl.ui.me.activity.QRcodeActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******
 * 添加好友
 */
public class AddContactActivity extends BaseActivity<ActivityAddContactBinding> implements SpringView.OnFreshListener, View.OnClickListener {
    private boolean isEnd = true;
    private AddNewContactAdapter mAdapter;
    private List<AddContactBean.InfoBean.ContactListBean> mListData;
    private List<AddContactBean.InfoBean.ContactListBean> contactListBean;
    private boolean allPersonFlag = false;
    private boolean sameIndustryPersonFlag = false;
    private boolean sameAreaPersonFlag = false;
    private boolean nearlyPersonFlag = false;
    private int key = 0;
    private int all = 0;
    private int industry = 0;
    private int origin = 0;
    private int nearby = 0;
    private int p = 1;
    private String keySearch;
    private String newMember;

    public static void actionStart(Activity activity, String addContactFlag) {
        Intent intent = new Intent(activity, AddContactActivity.class);
        intent.putExtra("addContactFlag", addContactFlag);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        String strAdd = getResources().getString(R.string.add_friend);
        setTextTitleView(strAdd, DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        setRightViewText("二维码", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRcodeActivity.actionStart(AddContactActivity.this);
            }

        });
    }

    private void getAddContact(int key, int all, int industry, int origin,
                               int nearby, String info, int p) {
        //获取推荐的人
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .getAddContact(LoginHelper.getInstance().getUserToken(), key, all, industry, origin, nearby, info, p), new ProgressSubscriber<AddContactBean>(mContext,
                new SubscriberOnNextListener<AddContactBean>() {
                    @Override
                    public void onNext(AddContactBean result) {
                        if (result.getStatus() == 1) {
                            isEnd = result.getInfo().getEnd() == 1;
                            contactListBean.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }

                    }
                }));
    }

    @Override
    protected void initViews() {
        newMember = getIntent().getStringExtra("addContactFlag");
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        loadListData(1);
        contactListBean = new ArrayList<>();
        mDataBinding.recyclerContactList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerContactList.addItemDecoration(new DividerGridItemDecoration(mContext));
        mAdapter = new AddNewContactAdapter(mContext, contactListBean, "", "");
        mDataBinding.recyclerContactList.setAdapter(mAdapter);
        mDataBinding.recyclerContactList.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerContactList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HxMemberDetailsActivity.actionStart((Activity) mContext, "", mAdapter.getItem(vh.getLayoutPosition()).getUid(), false);
            }
        });

        mDataBinding.btAllPerson.setOnClickListener(this);
        mDataBinding.btSameindustryPerson.setOnClickListener(this);
        mDataBinding.btSameareaPerson.setOnClickListener(this);
        mDataBinding.btNearlyPerson.setOnClickListener(this);

        mDataBinding.editSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    keySearch = mDataBinding.editSearchContent.getText().toString().trim();
                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher m = pattern.matcher(keySearch);
                    if (m.matches() && keySearch.length() <= 6) {
                        Toast.makeText(AddContactActivity.this, "请输入至少六位查询数字", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        contactListBean.clear();
                        getAddContact(1, all, industry, origin, nearby, keySearch, p);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected void loadListData(int page) {
        if (all == 1) {
            getAddContact(1, 1, 0, 0, 0, "", page);
        } else if (nearby == 1) {
            getAddContact(1, 0, 0, 0, 1, "", page);
        } else if (industry == 1) {
            getAddContact(1, 0, 1, 0, 0, "", page);
        } else if (origin == 1) {
            getAddContact(1, 0, 0, 1, 0, "", page);
        } else if (nearby == 1 || origin == 1 || industry == 1) {
            getAddContact(1, 0, 0, 0, 0, "", page);
        } else {
            if (!TextUtils.isEmpty(newMember) && newMember.equals("moreNewMember")) {
                getAddContact(1, 1, 0, 0, 0, "", page);
            } else {
                getAddContact(0, 0, 0, 0, 0, "", page);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_all_person:
                allPersonFlag = true;
                sameIndustryPersonFlag = false;
                sameAreaPersonFlag = false;
                nearlyPersonFlag = false;
                mDataBinding.btAllPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                mDataBinding.btSameindustryPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                mDataBinding.btSameareaPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                mDataBinding.btNearlyPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                mDataBinding.btSameindustryPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                mDataBinding.btSameareaPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                mDataBinding.btNearlyPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                key = 1;
                all = 1;
                industry = 0;
                origin = 0;
                nearby = 0;
                keySearch = "";
                p = 1;
                break;
            case R.id.bt_sameindustry_person:
                allPersonFlag = false;
                all = 0;
                key = 1;
                if (sameIndustryPersonFlag == false) {
                    sameIndustryPersonFlag = true;
                    mDataBinding.btSameindustryPerson.setBackgroundResource(R.drawable.bg_blue_add_contact);
                    mDataBinding.btSameindustryPerson.setTextColor(getResources().getColor(R.color.bottom_text_color_normal, null));
                    industry = 1;

                } else {
                    sameIndustryPersonFlag = false;
                    mDataBinding.btSameindustryPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                    mDataBinding.btSameindustryPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                    industry = 0;
                }
                break;
            case R.id.bt_samearea_person:
                allPersonFlag = false;
                all = 0;
                key = 1;
                if (sameAreaPersonFlag == false) {
                    sameAreaPersonFlag = true;
                    mDataBinding.btSameareaPerson.setBackgroundResource(R.drawable.bg_blue_add_contact);
                    mDataBinding.btSameareaPerson.setTextColor(getResources().getColor(R.color.bottom_text_color_normal, null));
                    origin = 1;
                } else {
                    sameAreaPersonFlag = false;
                    mDataBinding.btSameareaPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                    mDataBinding.btSameareaPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                    origin = 0;
                }
                break;
            case R.id.bt_nearly_person:
                allPersonFlag = false;
                all = 0;
                key = 1;
                if (nearlyPersonFlag == false) {
                    nearlyPersonFlag = true;
                    mDataBinding.btNearlyPerson.setBackgroundResource(R.drawable.bg_blue_add_contact);
                    mDataBinding.btNearlyPerson.setTextColor(getResources().getColor(R.color.bottom_text_color_normal, null));
                    nearby = 1;
                    key = 1;
                } else {
                    nearlyPersonFlag = false;
                    mDataBinding.btNearlyPerson.setBackgroundResource(R.drawable.bg_white_add_contact);
                    mDataBinding.btNearlyPerson.setTextColor(getResources().getColor(R.color.textBlackColor, null));
                    nearby = 0;
                    key = 0;
                }
                break;
        }
        if (!TextUtils.isEmpty(keySearch)) {
            key = 1;
        }
        contactListBean.clear();
        getAddContact(key, all, industry, origin, nearby, keySearch, p);
    }
}

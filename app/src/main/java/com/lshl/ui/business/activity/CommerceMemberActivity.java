package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.CommerceMemberBean;
import com.lshl.databinding.ActivityCommerceMemberBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.adapter.MemberHAdapter;
import com.lshl.ui.business.adapter.MemberPAdapter;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.PinYinUtils;
import com.lshl.view.MyIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/****
 * 商会会员列表
 */
public class CommerceMemberActivity extends BaseActivity<ActivityCommerceMemberBinding>
        implements MyIndexBar.OnIndexBarChangeListener {
    private List<CommerceMemberBean.InfoBean.HBean> hList;
    private List<CommerceMemberBean.InfoBean.PBean> pList;
    private MemberHAdapter memberHAdapter;
    private String bid;
    private MemberPAdapter memberPAdapter;
    private List<String> list;
    private String[] names;
    private LinearLayoutManager manager;

    public static void actionStart(Activity activity, String bid) {
        Intent intent = new Intent(activity, CommerceMemberActivity.class);
        intent.putExtra("bid", bid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("商会成员", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        bid = getIntent().getStringExtra("bid");
        mDataBinding.myIndexBar.setOnIndexBarChangeListener(this);
        LinearLayoutManager hManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.tvSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDataBinding.hRecycler.setLayoutManager(hManager);
        mDataBinding.hRecycler.addItemDecoration(new DividerGridItemDecoration(mContext));
        hList = new ArrayList<>();
        memberHAdapter = new MemberHAdapter(hList, CommerceMemberActivity.this);
        mDataBinding.hRecycler.setAdapter(memberHAdapter);
        manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.hRecycler.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.hRecycler) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = hList.get(vh.getLayoutPosition()).getUid();
                HxMemberDetailsActivity.actionStart(CommerceMemberActivity.this, "", uid, false);
            }
        });
        mDataBinding.pRecycler.setLayoutManager(manager);
        mDataBinding.pRecycler.addItemDecoration(new DividerGridItemDecoration(mContext));
        pList = new ArrayList<>();
        setListData(pList);
        memberPAdapter = new MemberPAdapter(CommerceMemberActivity.this, pList);
        mDataBinding.pRecycler.setAdapter(memberPAdapter);
        loadListData(1);
        if (list != null) {
            if (list.size() > 0) {
                Collections.sort(list);
            }
        }
        mDataBinding.pRecycler.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.pRecycler) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = pList.get(vh.getLayoutPosition()).getUid();
                HxMemberDetailsActivity.actionStart(CommerceMemberActivity.this, "", uid, false);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMemberList(bid), new ProgressSubscriber<CommerceMemberBean>(mContext, new SubscriberOnNextListener<CommerceMemberBean>() {
            @Override
            public void onNext(CommerceMemberBean result) {
                if (result != null) {
                    hList.addAll(result.getInfo().getH());
                    memberHAdapter.notifyDataSetChanged();
                    pList.addAll(result.getInfo().getP());
                    list = new ArrayList<>();
                    names = new String[pList.size()];
                    for (int i = 0; i < pList.size(); i++) {
                        list.add(pList.get(i).getRealname());
                        names[i] = pList.get(i).getRealname();
                    }
                    memberPAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    private Handler handler
            = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mDataBinding.tvHh.setVisibility(View.GONE);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commerce_member;
    }

    @Override
    public void onIndexChange(String word) {
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            if (word.equals(PinYinUtils.GetPinyin(name).charAt(0) + "")) {//如果当前对象的首字母和传递回来的一致
                int currentLastPosition = manager.findLastVisibleItemPosition();
                int currentFirstPosition = manager.findFirstVisibleItemPosition();
                if (i > currentLastPosition) {
                    int total = currentLastPosition - currentFirstPosition;
                    mDataBinding.pRecycler.getLayoutManager().scrollToPosition(i + total - 1);
                } else {
                    mDataBinding.pRecycler.getLayoutManager().scrollToPosition(i);
                }
                mDataBinding.tvHh.setVisibility(View.VISIBLE);
                mDataBinding.tvHh.setText(word);
                handler.removeCallbacksAndMessages(null);//发送之前先移除之前所有发送的消息,防止突然莫名其妙消失了
                handler.sendEmptyMessageDelayed(1, 3000);//三秒后隐藏界面
                return;//一定要 return 防止 for 循环继续执行,导致滚动到的不是第一个而是最后一个
            }
        }
    }

}

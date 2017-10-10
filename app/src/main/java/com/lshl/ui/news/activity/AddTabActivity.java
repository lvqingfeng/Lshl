package com.lshl.ui.news.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.NewsTabBean;
import com.lshl.databinding.ActivityAddTabBinding;
import com.lshl.databinding.NewsTabItemBinding;
import com.lshl.db.NewsTabDao;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemListener;
import com.lshl.ui.news.adapter.AddTabAdapter;
import com.lshl.utils.NewsTabItemTouchHelperCallback;
import com.lshl.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加管理频道的Activity
 */
public class AddTabActivity extends BaseActivity<ActivityAddTabBinding> implements NewsTabItemTouchHelperCallback.OnDragListener {

    private NewsTabDao mNewsTabDao;

    private List<NewsTabBean> mNewsTabList;
    private List<NewsTabBean> mNewsSelectedList;
    private List<NewsTabBean> mNewsUnselectedList;
    private AddTabAdapter mSelectedAdapter;
    private SimpleBindingAdapter<NewsTabItemBinding, NewsTabBean> mUnSelectedAdapter;

    private ItemTouchHelper mItemTouchHelper;

    public static void actionStart(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), AddTabActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {

        mNewsTabDao = new NewsTabDao();
        mNewsTabList = mNewsTabDao.getNewsTabList();

    }

    @Override
    protected void setupTitle() {
        setTextTitleView("频道管理", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        if (!LshlApplication.getApplication().isUploadNewsTab()) {
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).uploadNewsTab()
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<List<NewsTabBean>>>() {
                        @Override
                        public void onNext(HttpResult<List<NewsTabBean>> result) {
                            List<NewsTabBean> list = result.getInfo();
                            for (int i = 0; i < list.size(); i++) {
                                NewsTabBean bean = list.get(i);
                                for (NewsTabBean beanOld : mNewsTabList) {
                                    if (bean.equals(beanOld)) {
                                        list.remove(bean);
                                        i = -1;
                                        break;
                                    }
                                }
                            }
                            mNewsTabDao.saveNewsTabList(list);
                            mNewsTabList.clear();
                            mNewsTabList = mNewsTabDao.getNewsTabList();
                            LshlApplication.getApplication().setUploadNewsTab(true);
                            initNewsTab();
                        }
                    })
            );
        } else {
            initNewsTab();
        }

    }

    private void initNewsTabItem() {
        mNewsSelectedList = new ArrayList<>();
        mNewsUnselectedList = new ArrayList<>();
        mNewsSelectedList.addAll(mNewsTabDao.getAddNewsTabList());

        for (NewsTabBean newsTabBean : mNewsTabList) {
            if (!newsTabBean.isAdd()) {
                mNewsUnselectedList.add(newsTabBean);
            }
        }

    }

    private void initNewsTab() {
        initNewsTabItem();
        initRecyclerView();
        //已选频道的点击事件
        mDataBinding.recyclerSelected.addOnItemTouchListener(new OnRecyclerItemListener(mDataBinding.recyclerSelected) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                NewsTabBean bean = mNewsSelectedList.remove(position);
                mSelectedAdapter.notifyItemRemoved(position);
                mSelectedAdapter.notifyDataSetChanged();
                if (bean != null) {
                    bean.setAdd(false);
                    bean.setPosition(-1);
                    mNewsTabDao.updateNewsTab(bean.getId(), bean);
                    int itemCount = mUnSelectedAdapter.getItemCount();
                    mNewsUnselectedList.add(bean);
                    mUnSelectedAdapter.notifyItemInserted(itemCount);
                }/**/
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != 0) {
                    mItemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(AddTabActivity.this, 70);   //震动70ms
                }
            }
        });
        //未选频道的点击事件
        mDataBinding.recyclerUnselected.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerUnselected) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                NewsTabBean bean = mNewsUnselectedList.remove(position);
                mUnSelectedAdapter.notifyItemRemoved(position);
                if (bean != null) {
                    bean.setAdd(true);
                    bean.setPosition(mSelectedAdapter.getItemCount());
                    mNewsTabDao.updateNewsTab(bean.getId(), bean);
                    int itemCount = mSelectedAdapter.getItemCount();
                    mNewsSelectedList.add(bean);
                    mSelectedAdapter.notifyItemInserted(itemCount);
                }
            }
        });
    }

    private void initRecyclerView() {
        setupRecycler();


        mSelectedAdapter = new AddTabAdapter(mNewsSelectedList);
        mUnSelectedAdapter = new SimpleBindingAdapter<NewsTabItemBinding, NewsTabBean>(mNewsUnselectedList, R.layout.item_layout_news_tab) {
            @Override
            public void onBindViewHolder(NewsTabItemBinding binding, int position) {
                binding.setBean(mNewsUnselectedList.get(position));
            }
        };

        mItemTouchHelper = new ItemTouchHelper(new NewsTabItemTouchHelperCallback(mSelectedAdapter).setOnDragListener(this));
        mItemTouchHelper.attachToRecyclerView(mDataBinding.recyclerSelected);

        mDataBinding.recyclerSelected.setAdapter(mSelectedAdapter);
        mDataBinding.recyclerUnselected.setAdapter(mUnSelectedAdapter);
    }

    /**
     * 设置选择状态的RecyclerView和未选中的RecyclerView的一些基本属性
     */
    private void setupRecycler() {
        mDataBinding.recyclerSelected.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDataBinding.recyclerUnselected.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDataBinding.recyclerSelected.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                int position = lp.getViewLayoutPosition();
                if (position % 3 != 0 || position == 0) {
                    outRect.set(0, 0, 18, 0);
                }
            }
        });
        mDataBinding.recyclerUnselected.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                int position = lp.getViewLayoutPosition();
                if (position % 3 != 0 || position == 0) {
                    outRect.set(0, 0, 18, 0);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_tab;
    }

    @Override
    public void onFinishDrag() {
        mSelectedAdapter.notifyDataSetChanged();
    }
}

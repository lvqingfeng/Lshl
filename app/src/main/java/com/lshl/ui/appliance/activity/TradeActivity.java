package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lshl.LoadBannerHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.LoadImageHolder;
import com.lshl.base.LshlApplication;
import com.lshl.bean.BannerBean;
import com.lshl.bean.GoodsOrderBean;
import com.lshl.bean.TradeGoodsListBean;
import com.lshl.bean.TradeGoodsTypeBean;
import com.lshl.databinding.ActivityTradeBinding;
import com.lshl.databinding.TradeGoodsSearchBarBinding;
import com.lshl.iml.AppBarStateChangeListener;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.TradeGoodsListAdapter;
import com.lshl.ui.appliance.adapter.TradeGoodsTypeSelectAdapter;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.activity.EditGoodsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.news.activity.NewsInfoActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddPopWindow;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.GoodsTypeSelectPopWindow;
import com.lshl.view.SelectMenuPopWindow;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 应用界面----自贸区
 */
public class TradeActivity extends BaseActivity<ActivityTradeBinding> implements XRecyclerView.LoadingListener {

    // private MyRefreshHandler mRefreshHandler;

    private List<TradeGoodsListBean.ListBean> mListData;
    private List<TradeGoodsTypeBean.InfoBean> mGoodsTypeData;//商品类型的数据源

    private String mGoodsType;
    private String mCityNo;
    private String mSearchInfo = "";
    private String mGoodsOrder;

    private AddressSelectPopWindow mAddressPop;
    private AddPopWindow mSelectGoodsTypePop;
    private AddPopWindow mSelectGoodsOrderPop;
    private AddPopWindow mSearchGoodsPop;

    private SelectMenuPopWindow mSelectMenuGoodsTypePopWindow;
    private SelectMenuPopWindow mSelectMenuGoodsOrderPopWindow;

    private boolean isEnd;

    private TradeGoodsTypeSelectAdapter mGoodsTypeAdapter;
    private TradeGoodsListAdapter mAdapter;

    private boolean isSearchPopCancel = true;
    private ArrayList<GoodsOrderBean> mGoodsOrderData;
    private boolean isRefesh;
    private static final int ZIMAOQU = 0x000123;
    private GoodsTypeSelectPopWindow goodsTypeSelectPopWindow;

    public static void actionStart(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), TradeActivity.class);
        fragment.startActivity(intent);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TradeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZIMAOQU) {
            if (resultCode == RESULT_OK) {
                onRefreshNow();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataBinding.convenientBanner.startTurning(2500);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        mDataBinding.convenientBanner.stopTurning();
    }

    @Override
    protected void setupTitle() {
        mDataBinding.ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDataBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDataBinding.ivTitleAutoRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDataBinding.radioBtnScreenCity.setText("筛选城市");
                mDataBinding.radioBtnClassify.setText("分类");
                mDataBinding.radioBtnPrice.setText("价位");

                mCityNo = "";
                mGoodsType = "";
                mGoodsOrder = "";

                for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeData) {
                    infoBean.setSelect(false);
                }
                for (GoodsOrderBean bean : mGoodsOrderData) {
                    bean.setSelect(false);
                }

                for (int i = 0; i < mDataBinding.radioGroupScreenLayout.getChildCount(); i++) {
                    View button = mDataBinding.radioGroupScreenLayout.getChildAt(i);
                    if (button instanceof RadioButton) {
                        ((RadioButton) button).setChecked(false);
                    }
                }
                onRefreshNow();
            }
        });
        mDataBinding.ivAutoRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.radioBtnScreenCity.setText("筛选城市");
                mDataBinding.radioBtnClassify.setText("分类");
                mDataBinding.radioBtnPrice.setText("价位");
                mDataBinding.editSearchContent.setText("");
                mCityNo = "";
                mGoodsType = "";
                mGoodsOrder = "";
                mSearchInfo = "";
                for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeData) {
                    infoBean.setSelect(false);
                }
                for (GoodsOrderBean bean : mGoodsOrderData) {
                    bean.setSelect(false);
                }

                for (int i = 0; i < mDataBinding.radioGroupScreenLayout.getChildCount(); i++) {
                    View button = mDataBinding.radioGroupScreenLayout.getChildAt(i);
                    if (button instanceof RadioButton) {
                        ((RadioButton) button).setChecked(false);
                    }
                }
                onRefreshNow();
            }
        });
        mDataBinding.ivTitleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.convenientBanner.stopTurning();
                mSearchGoodsPop.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchContext = mDataBinding.editSearchContent.getText().toString().trim();
                if (TextUtils.isEmpty(searchContext)) {
                    showMessage("输入框为空，请输入");
                    return;
                }
                mSearchInfo = searchContext;
                onRefreshNow();
            }
        });
        mDataBinding.editSearchContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(TradeActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //接下来在这里做你自己想要做的事，实现自己的业务。
                    String searchContext = mDataBinding.editSearchContent.getText().toString().trim();
                    if (TextUtils.isEmpty(searchContext)) {
                        showMessage("输入框为空，请输入");
                    } else {
                        mSearchInfo = searchContext;
                        onRefreshNow();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {

        isUseDefaultTitle = false;

        mGoodsTypeData = new ArrayList<>();
        mAddressPop = new AddressSelectPopWindow(this);
        mSelectMenuGoodsOrderPopWindow = new SelectMenuPopWindow(this);

        initListData();

        //initGoodsTypePop();
        initBottomGoodsTypePop();

        initGoodsOrderPop();

        initSearchGoodsPop();

        getGoodsType();

    }

    private void initBottomGoodsTypePop() {
        goodsTypeSelectPopWindow = new GoodsTypeSelectPopWindow(this);
        goodsTypeSelectPopWindow.setWheelChangeListener(new GoodsTypeSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mDataBinding.radioBtnClassify.setText(cityData);
                mGoodsType = cityNo;
                onRefreshNow();
            }
        });
        goodsTypeSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mSelectMenuGoodsTypePopWindow = new SelectMenuPopWindow(this);
        mSelectMenuGoodsTypePopWindow.setOnClickSelectListener(new SelectMenuPopWindow.OnClickSelectListener() {
            @Override
            public void onClickSelectItem(String itemName) {
                for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeData) {
                    if (infoBean.getGd_name().equals(itemName)) {
                        mDataBinding.radioBtnClassify.setText(itemName);
                        mGoodsType = infoBean.getGd_id();
                        onRefreshNow();
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.recyclerView;
    }

    private void initSearchGoodsPop() {

        mSearchGoodsPop = new AddPopWindow(this, R.layout.layout_pop_trade_goods_search_bar);
        mSearchGoodsPop.setAnimationStyle(R.style.PopWindow_x_anim_style);
        mSearchGoodsPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        final TradeGoodsSearchBarBinding barBinding = TradeGoodsSearchBarBinding.bind(mSearchGoodsPop.getWindowRootView());

        barBinding.editGoodsSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    barBinding.tvGoodsSearch.setText("取消");
                    barBinding.tvGoodsSearch.setTextColor(ContextCompat.getColor(mContext, R.color.indicatorColor));
                    isSearchPopCancel = true;
                } else {
                    barBinding.tvGoodsSearch.setText("搜索");
                    barBinding.tvGoodsSearch.setTextColor(ContextCompat.getColor(mContext, R.color.textBlackColor));
                    isSearchPopCancel = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        barBinding.editGoodsSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onClickInputMethodSearch(barBinding);
                    return true;
                }
                return false;
            }
        });
        barBinding.tvGoodsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchPopCancel)
                    mSearchGoodsPop.closePopupWindow();
                else
                    onClickInputMethodSearch(barBinding);
            }
        });


    }

    private void onClickInputMethodSearch(TradeGoodsSearchBarBinding barBinding) {
        String searchStr = barBinding.editGoodsSearchText.getText().toString();

        if (TextUtils.isEmpty(searchStr)) {
            mSearchInfo = "";
            barBinding.editGoodsSearchText.setHint("请输入您要搜索的商品关键字");
        } else {
            mSearchInfo = searchStr;
        }
        onRefreshNow();
        mDataBinding.convenientBanner.startTurning(2500);
        mSearchGoodsPop.closePopupWindow();
    }


    /**
     * 初始化商品的排序PopWindow
     */
    private void initGoodsOrderPop() {
        mGoodsOrderData = new ArrayList<>();
        String[][] goodsOrderRes = {{"addtime", "发布时间↓"}, {"click ", "点击量↓"}, {"bottom_money ", "老乡价↑"}, {"top_money ", "老乡价↓"}};
        for (String[] strings : goodsOrderRes) {
            GoodsOrderBean bean = new GoodsOrderBean();
            bean.setCode(strings[0]);
            bean.setName(strings[1]);
            mGoodsOrderData.add(bean);
        }
        List<String> orderList = new ArrayList<>();
        for (GoodsOrderBean bean : mGoodsOrderData) {
            orderList.add(bean.getName());
        }
        mSelectMenuGoodsOrderPopWindow.bindingList(orderList);
        mSelectMenuGoodsOrderPopWindow.setOnClickSelectListener(new SelectMenuPopWindow.OnClickSelectListener() {
            @Override
            public void onClickSelectItem(String itemName) {
                for (GoodsOrderBean bean : mGoodsOrderData) {
                    if (bean.getName().equals(itemName)) {
                        mGoodsOrder = bean.getCode();
                        onRefreshNow();
                        break;
                    }
                }
            }
        });
       /* mSelectGoodsOrderPop = new AddPopWindow(this, R.layout.layout_pop_select_goods_type);
        mSelectGoodsOrderPop.setWidth(LshlApplication.sWindowWidth / 3);
        RecyclerView goodsOrder = (RecyclerView) mSelectGoodsOrderPop.getWindowRootView().findViewById(R.id.recycler_goods_type);
        goodsOrder.setLayoutManager(new LinearLayoutManager(mContext));
        final TradeGoodsOrderSelectAdapter goodsOrderAdapter = new TradeGoodsOrderSelectAdapter(mGoodsOrderData);
        goodsOrder.setAdapter(goodsOrderAdapter);
        goodsOrder.addOnItemTouchListener(new OnRecyclerItemClickListener(goodsOrder) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                for (GoodsOrderBean bean : mGoodsOrderData) {
                    bean.setSelect(false);
                }
                GoodsOrderBean bean = mGoodsOrderData.get(vh.getLayoutPosition());
                bean.setSelect(true);
                mDataBinding.radioBtnPrice.setText(bean.getName());
                mGoodsOrder = bean.getCode();
                onRefreshNow();
                goodsOrderAdapter.notifyDataSetChanged();
                mSelectGoodsOrderPop.closePopupWindow();
            }
        });*/
    }


    /**
     * 初始化商品类型的PopWindow
     */
    private void initGoodsTypePop() {


        mSelectGoodsTypePop = new AddPopWindow(this, R.layout.layout_pop_select_goods_type);
        mSelectGoodsTypePop.setHeight(580);
        mSelectGoodsTypePop.setWidth(LshlApplication.sWindowWidth / 3);
        RecyclerView goodsType = (RecyclerView) mSelectGoodsTypePop.getWindowRootView().findViewById(R.id.recycler_goods_type);
        goodsType.setLayoutManager(new LinearLayoutManager(mContext));
        mGoodsTypeAdapter = new TradeGoodsTypeSelectAdapter(mGoodsTypeData);
        goodsType.setAdapter(mGoodsTypeAdapter);
        goodsType.addOnItemTouchListener(new OnRecyclerItemClickListener(goodsType) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

                for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeData) {
                    infoBean.setSelect(false);
                }
                TradeGoodsTypeBean.InfoBean goodsType = mGoodsTypeData.get(vh.getLayoutPosition());
                goodsType.setSelect(true);
                mDataBinding.radioBtnClassify.setText(goodsType.getGd_name());
                mGoodsType = goodsType.getGd_id();
                onRefreshNow();
                mGoodsTypeAdapter.notifyDataSetChanged();
                mSelectGoodsTypePop.closePopupWindow();
            }
        });
    }

    /**
     * 初始化商品列表
     */
    private void initListData() {
        mListData = new ArrayList<>();
        setListData(mListData);
        mAdapter = new TradeGoodsListAdapter(this, mListData);
        initLoadData();
    }

    private void initTitle() {
        mDataBinding.appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {


                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    mDataBinding.titleName.setVisibility(View.GONE);
                    mDataBinding.convenientBanner.startTurning(2500);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mDataBinding.titleName.setVisibility(View.VISIBLE);
                    //关闭轮播
                    mDataBinding.convenientBanner.stopTurning();
                } else if (state == State.OVERLAP) {
                    //重叠状态
                } else {
                    //中间状态
                    mDataBinding.titleName.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void initViews() {

        mDataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditGoodsActivity.actionStart(TradeActivity.this, null, "", EditGoodsActivity.FROM_ADD_GOODS, ZIMAOQU);
            }
        });
        LoadBannerHelper.getBanner("goods", new LoadBannerHelper.InitBannerImage() {
            @Override
            public void setBanner(List<String> mList, final BannerBean bannerBean) {
                if (mList.size() > 0) {
                    mDataBinding.convenientBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {

                        @Override
                        public LoadImageHolder createHolder() {
                            return new LoadImageHolder();
                        }
                    }, mList)
                            .setPageIndicator(new int[]{R.drawable.bg_banner_select, R.drawable.bg_banner_unselect});
                    mDataBinding.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            BannerBean.InfoBean infoBean = bannerBean.getInfo().get(position);
                            if (TextUtils.isEmpty(infoBean.getBn_url())) {
                                return;
                            }
                            switch (infoBean.getBn_url()) {
                                case "shanghui":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        SHDynamDetailsicActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "new_member":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        HxMemberDetailsActivity.actionStart(TradeActivity.this, "", infoBean.getBn_urlid(), false);
                                    }
                                    break;
                                case "xiangmu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        ProjectDetailActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid(), ProjectDetailActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "koubei":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        KouBeiDetailsActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid(), KouBeiDetailsActivity.FROM_APPLICEN);
                                    }
                                    break;
                                case "gongyi":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        DscsProjectDetailsActivity.actionStart(TradeActivity.this, DscsProjectDetailsActivity.FROM_MB, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "huzhu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        DscsProjectDetailsActivity.actionStart(TradeActivity.this, DscsProjectDetailsActivity.FROM_MA, infoBean.getBn_urlid());
                                    }
                                    break;
                                case "zimaoqu":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        GoodsDetailsActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid(), GoodsDetailsActivity.FROM_OTHER);
                                    }
                                    break;
                                case "findhelper":
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        LookHelpDetailsActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid());
                                    }
                                    break;
                                default:
                                    if (!TextUtils.isEmpty(infoBean.getBn_urlid())) {
                                        NewsInfoActivity.actionStart(TradeActivity.this, infoBean.getBn_urlid(), ApiClient.getNewsImage(infoBean.getBn_img()));
                                    }
                                    break;
                            }
                        }
                    });
                } else {
                    mDataBinding.convenientBanner.setBackgroundResource(R.mipmap.account_logo);
                }
            }
        });
        //初始化列表
        initRecyclerView();

        initTitle();

        initOnClick();

        mAddressPop.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mCityNo = cityNo;
                mDataBinding.radioBtnScreenCity.setText(cityData);
                onRefreshNow();
            }
        });
        //设置PopWindow的关闭监听
        setPopDismissListener(mSelectMenuGoodsOrderPopWindow, mAddressPop, mSelectMenuGoodsTypePopWindow);

    }

    /**
     * 初始化筛选按钮的点击事件
     */
    private void initOnClick() {

        mDataBinding.radioBtnScreenCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mAddressPop.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.radioBtnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                goodsTypeSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
                /* int[] location = new int[2];
                v.getLocationOnScreen(location);

                mSelectGoodsTypePop.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] + v.getHeight());*/
//                mSelectMenuGoodsTypePopWindow.showPopupWindow(mDataBinding.getRoot());

            }
        });
        mDataBinding.radioBtnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
               /* int[] location = new int[2];
                v.getLocationOnScreen(location);

                mSelectGoodsOrderPop.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] + v.getHeight());*/
                mSelectMenuGoodsOrderPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });

    }

    /**
     * 获取商品的类型
     */
    private void getGoodsType() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getGoodsType()
                , new Subscriber<TradeGoodsTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mGoodsTypeData = new ArrayList<>();
                    }

                    @Override
                    public void onNext(TradeGoodsTypeBean tradeGoodsTypeBean) {
                        if (tradeGoodsTypeBean.getStatus().equals(ApiService.STATUS_SUC)) {
                            mGoodsTypeData.addAll(tradeGoodsTypeBean.getInfo());
                            List<String> typeName = new ArrayList<>();
                            for (TradeGoodsTypeBean.InfoBean infoBean : mGoodsTypeData) {
                                typeName.add(infoBean.getGd_name());
                            }
                            mSelectMenuGoodsTypePopWindow.bindingList(typeName);
                        }
                    }
                }
        );
    }

    private void initRecyclerView() {
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);

        mDataBinding.recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mDataBinding.recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        mDataBinding.recyclerView.setLoadingListener(this);

        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String gdId = mListData.get(vh.getLayoutPosition() - 1).getGd_id();
                GoodsDetailsActivity.actionStart(TradeActivity.this, gdId, GoodsDetailsActivity.FROM_OTHER);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade;
    }


    /**
     * 获取商品列表
     *
     * @param page
     */
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).tradeGoodsList(mGoodsType, mCityNo, mSearchInfo, mGoodsOrder, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<TradeGoodsListBean>>() {
                    @Override
                    public void onNext(HttpResult<TradeGoodsListBean> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            isEnd = result.getInfo().getEnd() == 1;
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                            mListData.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            if (isRefesh) {
                                isRefesh = false;
                                mDataBinding.recyclerView.refreshComplete();
                            } else {
                                mDataBinding.recyclerView.loadMoreComplete();
                            }
                          /*  mDataBinding.springView.onFinishFreshAndLoad();*/
                        } else {
                            showMessage("数据异常，请重试");
                        }
                    }
                })
        );
    }


    private void setPopDismissListener(PopupWindow... listener) {
        for (PopupWindow popupWindow : listener) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lightOn();
                }
            });
        }
    }

    private void onRefreshNow() {
        initLoadData();
    }

    @Override
    public void onRefresh() {
        isRefesh = true;
        onRefresh(isEnd);
    }

    @Override
    public void onLoadMore() {
        onLoadMore(isEnd);
    }


}

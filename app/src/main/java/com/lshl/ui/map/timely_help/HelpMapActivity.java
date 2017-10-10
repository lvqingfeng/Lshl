package com.lshl.ui.map.timely_help;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.HelpMemberListBean;
import com.lshl.databinding.ActivityHelpMapBinding;
import com.lshl.databinding.EditInputDialogBinding;
import com.lshl.databinding.PopHelpMemberListBinding;
import com.lshl.ui.map.adapter.HelpMemberListAdapter;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ListUtils;
import com.lshl.view.AddPopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class HelpMapActivity extends BaseActivity<ActivityHelpMapBinding> implements RadarSearchListener {

    private BaiduMap mBaiduMap;
    private RadarSearchManager mManager;
    private BaiduMapLocationHelper mLocationHelper;
    private boolean isSelectAll;


    private LatLng mCurrentLocationLatLng;//当前用户位置的参数
    private String mCurrentCityName;//当前用户城市名称

    private AddPopWindow mPopMemberList;
    private PopHelpMemberListBinding mMemberListBinding;

    private Map<String, RadarNearbyInfo> mNearInfoMap;
    //private List<RadarNearbyInfo> mNearByInfoList;
    private List<String> mMemberPhoneList;

    private HelpMemberListAdapter mMemberListAdapter;

    private List<Overlay> mMarkerList;

    public static void actionStart(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), HelpMapActivity.class);
        fragment.startActivity(intent);
    }

    public class Presenter {

        public void onClickOpenMemberList() {
            //开启用于展示救援人员列表的PopWindow
            mPopMemberList.showPopupWindow(mDataBinding.getRoot());
        }

        public void onClickClosePop() {
            mPopMemberList.closePopupWindow();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mDataBinding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mDataBinding.mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mDataBinding.mapView.onDestroy();
        //移除监听
        mManager.removeNearbyInfoListener(this);
      /*  //清除用户信息
        mManager.clearUserInfo();*/
        //释放资源
        mManager.destroy();
        mManager = null;
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("紧急救援", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("搜索", DEFAULT_TITLE_TEXT_COLOR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentLocationLatLng != null) {

                    mMemberListAdapter.cleanData();
                    mNearInfoMap.clear();
                    //构造请求参数，其中centerPt是自己的位置坐标
                    RadarNearbySearchOption searchOption = new RadarNearbySearchOption()
                            .centerPt(mCurrentLocationLatLng)
                            .pageNum(0)
                            .pageCapacity(100)
                            .radius(8000);

                    //发起查询请求
                    boolean flag = mManager.nearbyInfoRequest(searchOption);
                    Log.d("baiduMap", "搜索：" + (flag ? "成功" : "失败"));
                }
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {

        mLocationHelper = BaiduMapLocationHelper.getInstance().init(getApplicationContext());
        mBaiduMap = mDataBinding.mapView.getMap();
        mManager = RadarSearchManager.getInstance();

        mDataBinding.setIsSearchSuc(false);
        mDataBinding.setPresenter(new Presenter());
        mPopMemberList = new AddPopWindow(HelpMapActivity.this, R.layout.layout_pop_help_member_list);

        mMemberPhoneList = new ArrayList<>();
        mNearInfoMap = new HashMap<>();
        mMarkerList = new ArrayList<>();
    }

    @Override
    protected void initViews() {
        initBaiduMap();
        initPopSearchMemberList();


    }

    private void initPopSearchMemberList() {

        mPopMemberList.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mMemberListBinding = PopHelpMemberListBinding.bind(mPopMemberList.getWindowRootView());

        mMemberListBinding.setPresenter(new Presenter());

        mMemberListBinding.recyclerMemberList.addItemDecoration(new DividerGridItemDecoration(mContext));
        mMemberListBinding.recyclerMemberList.setLayoutManager(new LinearLayoutManager(mContext));
        mMemberListAdapter = new HelpMemberListAdapter(mContext);
        mMemberListBinding.recyclerMemberList.setAdapter(mMemberListAdapter);

        mMemberListBinding.btnSendHelpMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HelpMemberListBean.InfoBean> memberList = mMemberListAdapter.getSelectMemberList();
                if (memberList.size() < 1) {
                    Toast.makeText(mContext, "请选择要求救的人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                HelpMemberListBean.InfoBean[] infoArray = new HelpMemberListBean.InfoBean[memberList.size()];
                for (int i = 0; i < memberList.size(); i++) {
                    infoArray[i] = memberList.get(i);
                }
                showSendHelpDialog("请输入您的求救信息", infoArray);
            }
        });
        mMemberListBinding.cbxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = !isSelectAll;
                mMemberListAdapter.setSelectStatus(isSelectAll);
            }
        });

        mMemberListAdapter.setOnClickSelectCallBack(new HelpMemberListAdapter.OnClickSelectCallBack() {
            @Override
            public void onSelect(boolean isSelectAll) {
                Log.d("baiduMap", "开启计算是否全选" + isSelectAll);
                mMemberListBinding.cbxAll.setChecked(isSelectAll);
                HelpMapActivity.this.isSelectAll = isSelectAll;
            }
        });

        mPopMemberList.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                for (Overlay overlay : mMarkerList) {
                    overlay.remove();
                }
                isSelectAll = false;
                mBaiduMap.hideInfoWindow();
                mDataBinding.setIsSearchSuc(false);
                mMemberListAdapter.cleanData();
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                final HelpMemberListBean.InfoBean infoBean = (HelpMemberListBean.InfoBean) bundle.getSerializable("extra");
                if (infoBean != null) {
                    TextView msg = new TextView(mContext);
                    msg.setTextColor(Color.WHITE);
                    msg.setGravity(Gravity.CENTER);
                    msg.setText(infoBean.getPhone() + "\n" + infoBean.getRealname());
                    msg.setBackgroundColor(Color.GRAY);
                    InfoWindow infoWindow = new InfoWindow(msg, marker.getPosition(), -47);
                    mBaiduMap.showInfoWindow(infoWindow);
                    msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showSendHelpDialog("您正在向" + infoBean.getRealname() + "发起求救", infoBean);
                            mBaiduMap.hideInfoWindow();
                        }
                    });
                    return true;
                }
                return false;
            }
        });

    }

    private void initBaiduMap() {
        mManager.addNearbyInfoListener(this);
        mManager.setUserID(LoginHelper.getInstance().getUserBean().getPhone());
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(15);
        mBaiduMap.animateMapStatus(u);//设置地图的缩放
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层
        UiSettings settings = mBaiduMap.getUiSettings();
        settings.setCompassEnabled(true);//设置开启指南针图层
        settings.setOverlookingGesturesEnabled(false);//设置俯视手势为假的

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        mLocationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                Log.d("baiduMap", "查询结果：" + latLng.toString());
                mCurrentLocationLatLng = latLng;
                mCurrentCityName = location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet();
                //上传位置
                RadarUploadInfo info = new RadarUploadInfo();
                info.comments = LoginHelper.getInstance().getUserBean().getRealName();
                info.pt = mCurrentLocationLatLng;
                mManager.uploadInfoRequest(info);
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(0)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(locData);//设置开发者的方向
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.setMapStatus(msu);//将地图的视角引到当前页面位置
            }
        });
        mLocationHelper.locationStart();

    }

    @Override
    public void onGetNearbyInfoList(RadarNearbyResult result,
                                    RadarSearchError error) {
        Log.d("baiduMap", "搜索结果：" + error);
        // TODO Auto-generated method stub


        if (error == RadarSearchError.RADAR_NO_ERROR) {

            mNearInfoMap.clear();


            List<RadarNearbyInfo> infoList = result.infoList;
            //提示用户
            showMessage("已在8000米范围内，查询出救援成员");
            Log.d("baiduMap", "查询结果：" + infoList.size());

            mDataBinding.setIsSearchSuc(true);


            for (RadarNearbyInfo info : infoList) {

                mNearInfoMap.put(info.userID, info);
                mMemberPhoneList.add(info.userID);


            }

            //获取成功，处理数据
        } else {

            //获取失败
            Toast.makeText(mContext, "查询周边失败", Toast.LENGTH_LONG)
                    .show();
        }

        loadMemberList();

    }


    @Override
    public void onGetUploadState(RadarSearchError radarSearchError) {
        Log.d("baiduMap", "上传结果：" + radarSearchError);
    }

    //处理清除的监听
    @Override
    public void onGetClearInfoState(RadarSearchError error) {
        // TODO Auto-generated method stub
        if (error == RadarSearchError.RADAR_NO_ERROR) {
            //清除成功
            Toast.makeText(mContext, "清除位置成功", Toast.LENGTH_LONG)
                    .show();
        } else {
            //清除失败
            Toast.makeText(mContext, "清除位置失败", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_map;
    }


    private void loadMemberList() {
        StringBuilder sb = new StringBuilder();
        ListUtils.appendIdList(mMemberPhoneList, sb);

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .screenResueMemberList(LoginHelper.getInstance().getUserToken(), sb.toString())
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HelpMemberListBean>() {
                    @Override
                    public void onNext(HelpMemberListBean result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            List<HelpMemberListBean.InfoBean> infoList = result.getInfo();
                            for (int i = 0; i < infoList.size(); i++) {

                                HelpMemberListBean.InfoBean infoBean = infoList.get(i);
                                RadarNearbyInfo info = mNearInfoMap.get(infoBean.getPhone());
                                infoBean.setDistance(info.distance);
                                infoBean.setLatitude(info.pt.latitude);
                                infoBean.setLongitude(info.pt.longitude);

                                LatLng latLng = info.pt;
                                Log.d("baiduMap", "坐标点：" + latLng.toString() + "发布时间：" + DateUtils.getDateToString(info.timeStamp.getTime()));
                                //构建Marker图标
                                BitmapDescriptor bitmap = BitmapDescriptorFactory
                                        .fromResource(R.drawable.ease_icon_marka);
                                //构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions option = new MarkerOptions()
                                        .position(latLng)
                                        .icon(bitmap);
                                //在地图上添加Marker，并显示
                                Overlay overlay = mBaiduMap.addOverlay(option);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("extra", infoBean);
                                overlay.setExtraInfo(bundle);
                                mMarkerList.add(overlay);
                            }
                            mMemberListAdapter.addAllData(infoList);
                        }
                    }
                })
        );
    }


    private void showSendHelpDialog(String title, final HelpMemberListBean.InfoBean... helpMembers) {

        final AddPopWindow pop = new AddPopWindow(HelpMapActivity.this, R.layout.layout_dialog_edit_input);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x53000000));
        pop.showAtLocation(mDataBinding.getRoot(), Gravity.CENTER, 0, 0);
        final EditInputDialogBinding binding = EditInputDialogBinding.bind(pop.getWindowRootView());
        binding.tvDialogTitle.setText(title);


        binding.dialogParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose(pop);
            }
        });
        binding.tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose(pop);
            }
        });
        binding.tvDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose(pop);
                String inputStr = binding.editDialogInput.getText().toString();

                sendHelpMessage(inputStr, mCurrentCityName, mCurrentLocationLatLng, helpMembers);
            }
        });

    }

    private void dialogClose(PopupWindow pop) {
        pop.dismiss();
    }

    private void sendHelpMessage(String inputStr, String cityName, LatLng latLng, HelpMemberListBean.InfoBean[] helpMembers) {
        StringBuilder grelist = new StringBuilder();

        appHelpMemberList(helpMembers, grelist);

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .putRescue(LoginHelper.getInstance().getUserToken(), inputStr, cityName, latLng.longitude + "," + latLng.latitude, grelist.toString())
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        try {
                            String resultStr = result.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String staus = object.getString("status");
                            String info = object.getString("info");
                            if (staus.equals(ApiService.STATUS_SUC)) {
                                mPopMemberList.closePopupWindow();
                                HelpListActivity.actionStart(HelpMapActivity.this, info);
                                finish();
                            } else
                                Toast.makeText(mContext, "操作错误：" + info, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    private void appHelpMemberList(HelpMemberListBean.InfoBean[] helpMembers, StringBuilder grelist) {

        int length = helpMembers.length;
        for (int i = 0; i < length; i++) {
            HelpMemberListBean.InfoBean infoBeen = helpMembers[i];
            grelist.append(infoBeen.getUid()).append(";").append(infoBeen.getLongitude()).append(",").append(infoBeen.getLatitude()).append(";").append(infoBeen.getDistance());
            if (i + 1 < length) {
                grelist.append("|");
            }
        }
    }


}

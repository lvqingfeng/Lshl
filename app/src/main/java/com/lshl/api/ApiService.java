package com.lshl.api;

import com.lshl.base.HttpResult;
import com.lshl.bean.*;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 作者：吕振鹏
 * 创建时间：10月11日
 * 时间：18:20
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public interface ApiService {

    String STATUS_SUC = "1";
    String TOKEN_EX = "3";
    //String BASE_URL = "http://114.55.101.151/";
    String BASE_URL = "http://www.lushanghulian.com/";
    /********
     * 单分享的的URl
     */
    String SHARE_URL = BASE_URL + "Api/apply/shareModule/type/";
    /****
     * 联系商会H5
     */
    String SHANGHUI = BASE_URL + "Api/Business/businessAddress/bid/";
    /**
     * 分享-圈子H5
     */
    String SHARE_CIRCLE = BASE_URL + "Api/apply/circle/id/";
    /**
     * 分享-自贸区H5
     */
    String SHARE_GOODS = BASE_URL + "Api/apply/goods/id/";
    /**
     * 分享-项目H5
     */
    String SHARE_PROJECT = BASE_URL + "Api/apply/ProjectPromotion/id/";
    /**
     * 分享-互助H5
     */
    String SHARE_HELP = BASE_URL + "Api/apply/mutualAid/id/";
    /**
     * 分享-公益H5
     */
    String SHARE_GOOD = BASE_URL + "Api/apply/publicWelfare/id/";
    /**
     * 分享-口碑H5
     */
    String SHARE_KB = BASE_URL + "Api/apply/Scandalous/id/";
    /*****
     * 分享-找帮手
     * */

    /****
     * 网站协议H5
     */
    String AGREMENT = BASE_URL + "Api/Apply/WebsiteAgreement/mark/";
    /***
     * 各种H5类型标识
     */
    String USEAGREMENT = "yhxy";
    String GUAnLIZHIDU = "glzd";
    String GONGSIJIESHAO = "gsjs";
    String SHIYONGSHUOMING = "sysm";
    String DISCLAIMER = "mztk";
    String TechnicalSupport = "jszc";
    /**
     * 新闻详情H5
     */
    String NEWS_INFO = BASE_URL + "Api/Journalism/AppNewsDetailsHtml/aid/";
    /****
     * 商汇-商会介绍
     */
    String CommerceIntroduce = BASE_URL + "Api/Business/businessInfo/buid/";
    /***
     * 商汇中企业的详情页接口
     */
    String QIYEDETAILS = BASE_URL + "Api/Business/GetEnterpriseDetails/id/";
    /***
     * 商汇中动态的详情页接口
     */
    String SHDYNAMICDETAILS = BASE_URL + "Api/Business/GetBusinessDynamicDetails/id/";
    /****
     * 商会详情页
     */
    String SHDETAILS = BASE_URL + "Api/Business/GetBusinessDetails/id/";
    /****
     * 公益-项目详情说明(H5)welfare
     */
    String WELFARE = BASE_URL + "Api/MutualBenefit/ProjectInfo/mbid/";
    /****
     * 互助-项目详情说明(H5)mutual
     */
    String MUTUAL = BASE_URL + "Api/MutualAid/ProjectInfo/maid/";
    /****
     * 互助-打款说明(H5)
     */
    String MUTUAL_MONEY = BASE_URL + "Api/MutualAid/MoneyInfo/maid/";
    /****
     * 公益-打款说明 H5
     */
    String WELFARE_MONEY = BASE_URL + "Api/MutualBenefit/MoneyInfo/mbid/";
    /****
     * 公益-回执信息 H5
     */
    String WELFARE_RECEIPT = BASE_URL + "Api/MutualBenefit/ReceiptInfo/mbid/";
    /****
     * 个人动态列表  视频地址
     */
    String DYNAMIC_VIDEO = BASE_URL + "Data/Uploads/dynamicvoide/";
    /***
     * 服务站-服务站说明
     */
    String STATION_INTRODUCE = BASE_URL + "Api/Service/serviceInfo/sid/";
    /***
     * 服务站-服务申明
     */
    String STATION_STATEMENT = BASE_URL + "Api/Service/serviceStatement/sid/";
    /***
     * 服务站-服务详情
     */
    String STATION_FUNCTION_DETAILS = BASE_URL + "Api/Service/serviceDetails/id/";
    /**
     * 公益-打款详情
     */
    String PUBLIC_WELFARE_MONEY_INFO = BASE_URL + "Api/PublicWelfare/playMoneyInfo/pwid/";
    /**
     * 互助-打款详情
     */
    String MUTUAL_AID_MONEY_INFO = BASE_URL + "Api/MutualAid/playMoneyInfo/maid/";
    /**
     * 公益-回馈信息
     */
    String PUBLIC_WELFARE_BACK = BASE_URL + "Api/PublicWelfare/feedbackInfo/pwid/";
    /**
     * 支付-支付宝支付
     */
    String ALIPAY_WEB = BASE_URL + "Api/AlipPay/launchTransaction";
    /*****
     * 缴纳会费
     */
    String PAY_SH = BASE_URL + "Api/Business/chargeNum/buid/";

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @return
     */
    @POST("Api/Index/login")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("phone") String phone, @Field("password") String password);

    /**
     * 提交群的创建信息
     *
     * @param photo 群头像
     * @param gid   群id
     * @param rname 群名称
     * @param rinfo 群简介
     */
    @POST("Api/Hx/AddHx")
    @Multipart
    Observable<ResponseBody> uploadGroupCreate(@Part MultipartBody.Part photo, @Part("gid") RequestBody gid, @Part("rname") RequestBody rname, @Part("rinfo") RequestBody rinfo);

    /**
     * 提交删除群
     *
     * @param gid
     * @return
     */
    @POST("Api/Hx/DelectHx")
    @FormUrlEncoded
    Observable<ResponseBody> deleteGroup(@Field("gid") String gid);

    /**
     * 查找群列表
     *
     * @param info
     * @return
     */
    @GET("Api/Hx/SelectHx")
    Observable<GroupListBean> lookupGroupList(@Query("info") String info, @Query("p") String p);

    /**
     * 通过群组id 获取群组信息
     *
     * @param hxids
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Hx/ByHxidsGetGroupInfo")
    Observable<ResponseBody> getGroupInfo(@Field("hxids") String hxids);

    /**
     * 通过用户id 获取用户信息
     *
     * @param token
     * @param hxids
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Member/ByHxidGetMemberRealname")
    Observable<ResponseBody> getMemberRealname(@Field("token") String token, @Field("hxids") String hxids);

    @FormUrlEncoded
    @POST("Api/Notice/GetLikeFriends")
    Observable<List<FindUserBean>> getFindUserList(@Field("token") String token, @Field("info") String info);

    /**
     * 修改群名称
     *
     * @param gid
     * @param rname
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Hx/UpdataHx")
    Observable<ResponseBody> updateHxGroupName(@Field("gid") String gid, @Field("rname") String rname);


    /**
     * 修改群头像
     */
    @POST("Api/Hx/UpdataGroupImg")
    @Multipart
    Observable<ResponseBody> updateHxGruopHead(@Part MultipartBody.Part photo, @Part("gid") RequestBody gid);

    /**
     * 应用页面下及时雨下的联盟成员
     */
    @POST("Api/Notice/RescueAlliance")
    @FormUrlEncoded
    Observable<ResponseBody> updateMember(@Field("token") String token, @Field("p") int p);

    /**
     * 应用页面下救援联盟求救定位
     */
    @POST("Api/Apply/PutRescue")
    @FormUrlEncoded
    Observable<ForHelpBean> updateForHelp(@Field("p") String p);

    /**
     * 个人页面下及时雨求救信息
     */
    @POST("Api/Rescue/MyPutRescue")
    @FormUrlEncoded
    Observable<ResponseBody> updateQiuJiuList(@Field("token") String token, @Field("p") String p);

    /**
     * 个人页面下及时雨施救信息
     */
    @POST("Api/Rescue/MyGetRescue")
    @FormUrlEncoded
    Observable<ResponseBody> updateShiJiuList(@Field("token") String token, @Field("p") String p);

    /***
     * 判断当前用户是否救援联盟成员
     */
    @POST("Api/Notice/GetByUIdRescueAlliance")
    @FormUrlEncoded
    Observable<ResponseBody> judgeBelongUnion(@Field("token") String token);

    /***
     * 加入救援联盟
     */
    @POST("Api/Member/putRescueAlliance")
    @FormUrlEncoded
    Observable<ResponseBody> joinUnion(@Field("token") String token, @Field("ip") String ip);

    /**
     * 发出求救后筛选是否救援联盟列表
     */
    @POST("Api/Rescue/ScreenRescue")
    @FormUrlEncoded
    Observable<HelpMemberListBean> screenResueMemberList(@Field("token") String gid, @Field("phones") String phones);

    /***
     * 救援人员到场后的操作
     */
    @POST("Api/Rescue/ByPidUpdateReport")
    @FormUrlEncoded
    Observable<RequestBody> presentOperation(@Field("token") String token, @Field("gid") int gid, @Field("status") int status);


    /***
     * 求救详情
     */
    @POST("Api/Rescue/ByPidRescueEvent")
    @FormUrlEncoded
    Observable<QiujiuDetailsBean> qiujiuDetails(@Field("token") String token, @Field("pid") int pid);

    /**
     * 发布救援联盟
     *
     * @param token    用户id
     * @param info     发布的信息
     * @param cityname 城市名称
     * @param jingwei  当前地理坐标
     * @param grelist  求救人的信息
     * @return
     */
    @POST("Api/Rescue/PutRescue")
    @FormUrlEncoded
    Observable<ResponseBody> putRescue(@Field("token") String token, @Field("info") String info, @Field("cityname") String cityname, @Field("jingwei") String jingwei, @Field("grelist") String grelist);

    /**
     * 求救者发出求救之后的救援人员列表
     *
     * @param token
     * @param pid
     * @param p
     * @return
     */
    @GET("Api/Rescue/ByRidGetRescueEvent")
    Observable<HelpWaitGetMemberListBean> getRescueEvent(@Query("token") String token, @Query("pid") String pid, @Query("p") String p);

    /**
     * 救援联盟-是否正在发起求救
     *
     * @param token
     * @return
     */
    @POST("Api/Rescue/IsPutRescue")
    @FormUrlEncoded
    Observable<ResponseBody> rescueIsBeginning(@Field("token") String token);

    /**
     * 救援联盟-求救人员确认救援事件结束
     *
     * @param token
     * @param pid
     * @return
     */
    @POST("Api/Rescue/EditRescue")
    @FormUrlEncoded
    Observable<ResponseBody> earlyEndRescue(@Field("token") String token, @Field("pid") String pid);

    /**
     * 救援联盟-求救者确认救援人员状态
     *
     * @param token
     * @param gid    救援id
     * @param status 1 表示到达 4表示未到
     * @return
     */
    @POST("Api/Rescue/ByGidArrive")
    @FormUrlEncoded
    Observable<ResponseBody> gidArrive(@Field("token") String token, @Field("gid") String gid, @Field("status") String status);

    /**
     * 我的商会-加入商会
     *
     * @param body
     * @return
     */
    @POST("Api/Notice/PutMyBusiness")
    Observable<ReturnResultBean> joinsh(@Body RequestBody body);

    /**
     * 我的商会-未加入的商会
     *
     * @param token
     * @param cityno
     * @return
     */
    @POST("Api/Notice/ByCitynoBusiness")
    @FormUrlEncoded
    Observable<NotJoinBean> noJoinShanghui(@Field("token") String token, @Field("cityno") String cityno);

    /**
     * 我的商会-已加入的商会
     *
     * @param token
     * @return
     */
    @POST("Api/Notice/MyJoinBusinessList")


    @FormUrlEncoded
    Observable<AleradyCommerceBean> aleradyJoinShanghui(@Field("token") String token, @Field("p") String p);

    /**
     * 我的商会-退出商会
     *
     * @param body
     * @return
     */
    @POST("Api/Notice/EditMyBusiness")
    @FormUrlEncoded
    Observable<ReturnResultBean> tuichushanghui(@Body RequestBody body);

    /**
     * 自贸区-列表展示
     *
     * @param goodstype 商品分类
     * @param cityno    城市地区筛选
     * @param info      模糊搜索关键字
     * @param order     排序方式
     * @param p         页码
     * @return
     */
    @GET("Api/Goods/productList")
    Observable<HttpResult<TradeGoodsListBean>> tradeGoodsList(@Query("goodstype") String goodstype, @Query("cityno") String cityno, @Query("info") String info, @Query("order") String order, @Query("p") String p);

    /**
     * 救援联盟-救援人员收到的求救信息
     */
    @POST("/Api/Rescue/PutRescueEventInfo")
    @FormUrlEncoded
    Observable<ShiJiuDetailsBean> qiujiuMessage(@Field("token") String token, @Field("pid") int pid);

    /****
     * 救援成员到场后的操作
     */
    @POST("Api/Rescue/ByPidUpdateReport")
    @FormUrlEncoded
    Observable<ResultInfoBean> arriveOperation(@Field("token") String token, @Field("type") String type,
                                               @Field("gid") int gid, @Field("complaints_info") String complaints_info);

    /****
     * 自贸区我的订单  买家版本
     */
    @POST("Api/Goods/BuyMyOrder")
    @FormUrlEncoded
    Observable<MyOrderBean> updateMyOrder(@Field("token") String token, @Field("p") String p);

    /***
     * 自贸区  我的收藏
     */
    @POST("Api/Goods/myCollectionProduct")
    @FormUrlEncoded
    Observable<MyCollectionBean> updateMyCollection(@Field("token") String string, @Field("p") String p);
    /***
     * 自贸区  我的商城
     * */
    /****
     * 取消订单
     */
    @POST("Api/Goods/BuyCancelOrder")
    @FormUrlEncoded
    Observable<ResultInfoBean> cancelOrder(@Field("token") String token, @Field("goid") String goid, @Field("gdid") String gdid);

    /**
     * 自贸区 产品类型
     */
    @GET("Api/Apply/AndGoodsType")
    Observable<TradeGoodsTypeBean> getGoodsType();

    /**
     * 产品详情
     */
    @POST("Api/Goods/productDetails")
    @FormUrlEncoded
    Observable<GoodsDetailsBean> updateGoodsDetails(@Field("token") String token, @Field("gid") String gdid);

    /****
     * 自贸区-产品收藏
     */
    @POST("Api/Goods/collectionProduct")
    @FormUrlEncoded
    Observable<HttpResult<String>> collectionGoods(@Field("token") String token, @Field("gid") String gdid);

    /**
     * 展览-取消收藏
     *
     * @param token
     * @param gdid
     * @return
     */
    @POST("Api/Goods/cancelCollectionProduct")
    @FormUrlEncoded
    Observable<HttpResult<String>> cancelCollectionGoods(@Field("token") String token, @Field("gid") String gdid);

    /****
     * 自贸区-评论列表
     */
    @POST("/Api/Apply/GoodsCommentLists")
    @FormUrlEncoded
    Observable<GoodsCommentBean> updateGoodsComment(@Field("gdid") String gdid, @Field("p") String p);


    /**
     * 自贸区-预下订单
     * token	string
     * gdid	    int			产品id
     * selluid	int			产品主人uid
     * num	    int			数量
     * arrivetime
     *
     * @return
     */
    @POST("Api/Goods/ScheduledOrder")
    @FormUrlEncoded
    Observable<ResponseBody> scheduledOrder(@Field("token") String token, @Field("gdid") String gdid, @Field("selluid") String selluid, @Field("num") String num, @Field("arrivetime") String arrivetime);

    /**
     * 个人中心-获取会员资本资料
     *
     * @param token
     * @param uid
     * @return
     */
    @POST("Api/Member/getMemberBasicInfo")
    @FormUrlEncoded
    Observable<ResponseBody> getMemberBasicInfo(@Field("token") String token, @Field("uid") String uid);

    /**
     * 展览-发布人所有产品
     *
     * @param uid
     * @param p
     * @return
     */
    @GET("Api/Goods/ckeckAllProduct")
    Observable<HttpResult<MemberGoodsImageBean>> getMemberOtherImage(@Query("uid") String uid, @Query("p") String p);

    /****
     * 自贸区-我的产品列表
     */
    @POST("Api/Goods/myProduct")
    @FormUrlEncoded
    Observable<HttpResult<MyGoodsBean>> updateMyGoodsList(@Field("token") String token, @Field("p") String p);

    /***
     * 自贸区-订单评论
     **/
    @POST("Api/Goods/OdersComment")
    @FormUrlEncoded
    Observable<ResultInfoBean> updateOrderComment(@Field("token") String token, @Field("gdid") String gdid, @Field("goid") String goid, @Field("selluid") String selluid, @Field("info") String info);

    /***
     * 商汇-商会列表
     */
    @POST("Api/Business/businessList")
    @FormUrlEncoded
    Observable<HttpResult<ShangHuiBean>> updateBusinessInfo(@Field("token") String token, @Field("cityno") String cityno, @Field("info") String info, @Field("p") String p);

    /***
     * 商汇-企业列表
     **/
    @POST("Api/Business/enterprisesList")
    @FormUrlEncoded
    Observable<HttpResult<CityQiyeBean>> updateBusinessQiYe(@Field("type") String type, @Field("info") String info, @Field("token") String token, @Field("cityno") String cityno, @Field("p") String p);

    /****
     * 根据筛选的城市cityno获取商会动态
     */
    @POST("Api/Business/GetBusinessDynamic")
    @FormUrlEncoded
    Observable<DongTaiBean> updateBusinessDynamic(@Field("cityno") String cityno, @Field("p") String p);

    /****
     * 捐款列表
     */
    @POST("Api/Contribution/donationLists")
    @FormUrlEncoded
    Observable<YingyongJuanKuanBean> updateJuanKuanList(@Field("p") String p, @Field("type") String type);

    /*****
     * 公益互助-榜单
     */
    @POST("Api/MutualBenefit/RankingDonate")
    @FormUrlEncoded
    Observable<RankBean> updateRankList(@Field("rank") String rank, @Field("p") String p);

    /****
     * 商会企业列表
     */
    @POST("Api/Business/GetBusinessEnterprise")
    @FormUrlEncoded
    Observable<CityQiyeBean> updateSHCompanyList(@Field("buid") String buid, @Field("p") String p);

    /*****
     * 商会动态列表
     */
    @POST("Api/Business/GetBusinessNews")
    @FormUrlEncoded
    Observable<DongTaiBean> updateShDynamicList(@Field("buid") String buid, @Field("p") String p);

    /****
     * 口碑-口碑列表
     */
    @POST("Api/Scandalous/ScandalousList")
    @FormUrlEncoded
    Observable<KouBeiListBean> updateKouBeiList(@Field("token") String token, @Field("p") String p);

    /****
     * 口碑-应用端口碑详情
     */
    @POST("Api/Apply/ScandalousDetails")
    @FormUrlEncoded
    Observable<KouBeiDetailsBean> updateKouBeiDetails(@Field("sid") String sid);

    /****
     * 口碑-评论列表
     */
    @POST("Api/Scandalous/GetScandalousCommentList")
    @FormUrlEncoded
    Observable<KouBeiCommmentBean> updateKouBeicommentList(@Field("token") String token, @Field("sid") String sid, @Field("p") String p);

    /****
     * 口碑  口碑评论
     */
    @POST("Api/Scandalous/PutScandalousComments")
    @FormUrlEncoded
    Observable<ResponseBody> koubeiCommment(@Field("token") String token, @Field("sid") String sid, @Field("anonymous") String anonymous, @Field("cityname") String cityname,
                                            @Field("info") String info, @Field("passive_uid") String passive_uid, @Field("passive_realname") String passive_realname
    );

    /*****
     * 个人动态列表
     **/
    @POST("Api/DynamicComment/GetDynamicList")
    @FormUrlEncoded
    Observable<DynamicBean> updateDynamicList(@Field("token") String token, @Field("p") String p);

    /*****
     * 根据UID查询对应用户的动态列表
     **/
    @POST("Api/DynamicComment/GetDynamicPeopleList")
    @FormUrlEncoded
    Observable<DynamicBean> dynamicPeopleList(@Field("token") String token, @Field("uid") String uid, @Field("p") String p);

    /**
     * 自贸区-删除商品
     *
     * @param token
     * @param gdid
     * @return
     */
    @POST("Api/Goods/DelMyGoods")
    @FormUrlEncoded
    Observable<ResponseBody> deleteGoods(@Field("token") String token, @Field("gdid") String gdid);

    /**
     * 更新编辑的商品
     *
     * @param map
     * @return
     */
    @POST("Api/Goods/releaseProduct")
    @Multipart
    Observable<HttpResult<String>> updateGoods(@PartMap Map<String, RequestBody> bodyMap, @QueryMap Map<String, String> map);

    /**
     * 自贸区-卖家订单列表(卖家版)
     *
     * @param token
     * @param p
     * @return
     */
    @GET("Api/Goods/SellMyOrder")
    Observable<SellOrderBean> sellOrder(@Query("token") String token, @Query("p") String p);

    /**
     * 自贸区-删除订单(买家版)
     *
     * @param token
     * @param goid
     * @return
     */
    @POST("Api/Goods/BuyDelOrder")
    @FormUrlEncoded
    Observable<ResultInfoBean> byOrderDelete(@Field("token") String token, @Field("goid") String goid);

    /***
     * 用户发表个人朋友圈动态
     **/
    @POST("Api/DynamicComment/PutDynamicComment")
    @Multipart
    Observable<HttpResult<String>> sendDynamics(@Part("token") RequestBody token,
                                                @Part("format") RequestBody format,
                                                @Part("dynamic_info") RequestBody dynamic_info,
                                                @PartMap Map<String, RequestBody> bodyMap,
                                                @Part("cityname") RequestBody cityname);

    /***
     * 用户发表个人朋友圈动态
     **/
    @POST("Api/DynamicComment/PutDynamicComment")
    @Multipart
    Observable<HttpResult<String>> sendDynamics(@Part("token") RequestBody token,
                                                @Part("format") RequestBody format,
                                                @Part("dynamic_info") RequestBody dynamic_info,
                                                @Part("cityname") RequestBody cityname);

    /****
     * 互助-我的互助列表
     **/
    @POST("Api/MutualAid/myMutualAidList")
    @FormUrlEncoded
    Observable<MyMutualBean> updateMyMutualList(@Field("token") String token, @Field("p") String p);

    /***
     * 公益-我的公益
     ***/
    @POST("Api/PublicWelfare/myPublicWelfareList")
    @FormUrlEncoded
    Observable<HttpResult<AppliceWelfareBean>> updateMyWelfareList(@Field("token") String token, @Field("p") String p);

    /***
     * 公益-公益列表
     */
    @POST("Api/PublicWelfare/publicWelfareList")
    @FormUrlEncoded
    Observable<HttpResult<AppliceGongYiBean>> updateAppliceWelfareList(@Field("p") String p, @Field("type") String type);

    /***
     * 互助-互助列表
     */
    @POST("Api/MutualAid/mutualAidList")
    @FormUrlEncoded
    Observable<HttpResult<ForHelpBean>> pdateAppliceMutualList(@Field("p") String p, @Field("type") String type);

    /**
     * 公益-详情
     *
     * @return
     */
    @POST("Api/PublicWelfare/publicWelfareDetails")
    @FormUrlEncoded
    Observable<ResponseBody> mbProjectDetails(@Field("pwid") String pwid);

    /**
     * 公益 ---- 是否可以投票
     *
     * @return
     */
    @POST("Api/PublicWelfare/ifClickVote")
    @FormUrlEncoded
    Observable<HttpResult<String>> mbIfClickVote(@Field("token") String token, @Field("mtid") String mtid, @Field("type") String type);

    /**
     * 公益 --- 投票
     *
     * @return
     */
    @POST("Api/PublicWelfare/publicWelfareVote")
    @FormUrlEncoded
    Observable<HttpResult<String>> mbClickVote(@Field("token") String token, @Field("status") String status, @Field("maid") String mbid, @Field("type") String type);


    /**
     * 互助-详情
     *
     * @return
     */
    @POST("Api/MutualAid/mutualAidDetails")
    @FormUrlEncoded
    Observable<ResponseBody> maProjectDetails(@Field("maid") String maid);

    /***
     * 项目推广-项目列表
     */
    @POST("Api/ProjectPromotion/projectList")
    @FormUrlEncoded
    Observable<ProjectBean> updateProjectList(@Field("p") String p, @Field("token") String token);

    /**
     * 互助 ---- 是否可以投票
     *
     * @param token
     * @param type
     * @return
     */
    @POST("Api/MutualAid/ifClickVote")
    @FormUrlEncoded
    Observable<HttpResult<String>> maIfClickVote(@Field("token") String token, @Field("type") String type, @Field("mtid") String mtid);

    /**
     * 互助-投票
     *
     * @param token  string	Y
     * @param type   int	Y		初审1 复审2 终审3
     * @param mbid   int	Y		项目id
     * @param status int	Y		同意传 1 反对 0
     * @return
     */
    @POST("Api/MutualAid/mutualAidVote")
    @FormUrlEncoded
    Observable<HttpResult<String>> maClickVote(@Field("token") String token, @Field("status") String status, @Field("maid") String mbid, @Field("type") String type);

    /****
     * 项目推广-问答列表
     */
    @POST("Api/ProjectPromotion/question_answer")
    @FormUrlEncoded
    Observable<ProjectQuestionBean> updateProjectQuestionList(@Field("pid") String pid, @Field("p") String p);

    /****
     * 项目推广-提问
     */
    @POST("Api/ProjectPromotion/question")
    @FormUrlEncoded
    Observable<ResultInfoBean> projectQuestion(@Field("token") String token, @Field("pid") String pid, @Field("question_info") String question_info, @Field("answer_uid") String answer_uid);

    /****
     * 项目推广-回复
     */
    @POST("Api/ProjectPromotion/answer")
    @FormUrlEncoded
    Observable<ResultInfoBean> projectAnswer(@Field("token") String token, @Field("id") String id, @Field("answer_info") String answer_info);

    /**
     * 公益-执行人捐款信息
     *
     * @param uid
     * @param p
     * @return
     */
    @GET("Api/MutualBenefit/PersonInfo")
    Observable<HttpResult<InvestListBean>> getInvestListBean(@Query("uid") String uid, @Query("p") String p);

    /****
     * 个人中心-实名认证提交
     */
    @POST("Api/Member/realNameAuthentication")
    @Multipart
    Observable<ResultInfoBean> authentication(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /***
     * 获取个人信息
     */
    @POST("Api/Member/getMemberBasicInfo")
    @FormUrlEncoded
    Observable<PersonBasedataBean> updatePersonData(@Field("token") String token, @Field("uid") String uid);

    /****
     * 个人中心-企业列表
     */
    @POST("Api/Member/GetMemberEnterpriseList")
    @FormUrlEncoded
    Observable<EnterpeiseBean> updateMyEnterprise(@Field("token") String token, @Field("uid") String uid);

    /******
     * 口碑-我的口碑 列表
     */
    @GET("Api/Scandalous/MyScandalous")
    Observable<HttpResult<ReputationBean>> personalReputationList(@Query("token") String token, @Query("p") String p);

    /****
     * 口碑-删除评论
     */
    @POST("Api/Scandalous/DelScandalousComment")
    @FormUrlEncoded
    Observable<ResponseBody> deleteComment(@Field("token") String token, @Field("scid") String scid);

    /**
     *
     */
    @POST("Api/Scandalous/ReleaseScandalous")
    @Multipart
    Observable<HttpResult<String>> releaseReputation(@PartMap Map<String, RequestBody> params,
                                                     @QueryMap Map<String, String> body);
  /*  @Part("token") String token,
    @Part("title") String title,
    @Part("info") String info,
    @Part("type") String type,
    @Part("city") String city*/

    /***
     * 口碑-删除口碑
     */
    @POST("Api/Scandalous/DelScandalous")
    @FormUrlEncoded
    Observable<ResponseBody> deleteKoubei(@Field("token") String token, @Field("sid") String sid);

    /**
     * 发送验证码
     */
    @POST("Api/Index/phonecode_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> sendPhoneCode(@Field("phone") String phone);

    /**
     * 找回密码发送验证码
     *
     * @param phone
     * @return
     */
    @POST("Api/Index/getPasswordPhonecode_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> getPasswordPhonecode(@Field("phone") String phone);

    /**
     * 注册
     */
    @POST("Api/Index/register")
    @FormUrlEncoded
    Observable<ResponseBody> startRegister(@Field("phone") String phone, @Field("phonecode") String phonecode, @Field("password") String password);

    /**
     * 找回密码
     */
    @POST("Api/Index/GetBackPasswoed")
    @FormUrlEncoded
    Observable<HttpResult<String>> getBackPassword(@Field("phone") String phone, @Field("password") String password, @Field("phonecode") String phonecode);

    /**
     * 修改密码
     *
     * @param token    token
     * @param oldpwd   旧的密码
     * @param newpwd   新密码
     * @param renewpwd 修改的密码
     * @return
     */
    @POST("Api/Member/UpdatePass")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatePassword(@Field("token") String token, @Field("oldpwd") String oldpwd, @Field("newpwd") String newpwd, @Field("renewpwd") String renewpwd);

    /**
     * 检测token是否正确
     *
     * @param uid
     * @param token
     * @return
     */
    @POST("Api/Index/isToken")
    @FormUrlEncoded
    Observable<ResponseBody> tokenIsOk(@Field("uid") String uid, @Field("token") String token);

    /**
     * 更改手机号
     */
    @POST("Api/Member/updateMemberPhone")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateMemberPhone(@Field("old_phone") String old_phone, @Field("token") String token, @Field("new_phone") String new_phone, @Field("phonecode") String phonecode);

    /**
     * 商会动态列表
     */
    @GET("Api/Business/GetBusinessNews")
    Observable<HttpResult<SHDynamListBean>> getSHDynamList(@Query("buid") String buid, @Query("p") String p);

    /****
     * 工作信息提交(非企业法人)
     */
    @POST("Api/Member/JobFacts")
    @FormUrlEncoded
    Observable<ResponseBody> workMessageCommit(@Field("token") String token, @Field("rd") String rd, @Field("jobtype") String jobtype, @Field("jobname") String jobname, @Field("jobpost") String jobpost, @Field("jobaddress") String jobaddress, @Field("business_scope") String business_scope);

    /****
     * 商汇-企业详情
     */
    @POST("Api/Business/enterprisesDetails")
    @FormUrlEncoded
    Observable<BusinessDetailsBean> bubinessDetails(@Field("enid") String enid);

    /***
     * 个人中心-获取工作信息
     */
    @POST("Api/Member/GetMemberJobInfo")
    @FormUrlEncoded
    Observable<WorkBean> workMessage(@Field("token") String token, @Field("uid") String uid);

    /*****
     * 头像上传
     */
    @POST("Api/Member/uploadByForm")
    @Multipart
    Observable<ResponseBody> postHeadImage(@Part MultipartBody.Part headImage, @Part("token") RequestBody token);

    /****
     * 商汇-商会会员
     */
    @POST("Api/Business/businessMember")
    @FormUrlEncoded
    Observable<CommerceMemberBean> updateMemberList(@Field("bid") String bid);

    /**
     * 个人中心-会员基本信息的提交
     *
     * @param token
     * @param realname        真实姓名
     * @param rd              性别
     * @param age             出生年月日
     * @param origin_cityname 老家城市
     * @param origin_county   老家县区
     * @param origin_address  老家详情地址
     * @param live_cityname   现居城市
     * @param live_county     现居县区（街道）
     * @param live_address    现居详细地址
     * @param live_cityno     城市编号
     * @return
     */
    @POST("Api/Member/basicData")
    @FormUrlEncoded
    Observable<ResponseBody> baseDatacommit(@FieldMap Map<String, String> fieldMap);

    /**
     * 商汇-动态评论列表
     *
     * @param bdid
     * @param p
     * @return
     */
    @GET("Api/Business/businessCommentList")
    Observable<HttpResult<SHDynamCommentBean>> getSHDynamicCommentList(@Query("bdid") String bdid, @Query("p") String p);

    /**
     * 商汇-商会动态评论
     *
     * @return
     */
    @POST("Api/Business/businessComment")
    @FormUrlEncoded
    Observable<HttpResult<String>> sendDynamicComment(@Field("token") String token, @Field("bdid") String bdid, @Field("info") String info);

    /**
     * 商汇-联系商会
     *
     * @return
     */
    @POST("Api/Business/businessContact")
    @FormUrlEncoded
    Observable<HttpResult<String>> businessContact(@Field("token") String token, @Field("bid") String bid, @Field("info") String info);

    /**
     * 我的商会-退出商会
     *
     * @param token
     * @param businessid
     * @return
     */
    @POST("Api/Notice/EditMyBusiness")
    @FormUrlEncoded
    Observable<HttpResult<String>> editMyBusiness(@Field("token") String token, @Field("businessid") String businessid);

    /**
     * 加入商会
     *
     * @param token
     * @param buid
     * @param info
     * @return
     */
    @POST("Api/Business/applyJoinBusiness")
    @FormUrlEncoded
    Observable<HttpResult<String>> applyJoinBusiness(@Field("token") String token, @Field("buid") String buid, @Field("info") String info);

    /**
     * 商会-联系商会列表
     *
     * @param token
     * @param bid
     * @param p
     * @return
     */
    @GET("Api/Business/businessContactList")
    Observable<HttpResult<SHTalkAboutListBean>> getSHTalkAboutList(@Query("token") String token, @Query("bid") String bid, @Query("p") String p);

    /**
     * 服务站-服务站列表
     */
    @POST("Api/Service/serviceStationList")
    @FormUrlEncoded
    Observable<ServiceStationBean> updateServiceList(@Field("info") String info, @Field("cityno") String cityno, @Field("p") String p);

    /***
     * 服务站-投诉
     */
    @POST("Api/Service/serviceComplain")
    @FormUrlEncoded
    Observable<ResponseBody> stationComplaint(@Field("token") String token, @Field("sid") String sid, @Field("info") String info);

    /***
     * 服务站-详情
     */
    @POST("Api/Service/serviceStationDetails")
    @FormUrlEncoded
    Observable<StationDetailsBean> updateStationFunction(@Field("sid") String sid);

    /**
     * 获取朋友圈的图片
     *
     * @return
     */
    @POST("Api/DynamicComment/MyDynamicImgs")
    @FormUrlEncoded
    Observable<HttpResult<List<String>>> getMyDynammicImages(@Field("token") String token, @Field("uid") String uid);

    /**
     * 新闻-新闻列表页
     *
     * @return
     */
    @GET("Api/Journalism/AppNewsLists")
    Observable<HttpResult<NewsListBean>> getNewsListData(@Query("type") String type, @Query("p") String p);

    /**
     * 新闻-详情信息
     *
     * @param aid
     * @return
     */
    @POST("Api/Journalism/AppNewsDetails")
    @FormUrlEncoded
    Observable<HttpResult<NewsInfoBean>> getNewsInfo(@Field("aid") String aid);

    /**
     * 新闻-评论
     *
     * @param token
     * @param aid
     * @param anonymous
     * @param info
     * @return
     */
    @POST("Api/Journalism/AppNewsComment")
    @FormUrlEncoded
    Observable<HttpResult<String>> sendNewsComment(@Field("token") String token, @Field("aid") String aid
            , @Field("anonymous") String anonymous, @Field("info") String info, @Field("address") String address);

    /**
     * 新闻-评论列表
     *
     * @param aid
     * @param p
     * @return
     */
    @GET("Api/Journalism/AppNewsCommentList")
    Observable<HttpResult<NewsCommentBean>> newsCommentList(@Query("aid") String aid, @Query("p") String p);

    /***
     * 服务站-提交建议
     */
    @POST("Api/Service/issueOpinion")
    @FormUrlEncoded
    Observable<ResponseBody> commitOpinion(@Field("token") String token, @Field("sid") String sid, @Field("info") String info);

    /***
     * 项目推广-发起项目
     */
    @POST("Api/ProjectPromotion/LaunchProject")
    @Multipart
    Observable<HttpResult<String>> addproject(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /****
     * 我的财富-财富账号
     */
    @POST("Api/Member/MyWealthAccount/")
    @FormUrlEncoded
    Observable<WealthAccountBean> wealthAccount(@Field("token") String token);

    /***
     * 我的财富-添加我的财富账号
     */
    @POST("Api/Member/WealthAccount")
    @FormUrlEncoded
    Observable<ResponseBody> addWealthAccount(@Field("token") String token, @Field("addtype") String addtype, @Field("zhi") String zhi, @Field("wei") String wei, @Field("bankname") String bankname, @Field("banknum") String banknum, @Field("bankaddree") String bankaddree);

    /**
     * 捐款-资金走向
     */
    @POST("Api/Contribution/fundStatistics")
    @FormUrlEncoded
    Observable<Capitalbean> updateCapital(@Field("") String sha);

    /***
     * 捐款-搜索列表
     */
    @POST("Api/Contribution/ranking")
    @FormUrlEncoded
    Observable<RankingBean> updateRanking(@Field("p") String p, @Field("type") String type, @Field("info") String info, @Field("city") String city, @Field("order") String order);

    /****
     * 捐款-捐款详情
     */
    @POST("Api/Contribution/donationDetails")
    @FormUrlEncoded
    Observable<SearchResultBean> updateSearchResult(@Field("type") String type, @Field("uid") String uid);

    /**
     * 公益-发起公益
     *
     * @param params
     * @param body
     * @return
     */
    @POST("Api/PublicWelfare/launchPublicWelfare")
    @Multipart
    Observable<HttpResult<String>> launchPublicWelfare(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /**
     * 互助-发起互助
     *
     * @param params
     * @param body
     * @return
     */
    @POST("Api/MutualAid/launchMutualAid")
    @Multipart
    Observable<HttpResult<String>> launchMutualAid(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /***
     * 个人动态(朋友圈)点赞
     */
    @POST("Api/DynamicComment/UpdateMemberDynamicThing")
    @FormUrlEncoded
    Observable<HttpResult<String>> dynamicDianzan(@Field("token") String token, @Field("mdid") String mdid, @Field("status") String status);

    /***
     * 个人动态评论
     */
    @POST("Api/DynamicComment/PutComments")
    @FormUrlEncoded
    Observable<ResponseBody> dynamicComment(@Field("token") String token, @Field("mdid") String mdid, @Field("passive_member") String passive_member, @Field("comment_info") String comment_info);

    /****
     * 朋友圈(动态)点赞列表
     */
    @POST("Api/DynamicComment/GetThingList")
    @FormUrlEncoded
    Observable<DianzanListBean> dianzanList(@Field("token") String token, @Field("mdid") String mdid);

    /***
     * 个人动态评论列表
     */
    @POST("Api/DynamicComment/GetDynamicCommentList")
    @FormUrlEncoded
    Observable<DynamicCommentBean> dynamicCommentList(@Field("token") String token, @Field("mdid") String mdid, @Field("p") String p);

    /***
     * 项目推广-删除提问
     */
    @POST("Api/ProjectPromotion/delQuestion")
    @FormUrlEncoded
    Observable<HttpResult<String>> projectQuectionDelete(@Field("token") String token, @Field("id") String id);

    /****
     * 项目推广-删除回复
     */
    @POST("Api/ProjectPromotion/delAnswer")
    @FormUrlEncoded
    Observable<HttpResult<String>> projectAnswerDelete(@Field("token") String token, @Field("id") String id);

    /***
     * 项目推广-删除项目
     */
    @POST("Api/ProjectPromotion/delLaunchProject")
    @FormUrlEncoded
    Observable<HttpResult<String>> projectDelete(@Field("token") String token, @Field("pid") String pid);


    @POST("Api/PublicWelfare/launchPublicWelfare")
    @FormUrlEncoded
    Observable<HttpResult<String>> launchPublicWelfare(@Field("info") String token);

    /***
     * 个人中心-企业简介提交
     */
    @POST("Api/Member/enterprisesBasicInformation")
    @Multipart
    Observable<HttpResult<String>> qiyeDesctrubion(@PartMap Map<String, RequestBody> parms, @QueryMap Map<String, String> body);

    /***
     * 个人中心-企业认证提交
     */
    @POST("Api/Member/enterprisesAuthentication")
    @Multipart
    Observable<HttpResult<String>> qiyeAuthion(@PartMap Map<String, RequestBody> parms, @QueryMap Map<String, String> body);

    /****
     * 项目-项目详情
     */
    @POST("Api/ProjectPromotion/projectDetails")
    @FormUrlEncoded
    Observable<ProjectDetailsBean> updateProjectDetails(@Field("id") String id);

    /**
     * 分享到新闻
     *
     * @param headImage
     * @param title
     * @param content
     * @param onlylabel
     * @param project_id
     * @param uid
     * @return
     */
    @POST("Api/Apply/shareToNews")
    @Multipart
    Observable<HttpResult<String>> shareToNews(@Part MultipartBody.Part headImage,
                                               @Part("title") RequestBody title,
                                               @Part("content") RequestBody content,
                                               @Part("description") RequestBody description,
                                               @Part("onlylabel") RequestBody onlylabel,
                                               @Part("project_id") RequestBody project_id,
                                               @Part("uid") RequestBody uid);

    /**
     * 分享到新闻 纯文本
     *
     * @param headImage
     * @param title
     * @param content
     * @param onlylabel
     * @param project_id
     * @param uid
     * @return
     */
    @POST("Api/Apply/shareToNews")
    @Multipart
    Observable<HttpResult<String>> shareToNewsIsText(
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("description") RequestBody description,
            @Part("onlylabel") RequestBody onlylabel,
            @Part("project_id") RequestBody project_id,
            @Part("uid") RequestBody uid);

    /***********
     * 根据UID查询对应用户的动态列表
     */
    @POST("Api/DynamicComment/GetDynamicPeopleList")
    @FormUrlEncoded
    Observable<DynamicBean> updatePersonDynamic(@Field("token") String token, @Field("p") String p, @Field("uid") String uid);

    /*******
     * Banner-banner
     **/
    @POST("Api/Banner/bannerLists")
    @FormUrlEncoded
    Observable<BannerBean> updateBanner(@Field("typename") String typename);

    /***
     * Banner-商会banner
     */
    @POST("Api/Banner/businessBanner")

    @FormUrlEncoded
    Observable<ShanghuiBannerBean> updateShanghuiBanner(@Field("busid") String busid);

    /****
     * 个人中心-会长说
     */
    @POST("Api/Auth/presidentRelease")
    @Multipart
    Observable<HttpResult<String>> updatePresidentTalk(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /*****
     * 个人中心-发布会长说
     */
    @POST("Api/Auth/presidentRelease")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatePresidentTalktext(@Field("token") String token, @Field("file") File file, @Field("title") String title, @Field("content") String content);

    /*****
     * 个人中心-会长说我的列表
     */
    @POST("Api/Auth/myPresidentList")
    @FormUrlEncoded
    Observable<PresidentListBean> updatePredidentList(@Field("token") String token, @Field("p") String p);

    /****
     * 个人中心-删除会长说
     */
    @POST("Api/Auth/delPresident")
    @FormUrlEncoded
    Observable<HttpResult<String>> deletePresidentTalk(@Field("token") String token, @Field("id") String id);

    /**
     * 检测当前用户的权限
     *
     * @return
     */
    @POST("Api/Auth/authDetails")
    @FormUrlEncoded
    Observable<HttpResult<AuthorityCheckBean>> authortyCheck(@Field("token") String token);

    /**
     * 更新devicetoken
     *
     * @param uid         当前用户的uid
     * @param devicetoken 友盟的deviceToken
     * @return
     */
    @POST("Api/apply/deviceToken")
    @FormUrlEncoded
    Observable<HttpResult<String>> deviceTokenUpload(@Field("uid") String uid
            , @Field("devicetoken") String devicetoken);

    /**
     * 更新新闻频道
     *
     * @return
     */
    @GET("Api/Journalism/JournalismMoudle")
    Observable<HttpResult<List<NewsTabBean>>> uploadNewsTab();

    /**
     * 推送-我的推送列表
     */
    @GET("Api/Push/myPushList")
    Observable<HttpResult<NewNoticeBean>> getMyPushList(@Query("token") String token, @Query("p") String p);

    /*****
     * 网站建议
     */
    @POST("Api/Auth/websiteSuggestions")
    @FormUrlEncoded
    Observable<HttpResult<String>> doSuggest(@Field("token") String token
            , @Field("info") String info);

    /**
     * 点赞-被点赞(给我点赞的人员列表)
     *
     * @param token
     * @param p
     * @return
     */
    @GET("Api/Praise/getPraise")
    Observable<HttpResult<PraiseListBean>> getPraise(@Query("token") String token
            , @Query("p") String p);

    /**
     * 点赞-我点过的赞(列表)
     *
     * @param token
     * @param p
     * @return
     */
    @GET("Api/Praise/putPraise")
    Observable<HttpResult<PraiseListBean>> putPraise(@Query("token") String token
            , @Query("p") String p);

    /****
     * 个人动态(朋友圈)-删除
     */
    @POST("Api/DynamicComment/DelDynamicInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> deletDynamicDetails(@Field("token") String token
            , @Field("mid") String mid);

    /**
     * 点赞的详情
     *
     * @param token
     * @param uid
     * @return
     */
    @POST("Api/Praise/praiseInfo")
    @FormUrlEncoded
    Observable<HttpResult<PraiseBean>> getPraiseInfo(@Field("token") String token
            , @Field("uid") String uid);

    /**
     * 点赞
     *
     * @param token
     * @param cuid
     * @return
     */
    @POST("Api/Praise/doPraise")
    @FormUrlEncoded
    Observable<HttpResult<String>> clickPraise(@Field("token") String token
            , @Field("cuid") String cuid);

    /**
     * 获取担保人列表
     *
     * @param pwid
     * @return
     */
    @POST("Api/MutualAid/guaranteeList")
    @FormUrlEncoded
    Observable<HttpResult<List<GuarantorListBean>>> getGuaranteeList(@Field("maid") String pwid);

    /**
     * 公益列表
     *
     * @param pwid
     * @return
     */
    @POST("Api/PublicWelfare/executivePersonList")
    @FormUrlEncoded
    Observable<HttpResult<List<ExecutivePersonListBean>>> getExecutivePersonList(@Field("pwid") String pwid);

    /****
     * 个人中心-修改常驻地址
     */
    @POST("Api/Member/saveAddress")
    @FormUrlEncoded
    Observable<HttpResult<String>> editNowAddress(@Field("token") String token
            , @Field("cityname") String cityname, @Field("county") String county, @Field("address") String address, @Field("cityno") String cityno);

    /****
     * 圈子 通过评论id删除评论
     */
    @POST("Api/DynamicComment/ByThidDelComment")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteDynamicComment(@Field("token") String token
            , @Field("coid") String coid);

    /**
     * 查看推送，将推送的未读标记改为已读
     */
    @POST("Api/Push/savePushInfoStatus")
    @FormUrlEncoded
    Observable<HttpResult<String>> lookUpPushInfo(@Field("token") String token
            , @Field("pid") String pid);

    /**
     * 获取未读消息数量
     */
    @POST("Api/Push/pushInfoUnread")
    @FormUrlEncoded
    Observable<HttpResult<String>> getPushUnreadNum(@Field("token") String token);

    /***
     * 我的财富-资金列表
     */
    @POST("Api/Money/myMoneyList")
    @FormUrlEncoded
    Observable<FundrecordBean> updateFundRecord(@Field("token") String token
            , @Field("p") String p);

    /****
     * 个人中心-删除企业
     */
    @POST("Api/Member/delEnterprise")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteQiye(@Field("token") String token
            , @Field("enid") String enid);

    /*****
     * 口碑-发布口碑  红榜 纯文本
     */
    @POST("Api/Scandalous/ReleaseScandalous")
    @FormUrlEncoded
    Observable<HttpResult<String>> sendKouBeiHong(@FieldMap Map<String, String> body);


    /**
     * 招贤纳士 ----- 添加工作经历
     *
     * @param token
     * @param company_name      公司名称
     * @param company_starttime 开始时间
     * @param company_endtime   结束时间
     * @param info              工作描述
     * @param company_jobs      工作岗位
     * @return
     */
    @POST("Api/Recruit/launchWorkInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> launchWorkInfo(@Field("token") String token,
                                                  @Field("company_name") String company_name,
                                                  @Field("company_starttime") String company_starttime,
                                                  @Field("company_endtime") String company_endtime,
                                                  @Field("info") String info,
                                                  @Field("company_jobs") String company_jobs);


    /**
     * 招贤纳士 ----- 添加工作经历
     *
     * @param token
     * @param company_name      公司名称
     * @param company_starttime 开始时间
     * @param company_endtime   结束时间
     * @param info              工作描述
     * @param company_jobs      工作岗位
     * @return
     */
    @POST("Api/Recruit/saveWorkInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> saveWorkInfo(@Field("token") String token,
                                                @Field("weid") String weid,
                                                @Field("company_name") String company_name,
                                                @Field("company_starttime") String company_starttime,
                                                @Field("company_endtime") String company_endtime,
                                                @Field("info") String info,
                                                @Field("jobs") String company_jobs);

    @POST("Api/Recruit/workInfoDetails")
    @FormUrlEncoded
    Observable<HttpResult<WorkHistoryInfoBean>> workInfo(@Field("weid") String weid);

    /**
     * 招贤纳士-我的工作经历列表
     *
     * @param uid
     * @param p
     * @return
     */
    @GET("Api/Recruit/workInfoList")
    Observable<HttpResult<WorkHistoryListBean>> workInfoList(@Query("uid") String uid, @Query("p") String p);

    @POST("Api/Recruit/launchRecruit")
    @FormUrlEncoded
    Observable<HttpResult<String>> launchRecruit(@Field("token") String token,
                                                 @Field("school") String school,
                                                 @Field("education") String education,
                                                 @Field("major") String major);

    /*****
     * 好友雷达-列表
     */
    @POST("Api/FriendRadar/radarList")
    @FormUrlEncoded
    Observable<RadarListBean> updateRadarList(@Field("token") String token
            , @Field("p") String p);

    /****
     * 好友雷达-删除
     */
    @POST("Api/FriendRadar/delRadar")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteRadar(@Field("token") String token
            , @Field("pid") String pid);

    /****
     * 好友雷达-发起请求
     */
    @POST("Api/FriendRadar/getCityInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> radarSendRequest(@Field("token") String token
            , @Field("cityno") String cityno
            , @Field("cityname") String cityname);

    /*****
     * 招贤纳士-企业发布招聘
     */
    @POST("Api/Recruit/enLaunchRecruit")
    @FormUrlEncoded
    Observable<HttpResult<String>> releaseRecruit(@Field("token") String token
            , @Field("enid") String enid
            , @Field("title") String title
            , @Field("money") String money
            , @Field("education") String education
            , @Field("info") String info);

    /****
     * 招贤纳士-企业发布的招聘列表(传token为查看自己的)
     */
    @POST("Api/Recruit/recruitList")
    @FormUrlEncoded
    Observable<RecruitListBean> updateRecruList(@Field("token") String token
            , @Field("p") String p
            , @Field("type") String type
            , @Field("info") String info
            , @Field("cityno") String cityno);

    /****
     * 招贤纳士-企业招聘详情
     */
    @POST("Api/Recruit/recruitDetails")
    @FormUrlEncoded
    Observable<RecruitDetailsBean> updateRecruitDetails(@Field("erid") String erid
            , @Field("token") String token);

    /****
     * 招贤纳士-投递简历
     */
    @POST("Api/Recruit/deliveryResume")
    @FormUrlEncoded
    Observable<HttpResult<String>> deliverResume(@Field("token") String token
            , @Field("erid") String erid);

    /*****
     * 招贤纳士-收藏(取消收藏)
     */
    @POST("Api/Recruit/collectionRecruit")
    @FormUrlEncoded
    Observable<HttpResult<String>> followRecruit(@Field("token") String token
            , @Field("weid") String weid, @Field("status") String status);

    /**
     * 招贤纳士-收到的简历
     *
     * @param token
     * @param p
     * @return
     */
    @GET("Api/Recruit/resumeList")
    Observable<HttpResult<DeliveryResumeListBean>> resumeList(@Query("token") String token, @Query("p") String p);

    /**
     * 招贤纳士-查看简历详情
     */
    @POST("Api/Recruit/resumeDetails")
    @FormUrlEncoded
    Observable<HttpResult<JobInfoBean>> resumeDetails(@Field("rid") String rid
            , @Field("uid") String uid);

    /******
     * 招贤纳士-删除发布的招聘信息
     */
    @POST("Api/Recruit/delRecruit")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteRecruit(@Field("token") String token
            , @Field("erid") String erid);

    /******
     * 招贤纳士-删除我投递的简历
     */
    @POST("Api/Recruit/delDeliveryResume")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteResumeDelivery(@Field("token") String token
            , @Field("rid") String rid);

    /****
     * 招贤纳士-删除我收到的简历
     */
    @POST("Api/Recruit/delResume")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteResumeReceived(@Field("token") String token
            , @Field("rid") String rid);

    /**
     * 微信支付-微信下订单
     *
     * @return
     */
    @POST("Api/WxPay/preOrder")
    @FormUrlEncoded
    Observable<HttpResult<PayBean>> preOrder(@Field("token") String token, @Field("money") String money, @Field("type") String type, @Field("nums") String nums);

    /*****
     * 项目推广-编辑项目
     */
    @POST("Api/ProjectPromotion/editLaunchProject")
    @Multipart
    Observable<HttpResult<String>> editProject(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /**
     * 微信支付成功后的回调
     *
     * @param orderno
     * @return
     */
    @POST("Api/WxPay/notifyurl")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatePayStataus(@Field("orderno") String orderno);

    /**
     * 修改商品，带有图片
     *
     * @param params
     * @param body
     * @return
     */
    @POST("Api/Goods/editProduct_an")
    @Multipart
    Observable<HttpResult<String>> editGoodsHasImage(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /**
     * 修改商品，不带图片
     *
     * @param body
     * @return
     */
    @POST("Api/Goods/editProduct_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> editGoodsNoImage(@FieldMap Map<String, String> body);

    /****
     * 企业修改-基本资料修改
     */
    @POST("Api/Member/saveBasicEnterprise")
    @Multipart
    Observable<HttpResult<String>> enterpriseMessageModify(@PartMap Map<String, RequestBody> parms, @QueryMap Map<String, String> body);

    /******
     * 企业修改-基本资料修改(只修改文本 不上传营业执照)
     */
    @POST("Api/Member/saveBasicEnterprise")
    @FormUrlEncoded
    Observable<HttpResult<String>> enterpriseEditText(@Field("enid") String enid
            , @Field("code") String code
            , @Field("address") String address
            , @Field("cityno") String cityno
            , @Field("type") String type
            , @Field("capital") String capital
            , @Field("establish") String establish
            , @Field("deadline") String deadline
            , @Field("branched") String branched, @Field("name") String name
            , @Field("token") String token);

    /*****
     * 我的资金-删除记录
     */
    @POST("Api/Money/delMoneyList")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteMoney(@Field("token") String token
            , @Field("dmid") String dmid);

    /*****
     * 企业修改-详细信息修改(介绍和企业图片)安卓
     */
    @POST("Api/Member/saveDetailEnterprise_an")
    @Multipart
    Observable<HttpResult<String>> EditEnterpriseImage(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /*****
     * 企业修改-详细信息修改(介绍和企业图片)安卓(不改变图片)
     */
    @POST("Api/Member/saveDetailEnterprise_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> EditEnterprise(@FieldMap Map<String, String> body);

    /**
     * 项目-编辑项目，带有图片
     *
     * @param params
     * @param body
     * @return
     */
    @POST("Api/ProjectPromotion/editLaunchProject_an")
    @Multipart
    Observable<HttpResult<String>> editLaunchProjectHasImage(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /**
     * 项目-编辑项目，不带图片
     *
     * @param body
     * @return
     */
    @POST("Api/ProjectPromotion/editLaunchProject_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> editLaunchProjectNoImage(@FieldMap Map<String, String> body);

    /******
     * 我的财富-互助还款
     */
    @POST("Api/Money/myMutualRepayment")
    @FormUrlEncoded
    Observable<MutualRepayBean> updateMutualRepay(@Field("token") String token);

    @POST("Api/Goods/delMyProduct")
    @FormUrlEncoded
    Observable<HttpResult<String>> delMyGoods(@Field("token") String token
            , @Field("gid") String gid);

    /****
     * 一切为了打款。。。互助-互助详情
     */
    @POST("Api/MutualAid/mutualAidDetails")
    @FormUrlEncoded
    Observable<MutualDetailsBean> updateMutual(@Field("maid") String maid);

    /****
     * 一切为了打款。。。公益-公益详情。。。你开心就好
     */
    @POST("Api/PublicWelfare/publicWelfareDetails")
    @FormUrlEncoded
    Observable<WelfareDetailsBean> updateWelfare(@Field("pwid") String pwid);

    /*****
     * 找帮手-列表(包括个人)
     */
    @POST("Api/FindHelper/findHelperList")
    @FormUrlEncoded
    Observable<LookHelpListBean> updateLookHelpList(@Field("token") String token
            , @Field("p") String p
            , @Field("cityno") String cityno);

    /*******
     * 找帮手-发出找帮手  图文
     */
    @POST("Api/FindHelper/launchFindHelper")
    @Multipart
    Observable<HttpResult<String>> sendLookHelpImage(@PartMap Map<String, RequestBody> params,
                                                     @QueryMap Map<String, String> body);

    /*******
     * 找帮手-发出找帮手  纯文本
     */
    @POST("Api/FindHelper/launchFindHelper")
    @FormUrlEncoded
    Observable<HttpResult<String>> sendLookHelp(@FieldMap Map<String, String> body);

    /******
     * 找帮手-详情
     */
    @POST("Api/FindHelper/findHelperDetails")
    @FormUrlEncoded
    Observable<LookHelpDetailsBean> updateLookHelpDetails(@Field("fid") String fid);

    /*****
     * 找帮手-删除
     */
    @POST("Api/FindHelper/delFindHelper")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteLookHelp(@Field("token") String token
            , @Field("fhid") String fhid);

    /*****
     * 找帮手-评论列表
     */
    @POST("Api/FindHelper/question_answer")
    @FormUrlEncoded
    Observable<LookHelpCommentBean> updateLookHelpCommentList(@Field("fid") String fid
            , @Field("p") String p);

    /*****
     * 找帮手-评论
     */
    @POST("Api/FindHelper/question")
    @FormUrlEncoded
    Observable<HttpResult<String>> lookHelpComment(@Field("token") String token
            , @Field("fid") String fid
            , @Field("question_info") String question_info
            , @Field("answer_uid") String answer_uid);

    /******
     * 找帮手-回复
     */
    @POST("Api/FindHelper/answer")
    @FormUrlEncoded
    Observable<HttpResult<String>> lookHelpAnswer(@Field("token") String token
            , @Field("id") String id
            , @Field("answer_info") String answer_info);

    /*****
     * 找帮手-删除评论
     */
    @POST("Api/FindHelper/delQuestion")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteLookHelpComment(@Field("token") String token
            , @Field("id") String id);

    /*******
     * 找帮手-删除回复
     */
    @POST("Api/FindHelper/delAnswer")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteLookHelpAnswer(@Field("token") String token
            , @Field("id") String id);

    /*****
     * 招贤纳士-删除工作经历
     */
    @POST("Api/Recruit/delworkInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteJob(@Field("token") String token, @Field("weid") String weid);

    /******
     * 个人中心-获取会员资本资料
     */
    @POST("Api/Member/getMemberBasicInfo")
    @FormUrlEncoded
    Observable<PersonBasedataBean> updatePersonBaseData(@Field("token") String token
            , @Field("uid") String uid
            , @Field("hxid") String hxid);

    /*****
     * 消息-推荐好友
     */
    @POST("Api/Hx/recommendFriends")
    @FormUrlEncoded
    Observable<RecommendFriendsBean> updateRecommendList(@Field("token") String token
            , @Field("p") String p);

    /******
     * 支付宝支付-APP支付
     */
    @POST("Api/AliApp/preOrder")
    @FormUrlEncoded
    Observable<PayZfbBean> updatePaymentResult(@Field("token") String token
            , @Field("type") int type
            , @Field("money") String money
            , @Field("nums") String nums);

    /******
     * 图标
     */
    @POST("Api/Auth/headPortrait")
    @FormUrlEncoded
    Observable<IconBean> updateIcon(@Field("uid") String uid, @Field("hxid") String hxid);
    /*******
     * 查看是否是商会会员
     * */
    /******
     * 照片墙-提交 278
     */
    @POST("Api/Auth/image_wall")
    @Multipart
    Observable<HttpResult<String>> updateImageForImageWall(@PartMap Map<String, RequestBody> params
            , @Field("token") String token);

    /******
     * 照片墙-安卓上传
     */
    @POST("Api/Auth/image_wall_an")
    @Multipart
    Observable<HttpResult<String>> updateImageWall(@PartMap Map<String, RequestBody> params, @QueryMap Map<String, String> body);

    /****
     * 照片墙-安卓上传  删除图片没有添加新图片
     */
    @POST("Api/Auth/image_wall_an")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteImageWall(@Field("token") String token, @Field("old_img") String old_img, @Field("del_img") String del_img);

    /****
     * 照片墙-展示
     */
    @POST("Api/Auth/imageWallShow")
    @FormUrlEncoded
    Observable<ImageWallListBean> updateImageWallList(@Field("uid") String uid);

    /****
     * 行业分类
     */
    @POST("Api/Banner/industryModule")
    @FormUrlEncoded
    Observable<IndustryBean> updateIndustry(@Field("") String id);

    /****
     * 设置行业
     */
    @POST("Api/Banner/addIndustry")
    @FormUrlEncoded
    Observable<HttpResult<String>> setIndustry(@Field("token") String token, @Field("industry") String industry);

    /*****
     * 我的行业
     */
    @POST("Api/Banner/myIndustry")
    @FormUrlEncoded
    Observable<IndustryBean> updateMyIndustry(@Field("token") String token);

    /*****
     * 更新经纬度
     */
    @POST("Api/apply/jinWei")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatejingwei(@Field("uid") String uid, @Field("jinwei") String jinwei);

    /*******
     * 互助-互助详情
     */
    @POST("Api/MutualAid/mutualAidDetails")
    @FormUrlEncoded
    Observable<HuzhuDetailsBean> updateHUzhu(@Field("maid") String maid);

    /*****
     * 公益-公益详情
     */
    @POST("Api/PublicWelfare/publicWelfareDetails")
    @FormUrlEncoded
    Observable<GongYiDetailsBean> updateGOngyi(@Field("pwid") String pwid);

    /******
     * 商会详情   分享专用
     */
    @POST("Api/business/businessInfoYs/")
    @FormUrlEncoded
    Observable<ShanghuiDetailsBean> updateshanghuiDetails(@Field("busid") String busid);

    /******
     * 微信支付-支付成功后返回
     */
    @POST("Api/WxPay/notifyurl")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateweixinResule(@Field("orderno") String orderno);

    /******
     * 友情链接
     */
    @POST("Api/FriendshipLink")
    @FormUrlEncoded
    Observable<YouqingBean> updateyouqing(@Field("") String haha);

    /*****
     * 设置电话号码是否显示
     */
    @POST("Api/FriendshipLink/saveHideAccount")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatehidePhone(@Field("token") String token, @Field("status") String status);

    /******
     * 朋友圈背景默认图列表
     */
    @GET("Api/FriendshipLink/defaultDynamicBackgroundImage")
    Observable<DefaultImageBean> updateDefaultBackGroundImage();

    /*****
     * 设置朋友圈背景图  上传默认图片
     */
    @POST("Api/FriendshipLink/saveDynamicBackgroundImage")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateDefaultImage(@Field("token") String token, @Field("img") String img);

    /*****
     * 设置朋友圈背景图  上传本地图片
     */
    @POST("Api/FriendshipLink/saveDynamicBackgroundImage")
    @Multipart
    Observable<HttpResult<String>> updateDeImage(@Part MultipartBody.Part headImage, @Part("token") RequestBody token);

    /****
     * 谁看过我-看别人
     */
    @POST("Api/FriendshipLink/readMe")
    @FormUrlEncoded
    Observable<HttpResult<String>> lookMeDetails(@Field("token") String token, @Field("uid") String uid);

    /****
     * 谁看过我-列表
     */
    @POST("Api/FriendshipLink/getReadMe")
    @FormUrlEncoded
    Observable<LookMeBean> updateLookMeList(@Field("token") String token, @Field("p") String p);

    /******
     * 谁看过我-查看详情请求接口
     */
    @POST("Api/FriendshipLink/clickSaveReadNum")
    @FormUrlEncoded
    Observable<HttpResult<String>> commitLookMe(@Field("token") String token);

    /******
     * 更换手机号-发送验证码
     */
    @POST("Api/Index/savePhoneGetCode")
    @FormUrlEncoded
    Observable<HttpResult<String>> newSendVerification(@Field("phone") String phone);

    /******
     * 动态新通知
     */
    @POST("Api/DynamicComment/dynamicNewNotice")
    @FormUrlEncoded
    Observable<DynamicNewsInfo> updateDynamicNewsInfo(@Field("token") String token, @Field("p") String p);

    /******
     * 点击清除数量提醒
     */
    @POST("Api/DynamicComment/clickDel")
    @FormUrlEncoded
    Observable<HttpResult<String>> cleanDynamicNewsInfo(@Field("token") String token);

    /*****
     * 通过动态id查看动态详情
     */
    @POST("Api/DynamicComment/byMdidGetInfo_an")
    @FormUrlEncoded
    Observable<DynamicBean> updateDynamicDetails(@Field("token") String token, @Field("mdid") String mdid);

    /*****
     * 删除通知记录
     */
    @POST("Api/DynamicComment/delDynamicNewNotice")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteDynamicNewsinfos(@Field("token") String token, @Field("mdid") String mdid);

    /******
     * 新闻-删除评论(后台发布，无跳转的类型，接口129中 comment =0 是用此接口)
     */
    @POST("Api/Journalism/DelAppNewsComment")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleNewsinfoComment(@Field("token") String token, @Field("amid") String amid);

    /**
     * 版本控制-检查更新
     */
    @POST("Api/Auth/codeInfo")
    @FormUrlEncoded
    Observable<UpdateAppBean> checkVersionUpdate(@Field("key") String key);

    /******
     * 推送-删除
     */
    @POST("Api/Push/delPushInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteNewNotice(@Field("token") String token, @Field("pid") String pid);

    /******
     * 查看是否是商会会员
     */
    @POST("Api/Auth/isAddBusiness")
    @FormUrlEncoded
    Observable<NewNoticeShBean> updateSHNewNotice(@Field("uid") String uid, @Field("busid") String busid);

    /*****
     * 二期改版-首页
     */
    @GET("Api/Journalism/homePage")
    Observable<HomePageBean> updateHomePageList();

    /*****
     * 二期改版-鲁商，通知等
     */
    @POST("Api/Journalism/systemInfo")
    @FormUrlEncoded
    Observable<LuShangBean> updateLuShangList(@Field("type") String type, @Field("p") String p);

    /*****
     * 推送-未读数量
     */
    @POST("Api/Push/pushInfoUnread")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateTuisongNum(@Field("token") String token);

    /**
     * 二期改版-获取添加好友推荐列表
     *
     * @param token
     * @param key
     * @param all
     * @param industry
     * @param origin
     * @param nearby
     * @param info
     * @param p
     * @return
     */
    @GET("Api/Hx/friends")
    Observable<AddContactBean> getAddContact(@Query("token") String token, @Query("key") int key,
                                             @Query("all") int all, @Query("industry") int industry,
                                             @Query("origin") int origin, @Query("nearby") int nearby,
                                             @Query("info") String info, @Query("p") int p);
    /****
     * 二期招聘-发布招聘(248作废)
     * */
    @POST("Api/Recruit/enLaunchRecruit")
    @FormUrlEncoded
    Observable<HttpResult<String>>sendRecruit(@Field("token")String token
            ,@Field("title")String title
            ,@Field("enid")String enid
            ,@Field("min_money")String min_money
            ,@Field("max_money")String max_money
            ,@Field("education")String education
            ,@Field("info")String info
            ,@Field("cityname")String cityname
            ,@Field("cityno")String cityno
            ,@Field("phone")String phone
            ,@Field("demand")String demand);
    /*****
     * 二期招聘-招聘列表(249作废)
     * */
    @POST("Api/Recruit/recruitList")
    @FormUrlEncoded
    Observable<RecruitBean>updateRrcruitList(@Field("enid")String enid,@Field("info")String info,@Field("cityno")String cityno,@Field("p")String p);
    /*****
     * 二期修改-招聘详情(251作废)
     * */
    @POST("Api/Recruit/recruitDetails")
    @FormUrlEncoded
    Observable<RecruitDetailBean>updateRecruitDetail(@Field("erid")String erid);
    /*****
     * 二期改版-谁查看了卖家的产品
     * */
    @POST("Api/goods/collectionMyGood")
    @FormUrlEncoded
    Observable<GoodUnLookBean>updateGoodUnLook(@Field("gd_id")String gd_id);
    /*****
     * 二期修改-企业承接页
     * */
    @POST("Api/Business/undertakePage")
    @FormUrlEncoded
    Observable<CompanySecondBean>updateCompanySecondList(@Field("enid")String enid);
    /*****
     * 二期-企业的产品列表
     * */
    @POST("Api/Goods/enterpriseGoods")
    @FormUrlEncoded
    Observable<MyProductBean>updateMyProduct(@Field("enid")String enid,@Field("p")String p);
    /*****
     * 好友通讯录-是否好友
     * */
    @POST("Api/Hx/isMember")
    @FormUrlEncoded
    Observable<MailFriendBean>updateMailFriendList(@Field("phones")String phones,@Field("uid")String uid);
    /*****
     *通讯录邀请
     * */
    @POST("Api/Hx/invitation")
    @FormUrlEncoded
    Observable<HttpResult<String>>addFriend(@Field("phone")String phone,@Field("uid")String uid);
}

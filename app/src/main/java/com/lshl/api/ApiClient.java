package com.lshl.api;

import android.content.Context;
import android.util.Log;

import com.lshl.camera.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 作者：吕振鹏
 * 创建时间：08月27日
 * 时间：10:01
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class ApiClient {

    /**
     * 获取请求请求网络用的Service
     *
     * @param tClass
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getApiService(Class<T> tClass, RetrofitManager.RetrofitType type) {
        return RetrofitManager.getInstance().getRetrofit(type).create(tClass);
    }

    public static String getBaseImage(String imageName) {
        return ApiService.BASE_URL + imageName;
    }

    public static String getPictureCode() {
        return ApiService.BASE_URL + "Api/Index/Appverify";
    }

    //环信群组的拼接地址
    public static String getHxGroupImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/HxGroup/" + imageName;
    }
    //找帮手
    public static String getLookHelpImage(String imageName){
        return ApiService.BASE_URL+"Data/Uploads/FindHelper/"+imageName;
    }
    //默认背景图片
    public static String getDefaultImage(String imageName){
        return ApiService.BASE_URL+"Static/Image/"+imageName;
    }
    //企业
    public static String getEnterPriseImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/enterprises/" + imageName;
    }
    //照片墙
    public static String getImageWallImage(String imageName){
        return ApiService.BASE_URL+"Data/Uploads/ImageWall/"+imageName;
    }
    //热门服务
    public static String getServiceImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/service/" + imageName;
    }

    //公益图片拼接
    public static String getPublicWelfare(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/PublicWelfare/" + imageName;
    }

    //互助图片拼接
    public static String getMutualImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/MutualAid/" + imageName;
    }

    //个人头像
    public static String getHxFriendsImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/avatar/" + imageName;
    }

    //Banner图的拼接地址
    public static String getbannerImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/banner/" + imageName;
    }

    //动态（圈子）
    public static String getDynamicImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/dynamicimg/" + imageName;
    }

    //商会Banner
    public static String getShanghuiBannerImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/business/" + imageName;
    }

    //商品
    public static String getGoodsInfoImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/goods/" + imageName;
    }

    //商会的图标
    public static String getBuinessImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/businessimage/" + imageName;
    }

    //
    public static String getBuninessImages(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/business/" + imageName;
    }

    //商会动态
    public static String getBuinessDynamicImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/dynamic/" + imageName;
    }
    public static String getIdCardImage(String imageName){
        return ApiService.BASE_URL+"Data/Uploads/realname/"+imageName;
    }
    //口碑详情
    public static String getKouBeiDetailsImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/scandalous/" + imageName;
    }

    //项目
    public static String getProjectImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/ProjectPromotion/" + imageName;
    }

    //新闻
    public static String getNewsImage(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/article/" + imageName;
    }

    //会长说
    public static String getPresident(String imageName) {
        return ApiService.BASE_URL + "Data/Uploads/article/" + imageName;
    }

    public static <T> Map<String, String> createBodyMap(T t) {
        Class tClass = t.getClass();
        Map<String, String> bodyMap = new HashMap<>();
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);// 修改访问权限
                if (field.getType() == java.lang.String.class)
                    bodyMap.put(field.getName(), field.get(t) == null ? "" : String.valueOf(field.get(t)));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bodyMap;
    }

    /**
     * 单图片上传的Body
     * 在{@link ApiService}中需要如下定义接口
     * ----@POST("Api/Hx/AddHx")
     * ----@Multipart
     * Observable<> uploadGroupCreate(@Part MultipartBody.Part photo);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part getFileBody(File file) {
        Log.d("需要上传图片的大小和路径", "图片大小：" + file.length() + "图片路径：" + file.getPath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        String fileName = file.getName();
        return MultipartBody.Part.createFormData("file", fileName, photoRequestBody);
    }
    /****
     * 分享到新闻页的单图片
     * */
    public static MultipartBody.Part getFileNews(File file){
        Log.d("需要上传图片的大小和路径", "图片大小：" + file.length() + "图片路径：" + file.getPath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        String fileName = file.getName();
        return MultipartBody.Part.createFormData("file[]", fileName, photoRequestBody);
    }
    /**
     * 多图片上传的body
     * 在{@link ApiService}中需要如下定义接口
     * <p>
     * ---- @POST("Api/MutualAid/launchMutualAid")
     * --- @Multipart
     * Observable<> launchMutualAid(@PartMap Map<String, RequestBody> params);
     * </p>
     *
     * @param imageList
     * @return
     */
    public static Map<String, RequestBody> getImageParams(String loadKey, List<String> imageList) {

        Map<String, RequestBody> params = new HashMap<>();
        if (imageList.size() == 1) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(imageList.get(0)));
            params.put(loadKey + "\"; filename=\"icon.png", photo);
            return params;
        }
        for (int i = 0; i < imageList.size(); i++) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(imageList.get(i)));
            params.put(loadKey + "[]\"; filename=\"icon" + i + ".png", photo);
        }
        return params;
    }

    public static Map<String, RequestBody> getVideoParam(String path) {
        String[] endName = path.split("\\.");
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody photo = RequestBody.create(MediaType.parse("video/mp4"), new File(path));
        params.put("file\"; filename=\"icon." + endName[1], photo);
        return params;
    }

    /**
     * 图片压缩处理后进行上传
     *
     * @param file
     * @param listener
     */
    public static void pictureCompression(Context context, File file, OnCompressListener listener) {
        Luban.get(context)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)//设定压缩档次，默认三挡
                .setFilename(FileUtils.getInstance().getTempFilePath(context) + System.currentTimeMillis() + ".jpg")//设置上传后的名称
                .setCompressListener(listener).launch();
    }
}

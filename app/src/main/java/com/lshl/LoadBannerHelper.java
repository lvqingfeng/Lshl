package com.lshl;

import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/***
 * Created by Administrator on 2016/12/6.
 */

public class LoadBannerHelper{

    public static void getBanner(String type, final InitBannerImage banner){
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateBanner(type), new Subscriber<BannerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BannerBean bannerBean) {
                List<BannerBean.InfoBean> info = bannerBean.getInfo();
                List<String> list = new ArrayList<>();
                if (info.size()>0){
                    for (int i = 0; i <info.size() ; i++) {
                        list.add(ApiClient.getbannerImage(info.get(i).getBn_img()));
                    }
                    banner.setBanner(list,bannerBean);
                }else {

                }
            }
        });

    }
    public interface InitBannerImage{
        void setBanner(List<String> mList,BannerBean bannerBean);
    }
}


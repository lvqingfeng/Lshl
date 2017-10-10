package com.lshl.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.HttpResult;
import com.lshl.bean.DynamicBean;
import com.lshl.databinding.RecyclerItemDynamicBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.GlideCircleTransform;
import com.lshl.view.SendOpinionPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;

/***
 * Created by 张三 on 2016/11/4.
 * 圈子的适配器
 */

public class PerSonDynamicAdapter extends RecyclerView.Adapter<BindingViewHolder<RecyclerItemDynamicBinding>> {
    private boolean type;
    private List<DynamicBean.InfoBean.ListBean> mList;

    private DynamicImageAdapter imageAdapter;
    private String md_id;
    private Activity activity;
    private SendOpinionPopWindow sendOpinionPopWindow;

    public PerSonDynamicAdapter(List<DynamicBean.InfoBean.ListBean> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<RecyclerItemDynamicBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_dynamic, parent, false);
        return new BindingViewHolder<>(RecyclerItemDynamicBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<RecyclerItemDynamicBinding> holder, int position) {
        final int fPosition = position;
        final DynamicBean.InfoBean.ListBean bean = mList.get(fPosition);
        holder.getBinding().tvName.setText(bean.getRealname());
        holder.getBinding().tvAddress.setText(bean.getMb_cityname());
        holder.getBinding().tvInfo.setText(bean.getMb_dynamic_info());
        holder.getBinding().tvTime.setText(DateUtils.getDateToString2(Long.decode(bean.getMb_addtime()) * 1000));
        holder.getBinding().dianzanNum.setText("(" + bean.getMb_thing_nums() + ")");
        holder.getBinding().commentNum.setText("(" + bean.getMb_comment_nums() + ")");
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).transform(new GlideCircleTransform(holder.mContext)).into(holder.getBinding().ivHeadimg);
        LinearLayoutManager manager = new LinearLayoutManager(holder.mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        holder.getBinding().recyclerView.setLayoutManager(manager);
        holder.getBinding().recyclerView.addItemDecoration(new DividerGridItemDecoration(holder.mContext));
        if (bean.getImageUrl().size() >= 0) {
            holder.getBinding().recyclerView.setVisibility(View.VISIBLE);
        }
        imageAdapter = new DynamicImageAdapter(bean.getImageUrl());
        holder.getBinding().recyclerView.setAdapter(imageAdapter);
//        if (!TextUtils.isEmpty(bean.getMb_dynamic_voide())){
//            holder.getBinding().videoLayout.setVisibility(View.VISIBLE);
//            holder.getBinding().videoImage.setImageBitmap(getVideoBitmap(ApiClient.getDynamicImage(bean.getMb_dynamic_voide())));
//        }
        /*if (bean.getMb_thingstatus() == 1) {
            holder.getBinding().dianzan.setImageResource(R.drawable.ic_vector_dianzan_already);
            holder.getBinding().dianzanNum.setTextColor(ContextCompat.getColor(holder.mContext,R.color.dynamicComment));
            type = true;
        } else {
            type = false;
        }*/

        Constant.getGradeForAll(bean.getGrade(), holder.getBinding().tvGrade);
        md_id = bean.getMd_id();
        holder.getBinding().dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type) {//点赞状态,点击取消
                    loadDianzan(holder.mContext, md_id, "2");
                    holder.getBinding().dianzan.setImageResource(R.drawable.ic_vector_dianzan);
                    type = false;
                } else {//点赞
                    loadDianzan(holder.mContext, md_id, "1");
                    holder.getBinding().dianzan.setImageResource(R.drawable.ic_vector_dianzan_already);
                    type = true;
                }
            }
        });


        holder.getBinding().comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOpinionPopWindow.showPopupWindow(holder.getBinding().getRoot());
            }
        });
        sendOpinionPopWindow = new SendOpinionPopWindow(activity, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.String)
                                .dynamicComment(LoginHelper.getInstance().getUserToken()
                                        , mList.get(fPosition).getMd_id(), null, inputText),
                        new ProgressSubscriber<>(holder.mContext,
                                new SubscriberOnNextListener<ResponseBody>() {
                                    @Override
                                    public void onNext(ResponseBody result) {
                                        try {
                                            String string = result.string();
                                            JSONObject object = JSON.parseObject(string);
                                            String status = object.getString("status");
                                            String message = object.getString("info");
                                            if (ApiService.STATUS_SUC.equals(status)) {
                                                Toast.makeText(holder.mContext, message, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(holder.mContext, message, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }));
            }
        });
        sendOpinionPopWindow.setAnonymousGone();
        holder.getBinding().recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(holder.getBinding().recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ArrayList<String> mImages = new ArrayList<>();
                for (int i = 0; i < bean.getImageUrl().size(); i++) {
                    mImages.add(ApiClient.getDynamicImage(bean.getImageUrl().get(i)));
                }
                Intent intent = new Intent();
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, vh.getLayoutPosition());
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.setClass(holder.mContext, PhotoShowActivity.class);
                holder.mContext.startActivity(intent);
            }
        });
        holder.getBinding().ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HxMemberDetailsActivity.actionStart(activity, "", mList.get(fPosition).getMd_uid(), false);
            }
        });
    }

    public void loadDianzan(final Context context, String mdid, String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).dynamicDianzan(
                LoginHelper.getInstance().getUserToken(), mdid, status),
                new ProgressSubscriber<HttpResult<String>>(context, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Toast.makeText(context, result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public static Bitmap getVideoBitmap(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url, new HashMap<String, String>());
        Bitmap bitmap = retriever.getFrameAtTime();
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}


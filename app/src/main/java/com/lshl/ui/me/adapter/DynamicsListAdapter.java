package com.lshl.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.DynamicBean;
import com.lshl.databinding.DynamicsLayoutItemBinding;
import com.lshl.databinding.ImageBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.view.SendOpinionPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * 作者：吕振鹏
 * 创建时间：12月19日
 * 时间：21:58
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class DynamicsListAdapter extends RecyclerView.Adapter<BindingViewHolder<DynamicsLayoutItemBinding>> {

    private SendOpinionPopWindow sendOpinionPopWindow;
    private List<DynamicBean.InfoBean.ListBean> mListData;
    private int mImageSize;
    private BaseActivity mActivity;

    private String mMdId;

    private TextView mZanView;


    public DynamicsListAdapter(List<DynamicBean.InfoBean.ListBean> listData, BaseActivity activity) {
        mListData = listData;
        mActivity = activity;
        mImageSize = LshlApplication.sWindowWidth / 3 - 5;
        sendOpinionPopWindow = new SendOpinionPop(activity);
        sendOpinionPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mActivity.lightOn();
            }
        });
    }


    @Override
    public BindingViewHolder<DynamicsLayoutItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dynamics, parent, false);
        return new BindingViewHolder<>(DynamicsLayoutItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<DynamicsLayoutItemBinding> holder, int position) {

        final DynamicBean.InfoBean.ListBean listBean = mListData.get(position);
        String zanNum = listBean.getMb_thing_nums();
        mZanView = holder.getBinding().tvClickPraise;
        if (listBean.getMb_thingstatus().equals("1")) {
            zanStatusChange(ContextCompat.getDrawable(holder.mContext, R.drawable.ic_vector_dianzan_already), zanNum, ContextCompat.getColor(holder.mContext, R.color.dynamicComment));
        } else if (listBean.getMb_thingstatus().equals("0")) {
            zanStatusChange(ContextCompat.getDrawable(holder.mContext, R.drawable.ic_vector_dianzan), zanNum, 0xff888888);
        }

        holder.getBinding().tvComment.setText(listBean.getMb_comment_nums());
        holder.getBinding().tvClickPraise.setText(listBean.getMb_thing_nums());
        holder.getBinding().setDynamicsBean(listBean);
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(listBean.getMb_addtime()) * 1000));
        holder.getBinding().tvClickPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZanView = (TextView) v;
                int layoutPosition = holder.getLayoutPosition();
                DynamicBean.InfoBean.ListBean bean = mListData.get(layoutPosition);
                String zanStatus = mListData.get(layoutPosition).getMb_thingstatus();
                if ("1".equals(zanStatus))
                    loadDianzan(holder.mContext, bean.getMb_thing_nums(), bean.getMd_id(), layoutPosition, "2");
                else {
                    loadDianzan(holder.mContext, bean.getMb_thing_nums(), bean.getMd_id(), layoutPosition, "1");
                }
            }
        });
        holder.getBinding().tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                mMdId = mListData.get(layoutPosition).getMd_id();
                mActivity.lightOff();
                sendOpinionPopWindow.showPopupWindow(holder.getBinding().getRoot());
            }
        });

        final List<String> imageList = listBean.getImageUrl();

        if (imageList.size() == 1) {

            holder.getBinding().ivSingle.setVisibility(View.VISIBLE);
            holder.getBinding().surfaceFl.setVisibility(View.GONE);
            holder.getBinding().recyclerOtherImage.setVisibility(View.GONE);
            holder.getBinding().setSingleImage(imageList.get(0));
            holder.getBinding().ivSingle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> list = new ArrayList<>();
                    list.addAll(imageList);
                    showPhoto(holder.mContext, list, 0);
                }
            });
        } else if (imageList.size() > 1) {

            holder.getBinding().ivSingle.setVisibility(View.GONE);
            holder.getBinding().surfaceFl.setVisibility(View.GONE);
            holder.getBinding().recyclerOtherImage.setVisibility(View.VISIBLE);
            holder.getBinding().recyclerOtherImage.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getLayoutManager().getPosition(view);
                    if (position % 3 == 0) {
                        outRect.set(5, 5, 0, 0);
                    } else if (position % 3 == 1) {
                        outRect.set(5, 5, 0, 0);
                    } else if (position % 3 == 2) {
                        outRect.set(5, 5, 5, 0);
                    } else {
                        outRect.set(5, 5, 0, 0);
                    }
                }
            });
            holder.getBinding().recyclerOtherImage.setLayoutManager(new GridLayoutManager(holder.mContext, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            holder.getBinding().recyclerOtherImage.setAdapter(new SimpleBindingAdapter<ImageBinding, String>(imageList, R.layout.item_layout_image_fill) {
                @Override
                public void onBindViewHolder(ImageBinding binding, int position) {
                    final int fPosition = position;
                    ImageView imageView = binding.ivImage;
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    lp.weight = mImageSize;
                    lp.height = mImageSize;
                    imageView.setLayoutParams(lp);
                    binding.setImageUrl(imageList.get(position));
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPhoto(holder.mContext, (ArrayList<String>) imageList, fPosition);
                        }
                    });
                }
            });
        } else if (!TextUtils.isEmpty(listBean.getMb_dynamic_voide())) {
            holder.getBinding().ivSingle.setVisibility(View.GONE);
            holder.getBinding().surfaceFl.setVisibility(View.GONE);
            holder.getBinding().recyclerOtherImage.setVisibility(View.GONE);
        } else {
            holder.getBinding().ivSingle.setVisibility(View.GONE);
            holder.getBinding().surfaceFl.setVisibility(View.GONE);
            holder.getBinding().recyclerOtherImage.setVisibility(View.GONE);
        }


    }

    private void loadDianzan(final Context context, final String fZanNum, String mdid, final int position, final String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).dynamicDianzan(
                LoginHelper.getInstance().getUserToken(), mdid, status),
                new ProgressSubscriber<>(context, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        Toast.makeText(context, result.getInfo(), Toast.LENGTH_SHORT).show();
                        if (status.equals("1")) {
                            String zanNum = String.valueOf(Integer.decode(fZanNum) + 1);
                            mListData.get(position).setMb_thingstatus("1");
                            zanStatusChange(ContextCompat.getDrawable(context, R.drawable.ic_vector_dianzan_already), zanNum, ContextCompat.getColor(context, R.color.dynamicComment));
                            mListData.get(position).setMb_thing_nums(zanNum);
                        } else if (status.equals("2")) {
                            String zanNum = String.valueOf(Integer.decode(fZanNum) - 1);
                            mListData.get(position).setMb_thingstatus("0");
                            mListData.get(position).setMb_thing_nums(zanNum);
                            zanStatusChange(ContextCompat.getDrawable(context, R.drawable.ic_vector_dianzan), zanNum, 0xff888888);
                        }
                    }
                }));
    }

    private void zanStatusChange(Drawable zanIcon, String zanNum, int textColor) {
        TextView zanView = mZanView;
        zanView.setCompoundDrawablesWithIntrinsicBounds(zanIcon, null, null, null);
        zanView.setCompoundDrawablePadding(10);
        zanView.setTextColor(textColor);
        zanView.setText(zanNum);
    }

    private void showPhoto(Context context, ArrayList<String> imageList, int position) {
        Intent intent = new Intent(context, PhotoShowActivity.class);
        intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, imageList);
        intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
        intent.putExtra(PhotoShowActivity.SELECT_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    private class SendOpinionPop extends SendOpinionPopWindow {

        /**
         * 初始化一个PopupWindow
         *
         * @param context 上下文对象
         */
        SendOpinionPop(final Activity context) {
            super(context, new SendOpinionCallback() {
                @Override
                public void onCallback(String inputText, boolean isAnonymous) {
                    sendOpinion(inputText);
                }
            });
            setAnonymousGone();
        }


    }

    private void sendOpinion(String inputText) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.String)
                        .dynamicComment(LoginHelper.getInstance().getUserToken()
                                , mMdId, null, inputText),
                new ProgressSubscriber<>(mActivity, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        try {
                            String string = result.string();
                            JSONObject object = JSON.parseObject(string);
                            String status = object.getString("status");
                            String message = object.getString("info");
                            if (ApiService.STATUS_SUC.equals(status)) {
                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }));
    }

}

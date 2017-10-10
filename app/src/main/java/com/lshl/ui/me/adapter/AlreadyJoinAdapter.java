package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.bean.AlreadyJoinBean;
import com.lshl.bean.ReturnResultBean;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/10/18.
 */
public class AlreadyJoinAdapter extends RecyclerView.Adapter<AlreadyJoinAdapter.ViewHolder> {
    private List<AlreadyJoinBean.InfoEntity> mList;
    private String shanghuiId;

    public AlreadyJoinAdapter(List<AlreadyJoinBean.InfoEntity> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_alreadyjoin, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AlreadyJoinBean.InfoEntity entity = mList.get(position);
        if (entity != null) {
            holder.tv_already_name.setText(entity.getBusiness_name());
            holder.tv_already_zhiwei.setText(entity.getPosition());
            Glide.with(holder.iv_already_logo.getContext()).load(ApiClient.getBuinessImage(entity.getBusiness_shxx())).into(holder.iv_already_logo);
            shanghuiId = entity.getBusiness_id();
            holder.btn_tuichu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestBody body = new FormBody.Builder().add("token", LoginHelper.getInstance().getUserToken()).add("businessid", shanghuiId + "").build();
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .tuichushanghui(body)
                            , new ProgressSubscriber<>(holder.btn_tuichu.getContext(), new SubscriberOnNextListener<ReturnResultBean>() {
                                @Override
                                public void onNext(ReturnResultBean result) {
                                    if (result.getStatus() == 1) {
                                        //Toast.makeText(MyApp.applicationContext,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    );
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_already_logo;
        private TextView tv_already_name;
        private TextView tv_already_zhiwei;
        private Button btn_tuichu;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iv_already_logo = (ImageView) itemView.findViewById(R.id.iv_already_shlogo);
            this.tv_already_name = (TextView) itemView.findViewById(R.id.tv_already_name);
            this.tv_already_zhiwei = (TextView) itemView.findViewById(R.id.tv_already_zhiwei);
            this.btn_tuichu = (Button) itemView.findViewById(R.id.btn_tuichu);
        }
    }


}

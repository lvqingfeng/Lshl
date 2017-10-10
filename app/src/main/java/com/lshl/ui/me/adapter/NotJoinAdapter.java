package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.bean.NotJoinBean;
import com.lshl.bean.ReturnResultBean;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/10/18.
 */
public class NotJoinAdapter extends RecyclerView.Adapter<NotJoinAdapter.ViewHolder> {
    private List<NotJoinBean.InfoEntity> mList;

    private String businessId;

    public NotJoinAdapter(List<NotJoinBean.InfoEntity> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rececler_item_notjoin, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NotJoinBean.InfoEntity entity = mList.get(position);
        if (entity != null) {
            businessId = entity.getBuid();
            holder.tv_notjoin_name.setText(entity.getBusinessname());
            if (entity.getBusiness_briefing() != null) {
                holder.tv_notjoin_info.setText(entity.getBusiness_briefing().toString());
            }
            if (entity.getBusiness_shxx() != null) {
                Glide.with(holder.iv_notjoin_xx.getContext()).load(ApiClient.getBuinessImage(entity.getBusiness_shxx().toString())).into(holder.iv_notjoin_xx);
            }
            holder.btn_shenqingruhui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RequestBody body = new FormBody.Builder().add("token", LoginHelper.getInstance().getUserToken()).add("business_id", businessId).build();
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object)
                                    .joinsh(body)
                            , new ProgressSubscriber<>(holder.btn_shenqingruhui.getContext(), new SubscriberOnNextListener<ReturnResultBean>() {
                                @Override
                                public void onNext(ReturnResultBean result) {
                                    if (result != null) {
                                        if (result.getStatus() == 1) {
                                            Toast.makeText(holder.btn_shenqingruhui.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
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
        private ImageView iv_notjoin_xx;
        private TextView tv_notjoin_name;
        private TextView tv_notjoin_info;
        private Button btn_shenqingruhui;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_notjoin_xx = ((ImageView) itemView.findViewById(R.id.iv_notjoin_xx));
            tv_notjoin_name = (TextView) itemView.findViewById(R.id.tv_notjoin_name);
            tv_notjoin_info = (TextView) itemView.findViewById(R.id.tv_notjoin_info);
            btn_shenqingruhui = (Button) itemView.findViewById(R.id.btn_shenqingruhui);
        }
    }

}

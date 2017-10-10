package com.lshl.ui.map.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.HelpWaitGetMemberListBean;
import com.lshl.bean.HelpWaitGetMemberListBean.InfoBean.ListBean.HelpStatus;
import com.lshl.databinding.WaitGetMemberListBinding;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * 作者：吕振鹏
 * 创建时间：10月26日
 * 时间：16:41
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HelpWaitGetAdapter extends RecyclerView.Adapter<BindingViewHolder<WaitGetMemberListBinding>> {

    private List<HelpWaitGetMemberListBean.InfoBean.ListBean> mListData;
    private LayoutInflater mInflater;
    private Context mContext;

    public HelpWaitGetAdapter(Context context, List<HelpWaitGetMemberListBean.InfoBean.ListBean> listData) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListData = listData;
    }

    @Override
    public BindingViewHolder<WaitGetMemberListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_layout_wait_get_member_list, parent, false);
        return new BindingViewHolder<>(WaitGetMemberListBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<WaitGetMemberListBinding> holder, int position) {

        final int fPosition = position;
        final HelpWaitGetMemberListBean.InfoBean.ListBean listBean = mListData.get(fPosition);
        holder.getBinding().setInfoBean(listBean);


        //根据不同的状态让两个按钮显示不同的样式
        String status = listBean.getGre_status();
        if (status.equals(HelpStatus.RESCUE_GET_SUC.getStatusCode()) || status.equals(HelpStatus.REPORT.getStatusCode()) || status.equals(HelpStatus.WARNING.getStatusCode())) {//显示 到达和未到达两个按钮
            holder.getBinding().tvGet.setVisibility(View.VISIBLE);
            holder.getBinding().tvNotGet.setVisibility(View.VISIBLE);
            setTextAttr(holder.getBinding().tvGet, "到达", R.color.btn_green_pressed, R.drawable.bg_stroke_green, true);
            setTextAttr(holder.getBinding().tvNotGet, "未到", R.color.text_red_color, R.drawable.bg_stroke_red, true);
        } else if (status.equals(HelpStatus.GET_SUC.getStatusCode())) {//显示为已到达
            holder.getBinding().tvNotGet.setVisibility(View.GONE);
            setTextAttr(holder.getBinding().tvGet, "已到达", R.color.gray_btn_bg_color, -1, false);
        } else if (status.equals(HelpStatus.FALSE_INFO.getStatusCode())) {//显示为未到达
            holder.getBinding().tvGet.setVisibility(View.GONE);
            setTextAttr(holder.getBinding().tvNotGet, "未到达", R.color.gray_btn_bg_color, -1, false);
        } else if (status.equals(HelpStatus.WAIT_CONFIRM.getStatusCode())) {
            holder.getBinding().tvGet.setVisibility(View.GONE);
            holder.getBinding().tvNotGet.setVisibility(View.GONE);
        }
        //设置按钮的点击时间
        holder.getBinding().tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTodo(listBean.getGre_id(), HelpStatus.GET_SUC.getStatusCode(), fPosition);
            }
        });
        holder.getBinding().tvNotGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTodo(listBean.getGre_id(), HelpStatus.FALSE_INFO.getStatusCode(), fPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    private void onClickTodo(String gid, final String status, final int position) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                        .gidArrive(LoginHelper.getInstance().getUserToken(), gid, status)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        try {

                            String resultStr = result.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String statusJson = object.getString("status");
                            if (statusJson.equals(ApiService.STATUS_SUC)) {
                                mListData.get(position).setGre_status(status);
                                notifyItemChanged(position);
                            } else {
                                Toast.makeText(mContext, "错误信息：" + object.getString("info"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    /**
     * 设置TextView显示数据的属性
     *
     * @param tv        具体的控件
     * @param text      显示的文字
     * @param color     文字的颜色
     * @param backgroud 控件显示的背景
     * @param isEnabled 是否可点击
     */
    private void setTextAttr(TextView tv, String text, int color, int backgroud, boolean isEnabled) {
        tv.setText(text);
        tv.setEnabled(isEnabled);
        tv.setTextColor(ContextCompat.getColor(mContext, color));
        if (backgroud == -1) {
            tv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
        } else {
            tv.setBackgroundResource(backgroud);
        }

    }
}

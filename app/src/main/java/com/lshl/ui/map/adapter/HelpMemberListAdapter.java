package com.lshl.ui.map.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.HelpMemberListBean;
import com.lshl.databinding.SearchHelpMemberListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月25日
 * 时间：17:15
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HelpMemberListAdapter extends RecyclerView.Adapter<BindingViewHolder<SearchHelpMemberListBinding>> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<HelpMemberListBean.InfoBean> mListData;

    private OnClickSelectCallBack mSelectCallBack;


    public HelpMemberListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mListData = new ArrayList<>();
    }

    public void addAllData(List<HelpMemberListBean.InfoBean> listData) {
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    public void setSelectStatus(boolean allSelectStatus) {
        for (HelpMemberListBean.InfoBean infoBean : mListData) {
            infoBean.setSelected(allSelectStatus);
        }
        notifyDataSetChanged();
    }

    public void cleanData() {
        mListData.clear();
        notifyDataSetChanged();
    }

    public List<HelpMemberListBean.InfoBean> getSelectMemberList() {
        List<HelpMemberListBean.InfoBean> beanList = new ArrayList<>();
        for (HelpMemberListBean.InfoBean infoBean : mListData) {
            if (infoBean.isSelected()) {
                beanList.add(infoBean);
            }
        }
        return beanList;
    }

    public void setOnClickSelectCallBack(OnClickSelectCallBack callBack) {
        mSelectCallBack = callBack;
    }

    @Override
    public BindingViewHolder<SearchHelpMemberListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_layout_search_help_member_list, parent, false);
        return new BindingViewHolder<>(SearchHelpMemberListBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<SearchHelpMemberListBinding> holder, int position) {
        final HelpMemberListBean.InfoBean infoBean = mListData.get(position);
        holder.getBinding().setMemberBean(infoBean);
        holder.getBinding().checkMemberSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                infoBean.setSelected(isChecked);
                if (mSelectCallBack != null)
                    mSelectCallBack.onSelect(isSelectAll());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    private boolean isSelectAll() {
        for (HelpMemberListBean.InfoBean infoBean : mListData) {
            if (!infoBean.isSelected()) {
                return false;
            }
        }
        return true;
    }


    public interface OnClickSelectCallBack {

        void onSelect(boolean isSelectAll);

    }
}

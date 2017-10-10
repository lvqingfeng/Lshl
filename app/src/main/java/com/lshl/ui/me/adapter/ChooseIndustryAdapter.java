package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.IndustryBean;
import com.lshl.databinding.ItemLayoutChooseIndustryBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Created by Administrator on 2017/1/20.
 */

public class ChooseIndustryAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutChooseIndustryBinding>>
implements View.OnClickListener{
    private List<IndustryBean.InfoBean> mList;
    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    public ChooseIndustryAdapter(List<IndustryBean.InfoBean> mList) {
        this.mList = mList;
        initMap();
    }
    private void initMap(){
        for (int i = 0; i < mList.size(); i++) {
            map.put(i, false);
        }
    }
    @Override
    public BindingViewHolder<ItemLayoutChooseIndustryBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_choose_industry,parent,false);
        view.setOnClickListener(this);
        return new BindingViewHolder<>(ItemLayoutChooseIndustryBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutChooseIndustryBinding> holder, final int position) {
        IndustryBean.InfoBean bean = mList.get(position);
        holder.getBinding().industry.setText(bean.getIn_name());
        holder.getBinding().checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position, isChecked);
            }
        });
        //设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.getBinding().checkbox.setChecked(map.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }
    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }
    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }
    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);
    }
}

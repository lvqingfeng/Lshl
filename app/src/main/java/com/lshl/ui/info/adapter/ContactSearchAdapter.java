package com.lshl.ui.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseUserUtils;
import com.lshl.R;
import com.lshl.db.bean.HxUserBean;

import java.util.List;

/**
 * 联系人搜索adapter
 * Created by maxilong on 2017/3/8.
 */

public class ContactSearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<HxUserBean> hxUserBeenList;

    public ContactSearchAdapter(Context context, List<HxUserBean> hxUserBeenList) {
        this.mContext = context;
        this.hxUserBeenList = hxUserBeenList;
    }

    @Override
    public int getCount() {
        return hxUserBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return hxUserBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactSearchViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ContactSearchViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ease_row_contact, null);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContactSearchViewHolder) convertView.getTag();
        }
        HxUserBean hxUserBean = (HxUserBean) getItem(position);
//        viewHolder.name.setText(hxUserBean.getRealname());
        if (hxUserBean != null) {
            viewHolder.address.setText(hxUserBean.getLive_cityname());
            EaseUserUtils.setUserShow(hxUserBean.getHx_id(), viewHolder.name, viewHolder.avatar);
        }

        return convertView;
    }

    class ContactSearchViewHolder {
        ImageView avatar;
        TextView name;
        TextView address;
    }
}

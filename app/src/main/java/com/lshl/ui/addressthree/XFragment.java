package com.lshl.ui.addressthree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lshl.R;
import com.lshl.bean.AreaInfo;
import com.lshl.ui.me.activity.CreateCardsActivity;
import com.lshl.utils.JsonFormFileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class XFragment extends Fragment {

    private String cName;
    private String cId="";
    private List<AreaInfo> areaInfoList;
    private String pId;
    private String pName;
    private List<AreaInfo.SubBeanX> subsubBeanXList;
    private List<AreaInfo.SubBeanX.SubBean> subBeenList;
    private AreaAdapter adapter;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        lv = (ListView) view.findViewById(R.id.lv);
        if (getArguments() != null) {
            cId = getArguments().getString("cId");
            cName = getArguments().getString("cName");
            pId = getArguments().getString("pId");
            pName = getArguments().getString("pName");
        }
        try {
            areaInfoList = JsonFormFileUtils.getJsonArray(getActivity(), "address_three.json", AreaInfo.class);
            for (int i = 0; i < areaInfoList.size(); i++) {
                if (pId.equals(areaInfoList.get(i).getId())) {
                    subsubBeanXList = areaInfoList.get(i).getSub();
                }
            }
            for (int j = 0; j < subsubBeanXList.size(); j++) {
                if (cId.equals(subsubBeanXList.get(j).getId())) {
                    subBeenList = subsubBeanXList.get(j).getSub();
                }
            }
            adapter = new AreaAdapter(getContext(), subBeenList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lv.setAdapter(adapter);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", pName + cName + subBeenList.get(position).getCityname());
                Intent intent = new Intent(getActivity(), CreateCardsActivity.class);
                intent.putExtra("cityId", cId);
                intent.putExtra("addressName", pName + cName + subBeenList.get(position).getCityname());
                getActivity().setResult(getActivity().RESULT_OK,intent);
                getActivity().finish();
            }
        });
        return view;
    }

    class AreaAdapter extends BaseAdapter {

        private List list;
        public AreaAdapter(Context context, List<AreaInfo.SubBeanX.SubBean> list) {
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.area_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            AreaInfo.SubBeanX.SubBean item = (AreaInfo.SubBeanX.SubBean) list.get(position);
            viewHolder.textView.setText(item.getCityname());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}

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

public class CFragment extends Fragment {

    private String pId;
    private String pName;
    private ListView lv;
    private List<AreaInfo> areaInfoList;
    private AreaAdapter adapter;
    private List<AreaInfo.SubBeanX> subBeanXList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        lv = (ListView) view.findViewById(R.id.lv);
        if (getArguments() != null) {
            pId = getArguments().getString("pId");
            pName = getArguments().getString("pName");
        }
        try {
            areaInfoList = JsonFormFileUtils.getJsonArray(getActivity(), "address_three.json", AreaInfo.class);
            for (int i = 0; i < areaInfoList.size(); i++) {
                if (pId.equals(areaInfoList.get(i).getId())) {
                    subBeanXList = areaInfoList.get(i).getSub();
                }
            }
            adapter = new AreaAdapter(getContext(), subBeanXList);
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
                if (subBeanXList.get(position).getSub() != null && subBeanXList.get(position).getSub().size() > 0) {
                    AreaInfo.SubBeanX subBeanInfo = subBeanXList.get(position);
                    XFragment XFragment = new XFragment();
                    Bundle args = new Bundle();
                    if ("北京".equals(pName)) {
                        args.putString("pName", "");
                    } else if ("上海".equals(pName)) {
                        args.putString("pName", "");
                    } else if ("天津".equals(pName)) {
                        args.putString("pName", "");
                    } else if ("重庆".equals(pName)) {
                        args.putString("pName", "");
                    } else {
                        args.putString("pName", pName);
                    }
                    args.putString("cName", subBeanInfo.getCityname());
                    args.putString("cId", subBeanInfo.getId());
                    args.putString("pId", pId);
                    XFragment.setArguments(args);
                    XFragment.setTargetFragment(CFragment.this, 0);
                    getFragmentManager().beginTransaction().replace(R.id.content, XFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Log.i("TAG", pName + subBeanXList.get(position).getCityname());
                    Intent intent = new Intent(getActivity(), CreateCardsActivity.class);
                    intent.putExtra("cityId", subBeanXList.get(position).getId());
                    intent.putExtra("addressName", pName + subBeanXList.get(position).getCityname());
                    getActivity().setResult(getActivity().RESULT_OK,intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    class AreaAdapter extends BaseAdapter {

        private List list;

        public AreaAdapter(Context context, List<AreaInfo.SubBeanX> list) {
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
            AreaInfo.SubBeanX item = (AreaInfo.SubBeanX) list.get(position);
            viewHolder.textView.setText(item.getCityname());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}

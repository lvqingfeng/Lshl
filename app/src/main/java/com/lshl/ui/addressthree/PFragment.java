package com.lshl.ui.addressthree;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lshl.R;
import com.lshl.bean.AreaInfo;
import com.lshl.utils.JsonFormFileUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class PFragment extends Fragment {

    private List<AreaInfo> areaInfoList;
    private AreaAdapter adapter;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
        lv = (ListView) view.findViewById(R.id.lv);
        try {
            areaInfoList = JsonFormFileUtils.getJsonArray(getActivity(), "address_three.json", AreaInfo.class);
            adapter = new AreaAdapter(getContext(), areaInfoList);
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
                AreaInfo areaInfo = areaInfoList.get(position);
                CFragment CFragment = new CFragment();
                Bundle args = new Bundle();
                args.putString("pId", areaInfo.getId());
                args.putString("pName", areaInfo.getCityname());
                CFragment.setArguments(args);
                CFragment.setTargetFragment(PFragment.this, 0);
                getFragmentManager().beginTransaction().replace(R.id.content, CFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


    class AreaAdapter extends BaseAdapter {

        private List list;

        public AreaAdapter(Context context, List<AreaInfo> list) {
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
            AreaInfo item = (AreaInfo) list.get(position);
            viewHolder.textView.setText(item.getCityname());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}

/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lshl.ui.info.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.view.GlideRoundTransform;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<HxGroupBean> {

    private LayoutInflater inflater;
    private String newGroup;
    private String addPublicGroup;

    public GroupAdapter(Context context, int res, List<HxGroupBean> groups) {
        super(context, res, groups);
        this.inflater = LayoutInflater.from(context);
        newGroup = context.getResources().getString(R.string.The_new_group_chat);
        addPublicGroup = context.getResources().getString(R.string.add_public_group_chat);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_info_group_search_bar, parent, false);
            }
            final EditText query = (EditText) convertView.findViewById(R.id.search_title_view);
            query.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getFilter().filter(s);
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });

        } else if (getItemViewType(position) == 1) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_add_group, parent, false);
            }
            ImageView avatar = ((ImageView) convertView.findViewById(R.id.avatar));
            avatar.setBackgroundResource(R.drawable.bg_solid_yellow);
            avatar.setImageResource(R.drawable.ic_vector_add);
            ((TextView) convertView.findViewById(R.id.name)).setText(newGroup);
        } else if (getItemViewType(position) == 2) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_add_group, parent, false);
            }
            ImageView avatar = ((ImageView) convertView.findViewById(R.id.avatar));
            avatar.setBackgroundResource(R.drawable.bg_solid_green);
            avatar.setImageResource(R.drawable.ic_vector_add_group);
            ((TextView) convertView.findViewById(R.id.name)).setText(addPublicGroup);
            ((TextView) convertView.findViewById(R.id.header)).setVisibility(View.VISIBLE);

        } else {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_group, parent, false);
            }
            ImageView showImage = (ImageView) convertView.findViewById(R.id.avatar);
            HxGroupBean bean = getItem(position - 3);
            showImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (bean != null) {
                ((TextView) convertView.findViewById(R.id.name)).setText(bean.getGroup_name());
                if (TextUtils.isEmpty(bean.getGroup_img())) {
                    Glide.with(getContext()).load(R.drawable.em_group_icon).transform(new GlideRoundTransform(getContext(), 5)).into(showImage);
                } else {
                    Glide.with(getContext()).load(ApiClient.getHxGroupImage(bean.getGroup_img())).transform(new GlideRoundTransform(getContext(), 5)).into(showImage);
                }
            }

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount() + 3;
    }

}

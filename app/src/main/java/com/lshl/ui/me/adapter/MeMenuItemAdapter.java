package com.lshl.ui.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MenuItemBean;
import com.lshl.databinding.MyMenuTypeOneBinding;
import com.lshl.databinding.MyMenuTypeTwoBinding;
import com.lshl.databinding.SpaceItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月22日
 * 时间：10:35
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class MeMenuItemAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    /****
     *砍掉的功能图标 R.drawable.ic_vector_project,R.drawable.ic_vector_recruit,R.drawable.ic_vector_dscs,R.drawable.ic_vector_me_recruit,
     * 对应的背景色R.drawable.bg_round_yellow, R.drawable.bg_round_green,R.drawable.bg_round_blue,, R.drawable.bg_round_green
     * */
    private int[] mMenuIcon = {R.drawable.ic_vector_me_community, R.drawable.ic_vector_me_vip,
            R.drawable.ic_vector_me_settring, R.drawable.ic_vector_my_profile,
            R.drawable.ic_vector_my_wealth, R.drawable.ic_vector_my_coc,
            R.drawable.ic_vector_look_help,
             R.drawable.ic_vector_reputation, R.drawable.ic_vector_goods,  R.drawable.ic_vector_talk};
    private int[] mMenuBg = {R.drawable.bg_round_green, R.drawable.bg_round_yellow
            , R.drawable.bg_round_deep_blue
            , R.drawable.bg_round_cuilv
            , R.drawable.bg_round_deep_blue
            , R.drawable.bg_round_orange
            , R.drawable.bg_round_blue};

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_SPACE = 3;

    private List<MenuItemBean> mListData;
    private Context mContext;
    private LayoutInflater mInflater;

    private int mHintItemPosition = -1;

    public MeMenuItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mListData = new ArrayList<>();
        initData();
    }

    public void hintItem(int position) {
        mHintItemPosition = position;
        notifyDataSetChanged();
    }

    private void initData() {

        String[] menuStr = mContext.getResources().getStringArray(R.array.me_menu);
        for (int i = 0; i < menuStr.length; i++) {
            MenuItemBean bean = new MenuItemBean();
            bean.setMenuName(menuStr[i]);
            bean.setMenuRes(mMenuIcon[i]);
            if (i >= 3 && mMenuBg.length > i - 3)
                bean.setMenuBg(mMenuBg[i - 3]);
            mListData.add(bean);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 3) {
            return TYPE_ONE;
        } else if (position == 3 || position == 7) {
            return TYPE_SPACE;
        }
        return TYPE_TWO;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                View rootOne = mInflater.inflate(R.layout.item_layout_me_menu_type_one, parent, false);
                return new ViewHolderTypeOne(rootOne);
            case TYPE_TWO:
                View rootTwo = mInflater.inflate(R.layout.item_layout_me_menu_type_two, parent, false);
                return new ViewHolderTypeTwo(rootTwo);
            case TYPE_SPACE:
                View rootView = mInflater.inflate(R.layout.item_layout_space, parent, false);
                return new ViewHoldeSpace(rootView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ONE:
                MyMenuTypeOneBinding oneBinding = ((ViewHolderTypeOne) holder).getBinding();
                MenuItemBean itemOneBean = mListData.get(position);
                oneBinding.ivItemIcon.setImageResource(itemOneBean.getMenuRes());
                oneBinding.tvItemName.setText(itemOneBean.getMenuName());
                break;
            case TYPE_TWO:
                if (position != mHintItemPosition) {
                    MyMenuTypeTwoBinding twoBinding = ((ViewHolderTypeTwo) holder).getBinding();
                    MenuItemBean itemTwoBean = null;
                    if (position > 3 && position < 7) {
                        itemTwoBean = mListData.get(position - 1);
                    } else if (position > 7) {
                        itemTwoBean = mListData.get(position - 2);
                    }

                    twoBinding.ivItemIcon.setImageResource(itemTwoBean.getMenuRes());
                    twoBinding.tvItemName.setText(itemTwoBean.getMenuName());
                    twoBinding.ivItemIcon.setBackgroundResource(itemTwoBean.getMenuBg());
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mHintItemPosition != -1)
            return mListData == null ? 0 : mListData.size() + 2 - 1;
        else
            return mListData == null ? 0 : mListData.size() + 2;
    }


    private class ViewHolderTypeOne extends BindingViewHolder<MyMenuTypeOneBinding> {
        ViewHolderTypeOne(View itemView) {
            super(MyMenuTypeOneBinding.bind(itemView));
        }
    }

    private class ViewHolderTypeTwo extends BindingViewHolder<MyMenuTypeTwoBinding> {
        ViewHolderTypeTwo(View itemView) {
            super(MyMenuTypeTwoBinding.bind(itemView));
        }
    }

    private class ViewHoldeSpace extends BindingViewHolder<SpaceItemBinding> {

        public ViewHoldeSpace(View view) {
            super(SpaceItemBinding.bind(view));
        }
    }
}

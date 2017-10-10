package com.lshl.view;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.lshl.R;

/**
 * 作者：吕振鹏
 * 创建时间：11月03日
 * 时间：21:08
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 * <p>
 * 1.初始化PopWindow
 * 2.setEditHintSearch（）设置提示的文字
 * 3.通过设置setSearchContentCallback（）监听返回的数据
 */

public class SearchContentPopWindow extends AddPopWindow {

    private EditText mEditSearch;
    private TextView mTvSearch;
    private boolean isSearchPopCancel = true;
    private CharSequence mEditHintStr;

    private SearchContentCallback mCallback;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public SearchContentPopWindow(final Activity context) {
        super(context, R.layout.layout_pop_trade_goods_search_bar);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mEditSearch = (EditText) getWindowRootView().findViewById(R.id.edit_goods_search_text);
        mTvSearch = (TextView) getWindowRootView().findViewById(R.id.tv_goods_search);

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    mTvSearch.setText("取消");
                    mTvSearch.setTextColor(ContextCompat.getColor(context, R.color.indicatorColor));
                    isSearchPopCancel = true;
                } else {
                    mTvSearch.setText("搜索");
                    mTvSearch.setTextColor(ContextCompat.getColor(context, R.color.textBlackColor));
                    isSearchPopCancel = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onClickInputMethodSearch();
                    return true;
                }
                return false;
            }
        });
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchPopCancel)
                    closePopupWindow();
                else
                    onClickInputMethodSearch();
            }
        });


    }

    private void onClickInputMethodSearch() {
        String searchStr = mEditSearch.getText().toString();

        if (TextUtils.isEmpty(searchStr)) {
            searchStr = "";
            mEditSearch.setHint(mEditHintStr);
        }
        if (mCallback != null) {
            mCallback.onSearchCallback(searchStr);
        }
        closePopupWindow();
    }

    /**
     * 设置返回的监听
     *
     * @param callback
     */
    public void setSearchContentCallback(SearchContentCallback callback) {
        mCallback = callback;
    }

    /**
     * 设置搜索框中的提示内容
     * @param hintStr
     */
    public void setEditHintSearch(CharSequence hintStr) {
        mEditHintStr = hintStr;
        mEditSearch.setHint(mEditHintStr);
    }


    public interface SearchContentCallback {
        void onSearchCallback(String str);
    }
}

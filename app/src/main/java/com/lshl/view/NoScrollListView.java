package com.lshl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 这是一个没有滑动特性的ListView
 * 主要作用是用于嵌套于AbsListView 或者是ScrollView中
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}

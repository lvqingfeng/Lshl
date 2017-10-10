package com.lshl.iml;

import android.support.design.widget.AppBarLayout;

/**
 * 作者：吕振鹏
 * 创建时间：09月28日
 * 时间：15:02
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        OVERLAP,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        System.out.println("---- i = " + i);
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, i);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, i);
            }
            mCurrentState = State.COLLAPSED;
        } else if (Math.abs(i) < appBarLayout.getTotalScrollRange() && Math.abs(i) >= 340) {
            if (mCurrentState != State.OVERLAP) {
                onStateChanged(appBarLayout, State.OVERLAP, i);
            }
            mCurrentState = State.OVERLAP;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, i);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset);
}
package com.lshl.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 作者：吕振鹏
 * 创建时间：12月29日
 * 时间：22:19
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsTabItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchAdapter mItemTouchAdapter;

    public NewsTabItemTouchHelperCallback(ItemTouchAdapter itemTouchAdapter) {
        mItemTouchAdapter = itemTouchAdapter;
    }

    /**
     * 有一些item可以拖拽，一些item无法拖拽
     * 这个方法是为了告诉ItemTouchHelper是否需要RecyclerView支持长按拖拽，默认返回是ture（即支持），理所当然我们要支持，所以我们没有重写，因为默认true。
     * 实现部分item拖拽,将该方法返回false
     * 然后还使用掉用startDrag(ViewHolder)方法。
     * 原来如此，我们可以在item的长按事件中得到当前item的ViewHolder ，然后调用ItemTouchHelper.startDrag(ViewHolder vh)就可以实现拖拽了
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
     * dragFlags 是拖拽标志，swipeFlags是滑动标志，我们把swipeFlags 都设置为0，表示不处理滑动操作。
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    /**
     * 如果我们设置了非0的dragFlags ，那么当我们长按item的时候就会进入拖拽并在拖拽过程中不断回调onMove()方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        mItemTouchAdapter.onMove(fromPosition, toPosition);
        return true;
    }

    /**
     * 设置了非0的swipeFlags，我们在滑动item的时候就会回调onSwiped的方法
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mItemTouchAdapter.onSwiped(position);
    }


    ////////////////////拖拽的时候我们拖拽的对象不能高亮显示，这是不友好的，我们希望拖拽的Item在拖拽的过程中背景颜色加深，这样就需要继续重写下面两个方法/////////////////////////

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (background == null && bkcolor == -1) {
                Drawable drawable = viewHolder.itemView.getBackground();
                if (drawable == null) {
                    bkcolor = 0;
                } else {
                    background = drawable;
                }
            }
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 当手指松开的时候（拖拽完成的时候）调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(1.0f);
        if (background != null) viewHolder.itemView.setBackgroundDrawable(background);
        if (bkcolor != -1) viewHolder.itemView.setBackgroundColor(bkcolor);
        //viewHolder.itemView.setBackgroundColor(0);

        if (mOnDragListener != null) {
            mOnDragListener.onFinishDrag();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    private Drawable background = null;
    private int bkcolor = -1;

    private OnDragListener mOnDragListener;

    public NewsTabItemTouchHelperCallback setOnDragListener(OnDragListener listener) {
        mOnDragListener = listener;
        return this;
    }

    public interface OnDragListener {
        void onFinishDrag();
    }

    public interface ItemTouchAdapter {
        void onMove(int fromPosition, int toPosition);

        void onSwiped(int position);
    }


}

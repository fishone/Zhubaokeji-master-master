package com.zhubaokeji.android.biz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.kelin.scrollablepanel.library.ScrollablePanel;
import com.zhubaokeji.android.listener.Pullable;
import com.zhubaokeji.android.utils.DividerItemDecoration;


public class PullableScrollablePanel extends ScrollablePanel implements Pullable {
    public PullableScrollablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            if (linearManager.getItemCount() == 0) {
                return true;
            } else if (linearManager.findFirstVisibleItemPosition() == 0 && linearManager.findViewByPosition(0).getTop() >= 0) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            if (linearManager.getItemCount() == 0) {
                return false;
            }
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            int lastVisibleItemPosition = linearManager.findLastVisibleItemPosition();
            if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1) {
//                //锁定最后一个item
                View lastView = linearManager.findViewByPosition(lastVisibleItemPosition);

                if (lastView.getBottom() <= recyclerView.getMeasuredHeight()) {

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

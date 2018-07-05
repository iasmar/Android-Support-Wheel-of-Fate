package com.iasmar.rome.ui.views.custom.recyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.iasmar.rome.configuration.ColorsConstant;

import static com.iasmar.rome.util.ViewUtils.getColor;

//TODO  add comments

/**
 * Extends {@link SwipeRefreshLayout} to support non-direct descendant scrolling views.
 * <p>
 * {@link SwipeRefreshLayout} works as expected when a scroll view is a direct child: it triggers
 * the refresh only when the view is on top. This class adds a way (@link #setScrollUpChild} to
 * define which view controls this behavior.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    private View mScrollUpChild;
    private int touchSlop;
    private float prevX;

    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return mScrollUpChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;

    }

    public void setColorSchemeColors() {
        int[] colorScheme = ColorsConstant.schemeSwipeRefreshLayout;
        int[] colors = new int[colorScheme.length];
        int length = colorScheme.length;
        for (int i = 0; i < length; i++) {
            colors[i] = getColor(getContext(), colorScheme[i]);
        }
        super.setColorSchemeColors(colors);
    }

    // Fix the weird behavior with viewpager.
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = MotionEvent.obtain(event).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - prevX);

                if (xDiff > touchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
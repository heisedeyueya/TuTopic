package com.kankan.tutopic.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kankan.logging.Logger;

public class SwipeView extends SwipeRefreshLayout {
    private static final Logger LOG = Logger.getLogger(SwipeView.class);

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        LOG.debug("onTouchEvent:{}, ret = {}", event.getAction(), ret);

        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        LOG.debug("onInterceptTouchEvent:{}, ret ={}", ev.getAction(), ret);

        return ret;
    }

}

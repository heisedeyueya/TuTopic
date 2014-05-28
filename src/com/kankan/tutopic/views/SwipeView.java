package com.kankan.tutopic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kankan.logging.Logger;
import com.origamilabs.library.views.SwipeRefreshLayout;

public class SwipeView extends SwipeRefreshLayout {
    private static final Logger LOG = Logger.getLogger(SwipeView.class);

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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

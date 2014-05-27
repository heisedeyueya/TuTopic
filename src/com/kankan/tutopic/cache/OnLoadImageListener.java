package com.kankan.tutopic.cache;

import android.widget.ImageView;

public interface OnLoadImageListener {

    public final static int STATE_IMAGE_LOAD_SUCCEED = 1;
    public final static int STATE_IMAGE_LOAD_FAIL = 2;

    public void onLoadCompleted(String imageUrl, ImageView targetView, int state);
}

package com.blackmoon.tutopic.app;

import android.app.Application;

import com.blackmoon.tutopic.cache.ImageCache;
import com.kankan.logging.Logger;

public class BaseApplication extends Application {
    private static final Logger LOG = Logger.getLogger(BaseApplication.class);

    private static final String IMAGE_CACHE_NAME = "Images";
    private static final int CACHE_SIZE_FACTOR = 4;
    public static BaseApplication sInstance;
    private ImageCache mImageCache;

    public ImageCache getImageCache() {
        if (mImageCache == null) {
            mImageCache = ImageCache.getPerfectCache(getApplicationContext(), IMAGE_CACHE_NAME, CACHE_SIZE_FACTOR);
        }

        return mImageCache;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        LOG.debug("onCreate.");

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    }

    @Override
    public void onLowMemory() {

        if (mImageCache != null) {
            mImageCache.clearMemeoryCache();
        }
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        LOG.debug("onTerminate.");

        ActivityManager.getInstance().finishActivities();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

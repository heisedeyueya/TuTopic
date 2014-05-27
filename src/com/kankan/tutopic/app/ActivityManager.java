package com.kankan.tutopic.app;

import java.util.Map;
import java.util.WeakHashMap;

import android.app.Activity;

import com.kankan.logging.Logger;

public class ActivityManager {
    private static final Logger LOG = Logger.getLogger(ActivityManager.class);

    private Map<Activity, Boolean> mActivities;

    private static ActivityManager sInstance = null;

    public static ActivityManager getInstance() {
        if (sInstance == null) {
            sInstance = new ActivityManager();
        }

        return sInstance;
    }

    public void add(Activity activity) {
        LOG.info("add {}.", activity);

        mActivities.put(activity, true);
    }

    public void remove(Activity activity) {
        LOG.info("remove {}.", activity);

        mActivities.remove(activity);
    }

    public void finishActivities() {
        LOG.info("finish activities. size={}.", mActivities.size());

        for (Activity activity : mActivities.keySet()) {
            LOG.info("finish {}.", activity);

            activity.finish();
        }
        mActivities.clear();
    }

    private ActivityManager() {
        mActivities = new WeakHashMap<Activity, Boolean>();
    }
}

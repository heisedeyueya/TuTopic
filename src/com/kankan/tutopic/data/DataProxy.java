package com.kankan.tutopic.data;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public class DataProxy {
    // public static final String SERVER_URL = "http://maho.anime.m.kankan.com/"; // 内网
    public static final String SERVER_URL = "http://anime.m.kankan.com/";// 外网
    private static final String FEATURED_URL = SERVER_URL + "data/featured.json";

    private static DataProxy sInstance;

    private DataProxy() {
    }

    public synchronized static DataProxy getInstance() {
        if (sInstance == null) {
            sInstance = new DataProxy();
        }

        return sInstance;
    }

    public Featured loadFeatured() {
        Type type = new TypeToken<Response<Featured>>() {
        }.getType();

        URLLoader loader = new URLLoader();
        Featured featured = (Featured) loader.loadObject(FEATURED_URL, type);

        return featured;
    }
}

package com.kankan.tutopic.imageviewer;

import com.blackmoon.tutopic.R;
import com.kankan.tutopic.cache.ImageFetcher;
import com.polites.android.GestureImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class BigImageView extends RelativeLayout {
    GestureImageView imageView;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.image_viewer, this);
        setupViews();
    }

    private void setupViews() {
        imageView = (GestureImageView) findViewById(R.id.image);
    }

    public void loadImage(String url, ImageFetcher fetcher) {
        fetcher.loadImage(url, imageView);
    }

}

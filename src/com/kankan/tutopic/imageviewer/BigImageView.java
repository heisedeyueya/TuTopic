package com.kankan.tutopic.imageviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kankan.tutopic.cache.ImageFetcher;
import com.vjson.tutopic.R;

public class BigImageView extends RelativeLayout {
    ImageView imageView;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.image_viewer, this);
        setupViews();
    }

    private void setupViews() {
        imageView = (ImageView) findViewById(R.id.image);
    }

    public void loadImage(String url, ImageFetcher fetcher) {
        fetcher.loadImage(url, imageView);
    }

}

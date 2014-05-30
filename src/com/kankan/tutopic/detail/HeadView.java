package com.kankan.tutopic.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kankan.tutopic.cache.ImageFetcher;
import com.kankan.tutopic.imageviewer.ImgaeViewerActivity;
import com.kankan.tutopic.model.Topic;
import com.vjson.tutopic.R;

public class HeadView extends RelativeLayout {
    private ImageView imageview;
    private Topic topic;

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.detail_list_head, this);
        setupViews();
    }

    protected void setupViews() {
        imageview = (ImageView) findViewById(R.id.poster);
        setOnClickListener(clickListener);
    }

    public void populate(Topic topic, ImageFetcher fetcher) {
        this.topic = topic;
        loadPoster(topic.poster, fetcher);
    }

    private void loadPoster(String url, ImageFetcher fetcher) {
        fetcher.loadImage(url, imageview);
    }

    private final OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("topic", topic);
            ImgaeViewerActivity.launch(getContext(), bundle);
        }
    };
}

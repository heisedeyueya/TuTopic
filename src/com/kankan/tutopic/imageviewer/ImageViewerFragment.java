package com.kankan.tutopic.imageviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackmoon.tutopic.R;
import com.kankan.tutopic.base.BaseFragment;
import com.kankan.tutopic.data.Topic;
import com.polites.android.GestureImageView;

public class ImageViewerFragment extends BaseFragment {
    GestureImageView imageView;
    private Topic topic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_viewer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        topic = (Topic) args.getSerializable("topic");

        setupViews();
    }

    private void setupViews() {
        imageView = (GestureImageView) findViewById(R.id.image);
        getImageFetcher().loadImage(topic.poster, imageView);
    }
}

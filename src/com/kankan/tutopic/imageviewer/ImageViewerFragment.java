package com.kankan.tutopic.imageviewer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kankan.tutopic.base.BaseFragment;
import com.kankan.tutopic.model.Topic;
import com.vjson.tutopic.R;

public class ImageViewerFragment extends BaseFragment {
    private Topic topic;
    private ViewPager pager;
    private ImageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_pager, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        topic = (Topic) args.getSerializable("topic");

        setupViews();
    }

    private void setupViews() {
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ImageAdapter(getActivity(), getImageFetcher(), topic);
        pager.setAdapter(adapter);
    }
}

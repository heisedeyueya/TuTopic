package com.kankan.tutopic.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blackmoon.tutopic.R;
import com.kankan.tutopic.base.BaseFragment;
import com.kankan.tutopic.data.Topic;

public class DetailFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        Topic topic = (Topic) args.getSerializable("topic");

        showToast(topic.title, Toast.LENGTH_SHORT);
    }
}

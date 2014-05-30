package com.kankan.tutopic.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kankan.tutopic.base.BaseFragment;
import com.kankan.tutopic.model.Topic;
import com.kankan.tutopic.views.SwipeView;
import com.vjson.tutopic.R;

public class DetailFragment extends BaseFragment {
    private SwipeView swipeView;
    private ListView listView;
    private Topic topic;
    private CommentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        topic = (Topic) args.getSerializable("topic");

        setupViews();
    }

    private void setupViews() {
        swipeView = (SwipeView) findViewById(R.id.swipe);
        swipeView.setOnRefreshListener(listener);
        listView = (ListView) findViewById(R.id.list);
        HeadView head = new HeadView(getActivity());
        head.populate(topic, getImageFetcher());
        listView.addHeaderView(head);
        adapter = new CommentAdapter();
        listView.setAdapter(adapter);
    }

    private final OnRefreshListener listener = new OnRefreshListener() {

        @Override
        public void onRefresh() {

        }
    };
}

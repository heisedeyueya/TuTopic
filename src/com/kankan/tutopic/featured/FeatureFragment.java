package com.kankan.tutopic.featured;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blackmoon.tutopic.R;
import com.kankan.logging.Logger;
import com.kankan.tutopic.app.BaseApplication;
import com.kankan.tutopic.base.BaseFragment;
import com.kankan.tutopic.cache.ImageFetcher;
import com.kankan.tutopic.data.DataProxy;
import com.kankan.tutopic.data.Featured;
import com.kankan.tutopic.data.Topic;
import com.kankan.tutopic.detail.DetailActivity;
import com.kankan.tutopic.util.UIHelper;
import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;

public class FeatureFragment extends BaseFragment {
    private static final Logger LOG = Logger.getLogger(FeatureFragment.class);

    private StaggeredGridView staggeredGridView;
    private SwipeRefreshLayout swipeContent;
    private FeaturedLoader loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();

        loader = new FeaturedLoader();
        loader.execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        canclePotentialTask();
    }

    private void canclePotentialTask() {
        if (loader != null && !loader.isCancelled()) {
            loader.cancel(true);
        }
    }

    private void setupViews() {
        staggeredGridView = (StaggeredGridView) findViewById(R.id.staggeredGridView);
        staggeredGridView.setOnItemClickListener(onItemclickListener);

        swipeContent = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeContent.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContent.setOnRefreshListener(refreshListener);
    }

    final class FeaturedLoader extends AsyncTask<Void, Void, Featured> {
        @Override
        protected void onPreExecute() {
            swipeContent.setRefreshing(true);
        }

        @Override
        protected Featured doInBackground(Void... params) {
            Featured loadFeatured = DataProxy.getInstance().loadFeatured();

            return loadFeatured;
        }

        @Override
        protected void onPostExecute(Featured result) {
            if (!isCancelled()) {
                swipeContent.setRefreshing(false);
                ImageFetcher fetcher = getImageFetcher();
                FeatureedAdapter adapter = new FeatureedAdapter(result, fetcher);
                staggeredGridView.setAdapter(adapter);
            }
        }
    };

    private final OnRefreshListener refreshListener = new OnRefreshListener() {

        @Override
        public void onRefresh() {
            canclePotentialTask();
            loader = new FeaturedLoader();
            loader.execute();
        }
    };

    private final OnItemClickListener onItemclickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(StaggeredGridView parent, View view, int position, long id) {
            showToast("position=" + position, Toast.LENGTH_LONG);

            Bundle bundle = new Bundle();
            Topic topic = (Topic) parent.getAdapter().getItem(position);
            bundle.putSerializable("topic", topic);
            DetailActivity.launch(getActivity(), bundle);
        }
    };
}

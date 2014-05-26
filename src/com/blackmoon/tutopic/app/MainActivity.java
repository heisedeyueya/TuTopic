package com.blackmoon.tutopic.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

import com.blackmoon.tutopic.R;
import com.blackmoon.tutopic.cache.ImageFetcher;
import com.blackmoon.tutopic.data.DataProxy;
import com.blackmoon.tutopic.data.Featured;
import com.kankan.logging.Logger;
import com.origamilabs.library.views.StaggeredGridView;

public class MainActivity extends Activity {
    private static final Logger LOG = Logger.getLogger(MainActivity.class);
    StaggeredGridView staggeredGridView;
    SwipeRefreshLayout swipeContent;
    FeaturedLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staggeredGridView = (StaggeredGridView) findViewById(R.id.staggeredGridView);
        swipeContent = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeContent.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loader = new FeaturedLoader();
        loader.execute();
        swipeContent.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                LOG.debug("onRefresh");
                if (!loader.isCancelled()) {
                    loader.cancel(true);
                }

                loader = new FeaturedLoader();
                loader.execute();
            }
        });

    }

    class FeaturedLoader extends AsyncTask<Void, Void, Featured> {
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
            swipeContent.setRefreshing(false);

            ImageFetcher fetcher = ImageFetcher.getInstance(MainActivity.this);
            fetcher.setImageCache(BaseApplication.sInstance.getImageCache());
            StagedAdapter adapter = new StagedAdapter(result, fetcher);
            staggeredGridView.setAdapter(adapter);
        }

    };
}

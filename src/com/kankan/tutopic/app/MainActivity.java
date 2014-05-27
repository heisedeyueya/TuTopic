package com.kankan.tutopic.app;

import android.os.Bundle;

import com.kankan.logging.Logger;
import com.kankan.tutopic.base.BaseActivity;
import com.kankan.tutopic.featured.FeatureFragment;

public class MainActivity extends BaseActivity {
    private static final Logger LOG = Logger.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentFragment(FeatureFragment.class);
        getActionBar().setDisplayHomeAsUpEnabled(false);
    }

}

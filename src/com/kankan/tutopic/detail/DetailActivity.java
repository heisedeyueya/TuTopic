package com.kankan.tutopic.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kankan.tutopic.base.BaseActivity;

public class DetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentFragment(DetailFragment.class);
    }

    public static final void launch(Activity context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

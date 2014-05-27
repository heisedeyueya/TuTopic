package com.kankan.tutopic.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kankan.tutopic.base.BaseActivity;

public class ImgaeViewerActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentFragment(ImageViewerFragment.class);
    }

    public static void launch(Context context, Bundle args) {
        Intent intent = new Intent();
        intent.setClass(context, ImgaeViewerActivity.class);
        intent.putExtras(args);
        context.startActivity(intent);
    }
}

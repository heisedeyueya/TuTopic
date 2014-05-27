package com.kankan.tutopic.base;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.blackmoon.tutopic.R;
import com.kankan.logging.Logger;
import com.kankan.tutopic.app.ActivityManager;
import com.kankan.tutopic.util.UIHelper;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity {
    private static final Logger LOG = Logger.getLogger(BaseActivity.class);

    private OnBackListener mOnBackListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().add(this);

        LOG.debug("onCreate. [{}]", getClass().getSimpleName());

        setContentView(R.layout.content_frame);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // actionBar.setIcon(R.drawable.ac_logo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        LOG.debug("onStart. [{}]", getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        LOG.debug("onResume. [{}]", getClass().getSimpleName());

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LOG.debug("onPause. [{}]", getClass().getSimpleName());

        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        LOG.debug("onStop. [{}]", getClass().getSimpleName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        LOG.debug("onRestart. [{}]", getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LOG.debug("onDestroy. [{}]", getClass().getSimpleName());

        ActivityManager.getInstance().remove(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        LOG.debug("onNewIntent. [{}]", getClass().getSimpleName());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment = Fragment.instantiate(this, fragment.getClass().getName(), intent.getExtras());
            fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                if (mOnBackListener != null) {
                    mOnBackListener.onBackPressed();
                }
                finish();
            }
            break;

        default:
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    protected void setContentFragment(Class<? extends BaseFragment> fragmentClass) {
        Bundle arguments = null;
        if (getIntent() != null) {
            arguments = getIntent().getExtras();
        }
        setContentFragment(fragmentClass, arguments);
    }

    protected void setContentFragment(Class<? extends BaseFragment> fragmentClass, Bundle arguments) {
        LOG.debug("set content fragment. class={}", fragmentClass.getName());

        Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), arguments);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, fragment);
        t.commit();
    }

    protected void pushFragment(Class<? extends BaseFragment> fragmentClass, Bundle arguments) {
        LOG.debug("push fragment. class={}", fragmentClass.getName());

        Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), arguments);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, fragment);
        t.addToBackStack(null);
        t.commit();
    }

    protected void popFragment() {
        LOG.debug("pop fragment.");

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    protected void showToast(CharSequence text, int duration) {
        UIHelper.showToast(this, text, duration);
    }

    protected void showToast(int resId, int duration) {
        UIHelper.showToast(this, getString(resId), duration);
    }

    @Override
    public void onBackPressed() {
        boolean pressed = false;
        if (mOnBackListener != null) {
            pressed = mOnBackListener.onBackPressed();
        }
        if (!pressed) {
            super.onBackPressed();
        }
    }

    public void setKeyListener(OnBackListener listener) {
        mOnBackListener = listener;
    }

    public interface OnBackListener {
        public boolean onBackPressed();
    }
}

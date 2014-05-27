package com.kankan.tutopic.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackmoon.tutopic.R;
import com.kankan.logging.Logger;
import com.kankan.tutopic.app.BaseApplication;
import com.kankan.tutopic.cache.ImageFetcher;
import com.kankan.tutopic.util.UIHelper;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {
    private static final Logger LOG = Logger.getLogger(BaseFragment.class);

    private ImageFetcher mImageFetcher;

    public CharSequence getTitle() {
        return getActivity().getTitle();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        LOG.debug("onAttach. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LOG.debug("onCreate. [{}]", getClass().getSimpleName());

        customActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LOG.debug("onCreateView. [{}]", getClass().getSimpleName());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LOG.debug("onActivityCreated. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();

        LOG.debug("onStart. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();

        LOG.debug("onResume. [{}]", getClass().getSimpleName());

        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();

        LOG.debug("onPause. [{}]", getClass().getSimpleName());

        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();

        LOG.debug("onStop. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        LOG.debug("onDestroyView. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        LOG.debug("onDestroy. [{}]", getClass().getSimpleName());
    }

    @Override
    public void onDetach() {
        super.onDetach();

        LOG.debug("onDetach. [{}]", getClass().getSimpleName());
    }

    protected void customActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            setHasOptionsMenu(true);
        }
    }

    protected final View findViewById(int viewId) {
        return getView().findViewById(viewId);
    }

    protected void setTitle(CharSequence title) {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setTitle(int resId) {
        setTitle(getString(resId));
    }

    protected void replaceFragment(Class<?> fregmentClass, Bundle arguments) {
        LOG.debug("replace fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    protected void openFragment(Class<?> fregmentClass, Bundle arguments) {
        LOG.debug("open fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void openActivity(Class<? extends BaseActivity> activityClass, Bundle arguments) {
        LOG.debug("open activity. class={}", activityClass.getName());

        Intent intent = new Intent(getActivity(), activityClass);
        if (arguments != null) {
            intent.putExtras(arguments);
        }
        startActivity(intent);
    }

    protected void finish() {
        LOG.debug("finish.");

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            getActivity().finish();
        }

    }

    protected void showToast(CharSequence text, int duration) {
        Context context = getActivity();
        if (context != null) {
            UIHelper.showToast(context, text, duration);
        }
    }

    protected void showToast(int resId, int duration) {
        showToast(getString(resId), duration);
    }

    protected Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public ImageFetcher getImageFetcher() {
        if (mImageFetcher == null) {
            mImageFetcher = ImageFetcher.getInstance(getActivity());
            mImageFetcher.setImageCache(BaseApplication.sInstance.getImageCache());
        }

        return mImageFetcher;
    }
}

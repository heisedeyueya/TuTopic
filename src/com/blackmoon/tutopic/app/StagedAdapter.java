package com.blackmoon.tutopic.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.blackmoon.tutopic.R;
import com.blackmoon.tutopic.cache.ImageFetcher;
import com.blackmoon.tutopic.data.Featured;
import com.blackmoon.tutopic.data.Movie;

public class StagedAdapter extends BaseAdapter {
    Featured featured;
    ImageFetcher fetcher;

    public StagedAdapter(Featured featured, ImageFetcher fetcher) {
        this.featured = featured;
        this.fetcher = fetcher;
    }

    @Override
    public int getCount() {
        return featured.items.length;
    }

    @Override
    public Movie getItem(int position) {
        return featured.items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold holder = null;
        if (convertView == null) {
            holder = new ViewHold();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_staggered_demo, null);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHold) convertView.getTag();
        }
        fetcher.loadImage(getItem(position).poster, holder.image);

        return convertView;
    }

    static class ViewHold {
        ImageView image;
    }

}

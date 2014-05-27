package com.kankan.tutopic.featured;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmoon.tutopic.R;
import com.kankan.logging.Logger;
import com.kankan.tutopic.cache.ImageFetcher;
import com.kankan.tutopic.data.Featured;
import com.kankan.tutopic.data.Topic;

public class FeatureedAdapter extends BaseAdapter {
    private static Logger LOG = Logger.getLogger(FeatureedAdapter.class);

    Featured featured;
    ImageFetcher fetcher;

    public FeatureedAdapter(Featured featured, ImageFetcher fetcher) {
        this.featured = featured;
        this.fetcher = fetcher;
    }

    @Override
    public int getCount() {
        return featured.items == null ? 0 : featured.items.length;
    }

    @Override
    public Topic getItem(int position) {
        return featured.items == null ? null : featured.items[position];
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_staggered_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.image = (ImageView) convertView.findViewById(R.id.poster);
            convertView.setTag(holder);
        } else {
            holder = (ViewHold) convertView.getTag();
        }
        holder.populate(fetcher, getItem(position), position);

        return convertView;
    }

    static class ViewHold {
        ImageView image;
        TextView title;

        private void populate(ImageFetcher fetcher, Topic tp, int position) {
            if (tp != null) {
                fetcher.loadImage(tp.poster, image);
                title.setText(tp.title);
            } else {
                LOG.warn("topic == null, position={}", position);
            }
        }
    }

}

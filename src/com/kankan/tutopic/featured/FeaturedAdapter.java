package com.kankan.tutopic.featured;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kankan.logging.Logger;
import com.kankan.tutopic.cache.ImageFetcher;
import com.kankan.tutopic.cache.OnLoadImageListener;
import com.kankan.tutopic.model.Featured;
import com.kankan.tutopic.model.Topic;
import com.vjson.tutopic.R;

public class FeaturedAdapter extends BaseAdapter {
    private static Logger LOG = Logger.getLogger(FeaturedAdapter.class);

    ArrayList<Topic> topics;
    ImageFetcher fetcher;

    public FeaturedAdapter(ImageFetcher fetcher) {
        this.fetcher = fetcher;
        topics = new ArrayList<Topic>();
    }

    public void append(Featured featured) {
        for (Topic t : featured.items) {
            topics.add(t);
            notifyDataSetChanged();
        }
    }

    public void refresh(Featured featured) {
        clear();
        append(featured);
    }

    public void clear() {
        topics.clear();
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Topic getItem(int position) {
        return topics.get(position);
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
                fetcher.setImageLoadListener(new OnLoadImageListener() {

                    @Override
                    public void onLoadCompleted(String imageUrl, ImageView targetView, int state) {
                        
                    }
                });
                title.setText(tp.title);
            } else {
                LOG.warn("topic == null, position={}", position);
            }
        }
    }

}

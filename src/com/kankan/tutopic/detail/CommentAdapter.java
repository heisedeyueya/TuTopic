package com.kankan.tutopic.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vjson.tutopic.R;

public class CommentAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, null);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHold) convertView.getTag();
        }

        holder.populate("xxxxx");

        return convertView;
    }

    private static final class ViewHold {
        TextView comment;

        public void populate(String comm) {
            comment.setText(comm);
        }
    }

}

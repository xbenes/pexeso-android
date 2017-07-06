package com.xbenes2.pexeso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private int[] imageResources;

    public ImageAdapter(Context c) {
        context = c;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return imageResources.length;
    }

    public Object getItem(int position) {
        return imageResources[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.image_item, parent, false);
            view.setTag(R.id.square_image, view.findViewById(R.id.square_image));
        } else {
            view = convertView;
        }

        ImageView imageView = (ImageView) view.getTag(R.id.square_image);
        imageView.setImageResource(imageResources[position]);

        view.setTag(imageResources[position]);
        return view;
    }

    public void setImages(int[] images) {
        this.imageResources = images;

        // force repaint
        notifyDataSetChanged();
    }
}
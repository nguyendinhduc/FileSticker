package com.t3h.filesticker.main.image;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.t3h.filesticker.GlideApp;
import com.t3h.filesticker.R;

import java.io.File;
import java.util.List;

/**
 * Created by ducnd on 4/25/18.
 */

public class ImageAdapter extends BaseAdapter {
    private List<ImageData> imageDatas;

    public ImageAdapter(List<ImageData> imageDatas) {
        this.imageDatas = imageDatas;
    }

    @Override
    public int getCount() {
        if (imageDatas == null) {
            return 0;
        }
        return imageDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return imageDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        }
        ImageView image = view.findViewById(R.id.iv_image);
        ImageData imageData = imageDatas.get(position);
        GlideApp.with(image)
                .load(new File(imageData.getPath()))
                .error(R.drawable.ic_photo_size_select_actual_blue_200_48dp)
                .placeholder(R.drawable.ic_photo_size_select_actual_blue_200_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        return view;
    }
}

package com.t3h.filesticker.main.zip;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.t3h.filesticker.R;

import java.util.List;

/**
 * Created by ducnd on 4/26/18.
 */

public class ZipAdapter extends BaseAdapter {
    private List<ZipData> zipDatas;

    public ZipAdapter(List<ZipData> zipDatas) {
        this.zipDatas = zipDatas;
    }

    @Override
    public int getCount() {
        if (zipDatas == null) {
            return 0;
        }
        return zipDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return zipDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_zip, viewGroup, false);
        }
        TextView tvPath = view.findViewById(R.id.tv_path);
        TextView tvSize = view.findViewById(R.id.tv_size);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ZipData zipData = zipDatas.get(position);
        tvPath.setText(zipData.getPath());
        tvSize.setText(zipData.getSizeKB() + "KB");
        tvTitle.setText(zipData.getTitle());
        return view;
    }
}

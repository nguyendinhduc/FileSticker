package com.t3h.filesticker.main.doc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.t3h.filesticker.R;
import com.t3h.filesticker.main.zip.ZipData;

import java.util.List;

/**
 * Created by ducnd on 4/26/18.
 */

public class DocAdapter extends BaseAdapter {
    private List<DocData> docDatas;

    public DocAdapter(List<DocData> docDatas) {
        this.docDatas = docDatas;
    }

    @Override
    public int getCount() {
        if (docDatas == null) {
            return 0;
        }
        return docDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return docDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doc, viewGroup, false);
        }
        TextView tvPath = view.findViewById(R.id.tv_path);
        TextView tvSize = view.findViewById(R.id.tv_size);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        DocData docData = docDatas.get(position);
        tvPath.setText(docData.getPath());
        tvSize.setText(docData.getSizeKB() + "KB");
        tvTitle.setText(docData.getTitle());
        return view;
    }
}

package com.t3h.filesticker.main.image;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t3h.filesticker.databinding.FragmentImageBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 4/25/18.
 */

public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;
    private List<ImageData> imageDatas;
    private ImageAdapter adapter;
    private AsyncTask<Void, Void, List<ImageData>> loadImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater, container, false);
        initViews();
        return binding.gridImg;
    }

    private void initViews() {
        imageDatas = new ArrayList<>();
        adapter = new ImageAdapter(imageDatas);
        binding.gridImg.setAdapter(adapter);

        loadImage();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadImage() {
        loadImage = new AsyncTask<Void, Void, List<ImageData>>() {
            @Override
            protected List<ImageData> doInBackground(Void... voids) {
//                MediaStore.Images.Media.DATE_MODIFIED
                Cursor c = getContext().getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        MediaStore.Images.Media.DATE_ADDED + " DESC"
                );
                List<ImageData> imageDatas = new ArrayList<>();
                if (c != null) {
                    int indexData = c.getColumnIndex(MediaStore.Images.Media.DATA);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        String path = c.getString(indexData);
                        imageDatas.add(new ImageData(path));
                        c.moveToNext();
                    }
                    c.close();
                }
                return imageDatas;
            }

            @Override
            protected void onPostExecute(List<ImageData> imageData) {
                ImageFragment.this.imageDatas.clear();
                ImageFragment.this.imageDatas.addAll(imageData);
                adapter.notifyDataSetChanged();
                loadImage = null;
            }
        };
        loadImage.execute();
    }

    @Override
    public void onDestroyView() {
        if (loadImage != null) {
            loadImage.cancel(true);
            loadImage = null;
        }
        super.onDestroyView();
    }
}

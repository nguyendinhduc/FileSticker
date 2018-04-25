package com.t3h.filesticker.main.zip;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t3h.filesticker.databinding.FragmentZipBinding;
import com.t3h.filesticker.main.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 4/25/18.
 */

public class ZipFragment extends Fragment {
    private FragmentZipBinding binding;
    private List<ZipData> zipDatas;
    private ZipAdapter adapter;
    private AsyncTask<Void, Void, List<ZipData>> loadAsyn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentZipBinding.inflate(inflater, container, false);
        initViews();
        return binding.lvImg;
    }

    private void initViews() {
        zipDatas = new ArrayList<>();
        adapter = new ZipAdapter(zipDatas);
        binding.lvImg.setAdapter(adapter);
        loadDatas();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadDatas() {
        loadAsyn = new AsyncTask<Void, Void, List<ZipData>>() {
            @Override
            protected List<ZipData> doInBackground(Void... voids) {
                List<String> listArg = new ArrayList<>();
                listArg.add("application/x-rar-compressed");
                listArg.add("application/zip");
                Cursor c = getContext().getContentResolver().query(
                        MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE},
                        MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? OR " +
                                MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ?"
                        ,
                        new String[]{"application/x-rar-compressed", "application/x-rar-compressed", "application/zip", "application/zip"},
                        MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
                );
                List<ZipData> zipDatas = new ArrayList<>();
                if (c != null) {
                    int indexData = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    int indexSize = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                    int indexTitle = c.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        String path = c.getString(indexData);
                        long size = c.getLong(indexSize) / 1024;
                        String title = c.getString(indexTitle);
                        zipDatas.add(new ZipData(title, path, size));
                        c.moveToNext();
                    }
                    c.close();

                }
                return zipDatas;
            }

            @Override
            protected void onPostExecute(List<ZipData> zipData) {
                zipDatas.clear();
                zipDatas.addAll(zipData);
                adapter.notifyDataSetChanged();
                loadAsyn = null;
            }
        };
        loadAsyn.execute();
    }

    @Override
    public void onDestroyView() {
        if (loadAsyn != null) {
            loadAsyn.cancel(true);
            loadAsyn = null;
        }
        super.onDestroyView();
    }
}

package com.t3h.filesticker.main.doc;

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

import com.t3h.filesticker.databinding.FragmentDocBinding;
import com.t3h.filesticker.main.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 4/25/18.
 */

public class DocFragment extends Fragment {
    private FragmentDocBinding binding;
    private List<DocData> docDatas;
    private DocAdapter adapter;
    private AsyncTask<Void, Void, List<DocData>> loadAsyn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDocBinding.inflate(inflater, container, false);
        initViews();
        loadDatas();
        return binding.lvDoc;
    }

    private void initViews() {
        docDatas = new ArrayList<>();
        adapter = new DocAdapter(docDatas);
        binding.lvDoc.setAdapter(adapter);

    }

    @SuppressLint("StaticFieldLeak")
    private void loadDatas() {
        loadAsyn = new AsyncTask<Void, Void, List<DocData>>() {
            @Override
            protected List<DocData> doInBackground(Void... voids) {
                Cursor c = getContext().getContentResolver().query(
                        MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE},
                        MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? OR " +
                                MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? OR " +
                                MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? OR " +
                                MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? OR " +
                                MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? OR " + MediaStore.Files.FileColumns.MIME_TYPE + " = ?"
                        ,
                        new String[]{
                                "text/plain", "text/plain",
                                "text/csv", "text/csv",
                                "text/xml", "text/xml",
                                "application/msword", "application/msword",
                                "application/vnd.openxmlformats-", "application/vnd.openxmlformats-"
                        },
                        MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
                );
                List<DocData> docDatas = new ArrayList<>();
                if (c != null) {
                    int indexData = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    int indexSize = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                    int indexTitle = c.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        String path = c.getString(indexData);
                        long size = c.getLong(indexSize) / 1024;
                        String title = c.getString(indexTitle);
                        docDatas.add(new DocData(title, path, size));
                        c.moveToNext();
                    }
                    c.close();

                }
                return docDatas;
            }

            @Override
            protected void onPostExecute(List<DocData> docData) {
                docDatas.clear();
                docDatas.addAll(docData);
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
        super.onDestroyView();
    }
}

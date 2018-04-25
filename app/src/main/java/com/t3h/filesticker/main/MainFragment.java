package com.t3h.filesticker.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t3h.filesticker.databinding.FragmentMainBinding;

/**
 * Created by ducnd on 4/25/18.
 */

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private MainAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        intiViews();
        return binding.getRoot();
    }

    private void intiViews() {
        adapter = new MainAdapter(getChildFragmentManager());
        binding.page.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.page);
    }
}

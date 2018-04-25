package com.t3h.filesticker.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.t3h.filesticker.main.doc.DocFragment;
import com.t3h.filesticker.main.image.ImageFragment;
import com.t3h.filesticker.main.zip.ZipFragment;

/**
 * Created by ducnd on 4/25/18.
 */

public class MainAdapter extends FragmentPagerAdapter {
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ImageFragment();
        }
        if (position == 1) {
            return new ZipFragment();
        }
        return new DocFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Image";
        }
        if (position == 1) {
            return "Zip";
        }
        return "Doc";
    }
}

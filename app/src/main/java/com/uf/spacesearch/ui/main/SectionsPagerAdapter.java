package com.uf.spacesearch.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uf.spacesearch.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    JSONArray images;
    ArrayList<Fragment> fragments = new ArrayList<>();
    public HashMap<Integer, Fragment> fragmentHashMap = new HashMap();
    public SectionsPagerAdapter(Context context, FragmentManager fm, JSONArray jsonArray) {
        super(fm);
        mContext = context;
        images = jsonArray;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if(!fragmentHashMap.containsKey(position)) {
            Fragment fragment = PlaceholderFragment.newInstance(position + 1, images);
            fragmentHashMap.put(position, fragment);
            return fragment;
        }
        return fragmentHashMap.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
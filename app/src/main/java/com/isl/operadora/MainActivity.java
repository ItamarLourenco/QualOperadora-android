package com.isl.operadora;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new PagersAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.mipmap.ic_launcher);
        supportInvalidateOptionsMenu();
    }

    public class PagersAdapter extends FragmentPagerAdapter {

        private final String[] pages = { "Categories", "Home", "Top Paid" };

        public PagersAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pages[position];
        }

        @Override
        public int getCount() {
            return pages.length;
        }

        @Override
        public Fragment getItem(int position) {
            return new Fragment();
        }

    }
}


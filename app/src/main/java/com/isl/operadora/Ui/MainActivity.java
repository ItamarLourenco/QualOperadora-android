package com.isl.operadora.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.isl.operadora.Base.BaseActionBarActivity;
import com.isl.operadora.R;
import com.isl.operadora.Widgets.CustomFontTextView;


public class MainActivity extends BaseActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new PagersAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
    }

    private void setActionBar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.action_bar, null);
        CustomFontTextView title = (CustomFontTextView) v.findViewById(R.id.title);
        title.setText(getTitle());
        title.setTypeface(getString(R.string.opensansregular));
        getSupportActionBar().setCustomView(v);
    }

    public class PagersAdapter extends FragmentPagerAdapter {

        private final String[] pages = {
                getString(R.string.titleContatos),
                getString(R.string.titleConsults),
                getString(R.string.titleLigacoes)
        };

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
            switch (position){
                case 0: {
                    return ContactsFragment.newInstance();
                }
                case 1: {
                    return ConsultsFragment.newInstance();
                }
                case 2: {
                    return CallFragment.newInstance();
                }

                default:{
                    return ConsultsFragment.newInstance();
                }
            }
        }
    }
}


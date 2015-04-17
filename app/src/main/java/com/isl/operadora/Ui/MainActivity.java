package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.isl.operadora.Adapter.DddAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseActionBarActivity;
import com.isl.operadora.R;
import com.isl.operadora.Util.Util;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MainActivity extends BaseActionBarActivity{

    private AlertDialog mDialogDDD;
    public ArrayList<String> mDdds = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new PagersAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);

        checkIfConfiguredDdd();

    }

    private void setActionBar(){
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.action_bar, null);
        final CustomFontTextView title = (CustomFontTextView) view.findViewById(R.id.title);
        title.setText(getTitle());
        title.setTypeface(getString(R.string.opensansregular));

        final ImageView imageSearch = (ImageView) view.findViewById(R.id.imageSearch);

        AppController.getInstance().search = (EditText) view.findViewById(R.id.search);
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getVisibility() == View.VISIBLE) {
                    imageSearch.setImageResource(R.drawable.ic_action_remove);
                    title.setVisibility(View.GONE);
                    AppController.getInstance().search.setVisibility(View.VISIBLE);
                    AppController.getInstance().search.requestFocus();
                    Util.showKeyboard();
                } else {
                    title.setVisibility(View.VISIBLE);
                    AppController.getInstance().search.setVisibility(View.GONE);
                    Util.hideKeyboard(AppController.getInstance().search);
                    imageSearch.setImageResource(R.drawable.ic_action_search);
                }
            }
        });
        getSupportActionBar().setCustomView(view);
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

    private void checkIfConfiguredDdd(){
        if(TextUtils.isEmpty(AppController.getInstance().mDdd.getDDD()))
        {
            mDialogDDD = new AlertDialog.Builder(AppController.getInstance()).create();
            View view = getLayoutInflater().inflate(R.layout.list_ddd, null);
            mDialogDDD = new AlertDialog.Builder( this ).create();
            mDialogDDD.setView(view);
            mDialogDDD.setInverseBackgroundForced(true);

            for(int i=11; i<=99; i++)
                mDdds.add(String.valueOf(i));

            ListView listDDD = (ListView) view.findViewById(R.id.listDDD);
            listDDD.setAdapter(new DddAdapter(this, mDdds));

            mDialogDDD.show();
        }
        return;
    }

    public void onClickListView(int position)
    {
        String ddd = mDdds.get(position);
        AppController.getInstance().mDdd.saveDdd(ddd);
        Crouton.makeText(this, getString(R.string.dddAddWithSuccess, ddd), Style.INFO).show();
        if(mDialogDDD.isShowing())
            mDialogDDD.dismiss();
    }
}


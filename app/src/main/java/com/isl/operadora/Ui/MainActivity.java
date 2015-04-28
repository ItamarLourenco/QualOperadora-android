package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.isl.operadora.Adapter.DddAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseActionBarActivity;
import com.isl.operadora.R;
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Util;
import com.isl.operadora.Widgets.CustomFontTextView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MainActivity extends BaseActionBarActivity{

    private AlertDialog mDialogDDD;
    public String[] mDdds;
    public SharedPreferences.Editor mEditorPreferences;
    public InterstitialAd mInterstitialAd;
    public CountDownTimer countDownTimerForAdMob;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new PagersAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);

        startAdMobFullScreen();
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
                    AppController.getInstance().search.setText("");
                    title.setVisibility(View.VISIBLE);
                    AppController.getInstance().search.setVisibility(View.GONE);
                    Util.hideKeyboard(AppController.getInstance().search);
                    imageSearch.setImageResource(R.drawable.ic_action_search);
                }
            }
        });

        final ImageView imageSetting = (ImageView) view.findViewById(R.id.imageSetting);
        imageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSetting(v);
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
        if(TextUtils.isEmpty(AppController.getInstance().mSharedPreferences.getString(Preferences.settingDDD, "")))
        {
            mEditorPreferences = AppController.getInstance().mSharedPreferences.edit();
            mEditorPreferences.putBoolean(Preferences.settingNotification, true);
            mEditorPreferences.putBoolean(Preferences.settingToast, true);
            mEditorPreferences.apply();

            mDialogDDD = new AlertDialog.Builder(AppController.getInstance()).create();
            View view = getLayoutInflater().inflate(R.layout.list_ddd, null);
            mDialogDDD = new AlertDialog.Builder( this ).create();
            mDialogDDD.setView(view);
            mDialogDDD.setInverseBackgroundForced(true);
            mDdds = getResources().getStringArray(R.array.listOptions);
            ListView listDDD = (ListView) view.findViewById(R.id.listDDD);
            listDDD.setAdapter(new DddAdapter(this, mDdds));

            mDialogDDD.show();
        }
        return;
    }

    public void onClickListView(int position)
    {
        String ddd = mDdds[position];
        mEditorPreferences = AppController.getInstance().mSharedPreferences.edit();
        mEditorPreferences.putString(Preferences.settingDDD, ddd);
        mEditorPreferences.apply();
        Crouton.makeText(this, getString(R.string.dddAddWithSuccess, ddd), Style.INFO).show();
        if(mDialogDDD.isShowing())
            mDialogDDD.dismiss();
    }

    public void onClickSetting(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_main);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.setting:
                        startActivity(Preferences.newContent(MainActivity.this));
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void startAdMobFullScreen()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AppController.pubAdMob);
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                countDownTimerForAdMob.start();
                requestNewInterstitial();
            }
        });
        countDownTimerForAdMob = new CountDownTimer(AppController.timeOnMinutesAdMobFulLScreen, 60000){

            @Override
            public void onTick(long millisUntilFinished) {
                Logger.d("CountDown = " + millisUntilFinished + " Finish = " + AppController.timeOnMinutesAdMobFulLScreen);
            }

            @Override
            public void onFinish() {
                mInterstitialAd.show();
            }
        };
        countDownTimerForAdMob.start();
    }


    private void requestNewInterstitial() {
        mInterstitialAd.loadAd(AppController.getInstance().getAdRequest());
    }
}


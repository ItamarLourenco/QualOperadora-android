package com.isl.operadora.Ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseActionBarActivity;
import com.isl.operadora.R;
import com.isl.operadora.Widgets.CustomFontTextView;

/**
 * Created by webx on 23/04/15.
 */
public class Preferences extends BaseActionBarActivity {

    public static final String settingNotification = "showNotification";
    public static final String settingToast = "showToast";
    public static final String settingDDD = "settingDDD";

    public static Intent newContent(Context context){
        return new Intent(context, Preferences.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        PrefsFragment mPrefsFragment = new PrefsFragment();
        mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
        mFragmentTransaction.commit();


        setActionBar();
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    private void setActionBar()
    {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.action_bar, null);
        final CustomFontTextView title = (CustomFontTextView) view.findViewById(R.id.title);
        title.setText(R.string.settings);
        title.setTypeface(getString(R.string.opensansregular));
        view.findViewById(R.id.imageSearch).setVisibility(View.GONE);
        view.findViewById(R.id.imageSetting).setVisibility(View.GONE);
        if(AppController.getInstance().search != null)
        {
            AppController.getInstance().search.setVisibility(View.GONE);
        }
        getSupportActionBar().setCustomView(view);
    }
}

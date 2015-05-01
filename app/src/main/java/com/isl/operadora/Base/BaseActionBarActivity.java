package com.isl.operadora.Base;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by webx on 08/04/15.
 */
public class BaseActionBarActivity extends ActionBarActivity {
    private static boolean activityVisible;


    @Override
    public void onPause() {
        BaseActionBarActivity.activityPaused();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseActionBarActivity.activityResumed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}

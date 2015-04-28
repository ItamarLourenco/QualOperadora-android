package com.isl.operadora.Util;

import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isl.operadora.R;

import java.util.HashMap;

/**
 * Created by webx on 27/04/15.
 */
public class GoogleAnalyticsUtil {
    private static boolean isEnabled = false;
    private static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    private static Tracker tracker;

    public enum TrackerName {
        APP_TRACKER
    }

    public static void enableTracker(Context context) {
        tracker = getTracker(TrackerName.APP_TRACKER, context);
        if (tracker != null) {
            isEnabled = true;
        }
    }

    private static synchronized Tracker getTracker(TrackerName trackerId, Context context) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            Tracker t = null;

            if (trackerId == TrackerName.APP_TRACKER) {
                t = analytics.newTracker(R.xml.app_tracker);
            }

            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    public static void eventPortabilityQuery(String phone) {
        if (!isEnabled) {
            return;
        }

        if (!TextUtils.isEmpty(phone)) {
            String analyticsCategory = "Consultas";
            String analyticsAction = "Telefone";
            String analyticsLabel = phone;
            tracker.send(new HitBuilders.EventBuilder().setCategory(analyticsCategory).setAction(analyticsAction).setLabel(analyticsLabel).build());
        }
    }
}

package com.isl.operadora.Application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.isl.operadora.Ui.Preferences;
import io.fabric.sdk.android.Fabric;


/**
 * Created by webx on 08/04/15.
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static AppController appController;
    private RequestQueue requestQueue;
    public static final Gson GSON = new Gson();
    public EditText search;
    public SharedPreferences mSharedPreferences;

    public final static String pubAdMob = "ca-app-pub-2541702994665550/3303361023";

    @Override
    public void onCreate(){
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appController = this;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    }

    public static synchronized AppController getInstance(){
        return appController;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public String getDDD(){
        return AppController.getInstance().mSharedPreferences.getString(Preferences.settingDDD, "");
    }


}
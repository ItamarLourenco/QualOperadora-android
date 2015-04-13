package com.isl.operadora.Application;

import android.app.Application;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isl.operadora.SharedPreferences.Ddd;

/**
 * Created by webx on 08/04/15.
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static AppController appController;
    private RequestQueue requestQueue;
    public static final Gson GSON = new Gson();
    public Ddd mDdd;
    public EditText search;

    public final static String pubAdMob = "pub-2541702994665550";

    @Override
    public void onCreate(){
        super.onCreate();
        appController = this;

        mDdd = new Ddd();
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

}
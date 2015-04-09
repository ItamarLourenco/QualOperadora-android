package com.isl.operadora.Application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by webx on 08/04/15.
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static AppController appController;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(){
        super.onCreate();
        appController = this;
    }

    public static synchronized AppController getContext(){
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
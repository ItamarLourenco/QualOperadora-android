package com.isl.operadora.Util;

import android.util.Log;
import android.widget.Toast;

import com.isl.operadora.Application.AppController;

/**
 * Created by webx on 08/04/15.
 */
public class Logger {

    private static final String TAG = "QualOperadora - DEBUG";

    public static void d(Object message) {
        Log.d(TAG, String.valueOf(message));
    }

    public static void e(Object message) {
        Log.e(TAG, String.valueOf(message));
    }

    public static void i(Object message) {
        Log.i(TAG, String.valueOf(message));
    }

    public static void w(Object message) {
        Log.w(TAG, String.valueOf(message));
    }

    public static void v(Object message) {
        Log.v(TAG, String.valueOf(message));
    }

    public static void t(Object message){
        try{
            Toast.makeText(AppController.getContext(), String.valueOf(message), Toast.LENGTH_LONG).show();
            d(String.valueOf(message));
        }catch(RuntimeException e){}
    }

}

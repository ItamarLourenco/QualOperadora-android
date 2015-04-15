package com.isl.operadora.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.isl.operadora.Application.AppController;

/**
 * Created by itamarlourenco on 11/04/15.
 */
public class Ddd {
    private SharedPreferences sharedPreferences;

    private final String sharedDdd = "sharedDDD";
    private final String DDD = "DDD";

    public Ddd(){
        sharedPreferences = AppController.getInstance().getSharedPreferences(sharedDdd, Context.MODE_PRIVATE);
    }

    public void saveDdd(String ddd){
        sharedPreferences.edit().putString(DDD, ddd).commit();
    }

    public String getDDD(){
        return sharedPreferences.getString(DDD, "");
    }
}

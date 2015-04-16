package com.isl.operadora.Model;

import android.database.Cursor;
import android.provider.CallLog;

import com.isl.operadora.Application.AppController;

import java.util.ArrayList;

/**
 * Created by webx on 15/04/15.
 */
public class Calls {
    private String number;
    private int duration;
    private static ArrayList<Calls> mCalls;

    public Calls(String number, int duration){
        this.number = number;
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static ArrayList<Calls> getCalls()
    {
        mCalls = new ArrayList<Calls>();
        Cursor calls = AppController.getInstance().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                CallLog.Calls._ID + " ASC");


        if(calls != null)
        {
            calls.moveToFirst();
            while(calls.moveToNext())
            {
                String number = calls.getString(calls.getColumnIndexOrThrow(CallLog.Calls.NUMBER));
                int duration = calls.getInt(calls.getColumnIndex(CallLog.Calls.DURATION));
                mCalls.add(new Calls(number, duration));
            }
            calls.close();
        }
        return mCalls;
    }
}

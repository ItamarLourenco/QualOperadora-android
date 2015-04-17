package com.isl.operadora.Model;

import android.database.Cursor;
import android.provider.CallLog;

import com.isl.operadora.Application.AppController;
import com.isl.operadora.Util.Util;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by webx on 15/04/15.
 */
public class Calls {
    private String number;
    private String duration;
    private int type;
    private String name;
    private String when;
    private String carrier;
    private static ArrayList<Calls> mCalls;

    public Calls(String number, String duration, int type, String name, String when){
        this.number = number;
        this.duration = duration;
        this.type = type;
        this.name = name;
        this.when = when;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getWhen(){
        return when;
    }

    public void setWhen(String when){
        this.when = when;
    }

    public void setCarrier(String carrier){
        this.carrier = carrier;
    }

    public String getCarrier(){
        return carrier;
    }

    public static ArrayList<Calls> getCalls()
    {
        mCalls = new ArrayList<Calls>();

        Cursor managedCursor = AppController.getInstance().getContentResolver().query(CallLog.Calls.CONTENT_URI,null, null,null, CallLog.Calls._ID + " DESC");
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        int contact = managedCursor.getColumnIndex( CallLog.Calls.CACHED_NAME );

        while ( managedCursor.moveToNext() )
        {
            String phNumber = managedCursor.getString( number );
            int callType = managedCursor.getInt(type);
            String callDate = managedCursor.getString( date );
            String name = managedCursor.getString(contact);
            Date callDayTime = new Date(Long.valueOf(callDate));

            String callDuration = managedCursor.getString( duration );
            mCalls.add(new Calls(phNumber, callDuration, callType, name, Util.dateToString(callDayTime)));
        }
        managedCursor.close();
        return mCalls;
    }
}

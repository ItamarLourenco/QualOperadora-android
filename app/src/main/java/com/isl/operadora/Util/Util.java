package com.isl.operadora.Util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.isl.operadora.Application.AppController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by webx on 08/04/15.
 */
public class Util {
    public static String getTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final String formatPhone(String phone, String ddd){
        if(!TextUtils.isEmpty(phone))
        {
            phone = phone.replace(" ", "").replace("-", "").replace("+55", "").replaceAll("[ ./-]", "");
            if(phone.substring(0, 1).equals("0"))
            {
                phone = new StringBuffer(phone).deleteCharAt(0).toString();
            }
            if(phone.length() == 9)
            {
                phone = ddd+phone;
            }
            if(phone.length() == 8)
            {
                phone = ddd+phone;
            }
        }

        return phone;
    }

    public static boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) AppController.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void hideKeyboard(EditText editText)
    {
        InputMethodManager imm = (InputMethodManager) AppController.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

     public static String addLabel(String label, String carrier)
     {
        String str;
        if(TextUtils.isEmpty(label))
        {
            str = "(" + carrier + ")";
        }
        else
        {
            Pattern regex = Pattern.compile("\\(.+?\\)");
            if(regex.matcher(label).find())
            {
                str = label.replaceAll("\\(.+?\\)", "("+carrier+")");
            }
            else
            {
                str = label + " ("+carrier+")";
            }

        }
        return str;
     }

    public static String dateToString(java.util.Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " "
                + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

    }
}

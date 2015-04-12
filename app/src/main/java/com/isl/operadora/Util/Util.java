package com.isl.operadora.Util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.isl.operadora.Application.AppController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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

        return phone;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

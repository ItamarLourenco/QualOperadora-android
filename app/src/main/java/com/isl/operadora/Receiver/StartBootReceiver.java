package com.isl.operadora.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.isl.operadora.Util.Logger;

/**
 * Created by webx on 22/04/15.
 */
public class StartBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Logger.t("ABRIU!");
        }
    }
}
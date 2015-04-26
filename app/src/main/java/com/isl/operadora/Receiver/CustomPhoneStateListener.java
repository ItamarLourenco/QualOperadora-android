package com.isl.operadora.Receiver;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by webx on 22/04/15.
 */
public class CustomPhoneStateListener extends PhoneStateListener {

    public void onCallStateChange(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                break;
        }
    }
}
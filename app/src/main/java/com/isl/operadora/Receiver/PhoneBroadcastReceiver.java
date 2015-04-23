package com.isl.operadora.Receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Ui.DialogCustom;
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Util;

/**
 * Created by webx on 22/04/15.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver implements View.OnClickListener {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        Bundle bundle = intent.getExtras();
        final String phoneNr = bundle.getString("incoming_number");

        switch (telephony.getCallState()){
            case TelephonyManager.CALL_STATE_RINGING:
                if(!TextUtils.isEmpty(phoneNr))
                {
                    String number = Util.formatPhone(phoneNr, "");
                    new ContactRequest(new String[]{number})
                    {
                        @Override
                        public void onFinish(Portabily.PushPortabily portabily)
                        {
                            if (portabily != null) {
                                for (Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                                {
                                    LayoutInflater inflater = (LayoutInflater) AppController.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    AlertDialog mDialog = new AlertDialog.Builder(AppController.getInstance()).create();
                                    View view = inflater.inflate(R.layout.dialog_search_contacts, null);
                                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                    mDialog.setView(view);
                                    DialogCustom.loadViews(view, PhoneBroadcastReceiver.this);
                                    mDialog.setInverseBackgroundForced(false);
                                    mDialog.show();

//                                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AppController.getInstance())
//                                        .setSmallIcon(R.mipmap.ic_launcher)
//                                        .setContentTitle(phoneNr +" - "+ Contact.getContactDisplayNameByNumber(phoneNr))
//                                        .setContentText(dataPortabily.getOperadora());
//
//                                    NotificationManager mNotificationManager = (NotificationManager) AppController.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
//                                    mNotificationManager.notify(0, mBuilder.build());

                                }
                            }
                        }
                    };
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                Logger.t("DESLIGOU");
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}

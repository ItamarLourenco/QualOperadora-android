package com.isl.operadora.Receiver;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Ui.Preferences;
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Util;

/**
 * Created by webx on 22/04/15.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver implements View.OnClickListener {
    public AlertDialog mDialog;
    public boolean mCheckIsNotification;
    public boolean mCheckIsToast;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        Bundle bundle = intent.getExtras();
        final String phoneNr = bundle.getString("incoming_number");
        mCheckIsNotification = AppController.getInstance().mSharedPreferences.getBoolean(Preferences.settingNotification, true);
        mCheckIsToast = AppController.getInstance().mSharedPreferences.getBoolean(Preferences.settingToast, true);

        switch (telephony.getCallState()){
            case TelephonyManager.CALL_STATE_RINGING:
                if(!TextUtils.isEmpty(phoneNr) && (mCheckIsNotification == true || mCheckIsToast == true))
                {
                    final String number = Util.formatPhone(phoneNr, "");
                    new ContactRequest(new String[]{number})
                    {
                        @Override
                        public void onFinish(Portabily.PushPortabily portabily)
                        {
                            if(portabily != null)
                            {
                                for (Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                                {
                                    showDialog(dataPortabily);
                                }
                            }
                        }
                    };
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                break;
        }
    }

    private void showDialog(Portabily.PushPortabily.DataPortabily dataPortabily)
    {
        if(mCheckIsToast == true)
        {
            LayoutInflater inflater = (LayoutInflater) AppController.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            AppController.getInstance().getApplicationContext().setTheme(R.style.Theme_Qualoperadora);
            View view = inflater.inflate(R.layout.dialog_toast_on_call, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.carrieImage);
            imageView.setImageResource(
                    Carries.getCarreiImage(dataPortabily.getRn1())
            );

            TextView carrie = (TextView) view.findViewById(R.id.carrie);
            carrie.setText(dataPortabily.getOperadora());
            Toast toast = new Toast(AppController.getInstance());
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
        }


        if(mCheckIsNotification == true)
        {
            try {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AppController.getInstance())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(Contact.getContactDisplayNameByNumber(dataPortabily.getPhone()) + " - " + dataPortabily.getPhone())
                        .setContentText(dataPortabily.getOperadora());
                NotificationManager mNotificationManager = (NotificationManager) AppController.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }catch(IllegalArgumentException e){
                Logger.d("Erro Show notification");
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.logout:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;
        }
    }
}

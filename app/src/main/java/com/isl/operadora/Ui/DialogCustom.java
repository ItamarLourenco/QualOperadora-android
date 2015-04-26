package com.isl.operadora.Ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Widgets.CustomFontTextView;

/**
 * Created by webx on 21/04/15.
 */
public class DialogCustom{

    public static AdView adView;
    public static LinearLayout mLoadingLinear;
    public static LinearLayout mCarriesLinear;
    public static LinearLayout mNotFound;
    public static LinearLayout wLabelCanvas;
    public static LinearLayout mLabelCarrieOld;
    public static LinearLayout layoutAdmmob;
    public static LinearLayout mLabelContact;
    public static LinearLayout mLabelPhone;

    public static CustomFontTextView mCarrie;
    public static CustomFontTextView mPortabilty;
    public static CustomFontTextView mWhen;
    public static CustomFontTextView mCarrieOld;
    public static CustomFontTextView mContact;
    public static CustomFontTextView mPhone;
    public static CustomFontTextView mLoadingText;

    public static ButtonFlat mButtonLougout;
    public static ButtonFlat mButtonLougoutNotFound;
    public static ButtonFlat mButtonSave;
    public static ButtonFlat mButtonCall;
    public static ButtonFlat mSmsCall;
    private static boolean adMobLoaded = false;

    public static ImageView mCarrieImage;

    public static void loadViews(View view, View.OnClickListener fragment)
    {
        mLoadingLinear = (LinearLayout) view.findViewById(R.id.loading);
        mCarriesLinear = (LinearLayout) view.findViewById(R.id.carries);

        mCarrie = (CustomFontTextView) view.findViewById(R.id.carrie);
        mCarrie.setTypeface(AppController.getInstance().getString(R.string.opensansregular));
        mPortabilty = (CustomFontTextView) view.findViewById(R.id.portabilty);
        mPortabilty.setTypeface(AppController.getInstance().getString(R.string.opensansregular));
        mWhen = (CustomFontTextView) view.findViewById(R.id.when);
        mWhen.setTypeface(AppController.getInstance().getString(R.string.opensansregular));
        wLabelCanvas = (LinearLayout) view.findViewById(R.id.labelCanvas);
        mCarrieImage = (ImageView) view.findViewById(R.id.carrieImage);
        mButtonLougout = (ButtonFlat) view.findViewById(R.id.logout);
        mButtonLougout.setOnClickListener(fragment);
        mButtonSave = (ButtonFlat) view.findViewById(R.id.save);
        mButtonSave.setOnClickListener(fragment);
        mNotFound = (LinearLayout) view.findViewById(R.id.notFound);
        mButtonLougoutNotFound = (ButtonFlat) view.findViewById(R.id.logoutNotFount);
        mButtonLougoutNotFound.setOnClickListener(fragment);
        mLabelCarrieOld = (LinearLayout) view.findViewById(R.id.labelCarrieOld);
        mCarrieOld = (CustomFontTextView) view.findViewById(R.id.carrieOld);
        mContact = (CustomFontTextView) view.findViewById(R.id.contact);
        mPhone = (CustomFontTextView) view.findViewById(R.id.phone);
        mLoadingText = (CustomFontTextView) view.findViewById(R.id.loadingText);
        mButtonCall = (ButtonFlat) view.findViewById(R.id.call);
        mButtonCall.setOnClickListener(fragment);
        mSmsCall = (ButtonFlat) view.findViewById(R.id.sms);
        layoutAdmmob = (LinearLayout) view.findViewById(R.id.adMob);
        mSmsCall.setOnClickListener(fragment);
        mLabelContact = (LinearLayout) view.findViewById(R.id.labelContact);
        mLabelPhone = (LinearLayout) view.findViewById(R.id.labelPhone);
    }


    public static void populateView(Portabily.PushPortabily portabily, Contact contact, Contact currentContact) {
        hideLoad(false);
        if(portabily != null)
        {
            for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
            {
                if(dataPortabily != null)
                {
                    DialogCustom.mContact.setText(" " + contact.getName());
                    DialogCustom.mPhone.setText(" " + contact.getNumber());

                    if(!TextUtils.isEmpty(dataPortabily.getRn1()))
                    {
                        DialogCustom.mCarrieImage.setImageResource(
                                Carries.getCarreiImage(dataPortabily.getRn1())
                        );
                    }

                    if(!TextUtils.isEmpty(dataPortabily.getOperadoraAnterior()))
                    {
                        mCarrieOld.setText(" " + dataPortabily.getOperadoraAnterior());
                    }
                    else
                    {
                        mLabelCarrieOld.setVisibility(View.GONE);
                    }

                    if(TextUtils.isEmpty(dataPortabily.getOperadora()))
                    {
                        hideLoad(true);
                        notFound(); return;
                    }
                    else
                    {
                        loadAdMob();
                        currentContact.setCarrier(dataPortabily.getOperadora());
                        mCarrie.setText(" " + dataPortabily.getOperadora());
                    }
                    if(dataPortabily.isPortabilidade())
                    {
                        mPortabilty.setText(" " + AppController.getInstance().getString(R.string.yes));
                    }
                    else
                    {
                        mPortabilty.setText(" " + AppController.getInstance().getString(R.string.no));
                    }

                    if(!TextUtils.isEmpty(dataPortabily.getDate()))
                    {
                        mWhen.setText(" " + dataPortabily.getDate());
                    }
                    else
                    {
                        wLabelCanvas.setVisibility(View.GONE);
                    }
                }
                break;
            }
        }
        else
        {
            notFound(); return;
        }
    }

    public static void notFound()
    {
        mCarriesLinear.setVisibility(View.GONE);
        mNotFound.setVisibility(View.VISIBLE);
    }

    public static void hideLoad(boolean force)
    {
        if(adMobLoaded == true) {
            mLoadingLinear.setVisibility(View.GONE);
            mCarriesLinear.setVisibility(View.VISIBLE);
            adMobLoaded = false;
        }
        if(force == true){
            mLoadingLinear.setVisibility(View.GONE);
            mCarriesLinear.setVisibility(View.VISIBLE);
            adMobLoaded = false;
        }
    }

    public static void showLoad(String editLabel)
    {
        if(mLoadingLinear != null && mCarriesLinear != null){
            mLoadingLinear.setVisibility(View.VISIBLE);
            mCarriesLinear.setVisibility(View.GONE);
            mLoadingText.setText(editLabel);
        }
    }

    private static void loadAdMob()
    {
        adView = new AdView(AppController.getInstance());
        adView.setAdUnitId(AppController.pubAdMob);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                DialogCustom.layoutAdmmob.setVisibility(View.VISIBLE);
                adMobLoaded = true;
                hideLoad(false);
            }
        });
        DialogCustom.layoutAdmmob.addView(adView);
        adView.loadAd(AppController.getInstance().getAdRequest());
    }


    public static void populateViewCalls(Portabily.PushPortabily portabily, Calls calls, Calls currentCalls) {
        hideLoad(false);

        if(portabily != null)
        {
            for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
            {
                if(TextUtils.isEmpty(calls.getName()))
                {
                    mContact.setText(" " + AppController.getInstance().getString(R.string.unknow));
                }
                else
                {
                    mContact.setText(" " + calls.getName());
                }

                if(TextUtils.isEmpty(calls.getName()))
                {
                    mPhone.setText(" " + AppController.getInstance().getString(R.string.unknow));
                }
                else
                {
                    mPhone.setText(" " + calls.getNumber());
                }


                if(dataPortabily != null)
                {

                    if(!TextUtils.isEmpty(dataPortabily.getRn1()))
                    {
                        DialogCustom.mCarrieImage.setImageResource(
                                Carries.getCarreiImage(dataPortabily.getRn1())
                        );
                    }

                    if(!TextUtils.isEmpty(dataPortabily.getOperadoraAnterior()))
                    {
                        mCarrieOld.setText(" " + dataPortabily.getOperadoraAnterior());
                    }
                    else
                    {
                        mLabelCarrieOld.setVisibility(View.GONE);
                    }

                    if(TextUtils.isEmpty(dataPortabily.getOperadora()))
                    {
                        hideLoad(true);
                        notFound(); return;
                    }
                    else
                    {
                        loadAdMob();
                        currentCalls.setCarrier(dataPortabily.getOperadora());
                        mCarrie.setText(" " + dataPortabily.getOperadora());
                    }
                    if(dataPortabily.isPortabilidade())
                    {
                        mPortabilty.setText(" " + AppController.getInstance().getString(R.string.yes));
                    }
                    else
                    {
                        mPortabilty.setText(" " + AppController.getInstance().getString(R.string.no));
                    }

                    if(!TextUtils.isEmpty(dataPortabily.getDate()))
                    {
                        mWhen.setText(" " + dataPortabily.getDate());
                    }
                    else
                    {
                        wLabelCanvas.setVisibility(View.GONE);
                    }
                }
                break;
            }
        }
        else
        {
            notFound(); return;
        }
    }
}

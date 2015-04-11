package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends Fragment implements View.OnClickListener{

    public StickyListHeadersListView mListView;
    public ArrayList<Contact> mContacts;

    private LinearLayout mLoadingLinear;
    private LinearLayout mCarriesLinear;
    private LinearLayout mNotFound;
    private LinearLayout wLabelCanvas;
    private LinearLayout mAdViewLayout;
    public AdView mAdView;

    private CustomFontTextView mRegion;
    private CustomFontTextView mCarrie;
    private CustomFontTextView mPortabilty;
    private CustomFontTextView mWhen;

    private ButtonFlat mButtonLougout;
    private ButtonFlat mButtonLougoutNotFound;
    private ButtonFlat mButtonSave;

    private ImageView mCarrieImage;

    private AlertDialog mDialog;

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public ContactsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mAdView = new AdView(getActivity());
        mAdView.setAdUnitId(AppController.pubAdMob);
        mAdView.setAdSize(AdSize.BANNER);

        mContacts = Contact.getContacts(getActivity());

        mListView = (StickyListHeadersListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new ContactAdapter(this, getActivity(), mContacts));

        return view;
    }

    public void onClickListView(int position){
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_contacts, null);

        mDialog = new AlertDialog.Builder( getActivity() ).create();
        mDialog.setView(view);
        mDialog.setInverseBackgroundForced(true);

        mLoadingLinear = (LinearLayout) view.findViewById(R.id.loading);
        mCarriesLinear = (LinearLayout) view.findViewById(R.id.carries);

        mRegion = (CustomFontTextView) view.findViewById(R.id.region);
        mRegion.setTypeface(getString(R.string.opensansregular));
        mCarrie = (CustomFontTextView) view.findViewById(R.id.carrie);
        mCarrie.setTypeface(getString(R.string.opensansregular));
        mPortabilty = (CustomFontTextView) view.findViewById(R.id.portabilty);
        mPortabilty.setTypeface(getString(R.string.opensansregular));
        mWhen = (CustomFontTextView) view.findViewById(R.id.when);
        mWhen.setTypeface(getString(R.string.opensansregular));
        wLabelCanvas = (LinearLayout) view.findViewById(R.id.labelCanvas);
        mCarrieImage = (ImageView) view.findViewById(R.id.carrieImage);
        mButtonLougout = (ButtonFlat) view.findViewById(R.id.logout);
        mButtonLougout.setOnClickListener(this);
        mButtonSave = (ButtonFlat) view.findViewById(R.id.save);
        mButtonSave.setOnClickListener(this);
        mNotFound = (LinearLayout) view.findViewById(R.id.notFound);
        mButtonLougoutNotFound = (ButtonFlat) view.findViewById(R.id.logoutNotFount);
        mButtonLougoutNotFound.setOnClickListener(this);
        mAdViewLayout = (LinearLayout) view.findViewById(R.id.adView);

        try{
            mAdViewLayout.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("A0C1DE1295BCD23004629CE3AF971C7D").build();

            mAdView.loadAd(adRequest);
        }catch(IllegalStateException e){}


        mDialog.show();

        searchNumber(mContacts.get(position).getNumber());
    }

    public void searchNumber(String number){
        new ContactRequest(new String[] {number}){
            @Override
            public void onFinish(Portabily.PushPortabily portabily) {
                if(portabily != null){
                    for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                    {
                        if(dataPortabily != null)
                        {
                            mCarrieImage.setImageResource(
                                    Carries.getCarreiImage(dataPortabily.getRn1())
                            );
                            mLoadingLinear.setVisibility(View.GONE);
                            mCarriesLinear.setVisibility(View.VISIBLE);

                            if(TextUtils.isEmpty(dataPortabily.getOperadora())) {
                                notFound();
                            }else{
                                mCarrie.setText(" " + dataPortabily.getOperadora());
                            }
                            if(!TextUtils.isEmpty(dataPortabily.getOperadora())) {
                                mRegion.setText(" " + dataPortabily.getOperadora());
                            }
                            if(dataPortabily.isPortabilidade()) {
                                mPortabilty.setText(" " + getString(R.string.yes));
                            }else{
                                mPortabilty.setText(" " + getString(R.string.no));
                            }

                            if(!TextUtils.isEmpty(dataPortabily.getDate())){
                                mWhen.setText(" " + dataPortabily.getDate());
                            }else{
                                wLabelCanvas.setVisibility(View.GONE);
                            }
                        }
                    }
                }else{
                    if(mDialog.isShowing()){
                        mDialog.dismiss();
                    }
                    Crouton.makeText(getActivity(), R.string.error, Style.ALERT).show();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;

            case R.id.save:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;

            case R.id.logoutNotFount:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;
        }
    }

    private void notFound() {
        mCarriesLinear.setVisibility(View.GONE);
        mNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    public void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }
}

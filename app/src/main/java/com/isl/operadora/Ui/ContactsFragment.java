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
import com.isl.operadora.Util.Logger;
import com.isl.operadora.Util.Util;
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
    private LinearLayout mLabelCarrieOld;

    private CustomFontTextView mCarrie;
    private CustomFontTextView mPortabilty;
    private CustomFontTextView mWhen;
    private CustomFontTextView mCarrieOld;
    private CustomFontTextView mContact;
    private CustomFontTextView mPhone;

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

        mContacts = Contact.getContacts(getActivity());

        mListView = (StickyListHeadersListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new ContactAdapter(this, getActivity(), mContacts));

        return view;
    }

    public void onClickListView(int position)
    {
        if(!Util.isNetworkAvailable())
        {
            Crouton.makeText(getActivity(), R.string.error, Style.ALERT).show();
            return;
        }

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_contacts, null);

        mDialog = new AlertDialog.Builder( getActivity() ).create();
        mDialog.setView(view);
        mDialog.setInverseBackgroundForced(true);

        mLoadingLinear = (LinearLayout) view.findViewById(R.id.loading);
        mCarriesLinear = (LinearLayout) view.findViewById(R.id.carries);

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
        mLabelCarrieOld = (LinearLayout) view.findViewById(R.id.labelCarrieOld);
        mCarrieOld = (CustomFontTextView) view.findViewById(R.id.carrieOld);
        mContact = (CustomFontTextView) view.findViewById(R.id.contact);
        mPhone = (CustomFontTextView) view.findViewById(R.id.phone);

        mDialog.show();

        searchNumber(mContacts.get(position));
    }

    public void searchNumber(final Contact contact){
        new ContactRequest(new String[] { Util.formatPhone(contact.getNumber(), AppController.getInstance().mDdd.getDDD()) })
        {
            @Override
            public void onFinish(Portabily.PushPortabily portabily)
            {
                if(portabily != null)
                {
                    for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                    {
                        if(dataPortabily != null)
                        {
                            mContact.setText(" " + contact.getName());
                            mPhone.setText(" " + contact.getNumber());

                            mLoadingLinear.setVisibility(View.GONE);
                            mCarriesLinear.setVisibility(View.VISIBLE);

                            if(!TextUtils.isEmpty(dataPortabily.getRn1())){
                                mCarrieImage.setImageResource(
                                        Carries.getCarreiImage(dataPortabily.getRn1())
                                );
                            }

                            if(!TextUtils.isEmpty(dataPortabily.getOperadoraAnterior())){
                                mCarrieOld.setText(" " + dataPortabily.getOperadoraAnterior());
                            }else{
                                mLabelCarrieOld.setVisibility(View.GONE);
                            }

                            if(TextUtils.isEmpty(dataPortabily.getOperadora())) {
                                notFound(); return;
                            }else{
                                mCarrie.setText(" " + dataPortabily.getOperadora());
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
                }
                else
                {
                    mLoadingLinear.setVisibility(View.GONE);
                    mCarriesLinear.setVisibility(View.VISIBLE);
                    notFound(); return;
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
        return;
    }
}

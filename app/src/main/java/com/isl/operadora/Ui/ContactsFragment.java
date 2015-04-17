package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Util;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends Fragment implements View.OnClickListener{

    public StickyListHeadersListView mListView;
    public ArrayList<Contact> mContacts;
    public ArrayList<Contact> contactsForSearch;

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
    private CustomFontTextView mLoadingText;

    private ButtonFlat mButtonLougout;
    private ButtonFlat mButtonLougoutNotFound;
    private ButtonFlat mButtonSave;
    private ButtonFlat mButtonCall;
    private ButtonFlat mSmsCall;

    private ImageView mCarrieImage;
    private AlertDialog mDialog;
    private ContactAdapter mContactAdapter;

    private Contact currentContact;

    public static ContactsFragment newInstance()
    {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public ContactsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        mContacts = Contact.getContacts(getActivity());

        mListView = (StickyListHeadersListView) view.findViewById(R.id.listView);

        mContactAdapter = new ContactAdapter(this, getActivity(), mContacts);
        mListView.setAdapter(mContactAdapter);

        if(AppController.getInstance().search != null)
        {
            if(AppController.getInstance().search.getText().length() > 0)
            {
                searchContacts(AppController.getInstance().search.getText());
            }

            AppController.getInstance().search.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchContacts(s);
                }
            });
        }
        return view;
    }

    private void searchContacts(CharSequence s)
    {
        contactsForSearch = new ArrayList<Contact>();
        for(Contact contact : mContacts)
        {
            if(contact.getName().toLowerCase().contains(s.toString().toLowerCase()))
            {
                contactsForSearch.add(contact);
            }
        }
        mContactAdapter = new ContactAdapter(ContactsFragment.this, getActivity(), contactsForSearch);
        mListView.setAdapter(mContactAdapter);
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
        mLoadingText = (CustomFontTextView) view.findViewById(R.id.loadingText);
        mButtonCall = (ButtonFlat) view.findViewById(R.id.call);;
        mButtonCall.setOnClickListener(this);
        mSmsCall = (ButtonFlat) view.findViewById(R.id.sms);;
        mSmsCall.setOnClickListener(this);

        mDialog.show();

        if(contactsForSearch == null)
        {
            currentContact = mContacts.get(position);
        }
        else
        {
            currentContact = contactsForSearch.get(position);
        }
        searchNumber(currentContact);
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

                            if(!TextUtils.isEmpty(dataPortabily.getRn1()))
                            {
                                mCarrieImage.setImageResource(
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
                                notFound(); return;
                            }
                            else
                            {
                                currentContact.setCarrier(dataPortabily.getOperadora());
                                mCarrie.setText(" " + dataPortabily.getOperadora());
                            }
                            if(dataPortabily.isPortabilidade())
                            {
                                mPortabilty.setText(" " + getString(R.string.yes));
                            }
                            else
                            {
                                mPortabilty.setText(" " + getString(R.string.no));
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.logout:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;

            case R.id.save:
                editContact();
                break;

            case R.id.logoutNotFount:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;

            case R.id.call:
                startActivity(
                        new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentContact.getNumber()))
                );
                break;

            case R.id.sms:
                startActivity(
                        new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + currentContact.getName()))
                );
                break;
        }
    }

    private void editContact()
    {
        mLoadingLinear.setVisibility(View.VISIBLE);
        mCarriesLinear.setVisibility(View.GONE);
        mLoadingText.setText(R.string.editContact);

        new AsyncTask<Boolean, Void,Boolean>()
        {
            @Override
            protected Boolean doInBackground(Boolean... params)
            {
                String newLabel = Util.addLabel(currentContact.getLabel(), currentContact.getCarrier());
                return Contact.eddLabelNumber(currentContact.getId(), currentContact.getNumber(), newLabel);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean)
            {
                super.onPostExecute(aBoolean);
                if(aBoolean)
                {
                    Crouton.makeText(getActivity(), R.string.contactEdited, Style.CONFIRM).show();
                }
                else
                {
                    Crouton.makeText(getActivity(), R.string.contactNotEdited, Style.ALERT).show();
                }

                if(mDialog.isShowing())
                    mDialog.dismiss();

                currentContact.setLabel(Util.addLabel(currentContact.getLabel(), currentContact.getCarrier()));
                mContactAdapter.notifyDataSetChanged();

            }
        }.execute();
    }

    private void notFound()
    {
        mCarriesLinear.setVisibility(View.GONE);
        mNotFound.setVisibility(View.VISIBLE);
    }
}

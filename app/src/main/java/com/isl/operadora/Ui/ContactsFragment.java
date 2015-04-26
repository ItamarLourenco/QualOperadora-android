package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Util;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends BaseFragment implements View.OnClickListener{

    public StickyListHeadersListView mListView;
    public ArrayList<Contact> mContacts;
    public ArrayList<Contact> contactsForSearch;
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
            if(contact.getName() != null)
            {
                if(contact.getName().toLowerCase().contains(s.toString().toLowerCase()))
                {
                    contactsForSearch.add(contact);
                }
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
        DialogCustom.loadViews(view, this);
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
        new ContactRequest(new String[] { Util.formatPhone(contact.getNumber(), AppController.getInstance().getDDD()) })
        {
            @Override
            public void onFinish(Portabily.PushPortabily portabily)
            {
                DialogCustom.populateView(portabily, contact, currentContact);
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
        DialogCustom.showLoad(getString(R.string.editContact));
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
}

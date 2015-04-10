package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Logger;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends Fragment{

    public StickyListHeadersListView listView;
    public ArrayList<Contact> contacts;

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

        contacts = Contact.getContacts(getActivity());

        listView = (StickyListHeadersListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ContactAdapter(this, getActivity(), contacts));
        return view;
    }

    public void onClickListView(int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder( getActivity() );
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_contacts, null);
        dialog.setView(view);
        dialog.setInverseBackgroundForced(true);

        String number = contacts.get(position).getNumber();

        new ContactRequest(new String[] {number}){
            @Override
            public void onFinish(Portabily.PushPortabily portabily) {
                for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                {
                    Logger.t(dataPortabily.getPhone() + " = " + dataPortabily.getOperadora());
                }
            }
        };

        dialog.show();

    }
}

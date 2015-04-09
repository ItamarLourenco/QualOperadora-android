package com.isl.operadora.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends Fragment {

    public StickyListHeadersListView listView;

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
        listView = (StickyListHeadersListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ContactAdapter(getActivity(), Contact.getContacts(getActivity())));

        return view;
    }
}

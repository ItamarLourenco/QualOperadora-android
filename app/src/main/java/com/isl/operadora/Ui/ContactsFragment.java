package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Util.Util;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ContactsFragment extends Fragment{

    public StickyListHeadersListView listView;
    public ArrayList<Contact> contacts;
    public static final String security = "#@qu4l0pe4d0r4@#";

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
        dialog.show();

        Portabily portabily = new Portabily();
        Portabily.GetPortabily getPortabily = portabily.getPortabily();
        String timeStamp = Util.getTimeStamp();
        getPortabily.setTokenId(timeStamp);
        getPortabily.setTokenCryp(Util.md5(security + timeStamp));
        getPortabily.setPhones(new String[] {contacts.get(position).getNumber()});

        Toast.makeText(getActivity(), getPortabily.getJson(), Toast.LENGTH_LONG).show();

        String url = "http://54.191.245.5/qualOperadora/web/index.php?r=api/portabilidade";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), String.valueOf(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getContext().addToRequestQueue(stringRequest);

    }
}

package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isl.operadora.Adapter.ContactAdapter;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Contact;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_contacts, null);

        final AlertDialog dialog = new AlertDialog.Builder( getActivity() ).create();
        dialog.setView(view);
        dialog.setInverseBackgroundForced(true);
        final ImageView carrieImage = (ImageView) view.findViewById(R.id.carrie);
        final LinearLayout loadingLinear = (LinearLayout) view.findViewById(R.id.loading);

        String number = contacts.get(position).getNumber();
        new ContactRequest(new String[] {number}){
            @Override
            public void onFinish(Portabily.PushPortabily portabily) {
                if(portabily != null){
                    for(Portabily.PushPortabily.DataPortabily dataPortabily : portabily.getData())
                    {
                        carrieImage.setImageResource(
                                Carries.getCarreiImage(dataPortabily.getRn1())
                        );
                        loadingLinear.setVisibility(View.GONE);
                    }
                }else{
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                    Crouton.makeText(getActivity(), R.string.error, Style.ALERT).show();
                }
            }
        };
        dialog.show();
    }
}

package com.isl.operadora.Ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.isl.operadora.Adapter.CallAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Util;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CallFragment extends BaseFragment implements View.OnClickListener{

    private AlertDialog mDialog;
    private CallAdapter mCallAdapter;

    private Calls currentCall;

    private ArrayList<Calls> mCalls;
    private ArrayList<Calls> mCallsSearchs;
    private ListView mListView;

    public static CallFragment newInstance() {
        CallFragment fragment = new CallFragment();
        return fragment;
    }

    public CallFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalls = Calls.getCalls();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new CallAdapter(this, getActivity(), mCalls));

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
        mCallsSearchs = new ArrayList<Calls>();
        for(Calls call : mCalls)
        {
            if(call.getName() != null)
            {
                if(call.getName().toLowerCase().contains(s.toString().toLowerCase()))
                {
                    mCallsSearchs.add(call);
                }
            }
        }
        mCallAdapter = new CallAdapter(CallFragment.this, getActivity(), mCallsSearchs);
        mListView.setAdapter(mCallAdapter);
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

        if(mCallsSearchs == null)
        {
            currentCall = mCalls.get(position);
        }
        else
        {
            currentCall = mCallsSearchs.get(position);
        }
        searchNumber(currentCall);
    }

    public void searchNumber(final Calls call){
        new ContactRequest(new String[] { Util.formatPhone(call.getNumber(), AppController.getInstance().getDDD()) })
        {
            @Override
            public void onFinish(Portabily.PushPortabily portabily)
            {
                DialogCustom.populateViewCalls(portabily, call, currentCall);
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
                addContact();
                break;

            case R.id.logoutNotFount:
                if(mDialog.isShowing())
                    mDialog.dismiss();
                break;

            case R.id.call:
                startActivity(
                        new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentCall.getNumber()))
                );
                break;

            case R.id.sms:
                startActivity(
                        new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + currentCall.getName()))
                );
                break;
        }
    }

    private void addContact()
    {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        if(!TextUtils.isEmpty(currentCall.getName()))
        {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, currentCall.getName());
        }

        intent.putExtra(ContactsContract.Intents.Insert.PHONE, currentCall.getNumber());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, currentCall.getCarrier());

        getActivity().startActivity(intent);
    }
}

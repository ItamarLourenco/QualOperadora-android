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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonFlat;
import com.isl.operadora.Adapter.CallAdapter;
import com.isl.operadora.Application.AppController;
import com.isl.operadora.Base.BaseFragment;
import com.isl.operadora.Model.Calls;
import com.isl.operadora.Model.Carries;
import com.isl.operadora.Model.Portabily;
import com.isl.operadora.R;
import com.isl.operadora.Request.ContactRequest;
import com.isl.operadora.Util.Util;
import com.isl.operadora.Widgets.CustomFontTextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CallFragment extends BaseFragment implements View.OnClickListener{


    private LinearLayout mLoadingLinear;
    private LinearLayout mCarriesLinear;
    private LinearLayout mNotFound;
    private LinearLayout wLabelCanvas;
    private LinearLayout mLabelCarrieOld;

    private CustomFontTextView mCarrie;
    private CustomFontTextView mPortabilty;
    private CustomFontTextView mWhen;
    private CustomFontTextView mCarrieOld;
    private CustomFontTextView mCall;
    private CustomFontTextView mPhone;
    private CustomFontTextView mLoadingText;

    private ButtonFlat mButtonLougout;
    private ButtonFlat mButtonLougoutNotFound;
    private ButtonFlat mButtonSave;
    private ButtonFlat mButtonCall;
    private ButtonFlat mSmsCall;

    private ImageView mCarrieImage;
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
        mCall = (CustomFontTextView) view.findViewById(R.id.contact);
        mPhone = (CustomFontTextView) view.findViewById(R.id.phone);
        mLoadingText = (CustomFontTextView) view.findViewById(R.id.loadingText);
        mButtonCall = (ButtonFlat) view.findViewById(R.id.call);
        mButtonCall.setOnClickListener(this);
        mSmsCall = (ButtonFlat) view.findViewById(R.id.sms);
        mSmsCall.setOnClickListener(this);

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
        new ContactRequest(new String[] { Util.formatPhone(call.getNumber(), AppController.getInstance().mDdd.getDDD()) })
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
                            if(TextUtils.isEmpty(call.getName()))
                            {
                                mCall.setText(" " + getString(R.string.unknow));
                            }
                            else
                            {
                                mCall.setText(" " + call.getName());
                            }

                            mPhone.setText(" " + call.getNumber());

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
                                mCarrie.setText(" " + dataPortabily.getOperadora());
                                call.setCarrier(dataPortabily.getOperadora());
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

    private void addContact() {
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

    private void notFound()
    {
        mCarriesLinear.setVisibility(View.GONE);
        mNotFound.setVisibility(View.VISIBLE);
    }
}
